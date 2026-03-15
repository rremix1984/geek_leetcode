package com.bilibili.juc.web.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import org.springframework.stereotype.Service;

@Service
public class CodeExecutionService {

    private static final int DEFAULT_TIMEOUT_SECONDS = 5;
    private static final Pattern CLASS_NAME_PATTERN = Pattern.compile("public\\s+class\\s+([A-Za-z_$][A-Za-z\\d_$]*)");
    private static final Pattern PACKAGE_PATTERN = Pattern.compile("package\\s+([a-zA-Z_$][\\w$]*(?:\\.[a-zA-Z_$][\\w$]*)*)\\s*;");
    private static final Pattern MAX_RSS_PATTERN = Pattern.compile("^\\s*(\\d+)\\s+maximum resident set size\\s*$");

    public RunCodeResult execute(String code) {
        CompiledProgram compiledProgram = null;
        try {
            compiledProgram = compileSource(code);
            if (!compiledProgram.isSuccess()) {
                return RunCodeResult.fail(compiledProgram.getError());
            }
            RunCaseResult result = runCompiledProgram(compiledProgram, "", DEFAULT_TIMEOUT_SECONDS);
            if (!result.isSuccess()) {
                return RunCodeResult.fail(result.getError());
            }
            String output = result.getOutput();
            if (!result.getErrorOutput().isEmpty()) {
                output = output + "\n" + result.getErrorOutput();
            }
            return RunCodeResult.success(output);
        } catch (Exception e) {
            return RunCodeResult.fail("执行异常: " + e.getMessage());
        } finally {
            cleanup(compiledProgram);
        }
    }

    public CompiledProgram compileSource(String code) {
        if (code == null || code.trim().isEmpty()) {
            return CompiledProgram.fail("代码不能为空");
        }
        Matcher classMatcher = CLASS_NAME_PATTERN.matcher(code);
        if (!classMatcher.find()) {
            return CompiledProgram.fail("代码中未找到 public class");
        }
        String className = classMatcher.group(1);
        Matcher packageMatcher = PACKAGE_PATTERN.matcher(code);
        String packageName = packageMatcher.find() ? packageMatcher.group(1) : null;
        Path executionDir = Paths.get(System.getProperty("java.io.tmpdir"), "geek_leetcode_run", UUID.randomUUID().toString());
        try {
            Files.createDirectories(executionDir);
            Path sourceFile = writeSourceFile(executionDir, packageName, className, code);
            RunCodeResult compileResult = compile(executionDir, sourceFile);
            if (!compileResult.isSuccess()) {
                deleteRecursively(executionDir.toFile());
                return CompiledProgram.fail(compileResult.getError());
            }
            return CompiledProgram.success(executionDir, packageName, className);
        } catch (Exception e) {
            deleteRecursively(executionDir.toFile());
            return CompiledProgram.fail("执行异常: " + e.getMessage());
        }
    }

    public RunCaseResult runCompiledProgram(CompiledProgram compiledProgram, String input, int timeoutSeconds) {
        if (compiledProgram == null || !compiledProgram.isSuccess()) {
            return RunCaseResult.fail("编译结果不可用");
        }
        try {
            return run(compiledProgram.getExecutionDir(), compiledProgram.getPackageName(), compiledProgram.getClassName(), input,
                    timeoutSeconds <= 0 ? DEFAULT_TIMEOUT_SECONDS : timeoutSeconds);
        } catch (Exception e) {
            return RunCaseResult.fail("执行异常: " + e.getMessage());
        }
    }

    public void cleanup(CompiledProgram compiledProgram) {
        if (compiledProgram == null || compiledProgram.getExecutionDir() == null) {
            return;
        }
        deleteRecursively(compiledProgram.getExecutionDir().toFile());
    }

    private Path writeSourceFile(Path executionDir, String packageName, String className, String code) throws IOException {
        Path sourceFile;
        if (packageName == null || packageName.trim().isEmpty()) {
            sourceFile = executionDir.resolve(className + ".java");
        } else {
            Path packageDir = executionDir.resolve(packageName.replace('.', File.separatorChar));
            Files.createDirectories(packageDir);
            sourceFile = packageDir.resolve(className + ".java");
        }
        Files.write(sourceFile, code.getBytes(StandardCharsets.UTF_8));
        return sourceFile;
    }

    private RunCodeResult compile(Path executionDir, Path sourceFile) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) {
            return RunCodeResult.fail("未找到 JavaCompiler，请使用 JDK 运行服务");
        }
        ByteArrayOutputStream err = new ByteArrayOutputStream();
        int result = compiler.run(null, null, err, "-encoding", "UTF-8", "-cp", System.getProperty("java.class.path"), "-d",
                executionDir.toAbsolutePath().toString(), sourceFile.toAbsolutePath().toString());
        if (result != 0) {
            return RunCodeResult.fail("编译失败:\n" + err.toString());
        }
        return RunCodeResult.success("");
    }

    private RunCaseResult run(Path executionDir, String packageName, String className, String input, int timeoutSeconds)
            throws IOException, InterruptedException {
        String classpath = executionDir.toAbsolutePath() + File.pathSeparator + System.getProperty("java.class.path");
        String fullName = (packageName == null || packageName.trim().isEmpty()) ? className : packageName + "." + className;
        List<String> command = new ArrayList<>();
        String timeCommand = resolveTimeCommand();
        if (timeCommand != null) {
            command.add(timeCommand);
            command.add("-l");
        }
        command.add(resolveJavaCommand());
        command.addAll(Arrays.asList("-Xms64m", "-Xmx256m", "-cp", classpath, fullName));
        ProcessBuilder builder = new ProcessBuilder(command);
        builder.directory(executionDir.toFile());
        long start = System.nanoTime();
        Process process = builder.start();
        writeInput(process.getOutputStream(), input);
        boolean finished = process.waitFor(timeoutSeconds, TimeUnit.SECONDS);
        if (!finished) {
            process.destroyForcibly();
            return RunCaseResult.fail("运行超时（" + timeoutSeconds + "秒）");
        }
        long runtimeMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start);
        String output = readStream(process.getInputStream());
        String rawError = readStream(process.getErrorStream());
        ParsedError parsedError = parseErrorAndMemory(rawError);
        if (process.exitValue() != 0) {
            String err = parsedError.error.isEmpty() ? "运行失败，退出码: " + process.exitValue() : parsedError.error;
            return RunCaseResult.fail(err, runtimeMs, parsedError.memoryKb);
        }
        return RunCaseResult.success(output, parsedError.error, runtimeMs, parsedError.memoryKb);
    }

    private String readStream(java.io.InputStream stream) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = stream.read(buffer)) != -1) {
            out.write(buffer, 0, len);
        }
        return new String(out.toByteArray(), StandardCharsets.UTF_8);
    }

    private void writeInput(OutputStream stream, String input) throws IOException {
        if (stream == null) {
            return;
        }
        if (input != null && !input.isEmpty()) {
            stream.write(input.getBytes(StandardCharsets.UTF_8));
        }
        stream.flush();
        stream.close();
    }

    private ParsedError parseErrorAndMemory(String rawError) {
        if (rawError == null || rawError.isEmpty()) {
            return new ParsedError("", 0L);
        }
        long memory = 0L;
        List<String> kept = new ArrayList<>();
        String[] lines = rawError.split("\\r?\\n");
        for (String line : lines) {
            Matcher matcher = MAX_RSS_PATTERN.matcher(line);
            if (matcher.matches()) {
                try {
                    memory = Long.parseLong(matcher.group(1));
                } catch (NumberFormatException ignored) {
                }
                continue;
            }
            String trimmed = line.trim();
            if (trimmed.matches("\\d+\\s+.*")) {
                continue;
            }
            if (!trimmed.isEmpty()) {
                kept.add(line);
            }
        }
        String joined = String.join("\n", kept).trim();
        return new ParsedError(joined, memory);
    }

    private void deleteRecursively(File file) {
        if (file == null || !file.exists()) {
            return;
        }
        if (file.isDirectory()) {
            File[] children = file.listFiles();
            if (children != null) {
                for (File child : children) {
                    deleteRecursively(child);
                }
            }
        }
        file.delete();
    }

    private String resolveJavaCommand() {
        String javaHome = System.getProperty("java.home");
        if (javaHome == null || javaHome.trim().isEmpty()) {
            return "java";
        }
        Path javaPath = Paths.get(javaHome, "bin", "java");
        if (Files.exists(javaPath)) {
            return javaPath.toAbsolutePath().toString();
        }
        return "java";
    }

    private String resolveTimeCommand() {
        Path path = Paths.get("/usr/bin/time");
        if (Files.exists(path)) {
            return path.toAbsolutePath().toString();
        }
        return null;
    }

    public static class CompiledProgram {
        private final boolean success;
        private final String error;
        private final Path executionDir;
        private final String packageName;
        private final String className;

        private CompiledProgram(boolean success, String error, Path executionDir, String packageName, String className) {
            this.success = success;
            this.error = error;
            this.executionDir = executionDir;
            this.packageName = packageName;
            this.className = className;
        }

        public static CompiledProgram success(Path executionDir, String packageName, String className) {
            return new CompiledProgram(true, "", executionDir, packageName, className);
        }

        public static CompiledProgram fail(String error) {
            return new CompiledProgram(false, error, null, null, null);
        }

        public boolean isSuccess() {
            return success;
        }

        public String getError() {
            return error;
        }

        public Path getExecutionDir() {
            return executionDir;
        }

        public String getPackageName() {
            return packageName;
        }

        public String getClassName() {
            return className;
        }
    }

    public static class RunCaseResult {
        private final boolean success;
        private final String output;
        private final String error;
        private final String errorOutput;
        private final long runtimeMs;
        private final long memoryKb;

        private RunCaseResult(boolean success, String output, String error, String errorOutput, long runtimeMs, long memoryKb) {
            this.success = success;
            this.output = output;
            this.error = error;
            this.errorOutput = errorOutput;
            this.runtimeMs = runtimeMs;
            this.memoryKb = memoryKb;
        }

        public static RunCaseResult success(String output, String errorOutput, long runtimeMs, long memoryKb) {
            return new RunCaseResult(true, output, "", errorOutput, runtimeMs, memoryKb);
        }

        public static RunCaseResult fail(String error) {
            return new RunCaseResult(false, "", error, "", 0L, 0L);
        }

        public static RunCaseResult fail(String error, long runtimeMs, long memoryKb) {
            return new RunCaseResult(false, "", error, "", runtimeMs, memoryKb);
        }

        public boolean isSuccess() {
            return success;
        }

        public String getOutput() {
            return output;
        }

        public String getError() {
            return error;
        }

        public String getErrorOutput() {
            return errorOutput;
        }

        public long getRuntimeMs() {
            return runtimeMs;
        }

        public long getMemoryKb() {
            return memoryKb;
        }
    }

    public static class RunCodeResult {
        private final boolean success;
        private final String output;
        private final String error;

        private RunCodeResult(boolean success, String output, String error) {
            this.success = success;
            this.output = output;
            this.error = error;
        }

        public static RunCodeResult success(String output) {
            return new RunCodeResult(true, output, "");
        }

        public static RunCodeResult fail(String error) {
            return new RunCodeResult(false, "", error);
        }

        public boolean isSuccess() {
            return success;
        }

        public String getOutput() {
            return output;
        }

        public String getError() {
            return error;
        }
    }

    private static class ParsedError {
        private final String error;
        private final long memoryKb;

        private ParsedError(String error, long memoryKb) {
            this.error = error;
            this.memoryKb = memoryKb;
        }
    }
}

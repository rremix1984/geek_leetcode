package com.bilibili.juc.web.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

@Service
public class ProblemSubmitService {

    private static final Pattern INT_PATTERN = Pattern.compile("-?\\d+");

    private final CodeExecutionService codeExecutionService;
    private final ProblemProgressService problemProgressService;

    public ProblemSubmitService(CodeExecutionService codeExecutionService, ProblemProgressService problemProgressService) {
        this.codeExecutionService = codeExecutionService;
        this.problemProgressService = problemProgressService;
    }

    public Map<String, Object> submit(String problemId, String code) {
        List<TestCase> testCases = loadTestCases(problemId);
        if (testCases.isEmpty()) {
            return submitWithoutStandardCases(problemId, code);
        }
        CodeExecutionService.CompiledProgram compiledProgram = codeExecutionService.compileSource(code);
        if (!compiledProgram.isSuccess()) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("ok", true);
            map.put("accepted", false);
            map.put("id", problemId);
            map.put("totalCases", testCases.size());
            map.put("passCount", 0);
            map.put("passRate", 0D);
            map.put("runtimeMs", 0L);
            map.put("memoryKb", 0L);
            map.put("error", compiledProgram.getError());
            map.put("cases", Collections.emptyList());
            ProblemProgressService.ProblemProgress progress = problemProgressService.recordSubmit(problemId, false, 0L, 0L);
            map.put("progress", progress);
            return map;
        }
        List<Map<String, Object>> caseResults = new ArrayList<>();
        int passCount = 0;
        long maxRuntimeMs = 0L;
        long maxMemoryKb = 0L;
        try {
            for (int i = 0; i < testCases.size(); i++) {
                TestCase testCase = testCases.get(i);
                CodeExecutionService.RunCaseResult runCaseResult = codeExecutionService.runCompiledProgram(compiledProgram,
                        testCase.input + "\n", 5);
                String actual = normalizeOutput(runCaseResult.getOutput());
                boolean passed = runCaseResult.isSuccess() && compare(problemId, actual, testCase.expected);
                if (passed) {
                    passCount++;
                }
                long memoryKb = normalizeMemoryKb(runCaseResult.getMemoryKb());
                maxRuntimeMs = Math.max(maxRuntimeMs, runCaseResult.getRuntimeMs());
                maxMemoryKb = Math.max(maxMemoryKb, memoryKb);
                Map<String, Object> detail = new LinkedHashMap<>();
                detail.put("index", i + 1);
                detail.put("input", testCase.input);
                detail.put("expected", testCase.expected);
                detail.put("actual", runCaseResult.isSuccess() ? actual : "");
                detail.put("passed", passed);
                detail.put("runtimeMs", runCaseResult.getRuntimeMs());
                detail.put("memoryKb", memoryKb);
                detail.put("error", runCaseResult.isSuccess() ? runCaseResult.getErrorOutput() : runCaseResult.getError());
                caseResults.add(detail);
            }
        } finally {
            codeExecutionService.cleanup(compiledProgram);
        }
        boolean accepted = passCount == testCases.size();
        double passRate = testCases.isEmpty() ? 0D : passCount * 100D / testCases.size();
        ProblemProgressService.ProblemProgress progress = problemProgressService.recordSubmit(problemId, accepted, maxRuntimeMs,
                maxMemoryKb);
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("ok", true);
        map.put("accepted", accepted);
        map.put("id", problemId);
        map.put("totalCases", testCases.size());
        map.put("passCount", passCount);
        map.put("passRate", Math.round(passRate * 100D) / 100D);
        map.put("runtimeMs", maxRuntimeMs);
        map.put("memoryKb", maxMemoryKb);
        map.put("cases", caseResults);
        map.put("progress", progress);
        return map;
    }

    private Map<String, Object> submitWithoutStandardCases(String problemId, String code) {
        CodeExecutionService.CompiledProgram compiledProgram = codeExecutionService.compileSource(code);
        if (!compiledProgram.isSuccess()) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("ok", true);
            map.put("accepted", false);
            map.put("id", problemId);
            map.put("totalCases", 0);
            map.put("passCount", 0);
            map.put("passRate", 0D);
            map.put("runtimeMs", 0L);
            map.put("memoryKb", 0L);
            map.put("error", compiledProgram.getError());
            map.put("cases", Collections.emptyList());
            ProblemProgressService.ProblemProgress progress = problemProgressService.recordSubmit(problemId, false, 0L, 0L);
            map.put("progress", progress);
            return map;
        }
        try {
            CodeExecutionService.RunCaseResult runCaseResult = codeExecutionService.runCompiledProgram(compiledProgram, "", 5);
            long memoryKb = normalizeMemoryKb(runCaseResult.getMemoryKb());
            List<Map<String, Object>> cases = new ArrayList<>();
            Map<String, Object> detail = new LinkedHashMap<>();
            detail.put("index", 1);
            detail.put("input", "");
            detail.put("expected", "该题目未配置标准测试用例");
            detail.put("actual", runCaseResult.isSuccess() ? normalizeOutput(runCaseResult.getOutput()) : "");
            detail.put("passed", false);
            detail.put("runtimeMs", runCaseResult.getRuntimeMs());
            detail.put("memoryKb", memoryKb);
            detail.put("error", runCaseResult.isSuccess() ? runCaseResult.getErrorOutput() : runCaseResult.getError());
            cases.add(detail);
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("ok", true);
            map.put("accepted", false);
            map.put("id", problemId);
            map.put("totalCases", 0);
            map.put("passCount", 0);
            map.put("passRate", 0D);
            map.put("runtimeMs", runCaseResult.getRuntimeMs());
            map.put("memoryKb", memoryKb);
            map.put("error", "该题目暂未配置标准判题用例，已执行一次运行检查");
            map.put("cases", cases);
            ProblemProgressService.ProblemProgress progress = problemProgressService.recordSubmit(problemId, false,
                    runCaseResult.getRuntimeMs(), memoryKb);
            map.put("progress", progress);
            return map;
        } finally {
            codeExecutionService.cleanup(compiledProgram);
        }
    }

    private List<TestCase> loadTestCases(String problemId) {
        String id = problemId == null ? "" : problemId.trim().toLowerCase(Locale.ROOT);
        if ("no001".equals(id)) {
            return Arrays.asList(new TestCase("2 7 11 15\n9", "0 1"), new TestCase("3 2 4\n6", "1 2"),
                    new TestCase("3 3\n6", "0 1"));
        }
        if ("no704".equals(id)) {
            return Arrays.asList(new TestCase("-1 0 3 5 9 12\n9", "4"), new TestCase("-1 0 3 5 9 12\n2", "-1"),
                    new TestCase("5\n5", "0"));
        }
        if ("no121".equals(id)) {
            return Arrays.asList(new TestCase("7 1 5 3 6 4", "5"), new TestCase("7 6 4 3 1", "0"),
                    new TestCase("2 4 1", "2"));
        }
        return Collections.emptyList();
    }

    private boolean compare(String problemId, String actual, String expected) {
        if (problemId == null) {
            return normalizeOutput(expected).equals(actual);
        }
        String id = problemId.toLowerCase(Locale.ROOT);
        if ("no001".equals(id)) {
            List<Integer> a = parseInts(actual);
            List<Integer> e = parseInts(expected);
            if (a.size() < 2 || e.size() < 2) {
                return false;
            }
            int a0 = a.get(0);
            int a1 = a.get(1);
            int e0 = e.get(0);
            int e1 = e.get(1);
            return (a0 == e0 && a1 == e1) || (a0 == e1 && a1 == e0);
        }
        if ("no704".equals(id) || "no121".equals(id)) {
            List<Integer> a = parseInts(actual);
            List<Integer> e = parseInts(expected);
            if (a.isEmpty() || e.isEmpty()) {
                return false;
            }
            return a.get(0).intValue() == e.get(0).intValue();
        }
        return normalizeOutput(expected).equals(actual);
    }

    private List<Integer> parseInts(String text) {
        List<Integer> list = new ArrayList<>();
        if (text == null || text.isEmpty()) {
            return list;
        }
        Matcher matcher = INT_PATTERN.matcher(text);
        while (matcher.find()) {
            try {
                list.add(Integer.parseInt(matcher.group()));
            } catch (NumberFormatException ignored) {
            }
        }
        return list;
    }

    private String normalizeOutput(String value) {
        if (value == null) {
            return "";
        }
        return value.replace("\r", "").trim();
    }

    private long normalizeMemoryKb(long rawValue) {
        if (rawValue <= 0) {
            return 0L;
        }
        if (rawValue > 1024 * 1024) {
            return rawValue / 1024L;
        }
        return rawValue;
    }

    private static class TestCase {
        private final String input;
        private final String expected;

        private TestCase(String input, String expected) {
            this.input = input;
            this.expected = expected;
        }
    }
}

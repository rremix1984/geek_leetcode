package com.bilibili.juc.web.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ProblemProgressService {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Path storagePath = resolveStoragePath();
    private final Map<String, ProblemProgress> progressMap = load();

    public synchronized ProblemProgress snapshot(String problemId) {
        if (problemId == null || problemId.trim().isEmpty()) {
            return ProblemProgress.empty();
        }
        ProblemProgress progress = progressMap.get(problemId.trim());
        if (progress == null) {
            return ProblemProgress.empty();
        }
        return progress.copy();
    }

    public synchronized Map<String, ProblemProgress> snapshotAll() {
        Map<String, ProblemProgress> map = new LinkedHashMap<>();
        for (Map.Entry<String, ProblemProgress> entry : progressMap.entrySet()) {
            map.put(entry.getKey(), entry.getValue().copy());
        }
        return Collections.unmodifiableMap(map);
    }

    public synchronized ProblemProgress recordSubmit(String problemId, boolean accepted, long runtimeMs, long memoryKb) {
        if (problemId == null || problemId.trim().isEmpty()) {
            return ProblemProgress.empty();
        }
        String id = problemId.trim();
        ProblemProgress progress = progressMap.get(id);
        if (progress == null) {
            progress = new ProblemProgress();
            progressMap.put(id, progress);
        }
        progress.attempts++;
        progress.lastSubmittedAt = Instant.now().toString();
        if (accepted) {
            progress.accepted = true;
            progress.acceptedCount++;
            progress.lastAcceptedAt = progress.lastSubmittedAt;
            if (runtimeMs > 0 && (progress.bestRuntimeMs <= 0 || runtimeMs < progress.bestRuntimeMs)) {
                progress.bestRuntimeMs = runtimeMs;
            }
            if (memoryKb > 0 && (progress.bestMemoryKb <= 0 || memoryKb < progress.bestMemoryKb)) {
                progress.bestMemoryKb = memoryKb;
            }
        }
        save();
        return progress.copy();
    }

    private Path resolveStoragePath() {
        Path dir = Paths.get(System.getProperty("user.home"), ".geek-leetcode-web");
        try {
            Files.createDirectories(dir);
        } catch (IOException ignored) {
            Path fallback = Paths.get(System.getProperty("java.io.tmpdir"), "geek-leetcode-web");
            try {
                Files.createDirectories(fallback);
                return fallback.resolve("problem_progress.json");
            } catch (IOException ignoredToo) {
                return Paths.get(System.getProperty("java.io.tmpdir"), "problem_progress.json");
            }
        }
        return dir.resolve("problem_progress.json");
    }

    private Map<String, ProblemProgress> load() {
        if (!Files.exists(storagePath)) {
            return new LinkedHashMap<>();
        }
        try {
            byte[] bytes = Files.readAllBytes(storagePath);
            if (bytes.length == 0) {
                return new LinkedHashMap<>();
            }
            Map<String, ProblemProgress> map = objectMapper.readValue(bytes, new TypeReference<Map<String, ProblemProgress>>() {
            });
            return map == null ? new LinkedHashMap<>() : new LinkedHashMap<>(map);
        } catch (Exception ignored) {
            return new LinkedHashMap<>();
        }
    }

    private void save() {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(storagePath.toFile(), progressMap);
        } catch (IOException ignored) {
        }
    }

    public static class ProblemProgress {
        private boolean accepted;
        private int attempts;
        private int acceptedCount;
        private String lastSubmittedAt;
        private String lastAcceptedAt;
        private long bestRuntimeMs;
        private long bestMemoryKb;

        public static ProblemProgress empty() {
            return new ProblemProgress();
        }

        public ProblemProgress copy() {
            ProblemProgress copy = new ProblemProgress();
            copy.accepted = accepted;
            copy.attempts = attempts;
            copy.acceptedCount = acceptedCount;
            copy.lastSubmittedAt = lastSubmittedAt;
            copy.lastAcceptedAt = lastAcceptedAt;
            copy.bestRuntimeMs = bestRuntimeMs;
            copy.bestMemoryKb = bestMemoryKb;
            return copy;
        }

        public boolean isAccepted() {
            return accepted;
        }

        public void setAccepted(boolean accepted) {
            this.accepted = accepted;
        }

        public int getAttempts() {
            return attempts;
        }

        public void setAttempts(int attempts) {
            this.attempts = attempts;
        }

        public int getAcceptedCount() {
            return acceptedCount;
        }

        public void setAcceptedCount(int acceptedCount) {
            this.acceptedCount = acceptedCount;
        }

        public String getLastSubmittedAt() {
            return lastSubmittedAt;
        }

        public void setLastSubmittedAt(String lastSubmittedAt) {
            this.lastSubmittedAt = lastSubmittedAt;
        }

        public String getLastAcceptedAt() {
            return lastAcceptedAt;
        }

        public void setLastAcceptedAt(String lastAcceptedAt) {
            this.lastAcceptedAt = lastAcceptedAt;
        }

        public long getBestRuntimeMs() {
            return bestRuntimeMs;
        }

        public void setBestRuntimeMs(long bestRuntimeMs) {
            this.bestRuntimeMs = bestRuntimeMs;
        }

        public long getBestMemoryKb() {
            return bestMemoryKb;
        }

        public void setBestMemoryKb(long bestMemoryKb) {
            this.bestMemoryKb = bestMemoryKb;
        }
    }
}

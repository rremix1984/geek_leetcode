package com.animation.launcher;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 最近访问算法管理器
 * 负责管理用户最近访问的算法记录，提供持久化存储功能
 * 
 * @author 开发工程师
 * @version 1.0
 */
public class RecentAlgorithmManager {
    private static final int MAX_RECENT_COUNT = 5;
    private static final String CONFIG_DIR = ".leetcode_animation";
    private static final String CONFIG_FILE = "recent_algorithms.json";
    
    private LinkedList<RecentAlgorithmInfo> recentAlgorithms;
    private Gson gson;
    private Path configFilePath;
    
    /**
     * 最近访问算法信息
     */
    public static class RecentAlgorithmInfo {
        private String algorithmName;
        private String difficulty;
        private String technique;
        private String lastAccessTime;
        private int accessCount;
        
        public RecentAlgorithmInfo() {}
        
        public RecentAlgorithmInfo(String algorithmName, String difficulty, String technique) {
            this.algorithmName = algorithmName;
            this.difficulty = difficulty;
            this.technique = technique;
            this.lastAccessTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            this.accessCount = 1;
        }
        
        // Getters and Setters
        public String getAlgorithmName() { return algorithmName; }
        public void setAlgorithmName(String algorithmName) { this.algorithmName = algorithmName; }
        
        public String getDifficulty() { return difficulty; }
        public void setDifficulty(String difficulty) { this.difficulty = difficulty; }
        
        public String getTechnique() { return technique; }
        public void setTechnique(String technique) { this.technique = technique; }
        
        public String getLastAccessTime() { return lastAccessTime; }
        public void setLastAccessTime(String lastAccessTime) { this.lastAccessTime = lastAccessTime; }
        
        public int getAccessCount() { return accessCount; }
        public void setAccessCount(int accessCount) { this.accessCount = accessCount; }
        
        /**
         * 更新访问时间和次数
         */
        public void updateAccess() {
            this.lastAccessTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            this.accessCount++;
        }
        
        /**
         * 获取显示用的简短时间
         */
        public String getShortTime() {
            try {
                LocalDateTime dateTime = LocalDateTime.parse(lastAccessTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                LocalDateTime now = LocalDateTime.now();
                
                if (dateTime.toLocalDate().equals(now.toLocalDate())) {
                    return dateTime.format(DateTimeFormatter.ofPattern("HH:mm"));
                } else {
                    return dateTime.format(DateTimeFormatter.ofPattern("MM-dd"));
                }
            } catch (Exception e) {
                return "未知";
            }
        }
        
        /**
         * 获取难度颜色
         */
        public String getDifficultyColor() {
            switch (difficulty.toLowerCase()) {
                case "easy": return "#28a745";
                case "normal": case "medium": return "#ffc107";
                case "hard": return "#dc3545";
                default: return "#6c757d";
            }
        }
        
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            RecentAlgorithmInfo that = (RecentAlgorithmInfo) obj;
            return Objects.equals(algorithmName, that.algorithmName);
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(algorithmName);
        }
        
        @Override
        public String toString() {
            return algorithmName + " [" + difficulty + "] - " + getShortTime();
        }
    }
    
    /**
     * 构造函数
     */
    public RecentAlgorithmManager() {
        this.recentAlgorithms = new LinkedList<>();
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.configFilePath = initConfigPath();
        loadRecentAlgorithms();
    }
    
    /**
     * 初始化配置文件路径
     */
    private Path initConfigPath() {
        try {
            String userHome = System.getProperty("user.home");
            Path configDir = Paths.get(userHome, CONFIG_DIR);
            
            // 创建配置目录（如果不存在）
            if (!Files.exists(configDir)) {
                Files.createDirectories(configDir);
            }
            
            return configDir.resolve(CONFIG_FILE);
        } catch (Exception e) {
            System.err.println("初始化配置路径失败: " + e.getMessage());
            // 使用临时目录作为备选
            return Paths.get(System.getProperty("java.io.tmpdir"), CONFIG_FILE);
        }
    }
    
    /**
     * 添加最近访问的算法
     */
    public synchronized void addRecentAlgorithm(String algorithmName, String difficulty, String technique) {
        if (algorithmName == null || algorithmName.trim().isEmpty()) {
            return;
        }
        
        try {
            RecentAlgorithmInfo newInfo = new RecentAlgorithmInfo(algorithmName.trim(), difficulty, technique);
            
            // 检查是否已存在
            RecentAlgorithmInfo existing = recentAlgorithms.stream()
                    .filter(info -> info.equals(newInfo))
                    .findFirst()
                    .orElse(null);
            
            if (existing != null) {
                // 更新现有记录
                existing.updateAccess();
                // 移动到列表头部
                recentAlgorithms.remove(existing);
                recentAlgorithms.addFirst(existing);
            } else {
                // 添加新记录
                recentAlgorithms.addFirst(newInfo);
                
                // 保持最大数量限制
                while (recentAlgorithms.size() > MAX_RECENT_COUNT) {
                    recentAlgorithms.removeLast();
                }
            }
            
            // 保存到文件
            saveRecentAlgorithms();
            
        } catch (Exception e) {
            System.err.println("添加最近访问算法失败: " + e.getMessage());
        }
    }
    
    /**
     * 获取最近访问的算法列表
     */
    public List<RecentAlgorithmInfo> getRecentAlgorithms() {
        return new ArrayList<>(recentAlgorithms);
    }
    
    /**
     * 清除所有最近访问记录
     */
    public synchronized void clearRecentAlgorithms() {
        try {
            recentAlgorithms.clear();
            saveRecentAlgorithms();
        } catch (Exception e) {
            System.err.println("清除最近访问记录失败: " + e.getMessage());
        }
    }
    
    /**
     * 从文件加载最近访问记录
     */
    private void loadRecentAlgorithms() {
        try {
            if (!Files.exists(configFilePath)) {
                return;
            }
            
            // Java 8兼容的文件读取方式
            String json = readFileContent(configFilePath);
            if (json.trim().isEmpty()) {
                return;
            }
            
            Type listType = new TypeToken<LinkedList<RecentAlgorithmInfo>>(){}.getType();
            LinkedList<RecentAlgorithmInfo> loaded = gson.fromJson(json, listType);
            
            if (loaded != null) {
                recentAlgorithms = loaded;
                
                // 确保不超过最大数量
                while (recentAlgorithms.size() > MAX_RECENT_COUNT) {
                    recentAlgorithms.removeLast();
                }
            }
            
        } catch (Exception e) {
            System.err.println("加载最近访问记录失败: " + e.getMessage());
            recentAlgorithms = new LinkedList<>();
        }
    }
    
    /**
     * 保存最近访问记录到文件
     */
    private void saveRecentAlgorithms() {
        try {
            String json = gson.toJson(recentAlgorithms);
            writeFileContent(configFilePath, json);
        } catch (Exception e) {
            System.err.println("保存最近访问记录失败: " + e.getMessage());
        }
    }
    
    /**
     * Java 8兼容的文件读取方法
     */
    private String readFileContent(Path filePath) throws Exception {
        byte[] bytes = Files.readAllBytes(filePath);
        return new String(bytes, "UTF-8");
    }
    
    /**
     * Java 8兼容的文件写入方法
     */
    private void writeFileContent(Path filePath, String content) throws Exception {
        Files.write(filePath, content.getBytes("UTF-8"));
    }
    
    /**
     * 获取配置文件路径（用于调试）
     */
    public String getConfigFilePath() {
        return configFilePath.toString();
    }
    
    /**
     * 获取最近访问数量
     */
    public int getRecentCount() {
        return recentAlgorithms.size();
    }
    
    /**
     * 检查是否为空
     */
    public boolean isEmpty() {
        return recentAlgorithms.isEmpty();
    }
}
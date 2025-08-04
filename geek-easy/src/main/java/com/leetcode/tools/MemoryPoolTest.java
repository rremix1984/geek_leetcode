package com.leetcode.tools;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.util.List;

/**
 * 内存池测试工具
 * 用于查看当前JVM的所有内存池名称
 */
public class MemoryPoolTest {
    public static void main(String[] args) {
        System.out.println("=== JVM内存池信息 ===");
        
        List<MemoryPoolMXBean> memoryPools = ManagementFactory.getMemoryPoolMXBeans();
        
        for (MemoryPoolMXBean pool : memoryPools) {
            String name = pool.getName();
            String type = pool.getType().toString();
            
            System.out.println("内存池名称: " + name);
            System.out.println("内存池类型: " + type);
            
            if (pool.getUsage() != null) {
                long used = pool.getUsage().getUsed();
                long max = pool.getUsage().getMax();
                System.out.println("已使用: " + formatBytes(used));
                System.out.println("最大值: " + (max > 0 ? formatBytes(max) : "无限制"));
            }
            System.out.println("---");
        }
    }
    
    private static String formatBytes(long bytes) {
        if (bytes < 0) return "N/A";
        
        String[] units = {"B", "KB", "MB", "GB", "TB"};
        int unitIndex = 0;
        double size = bytes;
        
        while (size >= 1024 && unitIndex < units.length - 1) {
            size /= 1024;
            unitIndex++;
        }
        
        return String.format("%.2f %s", size, units[unitIndex]);
    }
}
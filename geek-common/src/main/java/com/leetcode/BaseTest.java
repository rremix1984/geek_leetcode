/**
 * copyright 2022/1/19
 */
package com.leetcode;

import org.junit.After;
import org.junit.Before;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class BaseTest {

    long startTime, endTime;

    @Before
    public void before() {
        startTime = System.currentTimeMillis();
    }

    @After
    public void after() {
        endTime = System.currentTimeMillis();
        System.out.printf("cost: %d ms", endTime - startTime);
    }

    // Math.random() -> [0, 1) 所有的小数，等概率返回
    // Math.random() * N -> [0, n) 所有小数，等概率返回
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random())
                    - (int) (maxValue * Math.random());
        }
        return arr;
    }

    public static Map<String, String> evaluateComplexity(String className, String methodName) {
        Map<String, String> complexityMap = new HashMap<>();

        try {
            Class<?> cls = Class.forName(className);
            Method method = cls.getMethod(methodName, int[].class);

            // 模拟输入数据，假设输入是一个数组
            int[] exampleInput = new int[1000];  // 修改数组大小进行测试

            long startTime = System.nanoTime();
            method.invoke(cls.newInstance(), exampleInput);

            long endTime = System.nanoTime();

            long duration = endTime - startTime;

            // 简单时间复杂度分析（这里只是演示，实际需要更复杂的分析）
            if (duration < 1000000) {
                complexityMap.put("Time Complexity", "O(1)");
            } else if (duration < 10000000) {
                complexityMap.put("Time Complexity", "O(n)");
            } else {
                complexityMap.put("Time Complexity", "O(n^2)");
            }

            // 简单空间复杂度分析
            Runtime runtime = Runtime.getRuntime();
            long usedMemoryBefore = runtime.totalMemory() - runtime.freeMemory();
            method.invoke(cls.newInstance(), (Object) exampleInput);
            long usedMemoryAfter = runtime.totalMemory() - runtime.freeMemory();
            long memoryUsage = usedMemoryAfter - usedMemoryBefore;

            if (memoryUsage < 1024) {
                complexityMap.put("Space Complexity", "O(1)");
            } else if (memoryUsage < 10240) {
                complexityMap.put("Space Complexity", "O(n)");
            } else {
                complexityMap.put("Space Complexity", "O(n^2)");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return complexityMap;
    }

    public static void main(String[] args) {
        Map<String, String> complexities = evaluateComplexity("MaxValueFinder", "findMax");
        System.out.println("Time Complexity: " + complexities.get("Time Complexity"));
        System.out.println("Space Complexity: " + complexities.get("Space Complexity"));
    }

    static class MaxValueFinder {
        public int findMax(int[] array) {
            int max = Integer.MIN_VALUE;
            for (int num : array) {
                if (num > max) {
                    max = num;
                }
            }
            return max;
        }
    }

}

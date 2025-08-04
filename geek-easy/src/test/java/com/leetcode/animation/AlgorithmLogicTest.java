package com.leetcode.animation;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * 算法逻辑测试类
 * 专门测试算法的核心逻辑，不依赖UI组件
 * 适用于无头环境和CI/CD流程
 * 
 * 设计文档：
 * 1. 功能需求：验证算法逻辑的正确性
 * 2. 测试范围：核心算法、边界条件、异常处理
 * 3. 测试策略：纯逻辑测试，无UI依赖
 * 4. 适用场景：CI/CD、无头环境、快速验证
 * 5. 性能要求：快速执行，低资源消耗
 */
public class AlgorithmLogicTest {
    
    /**
     * 测试两数之和算法
     */
    @Test
    public void testTwoSumAlgorithm() {
        // 基本情况
        int[] result1 = twoSum(new int[]{2, 7, 11, 15}, 9);
        assertArrayEquals("基本两数之和测试", new int[]{0, 1}, result1);
        
        // 负数情况
        int[] result2 = twoSum(new int[]{-1, -2, -3, -4, -5}, -8);
        assertArrayEquals("负数两数之和测试", new int[]{2, 4}, result2);
        
        // 重复元素
        int[] result3 = twoSum(new int[]{3, 3}, 6);
        assertArrayEquals("重复元素两数之和测试", new int[]{0, 1}, result3);
        
        // 无解情况
        int[] result4 = twoSum(new int[]{1, 2, 3}, 10);
        assertNull("无解情况应该返回null", result4);
    }
    
    /**
     * 测试二分查找算法
     */
    @Test
    public void testBinarySearchAlgorithm() {
        int[] nums = {1, 3, 5, 7, 9, 11, 13, 15};
        
        // 查找存在的元素
        assertEquals("查找首元素", 0, binarySearch(nums, 1));
        assertEquals("查找中间元素", 4, binarySearch(nums, 9));
        assertEquals("查找末尾元素", 7, binarySearch(nums, 15));
        
        // 查找不存在的元素
        assertEquals("查找不存在的小值", -1, binarySearch(nums, 0));
        assertEquals("查找不存在的中值", -1, binarySearch(nums, 6));
        assertEquals("查找不存在的大值", -1, binarySearch(nums, 20));
        
        // 边界情况
        assertEquals("单元素数组查找成功", 0, binarySearch(new int[]{5}, 5));
        assertEquals("单元素数组查找失败", -1, binarySearch(new int[]{5}, 3));
        assertEquals("空数组", -1, binarySearch(new int[]{}, 1));
    }
    
    /**
     * 测试哈希集合基本操作
     */
    @Test
    public void testHashSetOperations() {
        SimpleHashSet hashSet = new SimpleHashSet();
        
        // 基本添加和查找
        hashSet.add(1);
        assertTrue("添加后应该包含元素", hashSet.contains(1));
        assertFalse("不应该包含未添加的元素", hashSet.contains(2));
        
        // 重复添加
        hashSet.add(1);
        assertTrue("重复添加后仍应该包含元素", hashSet.contains(1));
        
        // 删除操作
        hashSet.remove(1);
        assertFalse("删除后不应该包含元素", hashSet.contains(1));
        
        // 删除不存在的元素
        hashSet.remove(999);
        assertFalse("删除不存在元素后状态不变", hashSet.contains(999));
        
        // 大量数据测试
        for (int i = 0; i < 1000; i++) {
            hashSet.add(i);
        }
        for (int i = 0; i < 1000; i++) {
            assertTrue("大量数据测试 - 应该包含 " + i, hashSet.contains(i));
        }
    }
    
    /**
     * 测试哈希映射基本操作
     */
    @Test
    public void testHashMapOperations() {
        SimpleHashMap hashMap = new SimpleHashMap();
        
        // 基本put和get
        hashMap.put(1, 100);
        assertEquals("基本put/get测试", Integer.valueOf(100), hashMap.get(1));
        
        // 更新值
        hashMap.put(1, 200);
        assertEquals("更新值测试", Integer.valueOf(200), hashMap.get(1));
        
        // 获取不存在的键
        assertNull("获取不存在的键应该返回null", hashMap.get(999));
        
        // 删除操作
        hashMap.remove(1);
        assertNull("删除后获取应该返回null", hashMap.get(1));
        
        // 多个键值对
        for (int i = 0; i < 100; i++) {
            hashMap.put(i, i * 10);
        }
        for (int i = 0; i < 100; i++) {
            assertEquals("多键值对测试 - 键 " + i, Integer.valueOf(i * 10), hashMap.get(i));
        }
    }
    
    /**
     * 测试K次取反后最大化数组和算法
     */
    @Test
    public void testLargestSumAfterKNegations() {
        // 基本情况
        assertEquals("基本K次取反测试", 5, largestSumAfterKNegations(new int[]{4, 2, 3}, 1));
        assertEquals("多次取反测试", 6, largestSumAfterKNegations(new int[]{3, -1, 0, 2}, 3));
        
        // 边界情况
        assertEquals("K=0情况", 9, largestSumAfterKNegations(new int[]{2, 3, 4}, 0));
        assertEquals("单元素数组", -5, largestSumAfterKNegations(new int[]{5}, 1));
        
        // 包含负数
        assertEquals("包含负数", 13, largestSumAfterKNegations(new int[]{2, -3, -1, 5, -4}, 2));
        
        // K大于数组长度
        assertEquals("K大于数组长度", 22, largestSumAfterKNegations(new int[]{-8, 3, -5, -3, -5, -2}, 6));
    }
    
    // ==================== 算法实现 ====================
    
    /**
     * 两数之和算法实现
     */
    private int[] twoSum(int[] nums, int target) {
        java.util.Map<Integer, Integer> map = new java.util.HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (map.containsKey(complement)) {
                return new int[]{map.get(complement), i};
            }
            map.put(nums[i], i);
        }
        return null;
    }
    
    /**
     * 二分查找算法实现
     */
    private int binarySearch(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }
    
    /**
     * K次取反后最大化数组和算法实现
     */
    private int largestSumAfterKNegations(int[] nums, int k) {
        java.util.Map<Integer, Integer> freq = new java.util.HashMap<>();
        for (int num : nums) {
            freq.put(num, freq.getOrDefault(num, 0) + 1);
        }

        int ans = java.util.Arrays.stream(nums).sum();
        
        // 优先处理负数，从最小的负数开始
        for (int i = -100; i < 0; i++) {
            if (freq.containsKey(i)) {
                int ops = Math.min(k, freq.get(i));
                ans += (-i) * ops * 2; // 每次取反增加 2 * |i| 的和
                freq.put(i, freq.get(i) - ops);
                freq.put(-i, freq.getOrDefault(-i, 0) + ops);
                k -= ops;
                if (k == 0) {
                    break;
                }
            }
        }

        // 如果还有剩余的k且为奇数，且没有0，则取反最小的正数
        if (k > 0 && k % 2 == 1 && !freq.containsKey(0)) {
            for (int i = 1; i <= 100; ++i) {
                if (freq.containsKey(i)) {
                    ans -= i * 2; // 取反最小正数会减少 2 * i 的和
                    break;
                }
            }
        }
        
        return ans;
    }
    
    // ==================== 简化的数据结构实现 ====================
    
    /**
     * 简化的哈希集合实现
     */
    private static class SimpleHashSet {
        private java.util.Set<Integer> set = new java.util.HashSet<>();
        
        public void add(int key) {
            set.add(key);
        }
        
        public void remove(int key) {
            set.remove(key);
        }
        
        public boolean contains(int key) {
            return set.contains(key);
        }
    }
    
    /**
     * 简化的哈希映射实现
     */
    private static class SimpleHashMap {
        private java.util.Map<Integer, Integer> map = new java.util.HashMap<>();
        
        public void put(int key, int value) {
            map.put(key, value);
        }
        
        public Integer get(int key) {
            return map.get(key);
        }
        
        public void remove(int key) {
            map.remove(key);
        }
    }
    
    /**
     * 测试第K大元素算法
     */
    @Test
    public void testKthLargestElement() {
        KthLargest kthLargest = new KthLargest(3, new int[]{4, 5, 8, 2});
        
        assertEquals("初始第3大元素", 4, kthLargest.add(3));
        assertEquals("添加5后第3大元素", 5, kthLargest.add(5));
        assertEquals("添加10后第3大元素", 5, kthLargest.add(10));
        assertEquals("添加9后第3大元素", 8, kthLargest.add(9));
        assertEquals("添加4后第3大元素", 8, kthLargest.add(4));
    }
    
    /**
     * 第K大元素数据结构实现
     */
    private static class KthLargest {
        private java.util.PriorityQueue<Integer> minHeap;
        private int k;
        
        public KthLargest(int k, int[] nums) {
            this.k = k;
            this.minHeap = new java.util.PriorityQueue<>();
            
            for (int num : nums) {
                add(num);
            }
        }
        
        public int add(int val) {
            minHeap.offer(val);
            if (minHeap.size() > k) {
                minHeap.poll();
            }
            return minHeap.peek();
        }
    }
    
    /**
     * 性能测试
     */
    @Test
    public void testPerformance() {
        long startTime = System.currentTimeMillis();
        
        // 测试大数据量的两数之和
        int[] largeArray = new int[10000];
        for (int i = 0; i < largeArray.length; i++) {
            largeArray[i] = i;
        }
        // 寻找和为19997的两个数（9998 + 9999 = 19997）
        int[] result = twoSum(largeArray, 19997);
        assertNotNull("大数组两数之和应该有结果", result);
        assertEquals("结果应该是最后两个索引", 9998, result[0]);
        assertEquals("结果应该是最后两个索引", 9999, result[1]);
        
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        
        assertTrue("性能测试：大数组处理应该在合理时间内完成", duration < 1000); // 1秒内
    }
    
    /**
     * 边界条件综合测试
     */
    @Test
    public void testBoundaryConditions() {
        // 空数组测试
        assertNull("空数组两数之和", twoSum(new int[]{}, 0));
        assertEquals("空数组二分查找", -1, binarySearch(new int[]{}, 1));
        
        // 单元素数组测试
        assertNull("单元素数组两数之和（无解）", twoSum(new int[]{1}, 2));
        assertEquals("单元素数组二分查找（成功）", 0, binarySearch(new int[]{5}, 5));
        assertEquals("单元素数组二分查找（失败）", -1, binarySearch(new int[]{5}, 3));
        
        // 极值测试
        int[] extremeArray = {Integer.MIN_VALUE, -1, 0, 1, Integer.MAX_VALUE};
        assertEquals("极值二分查找（最小值）", 0, binarySearch(extremeArray, Integer.MIN_VALUE));
        assertEquals("极值二分查找（最大值）", 4, binarySearch(extremeArray, Integer.MAX_VALUE));
        
        // K次取反边界测试
        assertEquals("K=0取反", 6, largestSumAfterKNegations(new int[]{1, 2, 3}, 0));
        assertEquals("K超大值取反", 4, largestSumAfterKNegations(new int[]{-2, 0, 2}, 1000));
    }
}
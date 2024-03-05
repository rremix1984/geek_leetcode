/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static java.util.Arrays.stream;
import static org.junit.Assert.assertArrayEquals;

/**
    [ARRAY]
    (简单)
    888. 公平的糖果交换
        爱丽丝和鲍勃拥有不同总数量的糖果。给你两个数组 aliceSizes 和 bobSizes ，
            1）aliceSizes[i] 是爱丽丝拥有的第 i 盒糖果中的糖果数量，
            2）bobSizes[j] 是鲍勃拥有的第 j 盒糖果中的糖果数量。
        两人想要互相交换一盒糖果，这样在交换之后，他们就可以拥有相同总数量的糖果。
        一个人拥有的糖果总数量是他们每盒糖果数量的总和。
        返回一个整数数组 answer，其中 answer[0] 是爱丽丝必须交换的糖果盒中的糖果的数目，
        answer[1] 是鲍勃必须交换的糖果盒中的糖果的数目。
        如果存在多个答案，你可以返回其中 任何一个 。
        题目测试用例保证存在与输入对应的答案。
    示例 1：
        输入：aliceSizes = {1, 1},  bobSizes = {2, 2}
        输出：{1, 2}
    示例 2：
        输入：aliceSizes = {1, 2},  bobSizes = {2, 3}
        输出：{1, 2}
    示例 3：
        输入：aliceSizes = {2},  bobSizes = {1, 3}
        输出：{2, 3}
    示例 4：
        输入：aliceSizes = {1, 2, 5},  bobSizes = {2, 4}
        输出：{5, 4}
*/
public class NO888_E_FairCandySwap_x2 {

    @Test
    public void test() {
        assertArrayEquals(new int[]{1, 2},
                fairCandySwap(new int[]{1, 1}, new int[]{2, 2}));
        assertArrayEquals(new int[]{1, 2},
                fairCandySwap(new int[]{1, 2}, new int[]{2, 3}));
        assertArrayEquals(new int[]{2, 3},
                fairCandySwap(new int[]{2}, new int[]{1, 3}));
        assertArrayEquals(new int[]{5, 4},
                fairCandySwap(new int[]{1, 2, 5}, new int[]{2, 4}));
    }

    public int[] fairCandySwap(int[] aliceSizes, int[] bobSizes) {
        int[] ans = new int[2];
        return ans;
    }

}




















/**
// 方法1：
public int[] fairCandySwap(int[] aliceSizes, int[] bobSizes) {
    int aliceTotal = 0;
    int bobTotal = 0;
    for (int aliceSize : aliceSizes)
        aliceTotal += aliceSize;

    Set<Integer> bobSet = new HashSet<>(bobSizes.length);
    for (int bobSize : bobSizes) {
        bobTotal += bobSize;
        bobSet.add(bobSize);
    }

    if (aliceTotal == bobTotal)
        return new int[]{};

    int pause = (aliceTotal + bobTotal) / 2 - aliceTotal;

    for (int aliceSize : aliceSizes)
        if (bobSet.contains(aliceSize + pause))
            return new int[]{aliceSize, aliceSize + pause};

    return new int[]{};
}

// 方法2：
public int[] fairCandySwap(int[] aliceSizes, int[] bobSizes) {
    int[] ans = new int[2];
    // alice 对齐 bob 需要补的差值
    int delta = (stream(aliceSizes).sum()
            - stream(bobSizes).sum()) / 2;

    // 相同的元素去重，因为只能换一次
    Set<Integer> set = new HashSet<>();
    for (int num : aliceSizes)
        set.add(num);

    // 只能换一次，所以去找 bob + delta = alice 的那一对儿值
    for (int bob : bobSizes) {
        int alice = bob + delta;
        if (set.contains(alice))
            return new int[]{alice, bob};
    }
    return ans;
}
*/
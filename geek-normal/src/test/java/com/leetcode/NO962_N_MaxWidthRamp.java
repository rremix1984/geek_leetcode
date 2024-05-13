package com.leetcode;

import org.junit.Test;
import java.util.Comparator;

import static com.leetcode.util.MathUtils.MAX;
import static com.leetcode.util.SystemUtil.printArr;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.util.Arrays.sort;

/**
    [ARRAY] |||
    (中等)
    NO.962 最大宽度坡
    给定一个整数数组 A，坡是元组 (i, j)，其中 i < j 且 A[i] <= A[j]。
    这样的坡的宽度为 j - i。
    找出 A 中的坡的最大宽度，如果不存在，返回 0 。
    示例 1：
        输入：[6, 0, 8, 2, 1, 5]
        输出：4
        解释：
        最大宽度的坡为 (i, j) = (1, 5): A[1] = 0 且 A[5] = 5.
    示例 2：

        输入：[9, 8, 1, 0, 1, 9, 4, 0, 4, 1]
        输出：7
        解释：
        最大宽度的坡为 (i, j) = (2, 9): A[2] = 1 且 A[9] = 1.
    提示：
        2 <= A.length <= 50000
        0 <= A[i] <= 50000
    Related Topics:栈,数组,单调栈
*/
@SuppressWarnings("all")
public class NO962_N_MaxWidthRamp {

    @Test
    public void test() {
        assert 4 == maxWidthRamp(new int[]{6, 0, 8, 2, 1, 5});
        assert 7 == maxWidthRamp(new int[]{9, 8, 1, 0, 1, 9, 4, 0, 4, 1});
    }

    public int maxWidthRamp(int[] nums) {
        // 2024/3/19 NO.2 挺难想的，需要琢磨
        // 2024/3/22 NO.3 没思路，琢磨不出来
        int len = 0;

        return len;
    }

}














/*
//初始化和排序：
//        首先，你需要对书架上的书进行观察，记录下每本书的位置。这相当于初始化 dict 数组。
//    然后，你按照书的出版年份对这些位置进行排序。这就是排序 dict 数组的过程。排序后的数组帮助你知道哪些书是较新的，哪些是较旧的。
//    寻找最大间隔：
//
//        开始的时候，你设想自己的手中拿着一本很旧的书，这就是 min 变量初始化为一个很大值的原因。
//    你从最旧的书开始，一本一本往后看（即遍历排序后的 dict 数组）。每当你看到一本书，你会比较它与你手中的那本书的新旧，并尝试放下更旧的那一本，捡起更新的一本。同时，你会计算如果用这本书作为结束点，能够得到的最大间隔是多少。
//    每次当你发现放下旧书换上新书可以得到更大的间隔时，你就更新你的记录（更新 ans）。
//    最终结果：
//
//        遍历结束后，你记录下的最大间隔就是两本符合条件的书之间的最大距离。
public int maxWidthRamp(int[] nums) {
    // 2024/3/19 NO.2
    // 兼容结果为0的场景
    int ans = 0;

    if (nums == null || nums.length == 0)
        return 0;

    int n = nums.length;
    Integer[] dict = new Integer[n];

    for (int i = 0; i < n; i++)
        dict[i] = i;

    // 这个方法需要看懂
    sort(dict, Comparator.comparing(i -> nums[i]));

    int min = n;
    for (int i : dict) {
        ans = max(ans, i - min);
        min = min(min, i);
    }

    return ans;
}
*/

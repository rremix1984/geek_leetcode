package com.leetcode.normal;

import org.junit.Test;
import java.util.Arrays;

import static com.leetcode.util.SystemUtil.printArr;
import static java.util.Arrays.copyOf;
import static java.util.Arrays.sort;
import static org.junit.Assert.assertArrayEquals;

/**
    [ARRAY] |||
    (中等)
    NO.324 摆动排序 II
        给你一个整数数组nums，将它重新排列成nums[0] < nums[1] > nums[2] <
    nums[3]...的顺序。你可以假设所有输入数组都可以得到满足题目要求的结果。
    示例 1：
        输入：nums = [1, 5, 1, 1, 6, 4]
        输出：[1, 6, 1, 5, 1, 4]
        解释：[1, 4, 1, 5, 1, 6] 同样是符合题目要求的结果，可以被判题程序接受。
    示例 2：
        输入：nums = [1, 3, 2, 2, 3, 1]
        输出：[2, 3, 1, 3, 1, 2]
        提示：
            1 <= nums.length <= 5 * 10 ^ 4
            0 <= nums[i] <= 5000
            题目数据保证，对于给定的输入 nums ，总能产生满足题目要求的结果
        进阶：你能用 O(n) 时间复杂度和 / 或原地 O(1) 额外空间来实现吗？
    Related Topics:数组,分治,快速选择,排序
    题解：
        不考虑进阶要求的时间O(N)，空间O(1)，用基础的排序法也是能做本题的。先把
    数组复制，从大到小排序。然后按照先奇数角标，后偶数角标的顺序，把从大到小的数据
    回填，即可完成题目要求。可以看到，按照上述方法调整顺序后，必然满足以下条件：
      1）偶数角标是不增序列
      2）奇数位置均大于等于偶数位置
    由于题目默认答案可构造，因此上述方法调整后一定满足题意。时间O(N*logN)：排序
    的时间复杂度，空间O(N)：数组复制占用空间
*/
public class NO324_N_WiggleSort {

    @Test
    public void test() {
        int[] nums = {1, 5, 1, 1, 6, 4};
        wiggleSort(nums);
        printArr(nums);
        assertArrayEquals(new int[]{1, 6, 1, 5, 1, 4}, nums);

        int[] nums2 = {1, 3, 2, 2, 3, 1};
        wiggleSort(nums2);
        assertArrayEquals(new int[]{2, 3, 1, 3, 1, 2}, nums2);
    }

    public void wiggleSort(int[] nums) {
        // 2024/3/15 NO.1
        // 2024/3/19 NO.2 没做出来，有一点点思路
        // 2024/3/25 NO.3 没做出来，思路对
    }

}
















/*
// 方法1：
public void wiggleSort(int[] nums) {
    int[] sortArr = Arrays.copyOf(nums, nums.length);
    Arrays.sort(sortArr);
    int cur = nums.length - 1;
    for (int i = 1; i < nums.length; i += 2) {
        nums[i] = sortArr[cur];
        cur--;
    }

    for (int i = 0; i < nums.length; i += 2) {
        nums[i] = sortArr[cur];
        cur--;
    }
}
*/
/**
 * copyright 2022/1/19
 */
package com.leetcode.normal;

import org.junit.Test;
import static com.leetcode.util.LogUtil.info;
import static java.lang.Math.max;

/**
    (中等)
    198. 打家劫舍
        你是一个专业的小偷，计划偷窃沿街的房屋。每间房内都藏有一定的现金，
        影响你偷窃的唯一制约因素就是相邻的房屋装有相互连通的防盗系统，如
        果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警。
        给定一个代表每个房屋存放金额的非负整数数组，计算你不触动警报装置
        的情况下 ，一夜之内能够偷窃到的最高金额。
    示例 1：
        输入：[1, 2, 3, 1]
        输出：4
        解释：偷窃 1 号房屋 (金额 = 1) ，然后偷窃 3 号房屋 (金额 = 3)。
        偷窃到的最高金额 = 1 + 3 = 4 。
    示例 2：
        输入：[2, 7, 9, 3, 1]
        输出：12
        解释：偷窃 1 号房屋 (金额 = 2), 偷窃 3 号房屋 (金额 = 9)，接着偷窃 5 号房屋 (金额 = 1)。
        偷窃到的最高金额 = 2 + 9 + 1 = 12 。
*/
@SuppressWarnings("all")
public class NO198_N_HouseRobber_x2 {

    @Test
    public void test() {
        assert 4 == rob(new int[]{1, 2, 3, 1});// 4
        assert 12 == rob(new int[]{2, 7, 9, 3, 1});// 12
    }

    public int rob(int[] nums) {
        return -1;
    }

}











/**
// 方法1：
public int rob(int[] nums) {
    // 前者最大值
    int pre = 0;

    // 当前最大值
    int cur = 0;

    for (int num : nums) {
        int tmp = cur;
        cur = Math.max(pre + num, cur);
        pre = tmp;
    }

    return cur;
}

// 方法2：
public int rob(int[] nums) {
    if (nums == null || nums.length == 0)
        return 0;

    int n = nums.length;
    // 第一位代表第几个元素，第二位代表 0-不偷，1-偷
    int[][] a = new int[n][2];

    // 第0个元素为0（不偷），值为0
    a[0][0] = 0;
    // 第0个元素为1（偷），值为nums[0]自身值
    a[0][1] = nums[0];

    // 根据一般式，迭代每一个元素
    for (int i = 1; i < n; i++) {
        a[i][0] = Math.max(a[i-1][0], a[i-1][1]);
        a[i][1] = a[i-1][0] + nums[i];
    }

    // 最后返回的最大值就是
    // 最后一个元素a[n-1]的【偷】与【不偷】的最大值
    return Math.max(a[n-1][0], a[n-1][1]);
}
*/
/**
 * copyright
 */
package com.lcr;

import org.junit.Test;
import java.util.Arrays;
import static java.lang.Math.max;
import static java.util.Arrays.copyOfRange;

/**
    [ARRAY]
    (中等)
    LCR.090 打家劫舍 II
    一个专业的小偷，计划偷窃一个环形街道上沿街的房屋，每间房内都藏有一定的现金。
    这个地方所有的房屋都 围成一圈 ，这意味着第一个房屋和最后一个房屋是紧挨着的。
    同时，相邻的房屋装有相互连通的防盗系统，如果两间相邻的房屋在同一晚上被小偷闯入，
    系统会自动报警。
    给定一个代表每个房屋存放金额的非负整数数组nums，请计算在不触动警报装置的情况下，
    今晚能够偷窃到的最高金额。
    示例 1：
        输入：nums = [2, 3, 2]
        输出：3
        解释：你不能先偷窃 1 号房屋（金额 = 2），然后偷窃 3 号房屋（金额 = 2）, 因为他们是相邻的。
    示例 2：
        输入：nums = [1, 2, 3, 1]
        输出：4
        解释：你可以先偷窃 1 号房屋（金额 = 1），然后偷窃 3 号房屋（金额 = 3）。
        偷窃到的最高金额 = 1 + 3 = 4 。
    示例 3：
        输入：nums = [0]
        输出：0
    提示：
        1 <= nums.length <= 100
        0 <= nums[i] <= 1000
    Related Topics:数组,动态规划
*/
public class LCR_090_N_Rob {

    @Test
    public void test(){
        assert 3 == rob(new int[]{2, 3, 2});
        assert 4 == rob(new int[]{1, 2, 3, 1});
        assert 0 == rob(new int[]{0});
    }

    public int rob(int[] nums) {
        if (nums.length == 0)
            return 0;

        if (nums.length == 1)
            return nums[0];

        return max(
                myRob(copyOfRange(nums, 0, nums.length - 1)),
                myRob(copyOfRange(nums, 1, nums.length))
        );
    }

    private int myRob(int[] nums) {
        int pre = 0;
        int cur = 0;
        int tmp;
        for (int num : nums) {
            tmp = cur;
            cur = max(pre + num, cur);
            pre = tmp;
        }
        return cur;
    }

}
























/*
// 方法1：
public int rob(int[] nums) {
    if (nums.length == 0)
        return 0;

    if (nums.length == 1)
        return nums[0];

    return max(
            myRob(copyOfRange(nums, 0, nums.length - 1)),
            myRob(copyOfRange(nums, 1, nums.length))
    );
}

private int myRob(int[] nums) {
    int pre = 0;
    int cur = 0;
    int tmp;
    for (int num : nums) {
        tmp = cur;
        cur = max(pre + num, cur);
        pre = tmp;
    }
    return cur;
}
*/
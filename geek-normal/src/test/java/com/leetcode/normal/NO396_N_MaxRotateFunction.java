/**
 * copyright @ 2019/12/23 lcy
 */
package com.leetcode.normal;

import org.junit.Test;
import java.util.Arrays;

import static com.leetcode.util.MathUtils.frontSum;
import static java.lang.Math.max;
import static java.util.Arrays.stream;

/**
    [ARRAY] |||||
    (中等)
    NO.396 旋转函数
    给定一个长度为n的整数数组nums。
    假设 arrk 是数组 nums 顺时针旋转 k 个位置后的数组，
    我们定义 nums 的旋转函数F为：
     F(k) = 0 * arrk[0] + 1 * arrk[1] + ... + (n - 1) * arrk[n - 1]
    返回 F(0), F(1), ..., F(n-1) 中的最大值。
    生成的测试用例让答案符合 32 位 整数。
    示例 1:
        输入: nums = [4, 3, 2, 6]
        输出: 26
        解释:
        F(0) = (0 * 4) + (1 * 3) + (2 * 2) + (3 * 6) = 0 + 3 + 4 + 18 = 25
        F(1) = (0 * 6) + (1 * 4) + (2 * 3) + (3 * 2) = 0 + 4 + 6 + 6 = 16
        F(2) = (0 * 2) + (1 * 6) + (2 * 4) + (3 * 3) = 0 + 6 + 8 + 9 = 23
        F(3) = (0 * 3) + (1 * 2) + (2 * 6) + (3 * 4) = 0 + 2 + 12 + 12 = 26
        所以 F(0), F(1), F(2), F(3) 中的最大值是 F(3) = 26 。
    示例 2:
        输入: nums = [100]
        输出: 0
    提示:
        n == nums.length
        1 <= n <= 10 ^ 5
        -100 <= nums[i] <= 100
    Related Topics:数组,数学,动态规划
*/
public class NO396_N_MaxRotateFunction {

    @Test
    public void test() {
        assert 26 == maxRotateFunction(new int[]{4, 3, 2, 6});
        assert 0 == maxRotateFunction(new int[]{100});
        assert 330 == maxRotateFunction(new int[]{1,2,3,4,5,6,7,8,9,10});
    }

    public int maxRotateFunction(int[] nums) {
        // 2024/3/12 NO.1
        // 2024/3/18 NO.2
        // 2024/3/21 NO.3 没思路
        // 2024/3/25 NO.4 思路对，但是没做出来
        // 2024/3/30 NO.5 思路差点，没做出来
        int ans = 0;
        return ans;
    }

}















/*
// 方法1：不对
public int maxRotateFunction(int[] nums) {
    int front = 0;
    int n = nums.length;
    int sum = 0;
    for (int i = 0; i < n; i++) {
        sum += nums[i];
        front += i * nums[i];
    }

    int ans = front;
    for (int i = n - 1; i > 0; i--) {
        front = front - n * nums[i] + sum;
        ans = max(ans, front);
    }
    return ans;
}

// 方法2：推荐
public int maxRotateFunction(int[] nums) {
    int ans = 0;
    int sum = 0, curSum = 0;
    for (int i = 0; i < nums.length; ++i) {
        sum += nums[i];
        curSum += i * nums[i];
    }

    ans = curSum;
    for (int i = nums.length - 1; i > 0; --i) {
        curSum += sum - nums.length * nums[i];
        ans = Math.max(ans, curSum);
    }
    return ans;
}
*/
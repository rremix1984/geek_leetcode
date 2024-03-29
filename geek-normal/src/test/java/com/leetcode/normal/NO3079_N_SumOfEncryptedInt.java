package com.leetcode.normal;

import org.junit.Test;
import static java.lang.Math.max;

/**
    [ARRAY]
    (中等)
    NO.3079 求出加密整数的和
    给你一个整数数组 nums ，数组中的元素都是 正 整数。定义一个加密函数 encrypt ，
    encrypt(x) 将一个整数 x 中 每一个 数位都用 x 中的 最大 数位替换。
    比方说 encrypt(523) = 555 且 encrypt(213) = 333 。
    请你返回数组中所有元素加密后的 和 。
    示例 1：
        输入：nums = [1, 2, 3]
        输出：6
        解释：加密后的元素位 [1, 2, 3] 。加密元素的和为 1 + 2 + 3 == 6 。
    示例 2：
        输入：nums = [10, 21, 31]
        输出：66
        解释：加密后的元素为 [11,22,33] 。加密元素的和为 11 + 22 + 33 == 66 。
    提示：
        1 <= nums.length <= 50
        1 <= nums[i] <= 1000
    Related Topics:数组,数学
*/
public class NO3079_N_SumOfEncryptedInt {

    @Test
    public void test() {
        assert 6 == sumOfEncryptedInt(new int[]{1, 2, 3});
        assert 66 == sumOfEncryptedInt(new int[]{10, 21, 31});
    }

    public int sumOfEncryptedInt(int[] nums) {
        int sum = 0;
        for (int num : nums)
            sum += encrypt(num);

        return sum;
    }

    public int encrypt(int num) {
        int largest = 0;
        int temp = num;
        while (temp > 0) {
            largest = max(largest, temp % 10);
            temp /= 10;
        }

        int encrypted = 0;
        temp = num;
        while (temp > 0) {
            encrypted = encrypted * 10 + largest;
            temp /= 10;
        }
        return encrypted;
    }

}

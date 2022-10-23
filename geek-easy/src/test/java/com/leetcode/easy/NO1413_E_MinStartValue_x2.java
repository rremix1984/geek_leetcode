/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

/**
    (简单)
    1413. 逐步求和得到正数的最小值
        给你一个整数数组 nums 。你可以选定任意的 正数 startValue 作为初始值。
        你需要从左到右遍历 nums 数组，并将 startValue 依次累加上 nums 数组中的值。
        请你在确保累加和始终大于等于 1 的前提下，选出一个最小的 正数 作为 startValue 。
    示例 1：
        输入：nums = {-3, 2, -3, 4, 2}
        输出：5
        解释：如果你选择 startValue = 4，在第三次累加时，和小于 1 。
             累加求和
             startValue = 4 | startValue = 5 | nums
                (4 -3 ) = 1  | (5 -3 ) = 2    |  -3
                (1 +2 ) = 3  | (2 +2 ) = 4    |   2
                (3 -3 ) = 0  | (4 -3 ) = 1    |  -3
                (0 +4 ) = 4  | (1 +4 ) = 5    |   4
                (4 +2 ) = 6  | (5 +2 ) = 7    |   2
    示例 2：
        输入：nums = {1, 2}
        输出：1
        解释：最小的 startValue 需要是正数。
    示例 3：
        输入：nums = {1, -2, -3}
        输出：5
    思路1：
        要保证所有的累加和 sum 满足 sum + startValue ≥ 1，只需保证累加和的最小值 min 满足 min + startValue ≥ 1，
        那么 startValue 的最小值即可取 -min + 1。
    思路2：
        当 nums 所有元素均为非负数时，可以直接返回 1。当有负数时，可以
        当某个数字满足 startValue 的要求时，比它大的数字肯定也都满足，比它小的数字则不一定能满足，
        因此 startValue 的性质具有单调性，此题可以用二分查找来解决。二分查找的左起始点为 1，
        右起始点可以设为 nums 的最小值的相反数乘上长度后再加 1，这样可以保证右端点一定满足 startValue 的要求。
        判断某个数字是否满足 startValue 的要求时，可以将 nums 的数字逐步加到这个数字上，判断是否一直为正即可。
*/
public class NO1413_E_MinStartValue_x2 {

    @Test
    public void test() {
        assert 5 == minStartValue(new int[]{-3, 2, -3, 4, 2});
        assert 1 == minStartValue(new int[]{1, 2});
        assert 5 == minStartValue(new int[]{1, -2, -3});
    }

    public int minStartValue(int[] nums) {
        return -1;
    }

}















/**
// 方法1：
public int minStartValue(int[] nums) {
    int sum = 0;
    int min = 0;
    for (int num : nums) {
        sum += num;
        min = min(min, sum);
    }
    return -min + 1;
}


// 方法2
public int minStartValue(int[] nums) {
    int m = Arrays.stream(nums).min().getAsInt();
    if (m >= 0)
        return 1;

    int left = 1;
    int right = -m * nums.length + 1;
    while (left < right) {
        int medium = (left + right) / 2;
        if (valid(medium, nums))
            right = medium;
        else
            left = medium + 1;
    }
    return left;
}

public boolean valid(int startValue, int[] nums) {
    for (int num : nums) {
        startValue += num;
        if (startValue <= 0)
            return false;
    }
    return true;
}
*/
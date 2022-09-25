/**
 * copyright 2022/1/19
 */
package com.offer.easy;

import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;

/**
    (简单)
    剑指 Offer II 006. 排序数组中两个数字之和
        给定一个已按照 升序排列  的整数数组 numbers ，请你从数组中找出两个数满足相加之和等于目标数 target 。
        函数应该以长度为 2 的整数数组的形式返回这两个数的下标值。numbers 的下标 从 0 开始计数 ，所以答案数组应当满足 0 <= answer[0] < answer[1] < numbers.length 。
        假设数组中存在且只存在一对符合条件的数字，同时一个数字不能使用两次。
    示例 1：
        输入：numbers = {1, 2, 4, 6, 10},  target = 8
        输出：{1, 3}
        解释：2 与 6 之和等于目标数 8 。因此 index1 = 1,  index2 = 3 。
    示例 2：
        输入：numbers = {2, 3, 4},  target = 6
        输出：{0, 2}
    示例 3：
        输入：numbers = {-1, 0},  target = -1
        输出：{0, 1}
*/
public class OfferII_006_E_TwoSum_x2 {

    @Test
    public void test() {
        assertArrayEquals(new int[]{1, 3}, twoSum(new int[]{1, 2, 4, 6, 10}, 8));
        assertArrayEquals(new int[]{0, 2}, twoSum(new int[]{2, 3, 4}, 6));
        assertArrayEquals(new int[]{0, 1}, twoSum(new int[]{-1, 0}, -1));
    }

    public int[] twoSum(int[] numbers, int target) {
        return new int[]{-1, -1};
    }

}
















/**
// 方法1：二分查找
public int[] twoSum(int[] numbers, int target) {
    for (int i = 0; i < numbers.length; i++) {
        int l = i + 1;
        int r = numbers.length - 1;
        while (l <= r) {
            int mid = l + (r - l) / 2;
            if (numbers[mid] == target - numbers[i])
                return new int[]{i, mid};
            else if (numbers[mid] > target - numbers[i])
                r = mid - 1;
            else
                l = mid + 1;
        }
    }
    return new int[]{-1, -1};
}


// 方法2：双指针法
public int[] twoSum(int[] numbers, int target) {
    int l = 0;
    int r = numbers.length - 1;
    while (l < r) {
        int sum = numbers[l] + numbers[r];
        if (sum == target)
            return new int[]{l, r};
        else if (sum < target)
            l++;
        else
            r--;
    }
    return new int[]{-1, -1};
}
*/
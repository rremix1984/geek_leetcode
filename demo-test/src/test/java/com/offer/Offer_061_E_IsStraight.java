/**
 * copyright 2022/1/19
 */
package com.offer;

import org.junit.Test;

import java.util.Arrays;

import static java.lang.Math.max;
import static java.lang.Math.min;

/**
    (简单)
    剑指 Offer 61. 扑克牌中的顺子
        从若干副扑克牌中随机抽 5 张牌，判断是不是一个顺子，即这5张牌是不是连续的。
        2～10为数字本身，A为1，J为11，Q为12，K为13，而大、小王为 0 ，可以看成任意数字。
        A 不能视为 14。
    示例 1:
        输入: {1, 2, 3, 4, 5}
        输出: True
    示例 2:
        输入: {0, 0, 1, 2, 5}
        输出: True
*/
public class Offer_061_E_IsStraight {

    @Test
    public void test() {
        assert isStraight(new int[]{1, 2, 3, 4, 5});
        assert isStraight(new int[]{0, 0, 1, 2, 5});
    }

    public boolean isStraight(int[] nums) {
        int joker = 0;
        Arrays.sort(nums); // 数组排序
        for (int i = 0; i < 4; i++)
            if (nums[i] == 0)
                joker++; // 统计大小王数量
            else if (nums[i] == nums[i + 1])
                return false; // 若有重复，提前返回 false

        return nums[4] - nums[joker] < 5; // 最大牌 - 最小牌 < 5 则可构成顺子
    }

}
















/**
// 方法1：
public boolean isStraight(int[] nums) {
    Set<Integer> set = new HashSet<>();
    int max = 0;
    int min = 14;
    for (int num : nums) {
        if (num == 0)
            continue; // 跳过大小王

        max = max(max, num); // 最大牌
        min = min(min, num); // 最小牌

        if (set.contains(num))
            return false; // 若有重复，提前返回 false

        set.add(num); // 添加此牌至 Set
    }
    return max - min < 5; // 最大牌 - 最小牌 < 5 则可构成顺子
}


// 方法2：
public boolean isStraight(int[] nums) {
    int joker = 0;
    Arrays.sort(nums); // 数组排序
    for (int i = 0; i < 4; i++)
        if (nums[i] == 0)
            joker++; // 统计大小王数量
        else if (nums[i] == nums[i + 1])
            return false; // 若有重复，提前返回 false
    return nums[4] - nums[joker] < 5; // 最大牌 - 最小牌 < 5 则可构成顺子
}
*/
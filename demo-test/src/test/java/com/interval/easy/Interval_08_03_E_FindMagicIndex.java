package com.interval.easy;

import org.junit.Test;

/**
    [ARRAY] |
    (简单)
    (过于简单了)
    Interval 08.03 魔术索引
    魔术索引。 在数组A[0...n-1]中，有所谓的魔术索引，满足条件A[i] = i。
    给定一个有序整数数组，编写一种方法找出魔术索引，若有的话，在数组A中找出
    一个魔术索引，如果没有，则返回-1。若有多个魔术索引，返回索引值最小的一个。
    示例1:
        输入：nums = [0, 2, 3, 4, 5]
        输出：0
        说明: 0下标的元素为0
    示例2:
        输入：nums = [1, 1, 1]
        输出：1
        说明:
            nums长度在[1, 1000000]之间
            此题为原书中的 Follow-up，即数组中可能包含重复元素的版本
    Related Topics:数组,二分查找
*/
public class Interval_08_03_E_FindMagicIndex {

    @Test
    public void test() {
        assert 0 == findMagicIndex(new int[]{0, 2, 3, 4, 5});
        assert 1 == findMagicIndex(new int[]{1, 1, 1});
    }

    public int findMagicIndex(int[] nums) {
        // 2024/3/11 NO.1
        return -1;
    }

}















/*
// 方法1：
public int findMagicIndex(int[] nums) {
    for (int i = 0; i < nums.length; i++)
        if (nums[i] == i)
            return i;

    return -1;
}
*/
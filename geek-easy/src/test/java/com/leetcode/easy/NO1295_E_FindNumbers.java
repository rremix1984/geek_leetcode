/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import static java.lang.Math.log10;

/**
    [ARRAY] |
    (简单)
    1295. 统计位数为偶数的数字
        给你一个整数数组nums，请你返回其中位数为【偶数】的数字的个数。
    示例 1：
        输入：nums = {12, 345, 2, 6, 7896}
        输出：2
        解释：12 是 2 位数字（位数为偶数）
             345 是 3 位数字（位数为奇数）
             2 是 1 位数字（位数为奇数）
             6 是 1 位数字 位数为奇数）
             7896 是 4 位数字（位数为偶数）
             因此只有 12 和 7896 是位数为偶数的数字
    示例 2：
        输入：nums = {555, 901, 482, 1771}
        输出：1
        解释：只有 1771 是位数为偶数的数字。
*/
public class NO1295_E_FindNumbers {

    @Test
    public void test() {
        assert 2 == findNumbers(new int[]{12, 345, 2, 6, 7896});
        assert 1 == findNumbers(new int[]{555, 901, 482, 1771});
    }

    public int findNumbers(int[] nums) {
        // 2024/3/6 NO.1 重点在于理解
        int evenNums = 0;
        return evenNums;
    }

}














/*
// 方法1：
public int findNumbers(int[] nums) {
    int evenNums = 0;
    for (int num : nums)
        // 偶数位
        if ((int)(log10(num)) % 2 != 0 )
            evenNums++;

    return evenNums;
}


// 方法2：
public int findNumbers(int[] nums) {
    int count = 0;
    for (int num : nums)
        if ((num >= 10 && num <= 99) ||
            (num >= 1000 && num <= 9999 || (num == 100000)))
            count++;

    return count;
}


// 方法3：
public int findNumbers(int[] nums) {
    int ans = 0;
    for (int val : nums)
        if ((call(val) & 1) == 0)
            ans++;

    return ans;
}

public int call(int val) {
    if (val < 10)
        return 1;

    int ans = 0;
    while (val > 0) {
        ans++;
        val /= 10;
    }
    return ans;
}
*/
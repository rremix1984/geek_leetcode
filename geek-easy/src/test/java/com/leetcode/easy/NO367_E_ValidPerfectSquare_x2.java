/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import static com.leetcode.util.LogUtil.info;

/**
    [NUMBER]
    （简单）
    367. 有效的完全平方数
        给定一个 正整数 num ，编写一个函数，
        如果 num 是一个完全平方数，则返回 true ，
        否则返回 false 。
        进阶：不要 使用任何内置的库函数，如  sqrt 。
    示例 1：
        输入：num = 16
        输出：true
    示例 2：
        输入：num = 14
        输出：false
*/
@SuppressWarnings("all")
public class NO367_E_ValidPerfectSquare_x2 {

    @Test
    public void test() {
        assert isPerfectSquare(144);// true
        assert !isPerfectSquare(120);// false
        assert !isPerfectSquare(2147483647);// false 容易超时
    }

    public boolean isPerfectSquare(int num) {
        return false;
    }

}












/*
// 方法1：二分查找法（正确答案，效率高）
public boolean isPerfectSquare(int num) {
    int left = 0, right = num;
    while (left <= right) {
        int mid = (right - left) / 2 + left;
        long square = (long) mid * mid;
        if (square < num)
            left = mid + 1;
        else if (square > num)
            right = mid - 1;
        else
            return true;
    }
    return false;
}

// 方法2：暴力破解法（错误答案，数字大一点就会超时）
public boolean isPerfectSquare(int num) {
    long x = 1, square = 1;
    while (square <= num)
        if (square == num)
            return true;
    square = x * x++;
    return false;
}
*/
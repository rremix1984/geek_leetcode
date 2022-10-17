/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

/**
    (简单)
    762. 二进制表示中质数个计算置位
        给你两个整数 left 和 right ，在闭区间 [left, right] 范围内，统计并返回计算置位位数为质数的整数个数。
        计算置位位数 就是二进制表示中 1 的个数。
        例如，21 的二进制表示 10101 有 3 个计算置位。
    示例 1：
        输入：left = 6, right = 10
        输出：4
        解释：6 -> 110 (2 个计算置位，2 是质数)
             7 -> 111 (3 个计算置位，3 是质数)
             9 -> 1001 (2 个计算置位，2 是质数)
             10-> 1010 (2 个计算置位，2 是质数)
             共计 4 个计算置位为质数的数字。
    示例 2：
        输入：left = 10, right = 15
        输出：5
        解释：10 -> 1010 (2 个计算置位, 2 是质数)
             11 -> 1011 (3 个计算置位, 3 是质数)
             12 -> 1100 (2 个计算置位, 2 是质数)
             13 -> 1101 (3 个计算置位, 3 是质数)
             14 -> 1110 (3 个计算置位, 3 是质数)
             15 -> 1111 (4 个计算置位, 4 不是质数)
             共计 5 个计算置位为质数的数字。
*/
public class NO762_E_CountPrimeSetBits_x2 {

    @Test
    public void test() {
        assert 4 == countPrimeSetBits(6, 10);
        assert 5 == countPrimeSetBits(10, 15);
    }

    public int countPrimeSetBits(int left, int right) {
        int ans = 0;
        return ans;
    }

}

















/**
public int countPrimeSetBits(int left, int right) {
    int ans = 0;
    for (int x = left; x <= right; x++)
        if (isPrime(Integer.bitCount(x)))
            ans++;
    return ans;
}

private boolean isPrime(int x) {
    if (x < 2)
        return false;

    for (int i = 2; i * i <= x; i++)
        if (x % i == 0)
            return false;
    return true;
}
*/

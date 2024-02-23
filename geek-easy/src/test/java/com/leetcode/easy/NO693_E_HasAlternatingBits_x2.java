/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

/**
    [NUMBER]
    (简单)
    693. 交替位二进制数
        给定一个正整数，检查它的二进制表示是否总是 0、1 交替出现：换
        句话说，就是二进制表示中相邻两位的数字永不相同。
    示例 1：
        输入：n = 5
        输出：true
        解释：5 的二进制表示是：101
    示例 2：
        输入：n = 7
        输出：false
        解释：7 的二进制表示是：111.
    示例 3：
        输入：n = 11
        输出：false
        解释：11 的二进制表示是：1011.
*/
public class NO693_E_HasAlternatingBits_x2 {

    @Test
    public void test() {
        assert hasAlternatingBits(5);
        assert !hasAlternatingBits(7);
        assert !hasAlternatingBits(11);
    }

    public boolean hasAlternatingBits(int n) {
        return false;
    }

}















/**
// 方法1：
public boolean hasAlternatingBits(int n) {
    int prev = 2;
    while (n != 0) {
        int cur = n % 2;
        if (cur == prev)
            return false;
        prev = cur;
        n /= 2;
    }
    return true;
}

// 方法2：
public boolean hasAlternatingBits(int n) {
    int a = n ^ (n >> 1);
    return (a & (a + 1)) == 0;
}
*/
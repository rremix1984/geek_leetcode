/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

/**
    (简单)
    263. 丑数
        丑数 就是只包含质因数2、3、5的正整数。
        给你一个整数n，请你判断 n 是否为丑数 。
        如果是，返回true；否则，返回false。
        1 没有质因数，因此它的全部质因数是{2, 3, 5}的空集。
        习惯上将其视作第一个丑数。
    示例 1：
        输入：n = 6
        输出：true
        解释：6 = 2 × 3
    示例 2：
        输入：n = 1
        输出：true
        解释：1 没有质因数，因此它的全部质因数是 {2, 3, 5} 的空集。
             习惯上将其视作第一个丑数。
    示例 3：
        输入：n = 14
        输出：false
        解释：14 不是丑数，因为它包含了另外一个质因数 7 。
    提示：
        -2^31 <= n <= 2^31 - 1
*/
public class NO263_E_IsUgly_x2 {

    @Test
    public void test() {
        assert isUgly(6);
        assert isUgly(1);
        assert !isUgly(14);
    }

    public boolean isUgly(int n) {
        return false;
    }

}


















/**
// 方法1：
public boolean isUgly(int n) {
    if (n <= 0)
        return false;

    int[] factors = {2, 3, 5};
    for (int f : factors)
        while (n % f == 0)
            n /= f;

    return n == 1;
}
*/
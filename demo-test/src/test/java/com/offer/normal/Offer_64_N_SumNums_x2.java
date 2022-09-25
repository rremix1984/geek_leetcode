/**
 * copyright 2022/1/19
 */
package com.offer.normal;

import org.junit.Test;

/**
    (中等)
    剑指 Offer 64. 求1+2+…+n
        求 1+2+...+n ，要求不能使用乘除法、for、while、if、else、switch、case等关键字及条件判断语句（A?B:C）。
    示例 1：
        输入: n = 3
        输出: 6
    示例 2：
        输入: n = 9
        输出: 45
*/
public class Offer_64_N_SumNums_x2 {

    @Test
    public void test() {
        assert 1 == sumNums(1);
        assert 3 == sumNums(2);
        assert 6 == sumNums(3);
        assert 45 == sumNums(9);
    }

    public int sumNums(int n) {
        return n;
    }

}

















/**
// 方法1：递归法
public int sumNums(int n) {
    if (n > 1)
        n += sumNums(n - 1);
    return n;
}
*/
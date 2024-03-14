package com.leetcode.normal;

import org.junit.Test;
import static java.lang.Integer.MAX_VALUE;
import static java.lang.Integer.MIN_VALUE;

/**
    [ARRAY] |
    （中等）
    NO.7 整数反转
    给你一个 32 位的有符号整数 x ，返回将 x 中的数字部分反转后的结果。
    如果反转后整数超过 32 位的有符号整数的范围 [−231,  231 − 1] ，就返回 0。
    假设环境不允许存储 64 位整数（有符号或无符号）。
    示例 1：
        输入：x = 123
        输出：321
    示例 2：
        输入：x = -123
        输出：-321
    示例 3：
        输入：x = 120
        输出：21
    示例 4：
        输入：x = 0
        输出：0
    提示：
        -231 <= x <= 231 - 1
    Related Topics:数学
*/
public class NO007_E_Reverse {

    @Test
    public void test() {
        assert 321  == reverse(123);
        assert -321 == reverse(-123);
        assert 21   == reverse(120);
        assert 0    == reverse(0);
        assert 0    == reverse(1534236469);
        assert 0    == reverse(MAX_VALUE);
        assert 0    == reverse(MIN_VALUE);
    }

    public int reverse(int x) {
        // 2024/3/13 NO.1
        int ans = 0;
        return ans;
    }

}















/*
// 方法1：
public int reverse(int x) {
    int ans = 0;
    while (x != 0) {
        int head = x % 10;
        ans = ans * 10 + head;

        if (ans > MAX_VALUE / 10)
            return 0;

        if (ans < MIN_VALUE / 10)
            return 0;

        x /= 10;
    }
    return ans;
}
*/
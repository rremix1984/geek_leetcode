/**
 * copyright 2022/1/19
 */
package com.offer;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.Integer.MIN_VALUE;

/**
    (简单)
    剑指 Offer II 001. 整数除法
        给定两个整数 a 和 b ，求它们的除法的商 a/b ，要求不得使用乘号 '*'、除号 '/' 以及求余符号 '%' 。
        注意：
        整数除法的结果应当截去（truncate）其小数部分，例如：truncate(8.345) = 8 以及 truncate(-2.7335) = -2
        假设我们的环境只能存储 32 位有符号整数，其数值范围是 [−231, 231−1]。本题中，如果除法结果溢出，则返回 231 − 1
    示例 1：
        输入：a = 15, b = 2
        输出：7
        解释：15/2 = truncate(7.5) = 7
    示例 2：
        输入：a = 7, b = -3
        输出：-2
        解释：7/-3 = truncate(-2.33333..) = -2
    示例 3：
        输入：a = 0, b = 1
        输出：0
    示例 4：
        输入：a = 1, b = 1
        输出：1
*/
public class OfferII_001_E_Divide {

    @Test
    public void test() {
        assert 7 == divide2(15, 2);
        assert -2 == divide2(7, -3);
        assert 0 == divide2(0, 1);
        assert 1 == divide2(1, 1);
    }

    public int divide2(int a, int b) {
        // 考虑被除数为最小值的情况
        if (a == MIN_VALUE) {
            if (b == 1)
                return MIN_VALUE;

            if (b == -1)
                return MAX_VALUE;
        }

        // 考虑除数为最小值的情况
        if (b == MIN_VALUE)
            return a == MIN_VALUE ? 1 : 0;

        // 考虑被除数为 0 的情况
        if (a == 0)
            return 0;

        // 一般情况，使用类二分查找
        // 将所有的正数取相反数，这样就只需要考虑一种情况
        boolean rev = false;
        if (a > 0) {
            a = -a;
            rev = !rev;
        }

        if (b > 0) {
            b = -b;
            rev = !rev;
        }

        List<Integer> cand = new ArrayList<>();
        cand.add(b);
        int index = 0;
        // 注意溢出
        while (cand.get(index) >= a - cand.get(index)) {
            cand.add(cand.get(index) + cand.get(index));
            ++index;
        }
        int ans = 0;
        for (int i = cand.size() - 1; i >= 0; i--)
            if (cand.get(i) >= a) {
                ans += 1 << i;
                a -= cand.get(i);
            }
        return rev ? -ans : ans;
    }

}


















/**
// 因为将 -2147483648 转成正数会越界，但是将 2147483647 转成负数，则不会
// 所以，我们将 a 和 b 都转成负数
// 时间复杂度：O(n)，n 是最大值 2147483647 --> 10^10 --> 超时
public int divide2(int a, int b) {
    // 32 位最大值：2^31 - 1 = 2147483647
    // 32 位最小值：-2^31 = -2147483648
    // -2147483648 / (-1) = 2147483648 > 2147483647 越界了
    if (a == MIN_VALUE && b == -1)
        return MAX_VALUE;

    int sign = (a > 0) ^ (b > 0) ? -1 : 1;

    // 环境只支持存储 32 位整数
    if (a > 0)
        a = -a;

    if (b > 0)
        b = -b;

    int res = 0;
    while (a <= b) {
        a -= b;
        res++;
    }

    // bug 修复：因为不能使用乘号，所以将乘号换成三目运算符
    return sign == 1 ? res : -res;
}

// 方法2：
public int divide2(int a, int b) {
    // 考虑被除数为最小值的情况
    if (a == MIN_VALUE) {
        if (b == 1)
            return MIN_VALUE;

        if (b == -1)
            return MAX_VALUE;

    }
    // 考虑除数为最小值的情况
    if (b == MIN_VALUE)
        return a == MIN_VALUE ? 1 : 0;

    // 考虑被除数为 0 的情况
    if (a == 0)
        return 0;

    // 一般情况，使用类二分查找
    // 将所有的正数取相反数，这样就只需要考虑一种情况
    boolean rev = false;
    if (a > 0) {
        a = -a;
        rev = !rev;
    }

    if (b > 0) {
        b = -b;
        rev = !rev;
    }

    List<Integer> candidates = new ArrayList<>();
    candidates.add(b);
    int index = 0;

    // 注意溢出
    while (candidates.get(index) >= a - candidates.get(index)) {
        candidates.add(candidates.get(index) + candidates.get(index));
        index++;
    }
    int ans = 0;
    for (int i = candidates.size() - 1; i >= 0; i--)
        if (candidates.get(i) >= a) {
            ans += 1 << i;
            a -= candidates.get(i);
        }
    return rev ? -ans : ans;
}
*/
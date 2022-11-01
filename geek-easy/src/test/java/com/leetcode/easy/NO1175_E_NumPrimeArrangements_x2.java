/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

import static com.leetcode.util.LogUtil.info;
import static com.leetcode.util.MathUtils.*;

/**
    (简单)
    1175. 质数排列
        请你帮忙给从 1 到 n 的数设计排列方案，使得所有的「质数」都应该被放在「质数索引」
        （索引从 1 开始）上；你需要返回可能的方案总数。
        让我们一起来回顾一下「质数」：质数一定是大于 1 的，并且不能用两个小于它的正整数
        的乘积来表示。
        由于答案可能会很大，所以请你返回答案 模 mod 10 ^ 9 + 7 之后的结果即可。
    示例 1：
        输入：n = 5
        输出：12
        解释：举个例子，[1,2,5,4,3] 是一个有效的排列，但 [5,2,3,4,1] 不是，
             因为在第二种情况里质数 5 被错误地放在索引为 1 的位置上。
    示例 2：
        输入：n = 100
        输出：682289015
    思路:
        求符合条件的方案数，使得所有质数都放在质数索引上，
        所有合数放在合数索引上，质数放置和合数放置是相互独立的，
        总的方案数即为「所有质数都放在质数索引上的方案数」×「所有合数都放在合数索引上的方案数」。
        求「所有质数都放在质数索引上的方案数」，即求质数个数 numPrimes 的阶乘。
       「所有合数都放在合数索引上的方案数」同理。求质数个数时，可以使用试除法。
*/
public class NO1175_E_NumPrimeArrangements_x2 {

    @Test
    public void test() {
        assert 12 == numPrimeArrangements(5);
        assert 682289015 == numPrimeArrangements(100);
    }

    public int numPrimeArrangements(int n) {
        int num = 0;
        for (int i = 1; i <= n; i++)
            if (isPrime(i))
                num++;

        return (int) (factorial(num)
                    * factorial(n - num) % MOD);
    }

}





















/**
// 方法1：
public int numPrimeArrangements(int n) {
    int num = 0;
    for (int i = 1; i <= n; i++)
        // 如果是质数
        if (isPrime(i))
            num++;

    // 阶乘（factorial）
    // 所有质数都在质数索引上的方案(num)!
    // 所有合数都在合数索引上的方案(n - num)!
    return (int) (factorial(num)
            * factorial(n - num) % MOD);
}
*/
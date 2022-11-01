/**
 * copyright 2022/1/19
 */
package com.leetcode;

import org.junit.Test;

/**
    (简单)
    779. 第K个语法符号
        我们构建了一个包含 n 行( 索引从 1  开始 )的表。首先在第一行我们写上一个 0。
        接下来的每一行，将前一行中的0替换为01，1替换为10。
        例如，对于 n = 3 ，第 1 行是 0 ，第 2 行是 01 ，第3行是 0110 。
        给定行数 n 和序数 k，返回第 n 行中第 k 个字符。（ k 从索引 1 开始）
    示例 1:
        输入: n = 1, k = 1
        输出: 0
        解释: 第一行：0
    示例 2:
        输入: n = 2, k = 1
        输出: 0
        解释:
        第一行: 0
        第二行: 01
    示例 3:
        输入: n = 2, k = 2
        输出: 1
        解释:
        第一行: 0
        第二行: 01
    提示:
        1 <= n <= 30
        1 <= k <= 2n - 1

    方法1：找规律 + 递归
    思路与算法
    按照方法一，我们可以尝试写表中的前几行：
         1）0
         2）01
         3）0110
         4）01101001
         5）⋯
        我们可以注意到规律：每一行的后半部分正好为前半部分的“翻转”——前半部分是 0 后半部分变为 1，
    前半部分是 1，后半部分变为 0。且每一行的前半部分和上一行相同。我们可以通过「数学归纳法」来进行证明。
    有了这个性质，那么我们再次思考原问题：对于查询某一个行第 k 个数字，如果 k 在后半部分，
    那么原问题就可以转化为求解该行前半部分的对应位置的“翻转”数字，又因为该行前半部分与上一行相同，
    所以又转化为上一行对应对应的“翻转”数字。那么按照这样一直递归下去，并在第一行时返回数字 0 即可。
*/
public class NO779_N_KthGrammar {

    @Test
    public void test() {
        assert 0 == kthGrammar(1,1);
        assert 0 == kthGrammar(2,1);
        assert 1 == kthGrammar(2,2);
    }

    public int kthGrammar(int n, int k) {
        k--;
        int res = 0;
        while (k > 0) {
            k &= k - 1;
            res ^= 1;
        }
        return res;
    }

}


















/**
// 方法1：
public int kthGrammar(int n, int k) {
    if (k == 1) {
        return 0;
    }
    if (k > (1 << (n - 2))) {
        return 1 ^ kthGrammar(n - 1, k - (1 << (n - 2)));
    }
    return kthGrammar(n - 1, k);
}

// 方法2：
public int kthGrammar(int n, int k) {
    k--;
    int res = 0;
    while (k > 0) {
        k &= k - 1;
        res ^= 1;
    }
    return res;
}

// 方法3：
public int kthGrammar(int n, int k) {
    if (n == 1)
        return 0;

    return (k & 1) ^ 1 ^ kthGrammar(n - 1, (k + 1) / 2);
}
*/
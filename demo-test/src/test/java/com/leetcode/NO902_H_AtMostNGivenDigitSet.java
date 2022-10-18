/**
 * copyright 2022/1/19
 */
package com.leetcode;

import org.junit.Test;

/**
    (困难)
    902. 最大为 N 的数字组合
        给定一个按 非递减顺序 排列的数字数组 digits 。你可以用任意次数 digits[i] 来写的数字。
        例如，如果 digits = ['1','3','5']，我们可以写数字，如 '13', '551', 和 '1351315'。
        返回 可以生成的小于或等于给定整数 n 的正整数的个数 。
    示例 1：
        输入：digits = ["1","3","5","7"], n = 100
        输出：20
        解释：
        可写出的 20 个数字是：
        1, 3, 5, 7, 11, 13, 15, 17, 31, 33, 35, 37, 51, 53, 55, 57, 71, 73, 75, 77.
    示例 2：
        输入：digits = ["1","4","9"], n = 1000000000
        输出：29523
        解释：
        我们可以写 3 个一位数字，9 个两位数字，27 个三位数字，
        81 个四位数字，243 个五位数字，729 个六位数字，
        2187 个七位数字，6561 个八位数字和 19683 个九位数字。
        总共，可以使用D中的数字写出 29523 个整数。
    示例 3:
        输入：digits = ["7"], n = 8
        输出：1

    方法一：数位动态规划
        本题为典型的数位动态规划题目，可以阅读「数位 DP」详细了解。我们称满足 x \le nx≤n 且仅包含 \textit{digits}digits 中出现的数字的 xx 为合法的，则本题需要找出所有合法的 xx 的个数。
        设 nn 是一个十进制的 kk 位数，所有数字位数小于 kk 且由 \textit{digits}digits 组成的数字则一定是小于 nn 的。我们用 \textit{dp}[i][0]dp[i][0] 表示由 \textit{digits}digits 构成且 nn 的前 ii 位的数字的个数，dp[i][1]dp[i][1] 表示由 \textit{digits}digits 构成且等于 nn 的前 ii 位的数字的个数，可知 \textit{dp}[i][1]dp[i][1] 的取值只能为 00 和 11。
        例如：n = 2345, \textit{digits} = \text{[``1",``2",``3",``4"]}n=2345,digits=[“1",“2",“3",“4"]。
        则 \textit{dp}[1][0], \textit{dp}[2][0], \textit{dp}[3][0], \textit{dp}[4][0]dp[1][0],dp[2][0],dp[3][0],dp[4][0] 分别表示小于 2, 23, 234, 23452,23,234,2345 的合法数的个数，\textit{dp}[1][1], \textit{dp}[2][1], \textit{dp}[3][1], \textit{dp}[4][1]dp[1][1],dp[2][1],dp[3][1],dp[4][1] 分别表示等于 2, 23, 234, 23452,23,234,2345 的合法数的个数。
        设 \textit{digits}digits 中的字符数目为 mm 个，数字 nn 的前 jj 位构成的数字为 \textit{num}[j]num[j]，数字 nn 的第 jj 个字符为 s[j]s[j]，当遍历到 nn 的第 ii 位时：
        当满足 i > 1i>1 时，此时任意数字 dd 构成的数字一定满足 d < \textit{num}[i]d<num[i]；
        设数字 a < \textit{num}[i-1]a<num[i−1]，则此时在 aa 的末尾追加一个数字 dd 构成的数为 a \times 10 + da×10+d，此时可以知道 dd 取 0,1,\cdots,90,1,⋯,9 中任意数字均满足小于 a \times 10 + d < \textit{num}[i] = \textit{num}[i-1] \times 10 + s[i]a×10+d<num[i]=num[i−1]×10+s[i]；
        设数字 a = \textit{num}[i-1]a=num[i−1]，则此时在 aa 的末尾追加一个数字 dd 构成的数为 a \times 10 + da×10+d，此时可以知道 d < s[i]d<s[i] 时，才能满足 a \times 10 + d < \textit{num}[i] = \textit{num}[i-1] \times 10 + s[i]a×10+d<num[i]=num[i−1]×10+s[i]；
        初始化时令 \textit{dp}[0][1] = 1dp[0][1]=1，如果前 ii 位中存在某一位 jj ，对于任意数字 dd 均不能满足 d = s[j]d=s[j]，则此时 \textit{dp}[i][1] = 0dp[i][1]=0；
        根据上述描述从小到到计算 dpdp，设 C[i]C[i] 表示数组 \textit{digits}digits 中小于 nn 的第 ii 位数字的元素个数，则状态转移方程为：

        dp[i][0] = \begin{cases} C[i], & i = 1 \\ m + dp[i-1][0] \times m + dp[i-1][1] \times C[i], & i > 1 \\ \end{cases}
        dp[i][0]={
        C[i],
        m+dp[i−1][0]×m+dp[i−1][1]×C[i],
        i=1
        i>1
        我们计算出前 kk 位小于 nn 的数字的个数 \textit{dp}[k][0]dp[k][0]，前 kk 位等于 nn 的数字的个数
        \textit{dp}[k][1]dp[k][1]，最终的答案为 \textit{dp}[k][0] + \textit{dp}[k][1]dp[k][0]+dp[k][1]。
*/
public class NO902_H_AtMostNGivenDigitSet {

    @Test
    public void test() {
        assert 20 == atMostNGivenDigitSet(new String[]{"1", "3", "5", "7"},100);
        assert 29523 == atMostNGivenDigitSet(new String[]{"1", "4", "9"},1000000000);
        assert 1 == atMostNGivenDigitSet(new String[]{"7"},8);
    }

    public int atMostNGivenDigitSet(String[] digits, int n) {
        String s = "" + n;
        int m = digits.length;
        int k = s.length();
        int[][] dp = new int[k + 1][2];
        dp[0][1] = 1;
        for (int i = 1; i <= k; i++) {
            for (String digit : digits)
                if (digit.charAt(0) == s.charAt(i - 1))
                    dp[i][1] = dp[i - 1][1];
                else if (digit.charAt(0) < s.charAt(i - 1))
                    dp[i][0] += dp[i - 1][1];
                else
                    break;
            if (i > 1)
                dp[i][0] += m + dp[i - 1][0] * m;
        }
        return dp[k][0] + dp[k][1];
    }

}

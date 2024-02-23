/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import static java.lang.Math.max;

/**
    [STRING]
    (简单)
    1422. 分割字符串的最大得分
        给你一个由若干 0 和 1 组成的字符串 s ，请你计算并返回将该字符串分割成两个 非空 子字符串（即 左 子字符串和 右 子字符串）所能获得的最大得分。
        「分割字符串的得分」为 左 子字符串中 0 的数量加上 右 子字符串中 1 的数量。
    示例 1：
        输入：s = "011101"
        输出：5
        解释：将字符串 s 划分为两个非空子字符串的可行方案有：
             左子字符串 = "0" 且 右子字符串 = "11101"，得分 = 1 + 4 = 5
             左子字符串 = "01" 且 右子字符串 = "1101"，得分 = 1 + 3 = 4
             左子字符串 = "011" 且 右子字符串 = "101"，得分 = 1 + 2 = 3
             左子字符串 = "0111" 且 右子字符串 = "01"，得分 = 1 + 1 = 2
             左子字符串 = "01110" 且 右子字符串 = "1"，得分 = 2 + 1 = 3
    示例 2：
        输入：s = "00111"
        输出：5
        解释：当 左子字符串 = "00" 且 右子字符串 = "111" 时，我们得到最大得分 = 2 + 3 = 5
    示例 3：
        输入：s = "1111"
        输出：3
*/
public class NO1422_E_MaxScore_x2 {

    @Test
    public void test() {
        assert 5 == maxScore("011101");
        assert 5 == maxScore("00111");
        assert 3 == maxScore("1111");
    }

    public int maxScore(String s) {
        int ans = 0;
        return ans;
    }

}
















/**
public int maxScore(String s) {
    int ans = 0;
    for (int i = 1; i < s.length(); i++) {
        int score = 0;
        // 左边0的数量
        for (int j = 0; j < i; j++)
            if (s.charAt(j) == '0')
                score++;

        // 右边1的数量
        for (int j = i; j < s.length(); j++)
            if (s.charAt(j) == '1')
                score++;

        // 累计计算最大score
        ans = max(ans, score);
    }
    return ans;
}
*/
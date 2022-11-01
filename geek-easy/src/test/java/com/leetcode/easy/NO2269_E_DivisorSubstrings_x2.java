/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

/**
    (简单)
    2269. 找到一个数字的 K 美丽值
        一个整数num的k美丽值定义为num中符合以下条件的子字符串数目：
            1）子字符串长度为k。
            2）子字符串能整除num。
        给你整数num和k，请你返回num的k美丽值。
    注意：
        允许有 前缀 0 。
        0 不能整除任何值。
        一个 子字符串 是一个字符串里的连续一段字符序列。
    示例 1：
        输入：num = 240, k = 2
        输出：2
        解释：以下是 num 里长度为 k 的子字符串：
             - "240" 中的 "24" ：24 能整除 240 。
             - "240" 中的 "40" ：40 能整除 240 。
             所以，k 美丽值为 2 。
    示例 2：
        输入：num = 430043, k = 2
        输出：2
        解释：以下是 num 里长度为 k 的子字符串：
             - "430043" 中的 "43" ：43 能整除 430043 。
             - "430043" 中的 "30" ：30 不能整除 430043 。
             - "430043" 中的 "00" ：0 不能整除 430043 。
             - "430043" 中的 "04" ：4 不能整除 430043 。
             - "430043" 中的 "43" ：43 能整除 430043 。
             所以，k 美丽值为 2 。
*/
public class NO2269_E_DivisorSubstrings_x2 {

    @Test
    public void test() {
        assert 2 == divisorSubstrings(240,2);
        assert 2 == divisorSubstrings(430043,2);
    }

    public int divisorSubstrings(int num, int k) {
        int ans = 0;
        return ans;
    }

}






















/**
// 方法1：
public int divisorSubstrings(int num, int k) {
    int ans = 0;
    String s = num + "";
    for (int i = 0; i <= s.length() - k; i++) {
        int a = parseInt(s.substring(i, i + k));
        if (a > 0 && num % a == 0)
            ans++;
    }
    return ans;
}
*/
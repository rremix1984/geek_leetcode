/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

/**
    [STRING]
    (简单)
    942. 增减字符串匹配
        由范围 [0,n] 内所有整数组成的 n + 1 个整数的排列序列可以表示为长度为 n 的字符串 s ，其中:
        如果 perm[i] < perm[i + 1] ，那么 s[i] == 'I'
        如果 perm[i] > perm[i + 1] ，那么 s[i] == 'D'
        给定一个字符串s，重构排列perm并返回它。如果有多个有效排列perm，则返回其中 任何一个 。
    示例 1：
        输入：s = "IDID"
        输出：[0, 4, 1, 3, 2]
    示例 2：
        输入：s = "III"
        输出：[0, 1, 2, 3]
    示例 3：
        输入：s = "DDI"
        输出：[3, 2, 0, 1]
    提示：
        1 <= s.length <= 105
        s 只包含字符 "I" 或 "D"

    方法一：贪心
        考虑 perm[0] 的值，根据题意：
        1）如果s[0] = ‘I’，那么令perm[0] = 0，则无论perm[1]为何值都满足perm[0] < perm[1]；
        2）如果s[0] = ‘D’，那么令perm[0] = n，则无论perm[1]为何值都满足perm[0] > perm[1]；
    确定好 perm[0] 后，剩余的 n − 1 个字符和 n 个待确定的数就变成了一个和原问题相同，
    但规模为n − 1的问题。因此我们可以继续按照上述方法确定perm[1]：如果s[1] = ‘I’，
    那么令perm[1]为剩余数字中的最小数；s[1] = ‘D’，那么令perm[1]为剩余数字中的最大数。
    如此循环直至剩下一个数，填入perm[n]中。代码实现时，由于每次都选择的是最小数和最大数，
    我们可以用两个变量 lo 和 hi 表示当前剩余数字中的最小数和最大数。
*/
public class NO942_E_DiStringMatch_x2 {

    @Test
    public void test() {
        assertArrayEquals(new int[]{0, 4, 1, 3, 2}, 
                diStringMatch("IDID"));
        assertArrayEquals(new int[]{0, 1, 2, 3}, 
                diStringMatch("III"));
        assertArrayEquals(new int[]{3, 2, 0, 1}, 
                diStringMatch("DDI"));
    }

    public int[] diStringMatch(String s) {
        int[] ans = new int[s.length() + 1];
        return ans;
    }

}



















/**
// 方法1：
public int[] diStringMatch(String s) {
    int n = s.length();
    int lo = 0;
    int hi = n;
    int[] perm = new int[n + 1];
    for (int i = 0; i < n; i++)
        if (s.charAt(i) == 'I')
            perm[i] = lo++;
        else
            perm[i] = hi--;

    // 最后剩下一个数，此时 lo == hi
    perm[n] = lo;
    return perm;
}
*/
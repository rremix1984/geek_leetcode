/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

/**
    (简单)
    2287. 重排字符形成目标字符串
        给你两个下标从 0 开始的字符串 s 和 target 。你可以从 s 取出一些字符并将其重排，得到若干新的字符串。
        从 s 中取出字符并重新排列，返回可以形成 target 的 最大 副本数。
    示例 1：
        输入：s = "ilovecodingonleetcode", target = "code"
        输出：2
        解释：对于 "code" 的第 1 个副本，选取下标为 4 、5 、6 和 7 的字符。
             对于 "code" 的第 2 个副本，选取下标为 17 、18 、19 和 20 的字符。
             形成的字符串分别是 "ecod" 和 "code" ，都可以重排为 "code" 。
             可以形成最多 2 个 "code" 的副本，所以返回 2 。
    示例 2：
        输入：s = "abcba", target = "abc"
        输出：1
        解释：选取下标为 0 、1 和 2 的字符，可以形成 "abc" 的 1 个副本。
             可以形成最多 1 个 "abc" 的副本，所以返回 1 。
             注意，尽管下标 3 和 4 分别有额外的 'a' 和 'b' ，但不能重用下标 2 处的 'c' ，所以无法形成 "abc" 的第 2 个副本。
    示例 3：
        输入：s = "abbaccaddaeea", target = "aaaaa"
        输出：1
        解释：选取下标为 0 、3 、6 、9 和 12 的字符，可以形成 "aaaaa" 的 1 个副本。
             可以形成最多 1 个 "aaaaa" 的副本，所以返回 1 。
*/
public class NO2287_E_RearrangeCharacters_x2 {

    @Test
    public void test() {
        assert 2 == rearrangeCharacters("ilovecodingonleetcode", "code");
        assert 1 == rearrangeCharacters("abcba", "abc");
        assert 1 == rearrangeCharacters("abbaccaddaeea", "aaaaa");
    }

    public int rearrangeCharacters(String s, String target) {
        int ans = Integer.MAX_VALUE;
        return ans;
    }

}
















/**
// 方法1：
public int rearrangeCharacters(String s, String target) {
    // 创建两个数组用来存放字符串s和target中出现各个字母的数量
    // 例如：下标为0的位置上如果是1，那么代表字母a出现了1次
    int[] list1 = new int[26];
    for (char c : s.toCharArray())
        ++list1[c - 'a']; // 等同于 list1[s.charAt(i) - 'a'] = list1[s.charAt(i) - 'a'] + 1

    // 遍历字符串s和target，并记录出现的字母次数
    int[] list2 = new int[26];
    for (char c : target.toCharArray())
        ++list2[c - 'a'];

    int ans = Integer.MAX_VALUE;

    // 经过这个循环后就能找到最小的副本数
    for (int i = 0; i < 26; i++)
        if (list2[i] != 0 )
            ans = min(ans, list1[i] / list2[i]);

    return ans;
}
*/
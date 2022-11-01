/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

/**
    (简单)
    2027. 转换字符串的最少操作次数
        给你一个字符串s，由n个字符组成，每个字符不是'X'就是'O'。
        一次操作定义为从s中选出三个连续字符并将选中的每个字符都转换为'O' 。
        注意，如果字符已经是'O'，只需要保持 不变 。
        返回将s中所有字符均转换为'O'需要执行的最少操作次数。
    示例 1：
        输入：s = "XXX"
        输出：1
        解释：XXX -> OOO
             一次操作，选中全部 3 个字符，并将它们转换为 'O' 。
    示例 2：
        输入：s = "XXOX"
        输出：2
        解释：XXOX -> OOOX -> OOOO
             第一次操作，选择前 3 个字符，并将这些字符转换为 'O' 。
             然后，选中后 3 个字符，并执行转换。最终得到的字符串全由字符 'O' 组成。
    示例 3：
        输入：s = "OOOO"
        输出：0
        解释：s 中不存在需要转换的 'X' 。
*/
public class NO2027_E_MinimumMoves_x2 {

    @Test
    public void test() {
        assert 1 == minimumMoves("XXX");
        assert 2 == minimumMoves("XXOX");
        assert 0 == minimumMoves("OOOO");
    }

    public int minimumMoves(String s) {
        int ans = 0;
        return ans;
    }

}



















/**
// 方法1：
public int minimumMoves(String s) {
    int ans = 0;
    for (int i = 0; i < s.length(); i++)
        // 遇到 'X' 就向后跳两个字符
        if (s.charAt(i) == 'X') {
            i += 2;
            ans++;
        }
    return ans;
}
*/
/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

import static com.leetcode.util.LogUtil.info;
import static java.lang.Math.min;
import static org.junit.Assert.assertArrayEquals;

/**
    (简单)
    821. 字符的最短距离
        给你一个字符串s和一个字符c，且c是s中出现过的字符。
        返回一个整数数组answer，其中answer.length == s.length且
        answer[i] 是 s 中从下标 i 到离它 最近 的字符 c 的 距离 。
        两个下标 i 和 j 之间的 距离 为 abs(i - j) ，其中 abs 是绝对值函数。
    示例 1：
        输入：s = "loveleetcode", c = "e"
        输出：[3, 2, 1, 0, 1, 0, 0, 1, 2, 2, 1, 0]
        解释：字符 'e' 出现在下标 3、5、6 和 11 处（下标从 0 开始计数）。
            距下标 0 最近的 'e' 出现在下标 3 ，所以距离为 abs(0 - 3) = 3 。
            距下标 1 最近的 'e' 出现在下标 3 ，所以距离为 abs(1 - 3) = 2 。
            对于下标 4 ，出现在下标 3 和下标 5 处的 'e' 都离它最近，
            但距离是一样的 abs(4 - 3) == abs(4 - 5) = 1 。
            距下标 8 最近的 'e' 出现在下标 6 ，所以距离为 abs(8 - 6) = 2 。
    示例 2：
        输入：s = "aaab", c = "b"
        输出：[3, 2, 1, 0]
    提示：
        1 <= s.length <= 104
        s[i] 和 c 均为小写英文字母
        题目数据保证 c 在 s 中至少出现一次

    方法一：两次遍历
        问题可以转换成，对 s 的每个下标 i，求
            1）s[i] 到其左侧最近的字符 c 的距离
            2）s[i] 到其右侧最近的字符 c 的距离
        这两者的最小值。
        对于前者，我们可以从左往右遍历 s，若 s[i]=c 则记录下此时字符 c 的的下标
        idx。遍历的同时更新 answer[i]=i−idx。
        对于后者，我们可以从右往左遍历 s，若 s[i]=c 则记录下此时字符 c 的的下标
        idx。遍历的同时更新 answer[i]=min(answer[i],idx−i)。
        代码实现时，在开始遍历的时候 idx 可能不存在，为了简化逻辑，
        我们可以用 −n 或 2n 表示，这里 n 是 s 的长度。
    方法二：遍历
        根据题意进行模拟即可：两次遍历，第一次找到每个 i 左边最近的 c，
        第二次找到每个 i 右边最近的 c。
*/
public class NO821_E_ShortestToChar_x2 {

    @Test
    public void test() {
        assertArrayEquals(new int[]{3, 2, 1, 0, 1, 0, 0, 1, 2, 2, 1, 0},
                shortestToChar("loveleetcode", 'e'));
        assertArrayEquals(new int[]{3, 2, 1, 0}, 
                shortestToChar("aaab", 'b'));
        assertArrayEquals(new int[]{2, 1, 0, 1},
                shortestToChar("aaba", 'b'));
    }

    public int[] shortestToChar(String s, char c) {
        int[] ans = new int[s.length()];
        return ans;
    }

}




















/**
// 方法1：
public int[] shortestToChar(String s, char c) {
    int[] ans = new int[s.length()];
    Arrays.fill(ans, MAX_VALUE);

    // 找到每个 i 左边最近的 c
    // j 是左边最近一个 c 的位置
    int j = -1;
    for (int i = 0; i < s.length(); i++) {
        if (s.charAt(i) == c)
            j = i;

        // 第一次，i 和 c 的距离，写入 ans 数组
        if (j != -1)
            ans[i] = i - j;
    }

    // 找到每个 i 右边最近的 c
    // j 是右边最近一个 c 的位置
    for (int i = s.length() - 1; i >= 0; i--) {
        if (s.charAt(i) == c)
            j = i;

        // 第二次，i 和 c 的距离，写入 ans 数组
        // 把从左向右那次的结果做一个 min(左, 右)
        if (j != -1)
            ans[i] = min(ans[i], j - i);
    }
    return ans;
}
*/
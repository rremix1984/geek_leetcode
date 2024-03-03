package com.leetcode.easy;

import org.junit.Test;

/**
    [ARRAY]
    （简单）
    NO.2515 到目标字符串的最短距离
    给你一个下标从 0 开始的 环形 字符串数组 words 和一个字符串 target 。环形数组 意味着数组首尾相连。
    形式上， words[i] 的下一个元素是 words[(i + 1) % n] ，而 words[i] 的前一个元素是 words[(i - 1 + n) % n] ，其中 n 是 words 的长度。
    从 startIndex 开始，你一次可以用 1 步移动到下一个或者前一个单词。
    返回到达目标字符串 target 所需的最短距离。如果 words 中不存在字符串 target ，返回 -1 。

    示例 1：
        输入：words = ["hello","i","am","leetcode","hello"], target = "hello", startIndex = 1
        输出：1
        解释：从下标 1 开始，可以经由以下步骤到达 "hello" ：
                - 向右移动 3 个单位，到达下标 4 。
                - 向左移动 2 个单位，到达下标 4 。
                - 向右移动 4 个单位，到达下标 0 。
                - 向左移动 1 个单位，到达下标 0 。
            到达 "hello" 的最短距离是 1 。
    示例 2：
        输入：words = ["a","b","leetcode"], target = "leetcode", startIndex = 0
        输出：1
        解释：从下标 0 开始，可以经由以下步骤到达 "leetcode" ：
                - 向右移动 2 个单位，到达下标 3 。
                - 向左移动 1 个单位，到达下标 3 。
            到达 "leetcode" 的最短距离是 1 。
    示例 3：
        输入：words = ["i","eat","leetcode"], target = "ate", startIndex = 0
        输出：-1
        解释：因为 words 中不存在字符串 "ate" ，所以返回 -1 。
            提示：
                1 <= words.length <= 100
                1 <= words[i].length <= 100
                words[i] 和 target 仅由小写英文字母组成
                0 <= startIndex < words.length
            Related Topics: 数组,字符串
    解题方法:
        从起始点同时向两个方向匹配target，使用step作为移动步数，每次循环step+1，成功时返回step，往左移动时左边减去step，小于0时加上列表长度，同理右侧移动超过最大范围时减去列表长度，默认返回-1
*/
public class NO2515_E_ClosetTarget {

    @Test
    public void test() {
        assert 1 == closetTarget(
                new String[]{"hello", "i", "am", "leetcode", "hello"},
            "hello", 1);
    }

    public int closetTarget(String[] words, String target, int startIndex) {
         return -1;
    }

}




















/*
// 方法1：
public int closetTarget(String[] words, String target, int startIndex) {
    int step = 0;
    int de, in;
    int n = words.length;

    for (int i = startIndex; i < n + startIndex; i++) {
        de = startIndex - step;
        in = i;
        if (de < 0)
            de += n;

        if (in > n - 1)
            in -= n;

        if (words[in].equals(target) || words[de].equals(target))
            return step;

        step++;
    }
    return -1;
}
*/
/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

/**
    (简单)
    868. 二进制间距
        给定一个正整数【n】，找到并返回【n】的二进制表示中两个 相邻【1】之间的最长距离。
        如果不存在两个相邻的【1】，返回【0】。
        如果只有【0】将两个【1】分隔开（可能不存在【0】），则认为这两个【1】彼此相邻。
        两个【1】之间的距离是它们的二进制表示中位置的绝对差。例如："1001" 中的两个【1】的距离为【3】。
    示例 1：
        输入：n = 22
        输出：2
        解释：22 的二进制是 "10110"。
             在 22 的二进制表示中，有三个 1，组成两对相邻的 1 。
             第一对相邻的 1 中，两个 1 之间的距离为 2 。
             第二对相邻的 1 中，两个 1 之间的距离为 1 。
             答案取两个距离之中最大的，也就是 2 。
    示例 2：
        输入：n = 8
        输出：0
        解释：8 的二进制是 "1000"。
             在 8 的二进制表示中没有相邻的两个 1，所以返回 0 。
    示例 3：
        输入：n = 5
        输出：2
        解释：5 的二进制是 "101"。
    提示：
        1 <= n <= 10 ^ 9
 */
public class NO868_E_BinaryGap_x2 {

    @Test
    public void test() {
        assert 2 == binaryGap(22);
        assert 0 == binaryGap(8);
        assert 2 == binaryGap(5);
    }

    public int binaryGap(int n) {
        int res = 0;
        return res;
    }

}















/**
// 方法1：
public int binaryGap(int n) {
    int res = 0;
    int last = -1;
    int cur = 0;
    while (n != 0) {
        // 最后一位是 1
        if ((n & 1) == 1) {
            if (last != -1)
                // 两个1之间的差异
                res = max(res, cur - last);
            // 记录最近的一个 1 的位置 (last)
            last = cur;
        }
        // 看前面的元素，向右移 1 位
        n >>= 1;
        cur++;
    }
    return res;
}
*/
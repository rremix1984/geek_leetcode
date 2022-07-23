/**
 * copyright 2022/1/19
 */
package com.leetcode;

import org.junit.Test;
import static com.leetcode.util.LogUtil.info;

/**
    （简单）
    455. 分发饼干
        假设你是一位很棒的家长，想要给你的孩子们一些小饼干。但是，每个孩子最
        多只能给一块饼干。对每个孩子 i，都有一个胃口值 g[i]，这是能让孩子们
        满足胃口的饼干的最小尺寸；并且每块饼干 j，都有一个尺寸 s[j] 。如果
        s[j] >= g[i]，我们可以将这个饼干 j 分配给孩子 i ，这个孩子会得到满
        足。你的目标是尽可能满足越多数量的孩子，并输出这个最大数值。
    示例 1:
        输入: g = [1, 2, 3], s = [1, 1]
        输出: 1
        解释:
        你有三个孩子和两块小饼干，3个孩子的胃口值分别是：1,2,3。
        虽然你有两块小饼干，由于他们的尺寸都是1，你只能让胃口值是1的孩子满足。
        所以你应该输出1。
    示例 2:
        输入: g = [1, 2], s = [1, 2, 3]
        输出: 2
        解释:
        你有两个孩子和三块小饼干，2个孩子的胃口值分别是1,2。
        你拥有的饼干数量和尺寸都足以让所有孩子满足。
        所以你应该输出2.
*/
public class NO455_AssignCookies_x2 {

    @Test
    public void test() {
        // 1
        info(findContentChildren(
                new int[]{1, 2, 3},
                new int[]{1, 1}));
        // 2
        info(findContentChildren(
                new int[]{1, 2},
                new int[]{1, 2, 3}));
    }

    public int findContentChildren(int[] child, int[] cookie) {
        return -1;
    }

}















/**
public int findContentChildren(int[] child, int[] cookie) {
    Arrays.sort(child);
    Arrays.sort(cookie);
    int index = 0;
    int result = 0;
    for (int i = 0; i < cookie.length && index < child.length; i++) {
        if (cookie[i] >= child[index]) {
            index++;
            result++;
        }
    }
    return result;
}
*/
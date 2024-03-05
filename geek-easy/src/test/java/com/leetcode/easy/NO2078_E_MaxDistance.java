/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import static java.lang.Math.max;

/**
    [ARRAY]
    (简单)
    2078. 两栋颜色不同且距离最远的房子
        街上有n栋房子整齐地排成一列，每栋房子都粉刷上了漂亮的颜色。
        给你一个下标从0开始且长度为n的整数数组colors，其中colors[i]表示第i栋房子的颜色。
        返回两栋颜色不同房子之间的最大距离。
        第i栋房子和第j栋房子之间的距离是abs(i-j)，其中abs(x)是x的绝对值。
    示例 1：
        输入：colors = {1, 1, 1, 6, 1, 1, 1}
        输出：3
        解释：上图中，颜色 1 标识成蓝色，颜色 6 标识成红色。
             两栋颜色不同且距离最远的房子是房子 0 和房子 3 。
             房子 0 的颜色是颜色 1 ，房子 3 的颜色是颜色 6 。
             两栋房子之间的距离是 abs(0 - 3) = 3 。
             注意，房子 3 和房子 6 也可以产生最佳答案。
    示例 2：
        输入：colors = {1, 8, 3, 8, 3}
        输出：4
        解释：上图中，颜色 1 标识成蓝色，颜色 8 标识成黄色，颜色 3 标识成绿色。
             两栋颜色不同且距离最远的房子是房子 0 和房子 4 。
             房子 0 的颜色是颜色 1 ，房子 4 的颜色是颜色 3 。
             两栋房子之间的距离是 abs(0 - 4) = 4 。
    示例 3：
        输入：colors = {0, 1}
        输出：1
        解释：两栋颜色不同且距离最远的房子是房子 0 和房子 1 。
             房子 0 的颜色是颜色 0 ，房子 1 的颜色是颜色 1 。
             两栋房子之间的距离是 abs(0 - 1) = 1 。
*/
public class NO2078_E_MaxDistance {

    @Test
    public void test() {
        assert 3 == maxDistance(new int[]{1, 1, 1, 6, 1, 1, 1});
        assert 4 == maxDistance(new int[]{1, 8, 3, 8, 3});
        assert 1 == maxDistance(new int[]{0, 1});
    }

    public int maxDistance(int[] colors) {
        int ans1 = 0;
        int ans2 = 0;
        // 从后向前找，计算距离开始位置最远的答案
        for (int i = colors.length - 1; i > 0; i--)
            if (colors[0] != colors[i]) {
                ans1 = i;
                break;
            }

        // 从前向后找，计算距离结束位置最远的答案
        for (int i = 0; i < colors.length - 1; i++)
            if (colors[colors.length - 1] != colors[i]) {
                ans2 = colors.length - 1 - i;
                break;
            }

        // 返回两者（从前向后、从后向前）的最大值
        return max(ans1, ans2);
    }

}


















/**
// 方法1：
public int maxDistance(int[] colors) {
    int ans1 = 0;
    // 从后向前找，计算距离开始位置最远的答案
    for (int i = colors.length - 1; i > 0; i--)
        if (colors[0] != colors[i]) {
            ans1 = i;
            break;
        }

    int ans2 = 0;
    // 从前向后找，计算距离结束位置最远的答案
    for (int i = 0; i < colors.length - 1; i++)
        if (colors[colors.length - 1] != colors[i]) {
            ans2 = colors.length - 1 - i;
            break;
        }

    // 返回两者（从前向后、从后向前）的最大值
    return max(ans1, ans2);
}
*/
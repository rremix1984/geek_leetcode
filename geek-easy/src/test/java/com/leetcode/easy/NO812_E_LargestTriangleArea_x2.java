/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import static java.lang.Math.max;

/**
    [ARRAY]
    (简单)
    812. 最大三角形面积
        给定包含多个点的集合，从其中取三个点组成三角形，返回能组成的最大三角形的面积。
    示例:
        输入: points = {{0, 0}, {0, 1}, {1, 0}, {0, 2}, {2, 0}}
        输出: 2
        解释: 这五个点如下图所示。组成的橙色三角形是最大的，面积为2。
    注意:
        3 <= points.length <= 50.
        不存在重复的点。
        -50 <= points[i][j] <= 50.
        结果误差值在 10^-6 以内都认为是正确答案。

     解题方法
        我们把 A(x1, y1),B(x2, y2),C(x3, y3)A(x1,y1),B(x2,y2),C(x3,y3)分别向 xx 轴投影，分别得到点E, D, FE,D,F。
        可以得到 3 个梯形：以下图中的黄色的边为梯形的上下底，分别以三角形的三条边作为梯形的斜边。
     于是：
        三角形 ABC 的面积 = 梯形 BDEA 的面积 + 梯形 AEFC 的面积 - 梯形 BDFC 的面积三角形ABC的面积=梯形BDEA的面积+梯形AEFC的面积−梯形BDFC的面积
        = [(y1 + y2) * (x1 - x2)]/2 + [(y3 + y1) * (x3 - x1)]/2 - [(y2 + y3) * (x3 - x2)]/2=[(y1+y2)∗(x1−x2)]/2+[(y3+y1)∗(x3−x1)]/2−[(y2+y3)∗(x3−x2)]/2
        = 1/2 * [x1(y2 - y3) + x2(y3 - y1) + x3(y1 - y2)]=1/2∗[x1(y2−y3)+x2(y3−y1)+x3(y1−y2)]
        我们就有了根据三角形的三点的坐标求面积的公式。
     代码
        三重 for 循环，从题目给出的二维坐标中取出 3 个点，根据上面的面积公式求组成的三角形面积。
        取最大面积即可。
*/
public class NO812_E_LargestTriangleArea_x2 {

    @Test
    public void test() {
        assert 2.0 == largestTriangleArea(new int[][]{{0, 0}, {0, 1}, {1, 0}, {0, 2}, {2, 0}});
    }

    public double largestTriangleArea(int[][] points) {
        double ans = 0.0;
        int n = points.length;
        for (int i = 0; i < n - 2; i++)
            for (int j = i + 1; j < n - 1; j++)
                for (int k = j + 1; k < n; k++) {
                    int x1 = points[i][0], y1 = points[i][1];
                    int x2 = points[j][0], y2 = points[j][1];
                    int x3 = points[k][0], y3 = points[k][1];
                    ans = max(ans,
                        Math.abs((x1 * y2 + x2 * y3 + x3 * y1) - (x1 * y3 + x2 * y1 + x3 * y2) / 2.0));
                }
        return ans;
    }

}
















/**
// 方法1：
public double largestTriangleArea(int[][] points) {
    double ans = 0.0;
    int n = points.length;
    for (int i = 0; i < n - 2; i++)
        for (int j = i + 1; j < n - 1; j++)
            for (int k = j + 1; k < n; k++) {
                int x1 = points[i][0], y1 = points[i][1];
                int x2 = points[j][0], y2 = points[j][1];
                int x3 = points[k][0], y3 = points[k][1];
                ans = max(ans,
                        Math.abs((x1 * y2 + x2 * y3 + x3 * y1) - (x1 * y3 + x2 * y1 + x3 * y2) / 2.0));
            }
    return ans;
}
*/
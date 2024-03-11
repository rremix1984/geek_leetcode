package com.interval.easy;

import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;

/**
    [ARRAY]
    (简单)
    Interval 08.10 颜色填充
        编写函数，实现许多图片编辑软件都支持的「颜色填充」功能。
        待填充的图像用二维数组image表示，元素为初始颜色值。
        初始坐标点的行坐标为sr列坐标为sc。需要填充的新颜色为newColor。
        「周围区域」是指颜色相同且在上、下、左、右四个方向上存在相连情况的若干元素。
        请用新颜色填充初始坐标点的周围区域，并返回填充后的图像。
    示例：
        输入：
        image = [[1, 1, 1],
                 [1, 1, 0],
                 [1, 0, 1]]
        sr = 1, sc = 1, newColor = 2
        输出：[[2,2,2],
              [2,2,0],
              [2,0,1]]
        解释:
        初始坐标点位于图像的正中间，坐标 (sr,sc)=(1,1) 。
        初始坐标点周围区域上所有符合条件的像素点的颜色都被更改成2。
        注意，右下角的像素没有更改为 2 ，因为它不属于初始坐标点的周围区域。
    提示：
        image 和 image[0] 的长度均在范围 [1, 50] 内。
        初始坐标点 (sr,sc) 满足 0 <= sr < image.length 和 0 <= sc < image[0].length 。
        image[i][j] 和 newColor 表示的颜色值在范围 [0, 65535] 内。
    Related Topics:深度优先搜索,广度优先搜索,数组,矩阵
*/
public class Interval_08_10_E_FloodFill {

    @Test
    public void test() {
        assertArrayEquals(
            new int[][]{{2,2,2},
                        {2,2,0},
                        {2,0,1}},
        floodFill(new int[][]{{1, 1, 1},
                              {1, 1, 0},
                              {1, 0, 1}},
                1, 1, 2));
    }

    public int[][] floodFill(int[][] image, int sr, int sc, int newColor) {
        // 2024/3/11 NO.1 用递归来做
        return image;
    }

}

















/*
// 方法1：
public int[][] floodFill(int[][] image, int sr, int sc, int newColor) {
    // 新值和旧值相等就没必要涂了
    if (newColor == image[sr][sc])
        return image;

    dfs(image, sr, sc, newColor, image[sr][sc]);
    return image;
}

public void dfs(int[][] image, int sr, int sc, int newColor, int oldColor) {
    // 1. 判断边界值，越界或者这个点不能涂就返回
    if (sr < 0 || sc < 0 || sr >= image.length || sc >= image[0].length
            || image[sr][sc] != oldColor)
        return;

    // 2. 先涂单元格
    image[sr][sc] = newColor;

    // 3. 四个方向扩散
    dfs(image, sr - 1, sc, newColor, oldColor);
    dfs(image, sr + 1, sc, newColor, oldColor);
    dfs(image, sr, sc - 1, newColor, oldColor);
    dfs(image, sr, sc + 1, newColor, oldColor);
}
*/

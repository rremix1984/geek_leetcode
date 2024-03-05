/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

/**
   [NUMBER]
    (简单)
    1742. 盒子中小球的最大数量
        你在一家生产小球的玩具厂工作，有 n 个小球，编号从 lowLimit 开始，到 highLimit 结束
        （包括 lowLimit 和 highLimit ，即 n == highLimit - lowLimit + 1）。
        另有无限数量的盒子，编号从 1 到 infinity 。
        你的工作是将每个小球放入盒子中，其中盒子的编号应当等于小球编号上每位数字的和。
        例如，编号 321 的小球应当放入编号 3 + 2 + 1 = 6 的盒子，而编号 10 的小球应当放入编号 1 + 0 = 1 的盒子。
        给你两个整数 lowLimit 和 highLimit ，返回放有最多小球的盒子中的小球数量。
        如果有多个盒子都满足放有最多小球，只需返回其中任一盒子的小球数量。
    示例 1：
        输入：lowLimit = 1, highLimit = 10
        输出：2
        解释：盒子编号：1 2 3 4 5 6 7 8 9 10 11 ...
             小球数量：2 1 1 1 1 1 1 1 1 0  0  ...
             编号 1 的盒子放有最多小球，小球数量为 2 。
    示例 2：
        输入：lowLimit = 5, highLimit = 15
        输出：2
        解释：盒子编号：1 2 3 4 5 6 7 8 9 10 11 ...
             小球数量：1 1 1 1 2 2 1 1 1 0  0  ...
             编号 5 和 6 的盒子放有最多小球，每个盒子中的小球数量都是 2 。
    示例 3：
        输入：lowLimit = 19, highLimit = 28
        输出：2
        解释：盒子编号：1 2 3 4 5 6 7 8 9 10 11 12 ...
             小球数量：0 1 1 1 1 1 1 1 1 2  0  0  ...
             编号 10 的盒子放有最多小球，小球数量为 2 。
    提示：
        1 <= lowLimit <= highLimit <= 10^5
*/
public class NO1742_E_CountBalls_x2 {

    @Test
    public void test() {
        assert 2 == countBalls(1, 10);
        assert 2 == countBalls(5, 15);
        assert 2 == countBalls(19, 28);
    }

    public int countBalls(int lowLimit, int highLimit) {
        return -1;
    }

}

















/*
// 方法1：
public int countBalls(int lowLimit, int highLimit) {
    // 10的5次方，100000 最大值每一位的和是 99999
    // 一共5个9，加在一起是 9 x 5 = 45 算上 0 一共是 46 个值的数组
    // 最多 46 个箱子
    int[] boxes = new int[46];
    for (int i = lowLimit; i <= highLimit; i++) {
        int box = getBox(i);
        boxes[box]++;
    }

    int max=0;
    for (int i = 1; i < 46 ; i++)
        max = max(max,boxes[i]);

    return max;
}

private int getBox(int i) {
    int sum = 0;
    while (i > 9) {
        sum += i % 10;
        i = i / 10;
    }
    sum += i;
    return sum;
}
*/
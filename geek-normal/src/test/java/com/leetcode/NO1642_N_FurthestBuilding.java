package com.leetcode;

import org.junit.Test;
import java.util.*;

/**
    [ARRAY] ||||||
    (中等)
    NO.1642 可以到达的最远建筑
    给你一个整数数组 heights，表示建筑物的高度。另有一些砖块 bricks 和梯子 ladders。
    你从建筑物 0 开始旅程，不断向后面的建筑物移动，期间可能会用到砖块或梯子。
    当从建筑物 i 移动到建筑物 i+1（下标 从 0 开始 ）时：
     1）如果当前建筑物的高度 大于或等于 下一建筑物的高度，则不需要梯子或砖块
     2）如果当前建筑的高度 小于 下一个建筑的高度，您可以使用【一架梯子】或【(h[i+1] - h[i])个砖块】
     3）如果以最佳方式使用给定的梯子和砖块，返回你可以到达的最远建筑物的下标（下标从0开始）。
    示例 1：
        输入：heights = [4, 2, 7, 6, 9, 14, 12], bricks = 5, ladders = 1
        输出：4
        解释：从建筑物 0 出发，你可以按此方案完成旅程：
            - 不使用砖块或梯子到达建筑物 1 ，因为 4 >= 2
            - 使用 5 个砖块到达建筑物 2 。你必须使用砖块或梯子，因为 2 < 7
            - 不使用砖块或梯子到达建筑物 3 ，因为 7 >= 6
            - 使用唯一的梯子到达建筑物 4 。你必须使用砖块或梯子，因为 6 < 9
            无法越过建筑物 4 ，因为没有更多砖块或梯子。
                                    |
                                    |
             ∵ 没有砖头和梯子 ∴ 上不去了|   |
                                    |   |
                                    |   |
                               /|   |   |
                            梯子 |   |   |
                       /|\   /  |   |   |
                      / | \ |   |   |   |
                      / |   |   |   |   |
                |\ 砖x5 |   |   |   |   |
                | \  /  |   |   |   |   |
                |  \|   |   |   |   |   |
             ___|___|___|___|___|___|___|___
                4   2   7   6   9   14  12
    示例 2：
        输入：heights = [4, 12, 2, 7, 3, 18, 20, 3, 19], bricks = 10, ladders = 2
        输出：7
    示例 3：
        输入：heights = [14, 3, 19 ,3], bricks = 17, ladders = 0
        输出：3
    提示：
        1 <= heights.length <= 10 ^ 5
        1 <= heights[i] <= 10 ^ 6
        0 <= bricks <= 10 ^ 9
        0 <= ladders <= heights.length
    Related Topics:贪心,数组,堆（优先队列）
*/
public class NO1642_N_FurthestBuilding {

    @Test
    public void test() {
        assert 4 == furthestBuilding(
                new int[]{4, 2, 7, 6, 9, 14, 12}, 5, 1);
        assert 3 == furthestBuilding(
                new int[]{14, 3, 19, 3}, 17, 0);
        assert 7 == furthestBuilding(
                new int[]{4, 12, 2, 7, 3, 18, 20, 3, 19}, 10, 2);
    }

    public int furthestBuilding(int[] heights, int bricks, int ladders) {
        // 2024/3/17 NO.1
        // 2024/3/22 NO.2 能看懂了
        // 2024/3/25 NO.3
        // 2024/3/28 NO.4 做不出来，但是能看懂，有思路。这道题必须多练习
        // 2024/3/31 NO.5 没做出来，思路几乎对了，沉不下新来做，思考深度不够
        // 2024/4/1  NO.6 一遍过
        int sum = 0;
        int n = heights.length;
        Queue<Integer> queue = new PriorityQueue<>((a,b)->b-a);
        // TODO

        return heights.length - 1;
    }

}















/*
// 方法1：
public int furthestBuilding(int[] heights, int bricks, int ladders) {
    // 最大Gap个用梯子上
    int sum = 0;
    Queue<Integer> queue = new PriorityQueue<>(
            (a, b) -> b - a);

    for (int i = 1; i < heights.length; i++) {
        int diff = heights[i] - heights[i - 1];
        if (diff <= 0)
            continue;

        // 记录一下砖头和
        sum += diff;

        // 将使用的砖头放入优先队列
        queue.offer(diff);

        // 如果发现砖头和比给的砖头要大了
        while (sum > bricks) {
            // 换成使用梯子
            ladders--;

            // 砖头和减去最大值
            // 梯子要替换最多的砖头
            sum -= queue.poll();
        }
        // 如果循环结束发现梯子为负数，说明上不去了。
        if (ladders < 0)
            return i - 1;
    }
    return heights.length - 1;
}

// 方法2：
public int furthestBuilding(int[] heights, int bricks, int ladders) {
    int brick_sum = 0;
    Queue<Integer> queue = new PriorityQueue<>((a, b) -> b - a);
    for (int i = 1; i < heights.length; i++) {
        int diff = heights[i] - heights[i - 1];
        if (diff <= 0)
            continue;

        //记录一下砖头和
        brick_sum += diff;

        //将使用的砖头放入优先队列
        queue.offer(diff);

        //如果发现砖头和比给的砖头要大了
        while (brick_sum > bricks) {
            //换成使用梯子
            ladders--;

            // 砖头和减去最大值
            // 梯子要替换最多的砖头
            brick_sum = brick_sum - queue.poll();
        }
        if (ladders < 0)
            return i - 1;
    }
    return heights.length - 1;
}
*/
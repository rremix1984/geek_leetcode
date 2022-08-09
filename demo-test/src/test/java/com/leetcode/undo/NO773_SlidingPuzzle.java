/**
 * copyright 2022/1/19
 */
package com.leetcode.undo;

import org.junit.Test;
import java.util.*;
import static com.leetcode.util.BoardUtil.swap;
import static com.leetcode.util.LogUtil.info;

/**
    （困难）
    773. 滑动谜题
        在一个 2 x 3 的板上（board）有 5 块砖瓦，用数字 1~5 来表示, 以及一块空缺用 0 来表示。一次 移动 定义为选择 0 与一个相邻的数字（上下左右）进行交换.
        最终当板 board 的结果是 [[1,2,3],[4,5,0]] 谜板被解开。
        给出一个谜板的初始状态 board ，返回最少可以通过多少次移动解开谜板，如果不能解开谜板，则返回 -1 。
    示例 1：
        输入：board = {{1,2,3},{4,0,5}}
        输出：1
        解释：交换 0 和 5 ，1 步完成
    示例 2:
        输入：board = {{1,2,3},{5,4,0}}
        输出：-1
        解释：没有办法完成谜板
    示例 3:
        输入：board = {{4,1,2},{5,0,3}}
        输出：5
        解释：最少完成谜板的最少移动次数是 5 ，
            一种移动路径:
            尚未移动: {{4,1,2},{5,0,3}}
            移动 1 次: {{4,1,2},{0,5,3}}
            移动 2 次: {{0,1,2},{4,5,3}}
            移动 3 次: {{1,0,2},{4,5,3}}
            移动 4 次: {{1,2,0},{4,5,3}}
            移动 5 次: {{1,2,3},{4,5,0}}
*/
public class NO773_SlidingPuzzle {

    @Test
    public void test() {
        info(slidingPuzzle(new int[][]{{1, 2, 3}, {4, 0, 5}}));//  1
        info(slidingPuzzle(new int[][]{{1, 2, 3}, {5, 4, 0}}));// -1
        info(slidingPuzzle(new int[][]{{4, 1, 2}, {5, 0, 3}}));//  5
    }

    int[][] neighbors = {{1, 3  },// 当位置在0的时候，可以交换的位置只有 1, 3
                        {0, 2, 4},// 当位置在1的时候，可以交换的位置只有 0, 2, 4
                        {1, 5   },// 当位置在2的时候，可以交换的位置只有 1, 5
                        {0, 4   },// 当位置在3的时候，可以交换的位置只有 0, 4
                        {1, 3, 5},// 当位置在4的时候，可以交换的位置只有 1, 3, 5
                        {2, 4  }};// 当位置在5的时候，可以交换的位置只有 2, 4

    public int slidingPuzzle(int[][] board) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 2; ++i)
            for (int j = 0; j < 3; ++j)
                sb.append(board[i][j]);

        String initial = sb.toString();
        // 胜利的状态 123450
        if ("123450".equals(initial))
            return 0;

        int step = 0;
        Queue<String> queue = new LinkedList<>();
        queue.offer(initial);

        Set<String> seen = new HashSet<>();
        seen.add(initial);

        // BFS广度遍历
        while (!queue.isEmpty()) {
            step++;
            int size = queue.size();
            for (int i = 0; i < size; ++i) {
                String status = queue.poll();
                for (String nextStatus : get(status)) {
                    if (!seen.contains(nextStatus)) {
                        if ("123450".equals(nextStatus))
                            return step;

                        queue.offer(nextStatus);
                        seen.add(nextStatus);
                    }
                }
            }
        }
        return -1;
    }

    // 枚举 status 通过一次交换操作得到的状态
    public List<String> get(String status) {
        List<String> ret = new ArrayList<>();
        char[] array = status.toCharArray();
        int x = status.indexOf('0');
        for (int y : neighbors[x]) {
            swap(array, x, y);
            ret.add(new String(array));
            swap(array, x, y);
        }
        return ret;
    }

}
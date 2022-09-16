/**
 * copyright 2022/1/19
 */
package com.leetcode.normal;

import org.junit.Test;

import static com.leetcode.util.LogUtil.info;

/**
    （中等）
    1091. 二进制矩阵中的最短路径
        给你一个 n x n 的二进制矩阵 grid 中，返回矩阵中最短 畅通路径 的长度。如果不存在这样的路径，返回 -1 。
        二进制矩阵中的 畅通路径 是一条从 左上角 单元格（即，(0, 0)）到 右下角 单元格（即，(n - 1, n - 1)）的路径，该路径同时满足下述要求：
        路径途经的所有单元格都的值都是 0 。
        路径中所有相邻的单元格应当在 8 个方向之一 上连通（即，相邻两单元之间彼此不同且共享一条边或者一个角）。
        畅通路径的长度 是该路径途经的单元格总数。
    示例 1：
        输入：grid = {{0, 1},
                     {1, 0}}
        输出：2
    示例 2：
        输入：grid = {{0, 0, 0},
                     {1, 1, 0},
                     {1, 1, 0}}
        输出：4
    示例 3：
        输入：grid = {{1, 0, 0},
                     {1, 1, 0},
                     {1, 1, 0}}
        输出：-1
*/
public class NO1091_N_ShortestPathInBinaryMatrix_x2 {

    @Test
    public void test() {
        assert 2 == shortestPathBinaryMatrix(
            new int[][]{{0, 1},
                        {1, 0}});// 2
        assert 4 == shortestPathBinaryMatrix(
            new int[][]{{0, 0, 0},
                        {1, 1, 0},
                        {1, 1, 0}});// 4
        assert -1 == shortestPathBinaryMatrix(
            new int[][]{{1, 0, 0},
                        {1, 1, 0},
                        {1, 1, 0}});// -1
    }
    
    int[] dx = {0, 0, -1, 1,-1, 1,-1, 1};
    int[] dy = {-1, 1, 0, 0, -1,-1, 1, 1};

    public int shortestPathBinaryMatrix(int[][] grid) {
        return -1;
    }

}

























/**
int[] dx = {0, 0, -1, 1,-1, 1,-1, 1};
int[] dy = {-1, 1, 0, 0, -1,-1, 1, 1};

public int shortestPathBinaryMatrix(int[][] grid) {

    Deque<Node> queue = new ArrayDeque<>();
    // 第一次移动的节点，step = 2 最少步骤是2步
    queue.addLast(new Node(0, 0, 2));

    int n = grid.length;

    // 如果左上、右下都是1，说明没法达到，返回-1
    // 如果行数小于等于2，那就是2步
    if ( grid[0][0] == 1
            || grid[n - 1][n - 1] == 1)
        return -1;
    else if (n <= 2)
        return n;

    // 开始BFS 广度优先遍历
    while (!queue.isEmpty()) {
        // 先把第一个元素出栈
        Node cur = queue.removeFirst();
        int x = cur.x;
        int y = cur.y;
        int step = cur.step;

        // 遍历 8 个方向 左、右、上、下、左上、右上、左下、右下
        for (int i = 0; i < 8; i++) {
            // 选中一个方向后，新的坐标 (newX, newY)
            int newX = x + dx[i];
            int newY = y + dy[i];

            // 如果新坐标没有到边界，[0, n)，并且元素值为 0
            if (  0 <= newX
                    && 0 <= newY
                    && newX < n
                    && newY < n
                    && grid[newX][newY] == 0) {

                // 如果找到终点，直接返回 step
                if (newX == n - 1 && newY == n - 1)
                    return step ;

                // 从新坐标 (newX, newY) 开始走，同时步骤 +1
                queue.addLast(new Node(newX, newY, step + 1));

                // 标记已遍历过，避免重复
                grid[newX][newY] = 1;
            }
        }
    }
    return -1;
}
*/
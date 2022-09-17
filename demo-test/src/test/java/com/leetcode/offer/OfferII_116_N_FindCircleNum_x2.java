/**
 * copyright 2022/1/19
 */
package com.leetcode.offer;

import org.junit.Test;

import java.util.LinkedList;
import java.util.Queue;

import static com.leetcode.util.LogUtil.info;

/**
    (普通)
    剑指 Offer II 116. 省份数量
        有 n 个城市，其中一些彼此相连，另一些没有相连。如果城市 a 与城市 b 直接相连，且城市 b 与城市 c 直接相连，那么城市 a 与城市 c 间接相连。
        省份 是一组直接或间接相连的城市，组内不含其他没有相连的城市。
        给你一个 n x n 的矩阵 isConnected ，其中 isConnected[i][j] = 1 表示第 i 个城市和第 j 个城市直接相连，而 isConnected[i][j] = 0 表示二者不直接相连。
        返回矩阵中 省份 的数量。
    示例 1：
        输入：isConn = { {1,1,0},
                        {1,1,0},
                        {0,0,1}}
        输出：2
    示例 2：
        输入：isConn = { {1,0,0},
                        {0,1,0},
                        {0,0,1}}
        输出：3
*/
public class OfferII_116_N_FindCircleNum_x2 {

    @Test
    public void test() {
        assert 2 == findCircleNum(
                new int[][]{{1, 1, 0},
                            {1, 1, 0},
                            {0, 0, 1}});
        assert 3 == findCircleNum(
                new int[][]{{1, 0, 0},
                            {0, 1, 0},
                            {0, 0, 1}});
        assert 5 == findCircleNum(
                new int[][]{{1, 0, 0, 0, 0},
                            {0, 1, 0, 0, 0},
                            {0, 0, 1, 0, 0},
                            {0, 0, 0, 1, 0},
                            {0, 0, 0, 0, 1}});
    }

    public int findCircleNum(int[][] isConn) {
        int ans = 0;
        return ans;
    }

}















/**
// 方法1：dfs深度遍历
public int findCircleNum(int[][] isConn) {
    int ans = 0;
    // vstd 代表城市是否被访问过 true-是，false-否
    boolean[] vstd = new boolean[isConn.length];
    // 选中一个城市 i，迭代选择另一个城市 j 判断是否相邻
    for (int i = 0; i < isConn.length; i++) {
        // 如果城市 i 没有被决策过
        if (!vstd[i]) {
            // 判断是否有两个城市(i, j) 相邻
            dfs(isConn, vstd, i);
            ans++;
        }
    }
    return ans;
}

public void dfs(int[][] isConn, boolean[] vstd, int i) {
    // 判断城市 i 与城市 j 是否相邻
    for (int j = 0; j < isConn.length; j++) {
        // 如果两个城市（i, j）相邻，说明可以成为一个'省'，同时标记城市 j 为访问过
        if (isConn[i][j] == 1 && !vstd[j]) {
            vstd[j] = true;
            dfs(isConn, vstd, i);
        }
    }
}

// 方法2：简化后的dfs
public int findCircleNum(int[][] isConn) {
    int ans = 0;
    boolean[] vstd = new boolean[isConn.length];
    for (int i = 0; i < isConn.length; i++) {
        if (!vstd[i]) {
            for (int j = 0; j < isConn.length; j++) {
                if (!vstd[j] && isConn[i][j] == 1) {
                    vstd[j] = true;
                    j = 0;
                }
            }
            ans++;
        }
    }
    return ans;
}

// 方法2：BFS
public int findCircleNum(int[][] isConn) {
    int ans = 0;
    // vstd 代表城市是否被访问过 true-是，false-否
    boolean[] vstd = new boolean[isConn.length];
    Queue<Integer> queue = new LinkedList<>();
    // 选中一个城市 i，迭代选择另一个城市 j 判断是否相邻
    for (int i = 0; i < isConn.length; i++) {
        // 如果城市 i 访问过跳过循环
        if (vstd[i])
            continue;

        queue.offer(i);
        while (!queue.isEmpty()) {
            int k = queue.poll();
            vstd[k] = true;
            for (int j = 0; j < isConn.length; j++)
                if (isConn[i][j] == 1 && !vstd[j])
                    queue.offer(j);
        }
        ans++;
    }
    return ans;
}
*/
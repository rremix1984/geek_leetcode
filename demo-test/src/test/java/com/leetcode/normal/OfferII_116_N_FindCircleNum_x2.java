/**
 * copyright 2022/1/19
 */
package com.leetcode.normal;

import com.leetcode.util.ListNode;
import org.junit.Test;

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
                new int[][]{{1,1,0},
                            {1,1,0},
                            {0,0,1}});
        assert 3 == findCircleNum(
                new int[][]{{1,0,0},
                            {0,1,0},
                            {0,0,1}});
    }

    public int findCircleNum(int[][] isConn) {
        int cities = isConn.length;
        boolean[] vstd = new boolean[cities];
        int ans = 0;
        for (int i = 0; i < cities; i++)
            if (!vstd[i]) {
                dfs(isConn, vstd, cities, i);
                ans++;
            }

        return ans;
    }

    public void dfs(int[][] isConn, boolean[] vstd, int cities, int i) {
        for (int j = 0; j < cities; j++)
            if (isConn[i][j] == 1 && !vstd[j]) {
                vstd[j] = true;
                dfs(isConn, vstd, cities, j);
            }
    }

}















/**
// 方法1：快慢指针法
public ListNode getKthFromEnd(ListNode head, int k) {
    ListNode fast = head, slow = head;
    for (int i = 0; i < k; i++)
        fast = fast.next;
    while (fast != null) {
        fast = fast.next;
        slow = slow.next;
    }
    return slow;
}
*/
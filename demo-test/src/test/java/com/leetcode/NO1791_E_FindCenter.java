/**
 * copyright 2022/1/19
 */
package com.leetcode;

import org.junit.Test;

/**
    (简单)
    1791. 找出星型图的中心节点
        有一个无向的 星型 图，由 n 个编号从 1 到 n 的节点组成。星型图有一个 中心 节点，并且恰有 n - 1 条边将中心节点与其他每个节点连接起来。
        给你一个二维整数数组 edges ，其中 edges[i] = [ui, vi] 表示在节点 ui 和 vi 之间存在一条边。请你找出并返回 edges 所表示星型图的中心节点。
    示例 1：
        输入：edges = {{1, 2}, {2, 3}, {4, 2}}
        输出：2
        解释：如上图所示，节点 2 与其他每个节点都相连，所以节点 2 是中心节点。
    示例 2：
        输入：edges = {{1, 2}, {5, 1}, {1, 3}, {1, 4}}
        输出：1
*/
public class NO1791_E_FindCenter {

    @Test
    public void test() {
        assert 2 == findCenter(new int[][]{{1, 2}, {2, 3}, {4, 2}});
        assert 1 == findCenter(new int[][]{{1, 2}, {5, 1}, {1, 3}, {1, 4}});
    }

    public int findCenter(int[][] edges) {
        int[] degrees = new int[edges.length + 2];
        for (int[] edge : edges) {
            degrees[edge[0]]++;
            degrees[edge[1]]++;
        }
        for (int i = 1; ; i++)
            if (degrees[i] == edges.length)
                return i;
    }

}














/**
public int findCenter(int[][] edges) {
    int[] degrees = new int[edges.length + 2];
    for (int[] edge : edges) {
        degrees[edge[0]]++;
        degrees[edge[1]]++;
    }
    for (int i = 1; ; i++)
        if (degrees[i] == edges.length)
            return i;
}
*/
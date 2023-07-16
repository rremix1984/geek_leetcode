package com.leetcode;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.leetcode.util.MathUtils.getArray;
import static com.leetcode.util.MathUtils.getArrays;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
    834. 树中距离之和
        给定一个无向、连通的树。树中有 n 个标记为 0...n-1 的节点以及 n-1 条边 。
        给定整数 n 和数组 edges ， edges[i] = [ai, bi]表示树中的节点 ai 和 bi 之间有一条边。
        返回长度为 n 的数组 answer ，其中 answer[i] 是树中第 i 个节点与所有其他节点之间的距离之和。
    示例 1:
        输入: n = 6, edges = [[0,1],[0,2],[2,3],[2,4],[2,5]]
        输出: [8,12,6,10,10,10]
        解释: 树如图所示。
        我们可以计算出 dist(0,1) + dist(0,2) + dist(0,3) + dist(0,4) + dist(0,5)
        也就是 1 + 1 + 2 + 2 + 2 = 8。 因此，answer[0] = 8，以此类推。
    示例 2:
        输入: n = 1, edges = []
        输出: [0]
    示例 3:
        输入: n = 2, edges = [[1,0]]
        输出: [1,1]
    提示:
        1 <= n <= 3 * 104
        edges.length == n - 1
        edges[i].length == 2
        0 <= ai, bi < n
        ai != bi
    给定的输入保证为有效的树
*/
public class NO834_H_SumOfDistancesInTree {

    @Test
    public void test() {
        assertArrayEquals(new int[]{1, 1},
            sumOfDistancesInTree(2, new int[][]{{1, 0}}));
        assertArrayEquals(new int[]{0},
            sumOfDistancesInTree(1, new int[][]{}));
        assertArrayEquals(new int[]{8, 12, 6, 10, 10, 10},
            sumOfDistancesInTree(6, new int[][]{{0, 1},{0, 2},{2, 3},{2, 4},{2, 5}}));
    }

    public int[] sumOfDistancesInTree(int n, int[][] edges) {
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++)
            graph.add(new ArrayList<>());

        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            graph.get(u).add(v);
            graph.get(v).add(u);
        }

        int[] count = new int[n];
        int[] answer = new int[n];

        // 第一次DFS计算子树节点个数和节点到其他节点的距离之和
        count[0] = dfs(0, -1, graph, count, answer);

        // 第二次DFS根据父节点的信息计算子节点的距离之和
        dfs2(0, -1, graph, count, answer, n);

        return answer;
    }

    // 第一次DFS
    private int dfs(int node, int parent, List<List<Integer>> graph, int[] count, int[] answer) {
        int subCount = 1;

        for (int child : graph.get(node)) {
            if (child != parent) {
                subCount += dfs(child, node, graph, count, answer);
                answer[node] += answer[child] + count[child];
            }
        }

        count[node] = subCount;
        return subCount;
    }

    // 第二次DFS
    private void dfs2(int node, int parent, List<List<Integer>> graph, int[] count, int[] answer, int n) {
        for (int child : graph.get(node)) {
            if (child != parent) {
                answer[child] = answer[node] - count[child] + n - count[child];
                dfs2(child, node, graph, count, answer, n);
            }
        }
    }

}

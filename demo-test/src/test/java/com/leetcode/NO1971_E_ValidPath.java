/**
 * copyright 2022/1/19
 */
package com.leetcode;

import org.junit.Test;

/**
    (简单)
    1971. 寻找图中是否存在路径
        有一个具有 n 个顶点的 双向 图，其中每个顶点标记从 0 到 n - 1（包含 0 和 n - 1）。
        图中的边用一个二维整数数组 edges 表示，其中 edges[i] = [ui, vi] 表示顶点 ui 和顶点 vi 之间的双向边。
        每个顶点对由 最多一条 边连接，并且没有顶点存在与自身相连的边。
        请你确定是否存在从顶点 source 开始，到顶点 destination 结束的 有效路径 。
        给你数组 edges 和整数 n、source 和 destination，如果从 source 到 destination 存在 有效路径 ，
        则返回 true，否则返回 false 。
    示例 1：
        输入：n = 3,  edges = {{0, 1}, {1, 2}, {2, 0}},  source = 0,  destination = 2
        输出：true
        解释：存在由顶点 0 到顶点 2 的路径:
            - 0 → 1 → 2
            - 0 → 2
    示例 2：
        输入：n = 6,  edges = {{0, 1}, {0, 2}, {3, 5}, {5, 4}, {4, 3}},  source = 0,  destination = 5
        输出：false
        解释：不存在由顶点 0 到顶点 5 的路径.
*/
public class NO1971_E_ValidPath {

    @Test
    public void test() {
        assert validPath(3, new int[][]{{0, 1}, {1, 2}, {2, 0}},0,2);
        assert !validPath(6, new int[][]{{0, 1}, {0, 2}, {3, 5}, {5, 4}, {4, 3}},0,5);
    }

    public boolean validPath(int n, int[][] edges, int source, int destination) {
        return false;
    }

}
















/**
// 方法1：
public boolean validPath(int n, int[][] edges, int source, int destination) {
    int[] root = new int[n];
    for (int i = 0 ; i < n; i++)
        root[i] = i;

    for (int[] edge : edges) {
        union(root, edge[0], edge[1]);
        if (find(root, source) == find(root, destination))
            return true;
    }
    return find(root, source) == find(root, destination);
}

private void union(int[] root, int p, int q) {
    root[find(root, p)] = find(root, q);
}

private int find(int[] root, int n) {
    return root[n] == n ? n : (root[n] = find(root, root[n]));
}

// 方法2：
public boolean validPath(int n, int[][] edges, int source, int destination) {
    List<Integer>[] arr = new List[n];
    for (int i = 0; i < n; i++)
        arr[i] = new ArrayList<>();

    for (int[] edge : edges) {
        arr[edge[0]].add(edge[1]);
        arr[edge[1]].add(edge[0]);
    }

    boolean[] visited = new boolean[n];
    visited[source] = true;
    Queue<Integer> queue = new LinkedList<>();
    queue.offer(source);
    while (!queue.isEmpty() && !visited[destination]) {
        int vertex = queue.poll();
        List<Integer> adjacent = arr[vertex];
        for (int next : adjacent)
            if (!visited[next]) {
                visited[next] = true;
                queue.offer(next);
            }
    }
    return visited[destination];
}
*/

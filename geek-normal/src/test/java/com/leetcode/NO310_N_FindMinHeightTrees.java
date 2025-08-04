package com.leetcode;

import org.junit.Test;
import java.util.*;
import static com.leetcode.util.MathUtils.getArray;
import static com.leetcode.util.SystemUtil.arrayAllMatch;

/**
    [ARRAY]
    (中等)
    NO.310 最小高度树
    树是一个无向图，其中任何两个顶点只通过一条路径连接。 换句话说，
    一个任何没有简单环路的连通图都是一棵树。
    给你一棵包含 n 个节点的树，标记为 0 到 n - 1 。给定数字 n 和一个有 n - 1
    条无向边的 edges 列表（每一个边都是一对标签），其中 edges[i] = [ai, bi]
    表示树中节点 ai 和 bi 之间存在一条无向边。
    可选择树中任何一个节点作为根。当选择节点 x 作为根节点时，设结果树的高度为 h 。
    在所有可能的树中，具有最小高度的树（即，min(h)）被称为 最小高度树 。
    请你找到所有的 最小高度树 并按 任意顺序 返回它们的根节点标签列表。
    树的 高度 是指根节点和叶子节点之间最长向下路径上边的数量。
    示例 1：
        输入：n = 4, edges = [[1, 0], [1, 2], [1, 3]]
        输出：[1]
        解释：如图所示，当根是标签为 1 的节点时，树的高度是 1 ，这是唯一的最小高度树。
    示例 2：
        输入：n = 6, edges = [[3, 0], [3, 1], [3, 2], [3, 4], [5, 4]]
        输出：[3, 4]
    提示：
        1 <= n <= 2 * 104
        edges.length == n - 1
        0 <= ai, bi < n
        ai != bi
        所有 (ai, bi) 互不相同
        给定的输入 保证 是一棵树，并且 不会有重复的边
    Related Topics:深度优先搜索,广度优先搜索,图,拓扑排序
*/
@SuppressWarnings("ALL")
public class NO310_N_FindMinHeightTrees {

    @Test
    public void test() {
        assert arrayAllMatch(getArray(1),
                findMinHeightTrees(4, new int[][]{{1, 0}, {1, 2}, {1, 3}}));
        assert arrayAllMatch(getArray(3, 4),
                findMinHeightTrees(6, new int[][]{{3, 0}, {3, 1}, {3, 2}, {3, 4}, {5, 4}}));
    }

    public List<Integer> findMinHeightTrees(int n, int[][] edges) {
        // 2024/3/18 NO.1
        List<Integer> ans = new ArrayList<>();
        return ans;
    }

}


















/*
// 方法1：
public List<Integer> findMinHeightTrees(int n, int[][] edges) {
    //graph 存储图；ans 存储答案；deque 用于广度优先遍历；redis 存储各点的度数
    List<Integer>[] graph = new ArrayList[n];
    List<Integer> ans = new ArrayList<>();
    Deque<Integer> deque = new LinkedList<>();

    int[] redis = new int[n];
    //排除n=1的情况
    if (n == 1) {
        ans.add(0);
        return ans;
    }

    //用于判断是否被遍历过的数组
    boolean[] judge = new boolean[n];
    //初始化图
    for (int i = 0; i < n; i++)
        graph[i] = new ArrayList<>();

    for (int[] elem : edges) {
        //初始化，并初始化redis数组
        graph[elem[0]].add(elem[1]);
        redis[elem[0]]++;
        graph[elem[1]].add(elem[0]);
        redis[elem[1]]++;
    }
    //先找出叶子结点
    for (int i = 0; i < n; i++) {
        if (redis[i] == 1) {
            deque.offer(i);
            judge[i] = true;
        }
    }
    //从叶子结点广度优先遍历
    while (!deque.isEmpty()) {
        //每次循环清除答案列表，答案列表中存储着同一深度（从叶子算起）的结点
        ans.clear();
        //size存储需要该层遍历的结点数
        int size = deque.size();
        for (int i = 0; i < size; i++) {
            //对每个结点广度优先遍历
            for (int elem : graph[deque.peek()]) {
                //先删去上一个结点（elem），即度数减1
                redis[elem]--;
                //如果该点未被遍历且度数为1（即为叶子结点）
                if (!judge[elem] && redis[elem] == 1) {
                    deque.offer(elem);
                    judge[elem] = true;
                }
            }
            //出队列，同时加入答案列表
            ans.add(deque.poll());
        }
    }
    //这里结束循环时，答案列表存储的就是深度最大的点了
    return ans;
}
*/
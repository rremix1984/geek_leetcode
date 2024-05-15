/**
 * copyright 2020-2025
 */
package com.lonch;

import com.lonch.util.MatrixNode;
import org.junit.Test;
import java.util.*;
import static com.lonch.util.MatrixNode.*;
import static java.lang.System.out;
import static java.util.Comparator.comparingInt;

/**
    [MATRIXLINKED] ||||||||||||||||||||
    (简单)
    NO.6 在上一步（NO.5）矩阵已经建好的基本上，
         任意给出两个节点，输出所有最短路径。
 */
@SuppressWarnings("all")
public class NO6_MatrixLinkedListShortestPath {

    @Test
    public void test() {
        MatrixNode<Integer> node = init(5);
        MatrixNode<Integer> start = node.pos(2);
        MatrixNode<Integer> end   = node.pos(5);
        print(node, start, end);
        List<List<MatrixNode<Integer>>> res = new ArrayList<>();
        // 2024/4/7   NO.1 没思路，但能看懂。
        // 2024/4/8   NO.2 有思路了，没写出来。
        // 2024/4/9   NO.3 一遍过，做了两遍都是一遍过
        // 2024/4/11、12、13、14、15、16、18
        //            NO.4、5、6、7、8、9、10 一遍过
        // 2024/4/30  NO.11 做错了，但是大致思路对，写的差不多，要化【定式】为【棋力】
        // 2024/5/6、7
        //            NO.12、13 一遍过
        // 2024/5/8   NO.14 Dijkstra算法看懂了，下次争取独立做出来
        // 2024/5/9、10
        //            NO.15 Dijkstra算法能独立做出来了、一遍过
        // 2024/5/11  NO.16 Dijkstra
        // 2024/5/12  NO.17 Dijkstra 一遍过
        // 2024/5/13  NO.18 Dijkstra 没有一遍过，但是思路对
        // 2024/5/14  NO.19 Dijkstra 做出来了，剪枝法也做出来了
        // TODO 方法1：剪枝法
        // List<List<MatrixNode<Integer>>> res = findShortestPaths(end, start);
        //
        // TODO 方法2：迪杰克斯拉算法
        // dijkstra(res, start, end);
        //
        print(res);
    }

}
















/*
// 方法1：剪枝法
public List<List<MatrixNode>> findShortestPaths(MatrixNode start, MatrixNode end) {
    List<List<MatrixNode>> res = new ArrayList<>();
    // 剪枝法
    dfs(res, new ArrayList<>(), start, end, new HashSet<>());
    return res;
}

// 递归搜索最短路径
private void dfs(List<List<MatrixNode>> res, List<MatrixNode> list,
                 MatrixNode start, MatrixNode end,
                 Set<MatrixNode> visited) {
    if (visited.contains(start))
        return;

    // 加入当前节点到路径中
    list.add(start);
    visited.add(start);

    // 如果当前节点是目标节点，且路径比已找到的最短路径短，则更新最短路径列表
    if (start == end) {
        if (res.isEmpty() || list.size() < res.get(0).size()) {
            res.clear();
            res.add(new ArrayList<>(list));
        } else if (list.size() == res.get(0).size())
            res.add(new ArrayList<>(list));
    } else {
        // 否则，继续搜索当前节点的相邻节点
        start.getNeighbors().forEach(
            cur -> dfs(res, list, cur, end, visited);
        );
    }

    // 回溯，移除当前节点，继续搜索其他可能的路径
    list.remove(start);
    visited.remove(start);
}

@Test
public void test() {
    MatrixNode<Character> mnode = init(5, 5);
    printMatrix(mnode);
    find(mnode.down.right.right,
        mnode.down.down.right.right.right).forEach(
        path -> {
            path.forEach(s -> out.print(s.val + " -> "));
            out.println();
        });
}

// 方法2：dijkstra算法
public void dijkstra(List<List<MatrixNode<Integer>>> res,
                     MatrixNode<Integer> start,
                     MatrixNode<Integer> end) {
    // 使用优先队列按距离排序
    // 建立一个队列queue，根据 MatrixNode 的 dist 值从小到大排序
    Queue<MatrixNode<Integer>> queue = new PriorityQueue<>(
        comparingInt(a -> a.dist)
    );

    // 当前节点（start）从【起始点】开始的最短路径长度
    start.dist = 0;

    // 将起始节点加入队列
    queue.offer(start);

    while (!queue.isEmpty()) {
        // 从队列中取出距离最小的节点（第一次一定是 start）
        MatrixNode<Integer> cur = queue.poll();

        if (cur.dist > end.dist) // 如果当前节点的距离已经大于终点节点的距离，停止搜索
            break;

        // 吾将上下左右而探索，
        // 更新当前节点的邻居节点，上下左右分别探索一轮，看queue 是否需要更新
        update(queue, cur, cur.right);
        update(queue, cur, cur.left);
        update(queue, cur, cur.up);
        update(queue, cur, cur.down);
    }
    // 从终点开始回溯，找到所有路径
    dfs(res, new LinkedList<>(), end);
}

private void update(Queue<MatrixNode<Integer>> queue,
                    MatrixNode<Integer> cur,
                    MatrixNode<Integer> next) {
    // 如果邻居节点不存在，直接返回
    if (next == null)
        return;

    int dist = cur.dist + next.val;
    // 如果新距离小于邻居节点当前的距离
    if (dist < next.dist) {
        next.dist = dist; // 更新邻居节点的距离
        next.prevs.clear(); // 清空邻居节点的前置节点列表
        next.prevs.add(cur); // 添加当前节点为邻居的前置节点

        // 将邻居节点加入队列
        queue.offer(next);

        // 如果新距离等于邻居节点的当前距离
    } else if (dist == next.dist) {
        // 添加当前节点为邻居的一个前置节点
        if (!next.prevs.contains(cur))
            next.prevs.add(cur);

        // 将邻居节点重新加入队列，确保能从多个路径到达
        queue.offer(next);
    }
}

private void dfs(List<List<MatrixNode<Integer>>> res,
                 LinkedList<MatrixNode<Integer>> list,
                 MatrixNode<Integer> node) {
    // 将当前节点添加到路径列表的前端
    list.addFirst(node);

    // 如果当前节点没有前置节点（即达到起点）
    if (node.prevs.isEmpty())
        // 将当前路径添加到结果集中
        res.add(new LinkedList<>(list));
    else
        // 否则继续回溯前置节点
        node.prevs.forEach(
                prev -> dfs(res, list, prev)
        );
    // 移除路径列表的当前节点，为回溯其他路径做准备
    list.removeFirst();
}
*/
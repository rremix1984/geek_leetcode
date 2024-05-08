/**
 * @copyright wxz
 */
package com.lonch;

import com.lonch.util.MatrixNode;
import org.junit.Test;
import java.util.*;
import static com.lonch.util.MatrixNode.*;
import static java.util.Comparator.comparingInt;

/**
 * 迪杰克斯拉算法
 */
public class DijkstraTest {

    @Test
    public void test() {
        MatrixNode<Integer> mnode = initIntRandom(7, 7);
        printMatrix(mnode);
        Set<List<MatrixNode<Integer>>> set
            = dijkstra(mnode, mnode.right.right.right.down.down.down);
        printAllPaths(set);
    }

    public Set<List<MatrixNode<Integer>>> dijkstra(MatrixNode<Integer> start,
                                                   MatrixNode<Integer> end) {
        // 存储所有找到的最短路径
        Set<List<MatrixNode<Integer>>> res = new HashSet<>();

        // 如果起点或终点为null，返回null
        if (start == null || end == null)
            return null;

        // 使用优先队列按距离排序
        Queue<MatrixNode<Integer>> queue = new PriorityQueue<>(
            comparingInt(a -> a.dist));

        // 起始节点的距离设为0
        start.dist = 0;

        // 起始节点没有前置节点
        start.prevs = new ArrayList<>();

        // 将起始节点加入队列
        queue.offer(start);

        while (!queue.isEmpty()) {
            // 从队列中取出距离最小的节点
            MatrixNode<Integer> cur = queue.poll();

            // 如果当前节点的距离已经大于终点节点的距离，停止搜索
            if (cur.dist > end.dist)
                break;

            // 更新当前节点的邻居节点
            update(queue, cur, cur.right);
            update(queue, cur, cur.left);
            update(queue, cur, cur.up);
            update(queue, cur, cur.down);
        }

        // 从终点开始回溯，找到所有路径
        dfs(res, new LinkedList<>(), end);

        // 返回所有最短路径集合
        return res;
    }

    private void update(Queue<MatrixNode<Integer>> queue,
                        MatrixNode<Integer> cur, MatrixNode<Integer> next) {
        // 如果邻居节点不存在，直接返回
        if (next == null)
            return;

        // 计算到邻居节点的新距离
        int distance = cur.dist + next.val;

        // 如果新距离小于邻居节点当前的距离
        if (distance < next.dist) {

            // 更新邻居节点的距离
            next.dist = distance;

            // 清空邻居节点的前置节点列表
            next.prevs.clear();

            // 添加当前节点为邻居的前置节点
            next.prevs.add(cur);

            // 将邻居节点加入队列
            queue.offer(next);
        // 如果新距离等于邻居节点的当前距离
        } else if (distance == next.dist) {

            // 添加当前节点为邻居的一个前置节点
            if (!next.prevs.contains(cur))
                next.prevs.add(cur);

            // 将邻居节点重新加入队列，确保能从多个路径到达
            queue.offer(next);
        }
    }

    private void dfs(Set<List<MatrixNode<Integer>>> res,
                     LinkedList<MatrixNode<Integer>> list, MatrixNode<Integer> node) {
        // 将当前节点添加到路径列表的前端
        list.addFirst(node);

        // 如果当前节点没有前置节点（即达到起点）
        if (node.prevs.isEmpty()) {

            // 如果已找到超过10条路径，则不再继续
            if (res.size() > 10)
                return;

            // 将当前路径添加到结果集中
            res.add(new ArrayList<>(list));
        } else {
            // 否则继续回溯前置节点
            node.prevs.forEach(
                prev -> dfs(res, list, prev)
            );
        }

        // 移除路径列表的当前节点，为回溯其他路径做准备
        list.removeFirst();
    }

}

















/*
public Set<List<MatrixNode<Integer>>> dijkstra(MatrixNode<Integer> start,
                                               MatrixNode<Integer> end) {
    if (start == null || end == null)
        return null;

    Queue<MatrixNode<Integer>> queue =
            new PriorityQueue<>(comparingInt(a -> a.dist));

    start.dist = 0;
    // 起始节点没有前置节点
    start.prevs = new ArrayList<>();
    queue.offer(start);

    while (!queue.isEmpty()) {
        MatrixNode<Integer> cur = queue.poll();

        // 如果当前节点的距离已经大于终点节点的距离，可以停止搜索
        if (cur.dist > end.dist)
            break;

        // 遍历所有可能的方向
        update(queue, cur, cur.right);
        update(queue, cur, cur.left);
        update(queue, cur, cur.up);
        update(queue, cur, cur.down);
    }

    // 输出所有路径
    // 使用 Set 来自动处理重复的路径
    Set<List<MatrixNode<Integer>>> res = new HashSet<>();
    // dijkstra算法最后需要从【尾部】向【头部】去查找到头部就停下来
    dfs(res, new LinkedList<>(), end);
    return res;
}

private void update(Queue<MatrixNode<Integer>> queue,
                    MatrixNode<Integer> cur,
                    MatrixNode<Integer> next) {
    if (next == null)
        return;

    int distance = cur.dist + next.val;

    if (distance < next.dist) {
        next.dist = distance;
        next.prevs.clear();
        next.prevs.add(cur);
        queue.offer(next);
    } else if (distance == next.dist) {
        if (!next.prevs.contains(cur))
            next.prevs.add(cur);
        queue.offer(next);
    }
}

private void dfs(Set<List<MatrixNode<Integer>>> res,
                 LinkedList<MatrixNode<Integer>> list,
                 MatrixNode<Integer> node) {
    list.addFirst(node);

    // 如果到达起点
    if (node.prevs.isEmpty())
        res.add(new ArrayList<>(list));
    else
        for (MatrixNode<Integer> prev : node.prevs)
            dfs(res, list, prev);

    list.removeFirst();
}
*/
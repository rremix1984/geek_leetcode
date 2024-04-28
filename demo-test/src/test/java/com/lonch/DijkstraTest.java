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
        MatrixNode<Integer> mnode = initIntRandom(5, 5);
        printMatrix(mnode);
        Set<List<MatrixNode<Integer>>> set = dijkstra(mnode, mnode.right.right.right.right.
                                down.down.down.down);
        printAllPaths(set);
    }

    public Set<List<MatrixNode<Integer>>> dijkstra(MatrixNode<Integer> start,
                                                   MatrixNode<Integer> end) {
        // 使用 Set 来自动处理重复的路径
        Set<List<MatrixNode<Integer>>> res = new HashSet<>();

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
//        Set<List<MatrixNode<Integer>>> res = new HashSet<>();
        // dijkstra算法最后需要从【尾部】向【头部】去查找到头部就停下来
        dfs(res, new LinkedList<>(), end);
        return res;
    }

    private void update(Queue<MatrixNode<Integer>> queue,
                        MatrixNode<Integer> cur,
                        MatrixNode<Integer> next) {
        if (next == null)
            return;

        // TODO
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
        // TODO
        list.addFirst(node);

        // 如果到达起点
        if (node.prevs.isEmpty()) {
            if (res.size() > 10)
                return;
            res.add(new ArrayList<>(list));
        } else
            for (MatrixNode<Integer> prev : node.prevs)
                dfs(res, list, prev);

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
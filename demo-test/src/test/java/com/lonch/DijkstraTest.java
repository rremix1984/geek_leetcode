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
        MatrixNode<Integer> mnode = initInt(5, 5);
        printMatrix(mnode);
        printAllPaths(dijkstraAllPaths(mnode, mnode.right.right.right.right));
    }

    public Set<List<MatrixNode<Integer>>> dijkstraAllPaths(MatrixNode<Integer> start, MatrixNode<Integer> end) {
        if (start == null || end == null)
            return null;

        PriorityQueue<MatrixNode<Integer>> pq = new PriorityQueue<>(comparingInt(a -> a.dist));
        start.dist = 0;
        start.prevs = new ArrayList<>(); // 起始节点没有前置节点
        pq.offer(start);

        while (!pq.isEmpty()) {
            MatrixNode<Integer> cur = pq.poll();

            // 如果当前节点的距离已经大于终点节点的距离，可以停止搜索
            if (cur.dist > end.dist)
                break;

            // 遍历所有可能的方向
            updateAndOffer(pq, cur, cur.right);
            updateAndOffer(pq, cur, cur.left);
            updateAndOffer(pq, cur, cur.up);
            updateAndOffer(pq, cur, cur.down);
        }

        // 输出所有路径
        Set<List<MatrixNode<Integer>>> allPaths = new HashSet<>(); // 使用 Set 来自动处理重复的路径
        dfs(new LinkedList<>(), allPaths, end);
        return allPaths;
    }

    private void updateAndOffer(PriorityQueue<MatrixNode<Integer>> pq,
                                       MatrixNode<Integer> currentNode,
                                       MatrixNode<Integer> nextNode) {
        if (nextNode == null) return;
        int newDist = currentNode.dist + nextNode.val;
        if (newDist < nextNode.dist) {
            nextNode.dist = newDist;
            nextNode.prevs.clear();
            nextNode.prevs.add(currentNode);
            pq.offer(nextNode);
        } else if (newDist == nextNode.dist) {
            if (!nextNode.prevs.contains(currentNode)) {
                nextNode.prevs.add(currentNode);
            }
            pq.offer(nextNode);
        }
    }

    private void dfs(LinkedList<MatrixNode<Integer>> path,
                                     Set<List<MatrixNode<Integer>>> allPaths,
                                     MatrixNode<Integer> node) {
        path.addFirst(node);
        if (node.prevs.isEmpty()) { // 如果到达起点
            allPaths.add(new ArrayList<>(path));
        } else {
            for (MatrixNode<Integer> prev : node.prevs)
                dfs(path, allPaths, prev);
        }
        path.removeFirst();
    }

}

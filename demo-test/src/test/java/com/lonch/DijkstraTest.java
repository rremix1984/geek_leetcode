/**
 * @copyright wxz
 */
package com.lonch;

import com.lonch.util.MatrixNode;
import org.junit.Test;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.LinkedList;
import java.util.Set;
import static com.lonch.util.MatrixNode.*;


public class DijkstraTest {

    @Test
    public void test() {
        MatrixNode<Integer> mnode = initInt(5, 5);
        printMatrix(mnode);
        dijkstraAllPaths(mnode, mnode.down.right.right.right);
    }

    public static void dijkstraAllPaths(MatrixNode<Integer> start, MatrixNode<Integer> end) {
        if (start == null || end == null) return;

        PriorityQueue<MatrixNode<Integer>> pq = new PriorityQueue<>((a, b) -> a.dist - b.dist);
        start.dist = 0;
        start.prevs = new ArrayList<>(); // 起始节点没有前置节点
        pq.offer(start);

        while (!pq.isEmpty()) {
            MatrixNode<Integer> cur = pq.poll();

            // 如果当前节点的距离已经大于终点节点的距离，可以停止搜索
            if (cur.dist > end.dist) {
                break;
            }

            // 遍历所有可能的方向
            updateAndOffer(pq, cur, cur.right);
            updateAndOffer(pq, cur, cur.left);
            updateAndOffer(pq, cur, cur.up);
            updateAndOffer(pq, cur, cur.down);
        }

        // 输出所有路径
        Set<List<MatrixNode<Integer>>> allPaths = new HashSet<>(); // 使用 Set 来自动处理重复的路径
        findAllPaths(new LinkedList<>(), allPaths, end);
        printAllPaths(allPaths);
    }

    private static void updateAndOffer(PriorityQueue<MatrixNode<Integer>> pq,
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

    private static void findAllPaths(LinkedList<MatrixNode<Integer>> path,
                                     Set<List<MatrixNode<Integer>>> allPaths,
                                     MatrixNode<Integer> node) {
        path.addFirst(node);
        if (node.prevs.isEmpty()) { // 如果到达起点
            allPaths.add(new ArrayList<>(path));
        } else {
            for (MatrixNode<Integer> prev : node.prevs) {
                findAllPaths(path, allPaths, prev);
            }
        }
        path.removeFirst();
    }

    private static void printAllPaths(Set<List<MatrixNode<Integer>>> allPaths) {
        System.out.println("All paths from start to end:");
        for (List<MatrixNode<Integer>> path : allPaths) {
            for (MatrixNode<Integer> node : path) {
                System.out.print("(" + node.val + ") -> ");
            }
            System.out.println("end");
        }
    }

}

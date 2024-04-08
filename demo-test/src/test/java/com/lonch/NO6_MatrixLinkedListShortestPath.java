/**
 * copyright 2020-2025
 */
package com.lonch;

import com.lonch.util.MatrixNode;
import org.junit.Test;
import java.util.*;
import static com.lonch.NO5_MatrixLinkedList.init;
import static com.lonch.util.MatrixNode.printMatrix;

/**
 * NO.10 在上一步（NO5）矩阵已经建好的基本上，任意给出两个节点，输出所有最短路径
 */
public class NO6_MatrixLinkedListShortestPath {

    @Test
    public void test() {
        MatrixNode mnode = init(5, 5);
        printMatrix(mnode);
        MatrixNode s, t;
        s = mnode.right.right.right;
        t = mnode.down.right.right;

        // 找到所有可能的最短路径
        // 打印所有最短路径
        findShortestPaths(s, t).forEach(path -> {
            path.forEach(node -> System.out.print(node.val + " -> "));
            System.out.println();
        });
    }

    // 搜索所有可能的最短路径
    public List<List<MatrixNode>> findShortestPaths(MatrixNode start, MatrixNode end) {
        // NO.1 没思路，但能看懂。4月18日 20:00 面试
        List<List<MatrixNode>> res = new ArrayList<>();
        // 剪枝法
        dfs(res, new ArrayList<>(), start, end, new HashSet<>());
        return res;
    }

    // 递归搜索最短路径
    private void dfs(List<List<MatrixNode>> res, List<MatrixNode> list,
                     MatrixNode start, MatrixNode end,
                     Set<MatrixNode> visited) {
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
                cur -> {
                    if (!visited.contains(cur))
                        dfs(res, list, cur, end, visited);
                }
            );
        }

        // 回溯，移除当前节点，继续搜索其他可能的路径
        list.remove(start);
        visited.remove(start);
    }

}
















/*
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
            cur -> {
                if (!visited.contains(cur))
                    dfs(res, list, cur, end, visited);
            }
        );
    }

    // 回溯，移除当前节点，继续搜索其他可能的路径
    list.remove(start);
    visited.remove(start);
}
*/
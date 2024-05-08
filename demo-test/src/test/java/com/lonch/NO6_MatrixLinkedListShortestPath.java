/**
 * copyright 2020-2025
 */
package com.lonch;

import com.lonch.util.MatrixNode;
import lombok.val;
import org.junit.Test;
import java.util.*;

import static com.lonch.util.MatrixNode.*;
import static java.lang.System.out;

/**
    [MATRIXLINKED] |||||||||||||
    (简单)
    NO.6 在上一步（NO5）矩阵已经建好的基本上，任意给出两个节点，输出所有最短路径
 */
@SuppressWarnings("all")
public class NO6_MatrixLinkedListShortestPath {

    @Test
    public void test() {
        MatrixNode<Character> mnode = init(7, 7);
        printMatrix(mnode);
        // 2024/4/7   NO.1 没思路，但能看懂。
        // 2024/4/8   NO.2 有思路了，没写出来。
        // 2024/4/9   NO.3 一遍过，做了两遍都是一遍过
        // 2024/4/11-12-13-14-15-16-18
        //            NO.4、5、6、7、8、9、10 一遍过
        // 2024/4/30  NO.11 做错了，但是大致思路对，写的差不多，要化【定式】为【棋力】
        // 2024/5/6-7 NO.12-13 一遍过
        List<List<MatrixNode>> res = findShortestPaths(mnode,
                mnode.right.right.right.down.down.down);
        res.forEach(
            cur -> {
                cur.forEach(
                    p -> out.printf("%s -> ", p.val)
                );
                out.println();
            }
        );
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
            path.forEach(
                s -> out.print(s.val + " -> ")
            );
            out.println();
        });
}
*/
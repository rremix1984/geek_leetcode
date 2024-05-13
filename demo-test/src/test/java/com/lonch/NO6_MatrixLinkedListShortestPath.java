/**
 * copyright 2020-2025
 */
package com.lonch;

import com.lonch.util.MatrixNode;
import lombok.val;
import org.junit.Test;
import java.util.*;
import static com.lonch.util.MatrixNode.*;
import static java.lang.System.arraycopy;
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
        MatrixNode<Integer> start = initI(6);
        MatrixNode<Integer> end   = start.pos(6);
        print(start, start, end);
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
        // TODO 方法1：剪枝法
        // List<List<MatrixNode<Character>>> res = findShortestPaths(start, end);
        //
        // TODO 方法2：迪杰克斯拉算法
        // dijkstra(res, start, end);
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
*/
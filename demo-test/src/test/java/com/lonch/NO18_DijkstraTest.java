/**
@copyright wxz
 */
package com.lonch;

import com.lonch.util.MatrixNode;
import lombok.val;
import org.junit.Test;
import java.util.*;
import static com.lonch.util.MatrixNode.*;

/**
    [ARRAY] |
    [困难]
    NO.18 迪杰克斯拉算法（Dijkstra）

    1. 准备出发
    你把起点标记为距离 0（因为你已经在那里了），然后开始探索四周。这就好比设置 start.dist = 0。

    2. 探索四周
    你开始向四周看，看每一步走去会加多少距离（比如走到右边的格子加 1，上面的格子加 2）。
    这些数字代表了你额外需要走的步数。在这个过程中，你会优先选择那些步数最少的方向，这就是优先队列
    （优先处理距离短的节点）在起作用。

    3. 更新地图
    每当你考虑走向一个新的位置时，如果通过你当前的路径到那里的总步数比之前记录的要少，
    你就更新那个位置上的数字，并记下你是从哪里来的。这就是检查和更新 dist 的过程。

    4. 发现更多选择
    如果你发现有另一条路可以到达某个位置，并且总步数与之前最短的路径相同，你会记下这也是一种可能。
    这样你就不会错过任何一条可能的最短路径。

    5. 回溯寻找路径
    当你到达终点后，你不只满足于到达那里，还要找出所有可能的最短路线。你开始回溯，沿着你来时的路逆
    向走回去，记录下来每一步，直到回到起点。这就是 dfs 方法在做的事情。

    6. 完成探索
    最后，你会得到一个包含所有可能最短路径的集合。这就像是你探索后在地图上画出所有能最快到达终点的路线。
                          2
               ① -----------------> ④
              / \                  /  \
           1 /   \ 1             / 4   \  3
            /     \     5       /       \
start ---> 〇      ③ -------> ⑥ ------> ⑦ ----> ⑧ end
            \    /             \   5    /
           8 \  /  5          5 \     / 7
              \/                 \  /
              ② ---------------> ⑤
                        2
    PriorityQueue queue; // 优先列表
     ____________      ____________      ____________      ____________      ____________
    |_1_|___0___|     |_1_|___1___|     |_1_|___2___|     |_1_|___4___|     |_1_|__14___|
    |_2_|_99999_|     |_2_|___1___|     |_2_|___2___|     |_2_|___5___|     |_2_|__15___|
    |_3_|_99999_|  => |_3_|_99999_|  => |_3_|___1___| =>  |_3_|___6___| =>  |_3_|__16___|
    |_4_|_99999_|     |_4_|_99999_|     |_4_|_99999_|     |_4_|___3___|     |_4_|___9___|
    |_5_|_99999_|     |_5_|_99999_|     |_5_|_99999_|     |_5_|_99999_|     |_4_|___7___|
        第1步             第2步              第3步                第4步            第5步
 */
@SuppressWarnings("all")
public class NO18_DijkstraTest {

    @Test
    public void test() {
        MatrixNode<Integer> start = initInt(10, 10);
        printMatrix(start);
        Set<List<MatrixNode<Integer>>> res = new HashSet<>();  // 存储所有找到的最短路径
        MatrixNode<Integer> end = start.right.right.right.down.down.down;
        // TODO
        dijkstra(res, start, end);
        printAllPaths(res);

        // 2024/5/8 NO.1 终于看懂了，能做出来了
        //
    }

    private void dijkstra(Set<List<MatrixNode<Integer>>> res,
                          MatrixNode<Integer> start, MatrixNode<Integer> end) {
        // 1. 初始化
        Queue<MatrixNode<Integer>> queue = new PriorityQueue<>(Comparator.comparingInt(a->a.dist));
        start.dist = 0;
        queue.offer(start);

        // 2. 更新
        while (!queue.isEmpty()) {
            MatrixNode<Integer> cur = queue.poll();
            if (cur.dist > end.dist)
                break;

            update(queue, cur, cur.up);
            update(queue, cur, cur.down);
            update(queue, cur, cur.left);
            update(queue, cur, cur.right);
        }

        // 3. 遍历
        dfs(res, new LinkedList<>(), end);
    }

    private void update(Queue<MatrixNode<Integer>> queue,
                        MatrixNode<Integer> cur, MatrixNode<Integer> next) {
        if (next == null)
            return;

        int dist = cur.dist + 1;
        if (dist < next.dist) {
            next.dist = dist;
            next.prevs.clear();
            next.prevs.add(cur);
            queue.offer(next);
        } else if (dist == next.dist) {
            if (!next.prevs.contains(cur))
                next.prevs.add(cur);
            queue.offer(next);
        }
    }

    private void dfs(Set<List<MatrixNode<Integer>>> res,
                     LinkedList<MatrixNode<Integer>> list, MatrixNode<Integer> end) {
        list.addFirst(end);
        if (end.prevs.isEmpty())
            res.add(new LinkedList<>(list));
        else
            end.prevs.forEach(cur -> dfs(res, list, cur));
        list.removeFirst();
    }

}

















/*
public Set<List<MatrixNode<Integer>>> dijkstra(MatrixNode<Integer> start,
                                               MatrixNode<Integer> end) {
    if (start == null || end == null)
        return null;

    // 建立一个队列queue，根据 MatrixNode 的 dist 值从小到大排序
    Queue<MatrixNode<Integer>> queue =
            new PriorityQueue<>(comparingInt(a -> a.dist));

    // 因为默认值是 MAX_VALUE，所以对于start节点，必须把这个值赋值为：0
    start.dist = 0;
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

// 方法2：
public void dijkstra(Set<List<MatrixNode<Integer>>> res,
                     MatrixNode<Integer> start,
                     MatrixNode<Integer> end) {
    // 使用优先队列按距离排序
    // 建立一个队列queue，根据 MatrixNode 的 dist 值从小到大排序
    Queue<MatrixNode<Integer>> queue = new PriorityQueue<>(
            comparingInt(a -> a.dist)
    );

    // 当前节点（start）从【起始点】开始的最短路径长度
    start.dist = 0;

    // 前置节点（起始节点没有前置节点）
    start.prevs = new ArrayList<>();

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

    // 如果新距离小于邻居节点当前的距离
    if (cur.dist + next.val < next.dist) {
        next.dist = cur.dist + next.val; // 更新邻居节点的距离
        next.prevs.clear(); // 清空邻居节点的前置节点列表
        next.prevs.add(cur); // 添加当前节点为邻居的前置节点

        // 将邻居节点加入队列
        queue.offer(next);

        // 如果新距离等于邻居节点的当前距离
    } else if (cur.dist + next.val == next.dist) {
        // 添加当前节点为邻居的一个前置节点
        if (!next.prevs.contains(cur))
            next.prevs.add(cur);

        // 将邻居节点重新加入队列，确保能从多个路径到达
        queue.offer(next);
    }
}

private void dfs(Set<List<MatrixNode<Integer>>> res,
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
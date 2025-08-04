/**
 * copyright 2019-2020
 * 沈家乐
 */
package com.leetcode;

import org.junit.Test;

/**
    [ARRAY]
    (中等)
    NO.1654 到家的最少跳跃次数
    有一只跳蚤的家在数轴上的位置 x 处。请你帮助它从位置 0 出发，到达它的家。
    跳蚤跳跃的规则如下：
     1）它可以 往前 跳恰好 a 个位置（即往右跳）。
     2）它可以 往后 跳恰好 b 个位置（即往左跳）。
     3）它不能 连续 往后跳 2 次。
     4）它不能跳到任何 forbidden 数组中的位置。
    跳蚤可以往前跳 超过 它的家的位置，但是它 不能跳到负整数 的位置。
    给你一个整数数组 forbidden ，其中 forbidden[i] 是跳蚤不能跳到的位置，同时给你整数 a， b 和 x ，请你返回跳蚤到家的最少跳跃次数。如果没有恰好到达 x 的可行方案，请你返回 -1 。
    示例 1：
        输入：forbidden = [14,4,18,1,15], a = 3, b = 15, x = 9
        输出：3
        解释：往前跳 3 次（0 -> 3 -> 6 -> 9），跳蚤就到家了。
    示例 2：
        输入：forbidden = [8,3,16,6,12,20], a = 15, b = 13, x = 11
        输出：-1
    示例 3：
        输入：forbidden = [1,6,2,14,5,17,4], a = 16, b = 9, x = 7
        输出：2
        解释：往前跳一次（0 -> 16），然后往回跳一次（16 -> 7），跳蚤就到家了。
    提示：
        1 <= forbidden.length <= 1000
        1 <= a, b, forbidden[i] <= 2000
        0 <= x <= 2000
        forbidden 中所有位置互不相同。
        位置 x 不在 forbidden 中。
    Related Topics:广度优先搜索,数组,动态规划
*/
public class NO1654_N_MinimumJumps {

    @Test
    public void test() {
        assert 3 == minimumJumps(
                new int[]{14,4,18,1,15}, 3, 15, 9);
        assert -1 == minimumJumps(
                new int[]{8,3,16,6,12,20}, 15, 13, 11);
        assert 2 == minimumJumps(
                new int[]{1,6,2,14,5,17,4}, 16, 9, 7);
    }

    public int minimumJumps(int[] forbidden, int a, int b, int x) {
        // 它可以 往前 跳恰好 a个位置（即往右跳）。
        // 它可以 往后跳恰好 b个位置（即往左跳）。
        // 它不能 连续 往后跳 2 次。
        // 它不能跳到任何forbidden数组中的位置。
        // 跳蚤可以往前跳 超过 它的家的位置，但是它 不能跳到负整数 的位置。

        return -1;
    }

}



















/*
// 方法1：
public int minimumJumps(int[] forbidden, int a, int b, int x) {
    // 它可以 往前 跳恰好 a个位置（即往右跳）。
    // 它可以 往后跳恰好 b个位置（即往左跳）。
    // 它不能 连续 往后跳 2 次。
    // 它不能跳到任何forbidden数组中的位置。
    // 跳蚤可以往前跳 超过 它的家的位置，但是它 不能跳到负整数 的位置。
    final int LIMIT = 8000;
    boolean[][] visited = new boolean[8001][2];
    Set<Integer> forbid = new HashSet<>(forbidden.length);
    for (int i : forbidden)
        forbid.add(i);

    Deque<int[]> q = new LinkedList<>(); // [ 当前位置, 向后跳次数 ]
    q.offer(new int[]{0, 0});
    int layer = -1;
    while (!q.isEmpty()) {
        int qs = q.size();
        layer++;
        for (int i = 0; i < qs; i++) {
            int[] p = q.poll();
            int cur = 0;
            if (p != null) {
                cur = p[0];
            }

            int backwardCount = 0;
            if (p != null) {
                backwardCount = p[1];
            }

            if (cur == x)
                return layer;

            if (visited[cur][backwardCount])
                continue;

            visited[cur][backwardCount] = true;

            if (cur + a <= LIMIT && !forbid.contains(cur + a))
                q.offer(new int[]{cur + a, 0});

            if (cur - b >= 0 && backwardCount < 1 && !forbid.contains(cur - b))
                q.offer(new int[]{cur - b, backwardCount + 1});
        }
    }
    return -1;
}
*/
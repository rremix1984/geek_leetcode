/**
 * copyright 2022/1/19
 */
package com.leetcode.undo;

import org.junit.Test;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
    (简单)
    886. 可能的二分法
        给定一组 n 人（编号为 1, 2, ..., n）， 我们想把每个人分进任意大小的两组。
        每个人都可能不喜欢其他人，那么他们不应该属于同一组。
        给定整数 n 和数组 dislikes ，其中 dislikes[i] = [ai, bi] ，表示不允许将编号为 ai 和
        bi的人归入同一组。当可以用这种方法将所有人分进两组时，返回 true；否则返回 false。
    示例 1：
        输入：n = 4,  dislikes = {{1, 2}, {1, 3}, {2, 4}}
        输出：true
        解释：group1 {1, 4},  group2 {2, 3}
    示例 2：
        输入：n = 3,  dislikes = {{1, 2}, {1, 3}, {2, 3}}
        输出：false
    示例 3：
        输入：n = 5,  dislikes = {{1, 2}, {2, 3}, {3, 4}, {4, 5}, {1, 5}}
        输出：false
*/
public class NO886_N_PossibleBipartition {

    @Test
    public void test() {
        assert !possibleBipartition(4, new int[][]{{1, 2}, {1, 3}, {2, 4}});
        assert !possibleBipartition(3, new int[][]{{1, 2}, {1, 3}, {2, 3}});
        assert !possibleBipartition(5, new int[][]{{1, 2}, {2, 3}, {3, 4}, {4, 5}, {1, 5}});
    }

    public boolean possibleBipartition(int n, int[][] dislikes) {
        int[] color = new int[n + 1];
        List<Integer>[] g = new List[n + 1];
        for (int i = 0; i <= n; ++i)
            g[i] = new ArrayList<>();

        for (int[] p : dislikes) {
            g[p[0]].add(p[1]);
            g[p[1]].add(p[0]);
        }

        for (int i = 1; i <= n; ++i) {
            if (color[i] == 0) {
                Queue<Integer> queue = new ArrayDeque<Integer>();
                queue.offer(i);
                color[i] = 1;
                while (!queue.isEmpty()) {
                    int t = queue.poll();
                    for (int next : g[t]) {
                        if (color[next] > 0 && color[next] == color[t]) {
                            return false;
                        }
                        if (color[next] == 0) {
                            color[next] = 3 ^ color[t];
                            queue.offer(next);
                        }
                    }
                }
            }
        }
        return true;
    }

}
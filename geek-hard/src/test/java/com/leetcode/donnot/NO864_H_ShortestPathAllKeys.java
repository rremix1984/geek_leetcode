/**
 * copyright 2022/1/19
 */
package com.leetcode.donnot;

import org.junit.Test;
import java.util.*;

import static java.lang.String.valueOf;

/**
    (困难)
    864. 获取所有钥匙的最短路径
        给定一个二维网格 grid ，其中：
            '.' 代表一个空房间
            '#' 代表一堵
            '@' 是起点
            小写字母代表钥匙
            大写字母代表锁
        我们从起点开始出发，一次移动是指向四个基本方向之一行走一个单位空间。
        我们不能在网格外面行走，也无法穿过一堵墙。如果途经一个钥匙，我们就把它捡起来。
        除非我们手里有对应的钥匙，否则无法通过锁。
        假设 k 为 钥匙/锁 的个数，且满足 1 <= k <= 6，字母表中的前 k 个字母在网
        格中都有自己对应的一个小写和一个大写字母。换言之，每个锁有唯一对应的钥匙，
        每个钥匙也有唯一对应的锁。另外，代表钥匙和锁的字母互为大小写并按字母顺序排列。
        返回获取所有钥匙所需要的移动的最少次数。如果无法获取所有钥匙，返回 -1 。
    示例 1：
        输入：grid = ["@.a.#","###.#","b.A.B"]
        输出：8
        解释：目标是获得所有钥匙，而不是打开所有锁。
    示例 2：
        输入：grid = ["@..aA","..B#.","....b"]
        输出：6
    示例 3:
        输入: grid = ["@Aa"]
        输出: -1
    提示：
        m == grid.length
        n == grid[i].length
        1 <= m, n <= 30
        grid[i][j] 只含有 '.', '#', '@', 'a'-'f' 以及 'A'-'F'
        钥匙的数目范围是 [1, 6]
        每个钥匙都对应一个 不同 的字母
        每个钥匙正好打开一个对应的锁
*/
public class NO864_H_ShortestPathAllKeys {

    @Test
    public void test() {
        assert 8 == shortestPathAllKeys(
                new String[]{"@.a.#",
                             "###.#",
                             "b.A.B"});
        assert 6 == shortestPathAllKeys(
                new String[]{valueOf(new char[]{'@', '.', '.', 'a', 'A'}),
                             valueOf(new char[]{'.', '.', 'B', '#', '.'}),
                             valueOf(new char[]{'.', '.', '.', '.', 'b'})});
        assert -1 == shortestPathAllKeys(
                new String[]{"@Aa"});
    }

    static int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    public int shortestPathAllKeys(String[] grid) {
        int m = grid.length;
        int n = grid[0].length();
        int sx = 0;
        int sy = 0;
        Map<Character, Integer> keyToIndex = new HashMap<>();
        for (int i = 0; i < m; ++i)
            for (int j = 0; j < n; ++j)
                if (grid[i].charAt(j) == '@') {
                    sx = i;
                    sy = j;
                } else if (Character.isLowerCase(grid[i].charAt(j))) {
                    if (!keyToIndex.containsKey(grid[i].charAt(j))) {
                        int idx = keyToIndex.size();
                        keyToIndex.put(grid[i].charAt(j), idx);
                    }
                }

        Queue<int[]> queue = new ArrayDeque<>();
        int[][][] dist = new int[m][n][1 << keyToIndex.size()];
        for (int i = 0; i < m; ++i)
            for (int j = 0; j < n; ++j)
                Arrays.fill(dist[i][j], -1);

        queue.offer(new int[]{sx, sy, 0});
        dist[sx][sy][0] = 0;
        while (!queue.isEmpty()) {
            int[] arr = queue.poll();
            int x = arr[0];
            int y = arr[1];
            int mask = arr[2];
            for (int i = 0; i < 4; ++i) {
                int nx = x + dirs[i][0];
                int ny = y + dirs[i][1];
                if (nx >= 0 && nx < m && ny >= 0 && ny < n && grid[nx].charAt(ny) != '#')
                    if (grid[nx].charAt(ny) == '.' || grid[nx].charAt(ny) == '@') {
                        if (dist[nx][ny][mask] == -1) {
                            dist[nx][ny][mask] = dist[x][y][mask] + 1;
                            queue.offer(new int[]{nx, ny, mask});
                        }
                    } else if (Character.isLowerCase(grid[nx].charAt(ny))) {
                        int idx = keyToIndex.get(grid[nx].charAt(ny));
                        if (dist[nx][ny][mask | (1 << idx)] == -1) {
                            dist[nx][ny][mask | (1 << idx)] = dist[x][y][mask] + 1;
                            if ((mask | (1 << idx)) == (1 << keyToIndex.size()) - 1)
                                return dist[nx][ny][mask | (1 << idx)];

                            queue.offer(new int[]{nx, ny, mask | (1 << idx)});
                        }
                    } else {
                        int idx = keyToIndex.get(Character.toLowerCase(grid[nx].charAt(ny)));
                        if ((mask & (1 << idx)) != 0 && dist[nx][ny][mask] == -1) {
                            dist[nx][ny][mask] = dist[x][y][mask] + 1;
                            queue.offer(new int[]{nx, ny, mask});
                        }
                    }
            }
        }
        return -1;
    }

}
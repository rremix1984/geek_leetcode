/**
 * copyright 2022/1/19
 */
package com.leetcode.undo;

import org.junit.Test;
import static com.leetcode.util.LogUtil.info;

/**
    NO547 Friend Circles 朋友圈

        There are N students in a class. Some of them are friends, while some are not.
    Their friendship is transitive in nature. For example, if A is a direct friend
    of B, and B is a direct friend of C, then A is an indirect friend of C. And we
    defined a friend circle is a group of students who are direct or indirect friends.

    Given a N*N matrix M representing the friend relationship between students in the
    class. If M[i][j] = 1, then the ithand jth students are direct friends with each other,
    otherwise not. And you have to output the total number of friend circles among all the students.

    这道题让我们求朋友圈的个数，题目中对于朋友圈的定义是可以传递的，比如A和B是好友，B和C是好友，
    那么即使A和C不是好友，那么他们三人也属于一个朋友圈。那么比较直接的解法就是 DFS 搜索，
    对于某个人，遍历其好友，然后再遍历其好友的好友，那么就能把属于同一个朋友圈的人都遍历一遍，
    同时标记出已经遍历过的人，然后累积朋友圈的个数，再去对于没有遍历到的人在找其朋友圈的人，
    这样就能求出个数。其实这道题的本质是之前那道题 Number of Connected Components in
    an Undirected Graph，其实许多题目的本质都是一样的，就是看我们有没有一双慧眼能把它们
    识别出来：

    Example 1:
        Input:
            [[1,1,0],
            [1,1,0],
            [0,0,1]]
        Output: 2
            Explanation:The 0th and 1st students are direct friends, so they are in a friend circle.
            The 2nd student himself is in a friend circle. So return 2.

    Example 2:
        Input:
            [[1,1,0],
            [1,1,1],
            [0,1,1]]
        Output: 1
            Explanation:The 0th and 1st students are direct friends, the 1st and 2nd students are direct friends,
            so the 0th and 2nd students are indirect friends. All of them are in the same friend circle, so return 1.
*/
public class NO547_FriendCircles {

    @Test
    public void test() {
        info(findCircleNum(new int[][]{
                {1, 1, 0},
                {1, 1, 1},
                {0, 1, 1}}));// 1
    }

    // 方法1：dfs深度优先遍历
    // 方法2：并查集
    public int findCircleNum(int[][] M) {
        int n = M.length;
        UnionFind uf = new UnionFind(n);
        for (int i = 0; i < n - 1; i++)
            for (int j = i + 1; j < n; j++)
                if (M[i][j] == 1)
                    uf.union(i, j);
        return uf.count;
    }

    class UnionFind{
        private int count = 0;
        private int[] parent, rank;

        public UnionFind(int n) {
            count = n;
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++)
                parent[i] = i;
        }

        public int find(int p) {
            while (p != parent[p]) {
                parent[p] = parent[parent[p]];
                p = parent[p];
            }
            return p;
        }

        public void union(int p, int q) {
            int rootP = find(p);
            int rootQ = find(q);
            if (rootP == rootQ)
                return;
            if (rank[rootQ] > rank[rootP]) {
                parent[rootP] = rootQ;
            } else {
                parent[rootQ] = rootP;
                if (rank[rootP] == rank[rootQ])
                    rank[rootP]++;
            }
            count--;
        }

        public int count() {
            return count;
        }
    }
}















/**
// 方法1：dfs深度优先遍历
public int findCircleNum(int[][] M) {
    int[] visited = new int[M.length];
    int count = 0;
    for (int i = 0; i < M.length; i++) {
        if (visited[i] == 0) {
            dfs(M, visited, i);
            count++;
        }
    }
    return count;
}

public void dfs(int[][] M, int[] visited, int i) {
    for (int j = 0; j < M.length; j++) {
        if (M[i][j] == 1 && visited[j] == 0) {
            visited[j] = 1;
            dfs(M, visited, j);
        }
    }
}

// 方法2：并查集
class UnionFind{
    private int count = 0;
    private int[] parent, rank;

    public UnionFind(int n) {
        count = n;
        parent = new int[n];
        rank = new int[n];
        for (int i=0; i<n; i++)
            parent[i] = i;
    }

    public int find(int p) {
        while (p != parent[p]) {
            parent[p] = parent[parent[p]];
            p = parent[p];
        }
        return p;
    }

    public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ)
            return;
        if (rank[rootQ] > rank[P]) {
            parent[rootP] = rootQ;
        } else {
            parent[rootQ] = rootP;
            if (rank[rootP] == rank[rootQ])
                rank[rootP]++;
        }
        count--;
    }

    public int count() {
        return count;
    }
}

public int findCircleNum(int[][] M) {
    int n = M.length;
    UnionFind uf = new UnionFind(n);
    for (int i = 0; i < n - 1; i++)
        for (int j = i + 1; j < n; j++)
            if (M[i][j] == 1)
                uf.union(i, j);
    return uf.count;
}
*/
/**
 * copyright 2022/1/19
 */
package com.leetcode.undo;

import org.junit.Test;
import java.util.*;

/**
    (中等)
    207. 课程表
        你这个学期必须选修 numCourses 门课程，记为 0 到 numCourses - 1 。
        在选修某些课程之前需要一些先修课程。 先修课程按数组 prerequisites 给出，
        其中 prerequisites[i] = [ai, bi] ，表示如果要学习课程 ai 则 必须 先学习课程  bi 。
        例如，先修课程对 [0, 1] 表示：想要学习课程 0 ，你需要先完成课程 1 。
        请你判断是否可能完成所有课程的学习？如果可以，返回 true ；否则，返回 false 。
    示例 1：
        输入：numCourses = 2, prerequisites = [[1,0]]
        输出：true
        解释：总共有 2 门课程。学习课程 1 之前，你需要完成课程 0 。这是可能的。
    示例 2：
        输入：numCourses = 2, prerequisites = [[1,0],[0,1]]
        输出：false
        解释：总共有 2 门课程。学习课程 1 之前，你需要先完成​课程 0 ；并且学习课程 0 之前，你还应先完成课程 1 。这是不可能的。
    提示：
        1 <= numCourses <= 105
        0 <= prerequisites.length <= 5000
        prerequisites[i].length == 2
        0 <= ai, bi < numCourses
        prerequisites[i] 中的所有课程对 互不相同

    方法一：入度表（广度优先遍历）
        统计课程安排图中每个节点的入度，生成 入度表 indegrees。
    借助一个队列 queue，将所有入度为 0 的节点入队。
    当 queue 非空时，依次将队首节点出队，在课程安排图中删除此节点 pre：
    并不是真正从邻接表中删除此节点 pre，而是将此节点对应所有邻接节点 cur 的入度 −1，即 indegrees[cur] -= 1。
    当入度 -1 后邻接节点 cur 的入度为 0，说明 cur 所有的前驱节点已经被 “删除”，此时将 cur 入队。
    在每次 pre 出队时，执行 numCourses--；
    若整个课程安排图是有向无环图（即可以安排），则所有节点一定都入队并出队过，
    即完成拓扑排序。换个角度说，若课程安排图中存在环，一定有节点的入度始终不为 0。
    因此，拓扑排序出队次数等于课程个数，返回 numCourses == 0 判断课程是否可以成功安排。
*/
public class NO207_N_CanFinish {
    public void test() {
        assert canFinish(2, new int[][]{{1,0}});
        assert !canFinish(2, new int[][]{{1,0}, {0,1}});
    }

    public boolean canFinish(int numCourses, int[][] prerequisites) {
        int[] indegrees = new int[numCourses];
        List<List<Integer>> adjacency = new ArrayList<>();
        Queue<Integer> queue = new LinkedList<>();
        for(int i = 0; i < numCourses; i++)
            adjacency.add(new ArrayList<>());

        // Get the indegree and adjacency of every course.
        for(int[] cp : prerequisites) {
            indegrees[cp[0]]++;
            adjacency.get(cp[1]).add(cp[0]);
        }

        // Get all the courses with the indegree of 0.
        for(int i = 0; i < numCourses; i++)
            if(indegrees[i] == 0)
                queue.add(i);

        // BFS TopSort.
        while(!queue.isEmpty()) {
            int pre = queue.poll();
            numCourses--;
            for(int cur : adjacency.get(pre))
                if(--indegrees[cur] == 0)
                    queue.add(cur);
        }

        return numCourses == 0;
    }

}

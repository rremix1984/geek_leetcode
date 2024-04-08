package com.leetcode.normal;

import org.junit.Test;
import java.util.Arrays;
import java.util.Comparator;
import static org.junit.Assert.assertArrayEquals;

/**
    [ARRAY] |
    (简单)
    NO.436 寻找右区间
    给你一个区间数组 intervals ，其中 intervals[i] = [starti, endi] ，
    且每个 starti 都 不同 。区间 i 的 右侧区间 可以记作区间 j ，并满足 startj >= endi ，
    且 startj 最小化 。注意 i 可能等于 j 。返回一个由每个区间 i 的 右侧区间 在 intervals
    中对应下标组成的数组。如果某个区间 i 不存在对应的 右侧区间 ，则下标 i 处的值设为 -1 。
    示例 1：
        输入：intervals = [[1, 2]]
        输出：[-1]
        解释：集合中只有一个区间，所以输出-1。
    示例 2：
        输入：intervals = [[3, 4],[2, 3],[1, 2]]
        输出：[-1,0,1]
        解释：对于 [3,4] ，没有满足条件的“右侧”区间。
        对于 [2,3] ，区间[3,4]具有最小的“右”起点;
        对于 [1,2] ，区间[2,3]具有最小的“右”起点。
    示例 3：
        输入：intervals = [[1,4],[2,3],[3,4]]
        输出：[-1,2,-1]
        解释：对于区间 [1,4] 和 [3,4] ，没有满足条件的“右侧”区间。
        对于 [2,3] ，区间 [3,4] 有最小的“右”起点。
    提示：
        1 <= intervals.length <= 2 * 104
        intervals[i].length == 2
        -106 <= starti <= endi <= 106
        每个间隔的起点都 不相同
    Related Topics:数组,二分查找,排序
*/
public class NO436_N_FindRightInterval {

    @Test
    public void test() {
        assertArrayEquals(new int[]{-1},
            findRightInterval(new int[][]{{1, 2}}));
        assertArrayEquals(new int[]{-1, 0, 1},
            findRightInterval(new int[][]{{3,4}, {2, 3},{1, 2}}));
        assertArrayEquals(new int[]{-1, 2, -1},
            findRightInterval(new int[][]{{1,4}, {2, 3},{3, 4}}));
    }

    public int[] findRightInterval(int[][] intervals) {
        // 2024/3/14 NO.1
        int n = intervals.length;
        int[] ans = new int[n];
        int[][] start = new int[n][2];
        for (int i = 0; i < n; i++) {
            start[i][0] = intervals[i][0];
            start[i][1] = i;
        }

        Arrays.sort(start, Comparator.comparingInt(a -> a[0]));

        for (int i = 0; i < n; i++) {
            int left = 0;
            int right = n - 1;
            int target = -1;
            while (left <= right) {
                int mid = (left + right) >>> 1;
                if (start[mid][0] >= intervals[i][1]) {
                    target = start[mid][1];
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }
            ans[i] = target;
        }
        return ans;
    }

}
















/*
// 方法1：
public int[] findRightInterval(int[][] intervals) {
    int n = intervals.length;
    int[] ans = new int[n];
    Map<Integer, Integer> map = new HashMap<>();
    for (int i = 0; i < n; i++)
        map.put(intervals[i][0], i);

    Arrays.sort(intervals, (o1, o2) -> {
        return o1[0] - o2[0];
    });

    for (int i = 0; i < n; i++) {
        int ei = intervals[i][1];
        int ri = map.get(intervals[i][0]);
        ans[ri] = -1;
        for (int j = i; j < n; j++)
            if (intervals[j][0] >= ei) {
                ans[ri] = map.get(intervals[j][0]);
                break;
            }
    }
    return ans;
}
*/
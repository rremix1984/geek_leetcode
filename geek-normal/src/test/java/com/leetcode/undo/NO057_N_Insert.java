/**
 * copyright 2022/1/19
 */
package com.leetcode.undo;

import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static org.junit.Assert.assertArrayEquals;

/**
    (中等)
    57. 插入区间
        给你一个 无重叠的 ，按照区间起始端点排序的区间列表。
        在列表中插入一个新的区间，你需要确保列表中的区间仍然有序且不重叠（如果有必要的话，可以合并区间）。
    示例 1：
        输入：intervals = {{1, 3}, {6, 9}},  newInterval = {2, 5}
        输出：{{1, 5}, {6, 9}}
    示例 2：
        输入：intervals = {{1, 2}, {3, 5}, {6, 7}, {8, 10}, {12, 16}},  newInterval = {4, 8}
        输出：{{1, 2}, {3, 10}, {12, 16}}
        解释：这是因为新的区间 {4, 8} 与 {3, 5}, {6, 7}, {8, 10} 重叠。
    示例 3：
        输入：intervals = {}, newInterval = {5, 7}
        输出：{{5, 7}}
    示例 4：
        输入：intervals = {{1, 5}}, newInterval = {2, 3}
        输出：{{1, 5}}
    示例 5：
        输入：intervals = {{1, 5}}, newInterval = {2, 7}
        输出：{{1, 7}}
    提示：
        0 <= intervals.length <= 104
        intervals[i].length == 2
        0 <= intervals[i][0] <= intervals[i][1] <= 105
        intervals 根据 intervals[i][0] 按 升序 排列
        newInterval.length == 2
        0 <= newInterval[0] <= newInterval[1] <= 105
*/
public class NO057_N_Insert {

    @Test
    public void test() {
        assertArrayEquals(new int[][]{{1, 5}, {6, 9}},
                insert(new int[][]{{1, 3}, {6, 9}}, new int[]{2, 5}));
        assertArrayEquals(new int[][]{{1, 2}, {3, 10}, {12, 16}},
                insert(new int[][]{{1, 2}, {3, 5}, {6, 7}, {8, 10}, {12, 16}}, new int[]{4, 8}));
        assertArrayEquals(new int[][]{{5, 7}},
                insert(new int[][]{}, new int[]{5, 7}));
        assertArrayEquals(new int[][]{{1, 5}},
                insert(new int[][]{{1, 5}}, new int[]{2, 3}));
        assertArrayEquals(new int[][]{{1, 7}},
                insert(new int[][]{{1, 5}}, new int[]{2, 7}));
    }

    public int[][] insert(int[][] intervals, int[] newInterval) {
        int[][] res = new int[intervals.length + 1][2];
        int idx = 0;
        // 遍历区间列表：
        // 首先将新区间左边且相离的区间加入结果集
        int i = 0;
        while (i < intervals.length && intervals[i][1] < newInterval[0])
            res[idx++] = intervals[i++];

        // 接着判断当前区间是否与新区间重叠，重叠的话就进行合并，直到遍历到当前区间在新区间的右边且相离，
        // 将最终合并后的新区间加入结果集
        while (i < intervals.length && intervals[i][0] <= newInterval[1]) {
            newInterval[0] = min(intervals[i][0], newInterval[0]);
            newInterval[1] = max(intervals[i][1], newInterval[1]);
            i++;
        }

        res[idx++] = newInterval;
        // 最后将新区间右边且相离的区间加入结果集
        while (i < intervals.length)
            res[idx++] = intervals[i++];

        return Arrays.copyOf(res, idx);
    }

}

















/**
// 方法1：
public int[][] insert(int[][] intervals, int[] newInterval) {
    int[][] res = new int[intervals.length + 1][2];
    int idx = 0;
    // 遍历区间列表：
    // 首先将新区间左边且相离的区间加入结果集
    int i = 0;
    while (i < intervals.length && intervals[i][1] < newInterval[0]) {
        res[idx++] = intervals[i++];
    }
    // 接着判断当前区间是否与新区间重叠，重叠的话就进行合并，直到遍历到当前区间在新区间的右边且相离，
    // 将最终合并后的新区间加入结果集
    while (i < intervals.length && intervals[i][0] <= newInterval[1]) {
        newInterval[0] = Math.min(intervals[i][0], newInterval[0]);
        newInterval[1] = Math.max(intervals[i][1], newInterval[1]);
        i++;
    }
    res[idx++] = newInterval;
    // 最后将新区间右边且相离的区间加入结果集
    while (i < intervals.length) {
        res[idx++] = intervals[i++];
    }

    return Arrays.copyOf(res, idx);
}

// 方法2：
public int[][] insert(int[][] intervals, int[] newInterval) {
    int left = newInterval[0];
    int right = newInterval[1];
    boolean placed = false;
    List<int[]> ansList = new ArrayList<>();
    for (int[] interval : intervals) {
        if (interval[0] <= right && interval[1] >= left) {
            // 与插入区间有交集，计算它们的并集
            left = min(left, interval[0]);
            right = max(right, interval[1]);
            continue;
        }

        if (interval[0] > right)
            // 在插入区间的右侧且无交集
            if (!placed) {
                ansList.add(new int[]{left, right});
                placed = true;
            }

        // 在插入区间的左侧且无交集
        ansList.add(interval);
    }

    if (!placed)
        ansList.add(new int[]{left, right});

    return ansList.toArray(new int[ansList.size()][2]);
}
*/
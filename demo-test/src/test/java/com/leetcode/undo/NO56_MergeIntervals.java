/**
 * copyright 2022/1/19
 */
package com.leetcode.undo;

import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import static com.leetcode.util.LogUtil.info;
import static com.leetcode.util.MathUtils.max;
import static java.util.Comparator.comparingInt;

/**
    （中等）
    56. 合并区间
        以数组 intervals 表示若干个区间的集合，其中单个区间为 intervals[i] = [starti, endi] 。
        请你合并所有重叠的区间，并返回 一个不重叠的区间数组，该数组需恰好覆盖输入中的所有区间 。
    示例 1：
        输入：intervals = {{1, 3}, {2, 6}, {8, 10}, {15, 18}}
        输出：[[1, 6], [8, 10], [15, 18]]
        解释：区间 [1, 3] 和 [2, 6] 重叠, 将它们合并为 [1, 6].
    示例 2：
        输入：intervals = {{1, 4}, {4, 5}}
        输出：[[1, 5]]
        解释：区间 [1, 4] 和 [4, 5] 可被视为重叠区间。
*/
public class NO56_MergeIntervals {

    @Test
    public void test() {
        info(merge(new int[][]{{1, 3}, {2, 6}, {8, 10}, {15, 18}}));// [[1, 6], [8, 10], [15, 18]]
        info(merge(new int[][]{{1, 4}, {4, 5}}));// [[1, 5]]
    }

    public int[][] merge(int[][] intervals) {
        if (intervals.length == 0)
            return new int[0][2];

        Arrays.sort(intervals,
            comparingInt(interval -> interval[0])
        );

        List<int[]> merged = new ArrayList<>();
        for (int[] interval : intervals) {
            if (merged.size() == 0 || merged.get(merged.size() - 1)[1] < interval[0])
                merged.add(new int[]{interval[0], interval[1]});
            else
                merged.get(merged.size() - 1)[1] =
                    max(merged.get(merged.size() - 1)[1], interval[1]);
        }
        return merged.toArray(new int[merged.size()][]);
    }

}














/**
public int[][] merge(int[][] intervals) {
    if (intervals.length == 0)
        return new int[0][2];

    Arrays.sort(intervals,
            Comparator.comparingInt(interval -> interval[0])
    );
    List<int[]> merged = new ArrayList<>();
    for (int i = 0; i < intervals.length; i++) {
        int L = intervals[i][0], R = intervals[i][1];
        if (merged.size() == 0 || merged.get(merged.size() - 1)[1] < L)
            merged.add(new int[]{L, R});
        else
            merged.get(merged.size() - 1)[1] = Math.max(merged.get(merged.size() - 1)[1], R);
    }
    return merged.toArray(new int[merged.size()][]);
}
*/
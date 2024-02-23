/**
 * copyright 2022/1/19
 */
package com.leetcode.donnot;

import org.junit.Test;

import java.util.Arrays;

/**
    [ARRAY]
    (简单)
    1051. 高度检查器
        学校打算为全体学生拍一张年度纪念照。根据要求:学生需要按照非递减的
        高度顺序排成一行。排序后的高度情况用整数数组expected表示，
        其中expected[i]是预计排在这一行中第i位的学生的高度（下标从0开始）。
        给你一个整数数组heights，表示当前学生站位的高度情况。heights[i]
        是这一行中第i位学生的高度（下标从0开始）。
        返回满足heights[i] != expected[i]的下标数量 。
    示例1：
        输入：heights = {1, 1, 4, 2, 1, 3}
        输出：3
        解释：高度：{1, 1, 4, 2, 1, 3}
             预期：{1, 1, 1, 2, 3, 4}
             下标 2 、4 、5 处的学生高度不匹配。
    示例 2：
        输入：heights = {5, 1, 2, 3, 4}
        输出：5
        解释：高度：{5, 1, 2, 3, 4}
             预期：{1, 2, 3, 4, 5}
             所有下标的对应学生高度都不匹配。
    示例 3：
        输入：heights = {1, 2, 3, 4, 5}
        输出：0
        解释：高度：{1, 2, 3, 4, 5}
             预期：{1, 2, 3, 4, 5}
             所有下标的对应学生高度都匹配。
    提示：
        1 <= heights.length <= 100
        1 <= heights[i] <= 100

    方法二：计数排序
        注意到本题中学生的高度小于等于 100，因此可以使用计数排序。
    在进行计数排序时，我们可以直接使用一个长度为 101 的数组，
    也可以先对数组 heights 进行一次遍历，找出最大值max，从而使
    用一个长度为 m + 1 的数组。
    当计数排序完成后，我们可以再使用一个长度为 n 的数组，显式地存
    储排序后的结果。为了节省空间，我们也直接在计数排序的数组上进行遍历，
*/
public class NO1051_E_HeightChecker {

    @Test
    public void test() {
        assert 3 == heightChecker(
                new int[]{1, 1, 4, 2, 1, 3});
        assert 5 == heightChecker(
                new int[]{5, 1, 2, 3, 4});
        assert 0 == heightChecker(
                new int[]{1, 2, 3, 4, 5});
        assert 4 == heightChecker(
                new int[]{2, 1, 2, 1, 1, 2, 2, 1});
    }

    public int heightChecker(int[] heights) {
        int ans = 0;
        int max = Arrays.stream(heights).max().getAsInt();

        int[] cnt = new int[max + 1];
        for (int h : heights)
            cnt[h]++;

        int idx = 0;
        for (int i = 1; i <= max; i++)
            for (int j = 1; j <= cnt[i]; j++)
                if (heights[idx++] != i)
                    ans++;

        return ans;
    }

}


















/**
// 方法1：
public int heightChecker(int[] heights) {
    int ans = 0;
    int n = heights.length;
    int[] expected = new int[n];
    System.arraycopy(heights, 0, expected, 0, n);
    Arrays.sort(expected);
    for (int i = 0; i < n; ++i)
        if (heights[i] != expected[i])
            ans++;

    return ans;
}

// 方法2：
public int heightChecker(int[] heights) {
    int ans = 0;
    int max = Arrays.stream(heights).max().getAsInt();
    int[] cnt = new int[max + 1];
    for (int h : heights)
        cnt[h]++;

    int idx = 0;
    for (int i = 1; i <= max; i++)
        for (int j = 1; j <= cnt[i]; j++)
            if (heights[idx++] != i)
                ans++;

    return ans;
}
*/
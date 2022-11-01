/**
 * copyright 2022/1/19
 */
package com.leetcode.undo;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.leetcode.util.MathUtils.getArray;
import static org.junit.Assert.assertArrayEquals;

/**
    (简单)
    2363. 合并相似的物品
        给你两个二维整数数组 items1 和 items2 ，表示两个物品集合。每个数组 items 有以下特质：
        items[i] = [valuei, weighti] 其中 valuei 表示第 i 件物品的 价值 ，weighti 表示第 i 件物品的 重量 。
        items 中每件物品的价值都是 唯一的 。
        请你返回一个二维数组 ret，其中 ret[i] = [valuei, weighti]， weighti 是所有价值为 valuei 物品的 重量之和 。
        注意：ret 应该按价值 升序 排序后返回。
    示例 1：
        输入：items1 = {{1, 1}, {4, 5}, {3, 8}},  items2 = {{3, 1}, {1, 5}}
        输出：{{1, 6}, {3, 9}, {4, 5}}
        解释：
        value = 1 的物品在 items1 中 weight = 1 ，在 items2 中 weight = 5 ，总重量为 1 + 5 = 6 。
        value = 3 的物品再 items1 中 weight = 8 ，在 items2 中 weight = 1 ，总重量为 8 + 1 = 9 。
        value = 4 的物品在 items1 中 weight = 5 ，总重量为 5 。
        所以，我们返回 {{1, 6}, {3, 9}, {4, 5}} 。
    示例 2：
        输入：items1 = {{1, 1}, {3, 2}, {2, 3}},  items2 = {{2, 1}, {3, 2}, {1, 3}}
        输出：{{1, 4}, {2, 4}, {3, 4}}
        解释：
        value = 1 的物品在 items1 中 weight = 1 ，在 items2 中 weight = 3 ，总重量为 1 + 3 = 4 。
        value = 2 的物品在 items1 中 weight = 3 ，在 items2 中 weight = 1 ，总重量为 3 + 1 = 4 。
        value = 3 的物品在 items1 中 weight = 2 ，在 items2 中 weight = 2 ，总重量为 2 + 2 = 4 。
        所以，我们返回 {{1, 4}, {2, 4}, {3, 4}} 。
    示例 3：
        输入：items1 = {{1, 3}, {2, 2}},  items2 = {{7, 1}, {2, 2}, {1, 4}}
        输出：{{1, 7}, {2, 4}, {7, 1}}
        解释：
        value = 1 的物品在 items1 中 weight = 3 ，在 items2 中 weight = 4 ，总重量为 3 + 4 = 7 。
        value = 2 的物品在 items1 中 weight = 2 ，在 items2 中 weight = 2 ，总重量为 2 + 2 = 4 。
        value = 7 的物品在 items2 中 weight = 1 ，总重量为 1 。
        所以，我们返回 {{1, 7}, {2, 4}, {7, 1}} 。
*/
public class NO2363_E_MergeSimilarItems {

    @Test
    public void test() {
        getArray(new int[][]{{1, 6}, {3, 9}, {4, 5}}).equals(
            mergeSimilarItems(new int[][]{{1, 1}, {4, 5}, {3, 8}}, new int[][]{{3, 1}, {1, 5}}));
        getArray(new int[][]{{1, 4}, {2, 4}, {3, 4}}).equals(
            mergeSimilarItems(new int[][]{{1, 1}, {3, 2}, {2, 3}}, new int[][]{{2, 1}, {3, 2}, {1, 3}}));
        getArray(new int[][]{{1, 7}, {2, 4}, {7, 1}}).equals(
            mergeSimilarItems(new int[][]{{1, 3}, {2, 2}}, new int[][]{{7, 1}, {2, 2}, {1, 4}}));
    }

    public List<List<Integer>> mergeSimilarItems(int[][] items1, int[][] items2) {
        List<List<Integer>> res = new ArrayList<>();
        int[] dict = new int[1001];
        for (int[] p : items1)
            dict[p[0]] += p[1];

        for (int[] p : items2)
            dict[p[0]] += p[1];

        for (int i = 0; i <= 1000; i++)
            if (dict[i] != 0)
                res.add(Arrays.asList(i, dict[i]));

        return res;
    }

}

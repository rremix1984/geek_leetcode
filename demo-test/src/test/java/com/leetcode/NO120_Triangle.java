/**
 * copyright 2022/1/19
 */
package com.leetcode;

import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static com.leetcode.util.LogUtil.info;

/**
    （中等）
    120. 三角形最小路径和
        给定一个三角形 triangle，找出自顶向下的最小路径和。
        每一步只能移动到下一行中相邻的结点上。相邻的结点在
        这里指的是 下标 与 上一层结点下标 相同或者等于 上一层
        结点下标 + 1 的两个结点。也就是说，如果正位于当前行的
        下标 i ，那么下一步可以移动到下一行的下标 i 或 i + 1 。
    示例 1：
        输入：triangle = [[2],[3,4],[6,5,7],[4,1,8,3]]
        输出：11
        解释：如下面简图所示：
             2
            3 4
           6 5 7
          4 1 8 3
        自顶向下的最小路径和为 11（即，2 + 3 + 5 + 1 = 11）。
    示例 2：
        输入：triangle = [[-10]]
        输出：-10
*/
public class NO120_Triangle {

    @Test
    public void test() {
        List list = new ArrayList<List<Integer>>(){{
            // [2],[3,4],[6,5,7],[4,1,8,3]
            add(new ArrayList(){{add(2);}});
            add(new ArrayList(){{add(3);add(4);}});
            add(new ArrayList(){{add(6);add(5);add(7);}});
            add(new ArrayList(){{add(4);add(1);add(8);add(3);}});
        }};
        info(minimumTotal(list)); // 11

        info(minimumTotal(new ArrayList(){{
                add(new ArrayList(){{add(10);}});
            }})); // -10
    }

    public int minimumTotal(List<List<Integer>> triangle) {
        int n = triangle.size();
        int[][] f = new int[n][n];
        f[0][0] = triangle.get(0).get(0);
        for (int i = 1; i < n; ++i) {
            f[i][0] = f[i - 1][0] + triangle.get(i).get(0);
            for (int j = 1; j < i; ++j)
                f[i][j] = Math.min(f[i - 1][j - 1], f[i - 1][j]) + triangle.get(i).get(j);
            f[i][i] = f[i - 1][i - 1] + triangle.get(i).get(i);
        }
        int minTotal = f[n - 1][0];
        for (int i = 1; i < n; ++i)
            minTotal = Math.min(minTotal, f[n - 1][i]);
        return minTotal;
    }
}

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
    46. 全排列
        给定一个不含重复数字的数组 nums ，返回其 所有可能的全排列 。你可以 按任意顺序 返回答案。
    示例 1：
        输入：nums = [1,2,3]
        输出：[[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
    示例 2：
        输入：nums = [0,1]
        输出：[[0,1],[1,0]]
    示例 3：
        输入：nums = [1]
        输出：[[1]]
*/
public class NO46_Permutations {

    @Test
    public void test() {
        info(permute(new int[]{1, 2, 3}));
    }

    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        return res;
    }

}

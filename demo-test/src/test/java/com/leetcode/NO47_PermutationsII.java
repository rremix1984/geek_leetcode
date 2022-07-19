/**
 * copyright 2022/1/19
 */
package com.leetcode;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.leetcode.util.LogUtil.info;

/**
    47. 全排列 II
    给定一个可包含重复数字的序列 nums ，按任意顺序 返回所有不重复的全排列。
    示例 1：
        输入：nums = [1,1,2]
        输出：
        [[1,1,2],
        [1,2,1],
        [2,1,1]]
    示例 2：
        输入：nums = [1,2,3]
        输出：[[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
*/
public class NO47_PermutationsII {

    @Test
    public void test() {
        info(permuteUnique(new int[]{1, 1, 2}));
    }

    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        return res;
    }

}

/**
 * copyright 2022/1/19
 */
package com.leetcode;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.leetcode.util.LogUtil.info;

/**
 * 给你一个包含 n 个整数的数组nums，判断nums中是否存在三个元素 a，b，c ，
 * 使得a + b + c = 0 ？请你找出所有和为 0 且不重复的三元组。
 *
   注意：答案中不可以包含重复的三元组。
   示例 1：
     输入：nums = [-1, 0, 1, 2, -1, -4]
     输出：[
            [-1, -1, 2],
            [-1, 0, 1]
          ]

   示例 2：
     输入：nums = []
     输出：[]
 */
public class NO15_ThreeSum {

    @Test
    public void test() {
        info(threeSum(new int[]{-1, 0, 1, 2, -1, -4}));
    }

    public List<List<Integer>> threeSum(int[] nums) {

        return new ArrayList<>();
    }

}









/**
public List<List<Integer>> threeSum(int[] nums) {
    return new ArrayList<>();
}
*/
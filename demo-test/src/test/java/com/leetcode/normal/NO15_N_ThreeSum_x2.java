/**
 * copyright 2022/1/19
 */
package com.leetcode.normal;

import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static com.leetcode.util.LogUtil.info;

/**
 * 难度【中等】
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
public class NO15_N_ThreeSum_x2 {

    @Test
    public void test() {
        info(threeSum(new int[]{-1, 0, 1, 2, -1, -4}));
    }

    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> res = new LinkedList<>();

        return res;
    }
}









/**
 public List<List<Integer>> threeSum(int[] nums) {
     Arrays.sort(nums);
     List<List<Integer>> res = new LinkedList<>();
     for (int i = 0; i < nums.length - 2; i++) {
         if (i == 0 || (i>0 && nums[i] != nums[i-1])) {
             int lo = i + 1;
             int hi = nums.length - 1;
             int sum = 0 - nums[i];
             while (lo < hi) {
                 if (nums[lo] + nums[hi] == sum) {
                     res.add(Arrays.asList(nums[i], nums[lo], nums[hi]));
                     while (lo < hi && nums[lo] == nums[lo + 1])
                        lo++;
                     while (lo < hi && nums[hi] == nums[hi - 1])
                        hi--;
                     lo++;
                     hi--;
                 } else if (nums[lo] + nums[hi] < sum) {
                    lo++;
                 } else {
                    hi--;
                 }
             }
         }
     }
     return res;
 }
 */
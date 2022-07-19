/**
 * copyright 2022/1/19
 */
package com.leetcode;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static com.leetcode.util.LogUtil.info;

public class NO1_TwoSum_x2 {

    @Test
    public void test() {
        info(twoSum(new int[]{2, 2, 4}, 6));// 6
    }

    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int comp = target - nums[i];
            if (map.containsKey(comp)) {
                return new int[]{ map.get(comp), i};
            }
            map.put(nums[i], i);
        }
        return new int[0];
    }

}











/**
public int[] twoSum(int[] nums, int target) {
    int[] a = new int[2];
    int numSize = nums.length;
    for (int i = 0; i < numSize - 1; i++) {
        for (int j = i + 1; j < numSize; j++) {
            if (nums[i] + nums[j] == target) {
                a[0] = i;
                a[1] = j;
                return a;
            }
        }
    }
    return new int[0];
}
*/
/**
 * copyright 2022/1/19
 */
package com.offer.normal;

import org.junit.Test;
import java.util.*;
import static com.leetcode.util.MathUtils.getArray;
import static java.util.Arrays.sort;

/**
    [ARRAY] |||||||||
    (中等)
    剑指 Offer II 007. 数组中和为 0 的三个数
        给你一个整数数组 nums ，判断是否存在三元组 [nums[i], nums[j], nums[k]]
        满足 i != j、i != k 且 j != k ，同时还满足 nums[i] + nums[j] + nums[k] == 0 。请
        你返回所有和为 0 且【不重复的三元组】。
        注意：答案中不可以包含重复的三元组。
    示例 1：
        输入：nums = [-1, 0, 1, 2, -1, -4]
        输出：[[-1, -1, 2], [-1, 0, 1]]
        解释：nums[0] + nums[1] + nums[2] = (-1) + 0 + 1 = 0 。
             nums[1] + nums[2] + nums[4] = 0 + 1 + (-1) = 0 。
             nums[0] + nums[3] + nums[4] = (-1) + 2 + (-1) = 0 。
             不同的三元组是 [-1,0,1] 和 [-1,-1,2] 。
             注意，输出的顺序和三元组的顺序并不重要。
    示例 2：
        输入：nums = [0, 1, 1]
        输出：[]
        解释：唯一可能的三元组和不为 0 。
    示例 3：
        输入：nums = [0, 0, 0]
        输出：[[0, 0, 0]]
        解释：唯一可能的三元组和为 0 。
*/
public class OfferII_007_N_ThreeSum {

    @Test
    public void test() {
        assert getArray(new int[][]{{-1, -1, 2}, {-1, 0, 1}})
            .equals(threeSum(new int[]{-1, 0, 1, 2, -1, -4}));
        assert threeSum(new int[]{0, 1, 1}).isEmpty();
        assert getArray(new int[][]{{0, 0, 0}})
            .equals(threeSum(new int[]{0, 0, 0}));
        assert getArray(new int[][]{{-1, -1, 2}, {-1, 0, 1}})
            .equals(threeSum(new int[]{-1, 0, 1, 2, -1, -4}));
        assert getArray(new int[][]{{0, 0, 0}})
            .equals(threeSum(new int[]{0, 0, 0, 0}));
    }

    public List<List<Integer>> threeSum(int[] nums) {
        // 2024/3/13 NO.1 先定住一个，再看其他三个
        // 2024/3/17 NO.2 思路有了，但是边界条件没判断好，基本功问题，要多练习
        // 2024/3/19 NO.3 还是不行,思路不行
        // 2024/3/20 NO.4
        // 2024/3/22 NO.5
        // 2024/3/25 NO.6 这题不好做，思路对，但是边界条件很难判断
        // 2024/3/29 NO.7 不好做，没做出来，要处理很多细节
        // 2024/3/30 NO.8 思路对,细节处理的不好
        // 2024/3/31 NO.9 思路对，比前几次强，没做出来
        List<List<Integer>> ans = new ArrayList<>();
        Arrays.sort(nums);

        return ans;
    }

}





















/*
// 方法1：
public List<List<Integer>> threeSum(int[] nums) {
    int n = nums.length;
    Arrays.sort(nums);
    List<List<Integer>> ans = new ArrayList<List<Integer>>();
    // 枚举 a
    for (int first = 0; first < n; ++first) {
        // 需要和上一次枚举的数不相同
        // 审题：满足 i != j、i != k 且 j != k
        if (first > 0 && nums[first] == nums[first - 1]) {
            continue;
        }
        // c 对应的指针初始指向数组的最右端
        int third = n - 1;
        int target = -nums[first];
        // 枚举 b
        for (int second = first + 1; second < n; ++second) {
            // 需要和上一次枚举的数不相同
            if (second > first + 1 && nums[second] == nums[second - 1]) {
                continue;
            }
            // 需要保证 b 的指针在 c 的指针的左侧
            while (second < third && nums[second] + nums[third] > target) {
                --third;
            }
            // 如果指针重合，随着 b 后续的增加
            // 就不会有满足 a+b+c=0 并且 b<c 的 c 了，可以退出循环
            if (second == third) {
                break;
            }
            if (nums[second] + nums[third] == target) {
                List<Integer> list = new ArrayList<Integer>();
                list.add(nums[first]);
                list.add(nums[second]);
                list.add(nums[third]);
                ans.add(list);
            }
        }
    }
    return ans;
}

// 方法2：三指针
public List<List<Integer>> threeSum(int[] nums) {
    List<List<Integer>> ans = new ArrayList<>();
    Arrays.sort(nums);
    int n = nums.length;

    // 三个指针 i, j, k 先固定一个 i 在看其他两个 j, k
    for (int i = 0; i < n; i++) {

        // 如果前后两个元素相同，就不用判断了，跳过
        // 题目要求满足 i != j、i != k 且 j != k
        if (i > 0 && nums[i] == nums[i - 1])
            continue;

        // j 指针从 i 后面 1 位开始
        int j = i + 1;

        // k 从最后一个元素 n - 1 位置开始
        int k = n - 1;

        // j 和 k 相向而行
        while (j < k) {

            // j 和 j-1 相等，j 指针向后走
            while (j > i + 1 &&
                    j < n && nums[j] == nums[j - 1])
                j++;

            // j 不能超过 k
            if (j >= k)
                break;

            // 三数求和
            int sum = nums[i] + nums[j] + nums[k];
            if (sum == 0) {
                ans.add(asList(nums[i], nums[j], nums[k]));
                j++;
            } else if (sum > 0) {
                k--;
            } else{
                j++;
            }
        }
    }
    return ans;
}
*/
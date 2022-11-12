/**
 * copyright 2022/1/19
 */
package com.leetcode.normal;

import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static com.leetcode.util.MathUtils.getArray;

/**
    (中等)
    491. 递增子序列
        给你一个整数数组nums，找出并返回所有该数组中不同的递增子序列，递增子序列中
        至少有两个元素。你可以按任意顺序返回答案。数组中可能含有重复元素，
        如出现两个整数相等，也可以视作递增序列的一种特殊情况。
    示例 1：
        输入：nums = [4,6,7,7]
        输出：[[4,6],[4,6,7],[4,6,7,7],[4,7],[4,7,7],[6,7],[6,7,7],[7,7]]
    示例 2：
        输入：nums = [4,4,3,2,1]
        输出：[[4,4]]
    提示：
        1 <= nums.length <= 15
        -100 <= nums[i] <= 100
*/
public class NO491_N_FindSubSequences_x2 {

    @Test
    public void test() {
        assert getArray(new int[][]{{4,6,7,7}, {4,6,7}, {4,6}, {4,7,7}, {4,7}, {6,7,7}, {6,7}, {7, 7}}).equals(
                findSubsequences(new int[]{4,6,7,7}));
        assert getArray(new int[][]{{4,4}}).equals(
                findSubsequences(new int[]{4,4,3,2,1}));
    }

    public List<List<Integer>> findSubsequences(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        return ans;
    }

}


















/**
// 方法1：
public List<List<Integer>> findSubsequences(int[] nums) {
    List<List<Integer>> ans = new ArrayList<>();
    dfs(ans, new ArrayList<>(),0, MIN_VALUE, nums);
    return ans;
}

public void dfs(List<List<Integer>> ans, List<Integer> temp, int cur, int last, int[] nums) {
    // 把数组遍历完了
    if (cur == nums.length) {
        // 只要大于2就可以放入结果集
        if (temp.size() >= 2)
            ans.add(new ArrayList<>(temp));
        return;
    }

    // 要求是递增，只要比最近一个（last）大就可以
    if (nums[cur] >= last) {
        // 加上去
        temp.add(nums[cur]);
        // 递归
        dfs(ans, temp, cur + 1, nums[cur], nums);
        // 减去最后一个元素
        temp.remove(temp.size() - 1);
    }

    // 相等的时候不需要操作
    if (nums[cur] != last)
        // 相当于只有cur自增，但是last不变，
        // 元素整体长度不增加，仅让序列号自增，
        // 比如：[4,6]、[4,6,7]都是计数器到了最后一位，
        // 但是并没有把所有元素都加上的结果
        dfs(ans, temp, cur + 1, last, nums);
}
*/
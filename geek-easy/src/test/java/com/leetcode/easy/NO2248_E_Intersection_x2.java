/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import com.leetcode.util.MathUtils;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static com.leetcode.util.MathUtils.getArray;
import static java.util.Collections.emptyList;

/**
    (简单)
    2248. 多个数组求交集
        给你一个二维整数数组nums ，其中 nums[i] 是由不同正整数组成的一个非空数组，
        按升序排列返回一个数组，数组中的每个元素在 nums 所有数组中都出现过。
    示例 1：
        输入：nums = {{3, 1, 2, 4, 5}, {1, 2, 3, 4}, {3, 4, 5, 6}}
        输出：{3, 4}
        解释：nums{0} = {3, 1, 2, 4, 5}，nums{1} = {1, 2, 3, 4}，nums{2} = {3, 4, 5, 6}，在 nums 中每个数组中都出现的数字是 3 和 4 ，所以返回 {3, 4} 。
    示例 2：
        输入：nums = {{1, 2, 3}, {4, 5, 6}}
        输出：{}
        解释：不存在同时出现在 nums[0] 和 nums[1] 的整数，所以返回一个空列表 [] 。
    提示：
        1 <= nums.length <= 1000
        1 <= sum(nums[i].length) <= 1000
        1 <= nums[i][j] <= 1000
        nums[i] 中的所有值 互不相同
*/
public class NO2248_E_Intersection_x2 {

    @Test
    public void test() {
        assert getArray(3, 4).equals(
            intersection(new int[][]{{3, 1, 2, 4, 5}, {1, 2, 3, 4}, {3, 4, 5, 6}}));
        assert emptyList().equals(
            intersection(new int[][]{{1, 2, 3}, {4, 5, 6}}));
    }

    // 最大的数字不超过1000，
    // 所以搞一个长度为1000的数组记录每个数字出现的次数
    public List<Integer> intersection(int[][] nums) {
        List<Integer> list = new ArrayList<>();
        return list;
    }
    
}
















/**
// 方法1：
// 最大的数字不超过1000，所以搞一个长度为1000的数组记录每个数字出现的次数
public List<Integer> intersection(int[][] nums) {
    int[] arr = new int[1001];

    // 在 arr 中初始化所有遇到过的字符
    for (int[] num : nums)
        for (int i : num)
            arr[i]++;

    List<Integer> list = new ArrayList<>();
    for (int i = 0; i < arr.length; i++)
        // 一共 nums.length 行，arr[i] 代表出现过 n 次
        // 如果正好相等，则说明每一行都出现过
        if (arr[i] == nums.length)
            list.add(i);

    //【按升序排列返回一个数组】
    Collections.sort(list);
    return list;
}
*/
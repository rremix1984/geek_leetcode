/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

import java.util.*;

import static com.leetcode.util.MathUtils.getArray;

/**
    [ARRAY] |
    (简单)
    2215. 找出两数组的不同
        给你两个下标从 0 开始的整数数组 nums1 和 nums2 ，请你返回一个长度为 2 的列表 answer ，其中：
        answer[0] 是 nums1 中所有 不 存在于 nums2 中的 不同 整数组成的列表。
        answer[1] 是 nums2 中所有 不 存在于 nums1 中的 不同 整数组成的列表。
        注意：列表中的整数可以按 任意 顺序返回。
    示例 1：
        输入：nums1 = {1, 2, 3}, nums2 = {2, 4, 6}
        输出：{{1, 3}, {4, 6}}
        解释：对于 nums1 ，nums1{1} = 2 出现在 nums2 中下标 0 处，然而 nums1{0} = 1 和 nums1{2} = 3 没有出现在 nums2 中。因此，answer{0} = {1,3}。
             对于 nums2 ，nums2{0} = 2 出现在 nums1 中下标 1 处，然而 nums2{1} = 4 和 nums2{2} = 6 没有出现在 nums2 中。因此，answer{1} = {4,6}。
    示例 2：
        输入：nums1 = {1, 2, 3, 3}, nums2 = {1, 1, 2, 2}
        输出：{{3}, {}}
        解释：对于 nums1 ，nums1[2] 和 nums1[3] 没有出现在 nums2 中。由于 nums1[2] == nums1[3] ，二者的值只需要在 answer[0] 中出现一次，故 answer[0] = [3]。
             nums2 中的每个整数都在 nums1 中出现，因此，answer[1] = [] 。
*/
public class NO2215_E_FindDifference {

    @Test
    public void test() {
        assert getArray(new int[][]{{1, 3}, {4, 6}}).equals(
            findDifference(new int[]{1, 2, 3}, new int[]{2, 4, 6}));
        assert getArray(new int[][]{{3}, {}}).equals(
            findDifference(new int[]{1, 2, 3, 3}, new int[]{1, 1, 2, 2}));
    }

    public List<List<Integer>> findDifference(int[] nums1, int[] nums2) {
        // 2024/2/25 NO.3
        return null;
    }

}















/*
// 方法1：
public List<List<Integer>> findDifference(int[] nums1, int[] nums2) {
    int[] arr1 = new int[2001];
    int[] arr2 = new int[2001];
    for (int num : nums1)
        arr1[num + 1000]++;

    for (int num : nums2)
        arr2[num + 1000]++;

    List<Integer> list1 = new ArrayList<>();
    List<Integer> list2 = new ArrayList<>();
    for (int i = 0; i < arr1.length; i++)
        // 1有，2没有
        if (arr1[i] > 0 && arr2[i] == 0)
            list1.add(i - 1000);
        // 2有，1没有
        else if (arr2[i] > 0 && arr1[i] == 0)
            list2.add(i - 1000);

    return Arrays.asList(list1, list2);
}

// 方法2：
public List<List<Integer>> findDifference(int[] nums1, int[] nums2) {
    // 2024/2/25 NO.3
    Set<Integer> set1 = new HashSet<>();
    Set<Integer> set2 = new HashSet<>();

    // 构建两个集合，分别包含 nums1 和 nums2 的唯一元素
    for (int num : nums1)
        set1.add(num);

    for (int num : nums2)
        set2.add(num);

    List<List<Integer>> answer = new ArrayList<>();
    answer.add(new ArrayList<>());
    answer.add(new ArrayList<>());

    // 查找只存在于 set1 中的元素
    for (int num : set1)
        if (!set2.contains(num))
            answer.get(0).add(num);

    // 查找只存在于 set2 中的元素
    for (int num : set2)
        if (!set1.contains(num))
            answer.get(1).add(num);

    return answer;
}
*/
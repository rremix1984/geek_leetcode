/**
 * copyright 2022/1/19
 */
package com.leetcode;

import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static com.leetcode.util.MathUtils.getArray;

/**
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
        assert getArray(new int[][]{{1, 3}, {4, 6}}).equals(findDifference(new int[]{1, 2, 3}, new int[]{2, 4, 6}));
        assert getArray(new int[][]{{3}, {}}).equals(findDifference(new int[]{1, 2, 3, 3}, new int[]{1, 1, 2, 2}));
    }

    public List<List<Integer>> findDifference(int[] nums1, int[] nums2) {
        // 哈希表初始化
        int[] nums = new int[2001];

        // 出现在nums1中的数标记为1
        for (int x : nums1)
            nums[x + 1000] = 1;

        for (int x:nums2) {
            // 将数值转为大于等于0的数
            x = x+1000;
            // 定义规则，如果在nums1和nums2都出现了，那么标记为3
            if (nums[x] == 1) nums[x] = 3;
                // 如果只出现在nums2，那么标记为2
            else if (nums[x] == 0) nums[x] = 2;
        }
        List<List<Integer>> list = new ArrayList<>();
        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();
        // 根据规则将数添加到列表中，此时记得还原数值-1000
        for (int i = 0; i < 2001; i++) {
            if (nums[i] == 1)
                list1.add(i-1000);
            else if (nums[i] == 2)
                list2.add(i-1000);
        }
        list.add(list1);
        list.add(list2);
        return list;
    }

}
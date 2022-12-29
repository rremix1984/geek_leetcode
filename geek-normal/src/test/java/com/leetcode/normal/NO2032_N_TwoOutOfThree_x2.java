package com.leetcode.normal;

import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static com.leetcode.util.MathUtils.getArray;

/**
    (中等)
    2032. 至少在两个数组中出现的值
        给你三个整数数组 nums1、nums2 和 nums3 ，请你构造并返回一个 元素各不相同的 数组，且由 至少 在 两个 数组中出现的所有值组成。数组中的元素可以按 任意 顺序排列。
    示例 1：
        输入：nums1 = [1, 1, 3, 2], nums2 = [2, 3], nums3 = [3]
        输出：[3, 2]
        解释：至少在两个数组中出现的所有值为：
            - 3 ，在全部三个数组中都出现过。
            - 2 ，在数组 nums1 和 nums2 中出现过。
    示例 2：
        输入：nums1 = [3, 1], nums2 = [2, 3], nums3 = [1, 2]
        输出：[2, 3, 1]
        解释：至少在两个数组中出现的所有值为：
            - 2 ，在数组 nums2 和 nums3 中出现过。
            - 3 ，在数组 nums1 和 nums2 中出现过。
            - 1 ，在数组 nums1 和 nums3 中出现过。
    示例 3：
        输入：nums1 = [1, 2, 2], nums2 = [4, 3, 3], nums3 = [5]
        输出：[]
        解释：不存在至少在两个数组中出现的值。
        提示：
            1 <= nums1.length, nums2.length, nums3.length <= 100
            1 <= nums1[i], nums2[j], nums3[k] <= 100
*/
public class NO2032_N_TwoOutOfThree_x2 {

    @Test
    public void test() {
        assert getArray(2, 3).equals(
            twoOutOfThree(new int[]{1, 1, 3, 2}, new int[]{2, 3}, new int[]{3}));
        assert getArray(1, 2, 3).equals(
            twoOutOfThree(new int[]{3, 1}, new int[]{2, 3}, new int[]{1, 2}));
        assert getArray(new int[0]).equals(
            twoOutOfThree(new int[]{1, 2, 2}, new int[]{4, 3, 3}, new int[]{5}));
    }

    public List<Integer> twoOutOfThree(int[] nums1, int[] nums2, int[] nums3) {
        List<Integer> list = new ArrayList<>();
        int[] l1 = new int[101];
        for (int n : nums1)
            l1[n] = 1;

        int[] l2 = new int[101];
        for (int n : nums2)
            l2[n] = 1;

        int[] l3 = new int[101];
        for (int n : nums3)
            l3[n] = 1;

        for (int i = 0; i < 101; i++)
            if (l1[i] + l2[i] + l3[i] > 1)
                list.add(i);

        return list;
    }

}



























/**
// 方法1：
public List<Integer> twoOutOfThree(int[] nums1, int[] nums2, int[] nums3) {
    Map<Integer, Integer> map = new HashMap<>();
    for (int i : nums1)
        map.put(i, 1);

    for (int i : nums2)
        map.put(i, map.getOrDefault(i, 0) | 2);

    for (int i : nums3)
        map.put(i, map.getOrDefault(i, 0) | 4);

    List<Integer> res = new ArrayList<>();
    for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
        int k = entry.getKey();
        int v = entry.getValue();
        if ((v & (v - 1)) != 0)
            res.add(k);

    }
    return res;
}


// 方法2：
public List<Integer> twoOutOfThree(int[] nums1, int[] nums2, int[] nums3) {
    List<Integer> list = new ArrayList<>();
    int[] l1 = new int[101];
    for (int n : nums1)
        l1[n] = 1;

    int[] l2 = new int[101];
    for (int n : nums2)
        l2[n] = 1;

    int[] l3 = new int[101];
    for (int n : nums3)
        l3[n] = 1;

    for (int i = 0; i < 101; i++)
        if (l1[i] + l2[i] + l3[i] > 1)
            list.add(i);

    return list;
}
*/
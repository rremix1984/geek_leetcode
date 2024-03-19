package com.leetcode;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.leetcode.util.MathUtils.*;
import static org.junit.Assert.assertArrayEquals;

/**
    [ARRAY]
    (中等)
    NO.2615 等值距离和
    给你一个下标从 0 开始的整数数组 nums 。现有一个长度等于 nums.length
    的数组 arr 。对于满足 nums[j] == nums[i] 且 j != i 的所有 j ，
    arr[i] 等于所有 |i - j| 之和。如果不存在这样的 j ，则令 arr[i] 等于 0 。
    返回数组 arr 。
    示例 1：
        输入：nums = [1,3,1,1,2]
        输出：[5,0,3,4,0]
        解释：
        i = 0 ，nums[0] == nums[2] 且 nums[0] == nums[3] 。因此，arr[0] = |0 - 2| + |0 - 3| = 5 。
        i = 1 ，arr[1] = 0 因为不存在值等于 3 的其他下标。
        i = 2 ，nums[2] == nums[0] 且 nums[2] == nums[3] 。因此，arr[2] = |2 - 0| + |2 - 3| = 3 。
        i = 3 ，nums[3] == nums[0] 且 nums[3] == nums[2] 。因此，arr[3] = |3 - 0| + |3 - 2| = 4 。
        i = 4 ，arr[4] = 0 因为不存在值等于 2 的其他下标。
    示例 2：
        输入：nums = [0,5,3]
        输出：[0,0,0]
        解释：因为 nums 中的元素互不相同，对于所有 i ，都有 arr[i] = 0 。
    提示：
        1 <= nums.length <= 105
        0 <= nums[i] <= 109
    Related Topics:数组,哈希表,前缀和
*/
public class NO2615_N_Distance {

    @Test
    public void test() {
        assertArrayEquals(getArrayL(0,0,0), distance(getArrays(0,5,3)));
    }

    public long[] distance(int[] nums) {
        long[] ans = new long[nums.length];


        // 构造哈希表，记录所有相同的数据
        HashMap<Integer, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            map.computeIfAbsent(num, k -> new ArrayList<>()).add(i);
        }


        // 遍历哈希表
        for (Map.Entry<Integer, List<Integer>> entry : map.entrySet()) {
            List<Integer> value = entry.getValue();

            // 前缀和 + 后缀和，区别是要乘以距离
            long[] list = new long[value.size()];
            long[] list2 = new long[value.size()];
            for (int i = 1; i < value.size(); i++)
                list[i] = list[i - 1] + (long) (value.get(i) - value.get(i - 1)) * i;

            for (int i = value.size() - 2; i >= 0; i--) {
                list2[i] = list2[i + 1] + (long) (value.get(i + 1) - value.get(i)) * (value.size() - i - 1);
            }

            // 将前缀和和后缀和相加
            for (int i = 0; i < value.size(); i++) {
                ans[value.get(i)] = list[i] + list2[i];
            }
        }
        return ans;
    }
}


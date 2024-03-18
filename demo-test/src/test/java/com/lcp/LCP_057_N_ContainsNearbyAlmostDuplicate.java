package com.lcp;

import org.junit.Test;
import java.util.TreeSet;

/**
    [ARRAY]
    (中等)
    LCP.057 存在重复元素的子数组
    给你一个整数数组 nums 和两个整数k和t。请你判断是否存在两个不同下标i和j，
    使得 abs(nums[i] - nums[j]) <= t ，同时又满足 abs(i - j) <= k 。
    如果存在则返回 true，不存在返回 false。
    示例 1：
        输入：nums = [1,2,3,1], k = 3, t = 0
        输出：true
    示例 2：
        输入：nums = [1,0,1,1], k = 1, t = 2
        输出：true
    示例 3：
        输入：nums = [1,5,9,1,5,9], k = 2, t = 3
        输出：false
    提示：
        0 <= nums.length <= 2 * 104
        -231 <= nums[i] <= 231 - 1
        0 <= k <= 104
        0 <= t <= 231 - 1
    Related Topics:数组,桶排序,有序集合,排序,滑动窗口
*/
public class LCP_057_N_ContainsNearbyAlmostDuplicate {

    @Test
    public void test() {
        assert containsNearbyAlmostDuplicate(new int[]{1, 2, 3, 1}, 3, 0);
        assert containsNearbyAlmostDuplicate(new int[]{1, 0, 1, 1}, 1, 2);
        assert !containsNearbyAlmostDuplicate(new int[]{1, 5, 9, 1, 5, 9}, 2, 3);
    }

    public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
        // 特殊情况
        if(nums.length < 2 || k == 0)
            return false;

        // 保存长度为k的窗口内的数
        TreeSet<Long> treeSet = new TreeSet<>();
        for (int i = 0; i < nums.length; i++) {

            // 由于 cur - floor 可能会出现溢出情况，所以用 Long 来表示
            long cur = nums[i];

            // 获取比 cur 小的数中最接近 cur 的数
            Long floor = treeSet.floor(cur);

            // 获取比 cur 大的数中最接近 cur 的数
            Long ceiling = treeSet.ceiling(cur);

            // 如果窗口中最接近 cur 的数 与 cur 的差 <= t, 说明存在这样的两个数，返回 true
            if(floor != null && cur - floor <= t)
                return true;

            if(ceiling != null && ceiling - cur <= t)
                return true;

            // 将当前数添加到窗口中
            treeSet.add(cur);

            // 剔除窗口最右边的元素
            if(i >= k)
                treeSet.remove(Long.valueOf(nums[i - k]));
        }
        return false;
    }
}

/**
 * copyright 2022/1/19
 */
package com.leetcode.undo;

import org.junit.Test;
import java.util.HashMap;
import java.util.Map;

/**
    (中等)
    525. 连续数组
        给定一个二进制数组 nums , 找到含有相同数量的 0 和 1 的最长连续子数组，并返回该子数组的长度。
    示例 1:
        输入: nums = [0,1]
        输出: 2
        说明: [0, 1] 是具有相同数量 0 和 1 的最长连续子数组。
    示例 2:
        输入: nums = [0,1,0]
        输出: 2
        说明: [0, 1] (或 [1, 0]) 是具有相同数量0和1的最长连续子数组。
    提示：
        1 <= nums.length <= 105
        nums[i] 不是 0 就是 1
*/
public class NO525_N_FindMaxLength {

    @Test
    public void test() {
        assert 2 == findMaxLength(new int[]{0, 1});
        assert 2 == findMaxLength(new int[]{0, 1, 0});
    }

    public int findMaxLength(int[] nums) {
        int cur = 0, ans = 0;
        // 避免扩容
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, -1);
        for (int i = 0; i < nums.length; i++) {
            // 遇到 0 减一；遇到 1 加一
            if (nums[i] == 0)
                nums[i] = --cur;
            else
                nums[i] = ++cur;

            if (map.containsKey(cur)) {
                // 关于长度为什么不是 i - map.get(cur) + 1
                // 可以参考一下答主你问我答第一个回答的图片
                // 当 map 中存在一样的 key 时
                // 数组中满足条件的位置应该是[map.get(cur) + 1, i]
                ans = Math.max(ans, i - map.get(cur));
            } else {
                map.put(cur, i);
            }
        }
        return ans;
    }

}

/**
 * copyright 2022/1/19
 */
package com.leetcode.normal;

import org.junit.Test;
import java.util.*;
import static com.leetcode.util.MathUtils.getArray;

/**
    (中等)
    229. 多数元素 II
        给定一个大小为n的整数数组，找出其中所有出现超过⌊n/3⌋次的元素。
    示例 1：
        输入：nums = [3,2,3]
        输出：[3]
    示例 2：
        输入：nums = [1]
        输出：[1]
    示例 3：
        输入：nums = [1,2]
        输出：[1,2]
    提示：
        1 <= nums.length <= 5 * 104
        -109 <= nums[i] <= 109
*/
public class NO229_N_MajorityElement_x2 {

    @Test
    public void test() {
        assert getArray(3).equals(
                majorityElement(new int[]{3, 2, 3}));
        assert getArray(1, 2).equals(
                majorityElement(new int[]{1, 2}));
        assert getArray(1, 2).equals(
                majorityElement(new int[]{1, 2}));
        assert getArray(4).equals(
                majorityElement(new int[]{4, 5, 3, 4, 4, 1, 0, -1, -2, 4, 6, 7, 8, 4}));
    }

    public List<Integer> majorityElement(int[] nums) {
        List<Integer> ans = new ArrayList<>();
        return ans;
    }

}














/**
// 方法1：
public List<Integer> majorityElement(int[] nums) {
    Map<Integer, Integer> cnt = new HashMap<>();

    for (int num : nums)
        if (cnt.containsKey(num))
            cnt.put(num, cnt.get(num) + 1);
        else
            cnt.put(num, 1);

    List<Integer> ans = new ArrayList<>();
    for (int x : cnt.keySet())
        if (cnt.get(x) > nums.length / 3)
            ans.add(x);

    return ans;
}
*/
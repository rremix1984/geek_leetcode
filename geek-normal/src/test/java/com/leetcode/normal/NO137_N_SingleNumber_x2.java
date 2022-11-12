/**
 * copyright 2022/1/19
 */
package com.leetcode.normal;

import org.junit.Test;
import static java.lang.Integer.MAX_VALUE;
import static java.lang.Integer.MIN_VALUE;

/**
    (中等)
    137. 只出现一次的数字 II
        给你一个整数数组nums，除某个元素仅出现一次外，其余每个元素都恰出现三次。
        请你找出并返回那个只出现了一次的元素。
        你必须设计并实现线性时间复杂度的算法且不使用额外空间来解决此问题。
    示例 1：
        输入：nums = [2, 2, 3, 2]
        输出：3
    示例 2：
        输入：nums = [0, 1, 0, 1, 0, 1, 99]
        输出：99
    提示：
        1 <= nums.length <= 3 * 104
        -231 <= nums[i] <= 231 - 1
        nums 中，除某个元素仅出现 一次 外，其余每个元素都恰出现 三次

    求和法：
        1.通过遍历数组获取所有元素的和以及 HashSet 内元素的和。
        2.（set * 3 - SumNum）/ 2 即可，除以 2 是因为我们减去之后得到的是 2 倍的目标元素。
    注：这个题目中需要注意溢出的情况 。
*/
public class NO137_N_SingleNumber_x2 {

    @Test
    public void test() {
        assert 3 == singleNumber(new int[]{2, 2, 3, 2});
        assert 99 == singleNumber(new int[]{0, 1, 0, 1, 0, 1, 99});
        assert MAX_VALUE == singleNumber(
                new int[]{43, 16, 45, 89, 45,
                        MIN_VALUE, 45, MAX_VALUE - 1, -MAX_VALUE,
                        MIN_VALUE, 43, MAX_VALUE, MIN_VALUE + 2, MIN_VALUE, 89,
                        MIN_VALUE + 2, 89, MIN_VALUE + 2, -MAX_VALUE, MAX_VALUE - 1,
                        -MAX_VALUE, 16, 16, MAX_VALUE - 1, 43});
    }

    public int singleNumber(int[] nums) {
        return 0;
    }

}














/**
// 方法1：
public int singleNumber(int[] nums) {
    Map<Integer, Integer> map = new HashMap<>();
    for (int num : nums)
        map.put(num, map.getOrDefault(num, 0) + 1);

    int ans = 0;
    for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
        int k = entry.getKey();
        int v = entry.getValue();
        if (v == 1) {
            ans = k;
            break;
        }
    }
    return ans;
}

// 方法2：
public int singleNumber(int[] nums) {
    int ans = 0;
    for (int i = 0; i < 32; i++) {
        int total = 0;
        for (int num: nums)
            total += ((num >> i) & 1);

        if (total % 3 != 0)
            ans |= (1 << i);
    }
    return ans;
}

// 方法3：
public int singleNumber(int[] nums) {
    Set<Integer> set = new HashSet<>();
    long setsum = 0;
    long allsum = 0;
    for (int num : nums) {
        //所有元素的和
        allsum += num;
        if (set.add(num))
            //HashSet元素和
            setsum += num;
    }
    // 所有唯一元素的3倍和 减去 元素和 等于 只出现一次元素的2倍
    // 只出现一次元素的2倍 除以 2 就是只出现一次的元素值
    return (int)((3 * setsum - allsum) / 2);
}
*/
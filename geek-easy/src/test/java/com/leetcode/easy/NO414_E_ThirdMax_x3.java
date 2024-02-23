/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

/**
    [ARRAY] |
    (简单)
    414. 第三大的数
        给你一个非空数组，返回此数组中第三大的数。如果不存在，则返回数组中最大的数。
    示例 1：
        输入：{3, 2, 1}
        输出：1
        解释：第三大的数是 1 。
    示例 2：
        输入：{1, 2}
        输出：2
        解释：第三大的数不存在, 所以返回最大的数 2 。
    示例 3：
        输入：{2, 2, 3, 1}
        输出：1
        解释：注意，要求返回第三大的数，是指在所有不同数字中排第三大的数。
             此例中存在两个值为 2 的数，它们都排第二。在所有不同数字中排第三大的数为 1 。
    提示：
        1 <= nums.length <= 104
        -2 ^ 31 <= nums[i] <= 2 ^ 31 - 1
*/
public class NO414_E_ThirdMax_x3 {

    @Test
    public void test() {
        assert 1 == thirdMax(new int[]{3, 2, 1});
        assert 2 == thirdMax(new int[]{1, 2});
        assert 1 == thirdMax(new int[]{2, 2, 3, 1});
    }

    public int thirdMax(int[] nums) {
        return -1;
    }

}















/*
// 方法1：
public int thirdMax(int[] nums) {
    Integer n_1 = null;// 第1大的数字
    Integer n_2 = null;// 第2大的数字
    Integer n_3 = null;// 第3大的数字
    for (int num : nums) {
        if (n_1 == null || num > n_1) {// 如果比第1大的还大
            n_3 = n_2;// 第2大的变第3大
            n_2 = n_1;// 第1大变第2大
            n_1 = num;
        } else if (n_1 > num && (n_2 == null || num > n_2)) {// 如果比第2大的还大
            n_3 = n_2;
            n_2 = num;
        } else if (n_2 != null && n_2 > num && (n_3 == null || num > n_3)) {// 如果比第3大的还大
            n_3 = num;
        }
    }
    // 审题，如果第3大的数字不存在，就返回第1大的数
    return n_3 == null ? n_1 : n_3;
}

// 方法2：
public int thirdMax(int[] nums) {
    Arrays.sort(nums);
    reverse(nums);
    for (int i = 1, diff = 1; i < nums.length; ++i)
        // 此时 nums[i] 就是第三大的数
        if (nums[i] != nums[i - 1] && ++diff == 3)
            return nums[i];

    return nums[0];
}

public void reverse(int[] nums) {
    int left = 0, right = nums.length - 1;
    while (left < right) {
        int temp = nums[left];
        nums[left] = nums[right];
        nums[right] = temp;
        left++;
        right--;
    }
}

// 方法3：
public int thirdMax(int[] nums) {
    TreeSet<Integer> set = new TreeSet<>();
    for (int num : nums) {
        set.add(num);
        if (set.size() > 3)
            set.remove(set.first());
    }

    if (set.size() == 3)
        return set.first();

    return set.last();
}
*/
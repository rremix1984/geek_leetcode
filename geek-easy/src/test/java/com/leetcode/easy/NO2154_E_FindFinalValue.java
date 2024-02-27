/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import java.util.Arrays;

/**
    [ARRAY] |
    (简单)
    2154. 将找到的值乘以2
        给你一个整数数组nums，另给你一个整数original，这是需要在nums中搜索的第一个数字。
        接下来，你需要按下述步骤操作：
         1）如果在nums中找到original，将 original乘以2，得到新original（即，令original=2*original）。
         2）否则，停止这一过程。
         3）只要能在数组中找到新original，就对新original继续重复这一过程。
        返回original的最终值。
    示例 1：
        输入：nums = {5, 3, 6, 1, 12},  original = 3
        输出：24
        解释：3能在nums中找到。3*2=6。
             6能在nums中找到。6*2=12。
            12能在nums中找到。12*2=24。
            24不能在nums中找到。因此，返回24。
    示例 2：
        输入：nums={2, 7, 9}, original=4
        输出：4
        解释：4不能在nums中找到。因此，返回4。
*/
public class NO2154_E_FindFinalValue {

    @Test
    public void test() {
        assert 24== findFinalValue(new int[]{5, 3, 6, 1, 12}, 3);
        assert 4 == findFinalValue(new int[]{2, 7, 9}, 4);
    }

    public int findFinalValue(int[] nums, int original) {
        // 2024/2/27 NO.3
        return original;
    }

}














/*
// 方法1：
public int findFinalValue(int[] nums, int original) {
    for (int num : nums)
        if (num == original)
            return findFinalValue(nums, original * 2);

    return original;
}
*/
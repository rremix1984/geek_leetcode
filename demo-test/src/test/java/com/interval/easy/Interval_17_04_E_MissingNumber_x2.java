/**
 * copyright 2022/1/19
 */
package com.interval.easy;

import org.junit.Test;

/**
    (简单)
    面试题 17.04. 消失的数字
        数组nums包含从0到n的所有整数，但其中缺了一个。请编写代码找出那个缺失的整数。你有办法在O(n)时间内完成吗？
        注意：本题相对书上原题稍作改动
    示例 1：
        输入：[3,0,1]
        输出：2
    示例 2：
        输入：[9,6,4,2,3,5,7,0,1]
        输出：8
*/
public class Interval_17_04_E_MissingNumber_x2 {

    @Test
    public void test() {
        assert 2 == missingNumber(new int[]{3, 0, 1});
        assert 8 == missingNumber(new int[]{9, 6, 4, 2, 3, 5, 7, 0, 1});
    }

    public int missingNumber(int[] nums) {
        int res = 0;
        return res;
    }

}
















/**
public int missingNumber(int[] nums) {
    int res = 0;
    for (int num : nums)
        res ^= num;

    for (int i = 0; i <= nums.length; i++)
        res ^= i;

    return res;
}
*/
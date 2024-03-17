/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

/**
    [ARRAY] |
    (简单)
    (太简单了，没意义)
    1486. 数组异或操作
        给你两个整数，n 和 start 。
        数组 nums 定义为：nums[i] = start + 2 * i（下标从 0 开始）
        且 n == nums.length 。
        请返回 nums 中所有元素按位【异或 (XOR)】后得到的结果。
    示例 1：
        输入：n = 5, start = 0
        输出：8
        解释：数组 nums 为 [0, 2, 4, 6, 8]，其中(0 ^ 2 ^ 4 ^ 6 ^ 8) = 8
             "^" 为按位异或 XOR 运算符。
    示例 2：
        输入：n = 4, start = 3
        输出：8
        解释：数组 nums 为 [3, 5, 7, 9]，其中 (3 ^ 5 ^ 7 ^ 9) = 8
    示例 3：
        输入：n = 1, start = 7
        输出：7
    示例 4：
        输入：n = 10, start = 5
        输出：2
*/
public class NO1486_E_XorOperation {

    @Test
    public void test() {
        assert 8 == xorOperation( 5,0);
        assert 8 == xorOperation( 4,3);
        assert 7 == xorOperation( 1,7);
        assert 2 == xorOperation(10,5);
    }

    public int xorOperation(int n, int start) {
        // 2024/3/18 NO.1
        int res = 0;
        return res;
    }

}
















/*
// 方法1：
public int xorOperation(int n, int start) {
    int res = 0;
    for (int i = 0; i < n; i++)
        res ^= (start + i * 2);

    return res;
}
*/
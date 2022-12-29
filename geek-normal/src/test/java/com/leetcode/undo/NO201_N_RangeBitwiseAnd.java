/**
 * copyright 2022/1/19
 */
package com.leetcode.undo;

import org.junit.Test;

/**
    (中等)
    201. 数字范围按位与
        给你两个整数 left 和 right ，表示区间 [left, right] ，
        返回此区间内所有数字 按位与 的结果（包含 left 、right 端点）。
    示例 1：
        输入：left = 5, right = 7
        输出：4
    示例 2：
        输入：left = 0, right = 0
        输出：0
    示例 3：
        输入：left = 1, right = 2147483647
        输出：0
    提示：
        0 <= left <= right <= 2 ^ 31 - 1
*/
public class NO201_N_RangeBitwiseAnd {

    @Test
    public void test() {
        assert 4 == rangeBitwiseAnd(5,7);
        assert 0 == rangeBitwiseAnd(0,0);
        assert 0 == rangeBitwiseAnd(1,2147483647);
    }

    public int rangeBitwiseAnd(int m, int n) {
        int shift = 0;
        // 找到公共前缀
        while (m < n) {
            m >>= 1;
            n >>= 1;
            ++shift;
        }
        return m << shift;
    }

}















/**
// 方法1：
public int rangeBitwiseAnd(int left, int right) {
    int mask = 1 << 30; // 最高位开始
    int ans = 0;
    while(mask > 0 && (left & mask) == (right & mask)) { //寻找相同前缀
        ans |= right & mask;
        mask >>= 1;
    }
    return ans;
}
*/
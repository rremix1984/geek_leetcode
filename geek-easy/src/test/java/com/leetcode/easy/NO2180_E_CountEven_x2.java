/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

/**
    (简单)
    2180. 统计各位数字之和为偶数的整数个数
        给你一个正整数num，请你统计并返回小于或等于 num 且
        各位数字之和为偶数的正整数的数目。
        正整数的各位数字之和是其所有位上的对应数字相加的结果。
    示例 1：
        输入：num = 4
        输出：2
        解释：只有 2 和 4 满足小于等于 4 且各位数字之和为偶数。
    示例 2：
        输入：num = 30
        输出：14
        解释：只有 14 个整数满足小于等于 30 且各位数字之和为偶数，分别是：
             2、4、6、8、11、13、15、17、19、20、22、24、26 和 28 。
*/
public class NO2180_E_CountEven_x2 {

    @Test
    public void test() {
        assert 2 == countEven(4);
        assert 14 == countEven(30);
    }

    public int countEven(int num) {
        return 0;
    }

}


















/**
// 方法1：
public int countEven(int num) {
    int ans = 0;
    for (int i = 2; i <= num; i++)
        // 各位相加的和 ret
        // 如果各位相加的和是个偶数
        // 结果 +1
        if (sumAll(i) % 2 == 0)
            ans++;

    return ans;
}

private int sumAll(int i) {
    int ret = 0;
    int tmp = i;
    while (tmp > 0) {
        ret += tmp % 10;
        tmp /= 10;
    }
    return ret;
}
*/
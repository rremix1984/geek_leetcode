/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import static java.lang.Math.max;

/**
    [ARRAY] |
    (简单)
    1399. 统计最大组的数目
        给你一个整数n。请你先求出从1到n的每个整数10进制
        表示下的数位和（每一位上的数字相加），
        【然后把数位和相等】的数字放到同一个组中。
        请你统计每个组中的数字数目，并返回数字数目并列最多的组有多少个。
    示例 1：
        输入：n = 13
        输出：4
        解释：总共有9个组，将1到13按数位求和后这些组分别是：
        [1, 10]，[2, 11]，[3, 12]，[4, 13]，[5]，[6]，[7]，[8]，[9]。
        总共有4个组拥有的数字并列最多。
    示例 2：
        输入：n = 2
        输出：2
        解释：总共有 2 个大小为 1 的组 [1]，[2]。
    示例 3：
        输入：n = 15
        输出：6
    示例 4：
        输入：n = 24
        输出：5
    提示：
        0 <= num <= 10^6
 */
public class NO1399_E_CountLargestGroup {

    @Test
    public void test() {
        assert 4 == countLargestGroup(13);
        assert 2 == countLargestGroup(2);
        assert 6 == countLargestGroup(15);
        assert 5 == countLargestGroup(24);
    }

    public int countLargestGroup(int n) {
        // 2024/2/27 NO.3
        int ans = 0;
        return ans;
    }

}


















/*
// 方法1：
public int countLargestGroup(int n) {
    int ans = 0;

    // 统计数位和有多少，因为从1开始，所以长度为 n+1
    int[] count = new int[n +  1];

    // 计算 1 - n 各个元素的数位和，例如数字i的数位和是sum[i / 10] + i % 10
    // 因为从1开始，所以长度为 n+1
    int[] sum = new int[n + 1];

    // 记录位数和最大的值，以便在后面挑出来
    int max = 1;

    for (int i = 1; i <= n; i++) {
        // [4, 13]   4 = 1 + 3 = (13 / 10) + 13 % 10
        // 这里不好理解，解释一下：
        // sum[1] = sum[0] + 1 % 10;
        // sum[11] = sum[1] + 1 % 10;
        // sum[111] = sum[11] + 1 % 10;
        // sum[1111] = sum[111] + 1 % 10;
        // sum[11111] = sum[1111] + 1 % 10;
        sum[i] = sum[i / 10] + i % 10;
        max = max(max, ++count[sum[i]]);
    }

    for (int num : count)
        if (num == max)
            ans++;

    return ans;
}
*/

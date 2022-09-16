/**
 * copyright 2022/1/19
 */
package com.leetcode.normal;

import org.junit.Test;
import java.util.Random;
import static com.leetcode.util.LogUtil.info;
import static com.leetcode.util.MathUtils.rand7;
import static org.junit.Assert.assertEquals;

/**
    (中等)
    470. 用 Rand7() 实现 Rand10()
        给定方法 rand7 可生成 [1,7] 范围内的均匀随机整数，试写一个方法 rand10 生成 [1,10] 范围内的均匀随机整数。
        你只能调用 rand7() 且不能调用其他方法。请不要使用系统的 Math.random() 方法。
        每个测试用例将有一个内部参数 n，即你实现的函数 rand10() 在测试时将被调用的次数。请注意，这不是传递给 rand10() 的参数。
    示例 1:
        输入: 1
        输出: [2]
    示例 2:
        输入: 2
        输出: [2, 8]
    示例 3:
        输入: 3
        输出: [3, 8, 10]
*/
public class NO470_N_ImplementRand10UsingRand7_x2 {

    @Test
    public void test() {
        int[] dp = new int[10];
        int len = 1000000;
        int res = 0;
        for (int i = 0; i < len; i++) {
            int tmp = rand10();
            dp[tmp - 1]++;
        }
        info(dp);
        for (int j : dp) {
            assert j != 0;
            res += j;
        }
        assert len == res;
    }

    public static int rand10() {
        return -1;
    }

}

















/**
// 方法1：
public int rand10() {
    while (true) {
        // 进制转换：将【7】进制 转换为【10】进制
        // [0 - 100] 的随机数
        int ans = (rand7() - 1) * 7 + (rand7() - 1);
        if (1 <= ans && ans <= 10)
            return ans;
    }
}
*/
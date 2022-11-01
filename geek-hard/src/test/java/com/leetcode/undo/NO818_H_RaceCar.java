/**
 * copyright 2022/1/19
 */
package com.leetcode.undo;

import org.junit.Test;

import java.util.Arrays;

/**
    （困难）
    818. 赛车
        你的赛车可以从位置 0 开始，并且速度为 +1 ，在一条无限长的数轴上行驶。赛车也可以向负方向行驶。
        赛车可以按照由加速指令 'A' 和倒车指令 'R' 组成的指令序列自动行驶。当收到指令 'A' 时，赛车这样行驶：
        position += speed  speed *= 2  当收到指令 'R' 时，赛车这样行驶：
        如果速度为正数，那么speed = -1  否则 speed = 1  当前所处位置不变。
        例如，在执行指令 "AAR" 后，赛车位置变化为 0 --> 1 --> 3 --> 3 ，速度变化为 1 --> 2 --> 4 --> -1 。
        给你一个目标位置 target ，返回能到达目标位置的最短指令序列的长度。
    示例 1：
        输入：target = 3
        输出：2
        解释：
            最短指令序列是 "AA" 。
            位置变化 0 --> 1 --> 3 。
    示例 2：
        输入：target = 6
        输出：5
        解释：
        最短指令序列是 "AAARA" 。
        位置变化 0 --> 1 --> 3 --> 7 --> 7 --> 6 。
*/
public class NO818_H_RaceCar {

    @Test
    public void test() {
        assert 2 == (racecar(3));// 2
        assert 5 == (racecar(6));// 5
    }

    public int racecar(int target) {
        int[] dp = new int[target + 3];
        Arrays.fill(dp, Integer.MAX_VALUE);

        dp[0] = 0;
        dp[1] = 1;
        dp[2] = 4;

        for (int t = 3; t <= target; ++t) {
            int k = 32 - Integer.numberOfLeadingZeros(t);
            if (t == (1<<k) - 1) {
                dp[t] = k;
                continue;
            }
            for (int j = 0; j < k-1; ++j)
                dp[t] = Math.min(dp[t], dp[t - (1 << (k-1)) + (1 << j)] + k-1 + j + 2);

            if ((1<<k) - 1 - t < t)
                dp[t] = Math.min(dp[t], dp[(1 << k) - 1 - t] + k + 1);
        }
        return dp[target];
    }

}
















/**
// 方法1：
public int racecar(int target) {
    int[] dp = new int[target + 3];
    Arrays.fill(dp, Integer.MAX_VALUE);

    dp[0] = 0;
    dp[1] = 1;
    dp[2] = 4;

    for (int t = 3; t <= target; ++t) {
        int k = 32 - Integer.numberOfLeadingZeros(t);
        if (t == (1<<k) - 1) {
            dp[t] = k;
            continue;
        }
        for (int j = 0; j < k-1; ++j)
            dp[t] = Math.min(dp[t], dp[t - (1 << (k-1)) + (1 << j)] + k-1 + j + 2);

        if ((1<<k) - 1 - t < t)
            dp[t] = Math.min(dp[t], dp[(1 << k) - 1 - t] + k + 1);
    }
    return dp[target];
}
*/
package com.leetcode;

import com.leetcode.util.SystemUtil;
import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;

/**
    [ARRAY]
    (中等)
    NO.739 每日温度
    给定一个整数数组 temperatures ，表示每天的温度，返回一个数组 answer ，
    其中 answer[i] 是指对于第 i 天，下一个更高温度出现在几天后。
    如果气温在这之后都不会升高，请在该位置用 0 来代替。
    示例 1:
        输入: temperatures = [73, 74, 75, 71, 69, 72, 76, 73]
        输出: [1, 1, 4, 2, 1, 1, 0, 0]
    示例 2:
        输入: temperatures = [30, 40, 50, 60]
        输出: [1, 1, 1, 0]
    示例 3:
        输入: temperatures = [30, 60, 90]
        输出: [1, 1, 0]
        提示：
            1 <= temperatures.length <= 105
            30 <= temperatures[i] <= 100
    Related Topics:栈,数组,单调栈
*/
public class NO739_N_DailyTemperatures {

    @Test
    public void test() {
        assertArrayEquals(
                new int[]{1, 1, 4, 2, 1, 1, 0, 0},
                dailyTemperatures(new int[]{73, 74, 75, 71, 69, 72, 76, 73}));
        assertArrayEquals(
                new int[]{1, 1, 1, 0},
                dailyTemperatures(new int[]{30, 40, 50, 60}));
        assertArrayEquals(
                new int[]{1, 1, 0},
                dailyTemperatures(new int[]{30, 60, 90}));
    }

    public int[] dailyTemperatures(int[] temperatures) {
        if (temperatures.length == 1)
            return new int[1];

        int len = temperatures.length;
        int[] dp = new int[len];
        dp[len - 1] = 0;
        for (int i = len - 2; i >= 0; i--) {
            for (int j = i + 1; j < len; j++) {
                if (temperatures[j] > temperatures[i]) {
                    dp[i] = j - i;
                    break;
                } else if (temperatures[j] == temperatures[i]) {
                    //如果dp[j]为0，说明后面不存在比i位置更高的温度
                    dp[i] = dp[j] == 0 ? 0 : (j - i) + dp[j];
                    break;
                } else {
                    dp[i] = 0;
                }
            }
        }
        return dp;
    }

}















/*
// 方法1：
public int[] dailyTemperatures(int[] temperatures) {
    if (temperatures.length == 1)
        return new int[1];

    int len = temperatures.length;
    int[] dp = new int[len];
    dp[len - 1] = 0;
    for (int i = len - 2; i >= 0; i--) {
        for (int j = i + 1; j < len; j++) {
            if (temperatures[j] > temperatures[i]) {
                dp[i] = j - i;
                break;
            } else if (temperatures[j] == temperatures[i]) {
                //如果dp[j]为0，说明后面不存在比i位置更高的温度
                dp[i] = dp[j] == 0 ? 0 : (j - i) + dp[j];
                break;
            } else {
                dp[i] = 0;
            }
        }
    }
    return dp;
}
*/
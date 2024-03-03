/**
 * copyright 2022/1/19
 */
package com.offer;

import org.junit.Test;
import java.util.Stack;
import static com.leetcode.util.MathUtils.getArrays;
import static org.junit.Assert.assertArrayEquals;

/**
    [ARRAY]
    (中等)
    剑指 Offer II 038. 每日温度
        请根据每日 气温 列表 temperatures ，重新生成一个列表，要求其对应位置的输出为：要想观测到更高的气温，至少需要等待的天数。如果气温在这之后都不会升高，请在该位置用 0 来代替。
    示例 1:
        输入: temperatures = {73, 74, 75, 71, 69, 72, 76, 73}
        输出: {1, 1, 4, 2, 1, 1, 0, 0}
    示例 2:
        输入: temperatures = {30, 40, 50, 60}
        输出: {1, 1, 1, 0}
    示例 3:
        输入: temperatures = {30, 60, 90}
        输出: {1, 1, 0}
*/
public class OfferII_038_N_DailyTemperatures {

    @Test
    public void test() {
        assertArrayEquals(new int[]{1, 1, 4, 2, 1, 1, 0, 0},
                dailyTemperatures(getArrays(73, 74, 75, 71, 69, 72, 76, 73)));
        assertArrayEquals(new int[]{1, 1, 1, 0},
            dailyTemperatures(getArrays(30, 40, 50, 60)));
        assertArrayEquals(new int[]{1, 1, 0},
                dailyTemperatures(getArrays(30, 60, 90)));
    }

    public int[] dailyTemperatures(int[] temperatures) {
        int[] ans = new int[temperatures.length];
        return ans;
    }

}

















/**
// 方法1：辅助栈
public int[] dailyTemperatures(int[] temperatures) {
    int[] ans = new int[temperatures.length];
    Stack<Integer> stack = new Stack<>();

    for (int i = 0; i < temperatures.length; i++) {
        while (!stack.isEmpty()
                && temperatures[i] > temperatures[stack.peek()]) {
            int pre = stack.pop();
            ans[pre] = i - pre;
        }
        stack.push(i);
    }

    return ans;
}
*/
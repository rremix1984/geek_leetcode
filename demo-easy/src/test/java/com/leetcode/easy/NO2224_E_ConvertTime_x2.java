/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

/**
    (简单)
    2224. 转化时间需要的最少操作数
        给你两个字符串 current 和 correct ，表示两个 24 小时制时间 。
        24 小时制时间 按 "HH:MM" 进行格式化，其中 HH 在 00 和 23 之间，
        而 MM 在 00 和 59 之间。最早的 24 小时制时间为 00:00 ，最晚的是 23:59 。
        在一步操作中，你可以将 current 这个时间增加 1、5、15 或 60 分钟。你可以执行这一操作 任意 次数。
        返回将 current 转化为 correct 需要的 最少操作数 。
    示例 1：
        输入：current = "02:30", correct = "04:35"
        输出：3
        解释：可以按下述 3 步操作将 current 转换为 correct ：
             - 为 current 加 60 分钟，current 变为 "03:30" 。
             - 为 current 加 60 分钟，current 变为 "04:30" 。
             - 为 current 加 5 分钟，current 变为 "04:35" 。
             可以证明，无法用少于 3 步操作将 current 转化为 correct 。
    示例 2：
        输入：current = "11:00", correct = "11:01"
        输出：1
        解释：只需要为 current 加一分钟，所以最小操作数是 1 。
*/
public class NO2224_E_ConvertTime_x2 {

    @Test
    public void test() {
        assert 3 == convertTime("02:30","04:35");
        assert 1 == convertTime("11:00","11:01");
    }

    public int convertTime(String current, String correct) {
        int m = (correct.charAt(3) - current.charAt(3)) * 10 + correct.charAt(4) - current.charAt(4);
        int h = (correct.charAt(0) - current.charAt(0)) * 10 + correct.charAt(1) - current.charAt(1);
        // 简简单单，吧时间转化为分钟，计算除数和余数的和即可
        h *= 60;
        m += h;
        return m / 60 +
                (m % 60) / 15 +
                (m % 60 % 15) / 5 +
                (m % 60 % 15 % 5);
    }
}

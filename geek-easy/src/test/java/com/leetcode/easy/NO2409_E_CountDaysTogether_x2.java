/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import com.leetcode.util.LogUtil;
import org.junit.Test;
import static com.leetcode.util.LogUtil.info;
import static com.leetcode.util.MathUtils.MONTHS;
import static java.lang.Integer.parseInt;
import static java.lang.Math.max;
import static java.lang.Math.min;

/**
    (简单)
    2409. 统计共同度过的日子数
        Alice 和 Bob 计划分别去罗马开会。
        给你四个字符串 arriveAlice ，leaveAlice ，arriveBob 和 leaveBob 。
        Alice 会在日期 arriveAlice 到 leaveAlice 之间在城市里（日期为闭区间），
        而 Bob 在日期 arriveBob 到 leaveBob 之间在城市里（日期为闭区间）。
        每个字符串都包含 5 个字符，格式为 "MM-DD" ，对应着一个日期的月和日。
        请你返回 Alice和 Bob 同时在罗马的天数。
        你可以假设所有日期都在 同一个 自然年，而且 不是 闰年。
        每个月份的天数分别为：[31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31] 。
    示例 1：
        输入：arriveAlice = "08-15", leaveAlice = "08-18", arriveBob = "08-16", leaveBob = "08-19"
        输出：3
        解释：Alice 从 8 月 15 号到 8 月 18 号在罗马。Bob 从 8 月 16 号到 8 月 19 号在罗马，他们同时在罗马的日期为 8 月 16、17 和 18 号。所以答案为 3 。
    示例 2：
        输入：arriveAlice = "10-01", leaveAlice = "10-31", arriveBob = "11-01", leaveBob = "12-31"
        输出：0
        解释：Alice 和 Bob 没有同时在罗马的日子，所以我们返回 0 。
*/
public class NO2409_E_CountDaysTogether_x2 {

    @Test
    public void test() {
        info(countDaysTogether("08-15","08-18","08-16","08-19"));
        assert 3 == countDaysTogether("08-15","08-18","08-16","08-19");
        assert 0 == countDaysTogether("10-01","10-31","11-01","12-31");
    }

    public int countDaysTogether(String arriveAlice, String leaveAlice,
                                 String arriveBob, String leaveBob) {
        int[] sum = new int[13];
        for (int i = 0; i < 12; i++)
            sum[i + 1] = sum[i] + MONTHS[i];

        int aa = call(sum, arriveAlice);
        int al = call(sum, leaveAlice);
        int ba = call(sum, arriveBob);
        int bl = call(sum, leaveBob);

        return max(0, min(al, bl) - max(aa, ba) + 1);
    }

    public int call(int[] sum, String s) {
        int m = parseInt(s.substring(0, 2));
        int d = parseInt(s.substring(3, 5));
        return sum[m - 1] + d;
    }

}
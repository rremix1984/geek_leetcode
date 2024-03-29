/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import static com.leetcode.util.LogUtil.info;
import static java.util.Arrays.sort;

/**
    [ARRAY] |||
    (简单)
    860. 柠檬水找零
        在柠檬水摊上，每一杯柠檬水的售价为5美元。顾客排队购买你的产品，
        （按账单 bills 支付的顺序）一次购买一杯。
        每位顾客只买一杯柠檬水，然后向你付5美元、10美元或20美元。
        你必须给每个顾客正确找零，也就是说净交易是每位顾客向你支付5美元。
        注意，一开始你手头没有任何零钱。
        给你一个整数数组bills，其中bills[i]是第i位顾客付的账。如果你能
        给每位顾客正确找零，返回true，否则返回false。
    示例 1：
        输入：bills = {5, 5, 5, 10, 20}
        输出：true
        解释：
            前3位顾客那里，我们按顺序收取3张5美元的钞票。
            第4位顾客那里，我们收取一张10美元的钞票，并返还5美元。
            第5位顾客那里，我们找还一张10美元的钞票和一张5美元的钞票。
            由于所有客户都得到了正确的找零，所以我们输出true。
    示例 2：
        输入：bills = {5, 5, 10, 10, 20}
        输出：false
        解释：
            前2位顾客那里，我们按顺序收取2张5美元的钞票。
            对于接下来的2位顾客，我们收取一张10美元的钞票，
            然后返还5美元。对于最后一位顾客，我们无法退回15美元，
            因为我们现在只有两张10美元的钞票。由于不是每位顾客都
            得到了正确的找零，所以答案是false。
*/
@SuppressWarnings("all")
public class NO860_E_LemonadeChange {

    @Test
    public void test() {
        assert (lemonadeChange(
                new int[]{5, 5, 5, 10, 20}));// true
        assert !(lemonadeChange(
                new int[]{5, 5, 10, 10, 20}));// false
    }

    public boolean lemonadeChange(int[] bills) {
        // 2024/2/27 NO.3
        // 2024/3/25 NO.4 一遍过，虽然过了一个月，还是能做出来
        // 2024/3/27 NO.5 忘了怎么做，需要练习
        return true;
    }

}












/*
// 方法1：
public boolean lemonadeChange(int[] bills) {
    int five = 0, ten = 0;
    for (int bill : bills) {
        if (bill == 5) {
            five++;
        } else if (bill == 10) {
            if (five == 0)
                return false;
            five--;
            ten++;
        } else {
            if (five > 0 && ten > 0) {
                five--;
                ten--;
            } else if (five >= 3) {
                five -= 3;
            } else {
                return false;
            }
        }
    }
    return true;
}
*/
/**
 * copyright 2022/1/19
 */
package com.lcp;

import org.junit.Test;

import java.util.Arrays;
import static java.lang.Math.min;

/**
    [ARRAY]
    (简单)
    LCP 40. 心算挑战
        「力扣挑战赛」心算项目的挑战比赛中，要求选手从 N 张卡牌中选出 cnt 张卡牌，若这 cnt 张卡牌数字总和为偶数，则选手成绩「有效」且得分为 cnt 张卡牌数字总和。
        给定数组 cards 和 cnt，其中 cards[i] 表示第 i 张卡牌上的数字。 请帮参赛选手计算最大的有效得分。若不存在获取有效得分的卡牌方案，则返回 0。
    示例 1：
        输入：cards = [1,2,8,9], cnt = 3
        输出：18
        解释：选择数字为 1、8、9 的这三张卡牌，此时可获得最大的有效得分 1+8+9=18。
    示例 2：
        输入：cards = [3,3,1], cnt = 1
        输出：0
        解释：不存在获取有效得分的卡牌方案。
    提示：
        1 <= cnt <= cards.length <= 10^5
        1 <= cards[i] <= 1000
*/
public class LCP_40_E_MaxmiumScore {

    @Test
    public void test() {
        assert 18 == maxmiumScore(new int[]{1,2,8,9},3);
        assert 0 == maxmiumScore(new int[]{3,3,1},1);
        assert 6 == maxmiumScore(new int[]{2,2,2,2},3);
    }

    public int maxmiumScore(int[] cards, int cnt) {
        return -1;
    }

}




















/*
// 方法1：
public int maxmiumScore(int[] cards, int cnt) {
    // 排序
    Arrays.sort(cards);

    // 对cnt=1的情况单独处理
    if (cnt == 1) {
        for (int i = cards.length-1; i >= 0; i--)
            if (cards[i]%2 == 0)
                return cards[i];
        return 0;
    }
    int sum = 0;

    // 获取倒数cnt个数
    int i = cards.length-1;
    for (; i >= cards.length-cnt; i--)
        sum += cards[i];

    // 如果和为偶数直接返回
    if (sum % 2 == 0)
        return sum;

    // i < 0表示cnt == cards.length,即只有一种选择
    if (i < 0)
        return 0;

    // 分析可知n个数的和（sum）为奇数，则n个数里一定存在奇数和偶数
    // 获取倒数cnt+1数的奇偶性：true为偶数，false为奇数
    boolean flag = cards[i] % 2 == 0;
    // 从倒数cnt数开始，向后遍历，寻找和（倒数cnt+1数）奇偶不同差值最小的数
    int index = i+1;
    while (index < cards.length && (flag == (cards[index]%2==0)))
        index++;

    // 找到后用倒数cnt+1数替换此数，如 5 7 9 14，cnt = 2, sum = 23 + 7 - 14 = 16
    return sum + cards[i] - cards[index];
}
*/
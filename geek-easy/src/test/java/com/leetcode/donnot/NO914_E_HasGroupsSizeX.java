/**
 * copyright 2022/1/19
 */
package com.leetcode.donnot;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
    (简单)
    914. 卡牌分组
        给定一副牌，每张牌上都写着一个整数。
        此时，你需要选定一个数字 X，使我们可以将整副牌按下述规则分成 1 组或更多组：
            1）每组都有 X 张牌。
            2）组内所有的牌上都写着相同的整数。
        仅当你可选的 X >= 2 时返回 true。
    示例 1：
        输入：deck = {1, 2, 3, 4, 4, 3, 2, 1}
        输出：true
        解释：可行的分组是 {1, 1}，{2, 2}，{3, 3}，{4, 4}
    示例 2：
        输入：deck = {1, 1, 1, 2, 2, 2, 3, 3}
        输出：false
        解释：没有满足要求的分组。
    提示：
        1 <= deck.length <= 104
        0 <= deck[i] < 104
 方法一：暴力
    思路
        我们枚举所有可行的 X，判断是否有满足条件的 X 即可。
    算法
        我们从 2 开始，从小到大枚举 X。
        由于每一组都有 X 张牌，那么 X 必须是卡牌总数 N 的约数。
        其次，对于写着数字 i 的牌，如果有 counti
        张，由于题目要求「组内所有的牌上都写着相同的整数」，
        那么 X 也必须是 counti 的约数，即 ：counti modX==0
        所以对于每一个枚举到的 X，我们只要先判断 X 是否为 N 的约数，
        然后遍历所有牌中存在的数字 i，看它们对应牌的数量counti
        是否满足上述要求。如果都满足等式，则 XX 为符合条件的解，
        否则需要继续令 X 增大，枚举下一个数字。
    方法二：最大公约数
    思路和算法
        由于方法一已经提及 X 一定为counti
        的约数，这个条件是对所有牌中存在的数字 i 成立的，所以我们可以推出，
        只有当X为所有counti的约数，即所有counti的最大公约数的约数时，才存在可能的分组。
        公式化来说，我们假设牌中存在的数字集合为 a, b, c, d, e那么只有当 X 为
        gcd(counta, countb, countc, countd, counte)的约数时才能满足要求。
        因此我们只要求出所有 counti 最大公约数 g，判断 g 是否大于等于 2 即可，
        如果大于等于 2，则满足条件，否则不满足。
*/
public class NO914_E_HasGroupsSizeX {

    @Test
    public void test() {
        assert hasGroupsSizeX(new int[]{1, 2, 3, 4, 4, 3, 2, 1});
        assert !hasGroupsSizeX(new int[]{1, 1, 1, 2, 2, 2, 3, 3});
    }

    public boolean hasGroupsSizeX(int[] deck) {
        int[] count = new int[10000];
        for (int c : deck)
            count[c]++;

        List<Integer> values = new ArrayList<>();
        for (int j : count)
            if (j > 0)
                values.add(j);

        for (int i = 2; i <= deck.length; i++)
            if (deck.length % i == 0) {
                boolean flag = true;
                for (int v: values)
                    if (v % i != 0) {
                        flag = false;
                        break;
                    }
                if (flag)
                    return true;
            }
        return false;
    }

}














/**
// 方法2：
public boolean hasGroupsSizeX(int[] deck) {
    int[] count = new int[10000];
    for (int c : deck)
        count[c]++;

    int group = -1;
    for (int num : count)
        if (num > 0)
            if (group == -1)
                group = num;
            else
                group = gcd(group, num);
    return group >= 2;
}

// 最大公约数
public int gcd(int x, int y) {
    if (x == 0)
        return y;
    return gcd(y % x, x);
}*/

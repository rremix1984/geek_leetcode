/**
 * copyright 2022/1/19
 */
package com.lcp.soeasy;

import org.junit.Test;

/**
    [ARRAY]
    (简单)
    (此题没意义)
    LCP 01. 猜数字
        小A和小B在玩猜数字。小B每次从1, 2, 3中随机选择一个，小A每次也从1, 2, 3
        中选择一个猜。他们一共进行三次这个游戏，请返回小A猜对了几次？
        输入的guess数组为小A每次的猜测，answer数组为小B每次的选择。
        guess和answer的长度都等于3。
    示例 1：
        输入：guess = {1, 2, 3},  answer = {1, 2, 3}
        输出：3
        解释：小A 每次都猜对了。
    示例 2：
        输入：guess = {2, 2, 3},  answer = {3, 2, 1}
        输出：1
        解释：小A 只猜对了第二次。
*/
public class LCP_01_E_Game {

    @Test
    public void test() {
        assert 3 == game(
            new int[]{1, 2, 3}, new int[]{1, 2, 3});
        assert 1 == game(
            new int[]{2, 2, 3}, new int[]{3, 2, 1});
    }

    public int game(int[] guess, int[] answer) {
        // 2024/3/4 NO.1 太简单了，没意义
        int res = 0;
        return res;
    }

}
















/*
// 方法1：
public int game(int[] guess, int[] answer) {
    int tmp =0;
    for (int i = 0; i < guess.length; i++) {
        if (guess[i] == answer[i]) {
            tmp++;
        }
    }
    return tmp;
}
*/
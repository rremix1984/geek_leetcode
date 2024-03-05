/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

/**
    [NUMBER]
    (简单)
    1025. 除数博弈
        爱丽丝和鲍勃一起玩游戏他们轮流行动。爱丽丝先手开局。
        最初，黑板上有一个数字 n 。在每个玩家的回合，玩家需要执行以下操作：
        选出任一 x，满足 0 < x < n 且 n % x == 0 。
        用 n - x 替换黑板上的数字 n 。
        如果玩家无法执行这些操作，就会输掉游戏。
        只有在爱丽丝在游戏中取得胜利时才返回 true 。
        假设两个玩家都以最佳状态参与游戏。
    示例 1：
        输入：n = 2
        输出：true
        解释：爱丽丝选择 1，鲍勃无法进行操作。
    示例 2：
        输入：n = 3
        输出：false
        解释：爱丽丝选择 1，鲍勃也选择 1，然后爱丽丝无法进行操作。

    方法1：数学法
      1. n = 1 和 n = 2 时结论成立。
      2. n > 2 时，假设 n ≤ k 时该结论成立，则 n = k + 1 时：
          1）如果 k 为偶数，则 k + 1 为奇数，x 是 k + 1 的因数，只可能是奇数，
        而奇数减去奇数等于偶数，且 k + 1 − x ≤ k，故轮到 Bob 的时候都是偶数。
        而根据我们的猜想假设 n ≤ k 的时候偶数的时候先手必胜，故此时无论 Alice 拿走什么，
        Bob 都会处于必胜态，所以 Alice 处于必败态。
          2）如果 k 为奇数，则 k + 1 为偶数，x 可以是奇数也可以是偶数，若 Alice 减去一个奇数，
        那么 k + 1 - x 是一个小于等于 kk 的奇数，此时 Bob 占有它，处于必败态，
        则 Alice 处于必胜态。
        综上所述，这个猜想是正确的。
*/
public class NO1025_E_DivisorGame_x2 {

    @Test
    public void test() {
        assert divisorGame(2);
        assert !divisorGame(3);
    }

    // 方法2：动态规划
    // f[i] 表示当前数字 i 的时候先手是处于必胜态还是必败态
    public boolean divisorGame(int n) {
        boolean[] f = new boolean[n + 5];
        f[1] = false;
        f[2] = true;
        for (int num = 3; num <= n; num++)
            for (int x = 1; x < num; x++)
                if ((num % x) == 0 && !f[num - x]) {
                    f[num] = true;
                    break;
                }

        return f[n];
    }

}

















/**
// 方法1：数学法
public boolean divisorGame(int n) {
    return n % 2 == 0;
}


// 方法2：动态规划
// f[i] 表示当前数字 i 的时候先手是处于必胜态还是必败态，
// true 表示先手必胜，false 表示先手必败
public boolean divisorGame(int n) {
    boolean[] f = new boolean[n + 5];
    f[1] = false;
    f[2] = true;
    for (int i = 3; i <= n; ++i)
        for (int j = 1; j < i; ++j)
            if ((i % j) == 0 && !f[i - j]) {
                f[i] = true;
                break;
            }
    return f[n];
}
*/
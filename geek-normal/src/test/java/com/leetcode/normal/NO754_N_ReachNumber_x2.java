/**
 * copyright 2022/1/19
 */
package com.leetcode.normal;

import org.junit.Test;
import static com.leetcode.util.LogUtil.info;
import static java.lang.Math.abs;

/**
    (中等)
    754. 到达终点数字
        在一根无限长的数轴上，你站在0的位置。终点在target的位置。
        你可以做一些数量的移动 numMoves :
        每次你可以选择向左或向右移动。
        第 i 次移动（从  i == 1 开始，到 i == numMoves ），在选择的方向上走 i 步。
        给定整数 target ，返回 到达目标所需的 最小 移动次数(即最小 numMoves ) 。
    示例 1:
        输入: target = 2
        输出: 3
        解释：第一次移动，从 0 到 1 。
             第二次移动，从 1 到 -1 。
             第三次移动，从 -1 到 2 。
    示例 2:
        输入: target = 3
        输出: 2
        解释：第一次移动，从 0 到 1 。
             第二次移动，从 1 到 3 。
    提示:
        -109 <= target <= 109
        target != 0
*/
public class NO754_N_ReachNumber_x2 {

    @Test
    public void test() {
        assert 3 == reachNumber(2);
        assert 2 == reachNumber(3);
        info(reachNumber(5));
    }

    public int reachNumber(int target) {
        target = abs(target);

        int k = 0;
        while (target > 0) {
            k++;
            target -= k;
        }

        if ((target & 1) == 0)
            return k;

        return k + 1 + k % 2;
    }

}















/**
// 方法1：
public int reachNumber(int target) {
    target = abs(target);

    int k = 0;
    while (target > 0) {
        k++;
        target -= k;
    }

    if ((target & 1) == 0)
        return k;

    return k + 1 + k % 2;
}
*/
/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

/**
    [STRING]
    (简单)
    1496. 判断路径是否相交
        给你一个字符串 path，其中 path[i] 的值可以是 'N'、'S'、'E' 或者 'W'，分别表示向北、向南、向东、向西移动一个单位。
        你从二维平面上的原点 (0, 0) 处开始出发，按 path 所指示的路径行走。
        如果路径在任何位置上与自身相交，也就是走到之前已经走过的位置，请返回 true ；否则，返回 false 。
    示例 1：
        输入：path = "NES"
        输出：false
        解释：该路径没有在任何位置相交。
    示例 2：
        输入：path = "NESWW"
        输出：true
        解释：该路径经过原点两次。
*/
public class NO1496_E_IsPathCrossing_x2 {

    @Test
    public void test() {
        assert !isPathCrossing("NES");
        assert isPathCrossing("NESWW");
    }

    public boolean isPathCrossing(String path) {
        return false;
    }

}















/**
public boolean isPathCrossing(String path) {
    Set<Long> set = new HashSet(){{add(0L);}};
    long x = 0;
    long y = 0;
    for (char c : path.toCharArray()) {
        switch (c) {
            case 'N':
                y++;
                break;
            case 'S':
                y--;
                break;
            case 'E':
                x++;
                break;
            default:
                x--;
        }
        if (!set.add((x << 32) + y))
            return true;
    }
    return false;
}
*/
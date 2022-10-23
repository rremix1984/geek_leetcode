/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

/**
    (简单)
    1232. 缀点成线
        给定一个数组 coordinates ，其中 coordinates[i] = [x, y] ， [x, y]
        表示横坐标为 x、纵坐标为 y 的点。请你来判断，这些点是否在该坐标系中属于同一条直线上。
    示例 1：
        输入：coordinates = {{1, 2}, {2, 3}, {3, 4}, {4, 5}, {5, 6}, {6, 7}}
        输出：true
    示例 2：
        输入：coordinates = {{1, 1}, {2, 2}, {3, 4}, {4, 5}, {5, 6}, {7, 7}}
        输出：false
*/
public class NO1232_E_CheckStraightLine_x2 {

    @Test
    public void test() {
        assert checkStraightLine(new int[][]{{1, 2}, {2, 3}, {3, 4}, {4, 5}, {5, 6}, {6, 7}});
        assert !checkStraightLine(new int[][]{{1, 1}, {2, 2}, {3, 4}, {4, 5}, {5, 6}, {7, 7}});
    }

    public boolean checkStraightLine(int[][] coordinates) {
        return true;
    }

}












/**
public boolean checkStraightLine(int[][] coordinates) {
    int x = coordinates[0][0] - coordinates[1][0];
    int y = coordinates[0][1] - coordinates[1][1];

    for (int i = 1; i < coordinates.length - 1; i++) {
        int disx = coordinates[i][0] - coordinates[i+1][0];
        int disy = coordinates[i][1] - coordinates[i+1][1];
        if (x * disy != y * disx)
            return false;
    }
    return true;
}
*/
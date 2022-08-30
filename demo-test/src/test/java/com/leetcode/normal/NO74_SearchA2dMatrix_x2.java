/**
 * copyright 2022/1/19
 */
package com.leetcode.normal;

import org.junit.Test;
import static com.leetcode.util.LogUtil.info;

/**
    （中等）
    74. 搜索二维矩阵
        编写一个高效的算法来判断 m x n 矩阵中，是否存在一个目标值。
        该矩阵具有如下特性：
            1）每行中的整数从左到右按升序排列。
            2）每行的第一个整数大于前一行的最后一个整数。
    示例 1：
        输入：matrix = [ [ 1, 3, 5, 7],
                        [10,11,16,20],
                        [23,30,34,60]],
            target = 3
        输出：true
*/
@SuppressWarnings("all")
public class NO74_SearchA2dMatrix_x2 {

    @Test
    public void test() {
        info(searchMatrix(new int[][]{
                { 1, 3, 5, 7},
                {10,11,16,20},
                {23,30,34,60}}, 3));// true
    }

    public boolean searchMatrix(int[][] matrix, int target) {
        return false;
    }

}














/*
// 方法1：二分查找
public boolean searchMatrix(int[][] matrix, int target) {
    int rows = matrix.length - 1;
    int columns = 0;
    while (rows >= 0 && columns < matrix[0].length) {
        int num = matrix[rows][columns];
        if (num == target)
            return true;
        else if (num > target)
            rows--;
        else
            columns++;
    }
    return false;
}
*/
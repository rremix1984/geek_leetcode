/**
 * copyright 2022/1/19
 */
package com.dp;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.leetcode.util.MathUtils.getArray;

/**
    (简单)
    119. 杨辉三角 II
        给定一个非负索引 rowIndex，返回「杨辉三角」的第 rowIndex 行。
        在「杨辉三角」中，每个数是它左上方和右上方的数的和。
    示例 1:
        输入: rowIndex = 3
        输出: {1, 3, 3, 1}
    示例 2:
        输入: rowIndex = 0
        输出: {1}
    示例 3:
        输入: rowIndex = 1
        输出: {1, 1}
    提示:
        0 <= rowIndex <= 33
*/
public class NO119_E_GetRow {

    @Test
    public void test() {
        assert getArray(1, 3, 3, 1).equals(getRow(3));
        assert getArray(1).equals(getRow(0));
        assert getArray(1, 1).equals(getRow(1));
    }

    public List<Integer> getRow(int rowIndex) {
        List<List<Integer>> C = new ArrayList<>();
        for (int i = 0; i <= rowIndex; ++i) {
            List<Integer> row = new ArrayList<>();
            for (int j = 0; j <= i; ++j)
                if (j == 0 || j == i)
                    row.add(1);
                else
                    row.add(C.get(i - 1).get(j - 1) + C.get(i - 1).get(j));
            C.add(row);
        }
        return C.get(rowIndex);
    }

}

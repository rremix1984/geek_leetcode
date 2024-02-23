/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.leetcode.util.MathUtils.getArray;

/**
    [ARRAY]
    (简单)
    989. 数组形式的整数加法
        整数的数组形式 num 是按照从左到右的顺序表示其数字的数组。
        例如，对于num = 1321，数组形式是[1, 3, 2, 1]。
        给定num，整数的数组形式，和整数k，返回整数num + k的数组形式。
    示例 1：
        输入：num = [1, 2, 0, 0], k = 34
        输出：[1, 2, 3, 4]
        解释：1200 + 34 = 1234
    示例 2：
        输入：num = [2, 7, 4], k = 181
        输出：[4, 5, 5]
        解释：274 + 181 = 455
    示例 3：
        输入：num = [2, 1, 5], k = 806
        输出：[1, 0, 2, 1]
        解释：215 + 806 = 1021
    提示：
        1 <= num.length <= 104
        0 <= num[i] <= 9
        num 不包含任何前导零，除了零本身
        1 <= k <= 104
*/
public class NO989_E_AddToArrayForm_x2 {

    @Test
    public void test() {
        assert getArray(1, 2, 3, 4).equals(
                addToArrayForm(new int[]{1, 2, 0, 0},  34));
        assert getArray(4, 5, 5).equals(
                addToArrayForm(new int[]{2, 7, 4}, 181));
        assert getArray(1, 0, 2, 1).equals(
                addToArrayForm(new int[]{2, 1, 5}, 806));
    }

    public List<Integer> addToArrayForm(int[] num, int k) {
        List<Integer> res = new ArrayList<>();
        return res;
    }

}



















/**
// 方法1：
public List<Integer> addToArrayForm(int[] num, int k) {
    List<Integer> res = new ArrayList<>();
    for (int i = num.length - 1; i >= 0; i--) {
        int sum = num[i] + k % 10;
        k /= 10;
        if (sum >= 10) {
            k++;
            sum -= 10;
        }
        res.add(sum);
    }
    for (; k > 0; k /= 10)
        res.add(k % 10);

    Collections.reverse(res);
    return res;
}

// 方法2：
public List<Integer> addToArrayForm(int[] num, int k) {
    List<Integer> res = new ArrayList<>();
    for (int i = num.length - 1; i >= 0 || k > 0; i--) {
        if (i >= 0)
            k += num[i];

        res.add(k % 10);
        k /= 10;
    }
    Collections.reverse(res);
    return res;
}
*/
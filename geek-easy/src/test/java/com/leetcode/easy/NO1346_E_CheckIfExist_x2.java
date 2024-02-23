/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import java.util.HashSet;
import java.util.Set;

/**
    [ARRAY]
    (简单)
    1346. 检查整数及其两倍数是否存在
        给你一个整数数组 arr，请你检查是否存在两个整数 N 和 M，满足 N 是 M 的两倍（即，N = 2 * M）。
        更正式地，检查是否存在两个下标 i 和 j 满足：
            i != j
            0 <= i, j < arr.length
            arr[i] == 2 * arr[j]
    示例 1：
        输入：arr = {10, 2, 5, 3}
        输出：true
        解释：N = 10 是 M = 5 的两倍，即 10 = 2 * 5 。
    示例 2：
        输入：arr = {7, 1, 14, 11}
        输出：true
        解释：N = 14 是 M = 7 的两倍，即 14 = 2 * 7 。
    示例 3：
        输入：arr = {3, 1, 7, 11}
        输出：false
        解释：在该情况下不存在 N 和 M 满足 N = 2 * M 。
*/
public class NO1346_E_CheckIfExist_x2 {

    @Test
    public void test() {
        assert checkIfExist(new int[]{10, 2, 5, 3});
        assert checkIfExist(new int[]{7, 1, 14, 11});
        assert !checkIfExist(new int[]{3, 1, 7, 11});
    }

    public boolean checkIfExist(int[] arr) {
        return false;
    }

}



















/**
// 方法1：
public boolean checkIfExist(int[] arr) {
    Set<Integer> set = new HashSet<>();
    for (int i : arr) {
        // 前者是后者的 2 倍
        if (set.contains(2 * i))
            return true;

        // 后者是前者的 2 倍
        if (i % 2 == 0 && set.contains(i / 2))
            return true;

        set.add(i);
    }
    return false;
}
*/
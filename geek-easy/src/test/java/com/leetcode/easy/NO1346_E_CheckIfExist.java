/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import java.util.*;

/**
    [ARRAY] |
    (简单)
    1346. 检查整数及其两倍数是否存在
        给你一个整数数组arr，请你检查是否存在两个整数N和M，满足N是M的两倍
        （即，N=2*M。更正式地，检查是否存在两个下标i和j满足：
          1）i != j
          2）0 <= i, j < arr.length
          3）arr[i] == 2 * arr[j]
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
public class NO1346_E_CheckIfExist {

    @Test
    public void test() {
        assert checkIfExist(new int[]{10, 2, 5, 3});
        assert checkIfExist(new int[]{7, 1, 14, 11});
        assert !checkIfExist(new int[]{3, 1, 7, 11});
    }

    public boolean checkIfExist(int[] arr) {
        // 2024/2/27 NO.3
        return false;
    }


}



















/*
// 方法1：
public boolean checkIfExist(int[] arr) {
    Set<Integer> set = new HashSet<>();
    for (int i : arr) {
        // 前者是后者的 2 倍
        if (set.contains(2 * i))
            return true;

        // 是偶数，且后者是前者的 2 倍
        if (i % 2 == 0 && set.contains(i / 2))
            return true;

        set.add(i);
    }
    return false;
}

// 方法2
public boolean checkIfExist(int[] arr) {
    Map<Integer, Integer> map = new HashMap<>();
    // 将数组元素及其索引存入哈希表
    for (int i = 0; i < arr.length; i++) {
        map.put(arr[i], i);
    }

    for (int i = 0; i < arr.length; i++) {
        // 检查2倍的元素是否存在
        if (map.containsKey(2 * arr[i]) && map.get(2 * arr[i]) != i) {
            return true;
        }
        // 如果元素是偶数，检查其一半的元素是否存在
        if (arr[i] % 2 == 0 && map.containsKey(arr[i] / 2) && map.get(arr[i] / 2) != i) {
            return true;
        }
    }

    return false;
}
*/
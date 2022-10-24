/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

import static com.leetcode.util.LogUtil.info;

/**
    (简单)
    1539. 第 k 个缺失的正整数
          给你一个 严格升序排列 的正整数数组 arr 和一个整数 k。
          请你找到这个数组里第 k 个缺失的正整数。
    示例 1：
        输入：arr = [2, 3, 4, 7, 11],  k = 5
        输出：9
        解释：缺失的正整数包括 [1, 5, 6, 8, 9, 10, 12, 13, ...] 。第 5 个缺失的正整数为 9 。
    示例 2：
        输入：arr = [1, 2, 3, 4],  k = 2
        输出：6
        解释：缺失的正整数包括 [5, 6, 7, ...] 。第 2 个缺失的正整数为 6 。
*/
public class NO1539_E_FindKthPositive_x2 {

    @Test
    public void test() {
        assert 9 == findKthPositive(new int[]{2, 3, 4, 7, 11}, 5);
        assert 6 == findKthPositive(new int[]{1, 2, 3, 4}, 2);
    }

    public int findKthPositive(int[] arr, int k) {
        int ans = -1;
        return ans;
    }

}



















/**
// 方法1：
public int findKthPositive(int[] arr, int k) {
    int ans = -1;
    int cur = 1;
    int i = 0;
    while (k > 0) {
        if (cur != arr[i]) {
            k--;
            ans = cur;
        } else if (i < arr.length - 1) {
            i++;
        }
        cur++;
    }
    return ans;
}
*/
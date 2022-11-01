/**
 * copyright 2022/1/19
 */
package com.leetcode.undo;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
   (简单) 
    1640. 能否连接形成数组
        给你一个整数数组 arr ，数组中的每个整数 互不相同 。
        另有一个由整数数组构成的数组 pieces，其中的整数也 互不相同 。
        请你以 任意顺序 连接 pieces 中的数组以形成 arr 。
        但是，不允许 对每个数组 pieces[i] 中的整数重新排序。
        如果可以连接 pieces 中的数组形成 arr ，返回 true ；否则，返回 false 。
    示例 1：
        输入：arr = {15, 88},  pieces = {{88}, {15}}
        输出：true
        解释：依次连接 {15} 和 {88}
    示例 2：
        输入：arr = {49, 18, 16},  pieces = {{16, 18, 49}}
        输出：false
        解释：即便数字相符，也不能重新排列 pieces{0}
    示例 3：
        输入：arr = {91, 4, 64, 78},  pieces = {{78}, {4, 64}, {91}}
        输出：true
        解释：依次连接 {91}、{4, 64} 和 {78}
*/
public class NO1640_E_CanFormArray {

    @Test
    public void test() {
        assert canFormArray(new int[]{15, 88}, new int[][]{{88}, {15}});
        assert !canFormArray(new int[]{49, 18, 16}, new int[][]{{16, 18, 49}});
        assert canFormArray(new int[]{91, 4, 64, 78}, new int[][]{{78}, {4, 64}, {91}});
    }

    public boolean canFormArray(int[] arr, int[][] pieces) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < pieces.length; i++)
            map.put(pieces[i][0], i);

        int i = 0;
        while (i < arr.length) {
            if (!map.containsKey(arr[i]))
                return false;

            int idx = map.get(arr[i]);
            for (int j = 0; j < pieces[idx].length; j++)
                if (arr[i + j] != pieces[idx][j])
                    return false;

            i += pieces[idx].length;
        }
        return true;
    }

}

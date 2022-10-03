/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static com.leetcode.util.MathUtils.getArray;

/**
    (简单)
    1200. 最小绝对差
        给你个整数数组 arr，其中每个元素都 不相同。
        请你找到所有具有最小绝对差的元素对，并且按升序的顺序返回。
        每对元素对 [a,b] 如下：
        a , b 均为数组 arr 中的元素
        a < b
        b - a 等于 arr 中任意两个元素的最小绝对差
    示例 1：
        输入：arr = {4, 2, 1, 3}
        输出：{{1, 2}, {2, 3}, {3, 4}}
    示例 2：
        输入：arr = {1, 3, 6, 10, 15}
        输出：{{1, 3}}
    示例 3：
        输入：arr = {3, 8, -10, 23, 19, -4, -14, 27}
        输出：{{-14, -10}, {19, 23}, {23, 27}}
*/
public class NO1200_E_MinimumAbsDifference_x2 {

    @Test
    public void test() {
        assert getArray(new int[][]{{1, 2}, {2, 3}, {3, 4}})
                .equals(minimumAbsDifference(new int[]{4, 2, 1, 3}));
        assert getArray(new int[][]{{1, 3}})
                .equals(minimumAbsDifference(new int[]{1, 3, 6, 10, 15}));
        assert getArray(new int[][]{{-14, -10}, {19, 23}, {23, 27}})
                .equals(minimumAbsDifference(new int[]{3, 8, -10, 23, 19, -4, -14, 27}));
    }

    public List<List<Integer>> minimumAbsDifference(int[] arr) {
        List<List<Integer>> ans = new ArrayList<>();
        return ans;
    }

}



















/**
// 方法1：
public List<List<Integer>> minimumAbsDifference(int[] arr) {
    Arrays.sort(arr);
    int min = Integer.MAX_VALUE;

    List<List<Integer>> ans = new ArrayList<>();
    for (int i = 1; i < arr.length; i++) {
        int cur = arr[i] - arr[i - 1];
        if (min < cur) {
            continue;
        } else if (min > cur) {
            min = cur;
            ans.clear();
        }
        List<Integer> tmp = new ArrayList<>();
        tmp.add(arr[i - 1]);
        tmp.add(arr[i]);
        ans.add(tmp);
    }
    return ans;
}
*/
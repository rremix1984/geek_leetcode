/**
 * copyright 2022/1/19
 */
package com.leetcode.donnot;

import org.junit.Test;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
    (简单)
    1005. K 次取反后最大化的数组和
        给你一个整数数组 nums 和一个整数 k ，按以下方法修改该数组：
        选择某个下标 i 并将 nums[i] 替换为 -nums[i] 。
        重复这个过程恰好 k 次。可以多次选择同一个下标 i 。
        以这种方式修改数组后，返回数组 可能的最大和 。
    示例 1：
        输入：nums = {4, 2, 3},  k = 1
        输出：5
        解释：选择下标 1 ，nums 变为 {4, -2, 3} 。
    示例 2：
        输入：nums = {3, -1, 0, 2},  k = 3
        输出：6
        解释：选择下标 (1,  2,  2) ，nums 变为 {3, 1, 0, 2} 。
    示例 3：
        输入：nums = {2, -3, -1, 5, -4},  k = 2
        输出：13
        解释：选择下标 (1,  4) ，nums 变为 {2, 3, -1, 5, 4} 。
*/
public class NO1005_E_LargestSumAfterKNegations {

    @Test
    public void test() {
        assert 6 == largestSumAfterKNegations(new int[]{3, -1, 0, 2}, 3);
        assert 13 == largestSumAfterKNegations(new int[]{2, -3, -1, 5, -4}, 2);
    }

    public int largestSumAfterKNegations(int[] nums, int k) {
        Map<Integer, Integer> freq = new HashMap<>();
        for (int num : nums)
            freq.put(num, freq.getOrDefault(num, 0) + 1);

        int ans = Arrays.stream(nums).sum();
        for (int i = -100; i < 0; i++) {
            if (freq.containsKey(i)) {
                int ops = Math.min(k, freq.get(i));
                ans += (-i) * ops * 2;
                freq.put(i, freq.get(i) - ops);
                freq.put(-i, freq.getOrDefault(-i, 0) + ops);
                k -= ops;
                if (k == 0)
                    break;
            }
        }

        if (k > 0 && k % 2 == 1 && !freq.containsKey(0)) {
            for (int i = 1; i <= 100; ++i) {
                if (freq.containsKey(i)) {
                    ans -= i * 2;
                    break;
                }
            }
        }
        return ans;
    }

}




















/**
// 方法1：
public int largestSumAfterKNegations(int[] A, int K) {
    //-100 <= A[i] <= 100,这个范围的大小是201
    int[] number = new int[201];

    //将[-100,100]映射到[0,200]上
    for (int t : A)
        number[t + 100]++;

    int i = 0;
    while (K > 0) {
        //找到A[]中最小的数字
        while (number[i] == 0)
            i++;

        number[i]--;//此数字个数-1
        number[200 - i]++;//其相反数个数+1

        //若原最小数索引>100,则新的最小数索引应为200-i.(索引即number[]数组的下标)
        if (i > 100)
            i = 200 - i;

        K--;
    }

    int sum = 0;

    //遍历number[]求和
    for (int j = i; j <number.length; j++)
        //j-100是数字大小,number[j]是该数字出现次数.
        sum += (j-100) * number[j];

    return sum;
}
*/
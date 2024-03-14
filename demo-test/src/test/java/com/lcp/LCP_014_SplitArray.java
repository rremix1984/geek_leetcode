package com.lcp;

import org.junit.Test;
import java.util.Map;
import java.util.HashMap;

/**
    [ARRAY] ||
    （困难）
    LCP.014 切分数组
        给定一个整数数组nums，小李想将nums切割成若干个非空子数组，使得每个
        子数组【最左边的数】和【最右边的数】的最大公约数大于1。为了减少他的工作量，
        请求出【最少】可以切成多少个【子数组】。
    示例 1：
        输入：nums = [2, 3, 3, 2, 3, 3]
        输出：2
        解释：最优切割为[2, 3, 3, 2] 和 [3, 3]。第一个子数组头尾数字的
             最大公约数为2，第二个子数组头尾数字的最大公约数为3。
    示例 2：
        输入：nums = [2, 3, 5, 7]
        输出：4
        解释：只有一种可行的切割：[2], [3], [5], [7]
        限制：
            1 <= nums.length <= 10^5
            2 <= nums[i] <= 10^6
*/
public class LCP_014_SplitArray {

    @Test
    public void test() {
        assert 2 == splitArray(new int[]{2, 3, 3, 2, 3, 3});
        assert 4 == splitArray(new int[]{2, 3, 5, 7});
    }

    private int splitArray(int[] nums) {
        // 2024/3/1 NO.1
        // 2024/3/12 NO.2


        return 0;
    }

}

















/*
// 方法1：
private int[] minPrime = new int[1000000 + 1];
private Map<Integer, Integer> primeMinIndex = new HashMap<>();

private void init() {
    for (int i = 2; i < minPrime.length; i++)
        if (minPrime[i] < 2)
            for (int j = i; j < minPrime.length; j += i)
                minPrime[j] = i;
}

public int splitArray(int[] nums) {
    init();
    int[] ans = new int[nums.length];
    for (int i = 0; i < nums.length; i++) {
        int n = nums[i];
        ans[i] = i > 0 ? ans[i - 1] + 1 : 1;
        while (n > 1) {
            int factor = minPrime[n];
            int minIndex = -1;
            if (primeMinIndex.containsKey(factor))
                minIndex = primeMinIndex.get(factor);
            else
                minIndex = i; primeMinIndex.put(factor, minIndex);

            if (minIndex > 0)
                ans[i] = Math.min(ans[i], ans[minIndex - 1] + 1);
            else
                ans[i] = 1;

            if (ans[i] < ans[minIndex])
                primeMinIndex.put(factor, i);

            n = n / factor;
        }
    }
    return ans[nums.length - 1];
}
*/
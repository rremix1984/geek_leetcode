package com.leetcode;

import org.junit.Test;
import java.util.Arrays;
import static java.lang.Integer.MAX_VALUE;
import static java.lang.Math.*;
import static java.util.Arrays.fill;

/**
    [ARRAY]
    (中等)
    NO.164 最大间距
        给定一个无序的数组 nums，返回数组在排序之后，相邻元素之间最大的差值。
        如果数组元素个数小于 2，则返回0。
        您必须编写一个在「线性时间」内运行并使用「线性额外空间」的算法。
    示例 1:
        输入: nums = [3, 6, 9, 1]
        输出: 3
        解释: 排序后的数组是 [1, 3, 6, 9], 其中相邻元素
             (3,6) 和 (6,9) 之间都存在最大差值 3。
    示例 2:
        输入: nums = [10]
        输出: 0
        解释: 数组元素个数小于 2，因此返回 0。
    提示:
        1 <= nums.length <= 105
        0 <= nums[i] <= 109
    Related Topics:数组,桶排序,基数排序,排序
    解题思路：
        以nums = [0, 3, 5, 6, 23, 28, 29, 33, 40]为例
        由于题目要求为线性时间复杂度 所以不能用Arrays.sort
        提交后发现，前面的那些人很多用了sort函数，真的不讲武德，我还是老老实实的用桶排序。
        这里用桶排序，只用考虑桶间的排序，不用考虑桶内的排序
        用后一个桶的最小值减前一个桶的最大值，可以得到最大间距。
*/
public class NO164_N_MaximumGap {

    @Test
    public void test() {
        assert 3 == maximumGap(new int[]{3, 6, 9, 1});
        assert 0 == maximumGap(new int[]{10});
    }

    // 线性时间复杂度和空间复杂度 不能用Arrays.sort
    public int maximumGap(int[] nums) {
        // 2024/3/12 NO.1
        if (nums.length < 2)
            return 0;

        int len = nums.length;

        // 找出最大值和最小值 为了方便后面确定桶的数量
        int max = -1;
        int min = MAX_VALUE;
        for (int num : nums) {
            max = max(num, max);
            min = min(num, min);
        }

        // 排除nums全部为一样的数字，nums = [1,1,1,1,1,1];
        if (max - min == 0)
            return 0;

        // 用于存放每个桶的最大值
        int[] bucketMin = new int[len - 1];
        // 用于存放每个桶的最小值
        int[] bucketMax = new int[len - 1];

        fill(bucketMax, -1);
        fill(bucketMin, MAX_VALUE);

        // 确定桶的间距
        int interval = (int) ceil((double)(max - min) / (len - 1));
        for (int num : nums) {
            // 找到每一个值所对应桶的索引
            int index = (num - min) / interval;
            if (num == min || num == max)
                continue;

            // 更新每个桶的数据
            bucketMax[index] = max(bucketMax[index], num);
            bucketMin[index] = min(bucketMin[index], num);
        }

        // maxGap 表示桶之间最大的差距
        int maxGap = 0;
        // preMax 表示前一个桶的最大值
        int preMax = min;
        for (int i = 0; i < len - 1; i++) {
            // 表示某一个桶为空
            // 但凡某一个桶不为空，都会在前面的数据中更新掉bucketMax的值
            if (bucketMax[i] == -1) continue;
            maxGap = max(bucketMin[i] - preMax, maxGap);
            preMax = bucketMax[i];
        }
        // [1, 10000000]
        maxGap = max(maxGap, max - preMax);
        return maxGap;
    }

}

















/*
// 方法1：
// 线性时间复杂度和空间复杂度 不能用Arrays.sort
public int maximumGap(int[] nums) {
    if (nums.length < 2)
        return 0;

    int len = nums.length;

    // 找出最大值和最小值 为了方便后面确定桶的数量
    int max = -1;
    int min = MAX_VALUE;
    for (int j : nums) {
        max = max(j, max);
        min = min(j, min);
    }

    // 排除nums全部为一样的数字，nums = [1,1,1,1,1,1];
    if (max - min == 0) return 0;
    // 用于存放每个桶的最大值
    int[] bucketMin = new int[len - 1];
    // 用于存放每个桶的最小值
    int[] bucketMax = new int[len - 1];
    Arrays.fill(bucketMax, -1);
    Arrays.fill(bucketMin, MAX_VALUE);

    // 确定桶的间距
    int interval = (int)Math.ceil((double)(max - min) / (len - 1));

    for (int num : nums) {
        // 找到每一个值所对应桶的索引
        int index = (num - min) / interval;
        if (num == min || num == max)
            continue;

        // 更新每个桶的数据
        bucketMax[index] = max(bucketMax[index], num);
        bucketMin[index] = min(bucketMin[index], num);
    }

    // maxGap 表示桶之间最大的差距
    int maxGap = 0;
    // preMax 表示前一个桶的最大值
    int preMax = min;
    for (int i = 0; i < len - 1; i++) {
        // 表示某一个桶为空
        // 但凡某一个桶不为空，都会在前面的数据中更新掉bucketMax的值
        if (bucketMax[i] == -1) continue;
        maxGap = max(bucketMin[i] - preMax, maxGap);
        preMax = bucketMax[i];
    }
    // [1, 10000000]
    maxGap = max(maxGap, max - preMax);
    return maxGap;
}
*/
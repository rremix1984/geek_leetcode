package com.interval;

import org.junit.Test;

import static com.leetcode.util.SwapUtil.swap;

/**
    [ARRAY] |
    (中等)
    Interval.10.11 峰与谷
    在一个整数数组中，“峰”是大于或等于相邻整数的元素，相应地，“谷”是小于或等于
    相邻整数的元素。例如，在数组{5, 8, 4, 2, 3, 4, 6}中，{8, 6}是峰，
    {5, 2}是谷。现在给定一个整数数组，将该数组按峰与谷的交替顺序排序。
    示例:
        输入: [5, 3, 1, 2, 3]
        输出: [5, 1, 3, 2, 3]
        提示：
        nums.length <= 10000
    Related Topics:贪心/数组/排序
    解题方法:
        用一个变量用来标识当前是峰还是谷。
        当为峰的时候，如果当前元素比他后一个元素大，那就调整下位置，大的元素放后面；
        当为谷的时候，如果当前元素比他后一个元素小，也调整下位置，小的元素放后面。
        这里大家可能会有一个疑问，调整完会导致之前的峰或者谷发生变化吗？
        是不会的，这里举一个例子。
        abc. a->b为峰，b->c为谷
        当c比b小时不用调整，当c比b大时，顺序调整为acb，因为a<b,而c>b, 所以a<c依然成立，前面依然是一个峰。
        每调整一次，下一次的标识变一下，直到所有元素都处理完。
*/
public class Interval_10_11_WiggleSort {

    @Test
    public void test() {

    }

    public void wiggleSort(int[] nums) {
        int i = 0;

        boolean peak = true;
        while (i < nums.length && i + 1 < nums.length) {
            if (peak) {
                if (nums[i] > nums[i + 1])
                    swap(nums, i, i + 1);
            } else {
                if (nums[i] < nums[i + 1])
                    swap(nums, i, i + 1);
            }
            i++;
            peak = !peak;
        }
    }

}

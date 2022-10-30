/**
 * copyright 2022/1/19
 */
package com.leetcode.donnot;

import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static com.leetcode.util.MathUtils.getArray;

/**
    (简单)
    题目描述：
        给定一个排序的整数数组nums，其中元素的范围在闭区间[lower, upper]当中，
        返回不包含在数组中的缺失区间。
    示例：
        输入: nums = [0, 1, 3, 50, 75], lower = 0 和 upper = 99,
        输出: ["2", "4->49", "51->74", "76->99"]
    思路：
        专门写一个函数用于构造缺失的区间，传递的参数设置为两个开区间
        根据区间的开头，中间，结尾三类来分别寻找缺失的区间
        对于边界情况，全空的话，先特殊处理
*/
public class NO163_E_FindMissingRanges {

    @Test
    public void test() {
        assert getArray("2", "4->49", "51->74", "76->99").equals(
            findMissingRanges(new int[]{0, 1, 3, 50, 75},0, 99));
    }

    public List<String> findMissingRanges(int[] nums, int lower, int upper) {
        List<String> res = new ArrayList<>();
        int n = nums.length;

        //全缺
        if (n == 0) {
            find(res,lower - 1, upper + 1);
            return res;
        }

        //开头
        if (nums[0] != lower)
            find(res, lower - 1, nums[0]);

        //中间,两两比较  （这里不能忽略开头和结尾，因为可能还要进行拼接）
        for (int i = 0; i < n - 1; i++)
            if (nums[i + 1] != nums[i] + 1)
                find(res, nums[i], nums[i + 1]);

        //结尾
        if (nums[n - 1] != upper)
            find(res, nums[n - 1], upper + 1);

        return res;
    }

    //传递的是开区间
    public void find(List<String> res, int left, int right) {
        StringBuilder sb = new StringBuilder();
        //只缺1个数
        if (left + 2 == right)
            sb.append(left + 1);
        else
            sb.append(left + 1).append("->").append(right - 1);
        res.add(sb.toString());
    }

}





















/**
public List<String> findMissingRanges(int[] nums, int lower, int upper) {
    List<String> res = new ArrayList<>();
    int n = nums.length;

    //全缺
    if (n == 0) {
        find(res,lower - 1, upper + 1);
        return res;
    }

    //开头
    if (nums[0] != lower)
        find(res, lower - 1, nums[0]);

    //中间,两两比较  （这里不能忽略开头和结尾，因为可能还要进行拼接）
    for (int i = 0; i < n - 1; i++)
        if (nums[i + 1] != nums[i] + 1)
            find(res, nums[i], nums[i + 1]);

    //结尾
    if (nums[n - 1] != upper)
        find(res, nums[n - 1], upper + 1);

    return res;
}

//传递的是开区间
public void find(List<String> res, int left, int right) {
    StringBuilder sb = new StringBuilder();
    //只缺1个数
    if (left + 2 == right)
        sb.append(left + 1);
    else
        sb.append(left + 1).append("->").append(right - 1);
    res.add(sb.toString());
}
*/
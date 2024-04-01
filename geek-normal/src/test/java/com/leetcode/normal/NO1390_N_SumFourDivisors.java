package com.leetcode.normal;

import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.leetcode.util.SystemUtil.printArray;
import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

/**
    [ARRAY] |
    (中等)
    NO.1390 四因数
    给你一个整数数组 nums，请你返回该数组中恰有四个因数的这些整数的各因数之和。
    如果数组中不存在满足题意的整数，则返回 0 。
    示例 1：
        输入：nums = [21, 4, 7]
        输出：32
        解释：
            21 有 4 个因数：1, 3, 7, 21
            4 有 3 个因数：1, 2, 4
            7 有 2 个因数：1, 7
            答案仅为 21 的所有因数的和。
    示例 2:
        输入: nums = [21, 21]
        输出: 64
    示例 3:
        输入: nums = [1, 2, 3, 4, 5]
        输出: 0
    提示：
        1 <= nums.length <= 104
        1 <= nums[i] <= 105
    Related Topics:数组,数学
*/
public class NO1390_N_SumFourDivisors {

    @Test
    public void test() {
        assert 32 == sumFourDivisors(new int[]{21, 4, 7});
        assert 64 == sumFourDivisors(new int[]{21, 21});
        assert 0 == sumFourDivisors(new int[]{1, 2, 3, 4, 5});
    }

    public int sumFourDivisors(int[] nums) {
        // 2024/4/1 NO.1
        int res = 0;
        for (int item : nums)
            res += sum(item);

        return res;
    }

    private int sum(int num) {
        // TODO

        return -1;
    }

}















/*
// 方法1：
public int sumFourDivisors(int[] nums) {
    int result = 0;
    for (int item : nums)
        result += sum(item);

    return result;
}

private int sum(int num) {
    List<Integer> list = new ArrayList<>();
    int count = (int) sqrt(num);
    for (int i = 1; i <= count; i++)
        if (num % i == 0 && !list.contains(i) && !list.contains(num / i)) {
            list.add(i);
            if (!list.contains(num / i))
                list.add(num / i);
        }

    if (list.size() == 4)
        return list.stream().distinct().mapToInt(o -> o).sum();

    return 0;
}
*/
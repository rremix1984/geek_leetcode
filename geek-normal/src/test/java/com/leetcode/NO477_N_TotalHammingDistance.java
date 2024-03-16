package com.leetcode;

import org.junit.Test;

/**
    [ARRAY]
    (中等)
    NO.477 汉明距离总合
    两个整数的 汉明距离 指的是这两个数字的二进制数对应位不同的数量。
    给你一个整数数组 nums，请你计算并返回 nums 中任意两个数之间 汉明距离的总和 。
    示例 1：
        输入：nums = [4, 14, 2]
        输出：6
        解释：在二进制表示中，
            4  表示为 0100 ，
            14 表示为 1110 ，
            2  表示为 0010 。（这样表示是为了体现后四位之间关系）
        所以答案为：
            HammingDistance(4, 14)
          + HammingDistance(4, 2)
          + HammingDistance(14, 2)
          = 2 + 2 + 2 = 6
    示例 2：
        输入：nums = [4, 14, 4]
        输出：4
    提示：
        1 <= nums.length <= 104
        0 <= nums[i] <= 109
        给定输入的对应答案符合 32-bit 整数范围
    Related Topics:位运算,数组,数学
    方法一：逐位统计
        在计算汉明距离时，我们考虑的是同一比特位上的值是否不同，而不同比特位之间是互不影响的。
        对于数组 nums 中的某个元素 val，若其二进制的第 i 位为 1，我们只需统计nums中有
        多少元素的第 i 位为 0，即计算出了 val 与其他元素在第 i 位上的汉明距离之和。
        具体地，若长度为 n 的数组 nums 的所有元素二进制的第 i 位共有 c 个 1，n−c 个 0，
        则些元素在二进制的第 i 位上的汉明距离之和为 c⋅(n−c)
        我们可以从二进制的最低位到最高位，逐位统计汉明距离。将每一位上得到的汉明距离累加即为答案。
        具体实现时，对于整数val二进制的第i位，我们可以用代码 (val >> i) & 1 来取出其第i位的值。
        此外，由于 10^9 < 2^30，我们可以直接从二进制的第 0 位枚举到第 29 位。
*/
public class NO477_N_TotalHammingDistance {

    @Test
    public void test1() {
        assert 6 == totalHammingDistance(new int[]{4, 14, 2});
        assert 4 == totalHammingDistance(new int[]{4, 14, 4});
    }

    public int totalHammingDistance(int[] nums) {
        int ans = 0;
        int n = nums.length;
        for (int i = 0; i < 30; i++) {
            int c = 0;
            for (int val : nums)
                c += (val >> i) & 1;

            ans += c * (n - c);
        }
        return ans;
    }

}














/*
// 方法1：
public int totalHammingDistance(int[] nums) {
    int ans = 0;
    int n = nums.length;
    for (int i = 0; i < 30; ++i) {
        int c = 0;
        for (int val : nums) {
            c += (val >> i) & 1;
        }
        ans += c * (n - c);
    }
    return ans;
}
*/
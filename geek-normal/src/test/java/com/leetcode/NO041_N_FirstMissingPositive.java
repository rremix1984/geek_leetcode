/**
 * copyright@2019/12/23 lyc
 */
package com.leetcode;

import org.junit.Test;

import static com.leetcode.util.SystemUtil.printArr;
import static java.lang.Math.abs;

/**
    [ARRAY]
    (中等)
    NO.041 缺失的第一个正整数
    给你一个未排序的整数数组 nums，请你找出其中没有出现的最小的正整数。
    请你实现时间复杂度为 O(n) 并且只使用常数级别额外空间的解决方案。
    示例 1：
        输入：nums = [1, 2, 0]
        输出：3
        解释：范围 [1,2] 中的数字都在数组中。
    示例 2：
        输入：nums = [3, 4, -1, 1]
        输出：2
        解释：1 在数组中，但 2 没有。
    示例 3：
        输入：nums = [7, 8, 9, 11, 12]
        输出：1
        解释：最小的正数 1 没有出现。
    提示：
        1 <= nums.length <= 10 ^ 5
        -2 ^ 31 <= nums[i] <= 2 ^ 31 - 1
    Related Topics:数组,哈希表
*/
public class NO041_N_FirstMissingPositive {

    @Test
    public void test() {
        assert 3 == firstMissingPositive(new int[]{1, 2, 0});
        assert 2 == firstMissingPositive(new int[]{3, 4, -1, 1});
        assert 1 == firstMissingPositive(new int[]{7, 8, 9, 11, 12});
    }

    public int firstMissingPositive(int[] nums) {
        // 2024/3/19 NO.1
        // 2024/3/20 NO.2 理解不了，脑子不转了
        int n = nums.length;

        /*
         第一步：预处理数组
         这个循环将数组中所有非正数（包括0和负数）替换为n+1。
         之所以选n+1是因为数组的长度为n，那么数组中可能出现的
         “第一个缺失的正数”最大也就是n+1（这种情况发生在数组
         恰好包含了1到n的所有正数）。替换这些值的目的是为了简化后续逻辑，
         让所有负数和0不会影响我们标记数组中存在的正数。
        */
        for (int i = 0; i < n; i++)
            if (nums[i] <= 0)
                nums[i] = n + 1;

        /*
          第二步：标记存在的正数
          这个循环遍历数组，对于每个元素，先取其绝对值（因为数组中的元素可
          能已经被标记为负数），判断如果这个绝对值num小于等于n，则将数组中
          num-1位置的元素标记为负数（如果还不是负数的话）。这里的abs是取
          绝对值的函数。
          这个步骤的目的是利用数组的索引来标记哪些正数是存在的。
          比如，如果nums中存在正数1，那么nums[0]（即nums[1-1]）会
          被标记为负数。
        */
        for (int i = 0; i < n; i++) {
            int num = abs(nums[i]);
            if (num <= n)
                nums[num - 1] = -abs(nums[num - 1]);
        }

        /*
         第三步：寻找第一个缺失的正数
         这个循环遍历数组，寻找第一个还是正数的元素，其索引 i 加 1 即为缺失
         的第一个正数。因为如果一个数x存在于数组中，那么nums[x-1]应该被
         标记为负数。如果某个索引i对应的nums[i]仍然为正数，那说明i+1这个
         数不存在于原数组中。
        */
        for (int i = 0; i < n; i++)
            if (nums[i] > 0)
                return i + 1;

        /*
         返回结果
         如果数组中的1到n都被标记了（即都存在），那么根据前面的逻辑，
         缺失的第一个正数就是n+1。
         综上所述，这个算法巧妙地利用数组本身的索引作为哈希表，
         通过标记存在的正数，来找出缺失的第一个正数。
        */
        return n + 1;
    }

}















/*
// 方法1：
public int firstMissingPositive(int[] nums) {
    int n = nums.length;
    for (int i = 0; i < n; i++)
        if (nums[i] <= 0)
            nums[i] = n + 1;

    for (int i = 0; i < n; i++) {
        int num = abs(nums[i]);
        if (num <= n)
            nums[num - 1] = -abs(nums[num - 1]);
    }

    for (int i = 0; i < n; i++)
        if (nums[i] > 0)
            return i + 1;

    return n + 1;
}
*/
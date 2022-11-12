/**
 * copyright 2022/1/19
 */
package com.leetcode.normal;

import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;

/**
    (中等)
    260. 只出现一次的数字 III
        给你一个整数数组nums，其中恰好有两个元素只出现一次，
        其余所有元素均出现两次。找出只出现一次的那两个元素。
        你可以按任意顺序返回答案。你必须设计并实现线性时间复
        杂度的算法且仅使用常量额外空间来解决此问题。
    示例 1：
        输入：nums = [1, 2, 1, 3, 2, 5]
        输出：[3, 5]
        解释：[5, 3] 也是有效的答案。
    示例 2：
        输入：nums = [-1, 0]
        输出：[-1, 0]
    示例 3：
        输入：nums = [0, 1]
        输出：[1, 0]
    提示：
        2 <= nums.length <= 3 * 10 ^ 4
        -2 ^ 31 <= nums[i] <= 2 ^ 31 - 1
        除两个只出现一次的整数外，nums中的其他数字都出现两次
*/
public class NO260_N_SingleNumber_x2 {

    @Test
    public void test() {
        assertArrayEquals(new int[]{5, 3},
                singleNumber(new int[]{1, 2, 1, 3, 2, 5}));
        assertArrayEquals(new int[]{0, -1},
                singleNumber(new int[]{-1, 0}));
        assertArrayEquals(new int[]{0, 1},
                singleNumber(new int[]{0, 1}));
        assertArrayEquals(new int[]{3, 7},
                singleNumber(new int[]{1, 1, 2, 2, 3, 5, 5, 6, 7, 6}));
    }

    public int[] singleNumber(int[] nums) {
        int[] ans = new int[2];
        return ans;
    }

}


















/**
// 方法1：
public int[] singleNumber(int[] nums) {
    // 把所有的元素进行异或操作，最终得到一个异或值。
    // 因为是不同的两个数字，所以这个值必定不为0
    int xor = 0;
    for (int num : nums)
        xor ^= num;

    // 取异或值最后一个二进制位为1的数字作为mask，
    // 如果是 1 则表示两个数字在这一位上不同。
    // 例如： 5 就是 00000101
    //      -5 就是 11111011，逻辑就是按位取反 再+1
    int mask = xor & (-xor);

    // 通过与这个 mask 进行与操作，如果为 0 的分为一个数组，
    // 为 1 的分为另一个数组。
    // 这样就把问题降低成了：“有一个数组每个数字都出现两次，有一个数字只出现了一次，
    // 求出该数字”。
    // 对这两个子问题分别进行全异或就可以得到两个解。也就是最终的数组了。
    int[] ans = new int[2];
    for (int num : nums)
        if ((num & mask) == 0)
            ans[0] ^= num;
        else
            ans[1] ^= num;

    return ans;
}
*/
/**
 * copyright 2022/1/19
 */
package com.leetcode.undo;

import org.junit.Test;
import java.util.Arrays;
import static java.lang.Math.max;

/**
    (中等)
    611. 有效三角形的个数
        给定一个包含非负整数的数组 nums ，返回其中可以组成三角形三条边的三元组个数。
    示例 1:
        输入: nums = [2,2,3,4]
        输出: 3
        解释:有效的组合是:
        2,3,4 (使用第一个 2)
        2,3,4 (使用第二个 2)
        2,2,3
    示例 2:
        输入: nums = [4,2,3,4]
        输出: 4
    提示:
        1 <= nums.length <= 1000
        0 <= nums[i] <= 1000

    方法二：排序 + 双指针
        我们可以对方法一进行优化。
        我们将当 a = nums[i], b = nums[j] 时，
    最大的满足 nums[k] < nums[i] + nums[j] 的下标 kk 记为 k_{i, j}。
    可以发现，如果我们固定 i，那么随着 j 的递增，不等式右侧 nums[i] + nums[j] 也是递增的，
    因此 k_{i, j}也是递增的。
    这样一来，我们就可以将 j 和 k 看成两个同向（递增）移动的指针，将方法一进行如下的优化：
    我们使用一重循环枚举 i。当 i 固定时，我们使用双指针同时维护 j 和 k，它们的初始值均为 i；
    我们每一次将 j 向右移动一个位置，即 j ← j + 1，并尝试不断向右移动 k，
    使得 kk 是最大的满足 nums[k] < nums[i] + nums[j] 的下标。我们将 max(k − j, 0) 累加入答案。
    当枚举完成后，我们返回累加的答案即可。
        细节
        与方法一中「二分查找的失败」类似，方法二的双指针中，也会出现不存在满足
    nums[k] < nums[i] + nums[j] 的下标的情况。
    此时，指针 k 不会出现在指针 j 的右侧，即 k − j ≤ 0，
    因此我们需要将 k - j 与 0 中的较大值累加入答案，防止错误的负数出现。
*/
public class NO611_N_TriangleNumber {

    @Test
    public void test() {
        assert 3 == triangleNumber(new int[]{2,2,3,4});
        assert 4 == triangleNumber(new int[]{4,2,3,4});
    }

    public int triangleNumber(int[] nums) {
        Arrays.sort(nums);
        int ans = 0;
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1, k = i; j < nums.length; j++) {
                while (k < nums.length - 1 && nums[k + 1] < nums[i] + nums[j])
                    k++;

                ans += max(k - j, 0);
            }
        }
        return ans;
    }

}

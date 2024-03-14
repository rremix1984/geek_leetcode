/**
 * copyright 2022/1/19
 */
package com.leetcode.normal;

import org.junit.Test;
import java.util.Arrays;
import static com.leetcode.util.SwapUtil.swap;
import static com.leetcode.util.SystemUtil.printArr;
import static org.junit.Assert.assertArrayEquals;

/**
    [ARRAY] |
    (中等,面试题)
    75. 颜色分类
        给定一个包含红色、白色和蓝色、共n个元素的数组nums，
        原地对它们进行排序，使得相同颜色的元素相邻，并按照红色、白色、蓝色顺序排列。
        我们使用整数 0、 1 和 2 分别表示红色、白色和蓝色。
        必须在不使用库的sort函数的情况下解决这个问题。
    示例 1：
        输入：nums = [2, 0, 2, 1, 1, 0]
        输出：[0, 0, 1, 1, 2, 2]
    示例 2：
        输入：nums = [2, 0, 1]
        输出：[0, 1, 2]
    提示：
        n == nums.length
        1 <= n <= 300
        nums[i] 为 0、1 或 2
    方法二：双指针
    我们用指针 p_0 来交换 0，p_1 来交换 1，初始值都为 0。当我们从左向右遍历整个数组时：
        如果找到了 1，那么将其与 nums[p1] 进行交换，并将 p_1 向后移动一个位置，这与方法一是相同的；
        如果找到了 0，那么将其与 nums[p0] 进行交换，并将 p_0 向后移动一个位置。这样做是正确的吗？
    我们可以注意到，因为连续的 0 之后是连续的 1，因此如果我们将 0 与 nums[p_0] 进行交换，
    那么我们可能会把一个 1 交换出去。当 p_0 < p_1 时，我们已经将一些 1 连续地放在头部，
    此时一定会把一个 1 交换出去，导致答案错误。因此，如果 p_0 < p_1，
    那么我们需要再将 nums[i] 与 nums[p1] 进行交换，其中 i 是当前遍历到的位置，
    在进行了第一次交换后，nums[i] 的值为 1，我们需要将这个 1 放到「头部」的末端。
    在最后，无论是否有 p_0 < p_1，我们需要将 p_0 和 p_1 均向后移动一个位置，
    而不是仅将 p_0 向后移动一个位置。

    方法三：双指针
        与方法二类似，我们也可以考虑使用指针 p_0 来交换 0，p_2来交换 2。此时，p_0的初始值仍然为 0，
    而 p_2 的初始值为 n-1。在遍历的过程中，我们需要找出所有的 0 交换至数组的头部，并且找出所有的 2 交换至数组的尾部。
    由于此时其中一个指针 p_2是从右向左移动的，因此当我们在从左向右遍历整个数组时，如果遍历到的位置超过了 p_2，
    那么就可以直接停止遍历了。具体地，我们从左向右遍历整个数组，设当前遍历到的位置为 i，
    对应的元素为 nums[i]；
        1）如果找到了0，那么与前面两种方法类似，将其与nums[p0]进行交换，并将p_0向后移动一个位置；
        2）如果找到了2，那么将其与 nums[p2] 进行交换，并将 p_2向前移动一个位置。
    这样做是正确的吗？可以发现，对于第二种情况，当我们将 nums[i] 与 nums[p2] 进行交换之后，
    新的 nums[i] 可能仍然是 2，也可能是 0。然而此时我们已经结束了交换，开始遍历下一个元素 nums[i+1]，
    不会再考虑 nums[i] 了，这样我们就会得到错误的答案。
        因此，当我们找到2时，我们需要不断地将其与 nums[p2] 进行交换，直到新的 nums[i] 不为 2。此时，
        如果nums[i]为0，那么对应着第一种情况；
        如果nums[i]为1，那么就不需要进行任何后续的操作。
*/
public class NO075_N_SortColors {

    @Test
    public void test() {
        int[] source = new int[]{2, 0, 2, 1, 1, 0};
        int[] target = new int[]{0, 0, 1, 1, 2, 2};
        sortColors(source);
        assertArrayEquals(source, target);

        int[] source2 = new int[]{2, 0, 1};
        int[] target2 = new int[]{0, 1, 2};
        sortColors(source2);
        assertArrayEquals(source2, target2);

        int[] source3 = new int[]{2, 1, 2};
        int[] target3 = new int[]{1, 2, 2};
        sortColors(source3);
        assertArrayEquals(source3, target3);
    }

    // 方法3：
    public void sortColors(int[] nums) {
        // 2024/3/13 NO.1 双指针法,典型题，有点意思
    }

}
















/*
// 方法1：
public void sortColors(int[] nums) {
    int p0 = 0;
    int p1 = 0;
    int cur = 0;
    for (int n : nums) {
        if (n == 1) {
            swap(nums, cur, p1++);
        } else if (n == 0) {
            swap(nums, cur, p0++);
            if (p0 < p1)
                swap(nums, cur, p1);
            p1++;
        }
        cur++;
    }
}

// 方法2：
public void sortColors(int[] nums) {
    int p0 = 0;
    int p2 = nums.length - 1;
    for (int i = 0; i <= nums.length - 1; i++) {
        while (i <= p2 && nums[i] == 2)
            swap(nums, i, p2--);

        if (nums[i] == 0)
            swap(nums, i, p0++);
    }
}
*/
/**
 * copyright 2022/1/19
 */
package com.leetcode.hard;

import org.junit.Test;

import static java.util.stream.IntStream.range;
import static org.junit.Assert.assertEquals;

/**
    （困难）
    410. 分割数组的最大值
        给定一个非负整数数组 nums 和一个整数 m ，你需要将这个数组
        分成 m 个非空的连续子数组。设计一个算法使得这 m 个子数组各
        自和的最大值最小。
    示例 1：
        输入：nums = [7, 2, 5, 10, 8], m = 2
        输出：18
        解释：
        一共有四种方法将 nums 分割为 2 个子数组。
        其中最好的方式是将其分为 [7, 2, 5] 和 [10, 8] 。
        因为此时这两个子数组各自的和的最大值为18，在所有情况中最小。
    示例 2：
        输入：nums = [1, 2, 3, 4, 5], m = 2
        输出：9
    示例 3：
        输入：nums = [1, 4, 4], m = 3
        输出：4

     方法二：二分查找 + 贪心
     思路及算法
         「使……最大值尽可能小」是二分搜索题目常见的问法。
         本题中，我们注意到：当我们选定一个值 x，我们可
         以线性地验证是否存在一种分割方案，满足其最大分割
         子数组和不超过 x。
         策略如下：
         贪心地模拟分割的过程，从前到后遍历数组，用 sum 表示
         当前分割子数组的和，cnt 表示已经分割出的子数组的数量
         （包括当前子数组），那么每当 sum 加上当前值超过了 x，
         我们就把当前取的值作为新的一段分割子数组的开头，并将
         cnt 加 1。遍历结束后验证是否 cnt 不超过 m。
         这样我们可以用二分查找来解决。二分的上界为数组 nums
         中所有元素的和，下界为数组 nums 中所
         有元素的最大值。通过二分查找，我们可以得到最小的最大分
         割子数组和，这样就可以得到最终的答案了。
*/
public class NO410_H_SplitArrayLargestSum_x2 {

    @Test
    public void test() {
        assertEquals(18, splitArray(new int[]{7, 2, 5, 10, 8}, 2));// 18
        assertEquals(9, splitArray(range(1, 6).toArray(),2));// 9
    }

    public int splitArray(int[] nums, int m) {
        int left = 0;
        return left;
    }

}








/**
// 方法1：动态规划
public int splitArray(int[] nums, int m) {
    int n = nums.length;
    int[][] f = new int[n + 1][m + 1];
    for (int i = 0; i <= n; i++) {
        Arrays.fill(f[i], Integer.MAX_VALUE);
    }
    int[] sub = new int[n + 1];
    for (int i = 0; i < n; i++) {
        sub[i + 1] = sub[i] + nums[i];
    }
    f[0][0] = 0;
    for (int i = 1; i <= n; i++) {
        for (int j = 1; j <= Math.min(i, m); j++) {
            for (int k = 0; k < i; k++) {
                f[i][j] = Math.min(f[i][j], Math.max(f[k][j - 1], sub[i] - sub[k]));
            }
        }
    }
    return f[n][m];
}




// 方法2：二分查找
public int splitArray(int[] nums, int m) {
    int left = 0, right = 0;
    for (int num : nums) {
        right += num;
        if (left < num)
            left = num;
    }
    // 最大值 == left, 数组和 == right
    while (left < right) {
        // 选中一个值 mid
        int mid = (right - left) / 2 + left;
        // 把数组nums 拆分m成份，且最大值 <= mid
        if (check(nums, mid, m))
            right = mid;
        else
            left = mid + 1;
    }
    return left;
}

/ *
 * 能否对 nums 数组进行拆分，只做m次拆分，确保最大值不大于 x。
 * true 能，数值和过大了，有潜力可挖，要继续往左面探索（前提是left < right）
 * false 不能，数值和过小了(例如：在[1, 2, 3]中想找到和为8的
 * 是不可能的)，没有潜力，挖不出来了往右边面找找看。
 * /
public boolean check(int[] nums, int x, int m) {
    // sum 分裂子数组的和
    int sum = 0;
    int cnt = 1;//分几次，从1次开始
    for (int num : nums) {
        // 最大和大于x就继续拆分
        // 直到找到一个不大于x的最大和
        if (sum + num > x) {
            cnt++;
            sum = num;
        } else {
            sum += num;
        }
    }
    return cnt <= m;
}
*/
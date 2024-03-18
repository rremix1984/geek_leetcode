package com.leetcode.normal;

import org.junit.Test;
import java.util.Arrays;
import java.util.List;
import static java.lang.Math.max;

/**
    [ARRAY]
    (中等)
    NO.2826 将三个组排序
    给你一个下标从 0 开始长度为 n 的整数数组 nums 。
    从 0 到 n - 1 的数字被分为编号从 1 到 3 的三个组，数字 i 属于组 nums[i] 。注意，有的组可能是 空的 。
    你可以执行以下操作任意次：
        选择数字 x 并改变它的组。更正式的，你可以将 nums[x] 改为数字 1 到 3 中的任意一个。
    你将按照以下过程构建一个新的数组 res ：
        将每个组中的数字分别排序。
        将组 1 ，2 和 3 中的元素 依次 连接以得到 res 。
    如果得到的 res 是 非递减顺序的，那么我们称数组 nums 是 美丽数组 。
    请你返回将 nums 变为 美丽数组 需要的最少步数。
    示例 1：
        输入：nums = [2,1,3,2,1]
        输出：3
        解释：以下三步操作是最优方案：
            1. 将 nums[0] 变为 1 。
            2. 将 nums[2] 变为 1 。
            3. 将 nums[3] 变为 1 。
        执行以上操作后，将每组中的数字排序，组 1 为 [0,1,2,3,4] ，组 2 和组 3 都为空。所以 res 等于 [0,1,2,3,4] ，它是非递减顺序的。
        三步操作是最少需要的步数。
    示例 2：
        输入：nums = [1,3,2,1,3,3]
        输出：2
        解释：以下两步操作是最优方案：
            1. 将 nums[1] 变为 1 。
            2. 将 nums[2] 变为 1 。
        执行以上操作后，将每组中的数字排序，组 1 为 [0,1,2,3] ，组 2 为空，组 3 为 [4,5] 。所以 res 等于 [0,1,2,3,4,5] ，它是非递减顺序的。
        两步操作是最少需要的步数。
    示例 3：
        输入：nums = [2,2,2,2,3,3]
        输出：0
        解释：不需要执行任何操作。
        组 1 为空，组 2 为 [0,1,2,3] ，组 3 为 [4,5] 。所以 res 等于 [0,1,2,3,4,5] ，它是非递减顺序的。
    提示：
        1 <= nums.length <= 100
        1 <= nums[i] <= 3
    Related Topics:数组,二分查找,动态规划
 思路和算法
 由于 n≥1，任意一个长度为 1 的子序列都是非严格递增子序列，因此数组 nums 中的以任意一个下标结尾的最长非严格递增子序列长度都大于等于 1。

 为了得到最长非严格递增子序列的长度，对于每个 0≤i<n 需要分别计算以下标 i 结尾的最长非严格递增子序列长度，然后在这 n 个最长非严格递增子序列的长度中寻找整个数组的最长非严格递增子序列长度，即可得到答案。

 当 i>0 时，如果存在下标 j 满足 0≤j<i 且 nums[j]≤nums[i]，则可以将 nums[i] 添加到以 nums[j] 结尾的非严格递增子序列的后面，得到新的非严格递增子序列。因此可以使用动态规划计算以每个下标结尾的最长非严格递增子序列长度。

 创建长度为 n 的数组 dp，其中 dp[i] 为以下标 i 结尾的最长非严格递增子序列长度。由于以任意一个下标结尾的最长非严格递增子序列长度都大于等于 1，因此将 dp 中的所有值初始化为 1。

 当 i=0 时，以下标 i 结尾的子序列只有一个，长度为 1，因此动态规划的边界情况是 dp[0]=1。

 当 i>0 时，对于满足 0≤j<i 且 nums[j]≤nums[i] 的任意下标 j，dp[i]≥dp[j]+1，为了使 dp[i] 最大化，应寻找符合要求的最大的 dp[j]，此时 dp[i]=max{dp[j]}+1。因此动态规划的状态转移方程是：对于所有满足 0≤j<i 且 nums[j]≤nums[i] 的下标 j，dp[i]=max{dp[j]}+1。

 由于每一项依赖于之前的项，因此应从小到大遍历每个 i 并计算 dp[i]。计算得到 dp 中的所有状态值之后，其中的最大值即为最长非严格递增子序列的长度。

 用 maxLength 表示数组 nums 的最长非严格递增子序列的长度，则最少操作数是 n−maxLength。
*/
public class NO2826_N_MinimumOperations {

    @Test
    public void test() {
        assert 3 == minimumOperations(Arrays.asList(2, 1, 3, 2, 1));
        assert 2 == minimumOperations(Arrays.asList(1, 3, 2, 1, 3, 3));
        assert 0 == minimumOperations(Arrays.asList(2, 2, 2, 2, 3, 3));
    }

    public int minimumOperations(List<Integer> nums) {
        int maxLength = 1;
        int n = nums.size();
        int[] dp = new int[n];
        Arrays.fill(dp, 1);
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++)
                if (nums.get(i) >= nums.get(j))
                    dp[i] = max(dp[i], dp[j] + 1);

            maxLength = max(maxLength, dp[i]);
        }
        return n - maxLength;
    }

}

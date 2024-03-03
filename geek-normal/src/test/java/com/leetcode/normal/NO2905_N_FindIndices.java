package com.leetcode.normal;

import org.junit.Test;

import static com.leetcode.util.MathUtils.getArrays;
import static org.junit.Assert.assertArrayEquals;

/**
    [ARRAY]
    （简单）
    NO.2905 找出满足差值条件的下标 II
    给你一个下标从 0 开始、长度为 n 的整数数组 nums ，以及整数 indexDifference 和整数 valueDifference 。
    你的任务是从范围 [0, n - 1] 内找出 2 个满足下述所有条件的下标 i 和 j ：
    abs(i - j) >= indexDifference 且
    abs(nums[i] - nums[j]) >= valueDifference
    返回整数数组 answer。如果存在满足题目要求的两个下标，则 answer = [i, j] ；否则，answer = [-1, -1] 。如果存在多组可供选择的下标对，只需要返回其中任意一组即可。
    注意：i 和 j 可能 相等 。
    示例 1：
        输入：nums = [5,1,4,1], indexDifference = 2, valueDifference = 4
        输出：[0,3]
        解释：在示例中，可以选择 i = 0 和 j = 3 。
        abs(0 - 3) >= 2 且 abs(nums[0] - nums[3]) >= 4 。
        因此，[0,3] 是一个符合题目要求的答案。
        [3,0] 也是符合题目要求的答案。
    示例 2：
        输入：nums = [2,1], indexDifference = 0, valueDifference = 0
        输出：[0,0]
        解释：
            在示例中，可以选择 i = 0 和 j = 0 。
            abs(0 - 0) >= 0 且 abs(nums[0] - nums[0]) >= 0 。
            因此，[0,0] 是一个符合题目要求的答案。
            [0,1]、[1,0] 和 [1,1] 也是符合题目要求的答案。
    示例 3：
        输入：nums = [1,2,3], indexDifference = 2, valueDifference = 4
        输出：[-1,-1]
        解释：在示例中，可以证明无法找出 2 个满足所有条件的下标。
        因此，返回 [-1,-1] 。
    提示：
        1 <= n == nums.length <= 105
        0 <= nums[i] <= 109
        0 <= indexDifference <= 105
        0 <= valueDifference <= 109
    Related Topics:数组,双指针
*/
public class NO2905_N_FindIndices {

    @Test
    public void test() {
        assertArrayEquals(getArrays(0, 0),
            findIndices(new int[]{2,1}, 0, 0));
        assertArrayEquals(getArrays(-1, -1),
            findIndices(new int[]{1,2,3}, 2, 4));
    }

    public int[] findIndices(int[] nums, int indexDifference, int valueDifference) {
        int maxInd=0,minInd=0,n=nums.length;
        for(int j=indexDifference;j<n;j++){
            int i=j-indexDifference;
            if(nums[i]>nums[maxInd]){
                maxInd=i;
            }
            if(nums[i]<nums[minInd]){
                minInd=i;
            }
            if(nums[maxInd]-nums[j]>=valueDifference){
                return new int[]{maxInd,j};
            }
            if(nums[j]-nums[minInd]>=valueDifference){
                return new int[]{minInd,j};
            }
        }
        return new int[]{-1,-1};
    }

}

















/**
 类似的题是：https://leetcode.cn/problems/best-time-to-buy-and-sell-stock/
 类似 121. 买卖股票的最佳时机，我们可以在枚举 j 的同时，
 维护nums[i] 的最大值 max 和最小值 min。
 计算差值是否符合要求。
 （暴力做法）直接想到的思路：从前往后的二重循环判断下标
 大于等于当前下标加上indexDifference的元素
 看有没有值
 大于等于当前元素值加上valueDifference的元素
 =>显然，你只要用 两个变量 一个max 一个min
 【反正就一点：用一个变量记录可能符合要求的元素】
 就能优化上面的暴力做法，去掉一重循环。
 但是比赛时即使我知道这一点，
 我也死活没想出来怎么用变量记录可能符合要求的元素
 其实就是在遍历比较可选区间中的最值，
 可以使这个可选区间从长度为1开始对比，
 这时另一个数（在一重循环中正在遍历的数）在距离可选区间
 indexDifference的下标位置，这样可选区间从最小的长度开始，
 避免了算漏可能符合要求的元素

 如果跟我当时比赛时的想法一样：第一个数从0开始，
 那么可选区间将从最大的长度开始，这样的话，
 由于用于记录的变量只对比了可选区间的第一个数，这样的话
 找出来的值必然会出现错漏。
 所以，一定要从最小的可选区间开始记录（记录需要的值的话）
 还有一点记录差值：你要觉得不好记录，就用两个变量啊：
 一个最大值，一个最小值。这样无论怎么样都能算出差值是否满足要求。
 */

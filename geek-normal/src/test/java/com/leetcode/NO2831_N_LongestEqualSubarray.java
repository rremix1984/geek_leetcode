/**
 * @copyright wangxiaozhe
 */
package com.leetcode;

import org.junit.Test;
import java.util.*;
import static com.leetcode.util.SystemUtil.displayMap;
import static java.lang.Math.max;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

/**
    [ARRAY]
    NO.2831 找出最长等值子数组
        给你一个下标从 0 开始的整数数组 nums 和一个整数 k 。
        如果子数组中所有元素都相等，则认为子数组是一个 等值子数组 。注意，空数组是 等值子数组 。
        从 nums 中删除最多 k 个元素后，返回可能的【最长】等值子数组的长度。
        子数组 是数组中一个连续且可能为空的元素序列。
    示例 1：
        输入：nums = [1, 3, 2, 3, 1, 3], k = 3
        输出：3
        解释：最优的方案是删除下标 2 和下标 4 的元素。
        删除后，nums 等于 [1, 3, 3, 3] 。
        最长等值子数组从 i = 1 开始到 j = 3 结束，长度等于 3 。
        可以证明无法创建更长的等值子数组。
    示例 2：
        输入：nums = [1, 1, 2, 2, 1, 1], k = 2
        输出：4
        解释：最优的方案是删除下标 2 和下标 3 的元素。
        删除后，nums 等于 [1, 1, 1, 1] 。
        数组自身就是等值子数组，长度等于 4 。
        可以证明无法创建更长的等值子数组。
    提示：
        1 <= nums.length <= 105
        1 <= nums[i] <= nums.length
        0 <= k <= nums.length
    Related Topics:数组,哈希表,二分查找,滑动窗口
*/
public class NO2831_N_LongestEqualSubarray {

    @Test
    public void test() {
        assertEquals(3,
                longestEqualSubarray(asList(1, 2, 3, 3, 1, 3), 3));
        assertEquals(4,
                longestEqualSubarray(asList(1, 1, 2, 2, 1, 1), 2));
    }

    public int longestEqualSubarray(List<Integer> nums, int delCnt) {
        // TODO 2024/5/23 TMD这题真难理解 ToT
        Map<Integer, List<Integer>> map = new HashMap<>();

        // 将每个元素及其索引存入哈希表
        for (int i = 0; i < nums.size(); i++)
            map.computeIfAbsent(
                nums.get(i), key -> new ArrayList<>()
            ).add(i);

        // low,high 代表子数组的可能长度，
        // 这个长度从 1 到 nums.size() 都有可能，所以二分查找
        int low = 1;
        int high = nums.size();
        while (low < high) {
            int subLen = low + (high - low + 1) / 2;
            if (hasSubarray(subLen, delCnt, map))
                low = subLen;
            else
                high = subLen - 1;
        }
        return low;
    }

    private boolean hasSubarray(int subLen, int delCnt,
                                Map<Integer, List<Integer>> map) {
        // 遍历 numIndices 中的每个值（即每个整数的索引列表）
        for (List<Integer> indices : map.values())

            // 遍历索引列表中的每个可能的子数组
            for (int i = subLen - 1; i < indices.size(); i++)
                // 计算当前子数组的实际长度
                // 想象一下，你有一个排列好的数字序列，比如 [0, 2, 4, 6, 8]，
                // 它们代表某个数字在数组中的位置。现在你要找到一个长度为 subLength 的子数组，
                // 并且你可以删除最多 k 个数字，使得剩下的子数组中的数字全都相等。
                //
                // 例如；假设有一个列表 indices = [0, 2, 4, 6, 8]，
                // subLength = 3，k = 2。你需要找到一个长度为 3 的子数组，允许最多删除 2 个元素。
                //
                // 获取子数组的实际长度：
                //
                // indices.get(i)：这是子数组的右端点（索引）。
                // indices.get(i - subLength + 1)：这是子数组的左端点（索引）。
                // indices.get(i) - indices.get(i - subLength + 1) + 1：计算子数组的实际长度。这包括子数组中所有的元素（不包括删除的）。
                //
                if (indices.get(i) - indices.get(i - subLen + 1) + 1 <= subLen + delCnt)
                    return true; // 找到一个满足条件的子数组，返回 true

        return false; // 没有找到满足条件的子数组，返回 false
    }

}
















/*
// 方法1：
//   为方便处理，首先遍历数组 nums 并使用哈希表记录每个元素出现的下标列表，然后计算最长
// 等值子数组的长度。用 maxSubLength 表示从数组 nums 中删除最多 k 个元素之后的最长
// 等值子数组的长度，则对于子数组的长度 subLength，当 subLength≤maxSubLength 时
// 一定可以通过从数组 nums 中删除最多 k 个元素得到长度为 subLength 的等值子数组，当
// subLength>maxSubLength 时一定不能通过从数组 nums 中删除最多 k 个元素得到长度为
// subLength 的等值子数组。因此，这道题是二分查找判定问题，需要找到从数组 nums 中删除
// 最多 k 个元素之后的最长等值子数组的长度。
//    用 low 和 high 分别表示二分查找的下界和上界。由于长度为 1 的子数组是等值子数组，
// 因此 low 的初始值等于 1；由于子数组的长度不可能超过数组 nums 的长度，因此 high 的
// 初始值等于数组 nums 的长度。判断是否可以通过从数组 nums 中删除最多 k 个元素得到长度
// 为 subLength 的等值子数组时，需要考虑数组 nums 中的每个元素的下标列表。对于特定元素
// 的下标列表，考虑其中的连续 subLength 个下标对应的子数组，下标范围中的最大下标与最小下
// 标之差加 1 等于子数组长度，如果子数组长度小于等于 subLength+k，则可以从该子数组中删除
// 最多 k 个元素得到长度为 subLength 的等值子数组。
//    每次查找时，取 mid 为 low 和 high 的平均数向上取整，将 mid 作为子数组的长度，判断
// 是否可以通过从数组 nums 中删除最多 k 个元素得到长度为 mid 的等值子数组，执行如下操作。
//   1）如果可以通过从数组 nums 中删除最多 k 个元素得到长度为 mid 的等值子数组，则最长等值
//      子数组的长度大于等于 mid，因此在 [mid,high] 中继续查找。
//   2）如果不能通过从数组 nums 中删除最多 k 个元素得到长度为 mid 的等值子数组，则最长等值
//      子数组的长度小于 mid，因此在 [low,mid−1] 中继续查找。
//   当 low=high 时，查找结束，此时 low 即为从数组 nums 中删除最多 k 个元素之后的最长等
// 值子数组的长度。
public int longestEqualSubarray(List<Integer> nums, int k) {
    Map<Integer, List<Integer>> numIndices
            = new HashMap<>();
    int size = nums.size();
    for (int i = 0; i < size; i++) {
        int num = nums.get(i);
        numIndices.putIfAbsent(num, new ArrayList<>());
        numIndices.get(num).add(i);
    }
    int low = 1, high = size;
    while (low < high) {
        int mid = low + (high - low + 1) / 2;
        if (hasEqualSubarray(mid, k, numIndices)) {
            low = mid;
        } else {
            high = mid - 1;
        }
    }
    return low;
}

public boolean hasEqualSubarray(int subLength, int k, Map<Integer, List<Integer>> numIndices) {
    Set<Map.Entry<Integer, List<Integer>>> entries = numIndices.entrySet();
    for (Map.Entry<Integer, List<Integer>> entry : entries) {
        List<Integer> indices = entry.getValue();
        int count = indices.size();
        for (int i = subLength - 1; i < count; i++)
            if (indices.get(i) - indices.get(i - subLength + 1) + 1 <= subLength + k)
                return true;
    }
    return false;
}

// 方法2：哈希表 + 双指针
// 我们可以用一个哈希表 g 维护每个元素的下标列表。
// 接下来，我们枚举每个元素作为等值元素，我们从哈希表 g 中取出这个元素的下标列表 ids，
// 然后我们定义两个指针 l 和 r，用于维护一个窗口，使得窗口内的元素个数减去等值元素的个数，
// 结果不超过 k。那么我们只需要求出最大的满足条件的窗口即可。
public int longestEqualSubarray(List<Integer> nums, int k) {
    int n = nums.size();
    List<Integer>[] g = new List[n + 1];
    Arrays.setAll(g, i -> new ArrayList<>());
    for (int i = 0; i < n; ++i) {
        g[nums.get(i)].add(i);
    }
    int ans = 0;
    for (List<Integer> ids : g) {
        int l = 0;
        for (int r = 0; r < ids.size(); ++r) {
            while (ids.get(r) - ids.get(l) - (r - l) > k) {
                ++l;
            }
            ans = Math.max(ans, r - l + 1);
        }
    }
    return ans;
}

// 方法3：简化过后的方法1（推荐）
public int longestEqualSubarray(List<Integer> nums, int delCnt) {
    Map<Integer, List<Integer>> map = new HashMap<>();

    // 将每个元素及其索引存入哈希表
    for (int i = 0; i < nums.size(); i++)
        map.computeIfAbsent(
                nums.get(i), key -> new ArrayList<>()
        ).add(i);

    displayMap(map);

    int low = 1;
    int high = nums.size();
    while (low < high) {
        int subLen = low + (high - low + 1) / 2;
        if (hasSubarray(subLen, delCnt, map))
            low = subLen;
        else
            high = subLen - 1;
    }
    return low;
}

private boolean hasSubarray(int subLen, int delCnt,
                                Map<Integer, List<Integer>> map) {
    // 遍历 numIndices 中的每个值（即每个整数的索引列表）
    for (List<Integer> indices : map.values())
        // 遍历索引列表中的每个可能的子数组
        for (int i = subLen - 1; i < indices.size(); i++)
            // 计算当前子数组的实际长度
            // 感性解释
            // 想象一下，你有一个排列好的数字序列，比如 [0, 2, 4, 6, 8]，
            // 它们代表某个数字在数组中的位置。现在你要找到一个长度为 subLength 的子数组，
            // 并且你可以删除最多 k 个数字，使得剩下的子数组中的数字全都相等。
            //
            // 例如；假设有一个列表 indices = [0, 2, 4, 6, 8]，
            // subLength = 3，k = 2。你需要找到一个长度为 3 的子数组，允许最多删除 2 个元素。
            //
            // 获取子数组的实际长度：
            //
            // indices.get(i)：这是子数组的右端点（索引）。
            // indices.get(i - subLength + 1)：这是子数组的左端点（索引）。
            // indices.get(i) - indices.get(i - subLength + 1) + 1：计算子数组的实际长度。这包括子数组中所有的元素（不包括删除的）。
            //
            if (indices.get(i) - indices.get(i - subLen + 1) + 1 <= subLen + delCnt)
                return true; // 找到一个满足条件的子数组，返回 true

    return false; // 没有找到满足条件的子数组，返回 false
}
*/
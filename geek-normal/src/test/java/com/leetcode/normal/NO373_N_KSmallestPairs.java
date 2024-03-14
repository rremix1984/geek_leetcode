package com.leetcode.normal;

import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import static com.leetcode.util.MathUtils.getArray;
import static com.leetcode.util.MathUtils.getArrays;
import static com.leetcode.util.SystemUtil.arrayAllMatch;
import static java.lang.Math.min;
import static org.junit.Assert.assertArrayEquals;

/**
    [ARRAY]
    (中等)
    NO.373 查找和最小的 K 对数字
    给定两个以 非递减顺序排列 的整数数组 nums1 和 nums2 , 以及一个整数 k 。
    定义一对值 (u,v)，其中第一个元素来自 nums1，第二个元素来自 nums2 。
    请找到和最小的 k 个数对 (u1,v1),  (u2,v2) ... (uk,vk) 。
    示例 1:
        输入: nums1 = [1,7,11], nums2 = [2,4,6], k = 3
        输出: [1,2],[1,4],[1,6]
        解释: 返回序列中的前 3 对数：
        [1,2],[1,4],[1,6],[7,2],[7,4],[11,2],[7,6],[11,4],[11,6]
    示例 2:
        输入: nums1 = [1,1,2], nums2 = [1,2,3], k = 2
        输出: [1,1],[1,1]
        解释: 返回序列中的前 2 对数：
        [1,1],[1,1],[1,2],[2,1],[1,2],[2,2],[1,3],[1,3],[2,3]
    提示:
        1 <= nums1.length, nums2.length <= 105
        -109 <= nums1[i], nums2[i] <= 109
        nums1 和 nums2 均为 升序排列
        1 <= k <= 104
        k <= nums1.length * nums2.length
    Related Topics:数组,堆（优先队列）
*/
public class NO373_N_KSmallestPairs {

    @Test
    public void test() {
        arrayAllMatch(
            getArray(new int[][]{{1,2},{1,4},{1,6}}),
            kSmallestPairs(new int[]{1, 7, 11}, new int[]{2, 4, 6},3));
        arrayAllMatch(
                getArray(new int[][]{{1, 1},{1, 1}}),
                kSmallestPairs(new int[]{1, 1, 2}, new int[]{1, 2, 3},2));
    }

    public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        // 2024/3/13 NO.1
        return null;
    }

}















/*
// 方法1：
private boolean flag = true;
public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
    int n = nums1.length;
    int m = nums2.length;
    // 判断是否需要交换顺序
    if (n <= m || (flag = false)) {// 注意：队列中存储的只是下标
        // 按照「两数和」递增排列
        Queue<int[]> q = new PriorityQueue<>(
                (a, b) ->
                        nums1[a[0]] + nums2[a[1]] - nums1[b[0]] - nums2[b[1]]);
        // 加入头节点
        // 这里有一个技巧：如果 k < n，那么一开始只需要往队列中添加前 k 个元素即可
        // 后面的 n - k 个元素肯定比前面 k 个元素大，所以加入没有意义
        for (int i = 0; i < min(n, k); i++)
            q.offer(new int[]{i, 0});

        List<List<Integer>> ans = new ArrayList<>();
        while (ans.size() < k && !q.isEmpty()) {
            // 弹出队顶元素，即最小元素
            int[] cur = q.poll();
            int a = cur[0], b = cur[1];
            ans.add(new ArrayList<Integer>() {{
                add(flag ? nums1[a] : nums2[b]);
                add(flag ? nums2[b] : nums1[a]);
            }});
            // 如果 b + 1 < m 表示该条链条后面还有元素，可以继续加入队列中
            if (b + 1 < m)
                q.offer(new int[]{a, b + 1});
        }
        return ans;
    } else {
        return kSmallestPairs(nums2, nums1, k);
    }
}
*/
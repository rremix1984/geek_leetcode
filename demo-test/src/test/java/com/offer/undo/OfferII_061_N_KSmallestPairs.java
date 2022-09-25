/**
 * copyright 2022/1/19
 */
package com.offer.undo;

import org.junit.Test;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import static com.leetcode.util.MathUtils.getArray;

/**
    (中等)
    剑指 Offer II 061. 和最小的 k 个数对
        给定两个以升序排列的整数数组 nums1 和 nums2 , 以及一个整数 k 。
        定义一对值 (u,v)，其中第一个元素来自 nums1，第二个元素来自 nums2 。
        请找到和最小的 k 个数对 (u1,v1),  (u2,v2)  ...  (uk,vk) 。
    示例 1:
        输入: nums1 = {1, 7, 11},  nums2 = {2, 4, 6},  k = 3
        输出: {{1, 2}, {1, 4}, {1, 6}}
        解释: 返回序列中的前 3 对数：
            {1, 2}, {1, 4}, {1, 6}, {7, 2}, {7, 4}, {11, 2}, {7, 6}, {11, 4}, {11, 6}
    示例 2:
        输入: nums1 = {1, 1, 2},  nums2 = {1, 2, 3},  k = 2
        输出: {1, 1}, {1, 1}
        解释: 返回序列中的前 2 对数：
            {1, 1}, {1, 1}, {1, 2}, {2, 1}, {1, 2}, {2, 2}, {1, 3}, {1, 3}, {2, 3}
    示例 3:
        输入: nums1 = {1, 2},  nums2 = {3},  k = 3
        输出: {1, 3}, {2, 3}
        解释: 也可能序列中所有的数对都被返回:{1, 3}, {2, 3}
*/
public class OfferII_061_N_KSmallestPairs {

    @Test
    public void test() {
        ArrayList<ArrayList<Integer>> arr1 = getArray(new int[][]{{1, 2}, {1, 4}, {1, 6}});
        List<List<Integer>> temp1 = kSmallestPairs(new int[]{1, 7, 11}, new int[]{2, 4, 6}, 3);
        assert arr1.containsAll(temp1) && temp1.containsAll(arr1);
        ArrayList<ArrayList<Integer>> arr2 = getArray(new int[][]{{1, 3}, {2, 3}});
        List<List<Integer>> temp2 = kSmallestPairs(new int[]{1, 2}, new int[]{3}, 3);
        assert arr2.containsAll(temp2) && temp2.containsAll(arr2);
        ArrayList<ArrayList<Integer>> arr3 = getArray(new int[][]{{1, 1}, {1, 1}});
        List<List<Integer>> temp3 = kSmallestPairs(new int[]{1, 1, 2}, new int[]{1, 2, 3}, 2);
        assert temp3.containsAll(arr3) && arr3.containsAll(temp3);
    }

    public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        ArrayList<List<Integer>> ans = new ArrayList<>();
        return ans;
    }

}


















/**
// 方法1：
public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
    PriorityQueue<int[]> pq = new PriorityQueue<>(
            k, (o1, o2) ->
            nums1[o1[0]] + nums2[o1[1]] - nums1[o2[0]] - nums2[o2[1]]);
    List<List<Integer>> ans = new ArrayList<>();
    int m = nums1.length;
    int n = nums2.length;
    for (int i = 0; i < Math.min(m, k); i++)
        pq.offer(new int[]{i,0});

    while (k-- > 0 && !pq.isEmpty()) {
        int[] idxPair = pq.poll();
        List<Integer> list = new ArrayList<>();
        list.add(nums1[idxPair[0]]);
        list.add(nums2[idxPair[1]]);
        ans.add(list);
        if (idxPair[1] + 1 < n)
            pq.offer(new int[]{idxPair[0], idxPair[1] + 1});
    }
    return ans;
}

// 方法2：
public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
    // 创建一个小根堆，小根堆中存放 <nums1对应元素的索引，nums2对应元素的索引>
    ArrayList<List<Integer>> ans = new ArrayList<>();
    PriorityQueue<int[]> heap = new PriorityQueue<>(
            (pair1, pair2) -> nums1[pair1[0]] + nums2[pair1[1]] - nums1[pair2[0]] - nums2[pair2[1]]
    );
    // 对小根堆进行初始化，将 <0,0>, ... , <k-1,0> 插入栈中（如果长度 n 小于 k 则取 n 个）。
    for(int i = 0; i < Math.min(k, nums1.length); i++){
        heap.add(new int[]{i,0});
    }

    for ( ; k > 0 && !heap.isEmpty(); k--){
        // 选出和最小的数对【i,j】(堆顶)，将堆顶弹出，把 `{nums1[i], nums2[j]}` 保存到列表中。
        int[] pair = heap.poll();
        ans.add(Arrays.asList(nums1[pair[0]],nums2[pair[1]]));
        // 当 `j + 1 < nums2.length` 时， 才将【i,j+1】插入堆中
        if(pair[1] < nums2.length - 1){
            heap.add(new int[]{pair[0], pair[1] + 1});
        }

    }
    // 返回结果
    return ans;
}
*/
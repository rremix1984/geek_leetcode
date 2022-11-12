/**
 * copyright 2022/1/19
 */
package com.leetcode.normal;

import org.junit.Test;
import static com.leetcode.util.SwapUtil.swap;

/**
    (中等)
    215. 数组中的第K个最大元素
        给定整数数组 nums 和整数 k，请返回数组中第 k 个最大的元素。
        请注意，你需要找的是数组排序后的第 k 个最大的元素，而不是第 k 个不同的元素。
        你必须设计并实现时间复杂度为 O(n) 的算法解决此问题。
    示例 1:
        输入: [3, 2, 1, 5, 6, 4], k = 2
        输出: 5
    示例 2:
        输入: [3, 2, 3, 1, 2, 4, 5, 5, 6], k = 4
        输出: 4
    提示：
        1 <= k <= nums.length <= 105
        -104 <= nums[i] <= 104
*/
public class NO215_N_FindKthLargest_x2 {

    @Test
    public void test() {
        assert 5 == findKthLargest(
                new int[]{3, 2, 1, 5, 6, 4}, 2);
        assert 4 == findKthLargest(
                new int[]{3, 2, 3, 1, 2, 4, 5, 5, 6}, 4);
    }

    public int findKthLargest(int[] nums, int k) {
        int idx = nums.length - k;
        int low = 0;
        int high = nums.length - 1;
        while (true) {
            int i = partition(nums, low, high);
            if (i == idx) {
                return nums[i];
            } else if (i < idx) {
                low = i + 1;
            } else {
                high = i - 1;
            }
        }
    }

    /**
     * 分区函数，将 arr[high] 作为 pivot 分区点
     * i、j 两个指针，i 作为标记“已处理区间”和“未处理区间”的分界点，也即 i 左边的（low~i-1）都是“已处理区”。
     * j 指针遍历数组，当 arr[j] 小于 pivot 时，就把 arr[j] 放到“已处理区间”的尾部，也即是 arr[i] 所在位置
     * 因此 swap(arr, i, j) 然后 i 指针后移，i++
     * 直到 j 遍历到数组末尾 arr[high]，将 arr[i] 和 arr[high]（pivot点） 进行交换，返回下标 i，就是分区点的下标。
     */
    private int partition(int[] arr, int low, int high) {
        int i = low;
        int pivot = arr[high];
        for (int j = low; j < high; j++)
            if (arr[j] < pivot) {
                swap(arr, i, j);
                i++;
            }

        swap(arr, i, high);
        return i;
    }

}













/**
// 方法1：
public int findKthLargest(int[] nums, int k) {
    int heapSize = nums.length;
    buildMaxHeap(nums, heapSize);
    for (int i = nums.length - 1; i >= nums.length - k + 1; i--) {
        swap(nums, 0, i);
        heapSize--;
        maxHeapify(nums, 0, heapSize);
    }
    return nums[0];
}

public void buildMaxHeap(int[] a, int heapSize) {
    for (int i = heapSize / 2; i >= 0; i--)
        maxHeapify(a, i, heapSize);
}

public void maxHeapify(int[] a, int i, int heapSize) {
    int l = i * 2 + 1;
    int r = i * 2 + 2;
    int largest = i;
    if (l < heapSize && a[l] > a[largest])
        largest = l;

    if (r < heapSize && a[r] > a[largest])
        largest = r;

    if (largest != i) {
        swap(a, i, largest);
        maxHeapify(a, largest, heapSize);
    }
}

// 方法2：堆排序（大顶堆）
public int findKthLargest(int[] nums, int k) {
    PriorityQueue<Integer> queue = new PriorityQueue<>((o1, o2) -> o1 - o2);
    for (int num : nums) {
        queue.add(num);
        if (queue.size() > k)
            return queue.poll();
    }
    return queue.poll();
}
*/
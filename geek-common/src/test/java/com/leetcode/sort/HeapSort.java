/**
 * copyright 2022/1/19
 */
package com.leetcode.sort;

import org.junit.Test;

import java.util.Arrays;

import static com.leetcode.BaseTest.generateRandomArray;
import static com.leetcode.util.SwapUtil.swap;
import static org.junit.Assert.assertArrayEquals;

/**
    7. 堆排序

    创建一个堆 H[0……n-1]；
    把堆首（最大值）和堆尾互换；
    把堆的尺寸缩小 1，并调用 shift_down(0)，目的是把新的数组顶端数据调整到相应位置；
    重复步骤 2，直到堆的尺寸为 1。
 */
public class HeapSort {

    @Test
    public void test() {
        int[] origin = generateRandomArray(100, 100);
        assertArrayEquals(Arrays.stream(origin).sorted().toArray(),
                heapSort(origin));
    }

    public static int[] heapSort(int[] arr) {
        int len = arr.length;
        int high = len / 2;

        // 初始化构建堆
        for (int i = high; i >= 0; i--)
            heapify(arr, i, len);

        for (int i = len - 1; i > 0; i--) {
            swap(arr, 0, i);
            len--;
            heapify(arr, 0, len);
        }
        return arr;
    }

    private static void heapify(int[] arr, int max, int len) {
        int left = 2 * max + 1;
        int right = 2 * max + 2;
        int largest = max;

        if (left < len && arr[left] > arr[largest])
            largest = left;

        if (right < len && arr[right] > arr[largest])
            largest = right;

        if (largest != max) {
            swap(arr, max, largest);
            heapify(arr, largest, len);
        }
    }

}


/* 方法2：
public int[] sortArray(int[] nums) {
    heapSort(nums);
    return nums;
}

public void heapSort(int[] nums) {
    int len = nums.length - 1;
    buildMaxHeap(nums, len);
    for (int i = len; i >= 1; --i) {
        swap(nums, i, 0);
        len--;
        maxHeapify(nums, 0, len);
    }
}

public void buildMaxHeap(int[] nums, int len) {
    for (int i = len / 2; i >= 0; --i) {
        maxHeapify(nums, i, len);
    }
}

public void maxHeapify(int[] nums, int i, int len) {
    for (; (i << 1) + 1 <= len;) {
        int lson = (i << 1) + 1;
        int rson = (i << 1) + 2;
        int large;
        if (lson <= len && nums[lson] > nums[i]) {
            large = lson;
        } else {
            large = i;
        }
        if (rson <= len && nums[rson] > nums[large]) {
            large = rson;
        }
        if (large != i) {
            swap(nums, i, large);
            i = large;
        } else {
            break;
        }
    }
}


// 方法1：
public static int[] heapSort(int[] arr) {
    int len = arr.length;
    int high = (int) floor(len / 2);

    // 初始化构建堆
    for (int i = high; i >= 0; i--)
        heapify(arr, i, len);

    for (int i = len - 1; i > 0; i--) {
        swap(arr, 0, i);
        len--;
        heapify(arr, 0, len);
    }
    return arr;
}

private static void heapify(int[] arr, int max, int len) {
    int left = 2 * max + 1;
    int right = 2 * max + 2;
    int largest = max;

    if (left < len && arr[left] > arr[largest])
        largest = left;

    if (right < len && arr[right] > arr[largest])
        largest = right;

    if (largest != max) {
        swap(arr, max, largest);
        heapify(arr, largest, len);
    }
}
*/
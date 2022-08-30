/**
 * copyright 2022/1/19
 */
package com.leetcode.sort;

import org.junit.Test;
import static com.leetcode.util.LogUtil.info;
import static com.leetcode.util.SwapUtil.swap;
import static java.lang.Math.floor;

/**
 * 7. 堆排序
 *
 * 创建一个堆 H[0……n-1]；
 * 把堆首（最大值）和堆尾互换；
 * 把堆的尺寸缩小 1，并调用 shift_down(0)，目的是把新的数组顶端数据调整到相应位置；
 * 重复步骤 2，直到堆的尺寸为 1。
 */
public class HeapSort {

    @Test
    public void test() {
        info(heapSort(new int[]{2, 1, 3, 5, 4, 6, 9, 8, 7, 10}));
    }

    public int[] heapSort(int[] arr) {
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

    private void heapify(int[] arr, int max, int len) {
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

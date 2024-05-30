/**
 * copyright 2022/1/19
 */
package com.leetcode.sort;

/**
    快速排序（Quick Sort）
        快速排序的基本思想：通过一趟排序将待排记录分隔成独立的两部分，其中一部分记录的关键字均比另一部分的关键字小，则可分别对这两部分记录继续进行排序，以达到整个序列有序。
    6.1 算法描述
        快速排序使用分治法来把一个串（list）分为两个子串（sub-lists）。具体算法描述如下：
        从数列中挑出一个元素，称为 “基准”（pivot）；
        重新排序数列，所有元素比基准值小的摆放在基准前面，所有元素比基准值大的摆在基准的后面（相同的数可以到任一边）。在这个分区退出之后，该基准就处于数列的中间位置。这个称为分区（partition）操作；
        递归地（recursive）把小于基准值元素的子数列和大于基准值元素的子数列排序。
*/
public class QuickSort {

    public static int[] quickSort(int[] arr, int left, int right) {
        return arr;
    }

}





















/*
// 方法1：
public static void quickSort(int[] arr, int left, int right) {
    if (left > right)
        return;

    int i = left;
    int j = right;
    int base = arr[left];

    // 当 i 和 j 不相遇的时候，在循环中进行检索
    while (i != j) {
        // 先由 j 从右往左检索比基准数小的就停下
        // 如果 >= 基准数，就继续走
        while (arr[j] >= base && i < j)
            j--;

        // 然后由 i 从左向右检索比基准数大的就停下
        // 如果 <= 基准数，就继续走
        while (arr[i] <= base && i < j)
            i++;

        //i, j指针都停下了，交换i, j元素
        swap(arr, i, j);
    }

    // 如果while循环条件不成立，跳出循环往下执行
    // 条件不成立说明 arr[i], arr[j] 相遇了
    // 就交换基准数 left 和 相遇位置的元素
    arr[left] = arr[i];
    arr[i] = base;

    // 此时左边的都比base小,右边的都比base大
    // 因为i已经是基准数了，不能动了
    quickSort(arr, left,i - 1);
    quickSort(arr,i + 1, right);
}

// 方法2：
public static void quickSort(int[] array, int begin, int end) {
    if (end <= begin)
        return;

    int pivot = partition(array, begin, end);
    quickSort(array, begin, pivot - 1);
    quickSort(array, pivot + 1, end);
}

static int partition(int[] a, int begin, int end) {
    // pivot 标杆位置
    int pivot = end;
    int counter = begin;
    for (int i = begin; i < end; i++) {
        if (a[i] < a[pivot]) {
            swap(a, counter, i);
            counter++;
        }
    }
    swap(a, counter, pivot);
    return counter;
}
*/
/**
 * copyright 2022/1/19
 */
package com.leetcode.hard;

import org.junit.Test;

import static com.leetcode.util.LogUtil.info;

/**
    （困难）
    493. 翻转对
        给定一个数组 nums ，如果 i < j 且 nums[i] > 2 * nums[j] 我们就将 (i, j) 称作一个重要翻转对。
        你需要返回给定数组中的重要翻转对的数量。
    示例 1:
        输入: [1, 3, 2, 3, 1]
        输出: 2
        解释：[3, 1] [3, 1]
    示例 2:
        输入: [2, 4, 3, 5, 1]
        输出: 3
        解释：[4, 1], [3, 1], [5, 1]
*/
public class NO493_H_ReversePairs_x2 {

    @Test
    public void test() {
        info(reversePairs(new int[]{1, 3, 2, 3, 1}));// 2
        info(reversePairs(new int[]{2, 4, 3, 5, 1}));// 3
    }

    public int reversePairs(int[] nums) {
        return -1;
    }

}

















/**
private int count;
public int reversePairs(int[] nums) {
    if (nums == null || nums.length < 2)
        return 0;

    count = 0;
    mergeSort(nums, 0, nums.length - 1);
    return count;
}

private void mergeSort(int[] nums, int start, int end) {
    if (start == end)
        return;

    int mid = start + (end - start) / 2;
    mergeSort(nums, start, mid);
    mergeSort(nums, mid + 1, end);
    int i = start;
    int j = mid + 1;
    while (i <= mid && j <= end) {
        if ((long) nums[i] > 2 * (long) nums[j]) {
            count += mid - i + 1;
            j++;
        } else {
            i++;
        }
    }
    // 统计完之后合并
    int[] tempArr = new int[end - start + 1];
    i = start;
    j = mid + 1;
    int idx = 0;
    while (i <= mid && j <= end) {
        tempArr[idx++] = nums[i] < nums[j] ? nums[i++] : nums[j++];
    }
    while (i <= mid) {
        tempArr[idx++] = nums[i++];
    }
    while (j <= end) {
        tempArr[idx++] = nums[j++];
    }
    for (i = 0, j = start; j <= end; i++, j++) {
        nums[j] = tempArr[i];
    }
}


// 方法2：归并排序
public int reversePairs(int[] nums) {
    return mergeSort(nums, 0, nums.length - 1);
}

public static int mergeSort(int[] arr, int left, int right) {
    if (left >= right)
        return 0;

    int mid = left + (right - left) / 2;

    int cnt = mergeSort(arr, left, mid) + mergeSort(arr, mid + 1, right);

    int i = left;
    int j = mid + 1;
    for (i <= mid) {
        while (j <=  right && arr[i]/2.0 > arr[j])
            j++;
        cnt += j - (mid + 1);
        i++;
    }

    Arrays.sort(arr, left, right + 1);
    return cnt;
}

// 方法3：
public int ret;
public int reversePairs(int[] nums) {
    ret = 0;
    mergeSort(nums, 0, nums.length - 1);
    return ret;
}

private void mergeSort(int[] nums, int left, int right) {
    if (right <= left)
        return;
    int mid = left + (right - left) / 2;
    mergeSort(nums, left, mid);
    mergeSort(nums, mid + 1, right);

    // count elements
    int count = 0;
    for (int i = left, j = mid + 1; i <= mid; ) {
        if (j > right || (long)nums[i] <= 2 * (long)nums[j]) {
            i++;
            ret += count;
        } else {
            j++;
            count++;
        }
    }
    Arrays.sort(nums, left, right + 1);
}
*/
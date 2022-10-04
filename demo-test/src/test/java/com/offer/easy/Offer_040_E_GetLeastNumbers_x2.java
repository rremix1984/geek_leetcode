/**
 * copyright 2022/1/19
 */
package com.offer.easy;

import org.junit.Test;
import java.util.Arrays;
import static org.junit.Assert.assertArrayEquals;

/**
    (简单)
    剑指 Offer 40. 最小的k个数
        输入整数数组 arr ，找出其中最小的 k 个数。例如，
        输入4、5、1、6、2、7、3、8这8个数字，则最小的4个数字是1、2、3、4。
    示例 1：
        输入：arr = {3, 2, 1}, k = 2
        输出：[1, 2] 或者 [2, 1]
    示例 2：
        输入：arr = {0, 1, 2, 1}, k = 1
        输出：[0]
*/
public class Offer_040_E_GetLeastNumbers_x2 {

    @Test
    public void test() {
        assertArrayEquals(new int[]{1, 2}, getLeastNumbers(new int[]{3, 2, 1}, 2));
        assertArrayEquals(new int[]{0}, getLeastNumbers(new int[]{0, 1, 2, 1}, 1));
    }

    public int[] getLeastNumbers(int[] arr, int k) {
        int[] t = new int[k];
        Arrays.sort(arr);
        for (int i = 0; i < k; i++)
            t[i] = arr[i];

        return t;
    }

}

















/**
// 方法1：暴力法
public int[] getLeastNumbers(int[] arr, int k) {
    int[] t = new int[k];
    Arrays.sort(arr);
    for (int i = 0; i < k; i++)
        t[i] = arr[i];

    return t;
}


// 方法2：堆排序
public int[] getLeastNumbers(int[] arr, int k) {
    int[] vec = new int[k];
    if (k == 0) // 排除 0 的情况
        return vec;

    PriorityQueue<Integer> queue = new PriorityQueue<>((num1, num2) -> num2 - num1);
    for (int i = 0; i < k; ++i)
        queue.offer(arr[i]);

    for (int i = k; i < arr.length; i++)
        if (queue.peek() > arr[i]) {
            queue.poll();
            queue.offer(arr[i]);
        }

    for (int i = 0; i < k; ++i)
        vec[i] = queue.poll();

    return vec;
}
*/
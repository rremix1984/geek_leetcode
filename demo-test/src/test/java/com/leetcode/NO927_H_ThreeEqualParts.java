/**
 * copyright 2022/1/19
 */
package com.leetcode;

import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;

/**
    (困难)
    927. 三等分
        给定一个由 0 和 1 组成的数组 arr ，将数组分成  3 个非空的部分 ，使得所有这些部分表示相同的二进制值。
        如果可以做到，请返回任何 [i, j]，其中 i+1 < j，这样一来：
            arr[0], arr[1], ..., arr[i] 为第一部分；
            arr[i + 1], arr[i + 2], ..., arr[j - 1] 为第二部分；
            arr[j], arr[j + 1], ..., arr[arr.length - 1] 为第三部分。
        这三个部分所表示的二进制值相等。
        如果无法做到，就返回 [-1, -1]。
        注意，在考虑每个部分所表示的二进制时，应当将其看作一个整体。例如，[1,1,0] 表示十进制中的 6，而不会是 3。此外，前导零也是被允许的，所以 [0,1,1] 和 [1,1] 表示相同的值。
    示例 1：
        输入：arr = {1, 0, 1, 0, 1}
        输出：{0, 3}
    示例 2：
        输入：arr = {1, 1, 0, 1, 1}
        输出：{-1, -1}
    示例 3:
        输入：arr = {1, 1, 0, 0, 1}
        输出：{0, 2}
*/
public class NO927_H_ThreeEqualParts {

    @Test
    public void test() {
        assertArrayEquals(threeEqualParts(new int[]{1, 0, 1, 0, 1}), new int[]{0, 3});
        assertArrayEquals(threeEqualParts(new int[]{1, 1, 0, 1, 1}), new int[]{-1, -1});
        assertArrayEquals(threeEqualParts(new int[]{1, 1, 0, 0, 1}), new int[]{0, 2});
    }

    public int[] threeEqualParts(int[] arr) {
        return new int[]{-1, -1};
    }

}















/**
// 方法1：
public int[] threeEqualParts(int[] arr) {
    int cnt = 0;
    for (int v : arr)
        cnt += v;

    if (cnt % 3 != 0)
        return new int[]{-1, -1};

    if (cnt == 0)
        return new int[]{0, arr.length - 1};

    cnt /= 3;

    int i = find(arr,1);
    int j = find(arr,cnt + 1);
    int k = find(arr,cnt * 2 + 1);

    while (k < arr.length && arr[i] == arr[j] && arr[j] == arr[k]) {
        i++;
        j++;
        k++;
    }

    if (k == arr.length)
        return new int[]{i - 1, j};

    return new int[]{-1, -1};
}

private int find(int[] arr, int x) {
    int s = 0;
    for (int i = 0; i < arr.length; i++) {
        s += arr[i];
        if (s == x)
            return i;
    }
    return 0;
}
*/
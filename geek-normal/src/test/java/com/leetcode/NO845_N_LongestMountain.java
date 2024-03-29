/**
 * copyright@2019/12/12
 */
package com.leetcode;

import org.junit.Test;
import static java.lang.Math.max;

/**
    [ARRAY]
    (中等)
    NO.845 最长山脉子数组
    把符合下列属性的数组 arr 称为 山脉数组 ：
      arr.length >= 3
      存在下标 i（0 < i < arr.length - 1），满足
      1）arr[0] < arr[1] < ... < arr[i - 1] < arr[i]
      2）arr[i] > arr[i + 1] > ... > arr[arr.length - 1]
    给出一个整数数组arr，返回最长山脉子数组的长度。如果不存在山脉子数组，返回0。
    示例 1：
        输入：arr = [2, 1, 4, 7, 3, 2, 5]
        输出：5
        解释：最长的山脉子数组是 [1, 4, 7, 3, 2]，长度为 5。
    示例 2：
        输入：arr = [2, 2, 2]
        输出：0
        解释：不存在山脉子数组。
    提示：
        1 <= arr.length <= 104
        0 <= arr[i] <= 104
    进阶：
        你可以仅用一趟扫描解决此问题吗？
        你可以用 O(1) 空间解决此问题吗？
    Related Topics:数组,双指针,动态规划,枚举
*/
public class NO845_N_LongestMountain {

    @Test
    public void test() {
        assert 5 == longestMountain(new int[]{2, 1, 4, 7, 3, 2, 5});
        assert 0 == longestMountain(new int[]{2, 2, 2});
    }

    public int longestMountain(int[] arr) {
        // 2024/3/16 NO.1
        //长度不够的，直接排除
        if (arr.length < 3)
            return 0;

        int max = 0;

        //依次从下标1，到下标len-2，当做中心点左右扩散，求出宽度。
        for (int i = 1; i < arr.length - 1; i++) {
            int left = i - 1;
            int right = i + 1;
            //如果不是左右的最高点，直接结束，进入下一个循环
            if (arr[left] >= arr[i] || arr[right] >= arr[i])
                continue;

            //山脉的最左侧下标(最终的left要比山脉的左侧下标小1)
            while (left >= 0 && arr[left] < arr[left + 1])
                left--;

            //山脉的最右侧下标(最终的right要比山脉的右侧下标大1)
            while (right < arr.length && arr[right] < arr[right - 1])
                right++;

            //比较，保存最大山脉长度
            max = max(max, right - left - 1);
        }
        return max;
    }

}
















/*
// 方法1：
public int longestMountain(int[] arr) {
    //长度不够的，直接排除
    if (arr.length < 3)
        return 0;

    int max = 0;

    //依次从下标1，到下标len-2，当做中心点左右扩散，求出宽度。
    for (int i = 1; i < arr.length - 1; i++) {
        int left = i - 1;
        int right = i + 1;

        //如果不是左右的最高点，直接结束，进入下一个循环
        if (arr[left] >= arr[i] || arr[right] >= arr[i])
            continue;

        //山脉的最左侧下标(最终的left要比山脉的左侧下标小1)
        while (left >= 0 && arr[left] < arr[left + 1])
            left--;

        //山脉的最右侧下标(最终的right要比山脉的右侧下标大1)
        while (right < arr.length && arr[right] < arr[right - 1])
            right++;

        //比较，保存最大山脉长度
        max = max(max, right - left - 1);
    }
    return max;
}
*/
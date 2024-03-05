/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import java.util.Arrays;

/**
    [ARRAY] |
    (简单)
    1013. 将数组分成和相等的三个部分
        给你一个整数数组arr，只有可以将其划分为三个和相等的非空部分时才返回true，否则返回false。
        形式上，如果可以找出索引 i + 1 < j 且满足
        ( arr[0]+arr[1]+...+arr[i]
        ==arr[i+1]+arr[i+2]+...+arr[j-1]
        ==arr[j]+arr[j+1]+...+arr[arr.length-1])
        就可以将数组三等分。
    示例 1：
        输入：arr = {0, 2, 1, -6, 6, -7, 9, 1, 2, 0, 1}
        输出：true
        解释：0 + 2 + 1 = -6 + 6 - 7 + 9 + 1 = 2 + 0 + 1
    示例 2：
        输入：arr = {0, 2, 1, -6, 6, 7, 9, -1, 2, 0, 1}
        输出：false
    示例 3：
        输入：arr = {3, 3, 6, 5, -2, 2, 5, 1, -9, 4}
        输出：true
        解释：3 + 3 = 6 = 5 - 2 + 2 + 5 + 1 - 9 + 4
*/
public class NO1003_E_CanThreePartsEqualSum {

    @Test
    public void test() {
        assert canThreePartsEqualSum(
            new int[]{0, 2, 1, -6, 6, -7, 9, 1, 2, 0, 1});
        assert !canThreePartsEqualSum(
            new int[]{0, 2, 1, -6, 6, 7, 9, -1, 2, 0, 1});
        assert canThreePartsEqualSum(
            new int[]{3, 3, 6, 5, -2, 2, 5, 1, -9, 4});
        assert !canThreePartsEqualSum(
            new int[]{1, 1, 1, 1});
    }

    public boolean canThreePartsEqualSum(int[] A) {
        // 2024/3/4 NO.1 关键还是思路，并不是怎么写出来
        return false;
    }

}























/*
// 方法1：双指针法
public boolean canThreePartsEqualSum(int[] A) {
    int sum = 0;
    // 1. 先求和
    for (int i : A)
        sum += i;

    // 2. 判断是否3的倍数
    if (sum % 3 != 0)
        // 总和不是3的倍数，直接返回false
        return false;

    // 3. 使用双指针,从数组两头开始一起找，节约时间
    // 左指针
    int left = 0;

    // 左侧求和
    int leftSum = A[left];

    // 右指针
    int right = A.length - 1;

    // 右侧求和
    int rightSum = A[right];

    // 使用left + 1 < right 的原因，防止只能将数组分成两个部分
    // 例如：[1,-1,1,-1]，使用left < right作为判断条件就会出错
    while (left + 1 < right) {
        if (leftSum == sum / 3 && rightSum == sum / 3)
            // 左右两边都等于 sum/3 ，中间也一定等于
            return true;

        if (leftSum != sum / 3)
            // left = 0赋予了初值，应该先left++，在leftSum += A[left];
            leftSum += A[++left];

        if (rightSum != sum / 3)
            // right = A.length - 1 赋予了初值，应该先right--，在rightSum += A[right];
            rightSum += A[--right];
    }
    return false;
}
*/
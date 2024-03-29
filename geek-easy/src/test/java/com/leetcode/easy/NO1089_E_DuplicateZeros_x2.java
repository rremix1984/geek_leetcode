/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;

/**
    (简单)
    1089. 复写零
        给你一个长度固定的整数数组 arr ，请你将该数组中出现的每个零都复写一遍，并将其余的元素向右平移。
        注意：请不要在超过该数组长度的位置写入元素。请对输入的数组 就地 进行上述修改，不要从函数返回任何东西。
    示例 1：
        输入：arr = {1, 0, 2, 3, 0, 4, 5, 0}
        输出：{1, 0, 0, 2, 3, 0, 0, 4}
        解释：调用函数后，输入的数组将被修改为：{1, 0, 0, 2, 3, 0, 0, 4}
    示例 2：
        输入：arr = {1, 2, 3}
        输出：{1, 2, 3}
        解释：调用函数后，输入的数组将被修改为：{1, 2, 3}
*/
public class NO1089_E_DuplicateZeros_x2 {

    @Test
    public void test() {
        int[] target = {1, 0, 2, 3, 0, 4, 5, 0};
        duplicateZeros(target);
        assertArrayEquals(new int[]{1, 0, 0, 2, 3, 0, 0, 4}, target);

        int[] target2 = {1, 2, 3};
        duplicateZeros(target2);
        assertArrayEquals(new int[]{1, 2, 3}, target2);

        int[] target3 = {0, 0, 0, 0, 0, 0, 0};
        duplicateZeros(target3);
        assertArrayEquals(new int[]{0, 0, 0, 0, 0, 0, 0}, target3);
    }

    public void duplicateZeros(int[] arr) {

    }
    
}















/**
public void duplicateZeros(int[] arr) {
    for (int i = 0; i < arr.length; i++) {
        if (arr[i] != 0)
            continue;

        //此时手动把后边的所有元素都后移一位：
        for (int j = arr.length - 1; j > i; j--)
            arr[j] = arr[j - 1];

        if (i < arr.length - 1)
            arr[i + 1] = 0;

        i++;
    }
}
*/
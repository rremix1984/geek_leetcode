/**
 * copyright 2022/1/19
 */
package com.leetcode;

import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;

/**
    (简单)
    1317. 将整数转换为两个无零整数的和
        「无零整数」是十进制表示中 不含任何 0 的正整数。
        给你一个整数 n，请你返回一个 由两个整数组成的列表 [A, B]，满足：
        A 和 B 都是无零整数
        A + B = n
        题目数据保证至少有一个有效的解决方案。
        如果存在多个有效解决方案，你可以返回其中任意一个。
    示例 1：
        输入：n = 2
        输出：{1, 1}
        解释：A = 1,  B = 1. A + B = n 并且 A 和 B 的十进制表示形式都不包含任何 0 。
    示例 2：
        输入：n = 11
        输出：{2, 9}
    示例 3：
        输入：n = 10000
        输出：{1, 9999}
    示例 4：
        输入：n = 69
        输出：{1, 68}
    示例 5：
        输入：n = 1010
        输出：{11, 999}
*/
public class NO1317_E_GetNoZeroIntegers {

    @Test
    public void test() {
        assertArrayEquals(new int[]{1, 1}, getNoZeroIntegers(2));
        assertArrayEquals(new int[]{2, 9}, getNoZeroIntegers(11));
        assertArrayEquals(new int[]{1, 9999}, getNoZeroIntegers(10000));
        assertArrayEquals(new int[]{1, 68}, getNoZeroIntegers(69));
        assertArrayEquals(new int[]{11, 999}, getNoZeroIntegers(1010));
    }

    public int[] getNoZeroIntegers(int n) {
        int [] res = new int[2];
        //n <= 10 时单独讨论一下
        if(n <= 10) {
            res[0] = 1;
            res[1] = n - 1;
            return res;
        }

        //求数字n的十进制长度
        int length = (int)Math.log10(n);

        //数字res[0]中每一位都是9,res[1]是与res[0]互补的数
        res[0] = (int)Math.pow(10, length) - 1;
        res[1] = n - res[0];

        //判断res[1]中十进制某一位是否为0
        int temp = res[1];
        int index = 1;

        while(temp > 0)
        {
            //如果res[1]某一位为0，则res[1]该位加上1，res[0]该位减去1
            if(temp % 10 == 0)
            {
                res[0] -= index;
                res[1] += index;
            }

            index *= 10;
            temp = temp / 10;
        }

        return res;
    }

}
















/**
// 方法1：
public int[] getNoZeroIntegers(int n) {
    int num1 = 0;
    int num2 = 0;
    int unit = 1;

    while (n > 0) {
        int sum = n % 10;
        if (sum == 0 || sum == 1 && n > 10)
            sum += 10;

        int digit1 = sum / 2;
        int digit2 = sum - digit1;
        num1 += digit1 * unit;
        num2 += digit2 * unit;
        n = (n - sum) / 10;
        unit *= 10;
    }

    return new int[]{num1, num2};
}
*/
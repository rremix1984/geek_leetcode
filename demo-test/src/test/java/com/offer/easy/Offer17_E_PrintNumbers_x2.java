/**
 * copyright 2022/1/19
 */
package com.offer.easy;

import org.junit.Test;
import java.util.stream.IntStream;

import static java.util.stream.IntStream.range;
import static org.junit.Assert.assertArrayEquals;

/**
    (简单)
    剑指 Offer 17. 打印从1到最大的n位数
        输入数字 n，按顺序打印出从 1 到最大的 n 位十进制数。比如输入 3，则打印出 1、2、3 一直到最大的 3 位数 999。
    示例 1:
        输入: n = 1
        输出: [1,2,3,4,5,6,7,8,9]
*/
public class Offer17_E_PrintNumbers_x2 {

    @Test
    public void test() {
        assertArrayEquals(range(1,10).toArray(), printNumbers(1));
        assertArrayEquals(range(1,100).toArray(), printNumbers(2));
        assertArrayEquals(range(1,1000).toArray(), printNumbers(3));
        assertArrayEquals(range(1,10000).toArray(), printNumbers(4));
        assertArrayEquals(range(1,100000).toArray(), printNumbers(5));
    }

    public int[] printNumbers(int n) {
        return null;
    }

}















/**
// 方法1：
public int[] printNumbers(int n) {
    // 9, 99, 999, 9999 ...
    int end = (int) pow(10, n) - 1;

    int[] res = new int[end];
    for(int i = 0; i < end; i++)
        res[i] = i + 1;

    return res;
}
*/
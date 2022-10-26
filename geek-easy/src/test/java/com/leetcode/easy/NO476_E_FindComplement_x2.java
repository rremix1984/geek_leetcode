/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

/**
    (简单)
    476. 数字的补数
        对整数的二进制表示取反（0变1 ，1变0）后，再转换为十进制表示，
        可以得到这个整数的补数。例如，整数5的二进制表示是"101"，
        取反后得到"010"，再转回十进制表示得到补数2。
        给你一个整数num，输出它的补数。
    示例 1：
        输入：num = 5
        输出：2
        解释：5 的二进制表示为 101（没有前导零位），其补数为 010。所以你需要输出 2 。
    示例 2：
        输入：num = 1
        输出：0
        解释：1 的二进制表示为 1（没有前导零位），其补数为 0。所以你需要输出 0 。
*/
public class NO476_E_FindComplement_x2 {

    @Test
    public void test() {
        assert 2 == findComplement(5);
        assert 0 == findComplement(1);
    }

    public int findComplement(int num) {
        int tmp = num, c = 1;
        while (tmp > 0) {
            c <<= 1;
            tmp >>= 1;
        }
        return num ^ c - 1;
    }

}

















/**
public int findComplement(int num) {
    int tmp = num, c = 1;
    while (tmp > 0) {
        c <<= 1;
        tmp >>= 1;
    }
    // 5 ^ (8-1) == 5 ^ 7
    // == 101 ^ 111 == 10
    // 10 == 2(10进制)
    return num ^ c - 1;
}


// 方法2：
public int findComplement(int num) {
    //Integer.highestOneBit(num) 得到的是 num 对应的二进制最高位为 1 ，其余位都为 0 时的数值
    //或者说是小于 num 的最大的 2 的幂次方，例如 5 -> 4，25 -> 16，45 -> 32
    //往左移 1 位再减 1 得到的就是两个数的异或结果
    //把上一步得到的数值跟 nu, 异或就得到答案了
    return (((Integer.highestOneBit(num) << 1)) - 1) ^ num;
}
*/
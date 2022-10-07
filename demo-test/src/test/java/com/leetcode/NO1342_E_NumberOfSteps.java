/**
 * copyright 2022/1/19
 */
package com.leetcode;

import org.junit.Test;

/**
    (简单)
    1342. 将数字变成 0 的操作次数
        给你一个非负整数 num ，请你返回将它变成 0 所需要的步数。
        如果当前数字是偶数，你需要把它除以 2 ；否则，减去 1 。
    示例 1：
        输入：num = 14
        输出：6
        解释：步骤 1) 14 是偶数，除以 2 得到 7 。
             步骤 2） 7 是奇数，减 1 得到 6 。
             步骤 3） 6 是偶数，除以 2 得到 3 。
             步骤 4） 3 是奇数，减 1 得到 2 。
             步骤 5） 2 是偶数，除以 2 得到 1 。
             步骤 6） 1 是奇数，减 1 得到 0 。
    示例 2：
        输入：num = 8
        输出：4
        解释：步骤 1） 8 是偶数，除以 2 得到 4 。
             步骤 2） 4 是偶数，除以 2 得到 2 。
             步骤 3） 2 是偶数，除以 2 得到 1 。
             步骤 4） 1 是奇数，减 1 得到 0 。
    示例 3：
        输入：num = 123
        输出：12
 思路与算法
    将 num 与 1 进行位运算来判断 num 的奇偶性。
 记录操作次数时：
     如果 num 是奇数，我们需要加上一次减 1 的操作。
     如果 num > 1，我们需要加上一次除以 22 的操作。
     然后使 num 的值变成 num / 2。重复以上操作直到 num=0 时结束操作。

*/
public class NO1342_E_NumberOfSteps {

    @Test
    public void test() {
        assert 6 == numberOfSteps(14);
        assert 4 == numberOfSteps(8);
        assert 12 == numberOfSteps(123);
    }

    public int numberOfSteps(int num) {
        int res = 0;
        return res;
    }

}
















/**
public int numberOfSteps(int num) {
    int res = 0;
    while (num > 0) {
        if (num > 1)
            res += 1 + (num & 1);
        else
            res += num & 1;
        num >>= 1;
    }
    return res;
}
*/
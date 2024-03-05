/**
 * copyright 2022/1/19
 */
package com.lcp;

import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;

/**
    [ARRAY]
    (简单)
    [数学]
    LCP 02. 分式化简
        有一个同学在学习分式。他需要将一个连分数化成最简分数，你能帮助他吗？
        连分数是形如上图的分式。在本题中，所有系数都是大于等于0的整数。
        输入的cont代表连分数的系数（cont[0]代表上图的a0，以此类推）。
        返回一个长度为2的数组[n, m]，使得连分数的值等于n / m，且n, m最大公约数为1。
    示例 1：
        输入：cont = {3, 2, 0, 2}
        输出：{13, 4}
        解释：原连分数等价于 【3】 + (1 / (【2】 + (1 / (【0】 + 1 / 【2】))))。
             注意 {26, 8}, {-13, -4} 都不是正确答案。
                                 1
                      3 +  -----------------
                             2   +    1                 13
                                 -----------    ===>   ----
                                   0  +  1              4
                                       -----
                                         2
    示例 2：
        输入：cont = {0, 0, 3}
        输出：{3, 1}
        解释：如果答案是整数，令分母为1即可。
*/
public class LCP_02_E_Fraction {

    @Test
    public void test() {
        assertArrayEquals(new int[]{13, 4},
            fraction(new int[]{3, 2, 0, 2}));
        assertArrayEquals(new int[]{3, 1},
            fraction(new int[]{0, 0, 3}));
    }

    public int[] fraction(int[] cont) {
        int n = cont.length;
        int fenzi = cont[n - 1];
        int fenmu = 1;
        for (int i = n - 2; i >= 0; i--) {
            int temp = fenzi;
            fenzi = fenmu + cont[i] * fenzi;
            fenmu = temp;
            if (fenzi % fenmu == 0) {
                fenzi = fenzi / fenmu;
                fenmu = 1;
            }
        }
        return new int[]{fenzi, fenmu};
    }

}














/**
public int[] fraction(int[] cont) {
    int n = cont.length;
    int fenzi = cont[n - 1];
    int fenmu = 1;
    for (int i = n - 2; i >= 0; i--) {
        int temp = fenzi;
        fenzi = fenmu + cont[i] * fenzi;
        fenmu = temp;
        if (fenzi % fenmu == 0) {
            fenzi = fenzi / fenmu;
            fenmu = 1;
        }
    }
    return new int[]{fenzi, fenmu};
}
*/
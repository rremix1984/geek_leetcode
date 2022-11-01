/**
 * copyright 2022/1/19
 */
package com.leetcode;

import org.junit.Test;

import static java.lang.Math.pow;

/**
    (困难)
    233. 数字 1 的个数
        给定一个整数 n，计算所有小于等于 n 的非负整数中数字 1 出现的个数。
    示例 1：
        输入：n = 13
        输出：6
    示例 2：
        输入：n = 0
        输出：0
*/
public class NO233_H_CountDigitOne {

    @Test
    public void test() {
        assert 6 == countDigitOne(13);
        assert 0 == countDigitOne(0);
    }

    public int countDigitOne(int n){
        int conut = 0;//保存1的个数
        int rem = 0;//保存尾数，方便统计对应位上存在有多少个1

        for (int i = 0; n != 0; i++) {
            int tem = n/10;//当前位前面的数大小
            if (tem * 10 + 1 < n)
                tem++;//3421的情况
            else if (tem * 10 + 1 == n)
                conut += rem + 1;//3420的情况

            conut += (tem * pow(10, i));//对应位的 1 前面的个数

            rem += (n % 10 * pow(10, i));//保存尾数

            n /= 10;
        }
        return conut;
    }

}



















/**
// 方法1：
public int countDigitOne(int n){
    int conut = 0;//保存1的个数
    int rem = 0;//保存尾数，方便统计对应位上存在有多少个1

    for (int i = 0; n != 0; i++) {
        //当前位前面的数大小
        int tem = n/10;

        //3421的情况
        if (tem * 10 + 1 < n) {
            tem++;
        //3420的情况
        } else if (tem * 10 + 1 == n) {
            conut += rem + 1;
        }

         //对应位的 1 前面的个数
        conut += (tem * pow(10, i));

        //保存尾数
        rem += (n % 10 * pow(10, i));

        n /= 10;
    }
    return conut;
}
*/
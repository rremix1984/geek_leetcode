/**
 * copyright (c) 2024 by 501735698@qq.com
 */
package com.atguigu;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
    [NUMBER] |||||||
    (简单）汉诺塔
    分治法
 */
public class Hanoitower {

    @Test
    public void test() {
        assertEquals(
        "第1个盘子，从A移动到C\n" +
                "第2个盘子，从A移动到B\n" +
                "第1个盘子，从C移动到B\n" +
                "第3个盘子，从A移动到C\n" +
                "第1个盘子，从B移动到A\n" +
                "第2个盘子，从B移动到C\n" +
                "第1个盘子，从A移动到C\n",
            hanoi(3, 'A', 'B', 'C'));
    }

    StringBuilder sb = new StringBuilder();

    //汉诺塔的移动的方法
    //使用分治算法
    public String hanoi(int num, char A, char B, char C) {
        // sb.append("第" + 1 + "个盘子，从" + A + "移动到" + C + "\n");
        // 2024/2/27 NO.3 分治法
        // 2024/3/21 NO.4 忘记了，没做出来
        // 2024/3/24 NO.5
        // 2024/3/25 NO.6 思路都对，就是做错了
        // 2024/3/30 NO.7
        return sb.toString();
    }

}

























/*
方法1：
public static void hanoiTower(int num, char a, char b, char c) {
    // 如果只有一个 元素
    if (num == 1) {
        out.println("第[1]个盘子，从" + a + "移动到" + c);
    } else {
        hanoiTower(num - 1, a, c, b);
        out.println("第" + num + "个盘子，从" + a + "移动到" + c);
        hanoiTower(num - 1, b, a, c);
    }
}

public static void main(String[] args) {
    hanoiTower(3, 'A', 'B', 'C');
}
*/
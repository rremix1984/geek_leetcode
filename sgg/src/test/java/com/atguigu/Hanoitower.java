package com.atguigu;

import org.junit.Test;

/**
    [NUMBER] |
    (简单）汉诺塔
    分治法
 */
public class Hanoitower {

    @Test
    public void test() {
        hanoi(3, 'A', 'B', 'C');
    }

    //汉诺塔的移动的方法
    //使用分治算法
    public static void hanoi(int num, char A, char B, char C) {
        // 2024/2/27 NO.3 分治法
        // 2024/3/21 NO.3 忘记了，没做出来

    }

}

























/*
方法1：
public static void hanoiTower(int num, char a, char b, char c) {
    // 如果只有一个 元素
    if (num == 1) {
        System.out.println("第[1]个盘子，从" + a + "移动到" + c);
    } else {
        hanoiTower(num - 1, a, c, b);
        System.out.println("第" + num + "个盘子，从" + a + "移动到" + c);
        hanoiTower(num - 1, b, a, c);
    }
}

public static void main(String[] args) {
    hanoiTower(3, 'A', 'B', 'C');
}
*/
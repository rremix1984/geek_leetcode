package com.sgg;

/**
 * (简单）汉诺塔
 */
public class Hanoitower {

    public static void main(String[] args) {
        hanoi(3, 'A', 'B', 'C');
    }

    //汉诺塔的移动的方法
    //使用分治算法
    public static void hanoi(int num, char a, char b, char c) {
        if (num == 1) {
            System.out.println("第1层塔，从" + a + "到" + c);
        } else {
            hanoi(num -1, a, c, b);
            System.out.println("第" + num + "层塔，从" + a + "到" + c);
            hanoi(num -1, b, a, c);
        }
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
package com.sgg;

/**
   (简单）汉诺塔
 */
public class Hanoitower {

    public static void main(String[] args) {
        hanoi(10, 'A', 'B', 'C');
    }

    //汉诺塔的移动的方法
    //使用分治算法
    public static void hanoi(int num, char a, char b, char c) {
        // 到最后一个盘子直接从 a 柱，移动到 c 柱
        if (num == 1) {
            System.out.println("第[" + num + "]个元素，从" + a + " 移动到" + c);
        } else {
            // 将 a 柱的 num-1 个盘子借助 c 柱移动到 b 柱
            hanoi(num - 1, a, c, b);

            // 将 a 柱的最大盘子移动到 c
            System.out.print("第[" + num + "]个元素，从" + a + " 移动到" + c + ", ");

            // 将 b 柱的 num - 1 个盘子借助 a 柱移动到 c 柱
            hanoi(num - 1, b, a, c);
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
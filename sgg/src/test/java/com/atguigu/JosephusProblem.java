package com.atguigu;

import java.util.LinkedList;
import java.util.List;

import static java.lang.System.out;

public class JosephusProblem {

    public static void main(String[] args) {
        int n = 10; // 总人数
        int m = 3;  // 报数到m的人被排除
        List<Integer> eliminationOrder = josephus(n, m);
        out.println("被淘汰的人的顺序是：" + eliminationOrder);
    }

    public static List<Integer> josephus(int n, int m) {
        // 初始化一个链表表示圈中的人
        List<Integer> circle = new LinkedList<>();
        for (int i = 1; i <= n; i++)
            circle.add(i);

        // 记录被淘汰人的顺序
        List<Integer> eliminationOrder = new LinkedList<>();

        int index = 0; // 从第一个人开始报数
        while (circle.size() > 0) {
            // 计算下一个被淘汰的人的位置
            index = (index + m - 1) % circle.size();
            // 从圈中移除这个人，并添加到被淘汰人的顺序列表中
            eliminationOrder.add(circle.remove(index));
        }

        return eliminationOrder;
    }
}
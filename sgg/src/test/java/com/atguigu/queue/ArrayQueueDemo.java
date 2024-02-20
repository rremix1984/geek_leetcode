package com.atguigu.queue;

import java.util.Scanner;

/**
 * 用数组来模拟队列
 */
public class ArrayQueueDemo {

//    @Test
//    public void test() throws Exception {
    public static void main(String[] args) {
        // 创建一个队列对象
        ArrayQueue queue = new ArrayQueue(3);
        char key = ' '; // 接收用户输入
        Scanner scanner = new Scanner(System.in);
        boolean loop = true;
        while (loop) {
            System.out.println("s(show): 显示队列");
            System.out.println("e(exit): 退出程序");
            System.out.println("a(add): 添加数据到队列");
            System.out.println("g(get): 从队列取出数据");
            System.out.println("h(head): 查看队列头的数据");

            key = scanner.next().charAt(0);// 接收一个字符
            switch (key) {
                case 's':
                    queue.showQeue();
                    break;
                case 'a':
                    System.out.println("输出一个数");
                    int value = scanner.nextInt();
                    queue.addQueue(value);
                    break;
                case 'g':
                    try {
                        int res = queue.getQueue();
                        System.out.printf("取出的数据是：%d\n", res);
                    } catch(Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'h':
                    try {
                        int res = queue.headQueue();
                        System.out.printf("队列头的数据是%d \n", res);
                    }catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'e':
                    scanner.close();
                    loop = false;
                    break;
                default:
                    break;
            }
        }
        System.out.println("程序退出！");
    }

}

/**
 * 写一个类用数组模拟队列
 */
@SuppressWarnings("all")
class ArrayQueue {

    private int maxSize; // 表示数组最大容量

    private int front; // 队列头（指向队列头的指针）

    private int rear; // 队列尾

    private int[] arr; // 该数组用于存放数据，模拟队列

    // 创建队列的构造器
    public ArrayQueue(int maxSize) {
        this.maxSize = maxSize;
        arr = new int[maxSize];
        front = -1; // 指向队列头部, 分析出front是指向对猎头的前一个位置
        rear = -1; // 指向队列尾部，指向队列尾的数据（即：队列最后一个数据）
    }

    // 判断队列是否满
    public boolean isFull() {
        return rear == maxSize - 1;
    }

    // 判断队列是否为空
    public boolean isEmpty() {
        return rear == front;
    }

    // 添加一个元素
    public void addQueue(int n) {
        // 添加的时候首先判断队列是否满，如果满了就加不进去了
        if (isFull()) {
            System.out.println("队列满，不能加入数据");
            return;
        }
        // 尾指针后移
        rear++;

        // 把数值加进去
        arr[rear] = n;
    }

    // 出队列，获取数据
    public int getQueue() throws Exception{
        if (isEmpty()) {
            throw new RuntimeException("队列空，不能取数据！");
        }

        // 头指针减一
        front++;

        //返回数值
        return arr[front];
    }

    // 显示队列所有元素
    public void showQeue() {
        if (isEmpty()) {
            System.out.println("队列空，没有数据！");
            return;
        }

        for (int i = front + 1; i <= rear; i++) {
            System.out.printf("arr[%d]=%d\n", i, arr[i]);
        }
    }

    // 显示队列头数据，只是显示不是取数据
    public int headQueue() {
        // 判断
        if (isEmpty()) {
            throw new RuntimeException("队列是空的，没有数据");
        }
        return arr[front + 1];
    }

}
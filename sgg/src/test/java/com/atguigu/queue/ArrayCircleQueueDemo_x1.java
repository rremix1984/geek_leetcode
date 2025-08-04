package com.atguigu.queue;

import org.junit.Test;

import static java.lang.System.out;

/**
    [ARRAY] |
    用数组实现队列（需要用环形数组）
 */
@SuppressWarnings("all")
public class ArrayCircleQueueDemo_x1 {

    @Test
    public void test() {
        // 测试模拟环形队列的方法
        ArrayCircleQueue queue = new ArrayCircleQueue(5); // 创建一个容量为5的队列
        // 入队
        queue.enqueue(10);
        queue.enqueue(20);
        queue.enqueue(30);

        // 出队
        out.println("Dequeued element: " + queue.dequeue()); // 应该为10
        out.println("Next element (peek): " + queue.peek()); // 应该为20

        // 继续入队
        queue.enqueue(40);
        queue.enqueue(50);
        queue.enqueue(60); // 这时队列应该已满

        try {
            queue.enqueue(70); // 尝试入队更多元素，应该抛出异常
        } catch (IllegalStateException e) {
            out.println("Caught exception: " + e.getMessage()); // 应该显示队列已满的消息
        }

        // 出队直到队列为空
        while (!queue.isEmpty()) {
            out.println("Dequeued: " + queue.dequeue());
        }

        // 检查队列是否为空
        out.println("Queue empty?" + queue.isEmpty()); // 应该为true

        try {
            queue.dequeue(); // 尝试从空队列中出队，应该抛出异常
        } catch (IllegalStateException e) {
            out.println("queue dequeue " + e.getMessage()); // 应该显示队列为空的消息
        }
    }

}

@SuppressWarnings("all")
class ArrayCircleQueue {
    // 2024/2/20 NO.1
    //

    /**
     * Constructs a new circular queue implemented using an array with the specified capacity.
     * The circular queue allows efficient enqueue and dequeue operations by reusing space in the array
     * when elements are removed from the front.
     *
     * @param capacity the maximum number of elements the queue can hold. Must be a positive integer.
     * @throws IllegalArgumentException if the specified capacity is less than or equal to zero.
     */ // 构造函数，初始化队列
    public ArrayCircleQueue(int capacity) {

    }

    /**
     * Adds the specified element to the end of this circular queue.
     *
     * @param element the element to be added to the queue
     * @throws IllegalStateException if the queue is full and cannot accept new elements
     *
     * The method implements the enqueue operation for a circular array-based queue.
     * When called, it attempts to insert the given element at the rear position
     * of the queue, following the circular queue implementation rules.
     *
     * Before adding the element, the method should check if the queue has available
     * space. If the queue is full, it throws an IllegalStateException to indicate
     * that the element cannot be added.
     */ // 入队
    public void enqueue(int element) throws IllegalStateException {
        return;
    }

    /**
     * Removes and returns the element at the front of this circular queue.
     * This operation reduces the queue size by one.
     *
     * @return the element at the front of this queue
     * @throws IllegalStateException if the queue is empty
     */ // 出队
    public int dequeue() throws IllegalStateException {
        return -1;
    }

    /**
     * Retrieves, but does not remove, the head element of this circular queue.
     * This method throws an exception if the queue is empty.
     *
     * @return the head element of this queue
     * @throws IllegalStateException if this queue is empty
     */ // 查看队列头部元素
    public int peek() throws IllegalStateException {
        return -1;
    }

    /**
     * Displays the current contents of the circular queue.
     *
     * This method prints all elements currently stored in the queue in their logical order,
     * from front to rear. The display includes only the active elements in the queue,
     * excluding any unused array slots. If the queue is empty, no output will be produced.
     *
     * The method is primarily intended for debugging and demonstration purposes to visualize
     * the queue's contents during program execution.
     *
     * @throws IllegalStateException if the queue is empty (implementation dependent)
     */
    public void showQueue() {

    }

    /**
     * Checks whether the circular queue is empty.
     *
     * @return {@code true} if the queue is empty, {@code false} otherwise
     */ // 检查队列是否为空
    public boolean isEmpty() {
        return false;
    }

    /**
     * Checks whether the circular queue is full.
     *
     * @return {@code true} if the queue has reached its maximum capacity,
     *         {@code false} otherwise
     */ // 检查队列是否已满
    public boolean isFull() {
        return false;
    }

}


















/*
class ArrayCircleQueue {
    private int[] queue;
    private int front; // 队列头
    private int rear;  // 队列尾
    private int size;  // 队列当前的大小
    private int capacity; // 队列的容量

    // 构造函数，初始化队列
    public ArrayCircleQueue(int capacity) {
        this.capacity = capacity;
        queue = new int[capacity];
        front = 0;
        rear = 0;
        size = 0;
    }

    // 入队
    public void enqueue(int element) throws IllegalStateException {
        if (size == capacity) {
            throw new IllegalStateException("Queue is full");
        }
        queue[rear] = element;
        rear = (rear + 1) % capacity; // 循环队列
        size++;
    }

    // 出队
    public int dequeue() throws IllegalStateException {
        if (size == 0) {
            throw new IllegalStateException("Queue is empty");
        }
        int element = queue[front];
        front = (front + 1) % capacity; // 循环队列
        size--;
        return element;
    }

    // 查看队列头部元素
    public int peek() throws IllegalStateException {
        if (size == 0) {
            throw new IllegalStateException("Queue is empty");
        }
        return queue[front];
    }

    public void showQueue() {
        if (isEmpty()) {
            out.println("queue is empty");
            return;
        }
        // 从front开始遍历多少个元素
        for (int i = front; i < front + size(); i++) {
            // 因为是环形的，所以要 i % size
            out.printf("arr[%d]=%d\n", i % size, queue[i % size]);
        }
    }

    public int size() {
        return (rear + size - front) % size;
    }

    // 检查队列是否为空
    public boolean isEmpty() {
        return size == 0;
    }

    // 检查队列是否已满
    public boolean isFull() {
        return size == capacity;
    }

}
*/
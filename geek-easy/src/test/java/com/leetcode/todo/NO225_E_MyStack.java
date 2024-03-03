package com.leetcode.todo;

import org.junit.Test;
import java.util.LinkedList;
import java.util.Queue;

/**
    [ARRAY]
    （简单）
    NO.225 用队列实现栈
    请你仅使用两个队列实现一个后入先出（LIFO）的栈，并支持普通栈的全部四种操作（push、top、pop 和 empty）。
    实现 MyStack 类：
      1）void push(int x) 将元素 x 压入栈顶。
      2）int pop() 移除并返回栈顶元素。
      3）int top() 返回栈顶元素。
      4）boolean empty() 如果栈是空的，返回 true ；否则，返回 false 。
    注意：
        你只能使用队列的基本操作 —— 也就是 push to back、peek/pop from front、size 和 is empty 这些操作。
        你所使用的语言也许不支持队列。 你可以使用 list （列表）或者 deque（双端队列）来模拟一个队列 , 只要是标准的队列操作即可。
    示例：
        输入：
            ["MyStack", "push", "push", "top", "pop", "empty"]
            [[], [1], [2], [], [], []]
        输出：
            [null, null, null, 2, 2, false]
    解释：
        MyStack myStack = new MyStack();
        myStack.push(1);
        myStack.push(2);
        myStack.top(); // 返回 2
        myStack.pop(); // 返回 2
        myStack.empty(); // 返回 False
    提示：
        1 <= x <= 9
        最多调用100 次 push、pop、top 和 empty
        每次调用 pop 和 top 都保证栈不为空
        进阶：你能否仅用一个队列来实现栈。
    Related Topics:栈,设计,队列
*/
public class NO225_E_MyStack {

    @Test
    public void test() {
        MyStack stack = new MyStack();
        stack.push(1);
        stack.push(2);
        assert 2 == stack.top(); // 返回 2
        assert 2 ==stack.pop(); // 返回 2
        assert !stack.empty(); // 返回 False
    }

}

class MyStack {
    Queue<Integer> queue1, queue2;

    /** Initialize your data structure here. */
    public MyStack() {
        queue1 = new LinkedList<>();
        queue2 = new LinkedList<>();
    }

    /** Push element x onto stack. */
    public void push(int x) {
        queue2.offer(x);
        while (!queue1.isEmpty())
            queue2.offer(queue1.poll());

        Queue<Integer> temp=queue1;
        queue1=queue2;
        queue2=temp;
    }

    /** Removes the element on top of the stack and returns that element. */
    public int pop() {
        return queue1.poll();
    }

    /** Get the top element. */
    public int top() {
        return queue1.peek();
    }

    /** Returns whether the stack is empty. */
    public boolean empty() {
        return queue1.isEmpty();
    }

}

/**
 * copyright 2022/1/19
 */
package com.leetcode;

import org.junit.Test;

import java.util.Stack;

import static com.leetcode.util.LogUtil.info;

/**
    232. 用栈实现队列
        请你仅使用两个栈实现先入先出队列。队列应当支持一般队列支持的所有操作（push、pop、peek、empty）：
        实现 MyQueue 类：
            void push(int x) 将元素 x 推到队列的末尾
            int pop() 从队列的开头移除并返回元素
            int peek() 返回队列开头的元素
            boolean empty() 如果队列为空，返回 true ；否则，返回 false
        说明：
        你 只能 使用标准的栈操作 —— 也就是只有 push to top, peek/pop from top, size, 和 is empty 操作是合法的。
        你所使用的语言也许不支持栈。你可以使用 list 或者 deque（双端队列）来模拟一个栈，只要是标准的栈操作即可。
    示例 1：
        输入：
            ["MyQueue", "push", "push", "peek", "pop", "empty"]
            [[], [1], [2], [], [], []]
        输出：
            [null, null, null, 1, 1, false]
*/
public class NO232_E_ImplementQueueUsingStacks {

    @Test
    public void test() {
        MyQueue myQueue = new MyQueue();
        myQueue.push(1); // queue is: [1]
        myQueue.push(2); // queue is: [1, 2] (leftmost is front of the queue)
        info(myQueue.peek()); // return 1
        info(myQueue.pop()); // return 1, queue is [2]
        info(myQueue.empty()); // return false
    }
}

// 双Stack法，实现队列
class MyQueue {

    private Stack<Object> stack1;
    private Stack<Object> stack2;

    public MyQueue() {
        stack1 = new Stack<>();
        stack2 = new Stack<>();
    }

    public void push(int x) {
        stack1.push(x);
    }

    public int pop() {
        int value;
        while (!stack1.empty()) {
            stack2.push(stack1.peek());
            stack1.pop();
        }
        value = (int)stack2.pop();
        while (!stack2.empty()) {
            stack1.push(stack2.peek());
            stack2.pop();
        }
        return value;
    }

    public int peek() {
        int value;
        while (!stack1.empty()) {
            stack2.push(stack1.peek());
            stack1.pop();
        }
        value = (int)stack2.peek();
        while (!stack2.empty()) {
            stack1.push(stack2.peek());
            stack2.pop();
        }
        return value;
    }

    public boolean empty() {
        return stack1.empty();
    }
}




















/**
class MyQueue {

    private Stack<Object> stack1;
    private Stack<Object> stack2;

    public MyQueue() {
        stack1 = new Stack<>();
        stack2 = new Stack<>();
    }

    public void push(int x) {
        stack1.push(x);
    }

    public int pop() {
        int value;
        while (!stack1.empty()) {
            stack2.push(stack1.peek());
            stack1.pop();
        }
        value = (int)stack2.pop();
        while (!stack2.empty()) {
            stack1.push(stack2.peek());
            stack2.pop();
        }
        return value;
    }

    public int peek() {
        int value;
        while (!stack1.empty()) {
            stack2.push(stack1.peek());
            stack1.pop();
        }
        value = (int)stack2.peek();
        while (!stack2.empty()) {
            stack1.push(stack2.peek());
            stack2.pop();
        }
        return value;
    }

    public boolean empty() {
        return stack1.empty();
    }
}
*/
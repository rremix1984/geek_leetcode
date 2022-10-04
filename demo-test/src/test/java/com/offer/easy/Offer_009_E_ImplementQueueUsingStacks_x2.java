/**
 * copyright 2022/1/19
 */
package com.offer.easy;

import org.junit.Test;

/**
    (简单)
    剑指 Offer 09. 用两个栈实现队列
        用两个栈实现一个队列。队列的声明如下，请实现它的两个函数 appendTail 和 deleteHead ，分别完成在队列尾部插入整数和在队列头部删除整数的功能。(若队列中没有元素，deleteHead 操作返回 -1 )
    示例 1：
        输入：["CQueue","appendTail","deleteHead","deleteHead"]
            [[],[3],[],[]]
        输出：[null,null,3,-1]
    示例 2：
        输入：["CQueue","deleteHead","appendTail","appendTail","deleteHead","deleteHead"]
            [[],[],[5],[2],[],[]]
        输出：[null,-1,null,null,5,2]
*/
public class Offer_009_E_ImplementQueueUsingStacks_x2 {

    @Test
    public void test() {
        CQueue queue = new CQueue();
        queue.appendTail(3);
        assert  3 == queue.deleteHead();
        assert -1 == queue.deleteHead();

        CQueue queue2 = new CQueue();
        assert -1 == queue2.deleteHead();
        queue2.appendTail(5);
        queue2.appendTail(2);
        assert 5 == queue2.deleteHead();
        assert 2 == queue2.deleteHead();
    }
}

class CQueue {

    public CQueue() {

    }

    public void appendTail(int value) {

    }

    public int deleteHead() {
        return -1;
    }
}





















/**
class CQueue {

    Stack<Integer> inStack;
    Stack<Integer> outStack;

    public CQueue() {
        inStack = new Stack<>();
        outStack = new Stack<>();
    }

    public void appendTail(int value) {
        inStack.push(value);
    }

    public int deleteHead() {
        if (inStack.isEmpty())
            return -1;

        while(!inStack.isEmpty()) {
            outStack.push(inStack.pop());
        }
        int res = outStack.pop();
        while(!outStack.isEmpty()) {
            inStack.push(outStack.pop());
        }
        return res;
    }
}
*/
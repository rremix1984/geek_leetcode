/**
 * copyright @leetcode.cn
 */
package com.leetcode;

import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
    [LISTNODE]
    (困难)
    NO.1206 设计跳表
    不使用任何库函数，设计一个 跳表 。
    跳表 是在 O(log(n)) 时间内完成增加、删除、搜索操作的数据结构。跳表相比于
    树堆与红黑树，其功能与性能相当，并且跳表的代码长度相较下更短，其设计思想与链表相似。
    例如，一个跳表包含 [30, 40, 50, 60, 70, 90] ，然后增加 80、45 到跳表中，
    以下图的方式操作：
    跳表中有很多层，每一层是一个短的链表。在第一层的作用下，增加、删除和搜索操
    作的时间复杂度不超过 O(n)。跳表的每一个操作的平均时间复杂度是 O(log(n))，
    空间复杂度是 O(n)。
    在本题中，你的设计应该要包含这些函数：
    bool search(int target) : 返回target是否存在于跳表中。
    void add(int num): 插入一个元素到跳表。
    bool erase(int num): 在跳表中删除一个值，如果 num 不存在，直接返回false.
    如果存在多个 num ，删除其中任意一个即可。
    注意，跳表中可能存在多个相同的值，你的代码需要处理这种情况。
    示例 1:
        输入
        ["Skiplist", "add", "add", "add", "search", "add", "search", "erase", "erase", "search"]
                [[], [1], [2], [3], [0], [4], [1], [0], [1], [1]]
        输出
        [null, null, null, null, false, null, true, false, true, false]
    提示:
        0 <= num, target <= 2 * 10 ^ 4
        调用search, add, erase操作次数不大于 5 * 10 ^ 4
    Related Topics:设计,链表
*/
public class NO1206_H_Skiplist {

    @Test
    public void test() {
        Skiplist skiplist = new Skiplist();
        skiplist.add(1);
        skiplist.add(2);
        skiplist.add(3);
        assertFalse(skiplist.search(0));   // 返回 false
        skiplist.add(4);
        assertTrue(skiplist.search(1));   // 返回 true
        assertFalse(skiplist.erase(0));    // 返回 false，0 不在跳表中
        assertTrue(skiplist.erase(1));    // 返回 true
        assertFalse(skiplist.search(1));   // 返回 false，1 已被擦除
    }

}

class Skiplist {
    private Node[] forward;
    private int MAX_LEVEL = 16;

    // 初始化操作
    public Skiplist() {
        // 设置头结点 并建立down的关系
        forward = new Node[MAX_LEVEL];
        Node head = new Node(-1);
        for(int i = MAX_LEVEL - 1 ; i >=0; i--){
            Node tmp = new Node(-1);
            // 建立层次down关系的核心代码
            tmp.down = head;
            forward[i] = head;
            head = tmp;
        }
    }

    public boolean search(int target) {
        return false;
    }

    public void add(int num) {

    }

    public boolean erase(int num) {
        return false;
    }

    private int randomLevel() {
        int level = 0;
        return level;
    }

}

class Node{
    int data; // 用于存放数据
    Node next; // 用于指向同一层的下一个Node
    Node down; // 指向相同数据的下一层
    public Node(int data){this.data = data;}
}



















/*
class Skiplist {

    //level层数组
    private Node[] forward;

    // 级别高度
    private int MAX_LEVEL = 16;

    // 初始化操作
    public Skiplist() {
        // 设置头结点 并建立down的关系
        forward = new Node[MAX_LEVEL];
        Node head = new Node(-1);
        for (int i = MAX_LEVEL - 1 ; i >=0; i--) {
            Node tmp = new Node(-1);
            // 建立层次down关系的核心代码
            tmp.down = head;
            forward[i] = head;
            head = tmp;
        }
    }

    // 查询操作
    public boolean search(int target) {
        Node localNode = forward[0];
        // 当前层数
        int i = 0 ;
        while (i < MAX_LEVEL) {
            // 从高层往下找
            if (localNode.data == target) {
                return true;
            } else if (localNode.next != null
                    && localNode.next.data <= target) {
                localNode = localNode.next;
            } else {
                localNode = localNode.down;
                i++;
            }
        }
        return false;
    }

    // 添加操作
    public void add(int num) {
        // 不用判断是否已经添加过了
        Node pre_node [] = new Node[MAX_LEVEL];
        for (int i = MAX_LEVEL-1 ; i >= 0 ; i--) {
            // 查找每一层的前驱节点
            Node per_head = forward[i];
            while (per_head.next != null && per_head.next.data < num) {
                per_head = per_head.next;
            }
            // 记录每一层合适的位置
            pre_node[i] = per_head;
        }
        // 从1开始算
        int levelNum = randomLevel();
        Node target = new Node(num);
        for (int i = 0 ; i < levelNum ; i ++) {
            // 创建上一层的节点
            Node tmp = new Node(num);
            tmp.down = target;
            target.next = pre_node[MAX_LEVEL - 1 - i].next;
            pre_node[MAX_LEVEL - 1 - i].next = target;
            target = tmp;
        }
    }

    // 删除操作
    public boolean erase(int num) {
        if (!search(num))
            return false;

        for (int i = MAX_LEVEL - 1; i >= 0; i--) {
            // 查找每一层的前驱节点 // 默认为头节点
            Node per_head = forward[i];
            while (per_head.next != null) {
                if (per_head.next.data == num) {
                    per_head.next = per_head.next.next;
                    break;
                }
                per_head = per_head.next;
            }
        }
        return true;
    }

    // 看随机数最多能到多少层 每次有50%的概率上升一层
    private int randomLevel() {
        int level = 0;

        // 概率 每个节点对于每层来说每次都有50%的插入该层  插入到队列的概率是100%
        double p = 1d;

        // 当 level < MAX_LEVEL，且随机数小于设定的晋升概率时，level + 1 概率变为原来的一般
        while (random() < p && level < MAX_LEVEL) {
            // 第一个循环肯定进来 变为1
            level++;
            p = p / 2.0;
        }
        return level;
    }

}
*/
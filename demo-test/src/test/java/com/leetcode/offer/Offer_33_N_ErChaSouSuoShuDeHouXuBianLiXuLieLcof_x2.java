/**
 * copyright 2022/1/19
 */
package com.leetcode.offer;

import org.junit.Test;
import java.util.Deque;
import java.util.LinkedList;


/**
    (中等)
    剑指 Offer 33. 二叉搜索树的后序遍历序列
        输入一个整数数组，判断该数组是不是某二叉搜索树的后序遍历结果。如果是则返回 true，否则返回 false。假设输入的数组的任意两个数字都互不相同。
        参考以下这颗二叉搜索树：
                 5
                / \
               2   6
              / \
             1   3
    示例 1：
        输入: [1, 6, 3, 2, 5]
        输出: false
    示例 2：
        输入: [1, 3, 2, 6, 5]
        输出: true
*/
public class Offer_33_N_ErChaSouSuoShuDeHouXuBianLiXuLieLcof_x2 {

    @Test
    public void test() {
        assert !verifyPostorder(new int[]{1, 6, 3, 2, 5});
        assert verifyPostorder(new int[]{1, 3, 2, 6, 5});
    }

    public static boolean verifyPostorder(int[] arr) {
        Deque<Integer> queue = new LinkedList<>();
        int rootVal = Integer.MAX_VALUE;

        for (int i = arr.length - 1; i >= 0; i--) {
            int num = arr[i];
            if (num > rootVal)
                return false;

            while (!queue.isEmpty() && queue.peek() > num)
                rootVal = queue.removeFirst();

            queue.addLast(num);
        }
        return true;
    }

}



















/**
// 方法1：
public boolean verifyPostorder(int[] postorder) {
    return call(postorder, 0, postorder.length - 1);
}

boolean call(int[] postorder, int i, int j) {
    if (i >= j)
        return true;

    int p = i;

    while (postorder[p] < postorder[j])
        p++;

    int m = p;

    while (postorder[p] > postorder[j])
        p++;

    return p == j && call(postorder, i, m - 1)
            && call(postorder, m, j - 1);
}


// 方法2：
public boolean verifyPostorder(int[] postorder) {
    Stack<Integer> stack = new Stack<>();
    int root = Integer.MAX_VALUE;

    for (int i = postorder.length - 1; i >= 0; i--) {
        if (postorder[i] > root)
            return false;

        while (!stack.isEmpty() && stack.peek() > postorder[i])
            root = stack.pop();
        stack.add(postorder[i]);
    }
    return true;
}
*/
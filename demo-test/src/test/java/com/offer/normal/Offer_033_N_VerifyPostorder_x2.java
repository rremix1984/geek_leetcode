/**
 * copyright 2022/1/19
 */
package com.offer.normal;

import org.junit.Test;

import java.util.Stack;

import static java.lang.Integer.MAX_VALUE;

/**
    (中等)
    剑指 Offer 33. 二叉搜索树的后序遍历序列
        输入一个整数数组，判断该数组是不是某二叉搜索树的后序遍历结果。
        如果是则返回 true，否则返回 false。假设输入的数组的任意两个数字都互不相同。
        参考以下这颗二叉搜索树：
               5
              / \
             2   6
            / \
           1   3
    示例 1：
        输入: [1,6,3,2,5]
        输出: false
    示例 2：
        输入: [1,3,2,6,5]
        输出: true
    提示：
        数组长度 <= 1000

    解题思路: 二叉树问题一定可以用递归解决
        不合格的后序遍历一定是在根节点的左侧出现了比根节点大的数，或者在根节点的右侧出现了比根节点小的树
    由于后序遍历的顺序是左右根，根节点位于序列的最后一位，然后根据根节点的值将序列下标（0，n-2）的结点分成左右子树序列
    所以我们采取分治的思想，将一个序列划为左右子树的两个子序列，对每一个子序列进行判断
*/
public class Offer_033_N_VerifyPostorder_x2 {

    @Test
    public void test() {
        assert !verifyPostorder(new int[]{1,6,3,2,5});
        assert verifyPostorder(new int[]{1,3,2,6,5});
    }

    public boolean verifyPostorder(int[] arr) {
        return true;
    }

}

















/**
// 方法1：
public boolean verifyPostorder(int[] postorder) {
    return call(postorder, 0, postorder.length - 1);
}

public boolean call(int[] postorder, int i, int j) {
    if(i >= j)
        return true;

    int p = i;
    while (postorder[p] < postorder[j])
        p++;

    int m = p;
    while(postorder[p] > postorder[j])
        p++;

    return p == j
            && call(postorder, i, m - 1)
            && call(postorder, m, j - 1);
}

// 方法2：
public boolean verifyPostorder(int[] postorder) {
    int n=postorder.length;
    return dfs(postorder,0,n-1);
}

//left表示左子序列起始位置下标，root表示根节点所在下标
private boolean dfs(int[] postorder,int left,int root){
    if(left>=root)return true;
    int right=left,i=left;
    //寻找右节点
    while(i<root&&postorder[i]<postorder[root]) i++;
    right=i;
    // 检查右子树有没有比根节点小的
    for(i+=1;i<root;i++){
        if(postorder[i]<postorder[root])return false;
    }
    //划分为左右两个子序列
    boolean l=dfs(postorder,left,right-1);
    boolean r=dfs(postorder,right,root-1);
    return l&&r;
}

// 方法3：
public boolean verifyPostorder(int[] arr) {
    Stack<Integer> stack = new Stack<>();
    int root = MAX_VALUE;
    for (int i = arr.length - 1; i >= 0; i--) {
        if (arr[i] > root)
            return false;

        while (!stack.isEmpty() && stack.peek() > arr[i])
            root = stack.pop();

        stack.add(arr[i]);
    }
    return true;
}
*/
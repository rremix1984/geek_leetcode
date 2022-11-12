/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import com.leetcode.util.MathUtils;
import com.leetcode.util.TreeNode;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static com.leetcode.util.MathUtils.cTree;
import static com.leetcode.util.MathUtils.inOrder;
import static org.junit.Assert.assertArrayEquals;

/**
    (简单)
    501. 二叉搜索树中的众数
        给你一个含重复值的二叉搜索树（BST）的根节点 root ，
        找出并返回 BST 中的所有 众数（即，出现频率最高的元素）。
        如果树中有不止一个众数，可以按 任意顺序 返回。
        假定 BST 满足如下定义：
            结点左子树中所含节点的值 小于等于 当前节点的值
            结点右子树中所含节点的值 大于等于 当前节点的值
            左子树和右子树都是二叉搜索树
    示例 1：
        输入：root = {1, null, 2, 2}
        输出：[2]
    示例 2：
        输入：root = {0}
        输出：[0]

    提示：
        树中节点的数目在范围 [1, 10 ^ 4] 内
        -10 ^ 5 <= Node.val <= 10 ^ 5
*/
public class NO501_E_FindMode {

    @Test
    public void test() {
        assertArrayEquals(new int[]{2},
            findMode(cTree(1, null, 2, null, null, 2)));
        assertArrayEquals(new int[]{0},
            findMode(cTree(0)));
    }

    public int[] findMode(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        inOrder(root, list);
        int pre = list.get(0);
        int cnt = 1;
        int maxCnt = 1;
        List<Integer> res = new ArrayList<>();
        res.add(list.get(0));
        for (int cur : list) {
            if (pre == cur)
                cnt++;
            else
                cnt = 1;
            if (cnt == maxCnt)
                res.add(cur);
            else if (cnt > maxCnt) {
                maxCnt = cnt;
                res.clear();
                res.add(cur);
            }
            pre = cur;
        }
        return res.stream().mapToInt(Integer::intValue).toArray();
    }

}

















/**
public int[] findMode(TreeNode root) {
    List<Integer> list = new ArrayList<>();

    inOrder(root, list);

    // 记录前一个元素值
    int pre = list.get(0);

    // 记录次数
    int cnt = 1;

    // 记录最大次数
    int maxCnt = 1;

    // 记录结果
    List<Integer> res = new ArrayList<>();
    res.add(list.get(0));
    for (int cur : list) {
        // 如果当前值 (cur) 与前一个节点 (pre) 的值相等
        if (pre == cur)
            cnt++;
        else
            cnt = 1;
        // 如果和最大次数相同，将值放入 res
        if (cnt == maxCnt)
            res.add(cur);
            // 如果大于最大次数
        else if (cnt > maxCnt) {
            // 更新最大次数
            maxCnt = cnt;
            // 重新更新 res
            res.clear();
            res.add(cur);
        }
        pre = cur;
    }
    return res.stream().mapToInt(Integer::intValue).toArray();
}


// 方法2：
List<Integer> answer = new ArrayList<>();
int base, count, maxCount;

public int[] findMode(TreeNode root) {
    dfs(root);
    int[] mode = new int[answer.size()];
    for (int i = 0; i < answer.size(); i++)
        mode[i] = answer.get(i);

    return mode;
}

public void dfs(TreeNode o) {
    if (o == null) {
        return;
    }
    dfs(o.left);
    update(o.val);
    dfs(o.right);
}

public void update(int x) {
    if (x == base) {
        ++count;
    } else {
        count = 1;
        base = x;
    }
    if (count == maxCount) {
        answer.add(base);
    }
    if (count > maxCount) {
        maxCount = count;
        answer.clear();
        answer.add(base);
    }
}
*/
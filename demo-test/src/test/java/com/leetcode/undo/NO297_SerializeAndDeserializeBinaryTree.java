/**
 * copyright 2022/1/19
 */
package com.leetcode.undo;

import com.leetcode.util.TreeNode;
import org.junit.Test;
import java.util.LinkedList;
import java.util.Queue;
import static com.leetcode.util.LogUtil.info;

/**
    （困难）
    297. 二叉树的序列化与反序列化
    序列化是将一个数据结构或者对象转换为连续的比特位的操作，进而可以将转换后的数据存储在
    一个文件或者内存中，同时也可以通过网络传输到另一个计算机环境，采取相反方式重构得到原数据。
    请设计一个算法来实现二叉树的序列化与反序列化。这里不限定你的序列 / 反序列化算法执行逻辑，
    你只需要保证一个二叉树可以被序列化为一个字符串并且将这个字符串反序列化为原始的树结构。
    提示: 输入输出格式与 LeetCode 目前使用的方式一致，详情请参阅 LeetCode 序列化二叉树的格式。
    你并非必须采取这种方式，你也可以采用其他的方法解决这个问题。

    示例 1：
        输入：root = [1,2,3,null,null,4,5]
        输出：[1,2,3,null,null,4,5]
*/
public class NO297_SerializeAndDeserializeBinaryTree {

    @Test
    public void test() {
        String s = serialize(
            new TreeNode(1,
                2, new TreeNode(3,
                                4, 5)));
        info(s);
        info(deserialize(s));
    }

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        if(root == null){
            return "";
        }
        StringBuilder res = new StringBuilder();
        res.append("[");
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (node != null) {
                res.append("" + node.val);
                queue.offer(node.left);
                queue.offer(node.right);
            } else {
                res.append("null");
            }
            res.append(",");
        }
        res.append("]");
        return res.toString();
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        if(data == ""){
            return null;
        }
        String[] dataList = data.substring(1, data.length() - 1).split(",");
        TreeNode root = new TreeNode(Integer.parseInt(dataList[0]));
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int i = 1;
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (!"null".equals(dataList[i])) {
                node.left = new TreeNode(Integer.parseInt(dataList[i]));
                queue.offer(node.left);
            }
            i++;
            if (!"null".equals(dataList[i])) {
                node.right = new TreeNode(Integer.parseInt(dataList[i]));
                queue.offer(node.right);
            }
            i++;
        }
        return root;
    }

}
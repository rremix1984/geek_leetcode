/**
 * copyright 2022/1/19
 */
package com.offer;

import com.leetcode.util.TreeNode;
import org.junit.Test;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import static com.leetcode.util.MathUtils.cTree;

/**
    (困难)
    剑指 Offer 37. 序列化二叉树
        请实现两个函数，分别用来序列化和反序列化二叉树。
        你需要设计一个算法来实现二叉树的序列化与反序列化。这里不限定你的序列 / 反序列化算法执行逻辑，你只需要保证一个二叉树可以被序列化为一个字符串并且将这个字符串反序列化为原始的树结构。
        提示：输入输出格式与 LeetCode 目前使用的方式一致，详情请参阅 LeetCode 序列化二叉树的格式。你并非必须采取这种方式，你也可以采用其他的方法解决这个问题。
    示例：
        输入：root = [1,2,3,null,null,4,5]
        输出：[1,2,3,null,null,4,5]
*/
public class Offer_037_H_Codec {

    @Test
    public void test() {
        TreeNode<Integer> tree = cTree(1, 2, 3, null, null, 4, 5);
        String ser = Codec.serialize(tree);
        assert tree.equals(Codec.deserialize(ser));
    }

    static class Codec {

        public static String serialize(TreeNode<Integer> root) {
            return rserialize(root, "");
        }

        public static TreeNode<Integer> deserialize(String data) {
            String[] dataArray = data.split(",");
            List<String> dataList = new LinkedList<>(Arrays.asList(dataArray));
            return rdeserialize(dataList);
        }

        public static String rserialize(TreeNode<Integer> root, String str) {
            if (root == null) {
                str += "None,";
            } else {
                str += root.val + ",";
                str = rserialize(root.left, str);
                str = rserialize(root.right, str);
            }
            return str;
        }

        public static TreeNode<Integer> rdeserialize(List<String> dataList) {
            if (dataList.get(0).equals("None")) {
                dataList.remove(0);
                return null;
            }
            TreeNode<Integer> root = new TreeNode<>(Integer.valueOf(dataList.get(0)));
            dataList.remove(0);
            root.left = rdeserialize(dataList);
            root.right = rdeserialize(dataList);
            return root;
        }

    }

}
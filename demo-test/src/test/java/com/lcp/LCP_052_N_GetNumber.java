package com.lcp;

import com.leetcode.util.TreeNode;
import org.junit.Test;
import java.util.Set;
import java.util.TreeSet;
import static com.leetcode.util.MathUtils.cTree;

/**
    [ARRAY]
    (中等)
    LCP.052 二叉搜索树染色
    欢迎各位勇者来到力扣城，本次试炼主题为「二叉搜索树染色」。
    每位勇士面前设有一个二叉搜索树的模型，模型的根节点为 root，
    树上的各个节点值均不重复。初始时，所有节点均为蓝色。
    现在按顺序对这棵二叉树进行若干次操作， ops[i] = [type, x, y]
    表示第 i 次操作为：
        type 等于 0 时，将节点值范围在 [x, y] 的节点均染蓝
        type 等于 1 时，将节点值范围在 [x, y] 的节点均染红
    请返回完成所有染色后，该二叉树中红色节点的数量。
    注意：题目保证对于每个操作的 x、y 值定出现在二叉搜索树节点中
    示例 1：
        输入：root = [1,null,2,null,3,null,4,null,5],
             ops = [[1,2,4],[1,1,3],[0,3,5]]
        输出：2
        解释：
            第 0 次操作，将值为 2、3、4 的节点染红；
            第 1 次操作，将值为 1、2、3 的节点染红；
            第 2 次操作，将值为 3、4、5 的节点染蓝；
            因此，最终值为 1、2 的节点为红色节点，返回数量 2
    示例 2：
        输入：root = [4,2,7,1,null,5,null,null,null,null,6]
             ops = [[0,2,2],[1,1,5],[0,4,5],[1,5,7]]
        输出：5
        解释：
            第 0 次操作，将值为 2 的节点染蓝；
            第 1 次操作，将值为 1、2、4、5 的节点染红；
            第 2 次操作，将值为 4、5 的节点染蓝；
            第 3 次操作，将值为 5、6、7 的节点染红；
            因此，最终值为 1、2、5、6、7 的节点为红色节点，返回数量 5
    提示：
        1 <= 二叉树节点数量 <= 10^5
        1 <= ops.length <= 10^5
        ops[i].length == 3
        ops[i][0] 仅为 0 or 1
        0 <= ops[i][1] <= ops[i][2] <= 10^9
        0 <= 节点值 <= 10^9
    Related Topics:树,线段树,二叉搜索树,数组,二分查找,二叉树,有序集合
*/
public class LCP_052_N_GetNumber {

    @Test
    public void test() {
//        assert 2 == getNumber(
//                cTree(1, null, 2, null, 3, null, 4, null, 5),
//                new int[][]{{1, 2, 4}, {1, 1, 3}, {0, 3, 5}});
        assert 5 == getNumber(
                cTree(4, 2, 7, 1, null, 5, null, null, null, null, 6),
                new int[][]{{0, 2, 2}, {1, 1, 5}, {0, 4, 5}, {1, 5, 7}});
    }

    // 保存的是没有操作过的节点
    TreeSet<Integer> set;

    public int getNumber(TreeNode root, int[][] ops) {
        if(root == null)
            return 0;

        set = new TreeSet<>();
        build(root);

        int res = 0;

        for(int i = ops.length - 1; i >= 0; i--){
            while(true){
                // 找到第一个大于x的节点
                Integer upper = set.higher(ops[i][1] - 1);
                if(upper == null || upper > ops[i][2]) break;
                // 删除操作过的节点
                set.remove(upper);
                // 如果是染红，记录红色节点数
                if(ops[i][0] == 1) res++;
            }

        }

        return res;
    }

    private void build(TreeNode root){
        if(root == null) return;
        build(root.left);
        set.add(root.val);
        build(root.right);
    }

}

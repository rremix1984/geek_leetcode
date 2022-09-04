/**
 * copyright 2022/1/19
 */
package com.leetcode.undo;

import org.junit.Test;
import java.util.*;
import static com.leetcode.util.LogUtil.info;

/**
    (困难)
    51. N 皇后
        按照国际象棋的规则，皇后可以攻击与之处在同一行或同一列或同一斜线
        上的棋子。n 皇后问题 研究的是如何将 n 个皇后放置在 n×n 的棋盘
        上，并且使皇后彼此之间不能相互攻击。给你一个整数 n ，返回所有不
        同的 n 皇后问题 的解决方案。每一种解法包含一个不同的 n 皇后问题
        的棋子放置方案，该方案中 'Q' 和 '.' 分别代表了皇后和空位。
    示例 1：
        输入：n = 4
        输出：[[".Q..",
               "...Q",
               "Q...",
               "..Q."]
              ,
              ["..Q.",
               "Q...",
               "...Q",
               ".Q.."]]
        解释：如上图所示，4 皇后问题存在两个不同的解法。
    示例 2:
        输入：n = 1
        输出：[["Q"]]
*/
@SuppressWarnings("all")
public class NO51_H_NQueens {

    @Test
    public void test() {
        info(solveNQueens(8));
    }

    public List<List<String>> solveNQueens(int n) {
        return null;
    }

}

/**
 * copyright 2022/1/19
 */
package com.leetcode.undo;

import org.junit.Test;
import java.util.HashSet;
import java.util.Set;
import static com.leetcode.util.LogUtil.info;

/**
    52. N皇后 II
        n 皇后问题 研究的是如何将 n 个皇后放置在 n × n 的棋盘上，并且使皇后彼此之间不能相互攻击。
        给你一个整数 n ，返回 n 皇后问题 不同的解决方案的数量。
    示例 1：
        输入：n = 4
        输出：2
        解释：如上图所示，4 皇后问题存在两个不同的解法。
    示例 2：
        输入：n = 1
        输出：1
*/
public class NO52_NQueensII {

    @Test
    public void test() {
        info(totalNQueens(4));// 2
        info(totalNQueens(1));// 1
    }


    public int totalNQueens(int qNum) {
        // diagonal 对角线
        return call(qNum, 0, new HashSet<>(),
                               new HashSet<>(),
                               new HashSet<>());
    }

    public int call(int qNum, int row, Set<Integer> columns, Set<Integer> diagonals1, Set<Integer> diagonals2) {
        if (row == qNum)
            return 1;

        int count = 0;
        for (int i = 0; i < qNum; i++) {
            if (columns.contains(i))
                continue;

            int diagonal1 = row - i;
            if (diagonals1.contains(diagonal1))
                continue;

            int diagonal2 = row + i;
            if (diagonals2.contains(diagonal2))
                continue;

            // 回溯法
            columns.add(i);
            diagonals1.add(diagonal1);
            diagonals2.add(diagonal2);

            count += call(qNum, row + 1, columns, diagonals1, diagonals2);

            columns.remove(i);
            diagonals1.remove(diagonal1);
            diagonals2.remove(diagonal2);
        }
        return count;
    }

}

















/**
public int totalNQueens(int n) {
    Set<Integer> columns = new HashSet<Integer>();
    Set<Integer> diagonals1 = new HashSet<Integer>();
    Set<Integer> diagonals2 = new HashSet<Integer>();
    return backtrack(n, 0, columns, diagonals1, diagonals2);
}

public int backtrack(int n, int row, Set<Integer> columns, Set<Integer> diagonals1, Set<Integer> diagonals2) {
    if (row == n) {
        return 1;
    } else {
        int count = 0;
        for (int i = 0; i < n; i++) {
            if (columns.contains(i)) {
                continue;
            }
            int diagonal1 = row - i;
            if (diagonals1.contains(diagonal1)) {
                continue;
            }
            int diagonal2 = row + i;
            if (diagonals2.contains(diagonal2)) {
                continue;
            }
            columns.add(i);
            diagonals1.add(diagonal1);
            diagonals2.add(diagonal2);
            count += backtrack(n, row + 1, columns, diagonals1, diagonals2);
            columns.remove(i);
            diagonals1.remove(diagonal1);
            diagonals2.remove(diagonal2);
        }
        return count;
    }
}
*/
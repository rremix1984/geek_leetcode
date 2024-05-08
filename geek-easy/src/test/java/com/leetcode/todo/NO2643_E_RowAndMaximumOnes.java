package com.leetcode.todo;

import org.junit.Test;
import static com.leetcode.util.MathUtils.getArrays;
import static org.junit.Assert.assertArrayEquals;

/**
    [ARRAY]
    （简单）
    NO.2643 一最多的行
    给你一个大小为 m x n 的二进制矩阵 mat ，请你找出包含最多 1 的行的下标（从 0 开始）以及这一行中 1 的数目。
    如果有多行包含最多的 1 ，只需要选择 行下标最小 的那一行。
    返回一个由行下标和该行中 1 的数量组成的数组。
    示例 1：
        输入：mat = [[0,1],[1,0]]
        输出：[0,1]
        解释：两行中 1 的数量相同。所以返回下标最小的行，下标为 0 。该行 1 的数量为 1 。所以，答案为 [0,1] 。
    示例 2：
        输入：mat = [[0,0,0],[0,1,1]]
        输出：[1,2]
        解释：下标为 1 的行中 1 的数量最多。该行 1 的数量为 2 。所以，答案为 [1,2] 。
    示例 3：
        输入：mat = [[0,0],[1,1],[0,0]]
        输出：[1,2]
        解释：下标为 1 的行中 1 的数量最多。该行 1 的数量为 2 。所以，答案为 [1,2] 。
    提示：

        m == mat.length
        n == mat[i].length
        1 <= m, n <= 100
        mat[i][j] 为 0 或 1
    Related Topics:数组,矩阵
*/
@SuppressWarnings("all")
public class NO2643_E_RowAndMaximumOnes {

    @Test
    public void test() {
        assertArrayEquals(getArrays(0, 1),
            rowAndMaximumOnes(new int[][]{{0,1}, {1,0}}));
        assertArrayEquals(getArrays(1, 2),
            rowAndMaximumOnes(new int[][]{{0,0,0}, {0,1,1}}));
        assertArrayEquals(getArrays(1, 2),
            rowAndMaximumOnes(new int[][]{{0,0},{1,1},{0,0}}));
    }

    public int[] rowAndMaximumOnes(int[][] mat) {
        int[]res=new int[2];
        for(int i=0;i<mat.length;i++){
            int n=0;
            for(int j=0;j<mat[0].length;j++){
                if(mat[i][j]==1) n++;
            }
            if(n>res[1]){
                res[1]=n;
                res[0]=i;
            }
        }
        return res;
    }

}



















/*
// 方法1
public int[] rowAndMaximumOnes(int[][] mat) {
    int[]res=new int[2];
    for(int i=0;i<mat.length;i++){
        int n=0;
        for(int j=0;j<mat[0].length;j++){
            if(mat[i][j]==1) n++;
        }
        if(n>res[1]){
            res[1]=n;
            res[0]=i;
        }
    }
    return res;
}
*/
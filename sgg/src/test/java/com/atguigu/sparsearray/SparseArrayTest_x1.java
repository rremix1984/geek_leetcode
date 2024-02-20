package com.atguigu.sparsearray;

import org.junit.Before;
import org.junit.Test;

/**
 * 稀疏矩阵处理方法：
 * 1.记录数组一共有几行、几列。有多少个不同的值
 * 2.把具有不同值的元素的行、列值记录在一个小规模数组中，从而缩小程序的规模
 * <p>
 *
 * 0   0   0   22  0   0   15
 * 0   11  0   0   0   17  0
 * 0   0   0   -6  0   0   0
 *</p>
 *     [行    列     值]
 * [0]  3     7     5   <-- 3行 7列 共5个元素
 * [1]  0     3     22  <-- 第 0 行，第 3 列，值为 22
 * [2]  0     6     15
 * [3]  1     1     11
 * [4]  1     5     17
 * [5]  2     3     -6
 * <p>
 *（1）二维数组 转 稀疏数组的思路
 *  1.遍历 原始的二维数组，得到有效数据的个数 sum
 *  2. 根据sum 就可以创建 稀疏数组 sparseArr int[sum +1][3]
 *  3. 将二维数组的有效数据数据存入到 稀疏数组
 *（2）稀疏数组转原始的二维数组的思路
 *  1.先读取稀疏数组的第一行，根据第一行的数据，创建原始的二维数组，比如上面的 chessArr2 =int [11][11]
 *  2. 在读取稀疏数组后几行的数据，并赋给 原始的二维数组 即可.
 * </p>
 *
 */
public class SparseArrayTest_x1 {

	// 初始化棋盘，大小是 11 x 11
	int[][] chessArr1;

	@Before
	public void before() {
		chessArr1 = new int[11][11];
		chessArr1[1][2] = 44;
		chessArr1[2][3] = 55;
		chessArr1[4][5] = 66;
		chessArr1[4][8] = 77;
		chessArr1[5][3] = 22;
		chessArr1[6][5] = 33;
		chessArr1[6][9] = 99;
		chessArr1[7][0] = 88;
		chessArr1[8][5] = -6;
		chessArr1[9][9] = 11;
		System.out.println(">>>>>>>>原始矩阵<<<<<<<<<<");
		for (int[] row : chessArr1) {
			for (int data : row)
				System.out.printf("%d\t", data);
			System.out.println();
		}
		System.out.println();
	}

	@Test
	public void test() {
		//1.转换稀疏矩阵
		int[][] sparseArr = zhuanhua(chessArr1);

		//2.还原
		huanyuan(sparseArr);
	}

	private int[][] zhuanhua(int[][] chessArr1) {
		// 创建稀疏数组 sparseArr
		int[][] sparseArr;
		int row = chessArr1.length;
		int col = chessArr1[0].length;

		int sum = 0;
		// 1.根据原始矩阵，找到元素总数，并初始化sparse数组
		for (int[] rowArr : chessArr1)
			for (int data : rowArr)
				if (data != 0)
					sum++;

		sparseArr = new int[sum + 1][3];
		sparseArr[0] = new int[]{row, col, sum};
		int count = 0;
		// 遍历二维数组，将非0的值存入稀疏数组中
		for (int i = 0; i < row; i++)
			for (int j = 0; j < col; j++)
				if (chessArr1[i][j] != 0)
					sparseArr[++count] = new int[]{i, j, chessArr1[i][j]};

		// 2.打印生成的稀疏数组
		for (int[] ints : sparseArr) {
			System.out.printf("%d\t%d\t%d\t\n", ints[0], ints[1], ints[2]);
		}
		System.out.println();
		return sparseArr;
	}

	private void huanyuan(int[][] sparseArr) {
		int row = sparseArr[0][0];
		int col = sparseArr[0][1];
		// 1.初始化目标矩阵 result
		int[][] result = new int[row][col];

		// 2.通过稀疏数组还原成二维数组
		for (int i = 1; i< sparseArr.length; i++) {
			result[sparseArr[i][0]][sparseArr[i][1]] = sparseArr[i][2];
		}

		// 3.打印矩阵 result
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				System.out.printf("%d\t", result[i][j]);
			}
			System.out.println();
		}

	}

}


























/*
private int[][] zhuanhua(int[][] chessArr1) {
	int row = chessArr1.length;
	int col = chessArr1[0].length;
	// 先得到非0元素个数 sum
	int sum = 0;
	for (int i = 0; i < row; i++)
		for (int j = 0; j < col; j++)
			if (chessArr1[i][j] != 0)
				sum++;

	int[][] sparseArr = new int[sum + 1][3];
	sparseArr[0][0] = row;
	sparseArr[0][1] = col;
	sparseArr[0][2] = sum;

	int count = 0;
	for (int i = 0; i < chessArr1.length; i++) {
		for (int j = 0; j < chessArr1[0].length; j++) {
			if (chessArr1[i][j] != 0) {
				count++;
				sparseArr[count][0] = i;
				sparseArr[count][1] = j;
				sparseArr[count][2] = chessArr1[i][j];
			}
		}
	}

	System.out.println("生成稀疏数组如下：");
	System.out.println(">>>>>>>>1.转换<<<<<<<<<<");
	for (int[] ints : sparseArr)
		System.out.printf("%d\t%d\t%d\t\n", ints[0], ints[1], ints[2]);

	System.out.println();
	return sparseArr;
}

private void huanyuan(int[][] sparseArr) {
	int[][] chessArr2 = new int[sparseArr[0][0]][sparseArr[0][1]];
	for(int i = 1; i < sparseArr.length; i++)
		chessArr2[sparseArr[i][0]][sparseArr[i][1]] = sparseArr[i][2];

	System.out.println("通过稀疏数组还原成二位数组");
	System.out.println(">>>>>>>>2.还原<<<<<<<<<<");

	for (int[] row : chessArr2) {
		for (int data : row)
			System.out.printf("%d\t", data);
		System.out.println();
	}
}
*/
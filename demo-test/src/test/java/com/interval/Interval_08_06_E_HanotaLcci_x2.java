/**
 * copyright 2022/1/19
 */
package com.interval;

import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

import static com.leetcode.util.MathUtils.getArray;

/**
    (简单)
    面试题 08.06. 汉诺塔问题
        在经典汉诺塔问题中，有 3 根柱子及 N 个不同大小的穿孔圆盘，盘子可以滑入任意一根柱子。一开始，所有盘子自上而下按升序依次套在第一根柱子上(即每一个盘子只能放在更大的盘子上面)。移动圆盘时受到以下限制:
        (1) 每次只能移动一个盘子;
        (2) 盘子只能从柱子顶端滑出移到下一根柱子;
        (3) 盘子只能叠在比它大的盘子上。
        请编写程序，用栈将所有盘子从第一根柱子移到最后一根柱子。
        你需要原地修改栈。
    示例1:
        输入：A = [2, 1, 0], B = [], C = []
        输出：C = [2, 1, 0]
    示例2:
        输入：A = [1, 0], B = [], C = []
        输出：C = [1, 0]
*/
public class Interval_08_06_E_HanotaLcci_x2 {

    @Test
    public void test() {
        ArrayList<Integer> c = new ArrayList<>();
        hanota(getArray(2, 1, 0), getArray(), c);
        assert c.equals(getArray(2, 1, 0));

        ArrayList<Integer> c2 = new ArrayList<>();
        hanota(getArray(5, 4, 3, 2, 1, 0), getArray(), c2);
        assert c2.equals(getArray(5, 4, 3, 2, 1, 0));
    }

    public void hanota(List<Integer> a, List<Integer> b, List<Integer> c) {
        
    }

}




















/**
// 方法1：递归法
// 如果要解决n层汉诺塔，一定要先解决n-1层
// 如果要解决3层汉诺塔，一定要先解决2层
// 如果要解决2层汉诺塔，一定要先解决1层（从 A 到 C）
public void hanota(List<Integer> A, List<Integer> B, List<Integer> C) {
    //递归求解:move(N,A,B,C)=move(n-1,A,C,B)+move(1,A,B,C)+move(n-1,B,A,C)
    //1.先将的A柱子中的n-1个的圆盘移动到B柱子
    //2.再将的A柱子中最后1个圆盘移动到C柱子
    //3.最后将B柱子的n-1个圆盘移动到C柱子
    //4.N代表 A还剩几个元素
    int N = A.size();
    move(N, A, B, C);
}

// 将N个圆盘从A柱经由B柱移动到C柱
void move(int N, List<Integer> A, List<Integer> B, List<Integer> C) {
    // A中只剩下1个圆盘了,直接移动到C柱子后结束
    if (N == 1) {
        C.add(A.remove(A.size() - 1));
        return;
    }

    // 1.先将的A柱子中的N-1个的圆盘移动到B柱子(此时B为目标柱子,A为原始柱子)
    // 步骤1：A -> B
    move(N - 1, A, C, B);

    // 2.再将的A柱子中最后1个圆盘移动到C柱子
    // 步骤2：A -> C
    C.add(A.remove(A.size() - 1));

    // 3.最后将B柱子的N-1个圆盘移动到C柱子(此时C为目标柱子,B为原始柱子)
    // 步骤3：B -> C
    move(N - 1, B, A, C);
}
*/

/**
public void hanota(List<Integer> a, List<Integer> b, List<Integer> c) {
    int n = a.size();
    call(n, a, b, c);
}

private void call(int n, List<Integer> a, List<Integer> b, List<Integer> c) {

    //         ||               ||               ||
    //        _||_              ||            ___||___
    //   ____|____|____     ____||____     __|________|_
    if (n == 1) {
        c.add(a.remove(a.size() - 1));
        return;
    }
    //         ||               ||               ||
    //      ___||___          __||__             ||
    //   __|________|__    __|______|__     _____||_____
    call(n - 1, a, c, b);

    //         ||               ||               ||
    //         ||             __||__          ___||___
    //   ______||______    __|______|__   ___|________|___
    c.add(a.remove(a.size() - 1));

    //         ||               ||             __||__
    //         ||               ||           _|______|_
    //   ______||______    _____||_____  ___|__________|__
    call(n - 1, b, a, c);
}
*/
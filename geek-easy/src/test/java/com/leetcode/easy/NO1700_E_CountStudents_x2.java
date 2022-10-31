/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

/**
    (简单)
    1700. 无法吃午餐的学生数量
        学校的自助午餐提供圆形和方形的三明治，分别用数字 0 和 1 表示。
        所有学生站在一个队列里，每个学生要么喜欢圆形的要么喜欢方形的。
        餐厅里三明治的数量与学生的数量相同。所有三明治都放在一个 栈 里，每一轮：
        如果队列最前面的学生 喜欢 栈顶的三明治，那么会 拿走它 并离开队列。
        否则，这名学生会 放弃这个三明治 并回到队列的尾部。
        这个过程会一直持续到队列里所有学生都不喜欢栈顶的三明治为止。
        给你两个整数数组 students 和 sandwiches ，其中 sandwiches[i] 是栈里面第 i 个
        三明治的类型（i = 0 是栈的顶部），
        students[j] 是初始队列里第 j 名学生对三明治的喜好（j = 0 是队列的最开始位置）。
        请你返回无法吃午餐的学生数量。
    示例 1：
        输入：students = {1, 1, 0, 0},  sandwiches = {0, 1, 0, 1}
        输出：0
        解释：- 最前面的学生放弃最顶上的三明治，并回到队列的末尾，学生队列变为 students = {1, 0, 0, 1}。
             - 最前面的学生放弃最顶上的三明治，并回到队列的末尾，学生队列变为 students = {0, 0, 1, 1}。
             - 最前面的学生拿走最顶上的三明治，剩余学生队列为 students = {0, 1, 1}，三明治栈为 sandwiches = {1, 0, 1}。
             - 最前面的学生放弃最顶上的三明治，并回到队列的末尾，学生队列变为 students = {1, 1, 0}。
             - 最前面的学生拿走最顶上的三明治，剩余学生队列为 students = {1, 0}，三明治栈为 sandwiches = {0, 1}。
             - 最前面的学生放弃最顶上的三明治，并回到队列的末尾，学生队列变为 students = {0, 1}。
             - 最前面的学生拿走最顶上的三明治，剩余学生队列为 students = {1}，三明治栈为 sandwiches = {1}。
             - 最前面的学生拿走最顶上的三明治，剩余学生队列为 students = {}，三明治栈为 sandwiches = {}。
             所以所有学生都有三明治吃。
    示例 2：
        输入：students = {1, 1, 1, 0, 0, 1},  sandwiches = {1, 0, 0, 0, 1, 1}
                        {1, 1, 0, 0, 1}  {0, 0, 0, 1, 1}
                        {0, 0, 1, 1, 1}  {0, 0, 0, 1, 1}
                        {1, 1, 1}        {0, 1, 1}
             注意：当student队列只有1，sandwitches 头元素为0的时候，无论如何都消化不下去了
        输出：3

    方法一：模拟
        假设喜欢吃圆形三明治的学生数量为 s_0，喜欢吃方形三明治的学生数量为 s_1。
    根据题意，我们可以知道栈顶的三明治能否被拿走取决于队列剩余的学生中是否有喜欢它的，
    因此学生在队列的相对位置不影响整个过程，我们只需要记录队列剩余的学生中 s_0和 s_1
    的值。我们对整个过程进行模拟，如果栈顶的元素为 0 并且 s0 >0，我们将 s0 减 1；
    如果栈顶的元素为 1 并且 s1>0，我们将 s_1 减 1；否则终止过程，并返回 s0+s1
*/
public class NO1700_E_CountStudents_x2 {

    @Test
    public void test() {
        assert 0 == countStudents(
                new int[]{1, 1, 0, 0}, new int[]{0, 1, 0, 1});
        assert 3 == countStudents(
                new int[]{1, 1, 1, 0, 0, 1}, new int[]{1, 0, 0, 0, 1, 1});
    }

    public int countStudents(int[] students, int[] sandwiches) {
        return 0;
    }

}
















/**
// 方法2：
public int countStudents(int[] students, int[] sandwiches) {
    int[] counts = new int[2];
    for (int num : students)
        counts[num]++;

    // 当学生不再需要 sandwitches[i] 却还能提供 [i] 的时候
    // 就循环不下去了，后面学生不可能拿到趟过了，直接返回结果
    if (counts[sandwiches[i]] > 0)
        counts[sandwiches[i]]--;
    else if (counts[sandwiches[i]] == 0)
        return sandwiches.length - i;

    return 0;
}

// 方法1：
public int countStudents(int[] students, int[] sandwiches) {
    int s1 = Arrays.stream(students).sum();
    int s0 = students.length - s1;
    for (int sandwich : sandwiches)
        if (sandwich == 0 && s0 > 0) {
            s0--;
        } else if (sandwich == 1 && s1 > 0) {
            s1--;
        } else {
            break;
        }

    return s0 + s1;
}
*/
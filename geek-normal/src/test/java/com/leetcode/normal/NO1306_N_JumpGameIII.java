/**
 * copyright 2022/1/19
 */
package com.leetcode.normal;

import org.junit.Test;

/**
    [ARRAY] |
    (中等)
    1306. 跳跃游戏 III
        这里有一个非负整数数组arr，你最开始位于该数组的起始下标start处。
        当你位于下标i处时，你可以跳到i+arr[i]或者i-arr[i]。
        请你判断自己是否能够跳到对应元素值为0的任一下标处。
        注意，不管是什么情况下，你都无法跳到数组之外。
    示例 1：
        输入：arr = {4, 2, 3, 0, 3, 1, 2}, start = 5
        输出：true
        解释：到达值为 0 的下标 3 有以下可能方案：
             下标 5 -> 下标 4 -> 下标 1 -> 下标 3
             下标 5 -> 下标 6 -> 下标 4 -> 下标 1 -> 下标 3
    示例 2：
        输入：arr = {4, 2, 3, 0, 3, 1, 2}, start = 0
        输出：true
        解释：到达值为 0 的下标 3 有以下可能方案：
             下标 0 -> 下标 4 -> 下标 1 -> 下标 3
    示例 3：
        输入：arr = {3, 0, 2, 1, 2}, start = 2
        输出：false
        解释：无法到达值为 0 的下标 1 处。
*/
public class NO1306_N_JumpGameIII {

    @Test
    public void test() {
        assert  canReach(
                new int[]{4, 2, 3, 0, 3, 1, 2}, 5);// true
        assert  canReach(
                new int[]{4, 2, 3, 0, 3, 1, 2}, 0);// true
        assert !canReach(
                new int[]{3, 0, 2, 1, 2}, 2);// false
        assert  canReach(
                new int[]{4, 2, 3, 0, 3, 1, 2},5);// true
    }

    public boolean canReach(int[] arr, int start) {
        // 2024/2/25 NO.3

        return false;
    }

}




















/**
// 方法1：
public boolean canReach(int[] arr, int start) {
    // 访问过的数组
    boolean[] visited = new boolean[arr.length];
    return dfs(arr, start, visited);
}

public boolean dfs(int[] num, int idx, boolean[] visited) {
    // 越界条件判断，是否重复经过判断
    // 如果重复经过说明上次也没找到值为 0 的节点
    if (idx < 0 || idx >= num.length || visited[idx])
        return false;

    // 当前坐标能够跳的步幅（+step、-step）值
    int step = num[idx];

    // 当值等于0，相当于跳到了0值所在的坐标，结束了
    if (step == 0)
        return true;

    // 标记为访问过
    visited[idx] = true;

    // 递归判断，向前、后跳是否能达到0值坐标
    return dfs(num, idx + step, visited)
        || dfs(num, idx - step, visited);
}
*/
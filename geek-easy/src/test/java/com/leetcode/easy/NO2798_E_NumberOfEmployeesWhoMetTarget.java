package com.leetcode.easy;

import org.junit.Test;

/**
    [ARRAY]
    （简单）
    NO.2798 满足目标工作时长的员工数目
    公司里共有n名员工，按从0到n-1编号。每个员工i已经在公司工作了hours[i]小时。
    公司要求每位员工工作至少target小时。
    给你一个下标从0开始、长度为n的非负整数数组hours和一个非负整数target。
    请你用整数表示并返回工作至少target小时的员工数。
    示例 1：
        输入：hours = [0,1,2,3,4], target = 2
        输出：3
        解释：公司要求每位员工工作至少 2 小时。
            - 员工 0 工作 0 小时，不满足要求。
            - 员工 1 工作 1 小时，不满足要求。
            - 员工 2 工作 2 小时，满足要求。
            - 员工 3 工作 3 小时，满足要求。
            - 员工 4 工作 4 小时，满足要求。
            共有 3 位满足要求的员工。
    示例 2：
        输入：hours = [5,1,4,2,2], target = 6
        输出：0
        解释：公司要求每位员工工作至少 6 小时。
            共有 0 位满足要求的员工。
    提示：
        1 <= n == hours.length <= 50
        0 <= hours[i], target <= 105
    Related Topics: 数组
 */
public class NO2798_E_NumberOfEmployeesWhoMetTarget {

    @Test
    public void test() {
        assert 3 == numberOfEmployeesWhoMetTarget(
                new int[]{0, 1, 2, 3, 4}, 2);
    }

    public int numberOfEmployeesWhoMetTarget(int[] hours, int target) {
        int ans = 0;
        return ans;
    }

}















/*
// 方法1：
public int numberOfEmployeesWhoMetTarget(int[] hours, int target) {
    int ans = 0;
    for (int h : hours)
        if (h >= target)
            ans++;

    return ans;
}
*/
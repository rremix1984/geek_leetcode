/**
 * copyright 2022/1/19
 */
package com.lcp;

import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;

import static com.leetcode.util.MathUtils.getDict;
import static com.leetcode.util.MathUtils.getDictInteger;
import static java.util.Arrays.sort;
import static java.util.Comparator.reverseOrder;

/**
    [ARRAY] |
    (简单)
    LCS 02. 完成一半题目
        有 N 位扣友参加了微软与力扣举办了「以扣会友」线下活动。主办方提供了2 * N道题目，
        整型数组 questions 中每个数字对应了每道题目所涉及的知识点类型。
        若每位扣友选择不同的一题，请返回被选的 N 道题目至少包含多少种知识点类型。
    示例 1：
        输入：questions = {2, 1, 6, 2}
        输出：1
        解释：有 2 位扣友在 4 道题目中选择 2 题。
             可选择完成知识点类型为 2 的题目时，此时仅一种知识点类型
             因此至少包含 1 种知识点类型。
    示例 2：
        输入：questions = {1, 5, 1, 3, 4, 5, 2, 5, 3, 3, 8, 6}
        输出：2
        解释：有 6 位扣友在 12 道题目中选择题目，需要选择 6 题。
        选择完成知识点类型为 3、5 的题目，因此至少包含 2 种知识点类型。
    提示：
        questions.length == 2*n
        2 <= questions.length <= 10^5
        1 <= questions[i] <= 1000
*/
public class LCS_02_E_HalfQuestions {

    @Test
    public void test() {
        assert 1 == halfQuestions(
            new int[]{2, 1, 6, 2});
        assert 2 == halfQuestions(
            new int[]{1, 5, 1, 3, 4, 5, 2, 5, 3, 3, 8, 6});
    }

    public int halfQuestions(int[] questions) {
        // 2024/3/12 NO.1
        int ans = 0;
        Integer[] dict = getDictInteger(1001, questions);

        int sum = 0;
        sort(dict, reverseOrder());
        for (Integer num : dict) {
            sum += num;
            ans++;
            if (sum >= questions.length / 2)
                break;
        }
        return ans;
    }

}

















/*
// 方法1：
public int halfQuestions(int[] questions) {
    // 知识点
    int count = 0;
    // 初始哈希表
    int[] dict = new int[1001];
    for (int i : questions)
        dict[i]++;

    // 数量和
    int sum = 0;
    // 排序
    Arrays.sort(dict);
    for (int i = dict.length - 1; i >= 0; i--) {
        sum += dict[i];
        count++;
        // 当此时的数量和超过问题数 / 2
        if (sum >= questions.length / 2)
            break;
    }

    return count;
}

// 方法2：简化的方法1
public int halfQuestions(int[] questions) {
    int ans = 0;
    int people = questions.length / 2;
    Integer[] dict = getDictInteger(1001, questions);

    int sum = 0;
    sort(dict, reverseOrder());
    for (Integer num : dict) {
        sum += num;
        ans++;
        if (sum >= people)
            break;
    }
    return ans;
}
*/
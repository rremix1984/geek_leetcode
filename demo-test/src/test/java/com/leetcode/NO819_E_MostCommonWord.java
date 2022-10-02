/**
 * copyright 2022/1/19
 */
package com.leetcode;

import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static java.lang.Math.max;
import static org.junit.Assert.assertEquals;

/**
    (简单)
    819. 最常见的单词
        给定一个段落 (paragraph) 和一个禁用单词列表 (banned)。返回出现次数最多，同时不在禁用列表中的单词。
        题目保证至少有一个词不在禁用列表中，而且答案唯一。
        禁用列表中的单词用小写字母表示，不含标点符号。段落中的单词不区分大小写。答案都是小写字母。
    示例：
    输入:
        paragraph = "Bob hit a ball, the hit BALL flew far after it was hit."
        banned = ["hit"]
        输出: "ball"
    解释:
        "hit" 出现了3次，但它是一个禁用的单词。
        "ball" 出现了2次 (同时没有其他单词出现2次)，所以它是段落里出现次数最多的，且不在禁用列表中的单词。
        注意，所有这些单词在段落里不区分大小写，标点符号需要忽略（即使是紧挨着单词也忽略， 比如 "ball,"），
        "hit"不是最终的答案，虽然它出现次数更多，但它在禁用单词列表中。
*/
public class NO819_E_MostCommonWord {

    @Test
    public void test() {
        assertEquals("ball",
            mostCommonWord("Bob hit a ball, the hit BALL flew far after it was hit.", new String[]{"hit"}));
    }

    // 第二次提交，估计第一次是split用时过多16ms，这次直接遍历字符串，成功（用时+内存：100%，81%）
    public String mostCommonWord(String paragraph, String[] banned) {
        // 出现最多次的单词
        String ans = "";
        int max = 0;
        // max：出现最多单词的次数；len：字符串长度
        int len = paragraph.length();

        // 先变小写
        paragraph = paragraph.toLowerCase();

        // 存放需排除的串，后续判断是否跳过
        Set<String> set = new HashSet<>();

        // 存放已遍历的单词和出现次数
        Map<String,Integer> map = new HashMap<>();

        // 字符串转字符数组
        char[] arr = paragraph.toCharArray();

        // 添加需排除的值
        for (String s:banned)
            set.add(s.toLowerCase());

        // i和j用于记录遍历位置，即分割字符串的左右两端，注意这里结束条件j<=len
        for (int i = 0, j = 0; j <= len; j++) {
            if (j < len && arr[j] >= 'a' && arr[j] <= 'z')
                continue;// 找到的是字母，直接跳过

            // 找到 i<j 使得 i与j 之间成单词字符串
            if (i < j) {
                // 分割字符串成 x
                String x = paragraph.substring(i,j);

                // 跳过判断
                if (!set.contains(x)) {
                    // 字符串 x 的出现次数
                    int count = map.getOrDefault(x,0) + 1;

                    // 记录单词和个数
                    map.put(x, count);

                    // 当前单词重复次数更多，替换ans
                    if (count > max)
                        ans = x;

                    // 单词最大重复次数
                    max = max(count, max);
                }
            }
            // 处理完i到j的字符串后 重置i
            i = j + 1;
        }
        return ans;
    }

}
/**
 * copyright 2022/1/19
 */
package com.leetcode.undo;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
    (中等)
    2288. 价格减免
        句子 是由若干个单词组成的字符串，单词之间用单个空格分隔，其中每个单词可以包含数字、小写字母、和美元符号 '$' 。如果单词的形式为美元符号后跟着一个非负实数，那么这个单词就表示一个价格。
        例如 "$100"、"$23" 和 "$6.75" 表示价格，而 "100"、"$" 和 "2$3" 不是。
        注意：本题输入中的价格均为整数。
        给你一个字符串 sentence  和一个整数 discount 。对于每个表示价格的单词，都在价格的基础上减免 discount% ，并 更新 该单词到句子中。所有更新后的价格应该表示为一个 恰好保留小数点后两位 的数字。
        返回表示修改后句子的字符串。
    示例 1：
        输入：sentence = "there are $1 $2 and 5$ candies in the shop", discount = 50
        输出："there are $0.50 $1.00 and 5$ candies in the shop"
        解释：表示价格的单词是 "$1" 和 "$2" 。
             - "$1" 减免 50% 为 "$0.50" ，所以 "$1" 替换为 "$0.50" 。
             - "$2" 减免 50% 为 "$1" ，所以 "$1" 替换为 "$1.00" 。
    示例 2：
        输入：sentence = "1 2 $3 4 $5 $6 7 8$ $9 $10$", discount = 100
        输出："1 2 $0.00 4 $0.00 $0.00 7 8$ $0.00 $10$"
        解释：任何价格减免 100% 都会得到 0 。
             表示价格的单词分别是 "$3"、"$5"、"$6" 和 "$9"。
             每个单词都替换为 "$0.00"。
    提示：
        1 <= sentence.length <= 105
        sentence 由小写英文字母、数字、' ' 和 '$' 组成
        sentence 不含前导和尾随空格
        sentence 的所有单词都用单个空格分隔
        所有价格都是 正 整数且不含前导零
        所有价格 最多 为  10 位数字
        0 <= discount <= 100
*/
public class NO2288_N_DiscountPrices {

    @Test
    public void test() {
        assertEquals("there are $0.50 $1.00 and 5$ candies in the shop",
                discountPrices("there are $1 $2 and 5$ candies in the shop", 50));
        assertEquals("1 2 $0.00 4 $0.00 $0.00 7 8$ $0.00 $10$",
                discountPrices("1 2 $3 4 $5 $6 7 8$ $9 $10$", 100));
    }

    public String discountPrices(String sentence, int discount) {
        // 字符串拼接
        StringBuilder sb = new StringBuilder();

        // 根据空格分隔每个单词
        String[] strs = sentence.split(" ");

        for (int i = 0; i < strs.length; i++) {
            // 获取当前单词
            String s = strs[i];

            // 正则表达式判断是否负责题目规则：有且仅有1个$，加上1个或多个数字
            if (s.matches("[$]{1}[0-9]+")) {
                // 剔除$符号
                s = s.substring(1);
                // 转成double
                double d = Double.valueOf(s);
                // 减去折扣
                d = d - d * ((double) discount / 100);
                // 加上$符号，取两位小数点
                strs[i] = "$" + String.format("%.2f", d);
            }
            sb.append(strs[i]);

            // 不是最后一个单词
            if(i < strs.length - 1)
                // 后面加多个空格
                sb.append(" ");
        }
        return sb.toString();
    }

}

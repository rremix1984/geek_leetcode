/**
 * copyright 2022/1/19
 */
package com.leetcode.donnot;

import org.junit.Test;

/**
    (简单)
    1694. 重新格式化电话号码
        给你一个字符串形式的电话号码number。number由数字、空格' '、和破折号'-'组成。
        请你按下述方式重新格式化电话号码。
            1）首先，删除 所有的空格和破折号。
            2）其次，将数组从左到右 每 3 个一组 分块，直到 剩下 4 个或更少数字。
                剩下的数字将按下述规定再分块：
                2 个数字：单个含 2 个数字的块。
                3 个数字：单个含 3 个数字的块。
                4 个数字：两个分别含 2 个数字的块。
            3）最后用破折号将这些块连接起来。
        注意，重新格式化过程中不应该生成仅含1个数字的块，并且最多生成两个含2个数字的块。
        返回格式化后的电话号码。
    示例 1：
        输入：number = "1-23-45 6"
        输出："123-456"
        解释：数字是 "123456"
             步骤 1：共有超过 4 个数字，所以先取 3 个数字分为一组。第 1 个块是 "123" 。
             步骤 2：剩下 3 个数字，将它们放入单个含 3 个数字的块。第 2 个块是 "456" 。
             连接这些块后得到 "123-456" 。
    示例 2：
        输入：number = "123 4-567"
        输出："123-45-67"
        解释：数字是 "1234567".
             步骤 1：共有超过 4 个数字，所以先取 3 个数字分为一组。第 1 个块是 "123" 。
             步骤 2：剩下 4 个数字，所以将它们分成两个含 2 个数字的块。这 2 块分别是 "45" 和 "67" 。
             连接这些块后得到 "123-45-67" 。
    示例 3：
        输入：number = "123 4-5678"
        输出："123-456-78"
        解释：数字是 "12345678" 。
             步骤 1：第 1 个块 "123" 。
             步骤 2：第 2 个块 "456" 。
             步骤 3：剩下 2 个数字，将它们放入单个含 2 个数字的块。第 3 个块是 "78" 。
             连接这些块后得到 "123-456-78" 。
    示例 4：
        输入：number = "12"
        输出："12"
    示例 5：
        输入：number = "--17-5 229 35-39475 "
        输出："175-229-353-94-75"
*/
public class NO1694_E_ReformatNumber {

    @Test
    public void test() {
        assert "123-456".equals(reformatNumber("1-23-45 6"));
        assert "123-45-67".equals(reformatNumber("123 4-567"));
        assert "123-456-78".equals(reformatNumber("123 4-5678"));
        assert "12".equals(reformatNumber("12"));
        assert "175-229-353-94-75".equals(reformatNumber("--17-5 229 35-39475 "));
    }

    public String reformatNumber(String number) {
        // 1.去掉所有的空格 ' ' 和破折号 '-'
        String s = number.replace(" ", "")
                         .replace("-", "");

        // 2.每3个一组，通过 '-' 连接
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i += 3)
            // 如果大于4个长度，需要拆分
            if (i + 4 > s.length() - 1) {
                // 如果最后剩下 1 - 2 个元素，直接拼接就好
                if (i + 2 >= s.length() - 1)
                    sb.append(s.substring(i));
                // 如果最后剩下 4 个元素，后面拼 2个 + 2个
                else
                    sb.append(s, i, i + 2)
                      .append("-")
                      .append(s.substring(i + 2));
                return sb.toString();
            } else {
                // 每次从 s 向后截取 3 个元素
                sb.append(s, i, i + 3).append('-');
            }

        return sb.toString();
    }

}


















/**
// 方法1：
public String reformatNumber(String number) {
    // 1.去掉所有的空格 ' ' 和破折号 '-'
    String s = number.replace(" ", "")
            .replace("-", "");

    // 2.每3个一组，通过 '-' 连接
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < s.length(); i += 3) {
        if (sb.length() != 0)
            sb.append("-");

        // 如果大于4个长度，需要拆分
        if (i + 4 > s.length() - 1) {
            // 如果最后剩下 1 - 2 个元素，直接拼接就好
            if (i + 2 >= s.length() - 1)
                sb.append(s.substring(i));
                // 如果最后剩下 3 - 4 个元素，后面拼 2个 + 2个
            else
                sb.append(s, i, i + 2)
                        .append("-")
                        .append(s.substring(i + 2));
            break;
        }
        // 每次从 s 向后截取 3 个元素
        sb.append(s, i, i + 3);
    }
    return sb.toString();
}
*/
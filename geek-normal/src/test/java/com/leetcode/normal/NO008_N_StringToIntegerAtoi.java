/**
 * copyright 2022/1/19
 */
package com.leetcode.normal;

import org.junit.Test;
import static com.leetcode.util.SystemUtil.*;
import static java.lang.Integer.*;

/**
    [STRING] ||
    (中等,面试题)
    NO.8 字符串转换整数 (atoi)
    请你来实现一个 myAtoi(string s) 函数，使其能将字符串转换成一个32位有符号整数（类似C/C++中的 atoi 函数）。
    函数 myAtoi(string s) 的算法如下：
      1）读入字符串并丢弃无用的前导空格
      2）检查下一个字符（假设还未到字符末尾）为正还是负号，读取该字符（如果有）。
      3）确定最终结果是负数还是正数。 如果两者都不存在，则假定结果为正。
      4）读入下一个字符，直到到达下一个非数字字符或到达输入的结尾。字符串的其余部分将被忽略。
    将前面步骤读入的这些数字转换为整数（即，"123" -> 123， "0032" -> 32）。
    如果没有读入数字，则整数为 0 。必要时更改符号（从步骤 2 开始）。
    如果整数数超过 32 位有符号整数范围 [−2 ^ 31,  2 ^ 31 − 1] ，需要截断这个整数，
    使其保持在这个范围内。具体来说，小于 −2 ^ 31 的整数应该被固定为 −2 ^ 31 ，
    大于 2 ^ 31 − 1 的整数应该被固定为 2 ^ 31 − 1 。
    返回整数作为最终结果。
    注意：
        本题中的空白字符只包括空格字符 ' ' 。
        除前导空格或数字后的其余字符串外，请勿忽略 任何其他字符。
    示例 1：
        输入：s = "42"
        输出：42
        解释：加粗的字符串为已经读入的字符，插入符号是当前读取的字符。
            第 1 步："42"（当前没有读入字符，因为没有前导空格）
            第 2 步："42"（当前没有读入字符，因为这里不存在 '-' 或者 '+'）
            第 3 步："42"（读入 "42"）
        解析得到整数 42 。
        由于 "42" 在范围 [-231, 231 - 1] 内，最终结果为 42 。
    示例 2：
        输入：s = "   -42"
        输出：-42
        解释：第 1 步："   -42"（读入前导空格，但忽视掉）
             第 2 步："   -42"（读入 '-' 字符，所以结果应该是负数）
             第 3 步："   -42"（读入 "42"）
        解析得到整数 -42 。
        由于 "-42" 在范围 [-231, 231 - 1] 内，最终结果为 -42 。
    示例 3：
        输入：s = "4193 with words"
        输出：4193
        解释：第 1 步："4193 with words"（当前没有读入字符，因为没有前导空格）
             第 2 步："4193 with words"（当前没有读入字符，因为这里不存在 '-' 或者 '+'）
             第 3 步："4193 with words"（读入 "4193"；由于下一个字符不是一个数字，所以读入停止）
        解析得到整数 4193 。
        由于 "4193" 在范围 [-231, 231 - 1] 内，最终结果为 4193 。
*/
public class NO008_N_StringToIntegerAtoi {

    @Test
    public void test() {
        assert  42  == myAtoi("42");             // 42
        assert -42  == myAtoi("   -42");         // -42
        assert 4193 == myAtoi("4193 with words");// 4193
        assert   0  == myAtoi("00000-42a1234");  // 0
        assert   0  == myAtoi(" ");              // 0
        assert  12  == myAtoi(" 12a345b");       // 12
    }

    public int myAtoi(String s) {
        // 2024/3/19 NO.1
        // 2024/3/25 NO.2 难啃，但是必须啃下来
        int ans = 0;

        return ans;
    }

}















/*
// 方法1：
public int myAtoi(String str) {
    str = str.trim();
    if (str.length() == 0) return 0;
    if (!Character.isDigit(str.charAt(0))
            && str.charAt(0) != '-' && str.charAt(0) != '+')
        return 0;
    long ans = 0L;
    boolean neg = str.charAt(0) == '-';
    int i = !Character.isDigit(str.charAt(0)) ? 1 : 0;
    while (i < str.length() && Character.isDigit(str.charAt(i))) {
        ans = ans * 10 + (str.charAt(i++) - '0');
        if (!neg && ans > Integer.MAX_VALUE) {
            ans = Integer.MAX_VALUE;
            break;
        }
        if (neg && ans > 1L + Integer.MAX_VALUE) {
            ans = 1L + Integer.MAX_VALUE;
            break;
        }
    }
    return neg ? (int) -ans : (int) ans;
}

// 方法2：短一点（推荐）
public int myAtoi(String s) {
    char[] arr = s.trim().toCharArray();
    int n = arr.length;
    long ans = 0;
    boolean isPositive = true;
    int sign = false;
    for (int i = 0; i < n; i++) {
        char ch = arr[i];
        if (!sign && (ch == '-' || ch == '+')) {
            isPositive = ch == '-' ? false : true;
            sign = true;
            continue;
        } else if (!Character.isDigit(ch)) {
            break;
        }
        sign = true;
        ans = ans * 10 + Character.getNumericValue(ch);
        // ans = ans * 10 + (ch - '0'); // 等效上面的
        if (ans > Integer.MAX_VALUE) {
            return isPositive ? Integer.MAX_VALUE : Integer.MIN_VALUE;
        }
    }
    return isPositive ? (int)ans : (int)-ans;
}

// 方法3：
public int myAtoi(String str) {
    int len = str.length();
    // str.charAt(i) 方法回去检查下标的合法性，一般先转换成字符数组
    char[] charArray = str.toCharArray();

    // 1、去除前导空格
    int index = 0;
    while (index < len && charArray[index] == ' ')
        index++;

    // 2、如果已经遍历完成（针对极端用例 "      "）
    if (index == len)
        return 0;

    // 3、如果出现符号字符，仅第1个有效，并记录正负
    int sign = 1;
    char firstChar = charArray[index];
    if (firstChar == '+') {
        index++;
    } else if (firstChar == '-') {
        index++;
        sign = -1;
    }

    // 4、将后续出现的数字字符进行转换
    // 不能使用 long 类型，这是题目说的
    int res = 0;
    while (index < len) {
        char currChar = charArray[index];
        // 4.1 先判断不合法的情况
        if (currChar > '9' || currChar < '0')
            break;

        // 题目中说：环境只能存储 32 位大小的有符号整数，因此，需要提前判：断乘以 10 以后是否越界
        if (res > Integer.MAX_VALUE / 10 || (res == Integer.MAX_VALUE / 10 && (currChar - '0') > Integer.MAX_VALUE % 10))
            return Integer.MAX_VALUE;

        if (res < Integer.MIN_VALUE / 10 || (res == Integer.MIN_VALUE / 10 && (currChar - '0') > -(Integer.MIN_VALUE % 10)))
            return Integer.MIN_VALUE;

        // 4.2 合法的情况下，才考虑转换，每一步都把符号位乘进去
        res = res * 10 + sign * (currChar - '0');
        index++;
    }
    return res;
}

public int myAtoi(String str) {
    int i = 0, j = 0, len = str.length();
    boolean negative = false;
    for (i = 0; i < len; i++) {
        if ('0' <= str.charAt(i) && str.charAt(i) <= '9') {
            break;
        } else if (str.charAt(i) == '-' || str.charAt(i) == '+') {
            negative = str.charAt(i) == '-';
            i++;
            break;
        } else if (str.charAt(i) != ' ') {
            return 0;
        }
    }
    for (j = i; j < len; j++)
        if (str.charAt(j) < '0' || '9' < str.charAt(j))
            break;

    int ret = 0;
    String num = str.substring(i, j);
    for (int x = 0; x < num.length(); x++) {
        int cur = num.charAt(x) - '0';
        if (negative) {
            //这里判断溢出的情况和第7题一样
            if (ret < Integer.MIN_VALUE / 10|| ret == Integer.MIN_VALUE / 10 && cur > 8)
                return Integer.MIN_VALUE;
            ret = ret * 10 - cur;
        } else {
            if (ret > Integer.MAX_VALUE / 10 || ret == Integer.MAX_VALUE / 10 && cur > 7)
                return Integer.MAX_VALUE;
            ret = ret * 10 + cur;
        }
    }
    return ret;
}

// 方法4：有瑕疵，leetcode过不了，对于 " " 返回outOfBoundsException
public int myAtoi(String str) {
    int index = 0;
    int sign = 1;
    int total = 0;

    // 1. Empty string
    if (str.length() == 0)
        return 0;

    // 2. Remove spaces
    while (str.charAt(index) == ' ')
        index++;

    // 3. Handle signs
    if (str.charAt(index) == '+' || str.charAt(index) == '-') {
        sign = str.charAt(index) == '+' ? 1 : -1;
        index++;
    }

    // 4. Convert number and avoid  overflow
    while (index < str.length()) {
        int digit = str.charAt(index) - '0';
        if (digit < 0 || digit > 9)
            break;

        // check if total will be overflow after 10 times and add digit
        if (Integer.MAX_VALUE / 10 < total || Integer.MAX_VALUE / 10 == total && Integer.MAX_VALUE % 10 < digit)
            return sign == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE;

        total = 10 * total + digit;
        index++;
    }
    return total * sign;
}

// 方法5：
public int myAtoi(String str) {
    // 步骤一：判空
    if (str == null || str.isEmpty())
        return 0;

    // 步骤二：去空格
    str = str.trim();

    // 步骤三：判断正负号
    boolean isNega = false;
    if (!str.isEmpty() && (str.charAt(0)=='-'||str.charAt(0)=='+')) {
        isNega = str.charAt(0) == '-';
        str = str.substring(1);
    }

    // 步骤四：非数字开头，直接返回0
    if (str.isEmpty() || '0' > str.charAt(0) || str.charAt(0) > '9')
        return 0;

    // 步骤五：进入循环（必须是数字）
    int ans = 0;
    for (int i = 0; i < str.length() && '0' <= str.charAt(i) && str.charAt(i) <= '9'; i++) {
        int num = str.charAt(i) - '0';
        // 步骤 5-1：正负号
        if (isNega)
            num *= -1;

        // 步骤 5-2：正负值上、下限越界
        if (ans > MAX_VALUE / 10 || (ans == MAX_VALUE / 10 && num > 7))
            return MAX_VALUE;

        if (ans < MIN_VALUE / 10 || (ans == MIN_VALUE / 10 && num < -8))
            return MIN_VALUE;

        // 步骤 5-3：累计前次计算和
        ans = ans * 10 + num;
    }
    return ans;
}
*/
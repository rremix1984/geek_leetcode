/**
 * copyright 2022/1/19
 */
package com.leetcode.normal;

import org.junit.Test;

import static java.lang.Integer.compare;
import static java.lang.Integer.parseInt;

/**
    (中等)
    165. 比较版本号
        给你两个版本号version1和version2，请你比较它们。
        版本号由一个或多个修订号组成，各修订号由一个 '.' 连接。每个修订号由多位数字组成，可能包含前导零。
        每个版本号至少包含一个字符。修订号从左到右编号，下标从0开始，最左边的修订号下标为0，
        下一个修订号下标为1，以此类推。例如，2.5.33 和 0.1都是有效的版本号。
        比较版本号时，请按从左到右的顺序依次比较它们的修订号。比较修订号时，只需比较忽略任何前导零后的整数值。
        也就是说，修订号1和修订号001相等 。如果版本号没有指定某个下标处的修订号，则该修订号视为0。
        例如，版本1.0小于版本1.1，因为它们下标为0的修订号相同，而下标为1的修订号分别为0和1，0 < 1。
        返回规则如下：
            如果 version1 > version2 返回 1，
            如果 version1 < version2 返回 -1，
            除此之外返回 0。
    示例 1：
        输入：version1 = "1.01", version2 = "1.001"
        输出：0
        解释：忽略前导零，"01"和"001"都表示相同的整数"1"
    示例 2：
        输入：version1 = "1.0", version2 = "1.0.0"
        输出：0
        解释：version1没有指定下标为2的修订号，即视为"0"
    示例 3：
        输入：version1 = "0.1", version2 = "1.1"
        输出：-1
        解释：version1中下标为0的修订号是"0"，version2中下标为0的修订号是"1"。0 < 1，所以version1 < version2
    提示：
        1 <= version1.length, version2.length <= 500
        version1和version2仅包含数字和'.'
        version1和version2都是有效版本号
        version1和version2的所有修订号都可以存储在32位整数中
*/
public class NO165_N_CompareVersion_x2 {

    @Test
    public void test() {
        assert  0 == compareVersion("1.01", "1.001");
        assert  0 == compareVersion("1.0", "1.0.0");
        assert -1 == compareVersion("0.1", "1.1");
        assert  1 == compareVersion("1.0.1", "1");
    }

    public int compareVersion(String version1, String version2) {
        return 0;
    }

}














/**
// 方法1：
public int compareVersion(String version1, String version2) {
    String[] v1 = version1.split("\\.");
    String[] v2 = version2.split("\\.");
    int i = 0;
    while (i < v1.length || i < v2.length) {
        int x = 0;
        int y = 0;
        if (i < v1.length)
            x = parseInt(v1[i]);

        if (i < v2.length)
            y = parseInt(v2[i]);

        if (x > y)
            return 1;

        if (x < y)
            return -1;

        i++;
    }
    return 0;
}

// 方法2：
public int compareVersion(String version1, String version2) {
    int i = 0;
    int j = 0;
    while (i < version1.length() || j < version2.length()) {
        // 不是符号点 "." 就是数字，然后累加即可 x =【x * 10 + charAt(i) - '0'】
        int x = 0;
        while (i < version1.length() && version1.charAt(i) != '.') {
            x = x * 10 + version1.charAt(i) - '0';
            i++;
        }
        i++;
        // 不是符号点 "." 就是数字，然后累加即可 y =【y * 10 + charAt(j) - '0'】
        int y = 0;
        while (j < version2.length() && version2.charAt(j) != '.') {
            y = y * 10 + version2.charAt(j) - '0';
            j++;
        }
        j++;
        if (x != y)
            return compare(x, y);
    }
    return 0;
}
*/
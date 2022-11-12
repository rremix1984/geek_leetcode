/**
 * copyright 2022/1/19
 */
package com.leetcode;

import org.junit.Test;
import java.util.*;
import static com.leetcode.util.MathUtils.getArray;

/**
    (中等)
    93. 复原 IP 地址
        有效 IP 地址 正好由四个整数（每个整数位于 0 到 255 之间组成，且不能含有前导 0），
        整数之间用 '.' 分隔。例如："0.1.2.201" 和 "192.168.1.1" 是 有效 IP 地址，
        但是 "0.011.255.245"、"192.168.1.312" 和 "192.168@1.1" 是 无效 IP 地址。
        给定一个只包含数字的字符串 s ，用以表示一个 IP 地址，返回所有可能的有效 IP 地址，
        这些地址可以通过在 s 中插入 '.' 来形成。你 不能 重新排序或删除 s 中的任何数字。
        你可以按 任何 顺序返回答案。
    示例 1：
        输入：s = "25525511135"
        输出：["255.255.11.135","255.255.111.35"]
    示例 2：
        输入：s = "0000"
        输出：["0.0.0.0"]
    示例 3：
        输入：s = "101023"
        输出：["1.0.10.23","1.0.102.3","10.1.0.23","10.10.2.3","101.0.2.3"]
    提示：
        1 <= s.length <= 20
        s 仅由数字组成
*/
public class NO093_N_RestoreIpAddresses {

    @Test
    public void test() {
        assert getArray("255.255.11.135", "255.255.111.35").equals(
                restoreIpAddresses("25525511135"));
        assert getArray("0.0.0.0").equals(
                restoreIpAddresses("0000"));
        assert getArray("1.0.10.23", "1.0.102.3","10.1.0.23", "10.10.2.3", "101.0.2.3").equals(
                restoreIpAddresses("101023"));
    }

    public List<String> restoreIpAddresses(String s) {
        List<String> result = new ArrayList<>();
        if (s.length() > 12)
            return result; // 算是剪枝了

        backTrack(result, s, 0, 0);
        return result;
    }

    // startIndex: 搜索的起始位置， pointNum:添加逗点的数量
    private void backTrack(List<String> result, String ipStr, int startIndex, int pointNum) {
        if (pointNum == 3) {// 逗点数量为3时，分隔结束
            // 判断第四段⼦字符串是否合法，如果合法就放进result中
            if (isValidAddress(ipStr, startIndex,ipStr.length() - 1))
                result.add(ipStr);
            return;
        }

        for (int i = startIndex; i < ipStr.length(); i++) {
            if (!isValidAddress(ipStr, startIndex, i))
                break;

            //在str的后⾯插⼊⼀个逗点
            ipStr = ipStr.substring(0, i + 1) + "." + ipStr.substring(i + 1);

            // 加一个逗点 "."
            pointNum++;

            // 插⼊逗点之后下⼀个⼦串的起始位置为i+2
            backTrack(result, ipStr, i + 2, pointNum);

            // 回溯
            pointNum--;

            ipStr = ipStr.substring(0, i + 1) + ipStr.substring(i + 2);// 回溯删掉逗点
        }
    }

    // 判断字符串s在左闭⼜闭区间[start, end]所组成的数字是否合法
    private Boolean isValidAddress(String s, int start, int end) {
        if (start > end)
            return false;

        // 0开头的数字不合法
        if (s.charAt(start) == '0' && start != end)
            return false;

        int num = 0;
        for (int i = start; i <= end; i++) {
            // 遇到⾮数字字符不合法
            if (s.charAt(i) > '9' || s.charAt(i) < '0')
                return false;

            // 按位累加
            num = num * 10 + (s.charAt(i) - '0');

            // 如果⼤于255了不合法
            if (num > 255)
                return false;
        }
        return true;
    }

}
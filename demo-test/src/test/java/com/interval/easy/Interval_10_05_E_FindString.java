package com.interval.easy;

import org.junit.Test;

/**
    [ARRAY] |
    (简单)
    Interval 10.05 稀疏数组搜索
    稀疏数组搜索。有个排好序的字符串数组，其中散布着一些空字符串，编写一种方法，
    找出给定字符串的位置。
    示例1:
        输入: words = ["at", "", "", "", "ball", "", "",
                    "car", "", "","dad", "", ""], s = "ta"
        输出：-1
        说明: 不存在返回-1。
    示例2:
        输入：words = ["at", "", "", "", "ball", "", "", "car",
                    "", "","dad", "", ""], s = "ball"
        输出：4
    提示: words的长度在[1, 1000000]之间
    Related Topics:数组,字符串,二分查找
*/
public class Interval_10_05_E_FindString {

    @Test
    public void test() {
        assert -1 == findString(
            new String[]{"at", "", "", "", "ball", "", "",
                    "car", "", "","dad", "", ""}, "ta");
        assert 4 == findString(
            new String[]{"at", "", "", "", "ball", "", "",
                    "car", "", "", "dad", "", ""},"ball");
    }

    public int findString(String[] words, String s) {
        // 2024/3/11 NO.1
        if (words.length == 1 && words[0].equals(s))
            return 0;

        int l = 0;
        int r = words.length - 1;
        while (l <= r) {
            while (l < r && words[l].isEmpty())
                l++;

            while (l < r && words[r].isEmpty())
                r--;

            if (l <= r) {
                // 去掉空串 ""
                int tmp = l + (r - l) / 2;
                while (tmp < r && words[tmp].isEmpty())
                    tmp++;

                if (words[tmp].compareTo(s) > 0) {
                    r = tmp - 1;
                } else if (words[tmp].compareTo(s) < 0) {
                    l = tmp + 1;
                } else {
                    return tmp;
                }
            }
        }
        return -1;
    }

}
















/*
// 方法1：
public int findString(String[] words, String s) {
    if (words.length == 1 && words[0].equals(s))
            return 0;

    int i = 0, j = words.length - 1;
    while (i <= j) {
        while (i < j && words[i].isEmpty())
            i++;

        while (i < j && words[j].isEmpty())
            j--;

        if (i <= j) {
            int mid = (i + j) / 2;
            // 去掉空串 ""
            int tmp = mid;
            while (tmp < j && words[tmp].isEmpty())
                tmp++;

            if (words[tmp].compareTo(s) > 0) {
                j = mid - 1;
            } else if (words[tmp].compareTo(s) < 0) {
                i = tmp + 1;
            } else {
                return tmp;
            }
        }
    }
    return -1;
}
*/
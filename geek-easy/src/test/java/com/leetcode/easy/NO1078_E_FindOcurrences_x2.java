/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;

/**
    [STRING]
    (简单)
    1078. Bigram 分词
        给出第一个词 first 和第二个词 second，考虑在某些文本 text 中可能以 "first second third" 形式出现的情况，其中 second 紧随 first 出现，third 紧随 second 出现。
        对于每种这样的情况，将第三个词 "third" 添加到答案中，并返回答案。
    示例 1：
        输入：text = "alice is a good girl she is a good student", first = "a", second = "good"
        输出：["girl","student"]
    示例 2：
        输入：text = "we will we will rock you", first = "we", second = "will"
        输出：["we","rock"]
*/
public class NO1078_E_FindOcurrences_x2 {

    @Test
    public void test() {
        assertArrayEquals(new String[]{"we","rock"}, findOcurrences( "we will we will rock you", "we", "will"));
    }

    public String[] findOcurrences(String text, String first, String second) {
        String[] words = text.split(" ");
        List<String> list = new ArrayList<>();
        for (int i = 2; i < words.length; i++) {
            if (words[i - 2].equals(first) && words[i - 1].equals(second)) {
                list.add(words[i]);
            }
        }
        return list.toArray(new String[0]);
//        int size = list.size();
//        String[] ret = new String[size];
//        for (int i = 0; i < size; i++) {
//            ret[i] = list.get(i);
//        }
//        return ret;
    }

}















/**
public String[] findOcurrences(String text, String first, String second) {
    String[] words = text.split(" ");
    List<String> list = new ArrayList<>();
    for (int i = 2; i < words.length; i++) {
        if (words[i - 2].equals(first) && words[i - 1].equals(second)) {
            list.add(words[i]);
        }
    }
    int size = list.size();
    String[] ret = new String[size];
    for (int i = 0; i < size; i++) {
        ret[i] = list.get(i);
    }
    return ret;
}
*/
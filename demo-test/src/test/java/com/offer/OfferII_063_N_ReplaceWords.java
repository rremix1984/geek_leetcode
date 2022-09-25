/**
 * copyright 2022/1/19
 */
package com.offer;

import org.junit.Test;
import java.util.*;
import static com.leetcode.util.MathUtils.getArray;
import static org.junit.Assert.assertEquals;

/**
    (中等)
    剑指 Offer II 063. 替换单词
        在英语中，有一个叫做 词根(root) 的概念，它可以跟着其他一些词组成另一个较长的单词——我们称这个词为 继承词(successor)。
        例如，词根an，跟随着单词 other(其他)，可以形成新的单词 another(另一个)。
        现在，给定一个由许多词根组成的词典和一个句子，需要将句子中的所有继承词用词根替换掉。如果继承词有许多可以形成它的词根，
        则用最短的词根替换它。
        需要输出替换之后的句子。
    示例 1：
        输入：dictionary = ["cat", "bat", "rat"],  sentence = "the cattle was rattled by the battery"
        输出："the cat was rat by the bat"
    示例 2：
        输入：dictionary = ["a", "b", "c"],  sentence = "aadsfasf absbs bbab cadsfafs"
        输出："a a b c"
    示例 3：
        输入：dictionary = ["a",  "aa",  "aaa",  "aaaa"],  sentence = "a aa a aaaa aaa aaa aaa aaaaaa bbb baba ababa"
        输出："a a a a a a a a bbb baba a"
    示例 4：
        输入：dictionary = ["catt", "cat", "bat", "rat"],  sentence = "the cattle was rattled by the battery"
        输出："the cat was rat by the bat"
    示例 5：
        输入：dictionary = ["ac", "ab"],  sentence = "it is abnormal that this solution is accepted"
        输出："it is ab that this solution is ac"
*/
public class OfferII_063_N_ReplaceWords {

    @Test
    public void test() {
        assertEquals("the cat was rat by the bat", 
                replaceWords(getArray("cat", "bat", "rat"),  "the cattle was rattled by the battery"));
        assertEquals("a a b c", 
                replaceWords(getArray("a", "b", "c"),  "aadsfasf absbs bbab cadsfafs"));
        assertEquals("a a a a a a a a bbb baba a", 
                replaceWords(getArray("a",  "aa",  "aaa",  "aaaa"),  "a aa a aaaa aaa aaa aaa aaaaaa bbb baba ababa"));
        assertEquals("the cat was rat by the bat", 
            replaceWords(getArray("catt", "cat", "bat", "rat"),  "the cattle was rattled by the battery"));
        assertEquals("it is ab that this solution is ac", 
            replaceWords(getArray("ac",  "ab"),  "it is abnormal that this solution is accepted"));
    }

    public String replaceWords(List<String> dictionary, String sentence) {
        return null;
    }

}



















/**
// 方法1：
public String replaceWords(List<String> dictionary, String sentence) {
    Set<String> dict = new HashSet<>(dictionary);
    String[] words = sentence.split(" ");

    for (int i = 0; i < words.length; i++)
        for (int j = 0; j < words[i].length(); j++)
            if (dict.contains(words[i].substring(0, 1 + j))) {
                words[i] = words[i].substring(0, 1 + j);
                break;
            }

    return String.join(" ", words);
}
*/
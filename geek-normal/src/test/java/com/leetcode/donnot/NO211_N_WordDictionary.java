/**
 * copyright 2022/1/19
 */
package com.leetcode.donnot;

import org.junit.Test;
import java.util.HashMap;
import java.util.Map;

/**
    (中等)
    211. 添加与搜索单词 - 数据结构设计
        请你设计一个数据结构，支持 添加新单词 和 查找字符串是否与任何先前添加的字符串匹配 。
        实现词典类 WordDictionary ：
            WordDictionary() 初始化词典对象
            void addWord(word) 将 word 添加到数据结构中，之后可以对它进行匹配
            bool search(word) 如果数据结构中存在字符串与 word 匹配，则返回 true ；否则，返回  false 。word 中可能包含一些 '.' ，每个 . 都可以表示任何一个字母。
    示例：
        输入：["WordDictionary","addWord","addWord","addWord","search","search","search","search"]
             [[],["bad"],["dad"],["mad"],["pad"],["bad"],[".ad"],["b.."]]
        输出：[null,null,null,null,false,true,true,true]
        解释：WordDictionary wordDictionary = new WordDictionary();
             wordDictionary.addWord("bad");
             wordDictionary.addWord("dad");
             wordDictionary.addWord("mad");
             wordDictionary.search("pad"); // 返回 False
             wordDictionary.search("bad"); // 返回 True
             wordDictionary.search(".ad"); // 返回 True
             wordDictionary.search("b.."); // 返回 True
    提示：
        1 <= word.length <= 25
        addWord 中的 word 由小写英文字母组成
        search 中的 word 由 '.' 或小写英文字母组成
        最多调用 104 次 addWord 和 search
*/
public class NO211_N_WordDictionary {

    @Test
    public void test() {
        WordDictionary wordDictionary = new WordDictionary();
        wordDictionary.addWord("bad");
        wordDictionary.addWord("dad");
        wordDictionary.addWord("mad");
        assert !wordDictionary.search("pad"); // 返回 False
        assert wordDictionary.search("bad"); // 返回 True
        assert wordDictionary.search(".ad"); // 返回 True
        assert wordDictionary.search("b.."); // 返回 True
    }

}

class WordDictionary {
    Trie trie;
    public WordDictionary() {
        this.trie = new Trie();
    }

    public void addWord(String word) {
        Trie cur = this.trie;
        for(int i=0;i<word.length();i++){
            char c = word.charAt(i);
            if(cur.map.containsKey(c))
                cur = cur.map.get(c);
            else{
                cur.map.put(c, new Trie());
                cur = cur.map.get(c);
            }
        }
        cur.map.put('#', new Trie());
    }

    public boolean search(String word) {
        word += "#";
        return dfs(0, this.trie, word);
    }

    private boolean dfs(int idx, Trie cur, String word) {
        if(idx == word.length())
            return true;
        char c = word.charAt(idx);
        if(c == '.'){
            for(char k:cur.map.keySet()){
                if(dfs(idx+1,cur.map.get(k),word))
                    return true;
            }
        }
        else if(cur.map.containsKey(c))
            return dfs(idx+1, cur.map.get(c), word);
        return false;
    }
}

class Trie {
    Map<Character, Trie> map;
    public Trie() {
        map = new HashMap<>();
    }
}

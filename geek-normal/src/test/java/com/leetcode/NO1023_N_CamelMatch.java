/**
 * copyright@2019/12/12
 */
package com.leetcode;

import org.junit.Test;
import java.util.ArrayList;
import static com.leetcode.util.SystemUtil.arrayAllMatch;
import static com.leetcode.util.SystemUtil.print;
import static java.util.Arrays.asList;
import java.util.List;

/**
    [ARRAY]
    (中等)
    NO.1023 驼峰式匹配
    给你一个字符串数组queries，和一个表示模式的字符串pattern，请你返回一个
    布尔数组answer。只有在待查项 queries[i]与模式串pattern匹配时，
    answer[i]才为true，否则为false。
    如果可以将小写字母插入模式串pattern 得到待查询项query，那么待查询项与给
    定模式串匹配。可以在任何位置插入每个字符，也可以不插入字符。
    示例 1：
        输入：queries = ["FooBar","FooBarTest","FootBall",
                        "FrameBuffer","ForceFeedBack"],
             pattern = "FB"
        输出：[true, false, true, true, false]
        示例：
            "FooBar" 可以这样生成："F" + "oo" + "B" + "ar"。
            "FootBall" 可以这样生成："F" + "oot" + "B" + "all".
            "FrameBuffer" 可以这样生成："F" + "rame" + "B" + "uffer".
    示例 2：
        输入：queries = ["FooBar","FooBarTest","FootBall","FrameBuffer",
                        "ForceFeedBack"],
             pattern = "FoBa"
        输出：[true, false, true, false, false]
        解释：
            "FooBar" 可以这样生成："Fo" + "o" + "Ba" + "r".
            "FootBall" 可以这样生成："Fo" + "ot" + "Ba" + "ll".
    示例 3：
        输入：queries = ["FooBar","FooBarTest","FootBall","FrameBuffer",
                        "ForceFeedBack"], pattern = "FoBaT"
        输出：[false,true,false,false,false]
        解释：
            "FooBarTest" 可以这样生成："Fo" + "o" + "Ba" + "r" + "T" + "est".
        提示：
            1 <= pattern.length, queries.length <= 100
            1 <= queries[i].length <= 100
            queries[i] 和 pattern 由英文字母组成
    Related Topics:字典树,数组,双指针,字符串,字符串匹配
*/
@SuppressWarnings("ALL")
public class NO1023_N_CamelMatch {

    @Test
    public void test() {
        assert arrayAllMatch(
           camelMatch(new String[]{"FooBar", "FooBarTest", "FootBall",
                    "FrameBuffer", "ForceFeedBack"}, "FB"),
            asList(true, false, true, true, false));
        assert arrayAllMatch(
            camelMatch(new String[]{"FooBar", "FooBarTest", "FootBall",
                    "FrameBuffer", "ForceFeedBack"}, "FoBa"),
            asList(true, false, true, false, false));
        assert arrayAllMatch(
            camelMatch(new String[]{"FooBar","FooBarTest","FootBall",
                    "FrameBuffer", "ForceFeedBack"}, "FoBaT"),
            asList(false, true, false, false, false));
    }

    public List<Boolean> camelMatch(String[] queries, String pattern) {
        List<Boolean> res = new ArrayList<>(queries.length);

        return res;
    }

}















/*
// 方法1：
public List<Boolean> camelMatch(String[] queries, String pattern) {
    List<Boolean> res = new ArrayList<>(queries.length);
    for (String query : queries) {
        String other = getOther(query, pattern);
        if (other.isEmpty())
            res.add(false);
        else
            res.add(other.toLowerCase().equals(other));
    }
    return res;
}

private static String getOther(String query, String pattern) {
    int index = 0;
    // 避免两个串相等时返回""
    StringBuilder sb = new StringBuilder("a");
    for (int i = 0; i < pattern.length(); i++) {
        int index2 = query.indexOf(pattern.charAt(i), index);
        if (index2 < 0)
            return "";

        sb.append(query, index, index2);
        index = index2 + 1;
    }
    return sb.append(query.substring(index)).toString();
}
*/
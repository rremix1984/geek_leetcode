/**
 * copyright 2022/1/19
 */
package com.leetcode;

import lombok.var;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.leetcode.util.LogUtil.info;
import static com.leetcode.util.MathUtils.getArray;
import static org.junit.Assert.assertArrayEquals;

/**
    (中等)
    811. 子域名访问计数
        网站域名 "discuss.leetcode.com" 由多个子域名组成。顶级域名为 "com" ，二级域名为 "leetcode.com" ，最低一级为 "discuss.leetcode.com" 。当访问域名 "discuss.leetcode.com" 时，同时也会隐式访问其父域名 "leetcode.com" 以及 "com" 。
        计数配对域名 是遵循 "rep d1.d2.d3" 或 "rep d1.d2" 格式的一个域名表示，其中 rep 表示访问域名的次数，d1.d2.d3 为域名本身。
        例如，"9001 discuss.leetcode.com" 就是一个 计数配对域名 ，表示 discuss.leetcode.com 被访问了 9001 次。
        给你一个 计数配对域名 组成的数组 cpdomains ，解析得到输入中每个子域名对应的 计数配对域名 ，并以数组形式返回。可以按 任意顺序 返回答案。
    示例 1：
        输入：cpdomains = {"9001 discuss.leetcode.com"}
        输出：{"9001 leetcode.com", "9001 discuss.leetcode.com", "9001 com"}
        解释：例子中仅包含一个网站域名："discuss.leetcode.com"。
        按照前文描述，子域名 "leetcode.com" 和 "com" 都会被访问，所以它们都被访问了 9001 次。
    示例 2：
        输入：cpdomains = {"900 google.mail.com",  "50 yahoo.com",  "1 intel.mail.com",  "5 wiki.org"}
        输出：{"901 mail.com", "50 yahoo.com", "900 google.mail.com", "5 wiki.org", "5 org", "1 intel.mail.com", "951 com"}
        解释：按照前文描述，会访问 "google.mail.com" 900 次，"yahoo.com" 50 次，"intel.mail.com" 1 次，"wiki.org" 5 次。
        而对于父域名，会访问 "mail.com" 900 + 1 = 901 次，"com" 900 + 50 + 1 = 951 次，和 "org" 5 次。
*/
public class NO811_N_SubdomainVisits {

    @Test
    public void name() {
        getArray("9001 leetcode.com", "9001 discuss.leetcode.com", "9001 com").equals(
                subdomainVisits(new String[]{"9001 discuss.leetcode.com"}));
        getArray("9001 leetcode.com", "9001 discuss.leetcode.com", "9001 com").equals(
                subdomainVisits(new String[]{"900 google.mail.com",  "50 yahoo.com",  "1 intel.mail.com",  "5 wiki.org"}));
    }

    public List<String> subdomainVisits(String[] cpdomains) {
        Map<String, Integer> map = new HashMap<>();
        for (String v : cpdomains) {
            int i = v.indexOf(" ");
            int a = Integer.parseInt(v.substring(0, i));
            while (i < v.length()) {
                if (v.charAt(i) == ' ' || v.charAt(i) == '.') {
                    String t = v.substring(i + 1);
                    map.put(t, map.getOrDefault(t, 0) + a);
                }
                i++;
            }
        }
        List<String> ans = new ArrayList<>();
        map.forEach(
            (k, v) -> ans.add(v + " " + k)
        );
        return ans;
    }

}

















/**
public List<String> subdomainVisits(String[] cpdomains) {
    Map<String, Integer> map = new HashMap<>();
    for (String v : cpdomains) {
        int i = v.indexOf(" ");
        int a = Integer.parseInt(v.substring(0, i));
        while (i < v.length()) {
            if (v.charAt(i) == ' ' || v.charAt(i) == '.') {
                String t = v.substring(i + 1);
                map.put(t, map.getOrDefault(t, 0) + a);
            }
            i++;
        }
    }
    List<String> ans = new ArrayList<>();
    map.forEach(
            (k, v) -> ans.add(v + " " + k)
    );
    return ans;
}
*/
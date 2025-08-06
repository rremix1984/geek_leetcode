#!/bin/bash

echo "开始修复剩余的repaint()调用..."

# 查找所有Java文件并修复剩余的repaint()调用
find /Users/wangxiaozhe/workspace/geek_leetcode/geek-animation/src/main/java -name "*.java" -type f | while read file; do
    echo "处理文件: $file"
    
    # 修复单独的repaint();调用（不在SwingUtilities.invokeLater中的）
    # 匹配模式：行首可能有空白，然后是repaint();
    sed -i '' 's/^\([ \t]*\)repaint();$/\1SwingUtilities.invokeLater(() -> repaint());/g' "$file"
    
    # 修复其他形式的repaint();调用
    sed -i '' 's/\([^>]\)repaint();/\1SwingUtilities.invokeLater(() -> repaint());/g' "$file"
    
    # 避免重复包裹
    sed -i '' 's/SwingUtilities\.invokeLater(() -> SwingUtilities\.invokeLater(() -> repaint()););/SwingUtilities.invokeLater(() -> repaint());/g' "$file"
done

echo "修复完成！"

# 验证修复结果
echo "验证修复结果..."
remaining=$(grep -r "^[[:space:]]*repaint();" /Users/wangxiaozhe/workspace/geek_leetcode/geek-animation/src/main/java --include="*.java" | grep -v "SwingUtilities.invokeLater" | wc -l)
echo "剩余未包裹的repaint()调用数量: $remaining"

if [ "$remaining" -eq 0 ]; then
    echo "所有repaint()调用已修复！"
else
    echo "仍有repaint()调用需要处理："
    grep -r "^[[:space:]]*repaint();" /Users/wangxiaozhe/workspace/geek_leetcode/geek-animation/src/main/java --include="*.java" | grep -v "SwingUtilities.invokeLater"
fi
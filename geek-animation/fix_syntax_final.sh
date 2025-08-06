#!/bin/bash

echo "开始修复语法错误..."

# 查找所有Java文件并修复语法错误
find /Users/wangxiaozhe/workspace/geek_leetcode/geek-animation/src/main/java -name "*.java" -type f | while read file; do
    echo "处理文件: $file"
    
    # 修复 SwingUtilities.invokeLater(() -> xxx.repaint();); 的语法错误
    # 将 ;); 替换为 );
    sed -i '' 's/SwingUtilities\.invokeLater(() -> \([^;]*\.repaint()\););/SwingUtilities.invokeLater(() -> \1);/g' "$file"
    
    # 修复其他可能的语法错误
    sed -i '' 's/\.repaint(););/.repaint());/g' "$file"
done

echo "语法修复完成！"

# 验证修复结果
echo "验证修复结果..."
remaining=$(grep -r "\.repaint(););" /Users/wangxiaozhe/workspace/geek_leetcode/geek-animation/src/main/java --include="*.java" | wc -l)
echo "剩余语法错误数量: $remaining"

if [ "$remaining" -eq 0 ]; then
    echo "所有语法错误已修复！"
else
    echo "仍有语法错误需要处理"
    grep -r "\.repaint(););" /Users/wangxiaozhe/workspace/geek_leetcode/geek-animation/src/main/java --include="*.java"
fi
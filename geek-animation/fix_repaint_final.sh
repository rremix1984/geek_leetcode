#!/bin/bash

# 批量修复所有未被SwingUtilities.invokeLater包裹的repaint()调用

SRC_DIR="/Users/wangxiaozhe/workspace/geek_leetcode/geek-animation/src/main/java"

echo "开始批量修复repaint()调用..."

# 查找所有包含repaint()调用的Java文件
find "$SRC_DIR" -name "*.java" -type f | while read file; do
    echo "处理文件: $file"
    
    # 使用sed替换所有未被SwingUtilities.invokeLater包裹的repaint()调用
    # 匹配模式：任何以空格开头，后跟任意字符，然后是.repaint();的行
    sed -i '' 's/\([ \t]*\)\([a-zA-Z_][a-zA-Z0-9_]*\.repaint();\)/\1SwingUtilities.invokeLater(() -> \2/g' "$file"
    
    # 处理可能的多层嵌套情况，移除重复的SwingUtilities.invokeLater包装
    sed -i '' 's/SwingUtilities\.invokeLater(() -> SwingUtilities\.invokeLater(() -> /SwingUtilities.invokeLater(() -> /g' "$file"
done

echo "批量修复完成！"

# 验证修复结果
echo "验证修复结果..."
remaining=$(find "$SRC_DIR" -name "*.java" -exec grep -l "\.repaint();" {} \; | xargs grep -n "\.repaint();" | grep -v "SwingUtilities.invokeLater" | wc -l)
echo "剩余未修复的repaint()调用数量: $remaining"
#!/bin/bash

# 修复缺失的右括号

SRC_DIR="/Users/wangxiaozhe/workspace/geek_leetcode/geek-animation/src/main/java"

echo "开始修复缺失的右括号..."

# 查找所有包含SwingUtilities.invokeLater但缺少右括号的文件
find "$SRC_DIR" -name "*.java" -type f | while read file; do
    echo "处理文件: $file"
    
    # 修复缺少右括号的SwingUtilities.invokeLater调用
    sed -i '' 's/SwingUtilities\.invokeLater(() -> \([^;]*\.repaint();\)/SwingUtilities.invokeLater(() -> \1);/g' "$file"
done

echo "修复完成！"
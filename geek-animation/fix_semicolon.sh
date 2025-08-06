#!/bin/bash

# 修复多余的分号

SRC_DIR="/Users/wangxiaozhe/workspace/geek_leetcode/geek-animation/src/main/java"

echo "开始修复多余的分号..."

# 查找所有包含多余分号的文件
find "$SRC_DIR" -name "*.java" -type f | while read file; do
    echo "处理文件: $file"
    
    # 修复多余的分号
    sed -i '' 's/SwingUtilities\.invokeLater(() -> \([^;]*\.repaint();\););/SwingUtilities.invokeLater(() -> \1);/g' "$file"
done

echo "修复完成！"
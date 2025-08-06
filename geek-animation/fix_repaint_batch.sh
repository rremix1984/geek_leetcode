#!/bin/bash

# 批量修复repaint()调用的脚本
# 将所有未被SwingUtilities.invokeLater包裹的repaint()调用进行修复

echo "开始批量修复repaint()调用..."

# 定义源代码目录
SRC_DIR="/Users/wangxiaozhe/workspace/geek_leetcode/geek-animation/src/main/java"

# 查找所有包含未被SwingUtilities.invokeLater包裹的repaint()调用的文件
find "$SRC_DIR" -name "*.java" -exec grep -l "\.repaint();" {} \; | while read file; do
    echo "处理文件: $file"
    
    # 检查文件是否包含未被包裹的repaint()调用
    if grep -q "(?<!SwingUtilities\.invokeLater\(\(\) -> ).*\.repaint\(\);" "$file" 2>/dev/null; then
        # 使用sed进行替换，但要小心不要替换已经被包裹的调用
        # 创建临时文件
        temp_file=$(mktemp)
        
        # 逐行处理文件
        while IFS= read -r line; do
            # 如果行包含repaint()但不包含SwingUtilities.invokeLater
            if [[ "$line" =~ \.repaint\(\)\; ]] && [[ ! "$line" =~ SwingUtilities\.invokeLater ]]; then
                # 提取缩进
                indent=$(echo "$line" | sed 's/[^ \t].*//')
                # 提取repaint调用
                repaint_call=$(echo "$line" | sed 's/.*\(.*\.repaint();\).*/\1/')
                # 替换为包裹的版本
                echo "${indent}SwingUtilities.invokeLater(() -> $repaint_call);" >> "$temp_file"
            else
                echo "$line" >> "$temp_file"
            fi
        done < "$file"
        
        # 替换原文件
        mv "$temp_file" "$file"
        echo "已修复: $file"
    fi
done

echo "批量修复完成！"
#!/bin/bash

# 算法动画系统启动脚本
# 直接启动树形分类界面

echo "🚀 正在启动算法动画系统..."
echo "📁 工作目录: $(pwd)"

# 检查是否已编译
if [ ! -d "target/classes" ]; then
    echo "⚠️  未找到编译文件，正在编译项目..."
    mvn clean compile
    if [ $? -ne 0 ]; then
        echo "❌ 编译失败，请检查项目配置"
        exit 1
    fi
fi

echo "✅ 启动树形分类算法动画界面..."
java -cp target/classes com.leetcode.animation.AlgorithmAnimationLauncher

echo "👋 算法动画系统已退出"
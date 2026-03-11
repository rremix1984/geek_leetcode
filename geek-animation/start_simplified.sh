#!/bin/bash

# 简化版算法动画启动脚本
# 使用 SimplifiedLauncher 作为入口

echo "🚀 正在启动简化版算法动画系统..."
echo "📁 工作目录: $(pwd)"

# 检查是否已编译
if [ ! -d "target/classes" ]; then
    echo "⚠️  未找到编译文件，正在编译项目..."
    mvn clean compile -q
    if [ $? -ne 0 ]; then
        echo "❌ 编译失败，请检查项目配置"
        exit 1
    fi
fi

echo "✅ 启动简化版算法动画界面..."
java -cp target/classes:$(mvn dependency:build-classpath -Dmdep.outputFile=/dev/stdout -q) \
     -Xms256m -Xmx1g \
     com.animation.launcher.SimplifiedLauncher

echo "👋 算法动画系统已退出"

#!/bin/bash

# LeetCode算法动画演示系统 - Java 8兼容的CodeCache优化启动脚本
# 专门解决 "CodeCache is full. Compiler has been disabled" 问题

echo "🚀 启动LeetCode算法动画演示系统 (Java 8 CodeCache优化版)"
echo "📁 工作目录: $(pwd)"

# 检查Java版本
java_version=$(java -version 2>&1 | head -n 1)
echo "☕ Java版本: $java_version"

# Java 8兼容的CodeCache优化参数
JVM_OPTS="-XX:ReservedCodeCacheSize=4096m \
          -XX:InitialCodeCacheSize=512m \
          -XX:+UseCodeCacheFlushing \
          -Xmx8g \
          -Xms3g \
          -XX:+UseG1GC \
          -XX:MaxGCPauseMillis=200 \
          -XX:+UseStringDeduplication \
          -XX:+OptimizeStringConcat \
          -XX:+UseFastAccessorMethods \
          -XX:+AggressiveOpts \
          -Djava.awt.headless=false \
          -Dfile.encoding=UTF-8 \
          -Dsun.java2d.opengl=true \
          -Dswing.aatext=true \
          -Dawt.useSystemAAFontSettings=on"

echo "🔧 Java 8兼容的CodeCache优化参数已设置"
echo "💾 最大堆内存: 8GB"
echo "🗂️  代码缓存: 4096MB (初始512MB)"
echo "⚡ 启用代码缓存自动清理"
echo "🎨 启用图形渲染和字体优化"
echo "🚀 启用G1垃圾收集器和性能优化"

# 检查是否已编译
if [ ! -d "target/classes" ]; then
    echo "🔨 首次编译项目..."
    mvn compile -q
    
    if [ $? -ne 0 ]; then
        echo "❌ 编译失败，请检查代码"
        exit 1
    fi
    echo "✅ 编译成功"
else
    echo "✅ 使用已编译的类文件"
fi

echo "🎯 启动算法动画系统..."
echo "⏳ 正在加载JVM和初始化CodeCache..."

# 启动应用
java $JVM_OPTS -cp target/classes com.animation.launcher.AlgorithmTreeLauncher

echo "👋 算法动画系统已退出"
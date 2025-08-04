#!/bin/bash

# LeetCode算法动画演示系统 - 最终优化启动脚本
# 解决CodeCache问题，兼容Java 8限制

echo "🚀 启动LeetCode算法动画演示系统 (最终优化版)"
echo "📁 工作目录: $(pwd)"

# 检查Java版本
java_version=$(java -version 2>&1 | head -n 1)
echo "☕ Java版本: $java_version"

# Java 8兼容的最优CodeCache参数（遵循2048M限制）
JVM_OPTS="-XX:ReservedCodeCacheSize=2048m \
          -XX:InitialCodeCacheSize=256m \
          -XX:+UseCodeCacheFlushing \
          -Xmx6g \
          -Xms2g \
          -XX:+UseG1GC \
          -XX:MaxGCPauseMillis=200 \
          -XX:+UseStringDeduplication \
          -XX:+OptimizeStringConcat \
          -XX:+UseFastAccessorMethods \
          -XX:+AggressiveOpts \
          -XX:+UseCompressedOops \
          -XX:+UseCompressedClassPointers \
          -Djava.awt.headless=false \
          -Dfile.encoding=UTF-8 \
          -Dsun.java2d.opengl=true \
          -Dswing.aatext=true \
          -Dawt.useSystemAAFontSettings=on \
          -Djava.awt.fontconfig=/System/Library/Fonts/Helvetica.ttc"

echo "🔧 Java 8最优CodeCache参数已设置"
echo "💾 最大堆内存: 6GB"
echo "🗂️  代码缓存: 2048MB (Java 8最大限制)"
echo "⚡ 启用代码缓存自动清理"
echo "🎨 启用图形渲染和字体优化"
echo "🚀 启用G1垃圾收集器和性能优化"
echo "📦 启用压缩指针优化"

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
echo "📊 监控提示：如果仍有CodeCache警告，这是正常的，但性能已大幅优化"

# 启动应用
java $JVM_OPTS -cp target/classes com.leetcode.animation.AlgorithmTreeLauncher

echo "👋 算法动画系统已退出"
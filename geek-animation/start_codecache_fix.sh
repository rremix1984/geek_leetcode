#!/bin/bash

# LeetCode算法动画演示系统 - CodeCache优化启动脚本
# 专门解决 "CodeCache is full. Compiler has been disabled" 问题

echo "🚀 启动LeetCode算法动画演示系统 (CodeCache优化版)"
echo "📁 工作目录: $(pwd)"

# 检查Java版本
java_version=$(java -version 2>&1 | head -n 1)
echo "☕ Java版本: $java_version"

# 专门针对CodeCache问题的JVM参数
JVM_OPTS="-XX:ReservedCodeCacheSize=8192m \
          -XX:InitialCodeCacheSize=1024m \
          -XX:CodeCacheExpansionSize=128m \
          -XX:+UseCodeCacheFlushing \
          -XX:+SegmentedCodeCache \
          -XX:CodeCacheMinimumUseSpace=4096m \
          -XX:+PrintCodeCache \
          -XX:+UnlockDiagnosticVMOptions \
          -XX:+TraceClassLoading \
          -Xmx12g \
          -Xms4g \
          -XX:+UseG1GC \
          -XX:G1HeapRegionSize=32m \
          -XX:MaxGCPauseMillis=200 \
          -XX:+UseStringDeduplication \
          -XX:+OptimizeStringConcat \
          -XX:+UseFastAccessorMethods \
          -XX:+AggressiveOpts \
          -Djava.awt.headless=false \
          -Dfile.encoding=UTF-8 \
          -Dsun.java2d.opengl=true \
          -Dswing.aatext=true \
          -Dawt.useSystemAAFontSettings=on \
          -Djava.awt.fontconfig=/System/Library/Fonts/Helvetica.ttc"

echo "🔧 CodeCache超级优化参数已设置"
echo "💾 最大堆内存: 12GB"
echo "🗂️  代码缓存: 8192MB (初始1024MB)"
echo "⚡ 启用分段代码缓存、自动清理和扩展"
echo "🎨 启用图形渲染和字体优化"
echo "📊 启用CodeCache监控"

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
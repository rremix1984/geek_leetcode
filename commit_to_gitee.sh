#!/bin/bash

# Git提交到Gitee自动化脚本
# 作者: 算法动画系统开发团队
# 用途: 自动化Git提交流程，包括状态检查、添加、提交和推送

set -e  # 遇到错误立即退出

# 颜色定义
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# 打印带颜色的信息
print_info() {
    echo -e "${BLUE}[INFO]${NC} $1"
}

print_success() {
    echo -e "${GREEN}[SUCCESS]${NC} $1"
}

print_warning() {
    echo -e "${YELLOW}[WARNING]${NC} $1"
}

print_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

# 检查是否在Git仓库中
check_git_repo() {
    if ! git rev-parse --git-dir > /dev/null 2>&1; then
        print_error "当前目录不是Git仓库！"
        exit 1
    fi
}

# 检查Git配置
check_git_config() {
    if ! git config user.name > /dev/null 2>&1; then
        print_warning "Git用户名未配置，请运行: git config --global user.name \"Your Name\""
    fi
    
    if ! git config user.email > /dev/null 2>&1; then
        print_warning "Git邮箱未配置，请运行: git config --global user.email \"you@example.com\""
    fi
}

# 显示当前Git状态
show_git_status() {
    print_info "检查Git状态..."
    git status
    echo
}

# 获取当前分支名
get_current_branch() {
    git branch --show-current
}

# 添加所有更改
add_changes() {
    print_info "添加所有更改到暂存区..."
    git add .
    print_success "文件已添加到暂存区"
}

# 提交更改
commit_changes() {
    local commit_message="$1"
    
    if [ -z "$commit_message" ]; then
        # 如果没有提供提交信息，使用默认信息
        local timestamp=$(date '+%Y-%m-%d %H:%M:%S')
        commit_message="feat: 代码更新 - $timestamp

- 优化算法动画系统功能
- 修复已知问题
- 提升用户体验"
    fi
    
    print_info "提交更改..."
    git commit -m "$commit_message"
    print_success "代码已提交到本地仓库"
}

# 推送到远程仓库
push_to_remote() {
    local branch="$1"
    
    if [ -z "$branch" ]; then
        branch=$(get_current_branch)
    fi
    
    print_info "推送到远程仓库 (分支: $branch)..."
    
    # 检查远程分支是否存在
    if git ls-remote --heads origin "$branch" | grep -q "$branch"; then
        git push origin "$branch"
    else
        print_warning "远程分支 '$branch' 不存在，将创建新分支"
        git push -u origin "$branch"
    fi
    
    print_success "代码已成功推送到Gitee仓库！"
}

# 显示帮助信息
show_help() {
    echo "Git提交到Gitee自动化脚本"
    echo
    echo "用法:"
    echo "  $0 [选项] [提交信息]"
    echo
    echo "选项:"
    echo "  -h, --help     显示帮助信息"
    echo "  -s, --status   只显示Git状态，不执行提交"
    echo "  -b, --branch   指定推送的分支名 (默认为当前分支)"
    echo "  -m, --message  指定提交信息"
    echo
    echo "示例:"
    echo "  $0                                    # 使用默认提交信息"
    echo "  $0 -m \"修复登录bug\"                   # 指定提交信息"
    echo "  $0 -b main -m \"发布新版本\"           # 指定分支和提交信息"
    echo "  $0 -s                                 # 只查看状态"
}

# 主函数
main() {
    local commit_message=""
    local target_branch=""
    local status_only=false
    
    # 解析命令行参数
    while [[ $# -gt 0 ]]; do
        case $1 in
            -h|--help)
                show_help
                exit 0
                ;;
            -s|--status)
                status_only=true
                shift
                ;;
            -b|--branch)
                target_branch="$2"
                shift 2
                ;;
            -m|--message)
                commit_message="$2"
                shift 2
                ;;
            -*)
                print_error "未知选项: $1"
                show_help
                exit 1
                ;;
            *)
                # 如果没有使用-m选项，将剩余参数作为提交信息
                if [ -z "$commit_message" ]; then
                    commit_message="$*"
                    break
                fi
                shift
                ;;
        esac
    done
    
    print_info "=== Git提交到Gitee自动化脚本 ==="
    echo
    
    # 检查Git环境
    check_git_repo
    check_git_config
    
    # 显示当前状态
    show_git_status
    
    # 如果只是查看状态，则退出
    if [ "$status_only" = true ]; then
        print_info "状态检查完成"
        exit 0
    fi
    
    # 检查是否有更改需要提交
    if git diff --quiet && git diff --cached --quiet; then
        print_warning "没有检测到需要提交的更改"
        exit 0
    fi
    
    # 确认是否继续
    echo -n "是否继续提交并推送到Gitee? (y/N): "
    read -r confirm
    if [[ ! $confirm =~ ^[Yy]$ ]]; then
        print_info "操作已取消"
        exit 0
    fi
    
    echo
    
    # 执行Git操作
    add_changes
    commit_changes "$commit_message"
    push_to_remote "$target_branch"
    
    echo
    print_success "=== 所有操作完成！==="
    print_info "您可以在Gitee仓库中查看最新的提交记录"
}

# 执行主函数
main "$@"
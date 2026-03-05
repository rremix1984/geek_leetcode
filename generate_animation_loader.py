import os
import re

ANIMATION_SRC_DIR = '/Users/wangxiaozhe/workspace/geek_leetcode/geek-animation/src/main/java/com/animation'
LAUNCHER_FILE = '/Users/wangxiaozhe/workspace/geek_leetcode/geek-animation/src/main/java/com/animation/launcher/AlgorithmTreeLauncher.java'

def get_animation_files(root_dir):
    animation_files = []
    for root, _, files in os.walk(root_dir):
        for file in files:
            if file.endswith('_Animation.java'):
                java_src_root = '/Users/wangxiaozhe/workspace/geek_leetcode/geek-animation/src/main/java'
                package_path = os.path.relpath(root, java_src_root).replace('/', '.')
                class_name = file.replace('.java', '')
                full_class_name = f'{package_path}.{class_name}'
                
                # Extract algorithm name from file name
                name = class_name.replace('_Animation', '')
                name = name.replace('_', ' ')
                name = re.sub(r'(NO)(\d+)', r'NO.\2', name)
                name = re.sub(r' ([EMH]) ', r' ', name) # Remove difficulty marker
                name = re.sub('([a-z])([A-Z])', r'\1 \2', name) # Add space for camelCase
                algorithm_name = name.strip()

                animation_files.append((algorithm_name, full_class_name))
    return animation_files

def generate_animation_loader_code(animation_files):
    code_lines = []
    for name, class_name in sorted(animation_files):
        code_lines.append(f'        animations.put("{name}", () -> new {class_name}().setVisible(true));')
    return '\n'.join(code_lines)

def update_launcher_file(launcher_file, new_code):
    with open(launcher_file, 'r') as f:
        lines = f.readlines()

    start_marker = '// ANIMATIONS_START'
    end_marker = '// ANIMATIONS_END'

    try:
        start_index = lines.index(start_marker + '\n')
        end_index = lines.index(end_marker + '\n')
    except ValueError:
        print(f'Error: Markers not found in {launcher_file}')
        # Fallback: find initAnimations method
        try:
            start_index = -1
            end_index = -1
            in_method = False
            for i, line in enumerate(lines):
                if 'private void initAnimations()' in line:
                    in_method = True
                if in_method and '{' in line and start_index == -1:
                    start_index = i + 1
                if in_method and '}' in line and start_index != -1:
                    end_index = i
                    break
            if start_index == -1 or end_index == -1:
                 print(f'Error: Could not find initAnimations method body in {launcher_file}')
                 return
        except Exception as e:
            print(f'Error finding initAnimations method: {e}')
            return


    new_lines = lines[:start_index + 1] + [new_code + '\n'] + lines[end_index:]

    with open(launcher_file, 'w') as f:
        f.writelines(new_lines)

if __name__ == '__main__':
    animation_files = get_animation_files(ANIMATION_SRC_DIR)
    new_code = generate_animation_loader_code(animation_files)
    # print(new_code)
    # update_launcher_file(LAUNCHER_FILE, new_code)
    # As I cannot directly modify the file, I will print the code to be inserted.
    print("--- BEGIN CODE TO INSERT ---")
    print(new_code)
    print("--- END CODE TO INSERT ---")
import os
import json

def generate_manifest():
    project_dir = '/home/fhu_thjen/projects/learning-java'
    vi_dir = os.path.join(project_dir, 'vi')
    
    chapters_dict = {}
    
    # Sort subdirectories in vi/
    subdirs = sorted([d for d in os.listdir(vi_dir) if os.path.isdir(os.path.join(vi_dir, d)) and d.startswith('no')])
    
    for chapter_folder in subdirs:
        chapter_path = os.path.join(vi_dir, chapter_folder)
        lessons = []
        
        # 1. Check for README.md in the root of the chapter
        readme_path = os.path.join(chapter_path, 'README.md')
        if os.path.exists(readme_path):
            rel_md = os.path.relpath(readme_path, project_dir)
            
            audio_mp3 = os.path.relpath(os.path.join(chapter_path, 'audio', 'README.mp3'), project_dir)
            audio_json = os.path.relpath(os.path.join(chapter_path, 'audio', 'README.json'), project_dir)
            
            has_mp3 = os.path.exists(os.path.join(project_dir, audio_mp3))
            has_json = os.path.exists(os.path.join(project_dir, audio_json))
            
            lessons.append({
                'id': 'README',
                'title': 'Tổng Quan Chương (README)',
                'md_path': rel_md,
                'mp3_path': audio_mp3,
                'json_path': audio_json,
                'has_audio': has_mp3 and has_json
            })
            
        # 2. Check for theory folder inside the chapter
        theory_path = os.path.join(chapter_path, 'theory')
        if os.path.exists(theory_path):
            files = sorted([f for f in os.listdir(theory_path) if f.endswith('.md') and not f.startswith('anki') and not f.startswith('study-notes')])
            for f in files:
                full_md_path = os.path.join(theory_path, f)
                rel_md = os.path.relpath(full_md_path, project_dir)
                base_name = os.path.splitext(f)[0]
                
                audio_mp3 = os.path.relpath(os.path.join(theory_path, 'audio', base_name + '.mp3'), project_dir)
                audio_json = os.path.relpath(os.path.join(theory_path, 'audio', base_name + '.json'), project_dir)
                
                title = base_name
                with open(full_md_path, 'r', encoding='utf-8') as mdf:
                    for line in mdf:
                        line_str = line.strip()
                        if line_str.startswith('# '):
                            title = line_str.replace('# ', '').strip()
                            break
                            
                has_mp3 = os.path.exists(os.path.join(project_dir, audio_mp3))
                has_json = os.path.exists(os.path.join(project_dir, audio_json))
                
                lessons.append({
                    'id': base_name,
                    'title': title,
                    'md_path': rel_md,
                    'mp3_path': audio_mp3,
                    'json_path': audio_json,
                    'has_audio': has_mp3 and has_json
                })
                
        if lessons:
            ch_display = chapter_folder.replace('_', ' ').title()
            chapters_dict[chapter_folder] = {
                'id': chapter_folder,
                'title': ch_display,
                'lessons': lessons
            }
            
    manifest = {
        'total_lessons': sum(len(c['lessons']) for c in chapters_dict.values()),
        'chapters': list(chapters_dict.values())
    }
    
    manifest_path = os.path.join(project_dir, 'lessons.json')
    with open(manifest_path, 'w', encoding='utf-8') as out:
        json.dump(manifest, out, indent=2, ensure_ascii=False)
        
    print(f"🎉 Successfully generated lessons.json with {manifest['total_lessons']} lessons including READMEs!")

if __name__ == '__main__':
    generate_manifest()

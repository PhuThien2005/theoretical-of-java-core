import os
import json

def generate_manifest():
    project_dir = '/home/fhu_thjen/projects/learning-java'
    vi_dir = os.path.join(project_dir, 'vi')
    
    chapters_dict = {}

    for root, dirs, files in sorted(os.walk(vi_dir)):
        if 'theory' in root and not root.endswith('audio'):
            chapter_folder = os.path.basename(os.path.dirname(root))
            
            for f in sorted(files):
                if f.endswith('.md') and not f.startswith('anki') and not f.startswith('study-notes'):
                    full_md_path = os.path.join(root, f)
                    rel_md = os.path.relpath(full_md_path, project_dir)
                    base_name = os.path.splitext(f)[0]
                    
                    audio_mp3 = os.path.relpath(os.path.join(root, 'audio', base_name + '.mp3'), project_dir)
                    audio_json = os.path.relpath(os.path.join(root, 'audio', base_name + '.json'), project_dir)
                    
                    title = base_name
                    with open(full_md_path, 'r', encoding='utf-8') as mdf:
                        for line in mdf:
                            line_str = line.strip()
                            if line_str.startswith('# '):
                                title = line_str.replace('# ', '').strip()
                                break
                    
                    has_mp3 = os.path.exists(os.path.join(project_dir, audio_mp3))
                    has_json = os.path.exists(os.path.join(project_dir, audio_json))

                    if chapter_folder not in chapters_dict:
                        ch_display = chapter_folder.replace('_', ' ').title()
                        chapters_dict[chapter_folder] = {
                            'id': chapter_folder,
                            'title': ch_display,
                            'lessons': []
                        }
                    
                    chapters_dict[chapter_folder]['lessons'].append({
                        'id': base_name,
                        'title': title,
                        'md_path': rel_md,
                        'mp3_path': audio_mp3,
                        'json_path': audio_json,
                        'has_audio': has_mp3 and has_json
                    })

    manifest = {
        'total_lessons': sum(len(c['lessons']) for c in chapters_dict.values()),
        'chapters': list(chapters_dict.values())
    }

    manifest_path = os.path.join(project_dir, 'lessons.json')
    with open(manifest_path, 'w', encoding='utf-8') as out:
        json.dump(manifest, out, indent=2, ensure_ascii=False)
    
    print(f"🎉 Successfully generated lessons.json with {manifest['total_lessons']} lessons across {len(chapters_dict)} chapters!")

if __name__ == '__main__':
    generate_manifest()

#!/usr/bin/env python3
import re
from pathlib import Path

def parse_outline(outline_path):
    with open(outline_path, 'r', encoding='utf-8') as f:
        lines = f.readlines()
    
    sections = {}
    current_section = None
    
    for line in lines:
        line = line.strip()
        if not line:
            continue
        if "Suggested reasonable study order" in line:
            break
        if line == "---":
            continue
        # Check for section header: e.g. "1. Overview of Java"
        m = re.match(r'^(\d+)\.\s+(.*)$', line)
        if m:
            sec_num = int(m.group(1))
            sec_title = m.group(2)
            current_section = sec_num
            sections[current_section] = {
                'title': sec_title,
                'concepts': []
            }
            continue
        
        if current_section is not None:
            # Check if it starts with a bullet point
            if line.startswith(('-', '*')):
                concept = re.sub(r'^[-\*\s]+', '', line)
                concept = concept.replace('`', '')
                concept = concept.rstrip(':').strip()
                if concept and concept != "...":
                    sections[current_section]['concepts'].append(concept)
    return sections

def clean_text(text):
    # Lowercase and replace non-alphanumeric characters with spaces
    text = text.lower()
    text = re.sub(r'[^a-z0-9\s]', ' ', text)
    return ' '.join(text.split())

def is_concept_present(concept, folder_content):
    concept_clean = clean_text(concept)
    if not concept_clean:
        return True
    
    # Simple direct check
    if concept_clean in folder_content:
        return True
        
    # Let's do some common variations or word-based matching
    if "function" in concept_clean:
        var1 = concept_clean.replace("function", "method")
        if var1 in folder_content:
            return True
    if "method" in concept_clean:
        var1 = concept_clean.replace("method", "function")
        if var1 in folder_content:
            return True
            
    concept_raw_lower = concept.lower()
    if concept_raw_lower in folder_content:
        return True

    if len(concept) <= 3 and not concept.isalnum():
        if concept in folder_content:
            return True
            
    words = [w for w in concept_clean.split() if len(w) > 2]
    if words and all(w in folder_content for w in words):
        return True
        
    return False

def extract_headings_and_terms(folder_path, sec_title):
    headings = []
    terms = []
    
    # Generic layout headings to ignore
    ignore_headings = {
        "learning goal", "outline coverage", "detailed notes", 
        "common review prompts", "overview", "summary", 
        "introduction", "conclusion", "table of contents",
        "runtime terms", "terms", "definitions", "common confusion",
        "short definition", "why it matters", "example", "notes"
    }
    
    # Clean section title for comparison
    sec_title_clean = clean_text(sec_title)
    
    # Check theory folder
    theory_dir = folder_path / "theory"
    if theory_dir.exists():
        for p in theory_dir.glob("*.md"):
            with p.open('r', encoding='utf-8') as f:
                for line in f:
                    # Look for headings: e.g. "## Object-Oriented"
                    m = re.match(r'^(#{1,3})\s+(.*)$', line.strip())
                    if m:
                        title = m.group(2).strip()
                        # Clean title
                        title_clean = title.replace('`', '')
                        title_lower = title_clean.lower()
                        
                        # Filter out layout headings
                        if title_lower in ignore_headings:
                            continue
                        # Filter out things like "Overview of Java - Part 1"
                        if "part " in title_lower:
                            continue
                        if title_lower == sec_title_clean:
                            continue
                            
                        headings.append(title_clean)
                            
    # Check terms folder
    terms_dir = folder_path / "terms"
    if terms_dir.exists():
        for p in terms_dir.glob("*.md"):
            with p.open('r', encoding='utf-8') as f:
                for line in f:
                    # Look for "## Term: Bytecode"
                    m = re.match(r'^#{1,3}\s+Term:\s+(.*)$', line.strip(), re.IGNORECASE)
                    if m:
                        term = m.group(1).strip().replace('`', '')
                        if term.lower() not in ignore_headings:
                            terms.append(term)
                    # Or just general terms headings if they don't have "Term:" prefix but are terms
                    elif re.match(r'^##\s+[^#\s].*$', line.strip()):
                        term = line.strip().lstrip('#').strip().replace('`', '')
                        term_lower = term.lower()
                        if term_lower not in ignore_headings and "part " not in term_lower:
                            terms.append(term)
                            
    return headings, terms

def is_folder_concept_in_outline(concept, outline_concepts_clean):
    concept_clean = clean_text(concept)
    if not concept_clean:
        return True
    
    # Check if this concept is in the outline concepts
    for oc in outline_concepts_clean:
        if concept_clean in oc or oc in concept_clean:
            return True
        # If words share significant overlap
        words_c = set(w for w in concept_clean.split() if len(w) > 2)
        words_oc = set(w for w in oc.split() if len(w) > 2)
        if words_c and words_oc and len(words_c.intersection(words_oc)) >= max(1, min(len(words_c), len(words_oc))):
            return True
            
    return False

def main():
    root = Path.cwd()
    outline_path = root / "outline.md"
    sections = parse_outline(outline_path)
    
    missing_in_folders = {}
    extra_in_folders = {}
    
    dirs = [d for d in root.iterdir() if d.is_dir() and re.match(r'^\d{2}-', d.name)]
    dir_map = {}
    for d in dirs:
        sec_num = int(d.name.split('-')[0])
        dir_map[sec_num] = d
        
    for sec_num, info in sorted(sections.items()):
        sec_title = info['title']
        concepts = info['concepts']
        
        folder = dir_map.get(sec_num)
        if not folder:
            continue
            
        folder_files = []
        if (folder / "theory").exists():
            folder_files.extend((folder / "theory").glob("*.md"))
        if (folder / "terms").exists():
            folder_files.extend((folder / "terms").glob("*.md"))
        if (folder / "README.md").exists():
            folder_files.append(folder / "README.md")
        if (folder / "anki").exists():
            folder_files.extend((folder / "anki").glob("*.tsv"))
            
        concatenated_content = ""
        for p in folder_files:
            try:
                concatenated_content += "\n" + p.read_text(encoding='utf-8')
            except Exception as e:
                pass
                
        folder_content_clean = clean_text(concatenated_content)
        
        # 1. Check if outline concepts are missing from the folder
        missing_concepts = []
        for concept in concepts:
            if not is_concept_present(concept, folder_content_clean):
                missing_concepts.append(concept)
        if missing_concepts:
            missing_in_folders[sec_num] = {
                'folder': folder.name,
                'concepts': missing_concepts
            }
            
        # 2. Check if folder has extra concepts/headings not in outline
        headings, terms = extract_headings_and_terms(folder, sec_title)
        outline_concepts_clean = [clean_text(c) for c in concepts]
        
        extra_concepts = []
        for h in headings:
            if not is_folder_concept_in_outline(h, outline_concepts_clean):
                extra_concepts.append(f"Heading: {h}")
        for t in terms:
            if not is_folder_concept_in_outline(t, outline_concepts_clean):
                item = f"Term: {t}"
                if item not in extra_concepts:
                    extra_concepts.append(item)
                    
        if extra_concepts:
            extra_in_folders[sec_num] = {
                'folder': folder.name,
                'concepts': extra_concepts
            }

    # Write full report to file
    report_path = root / "reports" / "outline-alignment-audit.txt"
    report_path.parent.mkdir(parents=True, exist_ok=True)
    
    with report_path.open("w", encoding="utf-8") as f:
        f.write("=== REPORT: OUTLINE CONCEPTS MISSING IN TOPIC FOLDERS ===\n")
        if not missing_in_folders:
            f.write("None! All outline concepts are present in their topic folders.\n")
        else:
            for sec_num, data in sorted(missing_in_folders.items()):
                f.write(f"\nFolder: {data['folder']} (Section {sec_num})\n")
                for c in data['concepts']:
                    f.write(f"  - {c}\n")
                    
        f.write("\n=== REPORT: TOPIC FOLDERS HAVE EXTRA CONCEPTS NOT IN OUTLINE ===\n")
        if not extra_in_folders:
            f.write("None! No extra concepts found in folders.\n")
        else:
            for sec_num, data in sorted(extra_in_folders.items()):
                f.write(f"\nFolder: {data['folder']} (Section {sec_num})\n")
                for c in data['concepts']:
                    f.write(f"  - {c}\n")
                    
    print(f"Audit completed successfully. Detailed report written to {report_path}")

if __name__ == "__main__":
    main()

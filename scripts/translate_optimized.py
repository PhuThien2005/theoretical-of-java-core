import os
import re
import urllib.request
import urllib.parse
import json
import time
from concurrent.futures import ThreadPoolExecutor

TERM_MAPPINGS = [
    ("kiểu dữ liệu nguyên thủy", "kiểu dữ liệu nguyên thủy (Primitive Type)"),
    ("kiểu dữ liệu tham chiếu", "kiểu dữ liệu tham chiếu (Reference Type)"),
    ("bộ thu gom rác", "bộ thu gom rác (Garbage Collector)"),
    ("phương thức khởi tạo", "phương thức khởi tạo (Constructor)"),
    ("máy ảo Java", "máy ảo Java (JVM - Java Virtual Machine)"),
    ("thời gian biên dịch", "thời gian biên dịch (Compile time)"),
    ("luồng điều khiển", "luồng điều khiển (Control Flow)"),
    ("bộ biên dịch JIT", "bộ biên dịch JIT (Just-In-Time compiler)"),
    ("kiểu nguyên thủy", "kiểu dữ liệu nguyên thủy (Primitive Type)"),
    ("kiểu tham chiếu", "kiểu dữ liệu tham chiếu (Reference Type)"),
    ("thời gian chạy", "thời gian chạy (Runtime)"),
    ("lớp trừu tượng", "lớp trừu tượng (Abstract Class)"),
    ("khả năng tiếp cận", "khả năng tiếp cận (Reachability)"),
    ("khả năng truy cập", "khả năng tiếp cận (Reachability)"),
    ("biên dịch JIT", "biên dịch JIT (Just-In-Time compilation)"),
    ("phạm vi truy cập", "phạm vi truy cập (Access Modifier)"),
    ("lớp bao bọc", "lớp bao bọc (Wrapper Class)"),
    ("suy luận kiểu", "suy luận kiểu (Type Inference)"),
    ("thu gom rác", "thu gom rác (Garbage Collection)"),
    ("vùng nhớ Heap", "vùng nhớ Heap (Heap)"),
    ("vùng nhớ Stack", "vùng nhớ Stack (Stack)"),
    ("hằng tự trị", "hằng tự trị (Literal)"),
    ("mã bytecode", "bytecode (Bytecode)"),
    ("trừu tượng", "trừu tượng (Abstraction)"),
    ("phương thức", "phương thức (Method)"),
    ("đa luồng", "đa luồng (Multithreading)"),
    ("câu lệnh", "câu lệnh (Statement)"),
    ("biểu thức", "biểu thức (Expression)"),
    ("nhập khẩu", "nhập khẩu (Import)"),
    ("kế thừa", "kế thừa (Inheritance)"),
    ("đóng gói", "đóng gói (Encapsulation)"),
    ("chú thích", "chú thích (Comment)"),
    ("nạp lớp", "nạp lớp (Class Loading)"),
    ("bytecode", "bytecode (Bytecode)"),
    ("Bytecode", "bytecode (Bytecode)"),
    ("đa hình", "đa hình (Polymorphism)"),
    ("hằng số", "hằng số (Constant)"),
    ("vòng đời", "vòng đời (Lifetime)"),
    ("giao diện", "giao diện (Interface)"),
    ("đối số", "đối số (Argument)"),
    ("tham số", "tham số (Parameter)"),
    ("luồng", "luồng (Thread)"),
    ("phạm vi", "phạm vi (Scope)"),
    ("gói", "gói (Package)"),
]

def translate_text(text, sl='en', tl='vi'):
    text = text.strip()
    if not text:
        return ""
    if re.match(r'^__[A-Z0-9_]+__$', text):
        return text
        
    url = "https://translate.googleapis.com/translate_a/single?client=gtx&sl={}&tl={}&dt=t".format(sl, tl)
    data = urllib.parse.urlencode({'q': text}).encode('utf-8')
    req = urllib.request.Request(url, data=data, headers={'User-Agent': 'Mozilla/5.0'})
    
    for attempt in range(5):
        try:
            with urllib.request.urlopen(req, timeout=10) as response:
                res_data = response.read().decode('utf-8')
                parsed = json.loads(res_data)
                translated_parts = []
                if parsed and parsed[0]:
                    for item in parsed[0]:
                        if item and item[0]:
                            translated_parts.append(item[0])
                return "".join(translated_parts)
        except Exception as e:
            print(f"Error translating text (attempt {attempt+1}): {e}")
            time.sleep(2)
            
    return text

def translate_blocks_parallel(blocks, sl='en', tl='vi'):
    if not blocks:
        return []
        
    # Translate empty or code placeholders directly to save time/requests
    results = ["" for _ in blocks]
    valid_indices = []
    valid_texts = []
    
    for idx, t in enumerate(blocks):
        if t.strip() and not re.match(r'^__[A-Z0-9_]+__$', t.strip()):
            valid_indices.append(idx)
            valid_texts.append(t)
        else:
            results[idx] = t
            
    if not valid_texts:
        return results
        
    # Use ThreadPoolExecutor to translate valid texts concurrently
    with ThreadPoolExecutor(max_workers=10) as executor:
        translated_valid = list(executor.map(lambda txt: translate_text(txt, sl, tl), valid_texts))
        
    for idx, trans_txt in zip(valid_indices, translated_valid):
        results[idx] = trans_txt
        
    return results

def apply_term_mappings(text, file_state):
    for term, replacement in TERM_MAPPINGS:
        if term in file_state:
            continue
        
        pattern = r'(?<![a-zA-Z0-9_àáạảãâầấậẩẫăằắặẳẵèéẹẻẽêềếệểễìíịỉĩòóọỏõôồốộổỗơờớợởỡùúụủũưừứựửữỳýỵỷỹđĐ])(' + re.escape(term) + r')(?![a-zA-Z0-9_àáạảãâầấậẩẫăằắặẳẵèéẹẻẽêềếệểễìíịỉĩòóọỏõôồốộổỗơờớợởỡùúụủũưừứựửữỳýỵỷỹđĐ])'
        match = re.search(pattern, text, re.IGNORECASE)
        if match:
            start, end = match.span(1)
            matched_text = match.group(1)
            rep = replacement
            if matched_text[0].isupper():
                rep = rep[0].upper() + rep[1:]
            text = text[:start] + rep + text[end:]
            file_state.add(term)
            
    return text

def preprocess_paragraph(text, links_list, codes_list):
    if not text.strip():
        return text, ""
        
    prefix = ""
    list_match = re.match(r'^(\s*([-*]|\d+\.)\s+)(.*)$', text)
    if list_match:
        prefix = list_match.group(1)
        text = list_match.group(3)
    else:
        quote_match = re.match(r'^(\s*(>\s*)+)(.*)$', text)
        if quote_match:
            prefix = quote_match.group(1)
            text = quote_match.group(3)
            
    def link_repl(match):
        anchor = match.group(1)
        url = match.group(2)
        links_list.append((anchor, url))
        return f" __LINK_{len(links_list)-1}__ "
        
    text = re.sub(r'\[([^\]]+)\]\(([^)]+)\)', link_repl, text)
    
    def code_repl(match):
        codes_list.append(match.group(0))
        return f" __CODE_{len(codes_list)-1}__ "
        
    text = re.sub(r'`[^`]+`', code_repl, text)
    
    return text, prefix

def postprocess_paragraph(translated_text, prefix, links_list, codes_list, file_state):
    for i, code in enumerate(codes_list):
        translated_text = re.sub(rf'__CODE_{i}__', code, translated_text)
        translated_text = re.sub(rf'__CODE_{i}', code, translated_text)
        
    for i, (anchor, url) in enumerate(links_list):
        codes_in_link = []
        def code_in_link_repl(m):
            codes_in_link.append(m.group(0))
            return f" __CODELINK_{len(codes_in_link)-1}__ "
            
        anchor_replaced = re.sub(r'`[^`]+`', code_in_link_repl, anchor)
        translated_anchor = translate_text(anchor_replaced)
        
        for idx, code in enumerate(codes_in_link):
            translated_anchor = translated_anchor.replace(f"__CODELINK_{idx}__", code)
            translated_anchor = translated_anchor.replace(f"__CODELINK_{idx}", code)
            
        link_str = f"[{translated_anchor}]({url})"
        translated_text = re.sub(rf'__LINK_{i}__', link_str, translated_text)
        translated_text = re.sub(rf'__LINK_{i}', link_str, translated_text)
        
    translated_text = apply_term_mappings(translated_text, file_state)
    return prefix + translated_text

def translate_mermaid_line(line, file_state):
    def repl_quoted(match):
        prefix = match.group(1)
        text = match.group(2)
        suffix = match.group(3)
        translated = translate_text(text)
        translated = apply_term_mappings(translated, file_state)
        return f'{prefix}"{translated}"{suffix}'
    
    line = re.sub(r'([\[\(\{])"([^"]+)"([\]\)\}])', repl_quoted, line)
    
    def repl_unquoted(match):
        prefix = match.group(1)
        text = match.group(2)
        suffix = match.group(3)
        if text.strip().isupper() and len(text.strip()) < 8:
            return match.group(0)
        if not text.strip() or text.strip().startswith('.') or text.strip().endswith('()') or text.strip().startswith('`'):
            return match.group(0)
        translated = translate_text(text)
        translated = apply_term_mappings(translated, file_state)
        return f'{prefix}{translated}{suffix}'
        
    line = re.sub(r'([\[\(\{])([^"\]\)\}]+)([\]\)\}])', repl_unquoted, line)
    
    def repl_arrow_label(match):
        text = match.group(2)
        translated = translate_text(text)
        translated = apply_term_mappings(translated, file_state)
        return f'{match.group(1)}{translated}{match.group(3)}'
    
    line = re.sub(r'(-\.\s*)([a-zA-Z\s\(\)\/]+)(\s*\.->)', repl_arrow_label, line)
    line = re.sub(r'(-->\s*\|)([^|]+)(\s*\|)', repl_arrow_label, line)
    
    return line

def should_translate_cell(cell):
    cell = cell.strip()
    if not cell:
        return False
    if cell.startswith('|-') or cell.endswith('-|') or all(c in '-:| ' for c in cell):
        return False
    if cell.startswith('`') and cell.endswith('`') and cell.count('`') == 2:
        return False
    if re.match(r'^[0-9\s\+\-\*\/\=\<\>\!\&\|]+$', cell):
        return False
    return True

def translate_markdown_file(src_path, dest_path):
    print(f"Translating {src_path} -> {dest_path}")
    with open(src_path, 'r', encoding='utf-8') as f:
        content = f.read()
        
    lines = content.split('\n')
    
    blocks_to_translate = []
    block_indices = []
    
    in_code_block = False
    in_mermaid = False
    
    processed_lines = []
    
    for idx, line in enumerate(lines):
        if line.strip().startswith('```'):
            if in_code_block:
                in_code_block = False
                in_mermaid = False
            else:
                in_code_block = True
                if 'mermaid' in line:
                    in_mermaid = True
            processed_lines.append((idx, 'code_boundary', line))
            continue
            
        if in_code_block:
            if in_mermaid:
                processed_lines.append((idx, 'mermaid_line', line))
            else:
                processed_lines.append((idx, 'code_line', line))
            continue
            
        if line.strip().startswith('#'):
            match = re.match(r'^([#]+)\s+(.*)$', line)
            if match:
                hashes = match.group(1)
                heading_text = match.group(2)
                processed_lines.append((idx, 'heading', (hashes, heading_text)))
                blocks_to_translate.append(heading_text)
                block_indices.append(len(processed_lines) - 1)
            else:
                processed_lines.append((idx, 'raw', line))
            continue
            
        if line.strip().startswith('|'):
            cells = line.split('|')
            processed_cells = []
            for c_idx, cell in enumerate(cells):
                if (c_idx == 0 or c_idx == len(cells) - 1) and not cell.strip():
                    processed_cells.append((c_idx, 'empty', cell))
                    continue
                if should_translate_cell(cell):
                    leading_space = len(cell) - len(cell.lstrip())
                    trailing_space = len(cell) - len(cell.rstrip())
                    val = cell.strip()
                    
                    processed_cells.append((c_idx, 'table_cell', (val, leading_space, trailing_space)))
                    blocks_to_translate.append(val)
                    block_indices.append((len(processed_lines), c_idx))
                else:
                    processed_cells.append((c_idx, 'raw_cell', cell))
            processed_lines.append((idx, 'table_row', processed_cells))
            continue
            
        if line.strip():
            links_list = []
            codes_list = []
            clean_text, prefix = preprocess_paragraph(line, links_list, codes_list)
            
            processed_lines.append((idx, 'paragraph', (clean_text, prefix, links_list, codes_list)))
            blocks_to_translate.append(clean_text)
            block_indices.append(len(processed_lines) - 1)
        else:
            processed_lines.append((idx, 'empty_line', line))
            
    # Parallel translation of all collected text blocks
    print(f"  Translating {len(blocks_to_translate)} blocks in parallel...")
    translated_blocks = translate_blocks_parallel(blocks_to_translate)
        
    file_state = set()
    output_lines = []
    
    for line_info in processed_lines:
        line_idx, line_type, data = line_info
        
        if line_type in ['code_boundary', 'code_line', 'raw', 'empty_line']:
            output_lines.append(data)
            
        elif line_type == 'mermaid_line':
            output_lines.append(translate_mermaid_line(data, file_state))
            
        elif line_type == 'heading':
            hashes, heading_text = data
            block_idx = block_indices.index(line_idx)
            translated_heading = translated_blocks[block_idx]
            translated_heading = apply_term_mappings(translated_heading, file_state)
            
            if translated_heading.strip().lower() == heading_text.strip().lower():
                output_lines.append(f"{hashes} {heading_text}")
            else:
                output_lines.append(f"{hashes} {translated_heading} ({heading_text})")
                
        elif line_type == 'paragraph':
            clean_text, prefix, links_list, codes_list = data
            block_idx = block_indices.index(line_idx)
            translated_text = translated_blocks[block_idx]
            
            final_line = postprocess_paragraph(translated_text, prefix, links_list, codes_list, file_state)
            output_lines.append(final_line)
            
        elif line_type == 'table_row':
            reconstructed_cells = []
            for cell_info in data:
                c_idx, cell_type, cell_data = cell_info
                if cell_type in ['empty', 'raw_cell']:
                    reconstructed_cells.append(cell_data)
                elif cell_type == 'table_cell':
                    val, leading_space, trailing_space = cell_data
                    
                    block_idx = block_indices.index((line_idx, c_idx))
                    translated_val = translated_blocks[block_idx]
                    
                    links_list = []
                    codes_list = []
                    clean_val, prefix = preprocess_paragraph(translated_val, links_list, codes_list)
                    final_val = postprocess_paragraph(clean_val, prefix, links_list, codes_list, file_state)
                    
                    reconstructed_cells.append(" " * leading_space + final_val + " " * trailing_space)
            output_lines.append('|'.join(reconstructed_cells))
            
    os.makedirs(os.path.dirname(dest_path), exist_ok=True)
    with open(dest_path, 'w', encoding='utf-8') as f:
        f.write('\n'.join(output_lines))
    print(f"  Finished {dest_path}")

def main():
    src_dir = "/home/fhu_thjen/projects/learning-java"
    vi_dir = os.path.join(src_dir, "vi")

    missing_files = []

    for root, dirs, files in os.walk(src_dir):
        parts = os.path.relpath(root, src_dir).split(os.sep)
        if parts[0] in ['vi', '.git', '.vscode', '.agents', '0.tmp', 'anki-guides', 'extras', 'references', 'reports', 'scratch', 'scripts', 'skills']:
            continue
        
        if not (parts[0][:2].isdigit() or parts[0] == '99-cheatsheets'):
            continue

        for file in files:
            if file.endswith('.md'):
                rel_path = os.path.relpath(os.path.join(root, file), src_dir)
                vi_path = os.path.join(vi_dir, rel_path)
                if not os.path.exists(vi_path):
                    missing_files.append(rel_path)

    missing_files.sort()
    print(f"Found {len(missing_files)} missing files to translate.")

    for idx, rel_path in enumerate(missing_files):
        src = os.path.join(src_dir, rel_path)
        dest = os.path.join(vi_dir, rel_path)
        print(f"[{idx+1}/{len(missing_files)}] ", end="")
        try:
            start_time = time.time()
            translate_markdown_file(src, dest)
            print(f"  Time taken: {time.time() - start_time:.2f}s")
        except Exception as e:
            print(f"Failed to translate {rel_path}: {e}")
        time.sleep(0.5)

if __name__ == "__main__":
    main()

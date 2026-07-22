import os
import re
import urllib.request
import urllib.parse
import json
import time

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
    # Check if text is just placeholders or code-like
    if re.match(r'^__[A-Z0-9_]+__$', text):
        return text
        
    url = "https://translate.googleapis.com/translate_a/single?client=gtx&sl={}&tl={}&dt=t".format(sl, tl)
    data = urllib.parse.urlencode({'q': text}).encode('utf-8')
    req = urllib.request.Request(url, data=data, headers={'User-Agent': 'Mozilla/5.0'})
    
    # Retry logic
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

def apply_term_mappings(text, file_state):
    for term, replacement in TERM_MAPPINGS:
        if term in file_state:
            continue
        
        # Word boundary pattern for Vietnamese characters
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

def translate_paragraph(text, file_state):
    if not text.strip():
        return text
        
    # Preserve lists markers or quotes markers
    prefix = ""
    # Match standard list items: '- ', '* ', '1. ', '2. ', etc.
    list_match = re.match(r'^(\s*([-*]|\d+\.)\s+)(.*)$', text)
    if list_match:
        prefix = list_match.group(1)
        text = list_match.group(3)
    else:
        # Match quotes: '> ' or '> > '
        quote_match = re.match(r'^(\s*(>\s*)+)(.*)$', text)
        if quote_match:
            prefix = quote_match.group(1)
            text = quote_match.group(3)
            
    # 1. Extract links [Anchor](URL)
    links = []
    def link_repl(match):
        anchor = match.group(1)
        url = match.group(2)
        
        # Translate anchor text while preserving code inside anchor
        codes_in_link = []
        def code_in_link_repl(m):
            codes_in_link.append(m.group(0))
            return f" __CODELINK_{len(codes_in_link)-1}__ "
            
        anchor_replaced = re.sub(r'`[^`]+`', code_in_link_repl, anchor)
        translated_anchor = translate_text(anchor_replaced)
        
        # Restore codes in anchor
        for i, code in enumerate(codes_in_link):
            translated_anchor = translated_anchor.replace(f"__CODELINK_{i}__", code)
            translated_anchor = translated_anchor.replace(f"__CODELINK_{i}", code)
            
        links.append(f"[{translated_anchor}]({url})")
        return f" __LINK_{len(links)-1}__ "
        
    text_with_links = re.sub(r'\[([^\]]+)\]\(([^)]+)\)', link_repl, text)
    
    # 2. Extract inline code
    codes = []
    def code_repl(match):
        codes.append(match.group(0))
        return f" __CODE_{len(codes)-1}__ "
        
    text_with_codes = re.sub(r'`[^`]+`', code_repl, text_with_links)
    
    # 3. Translate remaining text
    translated = translate_text(text_with_codes)
    
    # 4. Restore code blocks
    for i, code in enumerate(codes):
        translated = re.sub(rf'__CODE_{i}__', code, translated)
        translated = re.sub(rf'__CODE_{i}', code, translated)
        
    # 5. Restore links
    for i, link in enumerate(links):
        translated = re.sub(rf'__LINK_{i}__', link, translated)
        translated = re.sub(rf'__LINK_{i}', link, translated)
        
    # 6. Apply key terms mapping
    translated = apply_term_mappings(translated, file_state)
    
    return prefix + translated

def translate_mermaid_line(line, file_state):
    # Match text in double quotes inside brackets: ["Text"] or ("Text") or {"Text"}
    def repl_quoted(match):
        prefix = match.group(1) # [ or ( or {
        text = match.group(2)
        suffix = match.group(3) # ] or ) or }
        translated = translate_text(text)
        translated = apply_term_mappings(translated, file_state)
        return f'{prefix}"{translated}"{suffix}'
    
    line = re.sub(r'([\[\(\{])"([^"]+)"([\]\)\}])', repl_quoted, line)
    
    # Match text in brackets without quotes: [Text] or (Text) or {Text}
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
    
    # Match arrow labels like -. Text .-> or -->|Text|
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
    translated_lines = []
    
    in_code_block = False
    in_mermaid = False
    file_state = set()
    
    i = 0
    while i < len(lines):
        line = lines[i]
        
        # Code block boundaries
        if line.strip().startswith('```'):
            if in_code_block:
                in_code_block = False
                in_mermaid = False
                translated_lines.append(line)
            else:
                in_code_block = True
                if 'mermaid' in line:
                    in_mermaid = True
                translated_lines.append(line)
            i += 1
            continue
            
        if in_code_block:
            if in_mermaid:
                # Translate Mermaid diagram label texts
                translated_lines.append(translate_mermaid_line(line, file_state))
            else:
                # Do not translate standard code blocks
                translated_lines.append(line)
            i += 1
            continue
            
        # Headings
        if line.strip().startswith('#'):
            match = re.match(r'^([#]+)\s+(.*)$', line)
            if match:
                hashes = match.group(1)
                heading_text = match.group(2)
                # Translate heading text
                translated_heading = translate_text(heading_text)
                translated_heading = apply_term_mappings(translated_heading, file_state)
                # Format: hashes translated (original)
                if translated_heading.strip().lower() == heading_text.strip().lower():
                    translated_lines.append(f"{hashes} {heading_text}")
                else:
                    translated_lines.append(f"{hashes} {translated_heading} ({heading_text})")
            else:
                translated_lines.append(line)
            i += 1
            continue
            
        # Tables
        if line.strip().startswith('|'):
            # Parse row
            cells = line.split('|')
            translated_cells = []
            for idx, cell in enumerate(cells):
                # First and last element are empty if line starts/ends with |
                if (idx == 0 or idx == len(cells) - 1) and not cell.strip():
                    translated_cells.append(cell)
                    continue
                    
                if should_translate_cell(cell):
                    # Keep spacing
                    leading_space = len(cell) - len(cell.lstrip())
                    trailing_space = len(cell) - len(cell.rstrip())
                    val = cell.strip()
                    trans_val = translate_paragraph(val, file_state)
                    translated_cells.append(" " * leading_space + trans_val + " " * trailing_space)
                else:
                    translated_cells.append(cell)
            translated_lines.append('|'.join(translated_cells))
            i += 1
            continue
            
        # Paragraphs / regular lines
        translated_lines.append(translate_paragraph(line, file_state))
        i += 1
        
    # Write to destination file
    os.makedirs(os.path.dirname(dest_path), exist_ok=True)
    with open(dest_path, 'w', encoding='utf-8') as f:
        f.write('\n'.join(translated_lines))
    print(f"Finished {dest_path}")

# List of files to translate
files_to_translate = [
    "no01_overview/terms/01-runtime-terms.md",
    "no01_overview/theory/01-what-is-java.md",
    "no01_overview/theory/02-jvm-jre-jdk.md",
    "no01_overview/theory/03-compile-runtime-flow.md",
    "no01_overview/theory/04-editions-and-versions.md",
    "no02_basic_syntax/theory/01-program-anatomy.md",
    "no02_basic_syntax/theory/02-main-method.md",
    "no02_basic_syntax/theory/03-comments-packages-imports.md",
    "no02_basic_syntax/theory/04-naming-keywords-blocks-scope.md",
    "no03_data_types/theory/01-primitive-types.md",
    "no03_data_types/theory/02-reference-types.md",
    "no03_data_types/theory/03-literals-casting-numeric-behavior.md",
    "no03_data_types/theory/04-wrappers-null-equality.md",
    "no04_variables_constants/terms/01-variable-terms.md",
    "no04_variables_constants/theory/01-variable-categories.md",
    "no04_variables_constants/theory/02-final-and-constants.md",
    "no04_variables_constants/theory/03-default-scope-lifetime.md",
    "no04_variables_constants/theory/04-var-type-inference.md",
    "no06_control_flow/README.md",
    "no06_control_flow/terms/01-control-flow-terms.md",
    "no06_control_flow/theory/01-if-else-switch.md",
    "no06_control_flow/theory/02-loops.md",
    "no06_control_flow/theory/03-break-continue-return.md",
    "no06_control_flow/theory/04-switch-expression-labeled-control.md",
    "no07_arrays/README.md",
    "no07_arrays/terms/01-array-terms.md",
    "no07_arrays/theory/01-array-basics.md",
    "no07_arrays/theory/02-array-operations.md",
    "no08_string/README.md",
    "no08_string/terms/01-string-terms.md",
    "no08_string/theory/01-string-basics.md",
    "no08_string/theory/02-string-methods.md",
    "no08_string/theory/03-stringbuilder-stringbuffer.md",
    "no09_oop/theory/01-classes-objects.md",
    "no09_oop/theory/02-encapsulation.md",
    "no09_oop/theory/03-inheritance.md",
    "no09_oop/theory/04-polymorphism.md",
    "no09_oop/theory/05-abstraction.md",
    "no10_modifiers/terms/01-key-terms.md",
    "no10_modifiers/theory/01-access-modifier-concepts.md",
]

for rel_path in files_to_translate:
    src = rel_path
    dest = os.path.join("vi", rel_path)
    translate_markdown_file(src, dest)
    # Be polite to APIs
    time.sleep(1)

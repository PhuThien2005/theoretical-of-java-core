#!/usr/bin/env python3
import os
import re
import urllib.request
from html.parser import HTMLParser
from pathlib import Path

# List of URLs to download
BASE_URL = "https://docs.oracle.com/javase/tutorial/java/nutsandbolts/"
PAGES = [
    "index.html",
    "variables.html",
    "datatypes.html",
    "arrays.html",
    "operators.html",
    "expressions.html",
    "flow.html",
    "QandE/questions_and_exercises.html"
]

class TutorialHTMLParser(HTMLParser):
    def __init__(self):
        super().__init__()
        self.recording = False
        self.text_content = []
        self.ignore_tags = {"script", "style", "nav", "footer", "header"}
        self.current_tag = ""

    def handle_starttag(self, tag, attrs):
        self.current_tag = tag
        attrs_dict = dict(attrs)
        # In Oracle Java Tutorials, the main text is usually inside a container like id="PageContent" or similar
        # If we find that, we record. Otherwise we record everything outside ignore_tags.
        if tag not in self.ignore_tags:
            self.recording = True
        else:
            self.recording = False

        if tag == "h1":
            self.text_content.append("\n\n# ")
        elif tag == "h2":
            self.text_content.append("\n\n## ")
        elif tag == "h3":
            self.text_content.append("\n\n### ")
        elif tag in ("p", "div", "li"):
            self.text_content.append("\n")
        elif tag == "pre" or tag == "code":
            self.text_content.append(" `")

    def handle_endtag(self, tag):
        if tag == "pre" or tag == "code":
            self.text_content.append("` ")
        if tag in self.ignore_tags:
            self.recording = True

    def handle_data(self, data):
        if self.recording:
            text = data.strip()
            if text:
                # Add spacing if needed
                self.text_content.append(data)

    def get_text(self):
        raw_text = "".join(self.text_content)
        # Normalize multiple newlines and spaces
        cleaned = re.sub(r'\n\s*\n+', '\n\n', raw_text)
        return cleaned.strip()

def download_and_clean_page(page: str, output_dir: Path):
    url = BASE_URL + page if not page.startswith("QandE/") else "https://docs.oracle.com/javase/tutorial/java/nutsandbolts/" + page
    print(f"Downloading {url}...")
    headers = {"User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64)"}
    req = urllib.request.Request(url, headers=headers)
    
    try:
        with urllib.request.urlopen(req, timeout=15) as response:
            html_content = response.read().decode('utf-8', errors='ignore')
            
            # Parse HTML and convert to clean markdown-like text
            parser = TutorialHTMLParser()
            parser.feed(html_content)
            clean_text = parser.get_text()
            
            # Save to output file
            filename = page.replace("/", "_").replace(".html", ".txt")
            out_file = output_dir / filename
            out_file.write_text(clean_text, encoding='utf-8')
            print(f"Saved clean reference text to {out_file.relative_to(Path.cwd())}")
            
    except Exception as e:
        print(f"Error downloading {page}: {e}")

def main():
    root = Path.cwd()
    output_dir = root / "references" / "java-tutorials" / "nutsandbolts"
    output_dir.mkdir(parents=True, exist_ok=True)
    
    for page in PAGES:
        download_and_clean_page(page, output_dir)
        
    print("\nAll downloads and extractions complete.")

if __name__ == "__main__":
    main()

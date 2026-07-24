const fs = require('fs');
const { Marked } = require('marked');
const { JSDOM } = require('jsdom');

const marked = new Marked();

function extract(mdPath) {
  if (!fs.existsSync(mdPath)) {
    console.log(JSON.stringify([]));
    return;
  }
  const mdText = fs.readFileSync(mdPath, 'utf8');
  const html = marked.parse(mdText);
  const dom = new JSDOM(html);
  const doc = dom.window.document;
  
  const elements = doc.querySelectorAll('p, li, h1, h2, h3, h4, pre');
  const blocks = [];
  
  elements.forEach((el) => {
    let text = el.textContent.trim().replace(/\s+/g, ' ');
    if (el.tagName === 'PRE') {
      text = 'Đoạn mã mẫu Java:';
    }
    if (text) {
      blocks.append ? blocks.append(text) : blocks.push(text);
    }
  });
  
  console.log(JSON.stringify(blocks, null, 2));
}

const args = process.argv.slice(2);
if (args.length > 0) {
  extract(args[0]);
}

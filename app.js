/* ==========================================================================
   JAVA CORE AUDIO STUDIO - CORE APPLICATION LOGIC
   ========================================================================== */

document.addEventListener('DOMContentLoaded', () => {
  // DOM ELEMENTS
  const sidebar = document.getElementById('sidebar');
  const sidebarToggleBtn = document.getElementById('sidebar-toggle-btn');
  const sidebarContent = document.getElementById('sidebar-content');
  const themeToggleBtn = document.getElementById('theme-toggle-btn');
  const searchInput = document.getElementById('search-input');
  
  const breadcrumb = document.getElementById('breadcrumb');
  const lessonTitle = document.getElementById('lesson-title');
  const markdownContainer = document.getElementById('markdown-container');
  const contentViewer = document.getElementById('content-viewer');
  
  const playerLessonTitle = document.getElementById('player-lesson-title');
  const playerSnippet = document.getElementById('player-snippet');
  const playBtn = document.getElementById('play-btn');
  const prevSegBtn = document.getElementById('prev-seg-btn');
  const nextSegBtn = document.getElementById('next-seg-btn');
  const rewindBtn = document.getElementById('rewind-btn');
  const forwardBtn = document.getElementById('forward-btn');
  const speedSelect = document.getElementById('speed-select');
  
  const currentTimeEl = document.getElementById('current-time');
  const totalTimeEl = document.getElementById('total-time');
  const progressBar = document.getElementById('progress-bar');
  const progressBarWrapper = document.getElementById('progress-bar-wrapper');
  
  const muteBtn = document.getElementById('mute-btn');
  const volumeSlider = document.getElementById('volume-slider');
  const audioEngine = document.getElementById('audio-engine');
  const startLearningBtn = document.getElementById('start-learning-btn');

  // APP STATE
  let manifestData = null;
  let currentLesson = null;
  let currentTimestamps = [];
  let paragraphElements = [];
  let activeParagraphIdx = -1;
  let isPlaying = false;

  // CONFIGURE MARKED.JS
  if (window.marked) {
    marked.setOptions({
      highlight: function(code, lang) {
        if (window.hljs && hljs.getLanguage(lang)) {
          return hljs.highlight(code, { language: lang }).value;
        }
        return code;
      },
      breaks: true
    });
  }

  // 1. INITIALIZE & FETCH MANIFEST
  async function initApp() {
    setupEventListeners();
    
    try {
      const res = await fetch('lessons.json');
      if (!res.ok) throw new Error('Không thể tải file lessons.json');
      manifestData = await res.json();
      
      document.getElementById('lesson-count').textContent = `${manifestData.total_lessons} bài`;
      renderSidebar(manifestData.chapters);

      // Auto-load first lesson by default
      if (manifestData.chapters.length > 0 && manifestData.chapters[0].lessons.length > 0) {
        const firstLesson = manifestData.chapters[0].lessons[0];
        selectLesson(firstLesson, manifestData.chapters[0].title);
      }
    } catch (err) {
      console.error('Error loading manifest:', err);
      sidebarContent.innerHTML = `<div class="error-msg" style="padding:16px; color:#ef4444;"><i class="fa-solid fa-triangle-exclamation"></i> Lỗi nạp danh sách bài học</div>`;
    }
  }

  // 2. RENDER SIDEBAR CHAPTERS & LESSONS
  function renderSidebar(chapters) {
    sidebarContent.innerHTML = '';
    
    chapters.forEach((chapter, index) => {
      const groupEl = document.createElement('div');
      groupEl.className = `chapter-group ${index === 0 ? 'open' : ''}`;
      
      const headerEl = document.createElement('div');
      headerEl.className = 'chapter-header';
      headerEl.innerHTML = `
        <span><i class="fa-regular fa-folder-open"></i> ${chapter.title}</span>
        <i class="fa-solid fa-chevron-down arrow-icon"></i>
      `;
      headerEl.onclick = () => groupEl.classList.toggle('open');
      
      const lessonsContainer = document.createElement('div');
      lessonsContainer.className = 'chapter-lessons';
      
      chapter.lessons.forEach(lesson => {
        const itemEl = document.createElement('a');
        itemEl.className = 'lesson-item';
        itemEl.dataset.lessonId = lesson.id;
        itemEl.innerHTML = `
          <i class="fa-solid fa-circle-play" style="font-size:0.75rem; color:${lesson.has_audio ? '#14b8a6' : '#64748b'};"></i>
          <span>${lesson.title}</span>
        `;
        itemEl.onclick = (e) => {
          e.preventDefault();
          selectLesson(lesson, chapter.title);
        };
        lessonsContainer.appendChild(itemEl);
      });
      
      groupEl.appendChild(headerEl);
      groupEl.appendChild(lessonsContainer);
      sidebarContent.appendChild(groupEl);
    });
  }

  // 3. SELECT & LOAD LESSON
  async function selectLesson(lesson, chapterTitle) {
    currentLesson = lesson;
    activeParagraphIdx = -1;
    if (isPlaying) pauseAudio();
    
    // Highlight sidebar active item
    document.querySelectorAll('.lesson-item').forEach(el => el.classList.remove('active'));
    const activeItem = document.querySelector(`.lesson-item[data-lesson-id="${lesson.id}"]`);
    if (activeItem) activeItem.classList.add('active');
    
    // Update headers
    breadcrumb.textContent = `Chương: ${chapterTitle} / ${lesson.id}`;
    lessonTitle.textContent = lesson.title;
    playerLessonTitle.textContent = lesson.title;
    playerSnippet.textContent = 'Nhấn Play để bắt đầu nghe đọc...';
    
    // Load Markdown content
    try {
      markdownContainer.innerHTML = '<div class="loading-spinner"><i class="fa-solid fa-spinner fa-spin"></i> Đang nạp bài học...</div>';
      const mdRes = await fetch(lesson.md_path);
      const mdText = await mdRes.text();
      
      const htmlContent = marked.parse(mdText);
      markdownContainer.innerHTML = htmlContent;
      
      // Apply syntax highlighting
      if (window.hljs) {
        markdownContainer.querySelectorAll('pre code').forEach((el) => hljs.highlightElement(el));
      }
      
      // Map paragraphs
      mapParagraphElements();
    } catch (err) {
      markdownContainer.innerHTML = `<div class="error-msg">Không thể đọc file bài học: ${err.message}</div>`;
    }

    // Load Audio & Timestamps
    if (lesson.has_audio) {
      try {
        const jsonRes = await fetch(lesson.json_path);
        const json = await jsonRes.json();
        currentTimestamps = json.timestamps || [];
        
        audioEngine.src = lesson.mp3_path;
        audioEngine.load();
        totalTimeEl.textContent = formatTime(json.total_duration_seconds || 0);
      } catch (e) {
        console.warn('Could not load timestamps JSON:', e);
        currentTimestamps = [];
      }
    }
  }

  // MAP PARAGRAPH ELEMENTS FOR CLICK-TO-SEEK & SYNC
  function mapParagraphElements() {
    paragraphElements = [];
    const elements = markdownContainer.querySelectorAll('p, li, h1, h2, h3, h4');
    
    elements.forEach((el, index) => {
      const segId = index + 1; // 1-indexed matching JSON
      el.dataset.paragraphId = segId;
      paragraphElements.push(el);
      
      // Click to seek
      el.addEventListener('click', () => {
        if (!currentTimestamps.length) return;
        const ts = currentTimestamps.find(t => t.paragraph_id === segId);
        if (ts) {
          audioEngine.currentTime = ts.start;
          if (!isPlaying) playAudio();
        }
      });
    });
  }

  // 4. REAL-TIME AUDIO TIME UPDATE & AUTO-SCROLL SYNC
  audioEngine.addEventListener('timeupdate', () => {
    if (!currentTimestamps.length || audioEngine.paused) return;
    
    const currentTime = audioEngine.currentTime;
    const duration = audioEngine.duration || 1;
    
    // Update progress bar
    const pct = (currentTime / duration) * 100;
    progressBar.style.width = `${pct}%`;
    currentTimeEl.textContent = formatTime(currentTime);
    
    // Find active timestamp
    const activeTs = currentTimestamps.find(t => currentTime >= t.start && currentTime < t.end);
    if (activeTs) {
      const segIdx = activeTs.paragraph_id - 1;
      if (segIdx !== activeParagraphIdx && segIdx >= 0 && segIdx < paragraphElements.length) {
        activeParagraphIdx = segIdx;
        highlightAndScrollTo(segIdx, activeTs);
      }
    }
  });

  function highlightAndScrollTo(idx, tsObj) {
    paragraphElements.forEach(el => el.classList.remove('active-sync'));
    const targetEl = paragraphElements[idx];
    if (targetEl) {
      targetEl.classList.add('active-sync');
      
      // Smooth auto-scroll to center
      targetEl.scrollIntoView({ behavior: 'smooth', block: 'center' });
      
      const segNum = idx + 1;
      const totalSegs = paragraphElements.length;
      playerSnippet.textContent = `[Đoạn ${segNum}/${totalSegs}] ${tsObj.text_snippet || targetEl.textContent.slice(0, 70)}`;
    }
  }

  // 5. AUDIO PLAYBACK CONTROLS
  function togglePlay() {
    if (!audioEngine.src) return;
    if (isPlaying) pauseAudio();
    else playAudio();
  }

  function playAudio() {
    audioEngine.play().then(() => {
      isPlaying = true;
      playBtn.innerHTML = '<i class="fa-solid fa-pause"></i>';
    }).catch(e => console.error('Audio play error:', e));
  }

  function pauseAudio() {
    audioEngine.pause();
    isPlaying = false;
    playBtn.innerHTML = '<i class="fa-solid fa-play"></i>';
  }

  // EVENT LISTENERS
  function setupEventListeners() {
    playBtn.onclick = togglePlay;
    
    sidebarToggleBtn.onclick = () => sidebar.classList.toggle('collapsed');

    rewindBtn.onclick = () => { audioEngine.currentTime = Math.max(0, audioEngine.currentTime - 5); };
    forwardBtn.onclick = () => { audioEngine.currentTime = Math.min(audioEngine.duration, audioEngine.currentTime + 5); };
    
    prevSegBtn.onclick = () => {
      if (activeParagraphIdx > 0) {
        const prevTs = currentTimestamps[activeParagraphIdx - 1];
        if (prevTs) audioEngine.currentTime = prevTs.start;
      }
    };

    nextSegBtn.onclick = () => {
      if (activeParagraphIdx < currentTimestamps.length - 1) {
        const nextTs = currentTimestamps[activeParagraphIdx + 1];
        if (nextTs) audioEngine.currentTime = nextTs.start;
      }
    };

    speedSelect.onchange = () => {
      audioEngine.playbackRate = parseFloat(speedSelect.value);
    };

    volumeSlider.oninput = () => {
      audioEngine.volume = parseFloat(volumeSlider.value);
      muteBtn.innerHTML = audioEngine.volume === 0 ? '<i class="fa-solid fa-volume-xmark"></i>' : '<i class="fa-solid fa-volume-high"></i>';
    };

    muteBtn.onclick = () => {
      audioEngine.muted = !audioEngine.muted;
      muteBtn.innerHTML = audioEngine.muted ? '<i class="fa-solid fa-volume-xmark"></i>' : '<i class="fa-solid fa-volume-high"></i>';
    };

    progressBarWrapper.onclick = (e) => {
      if (!audioEngine.duration) return;
      const rect = progressBarWrapper.getBoundingClientRect();
      const clickX = e.clientX - rect.left;
      const pct = clickX / rect.width;
      audioEngine.currentTime = pct * audioEngine.duration;
    };

    // SEARCH FILTER
    searchInput.oninput = () => {
      const q = searchInput.value.toLowerCase().trim();
      document.querySelectorAll('.chapter-group').forEach(group => {
        let hasMatch = false;
        group.querySelectorAll('.lesson-item').forEach(item => {
          const text = item.textContent.toLowerCase();
          if (text.includes(q)) {
            item.style.display = 'flex';
            hasMatch = true;
          } else {
            item.style.display = 'none';
          }
        });
        group.style.display = hasMatch || q === '' ? 'block' : 'none';
        if (q !== '' && hasMatch) group.classList.add('open');
      });
    };

    // KEYBOARD SHORTCUTS
    window.addEventListener('keydown', (e) => {
      if (e.target.tagName === 'INPUT' || e.target.tagName === 'TEXTAREA') return;
      if (e.code === 'Space') {
        e.preventDefault();
        togglePlay();
      } else if (e.code === 'ArrowLeft') {
        e.preventDefault();
        audioEngine.currentTime = Math.max(0, audioEngine.currentTime - 5);
      } else if (e.code === 'ArrowRight') {
        e.preventDefault();
        audioEngine.currentTime = Math.min(audioEngine.duration, audioEngine.currentTime + 5);
      }
    });
  }

  // UTILITY: FORMAT SECONDS TO MM:SS
  function formatTime(secs) {
    if (isNaN(secs)) return '00:00';
    const m = Math.floor(secs / 60);
    const s = Math.floor(secs % 60);
    return `${m.toString().padStart(2, '0')}:${s.toString().padStart(2, '0')}`;
  }

  // START APP
  initApp();
});

# CAT Master SA — CAPS Curriculum & Exam Preparation Platform

CAT Master SA is a specialized learning companion designed for South African Computer Applications Technology (CAT) learners across Grades 10, 11, and 12. It covers the full Department of Basic Education (DBE) CAPS curriculum across Terms 1 through 4, combining offline-first structured modules, interactive assessments, video walkthroughs, downloadable PDF guides, gamified quizzes, a collaborative peer forum, and comprehensive learner analytics.

---

## User Review & Critical Decisions

> [!IMPORTANT]
> The following key decisions have been locked in based on your preferences:
> - **Brand Identity**: Confirmed as **CAT Master SA**, featuring a modern educational aesthetic with South African scholastic green and tech-amber accents.
> - **Access & Auth Architecture**: Google Sign-In is required to activate cloud sync and participate in community discussions, while cached curriculum content, offline assessments, and downloaded PDF summaries remain lightning-fast without an internet connection.
> - **Curriculum Dashboard Layout**: Equal multi-grade selector on the primary home screen allowing instant switching between Grade 10, Grade 11, and Grade 12 CAPS modules and Term 1–4 roadmaps.

---

## 1. Overview & Core Concept

### What It Does
CAT Master SA empowers high school learners in South Africa to master both CAT Theory (Systems Technologies, Network Technologies, Information Management, Social Implications) and Practical skills (Word Processing, Spreadsheets, Database Management, and HTML/Web Design). Learners can study term-by-term notes offline, watch practical software tutorials, take timed gamified quizzes with instant memorandum explanations, download offline PDF study guides, ask questions in a peer discussion forum, and monitor their mastery on a personalized analytics dashboard.

### Target Audience & Persona
- **South African High School Students**: Grade 10 (foundational theory & basic office tools), Grade 11 (intermediate data management & HTML), and Grade 12 (Matric NSC preparation, complex functions, database queries, and past paper practice).
- **Educators & Study Groups**: Teachers and peer study groups seeking structured revision tasks and standardized assessment practice aligned to CAPS.

### Key Value
High data costs and load-shedding disruptions in South Africa necessitate an **offline-first engine**. Once downloaded or cached, learners can review notes, practice interactive exam questions, and generate study guides without consuming mobile data.

---

## 2. User Experience & Visual Design

### Key User Flows

1. **Onboarding & Grade Selection Flow**:
   - Welcome banner introducing the CAPS curriculum breakdown.
   - Interactive Grade Selector chips (Grade 10, Grade 11, Grade 12) with immediate term switching (Term 1, Term 2, Term 3, Term 4).
   - Instant overview of current term progress, upcoming exam dates, and quick-continue card.

2. **Curriculum & Practical Learning Flow**:
   - Categorized modules: **Systems Technologies**, **Network Technologies**, **Internet & Communication**, **Social Implications**, **Word Processing (MS Word)**, **Spreadsheets (MS Excel)**, **Databases (MS Access)**, and **Web Design (HTML/CSS)**.
   - Lesson viewer with formatted CAPS notes, practical step-by-step tips, keyboard shortcut cheatsheets, and embedded video walkthroughs for tricky functions (e.g., `VLOOKUP`, nested `IF`, `COUNTIF`, Access SQL/query design).
   - "Download PDF Guide" button generating clean, printable, offline-accessible revision sheets.

3. **Gamified Practice & Assessment Flow**:
   - Multiple quiz modes: **Term Diagnostic**, **Speed Challenge (Blitz)**, and **Matric Past Exam Drill**.
   - Interactive question types: Multiple Choice, Match Columns (Term-to-Definition), True/False with corrections, and Practical Output Interpretation (e.g., spreadsheet formula errors `#VALUE!`, `#REF!`).
   - Post-quiz review screen with step-by-step memorandum explanations, XP gain, streak counter, and achievement badge unlocks.

4. **Peer Discussion Forum Flow**:
   - Categorized discussion channels (e.g., `#ExcelHelp`, `#TheoryRevision`, `#PatGuidance`, `#MatricPrep`).
   - Create post with tags, code/formula snippets, and image attachments.
   - Upvoting system, verified answer badges, and peer comments with offline drafting queue.

5. **Analytics & Performance Dashboard Flow**:
   - Visual term completion rings and overall syllabus mastery score.
   - Subject breakdown chart comparing Theory vs. Practical proficiency.
   - Identified "Weak Areas" (e.g., "Hardware Bus Architecture" or "Access Relationship Keys") with one-tap remedial review suggestions.
   - Study streak tracker, total quiz questions answered, and earned badges showcase.

### Visual Identity & Theme

- **Aesthetic Direction**: High-energy, clean scholastic tech theme inspired by South African educational excellence and modern productivity suites.
- **Color Palette**:
  - Primary: Deep Emerald Slate (`#006D44`) representing South African scholastic achievement.
  - Secondary / Accent: Energetic Tech Amber (`#F59E0B`) for XP, streak badges, and highlights.
  - Tertiary: Deep Cyan (`#0284C7`) for practical modules (Excel/Access).
  - Background Light: Clean Off-White (`#F8FAFC`); Surface Light: Pristine White (`#FFFFFF`).
  - Background Dark: Midnight Slate (`#0F172A`); Surface Dark: Rich Obsidian Slate (`#1E293B`).
  - Error: Coral Crimson (`#EF4444`); Success: Lush Green (`#10B981`).
- **Typography & Hierarchy**:
  - Display & Headings: Bold, confident sans-serif typography for high legibility under varying lighting conditions.
  - Body & Formulas: Monospace pill styling for Excel functions, HTML tags, and keyboard shortcuts (`Ctrl+Shift+Enter`).
- **Interactive Feedback & Motion**:
  - Smooth spring transitions between grade levels and term tabs.
  - Confetti particle animations on quiz completion and badge unlock.
  - Tactile button ripples and subtle card elevations for touch targets complying with standard 48dp ergonomics.

---

## 3. Key Product Decisions & Trade-Offs

- **Hybrid Persistence Strategy (Offline-First Room + Cloud Firestore)**:
  - *Chosen Approach*: Core CAPS curriculum content, practical guides, and quiz question banks are pre-bundled in a high-speed local Room database with pre-cached assets. Firestore is utilized for user profile sync, analytics backups, and the live community forum.
  - *Why*: Guarantees South African learners never get locked out during load-shedding or when mobile data runs out, while providing seamless cloud sync when internet is restored.
  - *Alternatives Considered*: Pure cloud-only architecture would fail learners with inconsistent cellular connectivity; pure local-only would prevent peer forum discussions and cross-device sync.

- **Downloadable PDF Guides via Android Print / Canvas Engine**:
  - *Chosen Approach*: On-device PDF generation using Android's native `PdfDocument` and print spooler APIs.
  - *Why*: Completely zero-bandwidth and instant. Learners can save directly to device storage or share via WhatsApp/Bluetooth with classmates.
  - *Alternatives Considered*: Fetching pre-hosted PDFs from remote servers requires steady network and storage hosting costs.

- **Authentication Gate Model**:
  - *Chosen Approach*: Credential Manager Google Sign-In with transparent session management. An informative sign-in banner allows learners to see what they are unlocking, keeping the session secure.
  - *Why*: Satisfies institutional security rules and provides reliable user attribution for forum posts.

---

## 4. Technical Architecture & Data Strategy

### Architecture & Component Diagram

```
┌────────────────────────────────────────────────────────────────────────┐
│                        Jetpack Compose UI Layer                        │
│                                                                        │
│   ┌───────────────────┐  ┌───────────────────┐  ┌──────────────────┐   │
│   │   Home / Grades   │  │   Study Modules   │  │  Quiz & Gamified │   │
│   │    Term 1-4 Hub   │  │ & Video Tutorials │  │    Assessment    │   │
│   └─────────┬─────────┘  └─────────┬─────────┘  └────────┬─────────┘   │
│             │                      │                     │             │
│   ┌─────────┴─────────┐  ┌─────────┴─────────┐  ┌────────┴─────────┐   │
│   │   Peer Discussion │  │  PDF Guide Maker  │  │ Learner Analytics│   │
│   │       Forum       │  │ & Export Service  │  │    Dashboard     │   │
│   └─────────┬─────────┘  └─────────┬─────────┘  └────────┬─────────┘   │
└─────────────┼──────────────────────┼─────────────────────┼─────────────┘
              │                      │                     │              
              ▼                      ▼                     ▼              
┌────────────────────────────────────────────────────────────────────────┐
│                  ViewModel Layer (StateFlow & M3 MVI)                  │
│     CatCurriculumViewModel  •  QuizViewModel  •  ForumViewModel        │
└────────────────────────────────────┬───────────────────────────────────┘
                                     │                                    
                                     ▼                                    
┌────────────────────────────────────────────────────────────────────────┐
│                        Repository & Data Layer                         │
│   ┌───────────────────────────────┐ ┌──────────────────────────────┐   │
│   │    Local Room Database        │ │    Firebase Firestore        │   │
│   │ - Bundled CAPS Curriculum     │ │ - Realtime Forum Threads     │   │
│   │ - Question Banks (Gr 10-12)   │ │ - Cloud User Profile         │   │
│   │ - Local Progress & Offline XP │ │ - Community Post Upvotes     │   │
│   └───────────────────────────────┘ └──────────────────────────────┘   │
└────────────────────────────────────────────────────────────────────────┘
```

### Data Model & State

- **`CurriculumModule`**: Grade level (10, 11, 12), Term (1–4), Category (Theory/Practical), Topic title, Key concepts summary, Practical shortcut tips, Video tutorial URL/ID, and PDF content breakdown.
- **`QuizQuestion`**: Grade level, Term, Subject strand, Question stem, Options list, Correct option index, Explanation memo, and Difficulty rating (Foundation, Medium, Exam Level).
- **`LearnerProgress`**: Module completion map, Term-by-term assessment scores, Current XP, Daily streak counter, and Unlocked Badges list (`List<String>`).
- **`ForumThread`**: Document ID, Author ID, Author name, Grade tag, Category tag (Excel, Word, Access, Theory, General), Title, Content text, Upvote count, Reply count, and Created timestamp.
- **`ForumReply`**: Reply ID, Thread ID, Author ID, Author name, Content text, Verified answer flag, and Created timestamp.

### Interactive Component & State Mapping

- **Grade & Term Switcher**: Instant tab selection mutating `selectedGrade` and `selectedTerm` in `CatCurriculumViewModel`, driving smooth content updates for the active term roadmap.
- **Interactive Quiz Engine**: State machine tracking `ActiveQuestionIndex`, `SelectedAnswer`, `IsAnswerSubmitted`, `ScoreAccumulator`, and `TimerRemaining`, displaying immediate explanatory feedback upon answer submission.
- **PDF Study Guide Exporter**: Triggers local `PdfDocument` composition with formatted CAPS headings, summary tables, formula references, and saves to device storage or launches Android Share Sheet.
- **Forum Interaction**: Search bar filtering threads by keyword and topic pill tags; instant local optimistic upvote toggle backed by Firestore transactions.
- **Theme Mode**: Supports explicit Light / Dark / System theme switching via Material 3 dynamic color tokens.

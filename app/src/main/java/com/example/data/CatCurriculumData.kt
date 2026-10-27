package com.example.data

import com.example.model.*

object CatCurriculumData {

    val modules: List<CurriculumModule> = listOf(
        // GRADE 12 (MATRIC) - TERM 1
        CurriculumModule(
            id = "gr12_t1_vlookup",
            grade = GradeLevel.GRADE_12,
            term = CurriculumTerm.TERM_1,
            strand = SubjectStrand.SPREADSHEETS,
            title = "Advanced Lookup Functions (VLOOKUP & HLOOKUP)",
            summary = "Master vertical and horizontal lookups for Matric Practical Paper 1. Learn exact vs approximate matches and common lookup traps.",
            keyConcepts = listOf(
                "VLOOKUP syntax: =VLOOKUP(lookup_value, table_array, col_index_num, [range_lookup])",
                "Range lookup FALSE (or 0) for exact match (text, learner IDs, barcodes).",
                "Range lookup TRUE (or 1) for approximate match (tax brackets, discount tiers, score scales - table must be sorted ascending!).",
                "Absolute referencing (\$A\$2:\$D\$50) on table arrays when copying formulas downward.",
                "HLOOKUP syntax when data is organized horizontally across columns."
            ),
            practicalTips = listOf(
                "Always lock the table array with \$ keys before dragging down (e.g., \$F\$2:\$H\$15).",
                "Ensure the lookup value exists in the very first column of your table array.",
                "Use IFERROR(VLOOKUP(...), \"Not Found\") to prevent unsightly #N/A errors."
            ),
            commonExamTraps = listOf(
                "Forgetting FALSE for exact match will cause wrong return values on unsorted data.",
                "Counting column index numbers from column A instead of starting from the table array's first column."
            ),
            formulaCheatsheet = listOf(
                "=VLOOKUP(C5, \$G\$2:\$I\$10, 3, FALSE)" to "Look up exact match in column 3 of locked table",
                "=HLOOKUP(B2, \$E\$1:\$K\$3, 2, TRUE)" to "Horizontal tier lookup with approximate match",
                "=IFERROR(VLOOKUP(A2, \$D\$2:\$F\$20, 2, 0), 0)" to "Safe lookup returning 0 instead of error"
            ),
            videoTutorialId = "vid_vlookup_matric"
        ),
        CurriculumModule(
            id = "gr12_t1_sys_tech",
            grade = GradeLevel.GRADE_12,
            term = CurriculumTerm.TERM_1,
            strand = SubjectStrand.SYSTEMS_TECH,
            title = "Hardware Architecture & Motherboard Buses",
            summary = "Comprehensive review of the Central Processing Unit (CPU), caching, bus architecture (System bus, Expansion bus), and motherboard components.",
            keyConcepts = listOf(
                "CPU components: Arithmetic Logic Unit (ALU), Control Unit (CU), Registers, and internal Cache (L1, L2, L3).",
                "Machine cycle: Fetch, Decode, Execute, and Store (Clock speed in GHz).",
                "Bus architecture: Data Bus, Address Bus, and Control Bus carrying signals across the system.",
                "RAM vs ROM: Volatility, BIOS/UEFI firmware in NVRAM/Flash ROM.",
                "Solid State Drives (NVMe M.2 vs SATA SSD vs traditional HDD): read/write IOPS, reliability, and lack of moving parts."
            ),
            practicalTips = listOf(
                "When comparing storage types in Paper 2, highlight SSD durability and power consumption, not just speed.",
                "Understand Overclocking risks: excessive heat generation, thermal throttling, and voiding manufacturer warranties."
            ),
            commonExamTraps = listOf(
                "Confusing RAM (temporary working memory) with ROM (non-volatile firmware storage).",
                "Describing clock speed purely as 'speed of computer' without mentioning gigahertz or cycle execution per core."
            )
        ),
        CurriculumModule(
            id = "gr12_t1_access_queries",
            grade = GradeLevel.GRADE_12,
            term = CurriculumTerm.TERM_1,
            strand = SubjectStrand.DATABASES,
            title = "Advanced Access Queries & Calculated Fields",
            summary = "Constructing multi-table queries, calculated fields with mathematical operations, date functions, and parameter prompts.",
            keyConcepts = listOf(
                "Calculated field syntax: NewFieldName: [FieldName] * 1.15 (colon separates alias from formula, square brackets enclose existing fields).",
                "Date calculation: Age: DateDiff(\"yyyy\", [BirthDate], Date()) or DatePart for extracted components.",
                "Parameter query: Enclose prompt in square brackets under Criteria (e.g., [Enter Province Code:]).",
                "Boolean & Wildcard criteria: Like \"J*\" for names starting with J, In (\"Gauteng\", \"KZN\"), Is Null for empty records.",
                "Summary totals: Click 'Totals' icon (Sigma) to apply Group By, Sum, Avg, Count, Min, Max."
            ),
            practicalTips = listOf(
                "Never put an equals sign (=) at the start of an Access calculated field in Query Design view.",
                "Verify field names in expressions match table field names character-for-character."
            ),
            commonExamTraps = listOf(
                "Putting quotes around field names instead of square brackets (e.g., \"Price\" vs [Price]).",
                "Using curly braces or round parentheses for calculated field aliases."
            ),
            formulaCheatsheet = listOf(
                "TotalCost: [Price] * [Quantity]" to "Calculated line total in Query grid",
                "VAT: [Price] * 0.15" to "15% South African VAT calculation",
                "Like \"*@gmail.com\"" to "Criteria matching all Gmail addresses"
            ),
            videoTutorialId = "vid_access_calculated"
        ),

        // GRADE 12 (MATRIC) - TERM 2
        CurriculumModule(
            id = "gr12_t2_excel_logic",
            grade = GradeLevel.GRADE_12,
            term = CurriculumTerm.TERM_2,
            strand = SubjectStrand.SPREADSHEETS,
            title = "Nested IF, AND, OR & Conditional Functions",
            summary = "Complex decision logic in Excel formulas. Nested IF structures, combining logical tests with AND/OR, COUNTIFS, and SUMIFS.",
            keyConcepts = listOf(
                "Nested IF: =IF(condition1, result1, IF(condition2, result2, defaultResult)). Always close all parentheses at the end.",
                "AND logic: =AND(A1 >= 50, B1 >= 50) returns TRUE only if all conditions evaluate to true.",
                "OR logic: =OR(A1 = \"Pass\", B1 = \"Distinction\") returns TRUE if at least one condition evaluates to true.",
                "COUNTIFS: =COUNTIFS(criteria_range1, criteria1, criteria2_range, criteria2).",
                "SUMIFS: =SUMIFS(sum_range, criteria_range1, criteria1, criteria_range2, criteria2) - note sum_range comes FIRST!"
            ),
            practicalTips = listOf(
                "Check that nested IF conditions are in sequential order (descending from highest threshold or ascending from lowest) to avoid false triggers.",
                "In SUMIFS, remember sum_range is the 1st argument, unlike SUMIF where it is the 3rd argument."
            ),
            formulaCheatsheet = listOf(
                "=IF(AND(B2>=50, C2>=50), \"Pass\", \"Fail\")" to "Both conditions must be met",
                "=COUNTIFS(A2:A50, \"Grade 12\", B2:B50, \">75\")" to "Count matriculants scoring over 75",
                "=SUMIFS(E2:E50, C2:C50, \"Gauteng\", D2:D50, \">=1000\")" to "Sum sales for Gauteng over 1000"
            ),
            videoTutorialId = "vid_excel_nested_if"
        ),
        CurriculumModule(
            id = "gr12_t2_networks",
            grade = GradeLevel.GRADE_12,
            term = CurriculumTerm.TERM_2,
            strand = SubjectStrand.NETWORK_TECH,
            title = "Network Topologies, Protocols & Security",
            summary = "In-depth study of PAN, LAN, WLAN, WAN, transmission media (UTP vs STP, single/multi-mode Fibre), IP addressing, and network security defences.",
            keyConcepts = listOf(
                "Physical transmission media: UTP (Cat 5e/6, max 100m, susceptible to EMI/attenuation) vs Fibre Optic (light pulses, high bandwidth, immune to EMI, long distances).",
                "Wireless networking: Wi-Fi standards (802.11 ax/ac), Bluetooth (short range 10m, PAN), Cellular 4G/5G.",
                "Network hardware: NIC, Switch (packet forwarding via MAC addresses), Router (routes packets between different IP subnets/WAN).",
                "IP Addressing: IPv4 (32-bit dotted decimal e.g. 192.168.1.1) vs IPv6 (128-bit hexadecimal). DHCP and DNS servers.",
                "Security protocols: WPA3 encryption, Firewalls, VPNs (Virtual Private Networks) for encrypted tunneling over public internet."
            ),
            practicalTips = listOf(
                "Remember switches use MAC addresses to forward frames locally; routers use IP addresses to route packets between networks.",
                "DNS resolves human-friendly URLs (www.education.gov.za) into numerical IP addresses."
            ),
            commonExamTraps = listOf(
                "Confusing a switch with a router in network diagram questions.",
                "Saying Fibre uses electricity (it uses light pulses via glass/plastic core)."
            )
        ),
        CurriculumModule(
            id = "gr12_t2_html_tables",
            grade = GradeLevel.GRADE_12,
            term = CurriculumTerm.TERM_2,
            strand = SubjectStrand.WEB_DESIGN,
            title = "HTML Tables, Hyperlinks & Embedded Media",
            summary = "Building structured HTML pages with tables (rowspan, colspan), relative vs absolute hyperlinks, audio/video embeds, and inline CSS formatting.",
            keyConcepts = listOf(
                "Table structure: <table>, <tr> (table row), <th> (table header, bold & centered), <td> (table data cell).",
                "Spanning cells: colspan=\"2\" (spans across columns), rowspan=\"3\" (spans down rows).",
                "Hyperlinks: <a href=\"filename.html\">Relative</a> vs <a href=\"https://...\">Absolute URL</a>, mailto links.",
                "Images & media: <img src=\"image.jpg\" alt=\"Description\" width=\"300\" height=\"200\"> (self-closing tag).",
                "CSS styling: style=\"color: #006D44; background-color: #F8FAFC; text-align: center;\"."
            ),
            practicalTips = listOf(
                "Always close open tags in reverse order of opening (e.g., <td><b>text</b></td>).",
                "Don't add closing </img> or <br></br> tags in basic HTML - they are void/self-closing elements."
            ),
            formulaCheatsheet = listOf(
                "<td colspan=\"2\">Header</td>" to "Merge two horizontal cells",
                "<a href=\"#top\">Back to Top</a>" to "Bookmark/anchor link within page",
                "<table border=\"1\" width=\"80%\">" to "Table with visible border and responsive width"
            )
        ),

        // GRADE 12 (MATRIC) - TERM 3
        CurriculumModule(
            id = "gr12_t3_excel_text",
            grade = GradeLevel.GRADE_12,
            term = CurriculumTerm.TERM_3,
            strand = SubjectStrand.SPREADSHEETS,
            title = "Text Extraction & Manipulation Functions",
            summary = "Extracting and formatting text strings in Excel: LEFT, RIGHT, MID, FIND, LEN, CONCATENATE, and handling Matric practical exam data cleaning.",
            keyConcepts = listOf(
                "LEFT(text, num_chars): Extracts characters from the left end of a string.",
                "RIGHT(text, num_chars): Extracts characters from the right end of a string.",
                "MID(text, start_num, num_chars): Extracts characters from the middle of a string.",
                "LEN(text): Returns total character length of a string, including spaces.",
                "FIND(find_text, within_text, [start_num]): Locates position number of a character (e.g. finding space in a full name).",
                "Dynamic surname extraction: =MID(A2, FIND(\" \", A2) + 1, LEN(A2))."
            ),
            practicalTips = listOf(
                "Remember that text extraction functions return a TEXT string, even if the characters are digits. Wrap with VALUE() if you need to calculate.",
                "Excel error codes: #VALUE! (wrong data type), #DIV/0! (division by zero), #REF! (deleted cell reference), #NAME? (misspelled function name)."
            ),
            formulaCheatsheet = listOf(
                "=MID(B2, 3, 4)" to "Extract 4 characters starting at position 3",
                "=LEFT(A2, FIND(\" \", A2) - 1)" to "Extract first name before the space",
                "=CONCAT(A2, \", \", B2)" to "Join surname, comma, and first name"
            )
        ),
        CurriculumModule(
            id = "gr12_t3_word_advanced",
            grade = GradeLevel.GRADE_12,
            term = CurriculumTerm.TERM_3,
            strand = SubjectStrand.WORD_PROCESSING,
            title = "Advanced Word: Mail Merge, TOC & Section Breaks",
            summary = "Comprehensive mastery of Word processing for Matric PAT and Paper 1: Automatic Table of Contents, Section Breaks, Citations, and Mail Merge.",
            keyConcepts = listOf(
                "Section Breaks (Next Page vs Continuous): Enables independent headers, footers, page numbering styles (Roman numerals vs Arabic), and page orientation (Portrait vs Landscape) within one document.",
                "Automatic Table of Contents: Requires applying Heading 1, Heading 2 styles throughout document.",
                "Mail Merge workflow: Select main document -> Choose recipient list (Excel/Access) -> Insert merge fields -> Preview -> Complete & Merge.",
                "Captions & Cross-references: Numbering figures/tables automatically and referencing them in text.",
                "Document protection: Restricting formatting and editing permissions."
            ),
            practicalTips = listOf(
                "To decouple page numbering in Section 2, click 'Link to Previous' in the Header & Footer tab to deactivate it.",
                "Always run 'Update Entire Table' on the Table of Contents before submitting."
            )
        ),

        // GRADE 12 (MATRIC) - TERM 4
        CurriculumModule(
            id = "gr12_t4_exam_prep",
            grade = GradeLevel.GRADE_12,
            term = CurriculumTerm.TERM_4,
            strand = SubjectStrand.SYSTEMS_TECH,
            title = "Matric Final NSC Revision & Exam Strategy",
            summary = "Exam breakdown for CAT Paper 1 (Practical - 150 marks, 3 hours) and Paper 2 (Theory - 150 marks, 3 hours). High-frequency CAPS questions and time allocation.",
            keyConcepts = listOf(
                "Paper 1 breakdown: Word (~50 marks), Excel (~50 marks), Access (~40 marks), HTML (~10 marks) / Integrated task.",
                "Paper 2 breakdown: Section A (Short questions - 25 marks), Section B (System Tech, Internet/Network, Info Management, Social Implications - 75 marks), Section C (Integrated scenario - 50 marks).",
                "Time management: 1 mark = 1.2 minutes. Never spend more than 40 minutes on an elusive single formula.",
                "Troubleshooting in scenarios: Identify the problem, explain the cause, and provide a concrete practical solution.",
                "Emerging technologies: AI, IoT, Cloud Computing, Autonomous vehicles, Blockchain, Green computing, and biometric authentication."
            ),
            practicalTips = listOf(
                "In Section C scenario questions, link your answer directly to the company or school mentioned in the question prompt.",
                "Save your work in Paper 1 frequently (Ctrl + S) and make sure modified files are saved in the exam examination folder."
            )
        ),

        // GRADE 11 - TERM 1
        CurriculumModule(
            id = "gr11_t1_if",
            grade = GradeLevel.GRADE_11,
            term = CurriculumTerm.TERM_1,
            strand = SubjectStrand.SPREADSHEETS,
            title = "Logical Functions: Single IF, COUNTIF & SUMIF",
            summary = "Foundations of spreadsheet decision-making. Single IF condition syntax, criteria counting, and conditional summing.",
            keyConcepts = listOf(
                "Single IF syntax: =IF(logical_test, [value_if_true], [value_if_false]).",
                "Comparison operators: = (equals), <> (not equals), > (greater than), < (less than), >= (greater/equal), <= (less/equal).",
                "COUNTIF syntax: =COUNTIF(range, criteria). Text criteria and comparison operators must be in quotation marks (e.g. \">=50\").",
                "SUMIF syntax: =SUMIF(range, criteria, [sum_range]).",
                "COUNTA (counts all non-empty cells) vs COUNT (counts numeric cells only)."
            ),
            practicalTips = listOf(
                "Enclose string outputs in double quotes in IF formulas: =IF(C2 >= 50, \"Pass\", \"Fail\"). Numbers do not need quotes.",
                "Use cell references in criteria where possible to make models dynamic."
            ),
            formulaCheatsheet = listOf(
                "=IF(D4>100, D4*0.9, D4)" to "Apply 10% discount if over 100",
                "=COUNTIF(B2:B30, \"Yes\")" to "Count all affirmative responses",
                "=SUMIF(C2:C30, \"Stationery\", D2:D30)" to "Sum total expenditure on Stationery"
            ),
            videoTutorialId = "vid_excel_single_if"
        ),
        CurriculumModule(
            id = "gr11_t1_sys_soft",
            grade = GradeLevel.GRADE_11,
            term = CurriculumTerm.TERM_1,
            strand = SubjectStrand.SYSTEMS_TECH,
            title = "System Software, Operating Systems & Utilities",
            summary = "The role of Operating Systems (Windows, Linux, macOS, Android, iOS), system utilities, device drivers, and storage management.",
            keyConcepts = listOf(
                "Operating system functions: Process management, Memory management, Storage/File management, Input/Output device handling, User interface (GUI vs CLI).",
                "System utilities: Disk Defragmenter (HDD optimization), Disk Cleanup, Backup utilities, Antivirus/Anti-malware, File compression (ZIP/RAR).",
                "Device drivers: Specialized software that enables the OS to communicate with hardware peripherals (printers, graphics cards).",
                "File systems: FAT32 (4GB file size limit) vs NTFS (security permissions, journaling, large file support).",
                "Virtual memory: Paging file / swap space on storage drive used when physical RAM is fully allocated."
            ),
            practicalTips = listOf(
                "Never defragment an SSD! It degrades flash cell lifespan without any performance gain since SSDs have zero mechanical seek time.",
                "Distinguish between System Software (OS, utilities, drivers) and Application Software (Word, Excel, games, browsers)."
            )
        ),
        CurriculumModule(
            id = "gr11_t2_access_intro",
            grade = GradeLevel.GRADE_11,
            term = CurriculumTerm.TERM_2,
            strand = SubjectStrand.DATABASES,
            title = "Introduction to Relational Databases (MS Access)",
            summary = "Database concepts: Fields, Records, Tables, Data Types, Primary Keys, Input Masks, and Validation Rules.",
            keyConcepts = listOf(
                "Database hierarchy: Database -> Tables -> Records (rows) -> Fields (columns).",
                "Access data types: Short Text (up to 255 chars), Long Text, Number, Date/Time, Currency, AutoNumber, Yes/No, OLE Object, Hyperlink.",
                "Primary Key: A unique identifier for every record in a table preventing duplicate entries.",
                "Validation Rule & Validation Text: Enforces data integrity at input time (e.g., >= 0 And <= 100). Validation Text is the custom error message displayed to the user.",
                "Input Mask: Restricts character types during entry (e.g. 000000 0000 00 0 for RSA ID number)."
            ),
            practicalTips = listOf(
                "Validation Rule: >=#2000/01/01# requires hash symbols (#) around dates in Access.",
                "Telephone numbers and Postal codes must be stored as Short Text, NOT Number, to preserve leading zeros!"
            ),
            formulaCheatsheet = listOf(
                "Validation Rule: In (\"M\", \"F\")" to "Restricts input to Male or Female",
                "Validation Rule: >=13 And <=19" to "Teenager age validation boundary",
                "Input Mask: 0000" to "Requires exactly four numeric digits"
            ),
            videoTutorialId = "vid_access_tables"
        ),
        CurriculumModule(
            id = "gr11_t2_html_intro",
            grade = GradeLevel.GRADE_11,
            term = CurriculumTerm.TERM_2,
            strand = SubjectStrand.WEB_DESIGN,
            title = "Web Development Fundamentals: HTML Structure",
            summary = "Core HTML page architecture: Document tags, text formatting, ordered/unordered lists, attributes, and colors.",
            keyConcepts = listOf(
                "HTML structure: <!DOCTYPE html>, <html>, <head>, <title>, <body>.",
                "Headings: <h1> (largest) to <h6> (smallest).",
                "Paragraphs & Breaks: <p>paragraph</p>, <br> line break, <hr> horizontal thematic break rule.",
                "Text formatting: <b>bold</b>, <i>italic</i>, <u>underline</u>, <strong>, <em>.",
                "Lists: <ol> (ordered/numbered list) and <ul> (unordered/bulleted list) containing <li> list items.",
                "Hexadecimal color codes: 6 characters (#RRGGBB e.g. #006D44 for green, #FFFFFF for white, #000000 for black)."
            ),
            practicalTips = listOf(
                "The <title> tag goes inside the <head> section and appears on the browser tab, NOT on the webpage body itself.",
                "Always save your HTML file with the .html or .htm extension."
            ),
            formulaCheatsheet = listOf(
                "<ol type=\"A\">" to "Numbered list with uppercase letters A, B, C",
                "<ul style=\"list-style-type: square;\">" to "Bulleted list with square bullets",
                "<body bgcolor=\"#F8FAFC\">" to "Sets page background color"
            )
        ),

        // GRADE 10 - TERM 1 & 2
        CurriculumModule(
            id = "gr10_t1_intro_ict",
            grade = GradeLevel.GRADE_10,
            term = CurriculumTerm.TERM_1,
            strand = SubjectStrand.SYSTEMS_TECH,
            title = "Introduction to Computers & Information Processing Cycle",
            summary = "Fundamental computer components: Input, Processing, Output, Storage, and Communication (IPOS cycle).",
            keyConcepts = listOf(
                "Information Processing Cycle: Input -> Processing -> Storage -> Output -> Communication.",
                "Data vs Information: Data consists of raw, unprocessed facts and figures; Information is processed, organized, meaningful data.",
                "Input devices: Keyboard, Mouse, Touchpad, Scanner, Barcode reader, Microphone, Webcam, Biometric scanner.",
                "Output devices: Monitors (LCD, LED, OLED - resolution in pixels), Printers (Inkjet vs Laser - speed in PPM, resolution in DPI), Speakers, Projectors.",
                "Primary storage (RAM) vs Secondary storage (SSD, HDD, Flash drive, Optical discs)."
            ),
            practicalTips = listOf(
                "Remember: A printer is an output device; a scanner is an input device; a multi-function printer (MFP) performs both.",
                "Laser printers have higher initial purchase costs but lower cost-per-page for high-volume text printing compared to Inkjets."
            )
        ),
        CurriculumModule(
            id = "gr10_t1_word_basics",
            grade = GradeLevel.GRADE_10,
            term = CurriculumTerm.TERM_1,
            strand = SubjectStrand.WORD_PROCESSING,
            title = "Word Processing Basics: Formatting & Layout",
            summary = "Basic formatting tools in MS Word: Font formatting, paragraph alignment, line spacing, margins, indents, and bullet lists.",
            keyConcepts = listOf(
                "Character formatting: Font family, font size, bold, italic, underline, subscript (H₂O), superscript (x²).",
                "Paragraph formatting: Left, Center, Right, and Justified alignment.",
                "Spacing: Line spacing (1.0, 1.15, 1.5, 2.0) and Paragraph spacing (Space Before, Space After).",
                "Indents: Left indent, Right indent, First-line indent, and Hanging indent (common in bibliographies).",
                "Page setup: Margins (Normal, Narrow, Wide), Orientation (Portrait vs Landscape), Page size (A4 standard)."
            ),
            practicalTips = listOf(
                "Never press Enter multiple times to create space between paragraphs - adjust Paragraph Spacing After instead!",
                "Use Ctrl + Z to undo and Ctrl + Y to redo actions."
            )
        ),
        CurriculumModule(
            id = "gr10_t2_excel_basics",
            grade = GradeLevel.GRADE_10,
            term = CurriculumTerm.TERM_2,
            strand = SubjectStrand.SPREADSHEETS,
            title = "Spreadsheet Essentials: Basic Arithmetic & Functions",
            summary = "Excel grid fundamentals: Cells, Columns, Rows, Ranges, basic operators (+, -, *, /), and core functions (SUM, AVERAGE, MIN, MAX, COUNT).",
            keyConcepts = listOf(
                "Cell referencing: Column letter followed by Row number (e.g. B4, Z10).",
                "Mathematical operators: + (addition), - (subtraction), * (multiplication), / (division), ^ (exponentiation).",
                "Order of operations: BODMAS (Brackets, Orders, Division, Multiplication, Addition, Subtraction).",
                "Core functions: =SUM(A1:A10), =AVERAGE(B2:B20), =MIN(C1:C10), =MAX(D1:D10), =COUNT(E1:E10).",
                "AutoFill handle: Small square at the bottom-right corner of an active cell used to copy formulas or sequences."
            ),
            practicalTips = listOf(
                "Every formula and function in Excel MUST start with an equals sign (=).",
                "AVERAGE ignores blank cells, but calculates zero values in the arithmetic mean."
            ),
            formulaCheatsheet = listOf(
                "=SUM(B2:B15)" to "Adds all numbers in the range",
                "=AVERAGE(C2:C20)" to "Calculates arithmetic mean",
                "=MAX(D2:D50) - MIN(D2:D50)" to "Calculates mathematical range"
            )
        )
    )

    val questions: List<QuizQuestion> = listOf(
        // GRADE 12 QUESTIONS
        QuizQuestion(
            id = "q_gr12_vlookup_1",
            grade = GradeLevel.GRADE_12,
            term = CurriculumTerm.TERM_1,
            strand = SubjectStrand.SPREADSHEETS,
            difficulty = QuestionDifficulty.MATRIC_EXAM,
            question = "A learner writes the formula =VLOOKUP(A2, \$D\$2:\$F\$10, 2, FALSE). What does the argument FALSE specify?",
            options = listOf(
                "The table array is unsorted and an exact match is required.",
                "The function will return 0 if the cell value is blank.",
                "The lookup should perform an approximate range match.",
                "The column index number should be counted from right to left."
            ),
            correctIndex = 0,
            explanation = "In the VLOOKUP function, setting the fourth argument [range_lookup] to FALSE (or 0) requires an exact match. If an exact match is not found, Excel returns the #N/A error. TRUE is used for approximate matches on sorted tables.",
            formulaOrSnippet = "=VLOOKUP(A2, \$D\$2:\$F\$10, 2, FALSE)"
        ),
        QuizQuestion(
            id = "q_gr12_access_1",
            grade = GradeLevel.GRADE_12,
            term = CurriculumTerm.TERM_1,
            strand = SubjectStrand.DATABASES,
            difficulty = QuestionDifficulty.MATRIC_EXAM,
            question = "Which expression correctly creates a calculated field named 'DiscountPrice' that gives a 10% discount on the existing field 'Price' in an Access query?",
            options = listOf(
                "DiscountPrice: [Price] * 0.9",
                "=DiscountPrice: [Price] * 0.9",
                "DiscountPrice = [Price] * 10%",
                "[DiscountPrice]: [Price] - 10%"
            ),
            correctIndex = 0,
            explanation = "In MS Access query design view, calculated fields follow the syntax: AliasName: [FieldName] * value. An equals sign (=) is never placed at the start, and percentage values must be written as decimals (0.9 or 0.1).",
            formulaOrSnippet = "DiscountPrice: [Price] * 0.9"
        ),
        QuizQuestion(
            id = "q_gr12_excel_err",
            grade = GradeLevel.GRADE_12,
            term = CurriculumTerm.TERM_2,
            strand = SubjectStrand.SPREADSHEETS,
            difficulty = QuestionDifficulty.MATRIC_EXAM,
            question = "An Excel formula produces the error #VALUE!. What is the most likely cause of this error in CAPS practical tasks?",
            options = listOf(
                "A mathematical operator is applied to a cell containing text instead of a number.",
                "A formula is trying to divide a numeric value by zero or an empty cell.",
                "A referenced cell was deleted from the worksheet.",
                "A function name was misspelled (such as =SUMM instead of =SUM)."
            ),
            correctIndex = 0,
            explanation = "#VALUE! occurs when a formula has the wrong data type (e.g. adding text like =A1+B1 where A1 contains 'Five'). Division by zero gives #DIV/0!, deleted references give #REF!, and misspelled function names give #NAME?."
        ),
        QuizQuestion(
            id = "q_gr12_net_1",
            grade = GradeLevel.GRADE_12,
            term = CurriculumTerm.TERM_2,
            strand = SubjectStrand.NETWORK_TECH,
            difficulty = QuestionDifficulty.MATRIC_EXAM,
            question = "Why is Fibre Optic cable immune to Electromagnetic Interference (EMI) while UTP copper cable is susceptible?",
            options = listOf(
                "Fibre transmits data as pulses of light through glass or plastic, not electrical signals.",
                "Fibre cables are coated in thick lead that blocks radio waves.",
                "Fibre uses higher AC electrical voltages that overpower radio frequency interference.",
                "Fibre networks only operate in vacuum conduit channels."
            ),
            correctIndex = 0,
            explanation = "Fibre-optic cables use light pulses (via LEDs or lasers) transmitted through thin glass/silica or plastic strands. Because light carries no electrical charge, it cannot be distorted by external electromagnetic fields or lightning."
        ),
        QuizQuestion(
            id = "q_gr12_excel_text",
            grade = GradeLevel.GRADE_12,
            term = CurriculumTerm.TERM_3,
            strand = SubjectStrand.SPREADSHEETS,
            difficulty = QuestionDifficulty.MATRIC_EXAM,
            question = "If cell A1 contains the text 'MATRIC2026', what will the formula =MID(A1, 7, 4) return?",
            options = listOf(
                "2026",
                "MATRIC",
                "IC20",
                "20"
            ),
            correctIndex = 0,
            explanation = "The MID function extracts characters starting from the 7th character for a length of 4 characters. Positions 1-6 are 'M-A-T-R-I-C', and positions 7-10 are '2-0-2-6'.",
            formulaOrSnippet = "=MID(\"MATRIC2026\", 7, 4)"
        ),
        QuizQuestion(
            id = "q_gr12_sys_bios",
            grade = GradeLevel.GRADE_12,
            term = CurriculumTerm.TERM_1,
            strand = SubjectStrand.SYSTEMS_TECH,
            difficulty = QuestionDifficulty.INTERMEDIATE,
            question = "What is the primary role of the UEFI / BIOS firmware stored in ROM when a computer is powered on?",
            options = listOf(
                "Performs the Power-On Self-Test (POST) and locates the OS bootloader.",
                "Cleans temporary files and defragments storage sectors.",
                "Translates spreadsheet formulas into machine code.",
                "Scans incoming internet traffic for trojan horse malware."
            ),
            correctIndex = 0,
            explanation = "The BIOS/UEFI firmware performs POST to test essential hardware (RAM, keyboard, CPU), initializes devices, and transfers execution control to the OS bootloader on secondary storage."
        ),

        // GRADE 11 QUESTIONS
        QuizQuestion(
            id = "q_gr11_excel_if_1",
            grade = GradeLevel.GRADE_11,
            term = CurriculumTerm.TERM_1,
            strand = SubjectStrand.SPREADSHEETS,
            difficulty = QuestionDifficulty.INTERMEDIATE,
            question = "What will be the result of the formula =IF(C2 >= 80, \"A\", IF(C2 >= 70, \"B\", \"C\")) if cell C2 contains 75?",
            options = listOf(
                "\"B\"",
                "\"A\"",
                "\"C\"",
                "#VALUE!"
            ),
            correctIndex = 0,
            explanation = "The first test C2 >= 80 evaluates to FALSE (since 75 < 80). The formula proceeds to the second test C2 >= 70, which evaluates to TRUE (75 >= 70), returning \"B\".",
            formulaOrSnippet = "=IF(C2 >= 80, \"A\", IF(C2 >= 70, \"B\", \"C\"))"
        ),
        QuizQuestion(
            id = "q_gr11_access_key",
            grade = GradeLevel.GRADE_11,
            term = CurriculumTerm.TERM_2,
            strand = SubjectStrand.DATABASES,
            difficulty = QuestionDifficulty.FOUNDATION,
            question = "Which field in a South African School Learner database is the most suitable choice for a Primary Key?",
            options = listOf(
                "South African ID Number (or unique Learner Admission Number)",
                "Learner Surname",
                "Date of Birth",
                "Home Postal Code"
            ),
            correctIndex = 0,
            explanation = "A Primary Key must uniquely identify every single record with zero possibility of duplicates or null entries. Surnames, birth dates, and postal codes can easily be shared by multiple learners."
        ),
        QuizQuestion(
            id = "q_gr11_html_img",
            grade = GradeLevel.GRADE_11,
            term = CurriculumTerm.TERM_2,
            strand = SubjectStrand.WEB_DESIGN,
            difficulty = QuestionDifficulty.FOUNDATION,
            question = "Which HTML tag and attribute combination correctly displays an image named 'chart.png' with alternate text?",
            options = listOf(
                "<img src=\"chart.png\" alt=\"Quarterly Sales Chart\">",
                "<image href=\"chart.png\" text=\"Quarterly Sales Chart\">",
                "<picture link=\"chart.png\"></picture>",
                "<img url=\"chart.png\" title=\"Quarterly Sales Chart\"></img>"
            ),
            correctIndex = 0,
            explanation = "The <img> tag uses the 'src' attribute for file path and 'alt' for alternate descriptive text (used for accessibility and when images fail to load). It is a self-closing void tag in standard HTML."
        ),
        QuizQuestion(
            id = "q_gr11_util_defrag",
            grade = GradeLevel.GRADE_11,
            term = CurriculumTerm.TERM_1,
            strand = SubjectStrand.SYSTEMS_TECH,
            difficulty = QuestionDifficulty.INTERMEDIATE,
            question = "Why should a Disk Defragmenter utility NOT be run on a Solid State Drive (SSD)?",
            options = listOf(
                "SSDs have no moving read/write heads and defragging causes unnecessary write wear on flash memory cells.",
                "SSDs cannot store files non-contiguously.",
                "Defragmentation will erase the operating system kernel on an SSD.",
                "SSDs already contain magnetic platters that defragment automatically."
            ),
            correctIndex = 0,
            explanation = "SSDs access data electronically with equal seek time for all blocks. Defragmenting writes large volumes of data, exhausting the finite read/write write-cycles of NAND flash cells without performance benefit."
        ),

        // GRADE 10 QUESTIONS
        QuizQuestion(
            id = "q_gr10_ipos",
            grade = GradeLevel.GRADE_10,
            term = CurriculumTerm.TERM_1,
            strand = SubjectStrand.SYSTEMS_TECH,
            difficulty = QuestionDifficulty.FOUNDATION,
            question = "Which of the following describes the difference between Data and Information in CAT?",
            options = listOf(
                "Data is raw, unorganized facts; Information is processed data that has context and meaning.",
                "Data is stored in RAM; Information is stored on hard drives.",
                "Data refers to text only; Information refers to images and video.",
                "Data is output; Information is input."
            ),
            correctIndex = 0,
            explanation = "Data consists of raw, unprocessed items (e.g. 12, 18, 15). Information is the result of processing that data to provide meaningful context (e.g. 'Average test score of Grade 10 CAT is 15/20')."
        ),
        QuizQuestion(
            id = "q_gr10_excel_sum",
            grade = GradeLevel.GRADE_10,
            term = CurriculumTerm.TERM_2,
            strand = SubjectStrand.SPREADSHEETS,
            difficulty = QuestionDifficulty.FOUNDATION,
            question = "Which Excel function adds all numbers in cells B2 to B10?",
            options = listOf(
                "=SUM(B2:B10)",
                "=TOTAL(B2..B10)",
                "=ADD(B2:B10)",
                "=COUNT(B2:B10)"
            ),
            correctIndex = 0,
            explanation = "In Excel, =SUM(B2:B10) adds all numeric values within the contiguous range. =COUNT only tallies how many cells contain numbers."
        )
    )

    val videoTutorials: List<VideoTutorial> = listOf(
        VideoTutorial(
            id = "vid_vlookup_matric",
            title = "Mastering VLOOKUP & HLOOKUP in Matric Exam Papers",
            strand = SubjectStrand.SPREADSHEETS,
            grade = GradeLevel.GRADE_12,
            durationMinutes = 8,
            description = "Walkthrough of authentic DBE CAT Paper 1 past exam questions featuring VLOOKUP, exact matches with FALSE, and absolute table range locking.",
            keyTimestamps = listOf(
                "00:30" to "Understanding lookup_value and table_array",
                "02:15" to "Locking Table Array with \$ signs (F4 shortcut)",
                "04:20" to "Choosing between TRUE (approx) and FALSE (exact)",
                "06:10" to "Troubleshooting the #N/A error with IFERROR"
            ),
            tipSnippet = "=VLOOKUP(LookupValue, \$TableRange\$, ColIndex, FALSE)"
        ),
        VideoTutorial(
            id = "vid_access_calculated",
            title = "MS Access: Calculated Fields, DateDiff & Validation Rules",
            strand = SubjectStrand.DATABASES,
            grade = GradeLevel.GRADE_12,
            durationMinutes = 11,
            description = "Step-by-step practical guide on constructing expressions in Access query design view, age calculations, and input masks for RSA IDs.",
            keyTimestamps = listOf(
                "01:00" to "Creating a Calculated Field (Alias: [Field])",
                "03:45" to "Using DateDiff to calculate Age in years",
                "06:30" to "Parameter Queries with square brackets [ ]",
                "08:50" to "Validation Rules: >=0 And <=100 and Validation Text"
            ),
            tipSnippet = "DiscountedPrice: [Price] * 0.85"
        ),
        VideoTutorial(
            id = "vid_excel_nested_if",
            title = "Nested IF, AND, OR and SUMIFS Step-by-Step",
            strand = SubjectStrand.SPREADSHEETS,
            grade = GradeLevel.GRADE_12,
            durationMinutes = 9,
            description = "Conquer multi-level conditional grading in Excel formulas, combining nested IFs with AND/OR logic without parenthesis mismatch.",
            keyTimestamps = listOf(
                "00:45" to "Nested IF structure logic diagram",
                "03:10" to "Combining IF with AND(cond1, cond2)",
                "05:30" to "SUMIFS vs SUMIF: Which argument comes first?",
                "07:45" to "Closing multiple brackets at the end"
            ),
            tipSnippet = "=IF(AND(Score>=80, Attendance>=90), \"Distinction\", \"Standard\")"
        ),
        VideoTutorial(
            id = "vid_html_tables",
            title = "HTML 5 Tables, Colspan, Rowspan & Hyperlinks",
            strand = SubjectStrand.WEB_DESIGN,
            grade = GradeLevel.GRADE_11,
            durationMinutes = 7,
            description = "Everything you need for the HTML section in CAT Paper 1: table tags, cell merging with colspan/rowspan, and anchor bookmark links.",
            keyTimestamps = listOf(
                "00:50" to "Basic table structure: table, tr, th, td",
                "02:40" to "Colspan vs Rowspan explained visually",
                "04:30" to "Relative hyperlinks vs absolute URLs",
                "06:00" to "Target='_blank' attribute behavior"
            ),
            tipSnippet = "<td colspan=\"3\">Merged Header</td>"
        ),
        VideoTutorial(
            id = "vid_word_sections",
            title = "Word Section Breaks, Unlinking Headers & Roman Numerals",
            strand = SubjectStrand.WORD_PROCESSING,
            grade = GradeLevel.GRADE_12,
            durationMinutes = 10,
            description = "A classic Matric PAT and exam requirement: Setting Roman numerals for preliminary pages and Arabic (1, 2, 3) for chapter content.",
            keyTimestamps = listOf(
                "01:10" to "Inserting Section Break (Next Page)",
                "03:00" to "Unlinking Header/Footer (Link to Previous)",
                "05:20" to "Page Number Format: Start at 1",
                "08:00" to "Automatic Table of Contents generation"
            ),
            tipSnippet = "Deactivate 'Link to Previous' before changing page number format in Section 2!"
        )
    )

    val forumThreads: List<ForumThread> = listOf(
        ForumThread(
            id = "post_1",
            title = "How do I do a nested IF with 4 grade boundaries without getting #VALUE!?",
            author = "Thabo M.",
            authorGrade = "Grade 12",
            category = "Excel",
            content = "Hi guys! I'm struggling with the June 2025 past paper. I need to output 'Level 7' if >=80, 'Level 6' if >=70, 'Level 5' if >=60, otherwise 'Below'. My formula keeps showing #VALUE!. What am I doing wrong?",
            formulaSnippet = "=IF(D2>=80, Level 7, IF(D2>=70, Level 6, IF(D2>=60, Level 5, Below)))",
            upvotes = 14,
            timestamp = "2 hours ago",
            isSolved = true,
            replies = listOf(
                ForumReply(
                    id = "rep_1_1",
                    author = "Mr. Van der Merwe (CAT Educator)",
                    authorGrade = "Teacher",
                    content = "Remember that text strings inside Excel formulas MUST always be enclosed in double quotation marks! Otherwise Excel thinks 'Level' is a function or named range. Your formula should be:\n=IF(D2>=80, \"Level 7\", IF(D2>=70, \"Level 6\", IF(D2>=60, \"Level 5\", \"Below\")))",
                    timestamp = "1 hour ago",
                    isTeacherVerified = true
                ),
                ForumReply(
                    id = "rep_1_2",
                    author = "Ayanda K.",
                    authorGrade = "Grade 12",
                    content = "Also make sure you have 3 closing brackets ))) at the very end of the formula, one for each IF you opened!",
                    timestamp = "45 mins ago"
                )
            )
        ),
        ForumThread(
            id = "post_2",
            title = "What is the correct Validation Rule for a South African 13-digit ID number in Access?",
            author = "Nandi S.",
            authorGrade = "Grade 11",
            category = "Access",
            content = "For our PAT Phase 2 database, how do I make sure the ID number entered has exactly 13 digits and only numbers?",
            upvotes = 9,
            timestamp = "5 hours ago",
            isSolved = true,
            replies = listOf(
                ForumReply(
                    id = "rep_2_1",
                    author = "Sipho D.",
                    authorGrade = "Grade 12",
                    content = "First, make sure the field data type is Short Text (NOT Number, because 13 digits is too big for Access Long Integer and you'll lose leading zeros!).\nThen in Input Mask put: 0000000000000\n(13 zeros require 13 mandatory digits 0-9).",
                    timestamp = "4 hours ago",
                    isTeacherVerified = true
                )
            )
        ),
        ForumThread(
            id = "post_3",
            title = "Difference between UTP and STP cables in Theory Paper 2",
            author = "Liam P.",
            authorGrade = "Grade 10",
            category = "Theory",
            content = "Can someone explain in simple terms why a school would use STP instead of UTP in their computer lab?",
            upvotes = 7,
            timestamp = "1 day ago",
            isSolved = false,
            replies = listOf(
                ForumReply(
                    id = "rep_3_1",
                    author = "Kagiso M.",
                    authorGrade = "Grade 11",
                    content = "STP has metal shielding (foil) around the twisted pairs of copper wires. This protects it from Electromagnetic Interference (EMI) from heavy electrical equipment or generators. UTP is unshielded, which is cheaper and more flexible, but vulnerable to electrical noise.",
                    timestamp = "18 hours ago"
                )
            )
        ),
        ForumThread(
            id = "post_4",
            title = "PAT Phase 3: HTML Website requirements checklist",
            author = "Zanele N.",
            authorGrade = "Grade 12",
            category = "PAT",
            content = "Does anyone have a quick checklist of the mandatory HTML elements for PAT Phase 3 to score full marks on the rubric?",
            upvotes = 21,
            timestamp = "2 days ago",
            isSolved = true,
            replies = listOf(
                ForumReply(
                    id = "rep_4_1",
                    author = "Mrs. Naidoo (Curriculum Advisor)",
                    authorGrade = "Teacher",
                    content = "CAPS PAT Phase 3 essentials:\n1. 2 to 3 linked HTML pages with working navigation.\n2. One well-structured table with headings (th) and cell formatting.\n3. At least one image with valid alt attribute.\n4. Both ordered (<ol>) and unordered (<ul>) lists.\n5. Consistent inline or internal CSS colors.\n6. Clear citations and no broken hyperlinks!",
                    timestamp = "1 day ago",
                    isTeacherVerified = true
                )
            )
        )
    )

    val achievementBadges: List<AchievementBadge> = listOf(
        AchievementBadge(
            id = "first_step",
            title = "Curriculum Explorer",
            description = "Completed your first South African CAT curriculum module.",
            iconName = "School",
            isUnlocked = true
        ),
        AchievementBadge(
            id = "excel_master",
            title = "Spreadsheet Sensei",
            description = "Answered 10 Excel VLOOKUP and Nested IF quiz questions correctly.",
            iconName = "TableChart",
            isUnlocked = true
        ),
        AchievementBadge(
            id = "matric_ready",
            title = "Matric Ready",
            description = "Scored over 80% on a Grade 12 past paper assessment drill.",
            iconName = "Verified",
            isUnlocked = true
        ),
        AchievementBadge(
            id = "db_architect",
            title = "Access Architect",
            description = "Mastered queries, input masks, and calculated fields in Access.",
            iconName = "Storage",
            isUnlocked = false
        ),
        AchievementBadge(
            id = "forum_contributor",
            title = "Peer Support Champion",
            description = "Asked or answered a question in the learner discussion forum.",
            iconName = "Forum",
            isUnlocked = false
        ),
        AchievementBadge(
            id = "streak_fire",
            title = "Study Streak Warrior",
            description = "Maintained a 5-day continuous CAT study streak.",
            iconName = "LocalFireDepartment",
            isUnlocked = true
        )
    )
}

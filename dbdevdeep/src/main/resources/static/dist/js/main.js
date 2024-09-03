import {
    ClassicEditor,
    AccessibilityHelp,
    Alignment,
    Autoformat,
    AutoImage,
    Autosave,
    BalloonToolbar,
    BlockQuote,
    BlockToolbar,
    Bold,
    Essentials,
    FontBackgroundColor,
    FontColor,
    FontFamily,
    FontSize,
    GeneralHtmlSupport,
    Heading,
    ImageBlock,
    ImageCaption,
    ImageInline,
    ImageInsert,
    ImageInsertViaUrl,
    ImageResize,
    ImageStyle,
    ImageTextAlternative,
    ImageToolbar,
    ImageUpload,
    Indent,
    IndentBlock,
    Italic,
    Link,
    LinkImage,
    List,
    ListProperties,
    MediaEmbed,
    Mention,
    Paragraph,
    PasteFromOffice,
    SelectAll,
    SimpleUploadAdapter,
    SpecialCharacters,
    SpecialCharactersArrows,
    SpecialCharactersCurrency,
    SpecialCharactersEssentials,
    SpecialCharactersLatin,
    SpecialCharactersMathematical,
    SpecialCharactersText,
    Table,
    TableCaption,
    TableCellProperties,
    TableColumnResize,
    TableProperties,
    TableToolbar,
    TodoList,
    Underline,
    Undo
} from 'ckeditor5';

import translations from 'ckeditor5/translations/ko.js';

const editorConfig = {
    toolbar: {
        items: [
            'undo', 'redo', '|', 'selectAll', '|', 'heading', '|', 'fontSize', 'fontFamily', 'fontColor', 'fontBackgroundColor',
            '|', 'bold', 'italic', 'underline', '|', 'specialCharacters', 'link', 'insertImage', 'mediaEmbed', 'insertTable',
            'blockQuote', '|', 'alignment', '|', 'bulletedList', 'numberedList', 'todoList', 'outdent', 'indent', '|', 'accessibilityHelp'
        ],
        shouldNotGroupWhenFull: false
    },
    plugins: [
        AccessibilityHelp, Alignment, Autoformat, AutoImage, Autosave, BalloonToolbar, BlockQuote, BlockToolbar, Bold, Essentials,
        FontBackgroundColor, FontColor, FontFamily, FontSize, GeneralHtmlSupport, Heading, ImageBlock, ImageCaption, ImageInline,
        ImageInsert, ImageInsertViaUrl, ImageResize, ImageStyle, ImageTextAlternative, ImageToolbar, ImageUpload, Indent, IndentBlock,
        Italic, Link, LinkImage, List, ListProperties, MediaEmbed, Mention, Paragraph, PasteFromOffice, SelectAll, SimpleUploadAdapter,
        SpecialCharacters, SpecialCharactersArrows, SpecialCharactersCurrency, SpecialCharactersEssentials, SpecialCharactersLatin,
        SpecialCharactersMathematical, SpecialCharactersText, Table, TableCaption, TableCellProperties, TableColumnResize,
        TableProperties, TableToolbar, TodoList, Underline, Undo
    ],
    balloonToolbar: ['bold', 'italic', '|', 'link', 'insertImage', '|', 'bulletedList', 'numberedList'],
    blockToolbar: [
        'fontSize', 'fontColor', 'fontBackgroundColor', '|', 'bold', 'italic', '|', 'link', 'insertImage', 'insertTable',
        '|', 'bulletedList', 'numberedList', 'outdent', 'indent'
    ],
    fontFamily: { supportAllValues: true },
    fontSize: { options: [10, 12, 14, 'default', 18, 20, 22], supportAllValues: true },
    heading: {
        options: [
            { model: 'paragraph', title: 'Paragraph', class: 'ck-heading_paragraph' },
            { model: 'heading1', view: 'h1', title: 'Heading 1', class: 'ck-heading_heading1' },
            { model: 'heading2', view: 'h2', title: 'Heading 2', class: 'ck-heading_heading2' },
            { model: 'heading3', view: 'h3', title: 'Heading 3', class: 'ck-heading_heading3' },
            { model: 'heading4', view: 'h4', title: 'Heading 4', class: 'ck-heading_heading4' },
            { model: 'heading5', view: 'h5', title: 'Heading 5', class: 'ck-heading_heading5' },
            { model: 'heading6', view: 'h6', title: 'Heading 6', class: 'ck-heading_heading6' }
        ]
    },
    htmlSupport: {
        allow: [
            { name: /^.*$/, styles: true, attributes: true, classes: true }
        ]
    },
    image: {
        toolbar: [
            'toggleImageCaption', 'imageTextAlternative', '|', 'imageStyle:inline', 'imageStyle:wrapText', 'imageStyle:breakText', '|', 'resizeImage'
        ]
    },
    initialData: '',
    language: 'ko',
    link: {
        addTargetToExternalLinks: true,
        defaultProtocol: 'https://',
        decorators: {
            toggleDownloadable: {
                mode: 'manual',
                label: 'Downloadable',
                attributes: { download: 'file' }
            }
        }
    },
    list: {
        properties: { styles: true, startIndex: true, reversed: true }
    },
    mention: {
        feeds: [
            { marker: '@', feed: [/* 사용자 정의 맨션 피드 */] }
        ]
    },
    placeholder: '입력란',
    table: {
        contentToolbar: ['tableColumn', 'tableRow', 'mergeTableCells', 'tableProperties', 'tableCellProperties']
    },
    translations: [translations]
};

// CKEditor 인스턴스를 초기화합니다
ClassicEditor.create(document.querySelector('#editor'), editorConfig);

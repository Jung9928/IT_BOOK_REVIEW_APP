package com.jung0407.it_book_review_app.util;

import lombok.Getter;

@Getter
public enum BookSubCategory {

    // 컴퓨터공학 서브 카테고리
    COMPUTER_EDUCATION("001"),
    NETWORK_DATA_COMMUNICATION("002"),
    MICRO_PROCESSOR("003"),
    SOFTWARE_ENGINEERING("004"),
    BLOCKCHAIN("015"),
    DATA_STRUCTURE_ALGORITHM("005"),
    COMPUTATIONAL_MATHEMATICS("006"),
    INFORMATION_COMMUNICATION("007"),
    HOBBY_COMMUNICATION("008"),
    ARTIFICIAL_INTELLIGENCE("009"),
    GENERAL_OS("011"),
    GENERAL_DATABASE("012"),
    DEVELOPMENT_METHODOLOGY("013"),

    // 컴퓨터 입문/활용 서브 카테고리
    COMPUTER_FOR_ADULT("001"),
    COMPUTER_FOR_CHILD("002"),
    WINDOW_FOR_BEGINNER("004"),
    INTERNET_FOR_BEGINNER("003"),
    COMPUTER_ASSEMBLY_REPAIR("005"),

    // 모바일 프로그래밍 서브 카테고리
    IPHONE("001"),
    ANDROID("002"),
    WINDOW("003"),
    MOBILE_GAME("004"),

    // 프로그래밍 언어 서브 카테고리
    JAVA("003"),
    C("007"),
    CPP("009"),
    JAVASCRIPT_CGI("010"),
    PYTHON("004"),
    PROGRAMMING_EDUCATION("018"),
    C_SHARP("008"),
    DOT_NET("001"),
    DELPHI("002"),
    AJAX("005"),
    ASP("006"),
    JSP("011"),
    PERL("012"),
    PHP("013"),
    RUBY_ON_RAILS("014"),
    VISUAL_BASIC("015"),
    VISUAL_CPP("016"),
    XML("017"),
    PROGRAMMING_LANG_EXTRA("019"),

    // 웹사이트 서브 카테고리
    HTML_JAVASCRIPT_CSS_JQUERY("001"),
    WEB_DESIGN("002"),
    WEB_PLANNING("003"),
    UI_UX("004"),
    MAKE_BLOG_HOMEPAGE("005"),

    // OS/DB 서브 카테고리
    CLOUD_BIGDATA("009"),
    OPERATION_SYSTEM_SERVER("010"),
    LINUX("003"),
    ORACLE("007"),
    WINDOWS("001"),
    SQL_SERVER("008"),
    MAC_OS("002"),
    UNIX("004"),
    ACCESS("005"),
    MYSQL("006"),

    // 게임 서브 카테고리
    DEVELOPMENT_GAME("003"),
    MOBILE_GAME2("005"),

    // 네트워크/해킹/보안 서브 카테고리
    GENERAL_NETWORK("001"),
    TCP_IP("002"),
    SECURITY_HACKING("003"),
    ;

    private String categoryCode;

    BookSubCategory(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getBookSubCategory() {
        return categoryCode;
    }
}

package com.jung0407.it_book_review_app.util;

import lombok.Getter;

@Getter
public enum BookMainCategory {

    // 도서 메인 카테고리
    COMPUTER_ENGINEERING("001001003031"),
    COMPUTER_BEGINNER("001001003019"),
    MOBILE_PROGRAMMING("001001003023"),
    PROGRAMMING_LANGUAGE("001001003022"),
    WEBSITE("001001003020"),
    OS_DATABASE("001001003025"),
    GAME("001001003027"),
    NETWORK_HACKING_SECURITY("001001003024"),
    GRAPHIC_DESIGN_MULTIMEDIA("001001003028")
    ;

    private String categoryCode;

    BookMainCategory(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getBookMainCategory() {
        return categoryCode;
    }
}

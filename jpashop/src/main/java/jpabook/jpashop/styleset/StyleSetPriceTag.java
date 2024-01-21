package jpabook.jpashop.styleset;

/**
 * 브랜드에서 StyleSet 카테고리가 상품이 하나 밖에 없거나
 * StyleSet 카테고리에 상품이 하나 밖에 없어서
 * HIGHEST_PRICE가 LOWEST_PRICE나 BRAND_LOWEST_PRICE에
 */
public enum StyleSetPriceTag {
    LOWEST_PRICE, //StyleSetType의 최저가 상품
    BRAND_LOWEST_PRICE, //StyleSetType별 브랜드의 최저가 상품
    HIGHEST_PRICE, //StyleSetType의 최고가 상품
    NORMAL; //일반상품
}

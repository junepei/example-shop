package jpabook.jpashop.common;

import jpabook.jpashop.styleset.StyleSetType;

public class JpaShopUtils {

    public static StyleSetType getStyleSetTypeByLabel(String label) {
        for(StyleSetType styleSetType : StyleSetType.values()) {
            if(styleSetType.getLabel().equals(label)) {
                return styleSetType;
            }
        }
        return null;
    }
}

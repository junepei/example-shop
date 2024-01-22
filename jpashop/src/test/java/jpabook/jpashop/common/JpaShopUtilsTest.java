package jpabook.jpashop.common;

import jpabook.jpashop.styleset.StyleSetType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JpaShopUtilsTest {

    @DisplayName("getStyleSetTypeByLabel - 성공")
    @Test
    void testGetStyleSetTypeByLabel() {
        String styleSetTypeLabel = "상의";
        assertThat(JpaShopUtils.getStyleSetTypeByLabel(styleSetTypeLabel)).isEqualTo(StyleSetType.TOP);
    }

    @DisplayName("getStyleSetTypeByLabel - 실패")
    @Test
    void testGetStyleSetTypeByLabel_fail() {
        String styleSetTypeLabel = "파자마";
        assertThat(JpaShopUtils.getStyleSetTypeByLabel(styleSetTypeLabel)).isEqualTo(null);
    }
}

package jpabook.jpashop.styleset;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static jpabook.jpashop.styleset.QStyleSet.styleSet;


@Repository
@RequiredArgsConstructor
public class StyleSetRepositoryImpl implements StyleSetRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public List<StyleSetDTO> findLowestStyleSetDTO() {
        //QStyleSet styleSet1 = styleSet;

        return queryFactory.select(Projections.fields(
                        StyleSetDTO.class,
                        styleSet.styleSetType,
                        styleSet.brandNo,
                        styleSet.brandName,
                        styleSet.price.min()
                )).from(styleSet)
                .groupBy(styleSet.styleSetType, styleSet.brandNo, styleSet.brandName)
                .fetch();
    }
}

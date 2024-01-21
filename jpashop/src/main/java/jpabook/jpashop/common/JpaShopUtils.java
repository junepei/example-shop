package jpabook.jpashop.common;

import java.util.ArrayList;
import java.util.List;

public class JpaShopUtils {
    public static <T> List<T> copyOfRange(List<T> list, int from, int end) {
        if (from < 0 || from >= list.size() || end < 0 || end >= list.size() || from > end)
            throw new IllegalArgumentException("Collection 복사중 오류가 발생했습니다.");
        List<T> result = new ArrayList<>(end - from + 1);
        for (int i = from; i <= end; i++)
            result.add(list.get(i));
        return result;
    }
}

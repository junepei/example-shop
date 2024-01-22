package jpabook.jpashop.styleset;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class StyleSetControllerTest extends StyleSetTestData{

    @Autowired
    MockMvc mockMvc;

    @DisplayName("구현 1 테스트")
    @Test
    void testGetLowestPriceCollection() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/style-set/collections/lowest-price"))
                .andExpect(status().isOk());
    }

    @DisplayName("구현 2 테스트")
    @Test
    void testGetLowestPriceBrandCollection() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/style-set/collections/brands/lowest-price"))
                .andExpect(status().isOk());
    }

    @DisplayName("구현 3 테스트")
    @Test
    void testGetLowestPriceCategoryCollection() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/style-set/collections/categories/lowest-price").param("name", "상의"))
                .andExpect(status().isOk());
    }
}

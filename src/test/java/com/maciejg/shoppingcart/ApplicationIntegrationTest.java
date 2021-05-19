package com.maciejg.shoppingcart;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maciejg.shoppingcart.producttype.exception.ProductTypeNotFoundException;
import com.maciejg.shoppingcart.producttype.model.AmountStrategy;
import com.maciejg.shoppingcart.producttype.model.ProductTypeEntity;
import com.maciejg.shoppingcart.producttype.repository.ProductTypeRepository;
import com.maciejg.shoppingcart.shoppinglist.model.Order;
import com.maciejg.shoppingcart.shoppinglist.model.OrderRecord;
import com.maciejg.shoppingcart.shoppinglist.model.Receipt;
import com.maciejg.shoppingcart.shoppinglist.model.ReceiptRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("itest")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ApplicationIntegrationTest {

    @Autowired
    private ProductTypeRepository productTypeRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void beforeEach() {
        productTypeRepository.saveAll(Arrays.asList(
                new ProductTypeEntity(1L, "PRODUCT_1", "Product 1", new BigDecimal("3"), AmountStrategy.STANDARD),
                new ProductTypeEntity(2L, "PRODUCT_2", "Product 2", new BigDecimal("7.23"), AmountStrategy.THREE_FOR_TWO)
        ));
    }

    @Test
    void positiveJsonIntegrationTest() throws Exception {
        Order order = new Order(Arrays.asList(
                new OrderRecord("PRODUCT_1", 1),
                new OrderRecord("PRODUCT_2", 20)
        ));

        Receipt expectedResult = Receipt.builder()
                .totalAmount(new BigDecimal("104.22"))
                .records(Arrays.asList(
                        ReceiptRecord.builder().productKey("PRODUCT_1").productName("Product 1").price(new BigDecimal("3.00")).quantity(1).amount(new BigDecimal("3.00")).build(),
                        ReceiptRecord.builder().productKey("PRODUCT_2").productName("Product 2").price(new BigDecimal("7.23")).quantity(20).amount(new BigDecimal("101.22")).build()
                ))
                .build();

        mockMvc.perform(post("/order")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResult)));
    }

    @Test
    void positivePlainTextIntegrationTest() throws Exception {
        Order order = new Order(Arrays.asList(
                new OrderRecord("PRODUCT_1", 5),
                new OrderRecord("PRODUCT_2", 2)
        ));

        String expectedResult = "RECEIPT\n" +
                "\n" +
                "Product name     Qty    Price       Amount\n" +
                "\n" +
                "Product 1        5      3.00        15.00\n" +
                "Product 2        2      7.23        14.46\n" +
                "\n" +
                "Total sum: 29.46";

        mockMvc.perform(post("/order")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.TEXT_PLAIN_VALUE)
                .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResult));
    }

    @Test
    void invalidProductTypeTest() {
        Order order = new Order(Arrays.asList(
                new OrderRecord("INVALID_PRODUCT", 1),
                new OrderRecord("PRODUCT_2", 20)
        ));

        Throwable thrown = catchThrowable(() -> mockMvc.perform(post("/order")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(order)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest()));

        assertThat(thrown.getCause()).isExactlyInstanceOf(ProductTypeNotFoundException.class);
    }
}

package com.maciejg.shoppingcart.shoppinglist.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maciejg.shoppingcart.shoppinglist.model.Order;
import com.maciejg.shoppingcart.shoppinglist.model.OrderRecord;
import com.maciejg.shoppingcart.shoppinglist.model.Receipt;
import com.maciejg.shoppingcart.shoppinglist.service.ReceiptStringConverter;
import com.maciejg.shoppingcart.shoppinglist.service.ShoppingListService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ShoppingListController.class)
class ShoppingListControllerWebTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShoppingListService shoppingListService;

    @MockBean
    private ReceiptStringConverter receiptStringConverter;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void placeOrderJson_CorrectOrderProvided_ReturnReceipt() throws Exception {
        Receipt testReceipt = Receipt.builder().build();
        when(shoppingListService.placeOrder(any())).thenReturn(testReceipt);

        mockMvc.perform(post("/order")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(
                        new Order(Arrays.asList(
                                new OrderRecord("key1", 1),
                                new OrderRecord("key2", 2)
                        ))
                )))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(testReceipt)));
    }

    @Test
    void placeOrderPlainText_CorrectOrderProvided_ReturnReceipt() throws Exception {
        Receipt testReceipt = Receipt.builder().build();
        when(shoppingListService.placeOrder(any())).thenReturn(testReceipt);

        String testReceiptString = "testReceipt";
        when(receiptStringConverter.transformToString(testReceipt)).thenReturn(testReceiptString);

        mockMvc.perform(post("/order")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.TEXT_PLAIN_VALUE)
                .content(objectMapper.writeValueAsString(
                        new Order(Collections.singletonList(
                                new OrderRecord("key1", 1)
                        ))
                )))
                .andExpect(status().isOk())
                .andExpect(content().string(testReceiptString));
    }

    @Test
    void placeOrder_NoReceiptRecords_Return400() throws Exception {
        mockMvc.perform(post("/order")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(
                        new Order(Collections.emptyList())
                )))
                .andExpect(status().isBadRequest());
    }

    @Test
    void placeOrder_NonPositiveQuantity_Return400() throws Exception {
        mockMvc.perform(post("/order")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(
                        new OrderRecord("key", 0)
                )))
                .andExpect(status().isBadRequest());
    }
}

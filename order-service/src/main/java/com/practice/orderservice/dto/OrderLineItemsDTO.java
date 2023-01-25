package com.practice.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class OrderLineItemsDTO {
    private Long Id;
    private String skuNumber;
    private BigDecimal price;
    private OrderDTO orderDTO;
    private String description;
    private Integer quantity;
}

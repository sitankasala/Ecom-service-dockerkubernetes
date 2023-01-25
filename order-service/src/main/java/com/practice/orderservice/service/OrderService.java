package com.practice.orderservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.orderservice.dto.InventoryResponseDTO;
import com.practice.orderservice.dto.OrderDTO;
import com.practice.orderservice.dto.OrderLineItemsDTO;
import com.practice.orderservice.model.Order;
import com.practice.orderservice.model.OrderLineItems;
import com.practice.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;

    private final WebClient.Builder webClientBuilder;

    private final ObjectMapper objectMapper;

    public String placeOrder(OrderDTO orderDTO) {
        List<String> skuList = orderDTO.getOrderLineItemsRequestList().stream().map(OrderLineItemsDTO::getSkuNumber).collect(Collectors.toList());
        List<InventoryResponseDTO> inventoryResponse = getInventoryResponse(skuList);
        List<InventoryResponseDTO> inventoryAvlblList = inventoryResponse.stream()
                .filter(inventoryResponseDTO -> Boolean.TRUE.equals(inventoryResponseDTO.getIsPresent()))
                .toList();
        inventoryResponse.stream()
                .filter(inventoryResponseDTO -> Boolean.FALSE.equals(inventoryResponseDTO.getIsPresent()))
                .forEach(System.out::println);
        List<OrderLineItems> orderLineItems = inventoryAvlblList.stream()
                .flatMap(inventoryResponseDTO -> orderDTO.getOrderLineItemsRequestList().stream()
                        .filter(orderLineItems1 -> inventoryResponseDTO.getSkuNumber().equalsIgnoreCase(orderLineItems1.getSkuNumber())))
                .map(this::mapToEntity)
                .collect(Collectors.toList());
        if (!ObjectUtils.isEmpty(orderLineItems)) {
            Order order = new Order();
            order.setOrderNumber(UUID.randomUUID().toString());
            order.setOrderLineItems(orderLineItems);
            orderRepository.save(order);
            return order.getOrderNumber();
        } else {
            return "Item out of stock";
        }

    }

    private List<InventoryResponseDTO> getInventoryResponse(List<String> skuList) {
        InventoryResponseDTO[] inventoryResponseArray = webClientBuilder.build()
                .method(HttpMethod.GET)
                .uri("http://inventory-service:8082/api/inventory/getInventoryStatus", uriBuilder -> uriBuilder.queryParam("skuList", skuList).build())
                .retrieve()
                .bodyToMono(InventoryResponseDTO[].class)
                .block();
        assert inventoryResponseArray != null;
        List<InventoryResponseDTO> inventoryResponseDTOS = Arrays.stream(inventoryResponseArray).map(inventoryResponseDTO -> objectMapper.convertValue(inventoryResponseDTO, InventoryResponseDTO.class))
                .collect(Collectors.toList());
        return Optional.of(inventoryResponseDTOS).orElse(Collections.emptyList());
    }

    private OrderLineItems mapToEntity(OrderLineItemsDTO orderLineItemsDTO) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDTO.getPrice());
        orderLineItems.setSkuNumber(orderLineItemsDTO.getSkuNumber());
        orderLineItems.setQuantity(orderLineItemsDTO.getQuantity());
        orderLineItems.setDescription(orderLineItemsDTO.getDescription());
        return orderLineItems;
    }
}

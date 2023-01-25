package com.practice.inventoryservice.service;

import com.practice.inventoryservice.dto.InventoryResponseDTO;
import com.practice.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    public List<InventoryResponseDTO> isInventoryInStock(List<String> skuList) {
        return inventoryRepository.findAllBySkuNumberIn(skuList).stream()
                .map(inventory -> InventoryResponseDTO.builder()
                        .skuNumber(inventory.getSkuNumber())
                        .isPresent(inventory.getQuantityInStock() > 0)
                        .build())
                .collect(Collectors.toList());
    }
}

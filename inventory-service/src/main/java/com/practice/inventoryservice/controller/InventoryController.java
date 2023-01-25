package com.practice.inventoryservice.controller;

import com.practice.inventoryservice.dto.InventoryResponseDTO;
import com.practice.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/getInventoryStatus")
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponseDTO> getInventoryStatus(@RequestParam List<String> skuList) {
        return inventoryService.isInventoryInStock(skuList);
    }
}

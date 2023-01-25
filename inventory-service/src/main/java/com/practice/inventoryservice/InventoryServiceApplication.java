package com.practice.inventoryservice;

import com.practice.inventoryservice.model.Inventory;
import com.practice.inventoryservice.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
//@EnableDiscoveryClient
public class InventoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner loadData(InventoryRepository inventoryRepository) {
        return args -> {
            if (inventoryRepository.findAll().isEmpty()) {
                Inventory inventory = new Inventory();
                inventory.setSkuNumber("23123312");
                inventory.setDescription("samsung galaxy");
                inventory.setQuantityInStock(20);
                inventoryRepository.save(inventory);

                Inventory inventory1 = new Inventory();
                inventory1.setSkuNumber("7237427L");
                inventory1.setDescription("google pixel");
                inventory1.setQuantityInStock(0);
                inventoryRepository.save(inventory1);
            }
        };
    }
}

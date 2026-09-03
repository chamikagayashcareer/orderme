package com.synewis.inventory.kafka;
import com.synewis.inventory.dto.OrderCreateEventDto;
import com.synewis.inventory.service.InventoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderConsumer {

    @Autowired
    private InventoryService service;

    @KafkaListener(
            topics = "${spring.kafka.template.default-topic}",
            groupId = "${spring.kafka.consumer.group-id}"
    )

    public void consume(OrderCreateEventDto orderEventDTO) {
        log.info("Receiving order event from the topic: {}", orderEventDTO.getOrderId());

        service.reserve(orderEventDTO.getQuantity(), orderEventDTO.getProductId());
    }

}
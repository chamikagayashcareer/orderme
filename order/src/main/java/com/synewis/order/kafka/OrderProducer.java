package com.synewis.order.kafka;

import com.synewis.order.common.OrderProducerBase;
import com.synewis.order.dto.OrderCreateEventDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderProducer implements OrderProducerBase {

    @Value("${spring.kafka.template.default-topic}")
    private String topic;

    @Autowired
    private final KafkaTemplate<String, OrderCreateEventDto> kafkaTemplate;

    public OrderProducer(KafkaTemplate<String, OrderCreateEventDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void sendOrderCreate(OrderCreateEventDto event) {
        kafkaTemplate.send(topic, event.getOrderId(), event);
        log.info("Order event sent: {}", event.getOrderId());
    }
}
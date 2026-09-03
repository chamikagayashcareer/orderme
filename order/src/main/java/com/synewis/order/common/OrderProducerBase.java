package com.synewis.order.common;

import com.synewis.order.dto.OrderCreateEventDto;

public interface OrderProducerBase {
    public void sendOrderCreate(OrderCreateEventDto event);
}

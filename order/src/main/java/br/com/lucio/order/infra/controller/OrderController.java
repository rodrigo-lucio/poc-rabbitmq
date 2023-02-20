package br.com.lucio.order.infra.controller;

import br.com.lucio.order.application.dto.OrderDTO;
import br.com.lucio.order.application.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    @Autowired
    private RabbitTemplate rabbitTemplate;


    @Autowired
    private OrderService orderService;

    @Autowired
    private ApplicationEventPublisher publisher;


    @GetMapping("/{id}")
    public OrderDTO get(@PathVariable UUID id) {
        return orderService.getOrder(id);
    }

    @PostMapping
    public ResponseEntity<OrderDTO> create(@RequestBody @Valid OrderDTO dto, UriComponentsBuilder uriBuilder) {
        OrderDTO orderCreated = orderService.createOrder(dto);
        URI uri = uriBuilder.path("/order/{id}").buildAndExpand(orderCreated.getId()).toUri();
        orderCreated.getPayments().forEach(payment -> publisher.publishEvent(payment));
        return ResponseEntity.created(uri).body(orderCreated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @NotNull UUID id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }

}

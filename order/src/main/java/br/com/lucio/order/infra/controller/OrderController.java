package br.com.lucio.order.infra.controller;

import java.net.URI;
import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.lucio.order.application.service.OrderService;
import br.com.lucio.order.application.dto.OrderDTO;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @GetMapping("/{id}")
    public OrderDTO get(@PathVariable @NotNull UUID id) {
        return orderService.getOrder(id);
    }

    @PostMapping
    public ResponseEntity<OrderDTO> create(@RequestBody @Valid OrderDTO dto, UriComponentsBuilder uriBuilder) {
        OrderDTO pedidoRealizado = orderService.createOrder(dto);
        URI endereco = uriBuilder.path("/order/{id}").buildAndExpand(pedidoRealizado.getId()).toUri();
        return ResponseEntity.created(endereco).body(pedidoRealizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable @NotNull UUID id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }

}

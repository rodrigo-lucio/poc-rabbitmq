package br.com.lucio.order.infra.controller;

import br.com.lucio.order.application.dto.OrderItemDTO;
import br.com.lucio.order.application.service.OrderItemService;
import com.turkraft.springfilter.boot.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/order/{orderId}/item")
public class OrderItemController {

    @Autowired
    private OrderItemService orderItemService;

    @GetMapping("/{id}")
    public OrderItemDTO get(@PathVariable UUID id) {
        return orderItemService.getItem(id);
    }

    @GetMapping
    public Page<OrderItemDTO> getOrderItems(@PathVariable UUID orderId, @Filter Specification<OrderItemDTO> specification, @PageableDefault Pageable pageable) {
        return orderItemService.findWithFilter(orderId, specification, pageable);
    }

    @PostMapping
    public ResponseEntity<OrderItemDTO> addItem(@PathVariable UUID orderId, @RequestBody @Valid OrderItemDTO orderItemDTO, UriComponentsBuilder uriBuilder) {
        OrderItemDTO itemAdded = orderItemService.addItem(orderId, orderItemDTO);
        URI uri = uriBuilder.path("/order/{orderId}/item/{id}").buildAndExpand(orderId, itemAdded.getId()).toUri();
        return ResponseEntity.created(uri).body(itemAdded);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @NotNull UUID id) {
        orderItemService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }

}

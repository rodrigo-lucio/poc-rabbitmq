package br.com.lucio.order.infra.controller;

import br.com.lucio.order.application.dto.OrderItemDTO;
import br.com.lucio.order.application.service.OrderItemService;
import br.com.lucio.order.infra.converter.PageDTOConverter;
import br.com.lucio.order.infra.dto.PageDTO;
import com.turkraft.springfilter.boot.Filter;
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

    private final OrderItemService orderItemService;

    private final PageDTOConverter<OrderItemDTO> converter;

    public OrderItemController(OrderItemService orderItemService, PageDTOConverter<OrderItemDTO> converter) {
        this.orderItemService = orderItemService;
        this.converter = converter;
    }

    @GetMapping("/{id}")
    public OrderItemDTO get(@PathVariable UUID orderId, @PathVariable UUID id) {
        return orderItemService.getItem(id, orderId);
    }

    @GetMapping
    public PageDTO<OrderItemDTO> getOrderItems(@PathVariable UUID orderId, @Filter Specification<OrderItemDTO> specification, @PageableDefault Pageable pageable) {
        return  converter.toPageDTO(orderItemService.findWithFilter(orderId, specification, pageable));
    }

    @PostMapping
    public ResponseEntity<OrderItemDTO> addItem(@PathVariable UUID orderId, @RequestBody @Valid OrderItemDTO orderItemDTO, UriComponentsBuilder uriBuilder) {
        OrderItemDTO itemAdded = orderItemService.add(orderId, orderItemDTO);
        URI uri = uriBuilder.path("/order/{orderId}/item/{id}").buildAndExpand(orderId, itemAdded.getId()).toUri();
        return ResponseEntity.created(uri).body(itemAdded);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<OrderItemDTO> updatePatch(@PathVariable UUID orderId, @PathVariable UUID id, @RequestBody @Valid OrderItemDTO orderItemDTO){
        OrderItemDTO updatedItem = orderItemService.updatePatch(orderId, id, orderItemDTO);
        return ResponseEntity.ok(updatedItem);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @NotNull UUID id) {
        orderItemService.delete(id);
        return ResponseEntity.noContent().build();
    }

}

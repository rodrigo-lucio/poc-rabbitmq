package br.com.lucio.order.application.service;

import br.com.lucio.order.application.dto.OrderItemDTO;
import br.com.lucio.order.application.exception.ResourceNotFoundException;
import br.com.lucio.order.shared.translation.TranslationComponent;
import br.com.lucio.order.shared.translation.TranslationConstants;
import br.com.lucio.order.domain.entity.Order;
import br.com.lucio.order.domain.entity.OrderItem;
import br.com.lucio.order.domain.repository.OrderItemRepository;
import br.com.lucio.order.domain.repository.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TranslationComponent translation;

    public OrderItemDTO getItem(UUID id, UUID orderId) {
        OrderItem orderItem = findOrderItem(id, orderId);
        return modelMapper.map(orderItem, OrderItemDTO.class);
    }

    private OrderItem findOrderItem(UUID id, UUID orderId) {
        return orderItemRepository.findByIdAndOrderId(id, orderId).orElseThrow(() ->
                new ResourceNotFoundException(translation.getMessage(TranslationConstants.ORDER_ITEM_NOT_FOUND_WITH_ID_FOR_ORDER_ID, id, orderId)));
    }

    public Page<OrderItemDTO> findWithFilter(UUID orderId, Specification<OrderItemDTO> specification, Pageable pageable) {
        if(!orderRepository.existsById(orderId)) {
            throw new ResourceNotFoundException(translation.getMessage(TranslationConstants.ORDER_NOT_FOUND_WITH_ID , orderId));
        }
        Specification<OrderItemDTO> spec = Specification.where(orderId(orderId)).and(specification);
        return orderItemRepository.findAll(spec, pageable).map(order -> modelMapper.map(order, OrderItemDTO.class));
    }
    private Specification<OrderItemDTO> orderId(UUID orderId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("order").get("id"), orderId);
    }

    @Transactional
    public OrderItemDTO addItem(UUID orderId, OrderItemDTO orderItemDTO) {
        Order order = findOrder(orderId);
        OrderItem orderItem = modelMapper.map(orderItemDTO, OrderItem.class);
        orderItem.setOrder(order);
        orderItem = orderItemRepository.saveAndFlush(orderItem);
        return modelMapper.map(orderItem, OrderItemDTO.class);
    }

    private Order findOrder(UUID id) {
        return orderRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(translation.getMessage(TranslationConstants.ORDER_NOT_FOUND_WITH_ID , id)));
    }

    @Transactional
    public void deleteItem(UUID id){
        if(!orderItemRepository.existsById(id)) {
            throw new ResourceNotFoundException(translation.getMessage(TranslationConstants.ORDER_NOT_FOUND_WITH_ID , id));
        }
        orderItemRepository.deleteById(id);
        orderItemRepository.flush();
    }

}

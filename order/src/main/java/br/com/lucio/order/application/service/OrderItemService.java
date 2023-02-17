package br.com.lucio.order.application.service;

import br.com.lucio.order.application.dto.OrderItemDTO;
import br.com.lucio.order.application.exception.ResourceNotFoundException;
import br.com.lucio.order.domain.entity.Order;
import br.com.lucio.order.domain.entity.OrderItem;
import br.com.lucio.order.domain.repository.OrderItemRepository;
import br.com.lucio.order.domain.repository.OrderRepository;
import br.com.lucio.order.shared.translation.TranslationComponent;
import br.com.lucio.order.shared.translation.TranslationConstants;
import br.com.lucio.order.shared.util.Utils;
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

    public OrderItemDTO getItem(UUID orderItemId, UUID orderId) {
        OrderItem orderItem = findOrderItem(orderItemId, orderId);
        return modelMapper.map(orderItem, OrderItemDTO.class);
    }

    private OrderItem findOrderItem(UUID orderItemId, UUID orderId) {
        return orderItemRepository.findByIdAndOrderId(orderItemId, orderId).orElseThrow(() ->
                new ResourceNotFoundException(translation.getMessage(TranslationConstants.ORDER_ITEM_NOT_FOUND_WITH_ID_FOR_ORDER_ID, orderItemId, orderId)));
    }

    @Transactional
    public OrderItemDTO updatePatch(UUID orderId, UUID orderItemId, OrderItemDTO orderItemDTO) {
        OrderItem savedItem = findOrderItem(orderItemId, orderId);
        OrderItem payload = modelMapper.map(orderItemDTO, OrderItem.class);
        Utils.copyNonNullProperties(payload, savedItem);
        orderItemRepository.saveAndFlush(savedItem);
        return modelMapper.map(savedItem, OrderItemDTO.class);
    }

    @Transactional
    public void delete(UUID orderItemId){
        if(!orderItemRepository.existsById(orderItemId)) {
            throw new ResourceNotFoundException(translation.getMessage(TranslationConstants.ORDER_NOT_FOUND_WITH_ID , orderItemId));
        }
        orderItemRepository.deleteById(orderItemId);
        orderItemRepository.flush();
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
    public OrderItemDTO add(UUID orderId, OrderItemDTO orderItemDTO) {
        Order order = findOrder(orderId);
        OrderItem orderItem = modelMapper.map(orderItemDTO, OrderItem.class);
        orderItem.setOrder(order);
        orderItemRepository.saveAndFlush(orderItem);
        return modelMapper.map(orderItem, OrderItemDTO.class);
    }

    private Order findOrder(UUID orderItemId) {
        return orderRepository.findById(orderItemId).orElseThrow(() ->
                new ResourceNotFoundException(translation.getMessage(TranslationConstants.ORDER_NOT_FOUND_WITH_ID , orderItemId)));
    }


}

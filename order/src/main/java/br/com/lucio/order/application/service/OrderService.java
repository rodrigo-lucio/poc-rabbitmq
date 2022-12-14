package br.com.lucio.order.application.service;

import br.com.lucio.order.application.dto.OrderDTO;
import br.com.lucio.order.application.exception.ResourceNotFoundException;
import br.com.lucio.order.shared.translation.TranslationConstants;
import br.com.lucio.order.shared.translation.TranslationComponent;
import br.com.lucio.order.domain.entity.Order;
import br.com.lucio.order.domain.entity.Status;
import br.com.lucio.order.domain.repository.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TranslationComponent translation;

    public OrderDTO getOrder(UUID id) {
        Order order = findOrder(id);
        return modelMapper.map(order, OrderDTO.class);
    }

    private Order findOrder(UUID id) {
        return orderRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(translation.getMessage(TranslationConstants.ORDER_NOT_FOUND_WITH_ID , id)));
    }

    @Transactional
    public OrderDTO createOrder(OrderDTO orderDTO) {
        Order order = modelMapper.map(orderDTO, Order.class);
        order.setStatus(Status.REGISTERED);
        order.getItems().forEach(item -> item.setOrder(order));
        orderRepository.saveAndFlush(order);
        return modelMapper.map(order, OrderDTO.class);
    }

    @Transactional
    public void deleteOrder(UUID id){
        if(!orderRepository.existsById(id)) {
            throw new ResourceNotFoundException(translation.getMessage(TranslationConstants.ORDER_NOT_FOUND_WITH_ID , id));
        }
        orderRepository.deleteById(id);
        orderRepository.flush();
    }

}

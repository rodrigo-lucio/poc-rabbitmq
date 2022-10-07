package br.com.lucio.order.application.service;

import br.com.lucio.order.application.exception.ServiceException;
import com.fasterxml.jackson.databind.deser.std.UUIDDeserializer;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.lucio.order.application.dto.OrderDTO;
import br.com.lucio.order.domain.entity.Order;
import br.com.lucio.order.domain.entity.Status;
import br.com.lucio.order.domain.repository.OrderRepository;

import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ModelMapper modelMapper;

    public OrderDTO getOrder(UUID id) {
        Order order = findOrder(id);
        return modelMapper.map(order, OrderDTO.class);
    }
    @Transactional
    public OrderDTO createOrder(OrderDTO orderDTO) {
        Order order = modelMapper.map(orderDTO, Order.class);
        order.setStatus(Status.REGISTERED);
        Order orderSet = order;
        order.getItems().forEach(item -> item.setOrder(orderSet));
        order = orderRepository.saveAndFlush(order);
        return modelMapper.map(order, OrderDTO.class);
    }

    @Transactional
    public void deleteOrder(UUID id){
        findOrder(id);
        orderRepository.deleteById(id);
    }
    private Order findOrder(UUID id) {
        return orderRepository.findById(id).orElseThrow(() ->
                new EmptyResultDataAccessException(String.format("Order not found with id %s", id), 1));
    }

}

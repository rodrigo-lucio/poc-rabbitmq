package br.com.lucio.order.application.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.lucio.order.application.dto.OrderDTO;
import br.com.lucio.order.domain.entity.Order;
import br.com.lucio.order.domain.entity.Status;
import br.com.lucio.order.domain.repository.OrderRepository;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public OrderDTO createOrder(OrderDTO orderDTO) {
        Order order = modelMapper.map(orderDTO, Order.class);
        order.setStatus(Status.REGISTERED);
        Order finalOrder = order;
        order.getItems().forEach(item -> item.setOrder(finalOrder));
        order = orderRepository.saveAndFlush(order);
        return modelMapper.map(order, OrderDTO.class);
    }

}

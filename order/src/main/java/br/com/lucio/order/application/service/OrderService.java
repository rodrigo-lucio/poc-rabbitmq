package br.com.lucio.order.application.service;

import br.com.lucio.order.application.dto.OrderDTO;
import br.com.lucio.order.application.exception.ResourceNotFoundException;
import br.com.lucio.order.application.exception.ServiceException;
import br.com.lucio.order.domain.entity.OrderItem;
import br.com.lucio.order.domain.entity.Person;
import br.com.lucio.order.domain.repository.PersonRepository;
import br.com.lucio.order.shared.translation.TranslationConstants;
import br.com.lucio.order.shared.translation.TranslationComponent;
import br.com.lucio.order.domain.entity.Order;
import br.com.lucio.order.domain.entity.Status;
import br.com.lucio.order.domain.repository.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PersonService personService;

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
        Order order = validate(orderDTO);
        orderRepository.saveAndFlush(order);
        return modelMapper.map(order, OrderDTO.class);
    }

    private Order validate(OrderDTO orderDTO) {
        Order order =  modelMapper.map(orderDTO, Order.class);
        if(Objects.isNull(orderDTO.getPerson()) || Objects.isNull(orderDTO.getPerson().getId())) {
            throw new ResourceNotFoundException(translation.getMessage(TranslationConstants.PERSON_CANNOT_BE_NULL));
        }

        Person person = personService.findPerson(orderDTO.getPerson().getId());
        if(Boolean.FALSE.equals(person.getActive())) {
            throw new ServiceException(translation.getMessage(TranslationConstants.PERSON_IS_NOT_ACTIVE));
        }

        order.setStatus(Status.REGISTERED);
        order.setPerson(person);
        order.getItems().forEach(item -> {
            item.setOrder(order);
            item.setUnitaryValue(item.getUnitaryValue().setScale(2, RoundingMode.HALF_UP));
            item.setAmount(calculateAmount(item));
        });
        BigDecimal amountOrder = order.getItems().stream()
                .map(OrderService::calculateAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setAmount(amountOrder);
        return order;
    }

    private static BigDecimal calculateAmount(OrderItem item) {
        return item.getUnitaryValue().multiply(new BigDecimal(item.getQuantity())).setScale(2, RoundingMode.HALF_UP);
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

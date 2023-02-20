package br.com.lucio.order.application.service;

import br.com.lucio.order.application.dto.OrderDTO;
import br.com.lucio.order.application.exception.ResourceNotFoundException;
import br.com.lucio.order.application.exception.ServiceException;
import br.com.lucio.order.application.validator.PersonValidator;
import br.com.lucio.order.domain.entity.*;
import br.com.lucio.order.domain.repository.OrderRepository;
import br.com.lucio.order.shared.translation.TranslationComponent;
import br.com.lucio.order.shared.translation.TranslationConstants;
import br.com.lucio.order.shared.util.UtilsBigDecimal;
import ir.cafebabe.math.utils.BigDecimalUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final PersonValidator personValidator;

    private final ModelMapper modelMapper;

    private final TranslationComponent translation;

    public OrderService(OrderRepository orderRepository, PersonValidator personValidator, ModelMapper modelMapper, TranslationComponent translation) {
        this.orderRepository = orderRepository;
        this.personValidator = personValidator;
        this.modelMapper = modelMapper;
        this.translation = translation;
    }

    public OrderDTO getOrder(UUID id) {
        Order order = findOrder(id);
        return modelMapper.map(order, OrderDTO.class);
    }

    private Order findOrder(UUID id) {
        return orderRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(translation.getMessage(TranslationConstants.ORDER_NOT_FOUND_WITH_ID, id)));
    }

    @Transactional
    public OrderDTO createOrder(OrderDTO orderDTO) {
        Order order = setAndValidate(orderDTO);
        orderRepository.saveAndFlush(order);
        return modelMapper.map(order, OrderDTO.class);
    }

    private Order setAndValidate(OrderDTO orderDTO) {
        Order order = modelMapper.map(orderDTO, Order.class);
        Person person = personValidator.validate(orderDTO.getPerson());
        setPredefinedValues(order, person);
        validatePayment(order);
        return order;
    }

    private static void setPredefinedValues(Order order, Person person) {
        order.setStatus(Status.CREATED);
        order.setPerson(person);
        order.getItems().forEach(item -> {
            item.setOrder(order);
            item.setUnitaryValue(UtilsBigDecimal.get(item.getUnitaryValue()));
            item.setAmount(calculateAmount(item));
        });

        BigDecimal amountOrder = order.getItems().stream()
                .map(OrderService::calculateAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setAmount(amountOrder);

        order.getPayments().forEach(payment -> {
            payment.setOrder(order);
            payment.setAmount(UtilsBigDecimal.get(payment.getAmount()));
            payment.setStatus(PaymentStatus.CREATED);
        });
    }

    @Transactional
    public void recalculate(Order order) {
        // #TODO
    }
    private static BigDecimal calculateAmount(OrderItem item) {
        return UtilsBigDecimal.get(item.getUnitaryValue().multiply(new BigDecimal(item.getQuantity())));
    }

    private void validatePayment(Order order) {
        BigDecimal amountPayments = order.getPayments().stream()
                .map(payment -> UtilsBigDecimal.get(payment.getAmount()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        if (BigDecimalUtils.is(order.getAmount()).notEq(amountPayments)) {
            throw new ServiceException(translation.getMessage(
                    TranslationConstants.AMOUNT_PAYMENTS_DO_NOT_MATCH_TOTAL_AMOUNT_THE_ORDER,
                    amountPayments, order.getAmount()));
        }
    }

    @Transactional
    public void deleteOrder(UUID id) {
        if (!orderRepository.existsById(id)) {
            throw new ResourceNotFoundException(translation.getMessage(TranslationConstants.ORDER_NOT_FOUND_WITH_ID, id));
        }
        orderRepository.deleteById(id);
        orderRepository.flush();
    }

}

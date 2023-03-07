package br.com.lucio.payment.application.service;

import br.com.lucio.payment.application.dto.OrderPaymentDTO;
import br.com.lucio.payment.application.dto.PaymentProcessorDTO;
import br.com.lucio.payment.domain.entity.OrderPayment;
import br.com.lucio.payment.domain.entity.PaymentStatus;
import br.com.lucio.payment.infra.repository.OrderPaymentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentRegister {

    private final OrderPaymentRepository orderPaymentRepository;

    private final ModelMapper modelMapper;

    private final ApplicationEventPublisher publisher;

    public PaymentRegister(OrderPaymentRepository orderPaymentRepository, ModelMapper modelMapper, ApplicationEventPublisher publisher) {
        this.orderPaymentRepository = orderPaymentRepository;
        this.modelMapper = modelMapper;
        this.publisher = publisher;
    }

    @Transactional
    public void execute(OrderPaymentDTO paymentDto) {
        if (orderPaymentRepository.notExistsByIdAndStatusConfirmed(paymentDto.getId())) {
            OrderPayment payment = this.modelMapper.map(paymentDto, OrderPayment.class);
            payment.setStatus(PaymentStatus.RECEIVED);
            orderPaymentRepository.save(payment);
            publisher.publishEvent(new PaymentProcessorDTO(payment.getId()));
        }
    }

}

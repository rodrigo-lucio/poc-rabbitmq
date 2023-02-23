package br.com.lucio.payment.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class PaymentProcessorDTO {

    private UUID id;

}

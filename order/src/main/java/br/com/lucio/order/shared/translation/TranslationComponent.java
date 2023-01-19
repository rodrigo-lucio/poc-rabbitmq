package br.com.lucio.order.shared.translation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class TranslationComponent {

    @Autowired
    private MessageSource messageSource;

    public String getMessage(String key, Object... args) {
        return messageSource.getMessage(key, args, Locale.ENGLISH);
    }

}

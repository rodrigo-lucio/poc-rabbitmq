package br.com.lucio.person.domain.entity;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Objects;

@Converter
public class DocumentConverter implements AttributeConverter<Document, String> {

    @Override
    public String convertToDatabaseColumn(Document document) {
        return Objects.isNull(document) ? null : document.toString();
    }

    @Override
    public Document convertToEntityAttribute(String documentNumber) {
        return Objects.isNull(documentNumber) ? null : Document.of(documentNumber);
    }
}

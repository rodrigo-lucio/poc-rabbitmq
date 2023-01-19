package br.com.lucio.order.infra.converter;


import br.com.lucio.order.infra.dto.PageDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class PageDTOConverter<T> {

    public PageDTO<T> toPageDTO(Page<T> page) {
        PageDTO<T> pageDTO = new PageDTO<>();
        pageDTO.setContent(page.getContent());
        pageDTO.setTotalElements(page.getTotalElements());
        pageDTO.setTotalPages(page.getTotalPages());
        return pageDTO;
    }
}

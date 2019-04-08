package app.mappers;

import app.dtos.SneakerDTO;
import app.entities.Sneaker;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class SneakerMapper {

    private ModelMapper mapper = new ModelMapper();

    public Sneaker toFullEntity(SneakerDTO dto) {
        return mapper.map(dto, Sneaker.class);
    }

    public SneakerDTO toFullDTO(Sneaker customer) {
        return mapper.map(customer, SneakerDTO.class);
    }
}

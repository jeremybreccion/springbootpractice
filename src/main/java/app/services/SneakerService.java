package app.services;

import app.dtos.SneakerDTO;
import app.entities.Sneaker;
import app.interfaces.SneakerRepository;
import app.mappers.SneakerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SneakerService {
    @Autowired
    private SneakerRepository sneakerRepository;

    @Autowired
    private SneakerMapper mapper;

    public void saveSneaker(SneakerDTO dto) {
        sneakerRepository.save(mapper.toFullEntity(dto));
    }

    public List<SneakerDTO> getAllSneakers() {
        return convertToDTOList(sneakerRepository.findAll());
    }

    private List<SneakerDTO> convertToDTOList(List<Sneaker> sneakerList) {
        return sneakerList.stream()
                .map(sneaker -> mapper.toFullDTO(sneaker))
                .collect(Collectors.toList());
    }
}

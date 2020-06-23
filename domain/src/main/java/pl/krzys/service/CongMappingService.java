package pl.krzys.service;

import io.vavr.collection.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.krzys.dto.CongMappingDTO;
import pl.krzys.exception.ResourceNotFoundException;
import pl.krzys.mapper.CongMappingMapper;
import pl.krzys.model.CongMapping;
import pl.krzys.repository.CongMappingRepository;

import java.util.Optional;

@Transactional
@Service("congMappingService")
public class CongMappingService {

    CongMappingRepository congMappingRepository;
    CongMappingMapper congMappingMapper;

    public CongMappingService(CongMappingRepository congMappingRepository, CongMappingMapper congMappingMapper) {
        this.congMappingRepository = congMappingRepository;
        this.congMappingMapper = congMappingMapper;
    }

    public List<CongMappingDTO> getAllCongMapping() {
        return List.ofAll(this.congMappingRepository.findAll()).map(CongMappingDTO::new);
    }

    public CongMappingDTO getCongMappingById(Long id) {
        Optional<CongMapping> congMapping = this.congMappingRepository.findById(id);
        return congMapping.map( c-> congMappingMapper.congMappingToCongMappingDTO(c)).orElseThrow(
                ResourceNotFoundException::new
        );
    }

    public CongMappingDTO createNew(CongMappingDTO newCongMappingDTO) {
        CongMapping newCongMapping;
        newCongMapping = congMappingMapper.congMappingDTOToCongMapping(newCongMappingDTO);
        congMappingRepository.save(newCongMapping);
        return congMappingMapper.congMappingToCongMappingDTO(newCongMapping);
    }

    public Optional<CongMappingDTO> update(CongMappingDTO updateCongMappingDTO) {
        return Optional.of(this.congMappingRepository
                .findById(updateCongMappingDTO.getId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(congMapping -> {
                    congMapping = congMappingMapper.congMappingDTOToCongMapping(updateCongMappingDTO);
                    congMappingRepository.save(congMapping);
                    return congMapping;
                })
                .map(CongMappingDTO::new);
    }

    public void delete(Long congMappingId) {
        if(!congMappingRepository.findById(congMappingId).isPresent()){
            throw new ResourceNotFoundException();
        } else {
            this.congMappingRepository.deleteById(congMappingId);
        }
    }
}

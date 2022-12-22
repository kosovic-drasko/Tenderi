package tender.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tender.domain.ViewPrvorangirani;
import tender.repository.ViewPrvorangiraniRepository;
import tender.service.dto.ViewPrvorangiraniDTO;
import tender.service.mapper.ViewPrvorangiraniMapper;

/**
 * Service Implementation for managing {@link ViewPrvorangirani}.
 */
@Service
@Transactional
public class ViewPrvorangiraniService {

    private final Logger log = LoggerFactory.getLogger(ViewPrvorangiraniService.class);

    private final ViewPrvorangiraniRepository viewPrvorangiraniRepository;

    private final ViewPrvorangiraniMapper viewPrvorangiraniMapper;

    public ViewPrvorangiraniService(
        ViewPrvorangiraniRepository viewPrvorangiraniRepository,
        ViewPrvorangiraniMapper viewPrvorangiraniMapper
    ) {
        this.viewPrvorangiraniRepository = viewPrvorangiraniRepository;
        this.viewPrvorangiraniMapper = viewPrvorangiraniMapper;
    }

    /**
     * Save a viewPrvorangirani.
     *
     * @param viewPrvorangiraniDTO the entity to save.
     * @return the persisted entity.
     */
    public ViewPrvorangiraniDTO save(ViewPrvorangiraniDTO viewPrvorangiraniDTO) {
        log.debug("Request to save ViewPrvorangirani : {}", viewPrvorangiraniDTO);
        ViewPrvorangirani viewPrvorangirani = viewPrvorangiraniMapper.toEntity(viewPrvorangiraniDTO);
        viewPrvorangirani = viewPrvorangiraniRepository.save(viewPrvorangirani);
        return viewPrvorangiraniMapper.toDto(viewPrvorangirani);
    }

    /**
     * Update a viewPrvorangirani.
     *
     * @param viewPrvorangiraniDTO the entity to save.
     * @return the persisted entity.
     */
    public ViewPrvorangiraniDTO update(ViewPrvorangiraniDTO viewPrvorangiraniDTO) {
        log.debug("Request to update ViewPrvorangirani : {}", viewPrvorangiraniDTO);
        ViewPrvorangirani viewPrvorangirani = viewPrvorangiraniMapper.toEntity(viewPrvorangiraniDTO);
        viewPrvorangirani = viewPrvorangiraniRepository.save(viewPrvorangirani);
        return viewPrvorangiraniMapper.toDto(viewPrvorangirani);
    }

    /**
     * Partially update a viewPrvorangirani.
     *
     * @param viewPrvorangiraniDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ViewPrvorangiraniDTO> partialUpdate(ViewPrvorangiraniDTO viewPrvorangiraniDTO) {
        log.debug("Request to partially update ViewPrvorangirani : {}", viewPrvorangiraniDTO);

        return viewPrvorangiraniRepository
            .findById(viewPrvorangiraniDTO.getId())
            .map(existingViewPrvorangirani -> {
                viewPrvorangiraniMapper.partialUpdate(existingViewPrvorangirani, viewPrvorangiraniDTO);

                return existingViewPrvorangirani;
            })
            .map(viewPrvorangiraniRepository::save)
            .map(viewPrvorangiraniMapper::toDto);
    }

    /**
     * Get all the viewPrvorangiranis.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ViewPrvorangiraniDTO> findAll() {
        log.debug("Request to get all ViewPrvorangiranis");
        return viewPrvorangiraniRepository
            .findAll()
            .stream()
            .map(viewPrvorangiraniMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one viewPrvorangirani by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ViewPrvorangiraniDTO> findOne(Long id) {
        log.debug("Request to get ViewPrvorangirani : {}", id);
        return viewPrvorangiraniRepository.findById(id).map(viewPrvorangiraniMapper::toDto);
    }

    /**
     * Delete the viewPrvorangirani by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ViewPrvorangirani : {}", id);
        viewPrvorangiraniRepository.deleteById(id);
    }
}

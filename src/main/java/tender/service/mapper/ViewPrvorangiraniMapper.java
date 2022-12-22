package tender.service.mapper;

import org.mapstruct.*;
import tender.domain.ViewPrvorangirani;
import tender.service.dto.ViewPrvorangiraniDTO;

/**
 * Mapper for the entity {@link ViewPrvorangirani} and its DTO {@link ViewPrvorangiraniDTO}.
 */
@Mapper(componentModel = "spring")
public interface ViewPrvorangiraniMapper extends EntityMapper<ViewPrvorangiraniDTO, ViewPrvorangirani> {}

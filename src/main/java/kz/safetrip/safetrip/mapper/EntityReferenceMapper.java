package kz.safetrip.safetrip.mapper;

import kz.safetrip.safetrip.model.entity.Place;
import kz.safetrip.safetrip.model.entity.Tour;
import kz.safetrip.safetrip.model.entity.User;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
public class EntityReferenceMapper {

    @Named("userFromId")
    public User userFromId(Long id) {
        if (id == null) {
            return null;
        }
        User entity = new User();
        entity.setId(id);
        return entity;
    }

    @Named("tourFromId")
    public Tour tourFromId(Long id) {
        if (id == null) {
            return null;
        }
        Tour entity = new Tour();
        entity.setId(id);
        return entity;
    }

    @Named("placeFromId")
    public Place placeFromId(Long id) {
        if (id == null) {
            return null;
        }
        Place entity = new Place();
        entity.setId(id);
        return entity;
    }
}

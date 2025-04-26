package be.freedombox.backend.service;

import be.freedombox.backend.dto.DishDTO;
import be.freedombox.backend.request.DishRequest;

import java.util.List;

public interface IDishService {
    List<DishDTO> all();
    DishDTO create(DishRequest dishrequest);
    List<DishDTO> getById(Long id);
}

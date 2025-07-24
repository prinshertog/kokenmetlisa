package be.freedombox.backend.service;

import be.freedombox.backend.dto.DishDTO;
import be.freedombox.backend.request.DishRequest;
import be.freedombox.backend.request.DishUpdateRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IDishService {
    List<DishDTO> all();
    void create(DishRequest dishrequest, MultipartFile file);
    List<DishDTO> getById(Long id);
    void delete(Long id);
    List<DishDTO> getByCategory(String category);

    void update(DishUpdateRequest dishUpdateRequest, MultipartFile file);
}

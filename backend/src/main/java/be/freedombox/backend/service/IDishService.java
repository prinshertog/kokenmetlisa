package be.freedombox.backend.service;

import be.freedombox.backend.dto.DishDTO;
import be.freedombox.backend.request.DishRequest;
import be.freedombox.backend.request.DishUpdateRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IDishService {
    void create(DishRequest dishrequest, MultipartFile file);
    void delete(Long id);
    void update(DishUpdateRequest dishUpdateRequest, MultipartFile file);
    List<DishDTO> getByCategory(String category);
    List<DishDTO> getById(Long id);
    List<DishDTO> all();
}

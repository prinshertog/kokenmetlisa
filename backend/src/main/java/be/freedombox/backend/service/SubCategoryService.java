package be.freedombox.backend.service;

import be.freedombox.backend.domain.SubCategory;
import be.freedombox.backend.dto.SubCategoryDTO;
import be.freedombox.backend.exception.ObjectAlreadyExistsException;
import be.freedombox.backend.repository.SubCategoryRepository;
import be.freedombox.backend.request.SubCategoryRequest;
import be.freedombox.backend.tools.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubCategoryService implements ISubCategoryService {

    private final SubCategoryRepository subCategoryRepository;

    @Autowired
    public SubCategoryService(SubCategoryRepository subCategoryRepository) {
        this.subCategoryRepository = subCategoryRepository;
    }

    @Override
    public SubCategoryDTO create(SubCategoryRequest subCategoryRequest) {
        SubCategory existingSubCategory = subCategoryRepository.findBySubCategory(subCategoryRequest.getSubCategory());
        if (existingSubCategory != null) {
            throw new ObjectAlreadyExistsException("The sub category " + subCategoryRequest.getSubCategory() + " already exists");
        }
        SubCategory subCategory = new SubCategory(subCategoryRequest.getSubCategory());
        return Mapper.toSubCategoryDTO(subCategoryRepository.save(subCategory));
    }

    @Override
    public List<SubCategoryDTO> all() {
        return subCategoryRepository.findAll().stream().map(Mapper::toSubCategoryDTO).collect(Collectors.toList());
    }
}

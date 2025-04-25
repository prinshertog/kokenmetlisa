package be.freedombox.backend.service;

import be.freedombox.backend.domain.SubCategory;
import be.freedombox.backend.dto.SubCategoryDTO;
import be.freedombox.backend.request.SubCategoryRequest;

import java.util.List;

public interface ISubCategoryService {
    List<SubCategoryDTO> all();
    SubCategoryDTO create(SubCategoryRequest subCategoryRequest);
}

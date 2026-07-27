package be.freedombox.backend.dto;

import be.freedombox.backend.domain.Category;
import lombok.*;

import java.util.Set;

@Builder
@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class InspirationDishDTO {
    private Long id;

    private String title;

    private String shortDescription;

    private String imageUrl;

    private Set<Category> categories;

    private String sourceName;

    private String externalUrl;
}
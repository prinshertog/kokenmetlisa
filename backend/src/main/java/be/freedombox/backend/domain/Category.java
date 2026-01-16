package be.freedombox.backend.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Table(name = "Categories")
public class Category {
    @Id
    @NotBlank(message = "Category is required")
    @Column(name = "category")
    private String name;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Category parentCategory;
    private int position;

    public Category(String name, Category parentCategory) {
        this.name = name;
        this.parentCategory = parentCategory;
    }
}

package be.freedombox.backend.domain;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Dishes")
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Valid
    @NotBlank(message = "Dish name is required")
    private String name;
    @Valid
    @NotBlank(message = "Description is required")
    @Column(length = 10000)
    private String description;
    @Valid
    @ManyToMany
    @NotNull
    private List<Category> categories;
    @Valid
    private String imageName;

    public Dish(String name, String description, List<Category> categories, String imageName) {
        this.name = name;
        this.description = description;
        this.categories = categories;
        this.imageName = imageName;
    }
}

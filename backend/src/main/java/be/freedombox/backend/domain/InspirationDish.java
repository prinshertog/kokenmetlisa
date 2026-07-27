package be.freedombox.backend.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Table(name = "inspiration_dishes")
public class InspirationDish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String title;

    @Size(max = 500)
    private String shortDescription;

    private String imageUrl;

    @ManyToMany
    @JoinTable(
            name = "inspiration_dishes_categories",
            joinColumns = @JoinColumn(name = "inspiration_dish_id"),
            inverseJoinColumns = @JoinColumn(name = "category_name")
    )
    private Set<Category> categories;

    @NotBlank
    private String sourceName;

    @NotBlank
    private String externalUrl;

    public InspirationDish(
            String title,
            String shortDescription,
            String imageUrl,
            Set<Category> categories,
            String sourceName,
            String externalUrl
    ) {
        this.title = title;
        this.shortDescription = shortDescription;
        this.imageUrl = imageUrl;
        this.categories = categories;
        this.sourceName = sourceName;
        this.externalUrl = externalUrl;
    }
}
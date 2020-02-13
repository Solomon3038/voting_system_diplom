package com.voting.system.project.to;

import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DishTo extends BaseTo {

    @NotBlank
    @Size(min = 2, max = 100)
    private String name;

    @NotNull
    @Range(min = 100)
    private Integer price;

    public DishTo(Integer id, String name, Integer price) {
        super(id);
        this.name = name;
        this.price = price;
    }
}

package com.voting.system.project.to;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RestaurantWithMenusTo extends BaseTo {

    @NotBlank
    private String name;

    @NotBlank
    private String address;

    @NonNull
    private List<MenuWithDishesTo> menus;

    public RestaurantWithMenusTo(Integer id, @NotBlank String name, @NotBlank String address, @NonNull List<MenuWithDishesTo> menus) {
        super(id);
        this.name = name;
        this.address = address;
        this.menus = menus;
    }
}

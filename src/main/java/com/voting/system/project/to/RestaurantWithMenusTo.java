package com.voting.system.project.to;

import lombok.*;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RestaurantWithMenusTo extends BaseTo {

    @NotBlank
    @Size(min = 2, max = 100)
    private String name;

    @NotBlank
    @Size(min = 2, max = 100)
    private String address;

    private List<MenuWithDishesTo> menus = new ArrayList<>();

    public RestaurantWithMenusTo(Integer id, String name, String address) {
        super(id);
        this.name = name;
        this.address = address;
    }

    public RestaurantWithMenusTo(Integer id, String name, String address, List<MenuWithDishesTo> menus) {
        this(id, name, address);
        setMenus(menus);
    }

    public void setMenus(List<MenuWithDishesTo> menus) {
        this.menus = CollectionUtils.isEmpty(menus) ? new ArrayList<>() : new ArrayList<>(menus);
    }
}

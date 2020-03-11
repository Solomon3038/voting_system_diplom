package com.voting.system.project.mapper;

import com.voting.system.project.model.MenuItem;
import com.voting.system.project.model.Restaurant;
import com.voting.system.project.to.MenuItemDishNameTo;
import com.voting.system.project.to.RestaurantTo;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.converter.ConverterFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

@Component
public class OrikaMapper extends ConfigurableMapper {

    @Override
    protected void configure(MapperFactory mapperFactory) {
        ConverterFactory converterFactory = mapperFactory.getConverterFactory();
        converterFactory.registerConverter(new DishConverter());

        mapperFactory.classMap(Restaurant.class, RestaurantTo.class)
                .field("id", "id")
                .field("name", "name")
                .field("address", "address")
                .field("menuItems", "menuItemDishNameTos")
                .register();

        mapperFactory.classMap(MenuItem.class, MenuItemDishNameTo.class)
                .field("id", "id")
                .field("date", "date")
                .field("price", "price")
                .field("dish", "dishName")
                .register();
    }
}

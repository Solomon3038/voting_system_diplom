package com.voting.system.project.util.mapper;

import com.voting.system.project.model.MenuItem;
import com.voting.system.project.model.Vote;
import com.voting.system.project.to.MenuItemDishIdTo;
import com.voting.system.project.to.MenuItemDishNameTo;
import com.voting.system.project.to.VoteTo;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.converter.ConverterFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

@Component
public class OrikaMapper extends ConfigurableMapper {

    @Override
    protected void configure(MapperFactory mapperFactory) {
        ConverterFactory converterFactory = mapperFactory.getConverterFactory();
        converterFactory.registerConverter(new DishStringConverter());
        converterFactory.registerConverter(new DishIntegerConverter());
        converterFactory.registerConverter(new UserIntegerConverter());
        converterFactory.registerConverter(new RestaurantIntegerConverter());

        mapperFactory.classMap(MenuItem.class, MenuItemDishNameTo.class)
                .field("id", "id")
                .field("date", "date")
                .field("price", "price")
                .field("dish", "dishName")
                .register();

        mapperFactory.classMap(MenuItem.class, MenuItemDishIdTo.class)
                .field("id", "id")
                .field("date", "date")
                .field("price", "price")
                .field("dish", "dishId")
                .register();

        mapperFactory.classMap(Vote.class, VoteTo.class)
                .field("id", "id")
                .field("date", "date")
                .field("user", "userId")
                .field("restaurant", "restaurantId")
                .register();
    }
}

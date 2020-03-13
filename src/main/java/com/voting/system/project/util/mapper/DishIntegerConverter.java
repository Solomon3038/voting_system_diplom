package com.voting.system.project.util.mapper;

import com.voting.system.project.model.Dish;
import ma.glasnost.orika.CustomConverter;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.metadata.Type;
import org.springframework.stereotype.Component;

@Component
public class DishIntegerConverter extends CustomConverter<Dish, Integer> {

    @Override
    public Integer convert(Dish source, Type<? extends Integer> destinationType, MappingContext mappingContext) {
        return source.getId();
    }
}
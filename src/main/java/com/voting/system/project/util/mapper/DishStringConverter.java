package com.voting.system.project.util.mapper;

import com.voting.system.project.model.Dish;
import ma.glasnost.orika.CustomConverter;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.metadata.Type;
import org.springframework.stereotype.Component;

@Component
public class DishStringConverter extends CustomConverter<Dish, String> {

    @Override
    public String convert(Dish source, Type<? extends String> destinationType, MappingContext mappingContext) {
        return source.getName();
    }
}
package com.voting.system.project.util.mapper;

import com.voting.system.project.model.Restaurant;
import ma.glasnost.orika.CustomConverter;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.metadata.Type;
import org.springframework.stereotype.Component;

@Component
public class RestaurantIntegerConverter extends CustomConverter<Restaurant, Integer> {

    @Override
    public Integer convert(Restaurant source, Type<? extends Integer> destinationType, MappingContext mappingContext) {
        return source.getId();
    }
}
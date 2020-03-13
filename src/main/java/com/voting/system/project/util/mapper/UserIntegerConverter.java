package com.voting.system.project.util.mapper;

import com.voting.system.project.model.User;
import ma.glasnost.orika.CustomConverter;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.metadata.Type;
import org.springframework.stereotype.Component;

@Component
public class UserIntegerConverter extends CustomConverter<User, Integer> {

    @Override
    public Integer convert(User source, Type<? extends Integer> destinationType, MappingContext mappingContext) {
        return source.getId();
    }
}
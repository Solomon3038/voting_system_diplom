package com.voting.system.project.web;

import com.voting.system.project.model.HasId;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

public abstract class AbstractAdminController {

    public static final String ADMIN_REST_URL = "/admin/restaurants";
    public static final String ADMIN_MENU_URL = ADMIN_REST_URL + "/{restId}/menus";

    protected final ModelMapper mapper;

    protected AbstractAdminController(ModelMapper mapper) {
        this.mapper = mapper;
    }

    protected <T extends HasId> ResponseEntity<T> getResponseEntity(T created, String url, Integer... ids) {
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(url)
                .buildAndExpand(ids).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}

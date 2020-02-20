package com.voting.system.project.util;

import com.voting.system.project.model.HasId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

public class ControllerUtil {

    public static <T extends HasId> ResponseEntity<T> getResponseEntity(T created, String url, Integer... ids) {
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(url)
                .buildAndExpand(ids).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}
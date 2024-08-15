package com.continewbie.guild_master.utils;

import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

public class UriCreator {public static URI createUri(String defaultUrl, long resourceId) {
    return UriComponentsBuilder
            .newInstance()
            .path(defaultUrl + "/{resource-id}")
            .buildAndExpand(resourceId)
            .toUri();

}

}

package com.br.paulohbs.registraion_cd.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class PagesUtils {

    private PagesUtils() {
    }

    public static Pageable createPageable(final int page, final int size) {
        return PageRequest.of(page, size);
    }
}

package org.faptic.crypto.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public enum Order {

    ASCENDING(List.of("ASCENDING", "ASC", "Ascending", "Asc", "asc")),
    DESCENDING(List.of("DESCENDING", "DESC", "Descending", "Desc", "desc")),
    NO_ORDER(List.of());

    private final List<String> acceptedValues;
}

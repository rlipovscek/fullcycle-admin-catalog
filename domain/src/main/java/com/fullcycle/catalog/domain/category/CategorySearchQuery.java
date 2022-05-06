package com.fullcycle.catalog.domain.category;

public record CategorySearchQuery(
        int page,
        int perPage,
        String terms,
        String sort,
        String direction
) {
    public static CategorySearchQuery create(
            final int aPage,
            final int aPerPage,
            final String aTerms,
            final String anSort,
            final String aDirection
    ) {
        return new CategorySearchQuery(aPage, aPerPage, aTerms, anSort, aDirection);
    }
}

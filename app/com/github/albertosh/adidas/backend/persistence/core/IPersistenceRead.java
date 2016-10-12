package com.github.albertosh.adidas.backend.persistence.core;

import com.github.albertosh.adidas.backend.persistence.utils.filter.Filter;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import rx.Observable;
import rx.Single;

public interface IPersistenceRead<T> {

    public final static Integer DEFAULT_PAGE_SIZE = 20;

    /**
     * Read value by ID
     */
    public Single<T> read(@Nonnull String id);

    /**
     * Read all values
     */
    public default Observable<T> read() {
        return read((Filter) null);
    }

    /**
     * Read all values filtered
     */
    public default Observable<T> read(@Nullable Filter filter) {
        return read(filter, null, null);
    }

    /**
     * Read all values paginated with default page size
     */
    public default Observable<T> read(@Nonnull Integer page) {
        return read(null, page, null);
    }

    /**
     * Read all values paginated
     */
    public default Observable<T> read(@Nonnull Integer page, @Nullable Integer pageSize) {
        return read(null, page,
                pageSize != null
                        ? pageSize
                        : DEFAULT_PAGE_SIZE);
    }

    /**
     * Read all values filtered and paginated
     */
    public Observable<T> read(@Nullable Filter filter, @Nullable Integer page, @Nullable Integer pageSize);

}

package com.github.albertosh.adidas.backend.persistence.utils.filter;

import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;

public class Filter<T> {

    private final Bson bsonFilter;

    public <FF extends IFilterField<T>> Filter(FF field, Object value) {
        this(FilterOperation.EQ, field, value);
    }

    public <FF extends IFilterField<T>> Filter(FilterOperation op, FF field, Object value) {
        this(op, field.getKey(), value);
    }

    private Filter(FilterOperation op, String key, Object value) {
        if (value instanceof Filter)
            this.bsonFilter = op.queryOperation(key, transformObjectIfNecessary(key, ((Filter) value).getBsonFilter()));
        else
            this.bsonFilter = op.queryOperation(key, transformObjectIfNecessary(key, value));
    }

    private Filter(FilterLogicOperation op, Bson... filters) {
        this.bsonFilter = op.applyOperation(filters);
    }

    public Bson getBsonFilter() {
        return bsonFilter;
    }

    private <FF extends IFilterField<T>> Object transformObjectIfNecessary(FF field, Object value) {
        return transformObjectIfNecessary(field.getKey(), value);
    }

    private Object transformObjectIfNecessary(String field, Object value) {
        if (value == null) {
            return null;
        } else {
            if ((field.toLowerCase().endsWith(IFilterField.ID_KEY))
                    || (field.toLowerCase().endsWith(IFilterField.ID_KEY + "s"))) {
                if (value instanceof String) {
                    return new ObjectId((String) value);
                } else if (value instanceof Iterable) {
                    Iterable asIterable = (Iterable) value;
                    Iterator iterator = asIterable.iterator();
                    Collection<ObjectId> ids = new HashSet<>();
                    while (iterator.hasNext()) {
                        Object nextId = iterator.next();
                        if (nextId instanceof String)
                            ids.add(new ObjectId((String) nextId));
                        else
                            throw new IllegalArgumentException("Object with key ID must be an String");
                    }
                    return ids;
                } else {
                    throw new IllegalArgumentException("Object with key ID must be an String");
                }
            } else {
                if (value instanceof LocalDate) {
                    return Date.from(((LocalDate) value).atStartOfDay().toInstant(ZoneOffset.UTC));
                } else {
                    return value;
                }
            }
        }
    }

    public Filter<T> and(Filter<T> filter) {
        return new Filter<T>(FilterLogicOperation.AND, this.bsonFilter, filter.getBsonFilter());
    }

    public <FF extends IFilterField<T>> Filter<T> and(FF field, Object value) {
        return and(field, FilterOperation.EQ, value);
    }

    public <FF extends IFilterField<T>> Filter<T> and(FF field, FilterOperation op, Object value) {
        return new Filter<T>(FilterLogicOperation.AND,
                this.bsonFilter,
                op.queryOperation(field, transformObjectIfNecessary(field, value)));
    }


    public Filter<T> or(Filter<T> filter) {
        return new Filter<T>(FilterLogicOperation.OR, this.bsonFilter, filter.getBsonFilter());
    }

    public <FF extends IFilterField<T>> Filter<T> or(FF field, Object value) {
        return or(field, FilterOperation.EQ, value);
    }

    public <FF extends IFilterField<T>> Filter<T> or(FF field, FilterOperation op, Object value) {
        return new Filter<T>(FilterLogicOperation.OR,
                this.bsonFilter,
                op.queryOperation(field, transformObjectIfNecessary(field, value)));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Filter<?> filter1 = (Filter<?>) o;

        return bsonFilter.equals(filter1.bsonFilter);

    }

    @Override
    public int hashCode() {
        return bsonFilter.hashCode();
    }

    @Override
    public String toString() {
        return "Filter{" +
                "bsonFilter=" + bsonFilter +
                '}';
    }
}
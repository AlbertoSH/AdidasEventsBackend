package com.github.albertosh.adidas.backend.persistence.utils.filter;

import org.bson.conversions.Bson;

import static com.mongodb.client.model.Filters.elemMatch;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.exists;
import static com.mongodb.client.model.Filters.gt;
import static com.mongodb.client.model.Filters.gte;
import static com.mongodb.client.model.Filters.in;
import static com.mongodb.client.model.Filters.lt;
import static com.mongodb.client.model.Filters.lte;
import static com.mongodb.client.model.Filters.ne;
import static com.mongodb.client.model.Filters.size;

public enum FilterOperation {
    EQ {
        @Override
        public Bson queryOperation(String field, Object value) {
            return eq(field, value);
        }
    },
    NEQ {
        @Override
        public Bson queryOperation(String field, Object value) {
            return ne(field, value);
        }
    },
    GT {
        @Override
        public Bson queryOperation(String field, Object value) {
            return gt(field, value);
        }
    },
    LT {
        @Override
        public Bson queryOperation(String field, Object value) {
            return lt(field, value);
        }
    },
    GTE {
        @Override
        public Bson queryOperation(String field, Object value) {
            return gte(field, value);
        }
    },
    LTE {
        @Override
        public Bson queryOperation(String field, Object value) {
            return lte(field, value);
        }
    },
    ELEM_MATCH {
        @Override
        public Bson queryOperation(String field, Object value) {
            if (value instanceof Filter)
                return elemMatch(field, ((Filter) value).getBsonFilter());
            else if (value instanceof Bson)
                return elemMatch(field, (Bson) value);
            else
                throw new IllegalArgumentException("'ELEM_MATCH' operator requires a filter!");
        }
    },
    IN {
        @Override
        public Bson queryOperation(String field, Object value) {
            if (value instanceof Iterable)
                return in(field, ((Iterable) value));
            else
                throw new IllegalArgumentException("'IN' operator requires an Iterable!");

        }
    },
    SIZE {
        @Override
        public Bson queryOperation(String field, Object value) {
            if (value instanceof Integer)
                return size(field, (int) value);
            else
                throw new IllegalArgumentException("'SIZE' operator requires an integer!");
        }
    }, EXISTS {
        @Override
        public Bson queryOperation(String field, Object value) {
            return exists(field);
        }
    };

    public abstract Bson queryOperation(String field, Object value);

    public <FF extends IFilterField> Bson queryOperation(FF field, Object value) {
        return queryOperation(field.getKey(), value);
    }
}

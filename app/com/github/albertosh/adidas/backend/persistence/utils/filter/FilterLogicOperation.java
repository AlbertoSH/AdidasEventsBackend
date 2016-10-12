package com.github.albertosh.adidas.backend.persistence.utils.filter;

import com.mongodb.client.model.Filters;

import org.bson.conversions.Bson;

enum FilterLogicOperation {
    AND {
        @Override
        public Bson applyOperation(Bson... filters) {
            return Filters.and(filters);
        }
    },
    OR {
        @Override
        public Bson applyOperation(Bson... filters) {
            return Filters.or(filters);
        }
    };

    public abstract Bson applyOperation(Bson... filters);
}
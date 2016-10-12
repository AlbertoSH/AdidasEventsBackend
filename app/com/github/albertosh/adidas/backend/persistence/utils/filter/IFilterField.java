package com.github.albertosh.adidas.backend.persistence.utils.filter;

public interface IFilterField<T> {

    final static String ID_KEY = "id";

    default String getKey() {
        StringBuilder builder = new StringBuilder(toString().toLowerCase());

        if (builder.toString().equals(ID_KEY))
            return "_id";

        while (builder.indexOf("_") != -1) {
            int index = builder.indexOf("_");
            builder.replace(index, index + 1, "");
            builder.replace(index, index + 1, builder.substring(index, index + 1).toUpperCase());
        }

        return builder.toString();
    }

}

package com.khlebtsov.kalories.mapper;

public interface Mapper<S, T> {
    /**
     *
     * @param source object
     * @return target object
     */
    T map(S source);
}

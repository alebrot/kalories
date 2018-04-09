package com.khlebtsov.kalories.mapper;


public interface BiMapper<S, T> extends Mapper<S, T> {
    /**
     * @param source object
     * @return target object
     */
    S mapBackward(T source);
}

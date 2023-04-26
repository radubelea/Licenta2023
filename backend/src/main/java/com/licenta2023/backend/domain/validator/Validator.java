package com.licenta2023.backend.domain.validator;

public interface Validator<T> {
    void validate(T entity) throws ValidationException;
}

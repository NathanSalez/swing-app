package org.dummy.app.service;

public interface Validator<T>
{
    /**
     * Validate the object parameter.
     * Must be called before every critical treatment.
     * @param object object to validate
     */
    void validate(T object) throws IllegalArgumentException;
}

package com.blog.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    String resourceName;
    String resourceField;
    long fieldValue;

    public ResourceNotFoundException(String rName, String rField, long rValue) {
        super(String.format("My message: %s not found with %s : %d", rName, rField, rValue));
        this.resourceName = rName;
        this.resourceField = rField;
        this.fieldValue = rValue;
    }
}
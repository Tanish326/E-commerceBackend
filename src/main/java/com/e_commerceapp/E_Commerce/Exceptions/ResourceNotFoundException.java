package com.e_commerceapp.E_Commerce.Exceptions;

public class ResourceNotFoundException extends RuntimeException {
    String resourcename;
    String fieldname;
    Long fieldid;
    String field;
    public ResourceNotFoundException(String resourcename,String field,String fieldname, Long fieldid){
       super(String.format("%s is not found with %s: %s",resourcename,field,fieldid));
        this.resourcename = resourcename;
        this.field = field;
        this.fieldname = fieldname;
        this.fieldid = fieldid;
    }
}

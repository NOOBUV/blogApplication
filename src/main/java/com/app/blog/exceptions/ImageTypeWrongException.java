package com.app.blog.exceptions;

public class ImageTypeWrongException extends RuntimeException{
    String message;
    public ImageTypeWrongException() {
        this.message = "Image type should be png or jpeg only";
    }
}

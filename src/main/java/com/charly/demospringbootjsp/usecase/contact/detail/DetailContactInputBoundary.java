package com.charly.demospringbootjsp.usecase.contact.detail;

public interface DetailContactInputBoundary {
    void execute(Long id, DetailContactOutputBoundary presenter);
}

package com.charly.demospringbootjsp.usecase.contact.list;

public interface ListContactInputBoundary {
    void execute(ListContactRequestModel requestModel, ListContactOutputBoundary presenter);
}

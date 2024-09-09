package com.charly.demospringbootjsp.presenter.contact;

import com.charly.demospringbootjsp.dto.contact.response.ListContactResponse;
import com.charly.demospringbootjsp.usecase.contact.list.ListContactOutputBoundary;
import com.charly.demospringbootjsp.usecase.contact.list.ListContactResponseModel;
import lombok.Getter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Getter
@Component
public class ListContactPresenter implements ListContactOutputBoundary {
    @Getter
    private ListContactResponse response;
    private final ModelMapper modelMapper;

    public ListContactPresenter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public void present(ListContactResponseModel responseModel) {
        this.response = modelMapper.map(responseModel, ListContactResponse.class);
    }

}

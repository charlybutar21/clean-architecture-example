package com.charly.demospringbootjsp.presenter.contact;

import com.charly.demospringbootjsp.dto.contact.response.DetailContactResponse;
import com.charly.demospringbootjsp.usecase.contact.detail.DetailContactOutputBoundary;
import com.charly.demospringbootjsp.usecase.contact.detail.DetailContactResponseModel;
import lombok.Getter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Getter
@Component
public class DetailContactPresenter implements DetailContactOutputBoundary {
    @Getter
    private DetailContactResponse response;
    private final ModelMapper modelMapper;

    public DetailContactPresenter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public void present(DetailContactResponseModel responseModel) {
        this.response = modelMapper.map(responseModel, DetailContactResponse.class);
    }
}

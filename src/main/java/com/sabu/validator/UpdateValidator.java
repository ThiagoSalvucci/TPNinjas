package com.sabu.validator;

import com.sabu.http.Update;

import static com.sabu.http.HttpUtils.BAD_REQUEST;

public class UpdateValidator {

    public void validate(Update update){
        Validator.isNotNull(update,"Update is null", BAD_REQUEST);
        Validator.isNotNull(update.getActions(),"Actions is null",BAD_REQUEST);
    }
}

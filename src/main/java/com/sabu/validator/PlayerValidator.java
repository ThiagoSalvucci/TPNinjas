package com.sabu.validator;

import com.sabu.entities.Player;

import static com.sabu.http.HttpUtils.BAD_REQUEST;

public class PlayerValidator {

    public void validate(Player player){
        Validator.isNotNull(player, "player is null", BAD_REQUEST);
        Validator.isNotNull(player.getName(), "name is null", BAD_REQUEST);
        Validator.isNotEmpty(player.getName(), "name is empty");
    }
}

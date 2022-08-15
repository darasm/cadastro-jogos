package com.dontclosethedoor.game.dto;

import lombok.Data;

@Data
public class BoardGameStatusResponseDto {

    private Long identifier;
    private String name;
    private CategoryResponseDto category;
    private String description;
    private int duration;
    private String status;
    private int quantity;

}

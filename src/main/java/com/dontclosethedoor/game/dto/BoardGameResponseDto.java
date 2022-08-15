package com.dontclosethedoor.game.dto;

import com.dontclosethedoor.game.model.BoardGame;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardGameResponseDto {

    private String name;
    private CategoryResponseDto category;
    private String description;
    private int duration;
    private String status;
    private int quantity;

    public BoardGameResponseDto(BoardGame boardGame) {
        this.name = boardGame.getName();
        this.category = CategoryResponseDto.builder()
                                .identifier(boardGame.getCategory().getIdentifier())
                                .name(boardGame.getCategory().getName())
                                .build();
        this.description = boardGame.getDescription();
        this.duration = boardGame.getDuration();
        this.status = boardGame.getStatus().toString();
        this.quantity = boardGame.getStockQuantity();
    }
}

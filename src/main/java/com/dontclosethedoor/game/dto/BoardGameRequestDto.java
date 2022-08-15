package com.dontclosethedoor.game.dto;

import com.dontclosethedoor.game.enuns.GameStatus;
import com.dontclosethedoor.game.model.BoardGame;
import com.dontclosethedoor.game.model.CategoryGame;
import com.dontclosethedoor.game.service.CategoryGameService;
import com.sun.istack.NotNull;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
public class BoardGameRequestDto {

    @NotNull
    @NotEmpty
    private String name;
    @NotNull
    @NotEmpty
    private Long categoryId;
    private String description;
    @Min(0)
    private int duration;
    @Min(0)
    private int quantity;

    public BoardGame toBoardGame(CategoryGameService categoryRepository) {

        CategoryGame categoryGame = categoryRepository.searchCategoryById(this.categoryId);

        return BoardGame.builder()
                .name(this.name)
                .category(categoryGame)
                .description(this.description)
                .duration(this.duration)
                .stockQuantity(this.quantity)
                .status(GameStatus.AVAILABLE)
                .build();
    }
}

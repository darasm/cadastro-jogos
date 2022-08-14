package com.dontclosethedoor.game.model;

import com.dontclosethedoor.game.enuns.GameStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "board-game")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardGame {

    private Long identifier;
    private String name;
    private CategoryGame category;
    private String description;
    private int duration;
    private GameStatus status;
    private int quantity;

}

package com.dontclosethedoor.game.model;

import com.dontclosethedoor.game.enuns.GameStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "board_game")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardGame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long identifier;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private CategoryGame category;

    private String description;

    private int duration;

    @Column(name = "game_status")
    @Enumerated(EnumType.STRING)
    private GameStatus status = GameStatus.AVAILABLE;

    @Column(name = "stock_quantity")
    private int stockQuantity;

}

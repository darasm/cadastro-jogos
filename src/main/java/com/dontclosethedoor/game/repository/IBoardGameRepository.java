package com.dontclosethedoor.game.repository;

import com.dontclosethedoor.game.model.BoardGame;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBoardGameRepository extends JpaRepository<BoardGame, Long> {
    BoardGame findByName(String boardName);
}

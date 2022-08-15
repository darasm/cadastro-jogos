package com.dontclosethedoor.game.service;

import com.dontclosethedoor.game.enuns.GameStatus;
import com.dontclosethedoor.game.model.BoardGame;
import com.dontclosethedoor.game.repository.IBoardGameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardGameService {

    private final IBoardGameRepository repository;
    public BoardGame addNewBoardGame(BoardGame board) {
        return repository.save(board);
    }

    public BoardGame changeBoardInformation(BoardGame boardGameChanged) {

        BoardGame boardGame = repository.findById(boardGameChanged.getIdentifier())
                .orElseThrow(IllegalArgumentException::new);

        boardGame.setName(boardGameChanged.getName());
        boardGame.setCategory(boardGameChanged.getCategory());
        boardGame.setDescription(boardGameChanged.getDescription());
        boardGame.setDuration(boardGameChanged.getDuration());
        boardGame.setStockQuantity(boardGameChanged.getStockQuantity());

        return repository.save(boardGame);
    }

    public BoardGame changeStatus(Long identifier) {

        BoardGame boardGame = repository.findById(identifier)
                .orElseThrow(IllegalArgumentException::new);

        if ((boardGame.getStatus().equals(GameStatus.AVAILABLE))) {
            boardGame.setStatus(GameStatus.UNAVAILABLE);
        } else {
            boardGame.setStatus(GameStatus.AVAILABLE);
        }

        return repository.save(boardGame);
    }

    public void deleteBoardGame(Long identifier) {

        BoardGame boardGame = repository.findById(identifier)
                .orElseThrow(IllegalArgumentException::new);

        repository.delete(boardGame);

    }

    public BoardGame findBoardGameByName(String boardName) {
        return repository.findByName(boardName);
    }

    public Page<BoardGame> searchForAllBoardGames(Pageable pageable) {
        return repository.findAll(pageable);
    }
}

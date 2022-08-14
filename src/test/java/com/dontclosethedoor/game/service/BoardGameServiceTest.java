package com.dontclosethedoor.game.service;

import com.dontclosethedoor.game.enuns.GameStatus;
import com.dontclosethedoor.game.model.BoardGame;
import com.dontclosethedoor.game.model.CategoryGame;
import com.dontclosethedoor.game.repository.IBoardGameRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class BoardGameServiceTest {

    @InjectMocks
    private BoardGameService boardGameService;
    @Mock
    private IBoardGameRepository repository;
    private BoardGame COUP;
    private CategoryGame STRATEGY_GAME;

    @BeforeEach
    public void createData(){

        STRATEGY_GAME = CategoryGame.builder()
                .identifier(12L)
                .name("Strategy")
                .build();

        COUP = BoardGame.builder()
                .identifier(12L)
                .name("Coup")
                .category(STRATEGY_GAME)
                .description("")
                .duration(15)
                .status(GameStatus.AVAILABLE)
                .quantity(10)
                .build();
    }

    @Test
    public void shouldAddNewBoardGame() {

        BoardGame catan = BoardGame.builder()
                .identifier(13L)
                .name("catan")
                .category(STRATEGY_GAME)
                .description("")
                .duration(15)
                .status(GameStatus.AVAILABLE)
                .quantity(10)
                .build();

        Mockito.when(repository.save(Mockito.any())).thenReturn(catan);

        BoardGame savedBoardGame = boardGameService.addNewBoardGame(catan);

        assertThat(savedBoardGame)
                .isEqualTo(catan);

    }

    @Test
    public void shouldModifyBoardNameAndDescription() {

        BoardGame expectBoardGame = BoardGame.builder()
                .identifier(13L)
                .name("COUP VERSION 2")
                .category(STRATEGY_GAME)
                .description("cool game")
                .duration(15)
                .status(GameStatus.AVAILABLE)
                .quantity(10)
                .build();
        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.of(COUP));

        Mockito.when(repository.save(Mockito.any())).thenReturn(expectBoardGame);

        BoardGame changedBoardGame = boardGameService.changeBoardInformation(expectBoardGame);

        assertThat(changedBoardGame).isEqualTo(expectBoardGame);
    }

    @Test
    public void shouldModifyStatusToUnavailable() {

        Long identifier = 12L;

        BoardGame expectBoardGame = BoardGame.builder()
                .identifier(12L)
                .name("Coup")
                .category(STRATEGY_GAME)
                .description("")
                .duration(15)
                .status(GameStatus.UNAVAILABLE)
                .quantity(10)
                .build();

        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.of(COUP));
        Mockito.when(repository.save(Mockito.any())).thenReturn(expectBoardGame);

        BoardGame returnedBoarGame = boardGameService.changeStatus(identifier);

        assertThat(returnedBoarGame).isEqualTo(expectBoardGame);

    }

    @Test
    public void shouldDeleteBoardGame() {

        Long identifier = 12L;

        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.of(COUP));

        try {
            boardGameService.deleteBoardGame(identifier);
        }catch (Exception e){
            Assertions.fail();
        }

    }

    @Test
    public void shouldReturnBoardGameDetailsByName() {

        String boardName = "Coup";

        Mockito.when(repository.findByName(Mockito.any())).thenReturn(COUP);

        BoardGame returnedBoardGame = boardGameService.findBoardGameByName(boardName);

        assertThat(returnedBoardGame).isEqualTo(COUP);
    }

    @Test
    public void shouldListAllBoardGames() {

        BoardGame chess = BoardGame.builder()
                .identifier(12L)
                .name("Chess")
                .category(STRATEGY_GAME)
                .description("")
                .duration(15)
                .status(GameStatus.UNAVAILABLE)
                .quantity(10)
                .build();

        BoardGame stratego = BoardGame.builder()
                .identifier(12L)
                .name("stratego")
                .category(STRATEGY_GAME)
                .description("")
                .duration(15)
                .status(GameStatus.UNAVAILABLE)
                .quantity(15)
                .build();

        Page<BoardGame> boardGamePage = new PageImpl<>(List.of(COUP, stratego, chess));

        Mockito.when(repository.findAll(Mockito.any(Pageable.class))).thenReturn(boardGamePage);

        Page<BoardGame> returnedList = boardGameService.searchForAllBoardGames(Pageable.ofSize(1));

        assertThat(returnedList).isEqualTo(boardGamePage);

    }

    @Test
    public void shouldNotFoundBoardGameByName(){

        String boardName = "AnyHouse";

        Mockito.when(repository.findByName(Mockito.any())).thenThrow(new IllegalArgumentException());

        Assertions.assertThrowsExactly(IllegalArgumentException.class,
                () -> { boardGameService.findBoardGameByName(boardName); });
    }

    @Test
    public void shouldNotFoundBoardGameToChangeInformation(){
        BoardGame stratego = BoardGame.builder()
                .identifier(12L)
                .name("stratego")
                .category(STRATEGY_GAME)
                .description("")
                .duration(15)
                .status(GameStatus.UNAVAILABLE)
                .quantity(15)
                .build();

        Assertions.assertThrowsExactly(IllegalArgumentException.class,
                () -> { boardGameService.changeBoardInformation(stratego); });
    }


    @Test
    public void shouldNotFoundBoardGameToDelete(){
        Assertions.assertThrowsExactly(IllegalArgumentException.class,
                () -> { boardGameService.deleteBoardGame(1L); });
    }

}
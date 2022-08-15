package com.dontclosethedoor.game.controller;

import com.dontclosethedoor.game.dto.BoardGameDetailsResponseDto;
import com.dontclosethedoor.game.dto.BoardGameRequestDto;
import com.dontclosethedoor.game.dto.BoardGameResponseDto;
import com.dontclosethedoor.game.dto.BoardGameStatusResponseDto;
import com.dontclosethedoor.game.model.BoardGame;
import com.dontclosethedoor.game.service.BoardGameService;
import com.dontclosethedoor.game.service.CategoryGameService;
import com.sun.istack.NotNull;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.net.URI;

@RestController
@RequestMapping("/api/v1/games/board_game")
@RequiredArgsConstructor
public class BoardGameController {

    private final BoardGameService boardGameService;
    private final CategoryGameService categoryGameService;
    private final ModelMapper modelMapper;
    @PostMapping
    @Transactional
    public ResponseEntity<BoardGameResponseDto> addNewBoardGame(@RequestBody BoardGameRequestDto request,
                                                                UriComponentsBuilder uriComponentsBuilder){

        BoardGame newBoard = request.toBoardGame(categoryGameService);
        BoardGame boardGame = boardGameService.addNewBoardGame(newBoard);

        URI uri = uriComponentsBuilder.path("/api/v1/games/board_game/{id}").buildAndExpand(boardGame.getIdentifier()).toUri();

        return ResponseEntity.created(uri).body(modelMapper.map(boardGame, BoardGameResponseDto.class));
    }

    @GetMapping
    public ResponseEntity<Page<BoardGameResponseDto>> listAllBoardGames(@PageableDefault(page = 0, direction = Sort.Direction.DESC, sort = "name")
                                                                  Pageable pageable){

        Page<BoardGame> boardGameList = boardGameService.searchForAllBoardGames(pageable);

        Page<BoardGameResponseDto> boardGameResponseDtoPage = boardGameList.map(BoardGameResponseDto::new);

        return ResponseEntity.ok(boardGameResponseDtoPage);
    }

    @GetMapping("/details")
    public ResponseEntity<BoardGameDetailsResponseDto> searchBoardGameByName(@RequestParam @NotEmpty @Min(2) String gameName){

        BoardGame returnedBoardGame = boardGameService.findBoardGameByName(gameName);

        return ResponseEntity.ok(modelMapper.map(returnedBoardGame, BoardGameDetailsResponseDto.class));
    }

    @PutMapping("/status/{id}")
    @Transactional
    public ResponseEntity<BoardGameStatusResponseDto> changeStatus(@PathVariable Long id){
        BoardGame boardGame = boardGameService.changeStatus(id);
        return ResponseEntity.ok(modelMapper.map(boardGame, BoardGameStatusResponseDto.class));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> removeBoardGame(@PathVariable Long id){

        boardGameService.deleteBoardGame(id);

        return ResponseEntity.noContent().build();
    }
}

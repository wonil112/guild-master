package com.continewbie.guild_master.game.controller;

import com.continewbie.guild_master.dto.MultiResponseDto;
import com.continewbie.guild_master.dto.SingleResponseDto;
import com.continewbie.guild_master.game.entity.Game;

import com.continewbie.guild_master.game.mapper.GameMapper;
import com.continewbie.guild_master.game.service.GameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.util.List;


@RestController
@RequestMapping("/games")
@Validated
@Slf4j
public class GameController {
    private final GameService gameService;
    private final GameMapper gameMapper;

    public GameController(GameService gameService, GameMapper gameMapper) {
        this.gameService = gameService;
        this.gameMapper = gameMapper;
    }

    @GetMapping("/{game-id}")
    public ResponseEntity getGame(@PathVariable("game-id") @Positive long gameId){
        Game findGame  = gameService.findGame(gameId);
        return new ResponseEntity<>(new SingleResponseDto<>(gameMapper.gameToGameResponseDto(findGame)),HttpStatus.OK);
    }

    @GetMapping
    ResponseEntity getGames(@Positive @RequestParam int page,
                              @Positive @RequestParam int size){
        Page<Game> pageGames = gameService.findGames(page -1 ,size);
        List<Game> findGames = pageGames.getContent();

        return new ResponseEntity<>(new MultiResponseDto<>(gameMapper
                .gamesToGamesResponses((findGames)),pageGames), HttpStatus.OK);
    }

    @DeleteMapping("/{game-id}")
    public void deleteGame(@PathVariable("game-id") @Positive long gameId){
        gameService.deleteGame(gameId);
    }

}

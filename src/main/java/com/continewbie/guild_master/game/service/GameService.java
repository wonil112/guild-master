package com.continewbie.guild_master.game.service;

import com.continewbie.guild_master.exception.BusinessLogicException;
import com.continewbie.guild_master.exception.ExceptionCode;
import com.continewbie.guild_master.game.entity.Game;
import com.continewbie.guild_master.game.repository.GameRepository;
import com.continewbie.guild_master.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;


@Service
@Transactional
public class GameService {

    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public Game findGame(long gameId){
        Game findGame = verifyFindIdGame(gameId);
        verifyActiveStatus(findGame);
        return findGame;
    }

    public Page<Game> findGames(int page, int size){
        return gameRepository.findAll(PageRequest.of(page,size, Sort.by("gameId")));
    }

    public void deleteGame(long gameId){
        Game findGame = verifyFindIdGame(gameId);
        findGame.setDeletedAt(LocalDateTime.now());
        gameRepository.save(findGame);
    }

    private Game verifyFindIdGame(long gameId){
        Optional<Game> optionalGame = gameRepository.findById(gameId);
        Game findGame  = optionalGame.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.GAME_NOT_FOUND));

        return findGame;
    }

    private void verifyActiveStatus(Game game){
        if (game.getDeletedAt() != null){
            throw new BusinessLogicException(ExceptionCode.GAME_NOT_FOUND);
        }
    }


}

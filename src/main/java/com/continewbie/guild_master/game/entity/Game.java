package com.continewbie.guild_master.game.entity;

import java.util.List;
import com.continewbie.guild_master.auditable.Auditable;
import com.continewbie.guild_master.position.entity.Position;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@Entity
public class Game extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long gameId;

    @Column(nullable = false)
    private String gameName;

    @Column(nullable = false)
    private String gameCode;

//    각 포지션에 대해 게임은 하나뿐이므로 게임이 변할 때 마다 게임에 대한 상태가 변화해야함
//    orphanRemoval 부모 entity가 삭제될 때 자식 entity도 삭제 됨
    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL ,orphanRemoval = true)
    private List<Position> positionList = new ArrayList<>();

    public Game() {}

    public Game(Long gameId, String gameName, String gameCode, LocalDateTime createdAt, LocalDateTime modifiedAt, LocalDateTime deletedAt) {
        this.gameId = gameId;
        this.gameName = gameName;
        this.gameCode = gameCode;
        setCreatedAt(createdAt);
        setModifiedAt(modifiedAt);
        setDeletedAt(deletedAt);
    }


    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getGameCode() {
        return gameCode;
    }

    public void setGameCode(String gameCode) {
        this.gameCode = gameCode;
    }
}

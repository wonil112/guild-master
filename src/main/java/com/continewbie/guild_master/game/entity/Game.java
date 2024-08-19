package com.continewbie.guild_master.game.entity;

import java.util.List;
import com.continewbie.guild_master.auditable.Auditable;
import com.continewbie.guild_master.guild.entity.Guild;
import com.continewbie.guild_master.position.entity.Position;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Game extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long gameId;

    @Column(nullable = false)
    private String gameName;

    @Column(nullable = false)
    private String gameCode;

    @OneToMany(mappedBy = "game")
    private List<Guild> guilds = new ArrayList<>();

    public void setGuild(Guild guild){
            this.guilds.add(guild);
            if(guild.getGame() != this){
                guild.setGame(this);
            }
    }

    //    각 포지션에 대해 게임은 하나뿐이므로 게임이 변할 때 마다 게임에 대한 상태가 변화해야함
//    orphanRemoval 부모 entity가 삭제될 때 자식 entity도 삭제 됨
    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Position> positionList = new ArrayList<>();

    public Game(long gameId, String gameName, String gameCode) {
        this.gameId = gameId;
        this.gameName = gameName;
        this.gameCode = gameCode;
    }
}
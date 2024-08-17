package com.continewbie.guild_master.position.entity;

import com.continewbie.guild_master.auditable.Auditable;
import com.continewbie.guild_master.game.entity.Game;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long positionId;

    @Column
    private String positionName;

    @Column
    private String gameCode;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    public Position(String positionName, String gameCode, Game game) {
        this.positionName = positionName;
        this.gameCode = gameCode;
        this.game = game;

    }
}


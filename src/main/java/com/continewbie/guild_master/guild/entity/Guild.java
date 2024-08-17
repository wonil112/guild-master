package com.continewbie.guild_master.guild.entity;

import com.continewbie.guild_master.auditable.Auditable;
import com.continewbie.guild_master.game.entity.Game;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Guild extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long guildId;

    @Column(nullable = false, length = 20, unique = true)
    private String guildName;

    @Column(nullable = false, length = 15)
    private String guildMasterName;

    @Column(nullable = false)
    private int guildTotalPopulation;

    @Column(nullable = false)
    private int guildCurrentPopulation = 1;

    @Column
    private String guildContent;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;



}

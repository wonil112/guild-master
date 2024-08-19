package com.continewbie.guild_master.guild.entity;

import com.continewbie.guild_master.auditable.Auditable;
import com.continewbie.guild_master.game.entity.Game;
import com.continewbie.guild_master.member.entity.MemberGuild;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Guild extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long guildId;

    @Column(nullable = false, length = 20)
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

    public void setGame(Game game) {
        if (!game.getGuilds().contains(this)) {
            game.getGuilds().add(this);
        }
        this.game = game;
    }

}

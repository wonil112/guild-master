package com.continewbie.guild_master.guild.entity;

import com.continewbie.guild_master.auditable.Auditable;
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

    @Column(nullable = false, length = 20)
    private String guildName;

    @Column(nullable = false, length = 15)
    private String guildMasterName;

    @Column(nullable = false)
    private int guildTotalPopulation;

    @Column(nullable = false)
    private int guildCurrentPopulation;

    @Column(nullable = false)
    private String guildContent;


}

package com.continewbie.guild_master;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class GuildMasterApplication {

	public static void main(String[] args) {
		SpringApplication.run(GuildMasterApplication.class, args);
	}
}
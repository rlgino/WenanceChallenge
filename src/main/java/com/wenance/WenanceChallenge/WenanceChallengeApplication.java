package com.wenance.WenanceChallenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WenanceChallengeApplication {

	public static void main(String[] args) {
		SpringApplication.run(WenanceChallengeApplication.class, args);
	}

}

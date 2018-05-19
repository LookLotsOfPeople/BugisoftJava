package com.beyondbell.bugisoft.RockPaperScissors;

import com.beyondbell.bugisoft.Bot;
import net.dv8tion.jda.bot.JDABot;
import net.dv8tion.jda.client.JDAClient;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.Random;
import java.util.Scanner;

public class RockPaperScissors {

	public String value;
	public String computerValue;
	public int userScore = 0;
	public int computerScore = 0;
	private final GuildMessageReceivedEvent event;

	public RockPaperScissors(GuildMessageReceivedEvent event) {
		this.event = event;
		switch (event.getMessage().getContentRaw()) {
			case "scissors" :
				value = "s";
			case "rock" :
				value = "r";
			case "paper" :
				value = "p";
		}
		calculateScores();
	}

	public void calculateScores() {
		int computerSign = (int) Math.random() * 2;
		if(computerSign == 0) {
			computerValue = "r";
		} else if(computerSign == 1) {
			computerValue = "p";
		} else if(computerSign == 2) {
			computerValue = "s";
		}

		if(value.equals("s") && computerValue.equals("r")) {
			computerScore++;
		} else if(value.equals("r") && computerValue.equals("p")) {
			computerScore++;
		} else if(value.equals("p") && computerValue.equals("s")) {
			computerScore++;
		} else if(value.equals("r") && computerValue.equals("s")) {
			userScore++;
		} else if(value.equals("p") && computerValue.equals("r")) {
			userScore++;
		} else if(value.equals("s") && computerValue.equals("p")) {
			userScore++;
		}

		event.getChannel().sendMessage("You picked " + value);
		new EmbedBuilder().setTitle("RPS")
				.addField(event.getAuthor().getName(), Integer.toString(userScore) ,true)
				.addField(event.getAuthor().getName(), Integer.toString(computerScore) ,true);
		if(computerScore == 2 || userScore == 2) {
			event.getChannel().sendMessage(computerScore > userScore ? "Computer won the game" : event.getAuthor().getName() + " won the game");
		}
	}

}
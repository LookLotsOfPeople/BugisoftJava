package com.beyondbell.bugisoft.UserInfo;

import com.beyondbell.bugisoft.TextFormatters.InputFormatter;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.util.ArrayList;

public class UserInfoQuery implements MessageCreateListener {
	@Override
	public void onMessageCreate(MessageCreateEvent event) {
		ArrayList<String> parameters = InputFormatter.stringToParameters(event);
		if (parameters.size() == 2 && parameters.get(0).equals(";") && parameters.get(1).equals("query")) {
			UserInfo user = UserInfoDatabase.findUser(event.getMessage().getAuthor());

			EmbedBuilder embed = new EmbedBuilder()
					.setTitle("User Info")
					.addField("Full Name", user.getUsername(), true)
					.addField("ID", user.getID(), true)
					.addField("Cash", user.getCash(), true)
					.addField("Level", user.getLevel(), true)
					.addField("Wins", user.getWins(), true)
					.addField("Losses", user.getLosses(), true)
					.setAuthor(event.getMessage().getAuthor());

			event.getChannel().sendMessage(embed);
		}
	}
}
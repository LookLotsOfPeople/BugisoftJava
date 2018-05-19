package com.beyondbell.bugisoft.Utilities.MessageUtilities;

import net.dv8tion.jda.core.entities.Message;

public class MessageBroadcaster {
	private final Message message;
	private final double seconds;

	public MessageBroadcaster(final Message message, double seconds) {
		this.message = message;
		this.seconds = seconds;

		if (seconds != 0) {
			Thread thread = new Thread(this::deleteMessage);
			thread.setDaemon(true);
			thread.start();
		} else {
			message.delete().queue();
		}
	}

	private void deleteMessage() {
		try {
			Thread.sleep((long) (seconds * 1000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		message.delete().queue();
	}
}
package com.beyondbell.bugisoft.Music.Commands;

import com.beyondbell.bugisoft.MessageDeleter.MessageDeleter;
import com.beyondbell.bugisoft.Music.GuildMusicManager;
import com.beyondbell.bugisoft.Music.Music;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class PlaySong {
	public PlaySong(final MessageReceivedEvent event, final String trackUrl) {
		TextChannel channel = event.getTextChannel();

		new JoinVoice(event);

		GuildMusicManager musicManager = Music.getGuildAudioPlayer(channel.getGuild());

		Music.AUDIO_PLAYER_MANAGER.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {
			AudioPlaylist playlist;
			int count = 0;

			@Override
			public void trackLoaded(AudioTrack track) {
				Message message;
				message = channel.sendMessage("Adding to queue " + track.getInfo().title).complete();
				new MessageDeleter(message, 5);

				Music.play(channel.getGuild(), musicManager, track);
			}

			@Override
			public void playlistLoaded(AudioPlaylist playlist) {
				this.playlist = playlist;

				Thread t = new Thread(this::queueSongs);
				t.run();

				Message message = channel.sendMessage("Added to Queue " + count + " songs of the playlist " + playlist.getName()).complete();
				new MessageDeleter(message, 5);
			}

			private void queueSongs() {
				for (AudioTrack audioTrack : playlist.getTracks()) {
					if (audioTrack != null) {
						Music.play(channel.getGuild(), musicManager, audioTrack);
						count++;
					}
				}
			}

			@Override
			public void noMatches() {
				Message message = channel.sendMessage("Nothing found by " + trackUrl).complete();
				new MessageDeleter(message, 5);
			}

			@Override
			public void loadFailed(FriendlyException exception) {
				Message message = channel.sendMessage("Could not play: " + exception.getMessage()).complete();
				new MessageDeleter(message, 5);
			}
		});
	}
}

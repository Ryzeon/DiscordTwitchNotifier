package me.ryzeon.notifier.commands;

import java.util.Arrays;

import com.jagrosh.jdautilities.command.SlashCommand;

import me.ryzeon.notifier.Notifier;
import me.ryzeon.notifier.twitch.TwitchProvider;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

/**
 * @Created by Ryzeon
 * @File: AddStreamerCommand.java
 * @Date: Jan 26, 2022 @ 6:41:45 PM
 * @Twitter: @Ryzeon_ 😎
 * @Github: github.ryzeon.me
 */

public class RemoveStreamerCommand extends SlashCommand {

    private final TwitchProvider twitchProvider = TwitchProvider.getInstance();

    {
        this.name = "removestreamer";
        this.help = "Remove a streamer to the list!";
        this.options = Arrays.asList(
                new OptionData(OptionType.STRING, "streamer", "Twitch username of the streamer!"));
    }

    @Override
        protected void execute(SlashCommandEvent event) {
            OptionMapping option = event.getOption("streamer");
            if(!Notifier.getInstance().getConfig().getStreamers_to_announce().contains(option.getAsString())) {
                event.reply("Streamer isn't in the list!").setEphemeral(true).queue();
                return;
            }
            twitchProvider.unlisten(option.getAsString());
            event.reply("Removed " + option.getAsString() + " from the list!").setEphemeral(true).queue();
        }
}

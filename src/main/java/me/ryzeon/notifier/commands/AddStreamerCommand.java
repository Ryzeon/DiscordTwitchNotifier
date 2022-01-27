package me.ryzeon.notifier.commands;

import java.util.Arrays;

import com.jagrosh.jdautilities.command.SlashCommand;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

/**
 * @Created by Ryzeon
 * @File: AddStreamerCommand.java
 * @Date: Jan 26, 2022 @ 6:41:45 PM
 * @Twitter: @Ryzeon_ 😎
 * @Github: github.ryzeon.me
 */

public class AddStreamerCommand extends SlashCommand {
    
        {
            this.name = "addstreamer";
            this.help = "Add a streamer to the list!";
            this.options = Arrays.asList(
                new OptionData(OptionType.STRING, "streamer", "Twitch username of the streamer!")
            );
        }
    
        @Override
        protected void execute(SlashCommandEvent event) {
            
        }
        
}

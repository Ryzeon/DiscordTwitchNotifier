package me.ryzeon.notifier.commands;

import com.jagrosh.jdautilities.command.SlashCommand;

import me.ryzeon.notifier.Notifier;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

/**
 * @Created by Ryzeon
 * @File: ReloadConfigCommand.java
 * @Date: Jan 26, 2022 @ 6:41:23 PM
 * @Twitter: @Ryzeon_ 😎
 * @Github: github.ryzeon.me
 */

public class ReloadConfigCommand extends SlashCommand {

    {
        this.name = "reloadconfig";
        this.help = "Reload the config!";
    }

    @Override
    protected void execute(SlashCommandEvent event) {
        try {
            Notifier.getInstance().reloadConfig();
            event.reply("Reloaded the config!").setEphemeral(true).queue();
        } catch (Exception e) {
            event.reply("Failed to reload the config! (view console for details)").setEphemeral(true).queue();
            e.printStackTrace();
        }
    }
    
}

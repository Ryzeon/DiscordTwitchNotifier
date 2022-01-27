package me.ryzeon.notifier.commands;

import java.util.Arrays;

import com.jagrosh.jdautilities.command.SlashCommand;

import me.ryzeon.notifier.Notifier;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

/**
 * @Created by Ryzeon
 * @File: SetAnnounceChannelCommand.java
 * @Date: Jan 26, 2022 @ 6:33:56 PM
 * @Twitter: @Ryzeon_ 😎
 * @Github: github.ryzeon.me
 */

public class SetAnnounceChannelCommand extends SlashCommand {

    {
        this.name = "setannouncechannel";
        this.help = "Set the channel to announce streams!";
        this.options = Arrays.asList(
            new OptionData(OptionType.CHANNEL, "channel", "Streams will be announced in this channel!")
        );
    }

    @Override
    protected void execute(SlashCommandEvent event) {
        OptionMapping option = event.getOption("channel");
        Notifier.getInstance().getConfig().setChannel_to_announce(option.getAsGuildChannel().getId());
        event.reply("Now announcing streams in " + option.getAsGuildChannel().getAsMention()).setEphemeral(true).queue();;
    }
}

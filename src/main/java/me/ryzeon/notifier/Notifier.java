package me.ryzeon.notifier;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

import javax.security.auth.login.LoginException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lombok.Getter;
import me.ryzeon.notifier.commands.AddStreamerCommand;
import me.ryzeon.notifier.commands.ReloadConfigCommand;
import me.ryzeon.notifier.commands.RemoveStreamerCommand;
import me.ryzeon.notifier.commands.SetAnnounceChannelCommand;
import me.ryzeon.notifier.config.Configuration;
import me.ryzeon.notifier.twitch.TwitchProvider;
import me.ryzeon.notifier.utils.json.JsonConfig;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.requests.GatewayIntent;

import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.jagrosh.jdautilities.command.SlashCommand;

/**
 * @Created by Ryzeon
 * @File: Notifier.java
 * @Date: Jan 26, 2022 @ 1:20:18 PM
 * @Twitter: @Ryzeon_ 😎
 * @Github: github.ryzeon.me
 */
@Getter
public class Notifier {

    private static Notifier instance;

    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .create();

    private Configuration config;

    private JDA jda;

    public void onStart() {
        try {
            config = JsonConfig.loadConfig(Configuration.class);
        } catch (InstantiationException | IllegalAccessException | IOException e1) {
            System.out.println("[Notifier] Failed to load config!");
            // TODO Auto-generated catch block
            e1.printStackTrace();
            return;
        }
        System.out.println("[Notifier] Config loaded!");
        JDABuilder botBuilder = JDABuilder.create(config.getBot_token(), Arrays.asList(
                new GatewayIntent[] {
                        GatewayIntent.DIRECT_MESSAGES,
                        GatewayIntent.GUILD_MESSAGES,
                        GatewayIntent.GUILD_MESSAGE_REACTIONS,
                        GatewayIntent.GUILD_EMOJIS,
                        GatewayIntent.GUILD_PRESENCES,
                        GatewayIntent.GUILD_MEMBERS }));
        botBuilder.setStatus(OnlineStatus.ONLINE);

        CommandClientBuilder builder = new CommandClientBuilder();
        builder.addSlashCommands(
                new AddStreamerCommand(),
                new RemoveStreamerCommand(),
                new ReloadConfigCommand(),
                new SetAnnounceChannelCommand());

        botBuilder.addEventListeners(builder.build());
        System.out.println("[Notifier] Commands loaded!");
        System.out.println("[Notifier] Starting bot...");
        try {
            jda = botBuilder.build();
        } catch (LoginException e) {
            System.out.println("[Notifier] Error while starting bot!");
            e.printStackTrace();
        }
        try {
            jda.awaitReady();
            TwitchProvider.getInstance().connect();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } // Stop code until JDA is ready
        System.out.println("[Notifier] Bot started!");
    }

    public void reloadConfig() throws Exception {
        this.config = JsonConfig.loadConfig(Configuration.class);
    }

    public static Notifier getInstance() {
        synchronized (Notifier.class) {
            if (instance == null) {
                instance = new Notifier();
            }
        }
        return instance;
    }
}

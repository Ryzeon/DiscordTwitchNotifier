package me.ryzeon.notifier.twitch;

import java.util.Arrays;

import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.philippheuer.events4j.api.domain.IEventSubscription;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.TwitchClientBuilder;
import com.github.twitch4j.common.events.channel.ChannelGoLiveEvent;
import com.github.twitch4j.helix.domain.UserList;

import lombok.Getter;
import me.ryzeon.notifier.Notifier;
import me.ryzeon.notifier.utils.json.JsonConfig;
import net.dv8tion.jda.api.entities.TextChannel;

/**
 * @Created by Ryzeon
 * @File: TwitchProvider.java
 * @Date: Jan 26, 2022 @ 7:16:59 PM
 * @Twitter: @Ryzeon_ 😎
 * @Github: github.ryzeon.me
 */

public class TwitchProvider {

    @Getter
    private static final TwitchProvider instance = new TwitchProvider();

    private TwitchClient client;

    public void connect() {
        client = TwitchClientBuilder.builder()
                .withEnableHelix(true)
                .withClientId(Notifier.getInstance().getConfig().getTwitch_client_id())
                .withTimeout(10000)
                .withClientSecret(Notifier.getInstance().getConfig().getTwitch_client_secret())
                .build();

        System.out.println("[Twitch] Connected!");
        client.getEventManager().onEvent(ChannelGoLiveEvent.class, event -> {
            TextChannel channel = Notifier.getInstance().getJda().getTextChannelById(Notifier.getInstance().getConfig().getChannel_to_announce());
            String message = Notifier.getInstance().getConfig().getMessage_to_announce()
            .replace("{$title}", event.getTitle())
            .replace("{$link}", "https://twitch.tv/" + event.getChannel().getName())
            .replace("{$user}", event.getChannel().getName())
            ;
            channel.sendMessage(message).queue();
        });
        new Thread(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println("[Twitch] Fetching streamers...");
            Notifier.getInstance().getConfig().getStreamers_to_announce().forEach(streamer -> {
                listen(streamer);
            });
        }).start();
        
    }

    public void listen(String streamer) {
        System.out.println("LISTENING TO " + streamer);
        client.getClientHelper().enableStreamEventListener(streamer);
        if (!Notifier.getInstance().getConfig().getStreamers_to_announce().contains(streamer)) {
            Notifier.getInstance().getConfig().getStreamers_to_announce().add(streamer);
            JsonConfig.save(Notifier.getInstance().getConfig());
        }
    }

    public void unlisten(String streamer) {
        System.out.println("UNLISTENING TO " + streamer);
        client.getClientHelper().disableStreamEventListener(streamer);
        if (Notifier.getInstance().getConfig().getStreamers_to_announce().contains(streamer)) {
            Notifier.getInstance().getConfig().getStreamers_to_announce().remove(streamer);
            JsonConfig.save(Notifier.getInstance().getConfig());
        }
    }

    public boolean checkIfStreamerExists(String streamer) {
        UserList list = client.getHelix().getUsers(null, null, Arrays.asList(streamer)).execute();
        return list.getUsers().size() > 0;

    }
}

package me.ryzeon.notifier.config;

import java.util.Arrays;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Created by Ryzeon
 * @File: Configuration.java
 * @Date: Jan 26, 2022 @ 4:21:23 PM
 * @Twitter: @Ryzeon_ 😎
 * @Github: github.ryzeon.me
 */
@Setter
@Getter
@ToString
public class Configuration {
    
    private String bot_token = "HERE_IS_YOUR_BOT_TOKEN";

    private String twitch_client_id = "HERE_IS_YOUR_TWITCH_CLIENT_ID";
    private String twitch_client_secret = "HERE_IS_YOUR_TWITCH_CLIENT_SECRET";

    private String channel_to_announce = "HERE_IS_YOUR_CHANNEL_TO_ANNOUNCE";
    private String message_to_announce = "@everyone {$user} is now live on Twitch! {$link}";
    
    private List<String> streamers_to_announce = Arrays.asList("ryzeon_");
}

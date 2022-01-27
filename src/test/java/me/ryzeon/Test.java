package me.ryzeon;

import com.google.gson.Gson;
import com.google.gson.JsonParser;

import me.ryzeon.notifier.Notifier;
import me.ryzeon.notifier.config.Configuration;
import me.ryzeon.notifier.utils.json.JsonConfig;

/**
 * @Created by Ryzeon
 * @File: Test.java
 * @Date: Jan 26, 2022 @ 4:57:52 PM
 * @Twitter: @Ryzeon_ 😎
 * @Github: github.ryzeon.me
 */

public class Test {
    
    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
      
        Configuration con = JsonConfig.loadConfig(Configuration.class);
        System.out.println(con);
        con.setBot_token("gaaxd");
        con.setChannel_to_announce("1939393");
        JsonConfig.save(con);
    }
}

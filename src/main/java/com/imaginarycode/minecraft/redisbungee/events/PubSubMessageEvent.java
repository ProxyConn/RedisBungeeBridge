package com.imaginarycode.minecraft.redisbungee.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.md_5.bungee.api.plugin.Event;

/**
 * Created by portalBlock on 9/16/2015.
 */
@AllArgsConstructor
public class PubSubMessageEvent extends Event {

    @Getter
    private final String channel, message;

}

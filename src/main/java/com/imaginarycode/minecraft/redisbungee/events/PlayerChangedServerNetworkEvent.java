package com.imaginarycode.minecraft.redisbungee.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.md_5.bungee.api.plugin.Event;

import java.util.UUID;

/**
 * Created by portalBlock on 9/16/2015.
 */
@AllArgsConstructor
public class PlayerChangedServerNetworkEvent extends Event {

    @Getter
    private final UUID uuid;

    @Getter
    private final String previousServer, server;

}

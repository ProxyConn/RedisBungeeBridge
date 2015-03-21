/*
 * Copyright (c) 2014 portalBlock. This work is provided AS-IS without any warranty.
 * You must provide a link back to the original project and clearly point out any changes made to this project.
 * This license must be included in all project files.
 * Any changes merged with this project are property of the copyright holder but may include the author's name.
 */
package net.portalblock.rbbridge;

import com.google.common.base.Joiner;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.imaginarycode.minecraft.redisbungee.RedisBungee;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.*;

/**
 * Created by portalBlock on 11/29/2014.
 */
public class MessageChannelListener implements Listener {

    private RedisBungeeBridge plugin;

    public MessageChannelListener(RedisBungeeBridge plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onPluginMessage(final PluginMessageEvent event) {
        if (event.getTag().equals("RedisBungee") && event.getSender() instanceof Server) {
            final byte[] data = Arrays.copyOf(event.getData(), event.getData().length);
            plugin.getProxy().getScheduler().runAsync(plugin, new Runnable() {
                @Override
                public void run() {
                    ByteArrayDataInput in = ByteStreams.newDataInput(data);

                    String subchannel = in.readUTF();
                    ByteArrayDataOutput out = ByteStreams.newDataOutput();
                    String type;

                    switch (subchannel) {
                        case "PlayerList":
                            out.writeUTF("PlayerList");
                            Set<UUID> original = Collections.emptySet();
                            type = in.readUTF();
                            if (type.equals("ALL")) {
                                out.writeUTF("ALL");
                                original = RedisBungee.getApi().getPlayersOnline();
                            } else {
                                try {
                                    original = RedisBungee.getApi().getPlayersOnServer(type);
                                } catch (IllegalArgumentException ignored) {
                                }
                            }
                            Set<String> players = new HashSet<String>();
                            for (UUID uuid : original)
                                players.add(RedisBungee.getApi().getNameFromUuid(uuid, false));
                            out.writeUTF(Joiner.on(',').join(players));
                            break;
                        case "PlayerCount":
                            out.writeUTF("PlayerCount");
                            type = in.readUTF();
                            if (type.equals("ALL")) {
                                out.writeUTF("ALL");
                                out.writeInt(RedisBungee.getApi().getPlayerCount());
                            } else {
                                out.writeUTF(type);
                                try {
                                    out.writeInt(RedisBungee.getApi().getPlayersOnServer(type).size());
                                } catch (IllegalArgumentException e) {
                                    out.writeInt(0);
                                }
                            }
                            break;
                        case "LastOnline":
                            String user = in.readUTF();
                            out.writeUTF("LastOnline");
                            out.writeUTF(user);
                            out.writeLong(RedisBungee.getApi().getLastOnline(RedisBungee.getApi().getUuidFromName(user, true)));
                            break;
                        default:
                            break;
                    }

                    ((Server) event.getSender()).sendData("RedisBungee", out.toByteArray());
                }
            });
        }
    }

}

/*
 * Copyright (c) 2014 portalBlock. This work is provided AS-IS without any warranty.
 * You must provide a link back to the original project and clearly point out any changes made to this project.
 * This license must be included in all project files.
 * Any changes merged with this project are property of the copyright holder but may include the author's name.
 */
package com.imaginarycode.minecraft.redisbungee;

import com.google.common.collect.*;
import lombok.NonNull;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.portalblock.pc.publicapi.API;
import net.portalblock.pc.publicapi.APIAccess;
import net.portalblock.pc.publicapi.NetworkPlayer;
import net.portalblock.rbbridge.RedisBungeeBridge;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.util.*;

/**
 * Created by portalBlock on 11/29/2014.
 */
public class RedisBungeeAPI {

    private final API api = APIAccess.getApi();
    private final int INDEX = 2;

    private final boolean LOG;

    RedisBungeeAPI() {
        LOG = Boolean.valueOf(System.getProperty("rbb.log", "false"));
    }

    private void printWarn(StackTraceElement s, String m, boolean force){
        if(s.getClassName().startsWith("net.portalblock")) return;
        if(LOG || force)
            RedisBungeeBridge.getRBBLogger().warning(s.getClassName() + "#" + s.getMethodName() + "(" + s.getLineNumber() + ") accessed RedisBungee method " + m);
    }

    private void printWarn(StackTraceElement s, String m){
        printWarn(s, m, false);
    }

    public final int getPlayerCount() {
        return api.getTotalOnlinePlayers();
    }

    public final long getLastOnline(@NonNull UUID player) {
        throw new RuntimeException("Non translated API method getLastOnline was called!");
    }

    public final ServerInfo getServerFor(@NonNull UUID player) {
        StackTraceElement s = Thread.currentThread().getStackTrace()[INDEX];
        printWarn(s, "getServerFor(UUID)");
        NetworkPlayer p = api.getPlayerByUUID(player);
        if(p == null) return null;
        for(Map.Entry<String, ServerInfo> entry : ProxyServer.getInstance().getServers().entrySet()){
            if(entry.getValue().getName().equals(p.getServer()))
                return entry.getValue();
        }
        return null;
    }

    public final Set<UUID> getPlayersOnline() {
        StackTraceElement s = Thread.currentThread().getStackTrace()[INDEX];
        printWarn(s, "getPlayersOnline()");
        Set<UUID> uuids = new HashSet<>();
        for(NetworkPlayer player : api.getAllPlayers()){
            uuids.add(RedisBungeeBridge.stringToUUID(player.getUuid()));
        }
        return uuids;
    }

    public final Collection<String> getHumanPlayersOnline() {
        StackTraceElement s = Thread.currentThread().getStackTrace()[INDEX];
        printWarn(s, "getHumanPlayersOnline()");
        LinkedList<String> names = new LinkedList<String>();
        for(NetworkPlayer player : api.getAllPlayers()){
            names.add(player.getName());
        }
        return names;
    }

    public final Multimap<String, UUID> getServerToPlayers() {
        StackTraceElement s = Thread.currentThread().getStackTrace()[INDEX];
        printWarn(s, "getServerToPlayers()");
        ImmutableMultimap.Builder<String, UUID> players = ImmutableMultimap.builder();
        for(NetworkPlayer player : api.getAllPlayers()){
            if(player.getServer().equals("Connecting")) continue;
            players.put(player.getServer(), RedisBungeeBridge.stringToUUID(player.getUuid()));
        }
        return players.build();
    }

    public final Set<UUID> getPlayersOnServer(@NonNull String server) {
        StackTraceElement s = Thread.currentThread().getStackTrace()[INDEX];
        printWarn(s, "getPlayersOnServer(String)");
        Set<UUID> uuids = new HashSet<>();
        for(NetworkPlayer p : api.getPlayersOnServer(server)){
            uuids.add(RedisBungeeBridge.stringToUUID(p.getUuid()));
        }
        return uuids;
    }

    public final boolean isPlayerOnline(@NonNull UUID uuid) {
        StackTraceElement s = Thread.currentThread().getStackTrace()[INDEX];
        printWarn(s, "isPlayerOnline(UUID)");
        for(NetworkPlayer player : api.getAllPlayers()){
            if(RedisBungeeBridge.stringToUUID(player.getUuid()).equals(uuid))
                return true;
        }
        return false;
    }

    public final InetAddress getPlayerIp(@NonNull UUID player) {

        StackTraceElement s = Thread.currentThread().getStackTrace()[INDEX];
        printWarn(s, "getPlayerIp(UUID)");
        try{
            return (api.getPlayerByUUID(player) != null ? InetAddress.getByName(api.getPlayerByUUID(player).getIp()) : null);
        }catch (Exception ignored){
            return null;
        }
    }

    public final String getProxy(@NonNull UUID player) {
        StackTraceElement s = Thread.currentThread().getStackTrace()[INDEX];
        printWarn(s, "getProxy(UUID)");
        return (api.getPlayerByUUID(player) != null ? api.getPlayerByUUID(player).getProxy() : null);
    }

    public final void sendProxyCommand(@NonNull String command) {
        StackTraceElement s = Thread.currentThread().getStackTrace()[INDEX];
        printWarn(s, "sendProxyCommand(String)");
        api.sendProxyCommand("RBAPIBridge", command);
    }

    public final void sendProxyCommand(@NonNull String proxyId, @NonNull String command) {
        StackTraceElement s = Thread.currentThread().getStackTrace()[INDEX];
        printWarn(s, "sendProxyCommand(String, String)");
        api.sendProxyCommand("RBAPIBridge", proxyId, command);
    }

    public final void sendChannelMessage(@NonNull String channel, @NonNull String message) {
        StackTraceElement s = Thread.currentThread().getStackTrace()[INDEX];
        printWarn(s, "sendChannelMessage(String, String)", true);
        throw new RuntimeException("Non translated API method sendChannelMessage was called!");
    }

    public final String getServerId() {
        StackTraceElement s = Thread.currentThread().getStackTrace()[INDEX];
        printWarn(s, "getServerId()");
        return api.getServerName();
    }

    public final List<String> getAllServers() {
        StackTraceElement s = Thread.currentThread().getStackTrace()[INDEX];
        printWarn(s, "getAllServers()");
        throw new RuntimeException("Non translated API method getAllServers was called!");
    }

    public final void registerPubSubChannels(String... channels) {
        StackTraceElement s = Thread.currentThread().getStackTrace()[INDEX];
        printWarn(s, "registerPubSubChannels(String...)");
        throw new RuntimeException("Non translated API method registerPubSubChannels was called!");
    }

    public final void unregisterPubSubChannels(String... channels) {
        StackTraceElement s = Thread.currentThread().getStackTrace()[INDEX];
        printWarn(s, "unregisterPubSubChannels(String...)");
        throw new RuntimeException("Non translated API method unregisterPubSubChannels was called!");
    }

    public final String getNameFromUuid(@NonNull UUID uuid) {
        StackTraceElement s = Thread.currentThread().getStackTrace()[INDEX];
        printWarn(s, "getNameFromUuid(UUID)");
        return getNameFromUuid(uuid, true);
    }

    public final String getNameFromUuid(@NonNull UUID uuid, boolean expensiveLookups) {
        StackTraceElement s = Thread.currentThread().getStackTrace()[INDEX];
        printWarn(s, "getNameFromUuid(UUID, boolean)");
        for(NetworkPlayer player : api.getAllPlayers()){
            if(RedisBungeeBridge.stringToUUID(player.getUuid()).equals(uuid))
                return player.getName();
        }
        if(!expensiveLookups) return null;
        //TODO Call Mojang
        throw new RuntimeException("Temporarily disabled expensiveLookups getNameFromUuid was called!");
    }

    public final UUID getUuidFromName(@NonNull String name) {
        StackTraceElement s = Thread.currentThread().getStackTrace()[INDEX];
        printWarn(s, "getUuidFromName(String)");
        return getUuidFromName(name, true);
    }

    public final UUID getUuidFromName(@NonNull String name, boolean expensiveLookups) {
        StackTraceElement s = Thread.currentThread().getStackTrace()[INDEX];
        printWarn(s, "getUuidFromName(String, boolean)");
        if(!ProxyServer.getInstance().getConfig().isOnlineMode()){
            try{
                return UUID.nameUUIDFromBytes(("OfflinePlayer:" + name).getBytes("UTF-8"));
            }catch (UnsupportedEncodingException e){
                return null;
            }
        }
        for(NetworkPlayer player : api.getAllPlayers()){
            if(player.getName().equalsIgnoreCase(name))
                return RedisBungeeBridge.stringToUUID(player.getUuid());
        }
        if(!expensiveLookups) return null;
        //TODO Call Mojang
        throw new RuntimeException("Temporarily disabled expensiveLookups getUuidFromName was called!");
    }

}

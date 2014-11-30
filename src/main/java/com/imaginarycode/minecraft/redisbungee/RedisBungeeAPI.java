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

    RedisBungeeAPI() { }

    public final int getPlayerCount() {
        return api.getTotalOnlinePlayers();
    }

    public final long getLastOnline(@NonNull UUID player) {
        throw new RuntimeException("Non translated API method getLastOnline was called!");
    }

    public final ServerInfo getServerFor(@NonNull UUID player) {
        NetworkPlayer p = api.getPlayerByUUID(player);
        if(p == null) return null;
        for(Map.Entry<String, ServerInfo> entry : ProxyServer.getInstance().getServers().entrySet()){
            if(entry.getValue().getName().equals(p.getServer()))
                return entry.getValue();
        }
        return null;
    }

    public final Set<UUID> getPlayersOnline() {
        Set<UUID> uuids = Collections.emptySet();
        for(NetworkPlayer player : api.getAllPlayers()){
            uuids.add(RedisBungeeBridge.stringToUUID(player.getUuid()));
        }
        return uuids;
    }

    public final Collection<String> getHumanPlayersOnline() {
        LinkedList<String> names = new LinkedList<String>();
        for(NetworkPlayer player : api.getAllPlayers()){
            names.add(player.getName());
        }
        return names;
    }

    public final Multimap<String, UUID> getServerToPlayers() {
        ImmutableMultimap.Builder<String, UUID> players = ImmutableMultimap.builder();
        for(NetworkPlayer player : api.getAllPlayers()){
            if(player.getServer().equals("Connecting")) continue;
            players.put(player.getServer(), RedisBungeeBridge.stringToUUID(player.getUuid()));
        }
        return players.build();
    }

    public final Set<UUID> getPlayersOnServer(@NonNull String server) {
        Set<UUID> uuids = Collections.emptySet();
        for(NetworkPlayer p : api.getPlayersOnServer(server)){
            uuids.add(RedisBungeeBridge.stringToUUID(p.getUuid()));
        }
        return uuids;
    }

    public final boolean isPlayerOnline(@NonNull UUID uuid) {
        for(NetworkPlayer player : api.getAllPlayers()){
            if(RedisBungeeBridge.stringToUUID(player.getUuid()).equals(uuid))
                return true;
        }
        return false;
    }

    public final InetAddress getPlayerIp(@NonNull UUID player) {
        try{
            return (api.getPlayerByUUID(player) != null ? InetAddress.getByName(api.getPlayerByUUID(player).getIp()) : null);
        }catch (Exception ignored){
            return null;
        }
    }

    public final String getProxy(@NonNull UUID player) {
        return (api.getPlayerByUUID(player) != null ? api.getPlayerByUUID(player).getProxy() : null);
    }

    public final void sendProxyCommand(@NonNull String command) {
        //TODO
        throw new RuntimeException("Temporarily disabled method sendProxyCommand was called!");
    }

    public final void sendProxyCommand(@NonNull String proxyId, @NonNull String command) {
        //TODO
        throw new RuntimeException("Temporarily disabled method sendProxyCommand was called!");
    }

    public final void sendChannelMessage(@NonNull String channel, @NonNull String message) {
        throw new RuntimeException("Non translated API method sendChannelMessage was called!");
    }

    public final String getServerId() {
        return api.getServerName();
    }

    public final List<String> getAllServers() {
        throw new RuntimeException("Non translated API method getAllServers was called!");
    }

    public final void registerPubSubChannels(String... channels) {
        throw new RuntimeException("Non translated API method registerPubSubChannels was called!");
    }

    public final void unregisterPubSubChannels(String... channels) {
        throw new RuntimeException("Non translated API method unregisterPubSubChannels was called!");
    }

    public final String getNameFromUuid(@NonNull UUID uuid) {
        return getNameFromUuid(uuid, true);
    }

    public final String getNameFromUuid(@NonNull UUID uuid, boolean expensiveLookups) {
        for(NetworkPlayer player : api.getAllPlayers()){
            if(RedisBungeeBridge.stringToUUID(player.getUuid()).equals(uuid))
                return player.getName();
        }
        if(!expensiveLookups) return null;
        //TODO Call Mojang
        throw new RuntimeException("Temporarily disabled expensiveLookups getNameFromUuid was called!");
    }

    public final UUID getUuidFromName(@NonNull String name) {
        return getUuidFromName(name, true);
    }

    public final UUID getUuidFromName(@NonNull String name, boolean expensiveLookups) {
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

package com.imaginarycode.minecraft.redisbungee;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;
import lombok.NonNull;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.portalblock.pc.publicapi.API;
import net.portalblock.pc.publicapi.APIAccess;
import net.portalblock.pc.publicapi.NetworkPlayer;
import net.portalblock.rbbridge.RedisBungeeBridge;

import java.net.InetAddress;
import java.util.*;

/**
 * Created by portalBlock on 11/29/2014.
 */
public class RedisBungeeAPI {

    private final List<String> reservedChannels;
    private final API api = APIAccess.getApi();

    RedisBungeeAPI() {
        this.reservedChannels = ImmutableList.of(
                "redisbungee-allservers",
                "redisbungee-" + api.getServerName(),
                "redisbungee-data"
        );
    }

    public final int getPlayerCount() {
        return api.getTotalOnlinePlayers();
    }

    public final long getLastOnline(@NonNull UUID player) {
        return plugin.getDataManager().getLastOnline(player);
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
        return Collections2.transform(((ImmutableSet<UUID>) getPlayersOnline()).asList(), new Function<UUID, String>() {
            @Override
            public String apply(UUID uuid) {
                return getNameFromUuid(uuid, false);
            }
        });
    }

    public final Multimap<String, UUID> getServerToPlayers() {
        return plugin.serversToPlayers();
    }

    public final Set<UUID> getPlayersOnServer(@NonNull String server) {
        Set<UUID> uuids = Collections.emptySet();
        for(NetworkPlayer p : api.getPlayersOnServer(server)){
            uuids.add(RedisBungeeBridge.stringToUUID(p.getUuid()));
        }
        return uuids;
    }

    public final boolean isPlayerOnline(@NonNull UUID player) {
        return getLastOnline(player) == 0;
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
        plugin.sendProxyCommand("allservers", command);
    }

    public final void sendProxyCommand(@NonNull String proxyId, @NonNull String command) {
        plugin.sendProxyCommand(proxyId, command);
    }

    public final void sendChannelMessage(@NonNull String channel, @NonNull String message) {
        plugin.sendChannelMessage(channel, message);
    }

    public final String getServerId() {
        return api.getServerName();
    }

    public final List<String> getAllServers() {
        //TODO
        //return plugin.getServerIds();
        return null;
    }

    public final void registerPubSubChannels(String... channels) {
        //RedisBungee.getPubSubListener().addChannel(channels);
    }

    public final void unregisterPubSubChannels(String... channels) {
        /*for (String channel : channels) {
            Preconditions.checkArgument(!reservedChannels.contains(channel), "attempting to unregister internal channel");
        }

        RedisBungee.getPubSubListener().removeChannel(channels);*/
    }

    public final String getNameFromUuid(@NonNull UUID uuid) {
        return getNameFromUuid(uuid, true);
    }

    public final String getNameFromUuid(@NonNull UUID uuid, boolean expensiveLookups) {
        return plugin.getUuidTranslator().getNameFromUuid(uuid, expensiveLookups);
    }

    public final UUID getUuidFromName(@NonNull String name) {
        return getUuidFromName(name, true);
    }

    public final UUID getUuidFromName(@NonNull String name, boolean expensiveLookups) {
        return plugin.getUuidTranslator().getTranslatedUuid(name, expensiveLookups);
    }

}

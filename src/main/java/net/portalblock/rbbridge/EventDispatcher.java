package net.portalblock.rbbridge;

import com.imaginarycode.minecraft.redisbungee.events.PlayerChangedServerNetworkEvent;
import com.imaginarycode.minecraft.redisbungee.events.PlayerJoinedNetworkEvent;
import com.imaginarycode.minecraft.redisbungee.events.PlayerLeftNetworkEvent;
import com.imaginarycode.minecraft.redisbungee.events.PubSubMessageEvent;
import lombok.Getter;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Event;
import net.md_5.bungee.api.plugin.Plugin;
import net.portalblock.pc.publicapi.NetworkPlayer;
import net.portalblock.pc.publicapi.PluginEventHandler;
import net.portalblock.pc.publicapi.PluginMessageHandler;

/**
 * Created by portalBlock on 9/16/2015.
 */
public class EventDispatcher extends PluginEventHandler implements PluginMessageHandler {

    @Getter
    private static EventDispatcher instance;

    private ProxyServer proxy;
    private Plugin plugin;

    public EventDispatcher(ProxyServer proxy, Plugin plugin) {
        this.proxy = proxy;
        this.plugin = plugin;

        instance = this;
    }

    @Override
    public void onPlayerLogin(NetworkPlayer player) {
        callAsyncEvent(new PlayerJoinedNetworkEvent(RedisBungeeBridge.stringToUUID(player.getUuid())));
    }

    @Override
    public void onPlayerLeave(NetworkPlayer player) {
        callAsyncEvent(new PlayerLeftNetworkEvent(RedisBungeeBridge.stringToUUID(player.getUuid())));
    }

    @Override
    public void onPlayerUpdate(NetworkPlayer before, NetworkPlayer after) {
        callAsyncEvent(new PlayerChangedServerNetworkEvent(RedisBungeeBridge.stringToUUID(before.getUuid()),
                before.getServer(), after.getServer()));
    }

    @Override
    public void onMessageGet(String channel, String message) {
        callAsyncEvent(new PubSubMessageEvent(channel, message));
    }

    private void callAsyncEvent(final Event event){
        proxy.getScheduler().runAsync(plugin, new Runnable() {
            @Override
            public void run() {
                proxy.getPluginManager().callEvent(event);
            }
        });
    }
}

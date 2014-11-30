package net.portalblock.rbbridge;

import com.imaginarycode.minecraft.redisbungee.RedisBungee;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.UUID;

/**
 * Created by portalBlock on 11/29/2014.
 */
public class RedisBungeeBridge extends Plugin {

    @Override
    public void onEnable() {
        getLogger().info("RedisBungeeBridge has been enabled, all RedisBungee API calls should now be translated into ProxyConn API calls.");
        RedisBungee.getApi();
        getProxy().registerChannel("RedisBungee");
        getProxy().getPluginManager().registerListener(this, new MessageChannelListener(this));
    }

    public static UUID stringToUUID(String uuid) {
        if (uuid.contains("-")) {
            return UUID.fromString(uuid);
        }
        return UUID.fromString(uuid.substring(0, 8) + "-" + uuid.substring(8, 12) + "-" + uuid.substring(12, 16) + "-" + uuid.substring(16, 20) + "-" + uuid.substring(20, 32));
    }
}

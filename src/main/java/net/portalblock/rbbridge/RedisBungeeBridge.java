/*
 * Copyright (c) 2014 portalBlock. This work is provided AS-IS without any warranty.
 * You must provide a link back to the original project and clearly point out any changes made to this project.
 * This license must be included in all project files.
 * Any changes merged with this project are property of the copyright holder but may include the author's name.
 */
package net.portalblock.rbbridge;

import com.imaginarycode.minecraft.redisbungee.RedisBungee;
import net.md_5.bungee.api.plugin.Plugin;
import net.portalblock.pc.publicapi.APIAccess;

import java.util.UUID;
import java.util.logging.Logger;

/**
 * Created by portalBlock on 11/29/2014.
 */
public class RedisBungeeBridge extends Plugin {

    private static Logger logger;

    public static Logger getRBBLogger(){
        return logger;
    }

    @Override
    public void onEnable() {
        getLogger().info("RedisBungeeBridge has been enabled, all RedisBungee API calls should now be translated into ProxyConn API calls.");
        RedisBungee.getApi();
        getProxy().registerChannel("RedisBungee");
        getProxy().getPluginManager().registerListener(this, new MessageChannelListener(this));
        APIAccess.getApi().registerEventHandler(new EventDispatcher(getProxy(), this));
        logger = getLogger();
    }

    public static UUID stringToUUID(String uuid) {
        if (uuid.contains("-")) {
            return UUID.fromString(uuid);
        }
        return UUID.fromString(uuid.substring(0, 8) + "-" + uuid.substring(8, 12) + "-" + uuid.substring(12, 16) + "-" + uuid.substring(16, 20) + "-" + uuid.substring(20, 32));
    }
}

package com.imaginarycode.minecraft.redisbungee;

/**
 * Created by portalBlock on 11/29/2014.
 */
public class RedisBungee {

    private static RedisBungeeAPI api = new RedisBungeeAPI();

    public static RedisBungeeAPI getApi(){
        return api;
    }

}

package de.TeraArts.API.TabList;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class TabListBuilder {

	public TabListBuilder() {
		
	}
	
    public void sendTabList(Player player, String header, String footer) {
        if (header == null) header = "";

        if (footer == null) footer = "";

        try {
            Object tabHeader = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\":\"" + header + "\"}");
            Object tabFooter = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\":\"" + footer + "\"}");
            Constructor<?> titleConstructor = getNMSClass("PacketPlayOutPlayerListHeaderFooter").getConstructor(getNMSClass("IChatBaseComponent"));
            Object packet = titleConstructor.newInstance(tabHeader);
            Field field = packet.getClass().getDeclaredField("b");
            field.setAccessible(true);
            field.set(packet, tabFooter);
            sendPacket(player, packet);
        } catch (SecurityException | NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | InstantiationException | NoSuchFieldException ex) {
            ex.printStackTrace();
        }
    }
	
	private static Class<?> getNMSClass(String name) {
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        try {
            return Class.forName("net.minecraft.server." + version + "." + name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
	
	private static void sendPacket(Player player, Object packet) {
        try {
            Object handle = player.getClass().getMethod("getHandle").invoke(player);
            Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
            playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, packet);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
	
}

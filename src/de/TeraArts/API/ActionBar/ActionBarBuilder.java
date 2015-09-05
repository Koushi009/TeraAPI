package de.TeraArts.API.ActionBar;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ActionBarBuilder {

	public ActionBarBuilder() {
		
	}
	
		public void sendActionBar(Player player, String message){
    	try {
    		Class<?> c1 = Class.forName("org.bukkit.craftbukkit." + Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3] + ".entity.CraftPlayer");
    		Object p = c1.cast(player);
    		Object ppoc = null;
    		Class<?> c4 = getNMSClass("PacketPlayOutChat");
    		Class<?> c5 = getNMSClass("Packet");
    		if (Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3].equalsIgnoreCase("v1_8_R1") ||
			    !Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3].startsWith("v1_8_")) {
        		Class<?> c2 = getNMSClass("ChatSerializer");
        		Class<?> c3 = getNMSClass("IChatBaseComponent");
        		Method m3 = c2.getDeclaredMethod("a", new Class<?>[] {String.class});
        		Object cbc = c3.cast(m3.invoke(c2, "{\"text\": \"" + message + "\"}"));
        		ppoc = c4.getConstructor(new Class<?>[] {c3, byte.class}).newInstance(new Object[] {cbc, (byte) 2});
    		} else {
    			Class<?> c2 = getNMSClass("ChatComponentText");
        		Class<?> c3 = getNMSClass("IChatBaseComponent");
    			Object o = c2.getConstructor(new Class<?>[] {String.class}).newInstance(new Object[] {message});
        		ppoc = c4.getConstructor(new Class<?>[] {c3, byte.class}).newInstance(new Object[] {o, (byte) 2});
    		}
    		Method m1 = c1.getDeclaredMethod("getHandle", new Class<?>[] {});
    		Object h = m1.invoke(p);
    		Field f1 = h.getClass().getDeclaredField("playerConnection");
    		Object pc = f1.get(h);
    		Method m5 = pc.getClass().getDeclaredMethod("sendPacket",new Class<?>[] {c5});
    		m5.invoke(pc, ppoc);
    	} catch (Exception ex) {
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

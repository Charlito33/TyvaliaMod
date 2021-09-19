package fr.tyvaliarp.tyvaliamod.protector;

import fr.tyvaliarp.tyvaliamod.TyvaliaMod;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ConnectionHandler {
    public static final ConnectionHandler INSTANCE = new ConnectionHandler();

    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event) {
        if (event.getEntity() != null && event.getEntity() instanceof EntityPlayer) {
            if (event.getEntity().world.isRemote) {
                if (Minecraft.getMinecraft().isSingleplayer()) {
                    System.out.println("Skipped key auth --> Singleplayer");
                } else {
                    System.out.println("Sending Auth Packet...");
                    TyvaliaMod.network.sendToServer(new PacketSGetKey());
                }
            }
        }
    }
}

package fr.tyvaliarp.tyvaliamod.proxy;

import fr.tyvaliarp.tyvaliamod.events.CommonEventHandler;
import fr.tyvaliarp.tyvaliamod.events.ServerEventHandler;
import fr.tyvaliarp.tyvaliamod.protector.ConnectionHandler;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy {
    public void preInit() {
        MinecraftForge.EVENT_BUS.register(CommonEventHandler.INSTANCE);
        MinecraftForge.EVENT_BUS.register(ConnectionHandler.INSTANCE);
    }

    public void init() {

    }

    public void postInit() {

    }
}

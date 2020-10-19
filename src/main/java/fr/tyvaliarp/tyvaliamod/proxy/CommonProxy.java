package fr.tyvaliarp.tyvaliamod.proxy;

import fr.tyvaliarp.tyvaliamod.events.CommonEventHandler;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy {
    public void preInit() {
        MinecraftForge.EVENT_BUS.register(CommonEventHandler.INSTANCE);
    }

    public void init() {

    }

    public void postInit() {

    }
}

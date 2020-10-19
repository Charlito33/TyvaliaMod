package fr.tyvaliarp.tyvaliamod.proxy;

import fr.tyvaliarp.tyvaliamod.events.ServerEventHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class ServerProxy extends CommonProxy {

    @Override
    public void preInit() {
        super.preInit();

        MinecraftForge.EVENT_BUS.register(ServerEventHandler.INSTANCE);
    }

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void postInit() {
        super.init();
    }
}

package fr.tyvaliarp.tyvaliamod;

import fr.tyvaliarp.tyvaliamod.capabilities.jobs.DefaultJobs;
import fr.tyvaliarp.tyvaliamod.capabilities.jobs.IJobs;
import fr.tyvaliarp.tyvaliamod.capabilities.jobs.JobsStorage;
import fr.tyvaliarp.tyvaliamod.commands.CommandJob;
import fr.tyvaliarp.tyvaliamod.creativeTabs.ModTab;
import fr.tyvaliarp.tyvaliamod.events.RegisteringEvent;
import fr.tyvaliarp.tyvaliamod.gui.tiles.GuiHandlerCustomFurnace;
import fr.tyvaliarp.tyvaliamod.gui.tiles.GuiHandlerMiningServer;
import fr.tyvaliarp.tyvaliamod.init.ModRecipes;
import fr.tyvaliarp.tyvaliamod.packets.PacketCUpdateJobs;
import fr.tyvaliarp.tyvaliamod.protector.PacketCReceiveKey;
import fr.tyvaliarp.tyvaliamod.protector.PacketSGetKey;
import fr.tyvaliarp.tyvaliamod.proxy.CommonProxy;
import fr.tyvaliarp.tyvaliamod.tiles.TileCustomFurnace;
import fr.tyvaliarp.tyvaliamod.tiles.TileMiningServer;
import fr.tyvaliarp.tyvaliamod.utils.References;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = References.MODID, name = References.NAME, version = References.VERSION)
public class TyvaliaMod {
    @Mod.Instance(References.MODID)
    public static TyvaliaMod instance;

    @SidedProxy(clientSide = References.clientProxy, serverSide = References.serverProxy)
    public static CommonProxy proxy;

    public static final CreativeTabs modTab = new ModTab();
    public static final boolean DEBUG = false;

    public static SimpleNetworkWrapper network;

    public TyvaliaMod() {
        MinecraftForge.EVENT_BUS.register(new RegisteringEvent());
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit();

        network = NetworkRegistry.INSTANCE.newSimpleChannel("tyvaliamod");
        network.registerMessage(PacketCUpdateJobs.Handler.class, PacketCUpdateJobs.class, 0, Side.CLIENT);
        network.registerMessage(PacketSGetKey.Handler.class, PacketSGetKey.class, 1, Side.SERVER);
        network.registerMessage(PacketCReceiveKey.Handler.class, PacketCReceiveKey.class, 2, Side.CLIENT);

        // Tiles Registering
        GameRegistry.registerTileEntity(TileCustomFurnace.class, new ResourceLocation(References.MODID, "tile_custom_furnace"));
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandlerCustomFurnace());

        GameRegistry.registerTileEntity(TileMiningServer.class, new ResourceLocation(References.MODID, "tile_mining_server"));
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandlerMiningServer());
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init();
        ModRecipes.INSTANCE.initRecipes();
        CapabilityManager.INSTANCE.register(IJobs.class, new JobsStorage(), DefaultJobs::new);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit();


    }

    @Mod.EventHandler
    public void serverLoad(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandJob());
    }
}

package fr.tyvaliarp.tyvaliamod.proxy;

import com.sun.org.apache.xpath.internal.operations.Bool;
import fr.tyvaliarp.tyvaliamod.commands.CommandJob;
import fr.tyvaliarp.tyvaliamod.events.ClientEventHandler;
import fr.tyvaliarp.tyvaliamod.events.CommonEventHandler;
import fr.tyvaliarp.tyvaliamod.gui.GuiTyvaliaMainMenu;
import fr.tyvaliarp.tyvaliamod.init.KeyBindings;
import fr.tyvaliarp.tyvaliamod.init.ModBlocks;
import fr.tyvaliarp.tyvaliamod.init.ModItems;
import fr.tyvaliarp.tyvaliamod.utils.URLUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.command.ServerCommandManager;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.Sys;

import java.io.IOException;

public class ClientProxy extends CommonProxy {
    public static String username = Minecraft.getMinecraft().getSession().getUsername();
    public static boolean vip = false;

    private static String apiUrl = "http://tyvalia-rp.000webhostapp.com/api/vip-check.php";

    @Override
    public void preInit() {
        super.preInit();

        MinecraftForge.EVENT_BUS.register(ModItems.INSTANCE);
        MinecraftForge.EVENT_BUS.register(ModBlocks.INSTANCE);
        MinecraftForge.EVENT_BUS.register(ClientEventHandler.INSTANCE);

        KeyBindings.register();

        //ClientCommandHandler.instance.registerCommand(new CommandJob());
    }

    @Override
    public void init() {
        super.init();



        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void postInit() {
        super.init();

        try {
            vip = Boolean.parseBoolean(URLUtils.readStringFromURL(apiUrl + "?username=" + username));
            System.out.println("ID130130130 " + vip);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SubscribeEvent
    public void onOpenGui(GuiOpenEvent event) {
        if (event.getGui() != null && event.getGui().getClass() == GuiMainMenu.class) {
            event.setGui(new GuiTyvaliaMainMenu());
        }
    }
}

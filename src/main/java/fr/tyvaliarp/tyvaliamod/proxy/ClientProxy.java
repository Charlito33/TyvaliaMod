package fr.tyvaliarp.tyvaliamod.proxy;

import fr.tyvaliarp.tyvaliamod.events.ClientEventHandler;
import fr.tyvaliarp.tyvaliamod.gui.GuiTyvaliaInGameMenu;
import fr.tyvaliarp.tyvaliamod.gui.GuiTyvaliaMainMenu;
import fr.tyvaliarp.tyvaliamod.init.KeyBindings;
import fr.tyvaliarp.tyvaliamod.init.ModBlocks;
import fr.tyvaliarp.tyvaliamod.init.ModItems;
import fr.tyvaliarp.tyvaliamod.utils.URLUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.IOException;

public class ClientProxy extends CommonProxy {
    public static Runtime runtime = Runtime.getRuntime();

    public static String username = Minecraft.getMinecraft().getSession().getUsername();
    public static boolean vip = false;

    private static String apiUrl = "https://tyvalia-rp.fr.nf/api/";

    @Override
    public void preInit() {
        super.preInit();

        MinecraftForge.EVENT_BUS.register(ModItems.INSTANCE);
        MinecraftForge.EVENT_BUS.register(ModBlocks.INSTANCE);
        MinecraftForge.EVENT_BUS.register(ClientEventHandler.INSTANCE);

        KeyBindings.register();

        runtime.addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URLUtils.readStringFromURL(apiUrl + "set-connected.php?username=" + username + "&status=false");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }));

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
            ClientProxy.vip = stringToBool(URLUtils.readStringFromURL(apiUrl + "check-vip.php?username=" + username));
            URLUtils.readStringFromURL(apiUrl + "set-connected.php?username=" + username + "&status=true");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SubscribeEvent
    public void onOpenGui(GuiOpenEvent event) {
        if (event.getGui() != null) {
            if (event.getGui().getClass() == GuiMainMenu.class) {
                event.setGui(new GuiTyvaliaMainMenu());
            } else if (event.getGui().getClass() == GuiIngameMenu.class) {
                event.setGui(new GuiTyvaliaInGameMenu());
            }
        }
    }

    private Boolean stringToBool(String string) {
        return string.contains("true");
    }
}

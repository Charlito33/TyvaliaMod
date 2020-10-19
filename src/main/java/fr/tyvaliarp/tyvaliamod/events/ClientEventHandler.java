package fr.tyvaliarp.tyvaliamod.events;


import fr.tyvaliarp.tyvaliamod.capabilities.jobs.JobsStorage;
import fr.tyvaliarp.tyvaliamod.init.KeyBindings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import scala.swing.event.KeyPressed;

import java.util.List;

public class ClientEventHandler {
    public static final ClientEventHandler INSTANCE = new ClientEventHandler();

    public static final Minecraft MC = Minecraft.getMinecraft();

    @SubscribeEvent
    public void onRenderPre(RenderGameOverlayEvent event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.DEBUG) {
            if (!KeyBindings.originalF3Menu.isKeyDown()) {
                event.setCanceled(true);

                Minecraft MC = Minecraft.getMinecraft();
                FontRenderer fontRenderer = MC.fontRenderer;

                String fps = MC.debug.split(",", 2)[0].split(" ")[0] + " fps";

                this.drawString(fontRenderer, fps, 5, 5, 0xFFFFFF);
                this.drawString(fontRenderer, "Jobs :", 5, 25, 0x1e64b4);

                List<String> jobs = MC.player.getCapability(JobsStorage.JOBS_CAPABILITY, null).getJobs();

                for (int i = 0; i < jobs.size(); i++) {
                    this.drawString(fontRenderer, "- " + I18n.format(jobs.get(i)), 5, 35 + (i * 10), 0xFFFFFF);
                }
            }
        } // Render new F3 menu
    }

    @SubscribeEvent
    public void onGameOverlayPost(RenderGameOverlayEvent.Post event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
            int width = event.getResolution().getScaledWidth();
        } // Render new F3 menu


    }

    public void drawString(FontRenderer fontRenderer, String string, int x, int y, int color) {
        fontRenderer.drawStringWithShadow(string, x, y, color);
    }

    @SubscribeEvent
    public void attachCapability(AttachCapabilitiesEvent<Chunk> event) {

    }
}

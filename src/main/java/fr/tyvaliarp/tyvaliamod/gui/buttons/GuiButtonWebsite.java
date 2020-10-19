package fr.tyvaliarp.tyvaliamod.gui.buttons;

import fr.tyvaliarp.tyvaliamod.utils.References;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class GuiButtonWebsite extends GuiButton {
    private static final ResourceLocation WEBSITE_ICON = new ResourceLocation(References.MODID, "textures/gui/buttons/website.png");
    private static final ResourceLocation WEBSITE_ICON_HOVER = new ResourceLocation(References.MODID, "textures/gui/buttons/website_hover.png");

    public GuiButtonWebsite(int buttonId, int x, int y) {
        super(buttonId, x, y, 20, 20, "");
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        if (this.visible) {
            boolean mouseHover = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;

            if (mouseHover) {
                mc.getTextureManager().bindTexture(WEBSITE_ICON_HOVER);
            } else {
                mc.getTextureManager().bindTexture(WEBSITE_ICON);
            }

            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            Gui.drawScaledCustomSizeModalRect(this.x, this.y, 0, 0, 128, 128, 20, 20, 128, 128);
        }
    }
}

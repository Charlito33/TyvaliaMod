package fr.tyvaliarp.tyvaliamod.gui.tiles;

import fr.tyvaliarp.tyvaliamod.tiles.ContainerMiningServer;
import fr.tyvaliarp.tyvaliamod.tiles.TileMiningServer;
import fr.tyvaliarp.tyvaliamod.utils.References;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;

public class GuiMiningServer extends GuiContainer {
    private static final ResourceLocation background = new ResourceLocation(References.MODID, "textures/gui/container/mining_server.png");
    private TileMiningServer tile;

    public GuiMiningServer(TileMiningServer tile, InventoryPlayer playerInventory) {
        super(new ContainerMiningServer(tile, playerInventory));
        this.tile = tile;

        xSize = 203;
        ySize = 203;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        int i = (width - xSize) / 2;
        int j = (height - ySize) / 2;
        drawDefaultBackground();
        mc.getTextureManager().bindTexture(background);
        drawTexturedModalRect(i, j, 0, 0, xSize, ySize);

        int energy = tile.getField(0);
        int timePassed = tile.getField(1);

        int textureHeight = Math.round(((float) energy / (float) tile.getMaxEnergy()) * 45f);
        drawTexturedModalRect(i + 62, j + 61 - textureHeight, 208, 0, 10, textureHeight);

        int textureWidth = Math.round(((float) timePassed / (float) tile.getGenerationTime()) * 76f);
        drawTexturedModalRect(i + 86, j + 69, 0, 208, textureWidth, 10);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        int i = (width - xSize) / 2;
        int j = (height - ySize) / 2;

        int energy = tile.getField(0);
        int timePassed = tile.getField(1);

        if (inRect(mouseX, mouseY, i + 62, j + 16, 10, 45)) {
            drawHoveringText("Energy : " + energy + "/" + tile.getMaxEnergy(), mouseX - i, mouseY - j);
        }
        if (inRect(mouseX, mouseY, i + 86, j + 69, 76, 10)) {
            drawHoveringText("Generation : " + timePassed + "/" + tile.getGenerationTime(), mouseX - i, mouseY - j);
        }

        int k = 0;
        int l = 0;

        for (k = 0; k < 2; k++) {
            for (l = 0; l < 4; l++) {
                if (inRect(mouseX, mouseY, i + k * 18 + 8, j + 12 + l * 18, 16, 16)) {
                    drawHoveringText("GPU Slot : " + ((k + l * 2) + 1) + "/8", mouseX - i, mouseY - j);
                }
            }
        }

        for (k = 0; k < 2; k++) {
            if (inRect(mouseX, mouseY, i + k * 18 + 50, j + 66, 16, 16)) {
                if (k == 0) drawHoveringText("Energy Input", mouseX - i, mouseY - j);
                else drawHoveringText("Energy Input Multiplier", mouseX - i, mouseY - j);
            }
        }

        if (inRect(mouseX, mouseY, i + 164, j + 66, 16, 16)) {
            drawHoveringText("Output", mouseX - i, mouseY - j);
        }
    }

    private boolean inRect(int mouseX, int mouseY, int rectX, int rectY, int width, int height) {
        if (mouseX >= rectX && mouseX <= rectX + width && mouseY >= rectY && mouseY <= rectY + height) {
            return true;
        }

        return false;
    }
}

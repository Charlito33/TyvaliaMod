package fr.tyvaliarp.tyvaliamod.gui.tiles;

import fr.tyvaliarp.tyvaliamod.gui.tiles.GuiCustomFurnace;
import fr.tyvaliarp.tyvaliamod.tiles.ContainerCustomFurnace;
import fr.tyvaliarp.tyvaliamod.tiles.TileCustomFurnace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

public class GuiHandlerCustomFurnace implements IGuiHandler {
    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
        if (te instanceof TileCustomFurnace) {
            return new ContainerCustomFurnace((TileCustomFurnace) te, player.inventory);
        }

        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity te = world.getTileEntity(new BlockPos(x, y , z));
        if (te instanceof TileCustomFurnace) {
            return new GuiCustomFurnace((TileCustomFurnace) te, player.inventory);
        }

        return null;
    }
}

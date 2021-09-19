package fr.tyvaliarp.tyvaliamod.gui.tiles;

import fr.tyvaliarp.tyvaliamod.tiles.ContainerMiningServer;
import fr.tyvaliarp.tyvaliamod.tiles.TileMiningServer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

public class GuiHandlerMiningServer implements IGuiHandler {
    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));
        if (tileEntity instanceof TileMiningServer) {
            return new ContainerMiningServer((TileMiningServer) tileEntity, player.inventory);
        }

        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));
        if (tileEntity instanceof TileMiningServer) {
            return new GuiMiningServer((TileMiningServer) tileEntity, player.inventory);
        }

        return null;
    }
}

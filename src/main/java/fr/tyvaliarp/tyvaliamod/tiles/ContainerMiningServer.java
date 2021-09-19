package fr.tyvaliarp.tyvaliamod.tiles;

import fr.tyvaliarp.tyvaliamod.init.ModItems;
import fr.tyvaliarp.tyvaliamod.utils.ModItem;
import fr.tyvaliarp.tyvaliamod.utils.SlotOneSingleItem;
import fr.tyvaliarp.tyvaliamod.utils.SlotOutput;
import fr.tyvaliarp.tyvaliamod.utils.SlotSingleItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


public class ContainerMiningServer extends Container {
    private TileMiningServer tile;
    private int energy = 0;
    private int timePassed = 0;

    public ContainerMiningServer(TileMiningServer tile, InventoryPlayer playerInventory) {
        this.tile = tile;

        int i = 0;
        int j = 0;

        for (i = 0; i < 2; i++) {
            for (j = 0; j < 4; j++) {
                addSlotToContainer(new SlotOneSingleItem(tile, j + i * 4, i * 18 + 8, 12 + j * 18, ModItems.mining_card));
            }
        }

        for (i = 0; i < 2; i++) {
            if (i == 0) addSlotToContainer(new SlotSingleItem(tile, 8 + i, 50 + i * 18, 66, ModItems.energy));
            else addSlotToContainer(new SlotSingleItem(tile, 8 + i, 50 + i * 18, 66, ModItems.fan));

        }

        addSlotToContainer(new SlotOutput(tile, 10, 164, 66));

        for (i = 0; i < 3; ++i) { // IDK Why ++i and not i++
            for (j = 0; j < 9; ++j) {
                addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 21 + j * 18, 116 + i * 18));
            }
        }

        for (i = 0; i < 9; ++i) {
            addSlotToContainer(new Slot(playerInventory, i, 21 + i * 18, 180));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return tile.isUsableByPlayer(playerIn);
    }

    @Override
    public void addListener(IContainerListener listener) {
        super.addListener(listener);
        listener.sendAllWindowProperties(this, this.tile);
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        for (int i = 0; i < listeners.size(); ++i) {
            IContainerListener iContainerListener = (IContainerListener) listeners.get(i);

            if (energy != tile.getField(0)) {
                iContainerListener.sendWindowProperty(this, 0, tile.getField(0));
            }

            if (timePassed != tile.getField(1)) {
                iContainerListener.sendWindowProperty(this, 1, tile.getField(1));
            }
        }

        energy = tile.getField(0);
        timePassed = tile.getField(1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data) {
        tile.setField(id, data);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        return ItemStack.EMPTY;
    }
}

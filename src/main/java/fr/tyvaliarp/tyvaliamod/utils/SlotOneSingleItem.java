package fr.tyvaliarp.tyvaliamod.utils;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SlotOneSingleItem extends Slot {
    private Item item;

    public SlotOneSingleItem(IInventory inventoryIn, int index, int xPosition, int yPosition, Item item) {
        super(inventoryIn, index, xPosition, yPosition);
        this.item = item;
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return stack.isEmpty() || stack.getItem() == item;
    }

    @Override
    public int getSlotStackLimit() {
        return 1;
    }
}

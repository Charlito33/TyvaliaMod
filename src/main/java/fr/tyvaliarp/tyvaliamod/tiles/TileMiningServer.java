package fr.tyvaliarp.tyvaliamod.tiles;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;

public class TileMiningServer extends TileEntityLockable implements ITickable {
    private NonNullList<ItemStack> stacks = NonNullList.withSize(11, ItemStack.EMPTY);

    private int energy = 0;
    private int timePassed = 0;

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        stacks = NonNullList.withSize(getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, stacks);

        energy = compound.getInteger("energy");
        timePassed = compound.getInteger("timePassed");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);

        ItemStackHelper.saveAllItems(compound, stacks);

        compound.setInteger("energy", energy);
        compound.setInteger("timePassed", timePassed);

        return compound;
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }

    @Override
    public String getName() {
        return "tile.mining_server.name";
    }

    @Override
    public int getField(int id) {
        switch (id) {
            case 0:
                return energy;
            case 1:
                return timePassed;
        }

        return 0;
    }

    @Override
    public void setField(int id, int value) {
        switch (id) {
            case 0:
                energy = value;
                break;
            case 1:
                timePassed = value;
                break;
        }
    }

    @Override
    public int getFieldCount() {
        return 2;
    }

    @Override
    public int getSizeInventory() {
        return stacks.size();
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return stacks.get(index);
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        return ItemStackHelper.getAndSplit(stacks, index, count);
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        return ItemStackHelper.getAndRemove(stacks, index);
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        stacks.set(index, stack);

        if (stack.getCount() > getInventoryStackLimit()) {
            stack.setCount(getInventoryStackLimit());
        }
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack stack : stacks) {
            if (!stack.isEmpty()) return false;
        }

        return true;
    }

    @Override
    public void clear() {
        for (ItemStack stack : stacks) {
            stack = ItemStack.EMPTY;
        }
    }

    @Override
    public void openInventory(EntityPlayer player) {

    }

    @Override
    public void closeInventory(EntityPlayer player) {

    }

    @Override
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
        return null;
    }

    @Override
    public String getGuiID() {
        return null;
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return true; // Not Yet Implemented
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer player) {
        return world.getTileEntity(pos) == this && player.getDistanceSq((double) pos.getX() + 0.5d, (double) pos.getY() + 0.5d, (double) pos.getZ() + 0.5d) <= 64.0d;
    }

    // Custom

    public boolean isOff() {
        return energy == 0;
    }

    public boolean isOn() {
        return energy > 0;
    }

    public boolean isEnergyFull() {
        return energy == getMaxEnergy();
    }

    public int getMaxEnergy() {
        return 100_000;
    }

    public int getGenerationTime() {
        return 10_000;
    }

    public int getEnergyValue() { // Energy "item" value
        return 10;
    }

    public ItemStack getGenerationResult() {
        return new ItemStack(Items.NETHER_STAR);
    }

    public int getEnergyMultiplier() {
        return stacks.get(9).getCount() + 1;
    }

    public int getGenerationMultiplier() {
        int count = 0;

        for (int i = 0; i < 8; i++) {
            count += stacks.get(i).isEmpty() ? 0 : 1;
        }

        return count;
    }

    public boolean generate() {
        ItemStack result = getGenerationResult();

        ItemStack stack10 = getStackInSlot(10); // Stack 10 is result

        if (stack10.isEmpty()) {
            setInventorySlotContents(10, result.copy());
            return true;
        } else if (stack10.getCount() + result.getCount() <= getInventoryStackLimit()) {
            stack10.setCount(stack10.getCount() + result.getCount());
            return true;
        }

        return false;
    }

    @Override
    public void update() {
        if (!world.isRemote) {
            if (!isEnergyFull() && energy <= getMaxEnergy() - getEnergyValue() * getEnergyMultiplier() && !stacks.get(8).isEmpty()) { // Stack 8 is Energy Stack
                energy += getEnergyValue() * getEnergyMultiplier();
                decrStackSize(8, 1);
            }

            if (isOn() && energy - (16 + getGenerationMultiplier() * 2) >= 0) {
                energy -= getGenerationMultiplier() * 8;

                timePassed += 4 * getGenerationMultiplier();
                if (timePassed >= getGenerationTime()) {
                    if (generate()) timePassed = 0; // Wait until result is not full
                }
            } else {
                if (timePassed > 0) {
                    if (timePassed - 10 >= 0) {
                        timePassed -= 10;
                    } else {
                        timePassed = 0;
                    }
                } // Reset if no energy
            }

            markDirty();
        }
    }
}

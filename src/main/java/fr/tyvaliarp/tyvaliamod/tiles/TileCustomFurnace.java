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
import net.minecraftforge.oredict.OreDictionary;

public class TileCustomFurnace extends TileEntityLockable implements ITickable {
    private NonNullList<ItemStack> stacks = NonNullList.withSize(5, ItemStack.EMPTY);
    private String customName;
    private int timePassed = 0;
    private int burningTimeLeft = 0;

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.stacks = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, this.stacks);

        if (compound.hasKey("CustomName", 8)) {
            this.customName = compound.getString("CustomName");
        }

        this.burningTimeLeft = compound.getInteger("burningTimeLeft");
        this.timePassed = compound.getInteger("timePassed");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        ItemStackHelper.saveAllItems(compound, this.stacks);

        if (this.hasCustomName()) {
            compound.setString("CustomName", this.customName);
        }

        compound.setInteger("burningTimeLeft", this.burningTimeLeft);
        compound.setInteger("timePassed", this.timePassed);

        return compound;
    }

    @Override
    public boolean hasCustomName() {
        return this.customName != null && !this.customName.isEmpty();
    }

    @Override
    public String getName() {
        return hasCustomName() ? this.customName : "tile.custom_furnace.name";
    }

    public void setCustomName(String name) {
        this.customName = name;
    }

    @Override
    public int getField(int id) {
        switch (id) {
            case 0:
                return this.burningTimeLeft;
            case 1:
                return this.timePassed;
        }

        return 0;
    }

    @Override
    public void setField(int id, int value) {
        switch (id) {
            case 0:
                this.burningTimeLeft = value;
                break;
            case 1:
                this.timePassed = value;
        }
    }

    @Override
    public int getFieldCount() {
        return 2;
    }

    @Override
    public int getSizeInventory() {
        return this.stacks.size();
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return this.stacks.get(index);
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        return ItemStackHelper.getAndSplit(this.stacks, index, count);
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        return ItemStackHelper.getAndRemove(stacks, index);
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        this.stacks.set(index, stack);

        if (stack.getCount() > this.getInventoryStackLimit()) {
            stack.setCount(this.getInventoryStackLimit());
        }
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack stack : this.stacks) {
            if (!stack.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public void clear() {
        for (int i = 0; i < this.stacks.size(); i++) {
            this.stacks.set(i, ItemStack.EMPTY);
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
        if (index == 2) { // Slot 3 = Wood
            return OreDictionary.getOres("plankWood").contains(new ItemStack(stack.getItem(), 1, OreDictionary.WILDCARD_VALUE));
        }

        if (index == 3) { // Slot 4 = Wheat
            return stack.getItem() == Items.WHEAT;
        }

        if (index == 4) { // Result = Nothing
            return false;
        }

        // 1 && 2 == Everything
        return true;
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer player) {
        return this.world.getTileEntity(this.pos) != this ? false : player.getDistanceSq((double) this.pos.getX() + 0.5d, (double) this.pos.getY() + 0.5d, (double) this.pos.getZ() + 0.5d) <= 64.0d;
    }


    // Custom
    public boolean hasFuelEmpty() {
        return this.getStackInSlot(2).isEmpty() || this.getStackInSlot(3).isEmpty();
    }

    public ItemStack getRecipeResult() {
        return RecipesCustomFurnace.getRecipeResult(new ItemStack[] {
           this.getStackInSlot(0),
           this.getStackInSlot(1)
        });
    }

    public boolean canSmelt() {
        ItemStack result = this.getRecipeResult();

        if (result != null) {
            ItemStack slot4 = this.getStackInSlot(4); // Result slot content

            if (slot4.isEmpty()) {
                return true;
            }

            if (slot4.getItem() == result.getItem() && slot4.getItemDamage() == result.getItemDamage()) {
                int newStackSize = slot4.getCount() + result.getCount();
                if (newStackSize <= this.getInventoryStackLimit() && newStackSize <= slot4.getMaxStackSize()) {
                    return true;
                }
            }
        }

        return false;
    }

    public void smelt() {
        ItemStack result = this.getRecipeResult();

        this.decrStackSize(0, 1);
        this.decrStackSize(1, 1);

        ItemStack stack4 = this.getStackInSlot(4);

        if (stack4.isEmpty()) {
            this.setInventorySlotContents(4, result.copy());
        } else {
            stack4.setCount(stack4.getCount() + result.getCount());
        }
    }

    public int getFullRecipeTime() {
        return 200; // Time to finish
    }

    public int getFullBurnTime() {
        return 300; // Time to use 1 Plank + 1 Wheat
    }

    public boolean isBurning() {
        return burningTimeLeft > 0; // If Burning
    }

    @Override
    public void update() {
        if (!this.world.isRemote) {
            if (this.isBurning()) {
                this.burningTimeLeft--;
            }

            if (!this.isBurning() && this.canSmelt() && !this.hasFuelEmpty()) {
                this.burningTimeLeft = this.getFullBurnTime();
                this.decrStackSize(2, 1);
                this.decrStackSize(3, 1);
            }

            if (this.isBurning() && this.canSmelt()) {
                this.timePassed++;
                if (timePassed >= this.getFullRecipeTime()) {
                    timePassed = 0;
                    this.smelt();
                }
            } else {
                timePassed = 0;
            }

            this.markDirty();
        }
    }
}

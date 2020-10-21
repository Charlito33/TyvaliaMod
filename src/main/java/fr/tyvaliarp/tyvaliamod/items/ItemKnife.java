package fr.tyvaliarp.tyvaliamod.items;

import fr.tyvaliarp.tyvaliamod.TyvaliaMod;
import fr.tyvaliarp.tyvaliamod.init.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

import javax.annotation.Nullable;

public class ItemKnife extends ItemSword {
    public ItemKnife() {
        super(ToolMaterial.IRON);

        setRegistryName("knife").setUnlocalizedName("knife");
        setCreativeTab(TyvaliaMod.modTab);

        ModItems.INSTANCE.getItems().add(this);
    }

    @Override
    public boolean hasContainerItem(ItemStack stack) {
        return true;
    }

    @Nullable
    @Override
    public ItemStack getContainerItem(ItemStack itemStack) {
        ItemStack newItem = itemStack.copy();
        newItem.setItemDamage(newItem.getItemDamage() + 1);
        return newItem;
    }
}

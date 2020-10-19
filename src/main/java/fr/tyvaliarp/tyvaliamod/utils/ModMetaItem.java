package fr.tyvaliarp.tyvaliamod.utils;

import fr.tyvaliarp.tyvaliamod.TyvaliaMod;
import fr.tyvaliarp.tyvaliamod.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class ModMetaItem extends Item {
    public final String[] subTypes;

    public ModMetaItem(String name, String[] subTypes) {
        setRegistryName(name).setUnlocalizedName(name);
        setHasSubtypes(true);
        setCreativeTab(TyvaliaMod.modTab);

        this.subTypes = subTypes;

        ModItems.INSTANCE.getMetaItems().add(this);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        int metadata = stack.getItemDamage();

        if (metadata < 0 || metadata >= subTypes.length) {
            metadata = 0;
        }

        return super.getUnlocalizedName(stack) + "_" + subTypes[metadata];
    }

    @Override
    public int getMetadata(ItemStack stack) {
        int metadata = stack.getItemDamage();

        if (metadata < 0 || metadata >= subTypes.length) {
            metadata = 0;
        }

        return metadata;
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (getCreativeTab() != tab) return;
        super.getSubItems(tab, items);

        for (int i = 1; i < subTypes.length; i++) {
            items.add(new ItemStack(this, 1, i));
        }
    }
}

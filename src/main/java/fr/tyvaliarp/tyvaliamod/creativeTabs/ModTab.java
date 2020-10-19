package fr.tyvaliarp.tyvaliamod.creativeTabs;

import fr.tyvaliarp.tyvaliamod.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class ModTab extends CreativeTabs {
    public ModTab() {
        super("tyvaliamodtab");
    }

    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(ModItems.driver_license);
    }
}

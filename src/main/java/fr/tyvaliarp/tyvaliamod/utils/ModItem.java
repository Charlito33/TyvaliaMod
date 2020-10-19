package fr.tyvaliarp.tyvaliamod.utils;

import fr.tyvaliarp.tyvaliamod.TyvaliaMod;
import fr.tyvaliarp.tyvaliamod.init.ModItems;
import net.minecraft.item.Item;

public class ModItem extends Item {
    public ModItem(String name) {
        setRegistryName(name).setUnlocalizedName(name);
        setCreativeTab(TyvaliaMod.modTab);

        ModItems.INSTANCE.getItems().add(this);
    }
}

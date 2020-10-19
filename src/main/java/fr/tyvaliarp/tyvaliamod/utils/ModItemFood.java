package fr.tyvaliarp.tyvaliamod.utils;

import fr.tyvaliarp.tyvaliamod.TyvaliaMod;
import fr.tyvaliarp.tyvaliamod.init.ModItems;
import net.minecraft.item.ItemFood;

public class ModItemFood extends ItemFood {
    public ModItemFood(String name, int food, float saturation) {
        super(food, saturation, false);
        setRegistryName(name).setUnlocalizedName(name);
        setCreativeTab(TyvaliaMod.modTab);

        ModItems.INSTANCE.getItems().add(this);
    }
}

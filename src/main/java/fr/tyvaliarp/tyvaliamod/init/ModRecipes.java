package fr.tyvaliarp.tyvaliamod.init;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class ModRecipes {
    public static ModRecipes INSTANCE = new ModRecipes();

    public void initRecipes() {
        OreDictionary.registerOre("knife", new ItemStack(ModItems.knife, 1, OreDictionary.WILDCARD_VALUE));
    }
}

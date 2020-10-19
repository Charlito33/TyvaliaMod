package fr.tyvaliarp.tyvaliamod.events;

import fr.tyvaliarp.tyvaliamod.init.ModBlocks;
import fr.tyvaliarp.tyvaliamod.init.ModItems;
import fr.tyvaliarp.tyvaliamod.utils.ModBlock;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;

public class RegisteringEvent {
    @SubscribeEvent
    public void registerItems(RegistryEvent.Register<Item> e) {
        ModItems.INSTANCE.init();

        ArrayList<Item> items = new ArrayList<>();

        items.addAll(ModItems.INSTANCE.getItems());
        items.addAll(ModItems.INSTANCE.getMetaItems());

        e.getRegistry().registerAll(items.toArray(new Item[0]));
    }

    @SubscribeEvent
    public void registerBlocks(RegistryEvent.Register<Block> e) {
        ModBlocks.INSTANCE.init();

        e.getRegistry().registerAll(ModBlocks.INSTANCE.getBlocks().toArray(new Block[0]));
    }
}

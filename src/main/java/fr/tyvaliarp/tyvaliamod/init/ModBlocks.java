package fr.tyvaliarp.tyvaliamod.init;

import fr.tyvaliarp.tyvaliamod.blocks.BlockATM;
import fr.tyvaliarp.tyvaliamod.blocks.BlockRoadLine;
import fr.tyvaliarp.tyvaliamod.blocks.BlockWeedPlant;
import fr.tyvaliarp.tyvaliamod.utils.ModBlock;
import fr.tyvaliarp.tyvaliamod.utils.References;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.ArrayList;
import java.util.List;

public class ModBlocks {
    public static final ModBlocks INSTANCE = new ModBlocks();

    private List<Block> blocks;

    public static Block weed_plant, road, road_line, lootbox_ultimate, lootbox_gun, lootbox_car, atm;

    public void init() {
        blocks = new ArrayList<>();

        weed_plant = new BlockWeedPlant();
        road = new ModBlock("road", Material.ROCK);
        road_line = new BlockRoadLine();
        lootbox_ultimate = new ModBlock("lootbox_ultimate", Material.IRON);
        lootbox_gun = new ModBlock("lootbox_gun", Material.IRON);
        lootbox_car = new ModBlock("lootbox_car", Material.IRON);
        atm = new BlockATM();

        for (Block block : blocks) {
            ItemBlock ib = new ItemBlock(block);
            ib.setRegistryName(block.getRegistryName());
            GameRegistry.findRegistry(Item.class).register(ib);
        }
    }

    @SubscribeEvent
    public void registerModels(ModelRegistryEvent e) {
        for (Block b : blocks) {
            registerModel(b);
        }

        registerModel(road_line);
    }

    public void registerModel(Block block) {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(new ResourceLocation(References.MODID, block.getUnlocalizedName().substring(5)), "inventory"));
    }

    public List<Block> getBlocks() {
        return blocks;
    }
}

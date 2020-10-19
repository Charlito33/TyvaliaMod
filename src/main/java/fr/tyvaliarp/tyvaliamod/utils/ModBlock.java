package fr.tyvaliarp.tyvaliamod.utils;


import fr.tyvaliarp.tyvaliamod.TyvaliaMod;
import fr.tyvaliarp.tyvaliamod.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.lang.reflect.Type;

public class ModBlock extends Block {
    public ModBlock(String name, Material materialIn) {
        super(materialIn);

        setRegistryName(name).setUnlocalizedName(name);
        setCreativeTab(TyvaliaMod.modTab);

        ModBlocks.INSTANCE.getBlocks().add(this);
    }
}

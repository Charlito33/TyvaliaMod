package fr.tyvaliarp.tyvaliamod.blocks;

import fr.tyvaliarp.tyvaliamod.TyvaliaMod;
import fr.tyvaliarp.tyvaliamod.init.ModBlocks;
import fr.tyvaliarp.tyvaliamod.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

import java.util.Random;

public class BlockWeedPlant extends BlockCrops {
    public static final PropertyInteger WEED_AGE = PropertyInteger.create("age", 0, 3);

    @Override
    protected PropertyInteger getAgeProperty() {
        return WEED_AGE;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {WEED_AGE});
    }

    private static final AxisAlignedBB[] boundingBox = new AxisAlignedBB[] {
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1D, 1.0D),
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1D, 1.0D),
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1D, 1.0D),
            new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1D, 1.0D)
    };

    public BlockWeedPlant() {
        setRegistryName("weed_plant").setUnlocalizedName("weed_plant");

        ModBlocks.INSTANCE.getBlocks().add(this);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            if (this.isMaxAge(state)) {
                worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(ModItems.weed, 2)));
                worldIn.setBlockState(pos, this.withAge(0));
                return true;
            }
        }

        return false;
    }

    @Override
    protected Item getSeed() {
        return ModItems.weed_seed;
    }

    @Override
    protected Item getCrop() {
        return ModItems.weed;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return boundingBox[((Integer)state.getValue(this.getAgeProperty())).intValue()];
    }

    @Override
    public int getMaxAge() {
        return 3;
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if (fromPos.equals(pos.down())) {
            IBlockState blockState = worldIn.getBlockState(fromPos);

            if (!blockState.getBlock().canSustainPlant(blockState, worldIn, fromPos, EnumFacing.UP, ModItems.weedIPlantable)) {
                worldIn.setBlockToAir(pos);
                worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(ModItems.weed_seed)));
            }
        }
    }
}

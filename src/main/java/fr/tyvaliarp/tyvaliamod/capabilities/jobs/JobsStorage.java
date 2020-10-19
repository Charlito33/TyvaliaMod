package fr.tyvaliarp.tyvaliamod.capabilities.jobs;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class JobsStorage implements Capability.IStorage<IJobs> {
    @CapabilityInject(IJobs.class)
    public static final Capability<IJobs> JOBS_CAPABILITY = null;

    @Override
    public NBTBase writeNBT(Capability<IJobs> capability, IJobs instance, EnumFacing side) {
        NBTTagCompound nbt = new NBTTagCompound();

        return nbt;
    }

    @Override
    public void readNBT(Capability<IJobs> capability, IJobs instance, EnumFacing side, NBTBase base) {
        if (base instanceof NBTTagCompound) {
            NBTTagCompound nbt = (NBTTagCompound)base;
        }
    }
}

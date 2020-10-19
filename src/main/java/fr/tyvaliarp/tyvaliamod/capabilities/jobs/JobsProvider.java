package fr.tyvaliarp.tyvaliamod.capabilities.jobs;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nullable;

public class JobsProvider implements ICapabilitySerializable<NBTBase> {
    protected IJobs jobs;

    public JobsProvider() {
        this.jobs = new DefaultJobs();
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == JobsStorage.JOBS_CAPABILITY;
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        return this.hasCapability(capability, facing) ? JobsStorage.JOBS_CAPABILITY.cast(this.jobs) : null;
    }

    @Override
    public NBTBase serializeNBT() {
        return JobsStorage.JOBS_CAPABILITY.writeNBT(this.jobs, null);
    }

    @Override
    public void deserializeNBT(NBTBase nbt) {
        JobsStorage.JOBS_CAPABILITY.readNBT(this.jobs, null, nbt);
    }
}

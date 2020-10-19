package fr.tyvaliarp.tyvaliamod.events;

import fr.tyvaliarp.tyvaliamod.capabilities.jobs.JobsProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CommonEventHandler {
    public static final CommonEventHandler INSTANCE = new CommonEventHandler();

    public static final ResourceLocation CAPABILITY_JOBS_LOCATION = new ResourceLocation("tyvaliamod", "jobs");

    @SubscribeEvent
    public void attachCapability(AttachCapabilitiesEvent<Chunk> event) {

    }

    @SubscribeEvent
    public void blockPlaced(BlockEvent.PlaceEvent event) {
        if (!event.getWorld().isRemote) {

        }
    }

    @SubscribeEvent
    public void breakBlock(BlockEvent.BreakEvent event) {
        if (!event.getWorld().isRemote) {

        }
    }

    @SubscribeEvent
    public void attachCapabilityEntity(AttachCapabilitiesEvent<Entity> entity) {
        if (entity.getObject() instanceof EntityPlayer) {
            entity.addCapability(CAPABILITY_JOBS_LOCATION, new JobsProvider());
        }
    }
}

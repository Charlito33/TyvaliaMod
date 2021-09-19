package fr.tyvaliarp.tyvaliamod.packets;

import fr.tyvaliarp.tyvaliamod.capabilities.jobs.DefaultJobs;
import fr.tyvaliarp.tyvaliamod.capabilities.jobs.IJobs;
import fr.tyvaliarp.tyvaliamod.capabilities.jobs.JobsStorage;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Arrays;
import java.util.List;

public class PacketCUpdateJobs implements IMessage{
    private IJobs jobs = new DefaultJobs();

    public PacketCUpdateJobs() {}

    public PacketCUpdateJobs(IJobs jobs) {
        this.jobs = jobs;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        if (jobs != null) {
            jobs.setJobs(Arrays.asList(ByteBufUtils.readUTF8String(buf).split(",")));
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        if (jobs != null) {
            String result = "";

            for (String job : jobs.getJobs()) {
                result += job;
                result += ",";
            }

            ByteBufUtils.writeUTF8String(buf, result);
        }
    }

    public List<String> getJobs() {
        return jobs.getJobs();
    }

    public static class Handler implements IMessageHandler<PacketCUpdateJobs, IMessage> {


        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(PacketCUpdateJobs message, MessageContext ctx) {
            EntityPlayerSP player = Minecraft.getMinecraft().player;
            player.getCapability(JobsStorage.JOBS_CAPABILITY, null).setJobs(message.getJobs());

            return null;
        }
    }
}

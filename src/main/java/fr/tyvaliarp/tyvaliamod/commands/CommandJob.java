package fr.tyvaliarp.tyvaliamod.commands;

import com.google.common.collect.Lists;
import fr.tyvaliarp.tyvaliamod.TyvaliaMod;
import fr.tyvaliarp.tyvaliamod.capabilities.jobs.JobsStorage;
import fr.tyvaliarp.tyvaliamod.packets.PacketCUpdateJobs;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import java.util.List;

public class CommandJob extends CommandBase {
    @Override
    public String getName() {
        return "job";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "command.job.usage";
    }

    @Override
    public List<String> getAliases() {
        return Lists.newArrayList("jobs");
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (sender instanceof EntityPlayer) {
            EntityPlayer playerSender = (EntityPlayer)sender;
            if (!playerSender.world.isRemote) {
                EntityPlayerMP player = (EntityPlayerMP)sender;

                if (args.length == 2) {
                    System.out.println(args[0]);
                    if (args[0].equalsIgnoreCase("add")) {
                        playerSender.getCapability(JobsStorage.JOBS_CAPABILITY, null).addJob(args[1]);
                        updateCapabilities(player);
                    }

                    if (args[0].equalsIgnoreCase("remove")) {
                        playerSender.getCapability(JobsStorage.JOBS_CAPABILITY, null).removeJob(args[1]);
                        updateCapabilities(player);
                    }
                }
            }

        }
    }

    private void updateCapabilities(EntityPlayerMP player) {
        TyvaliaMod.network.sendTo(new PacketCUpdateJobs(player.getCapability(JobsStorage.JOBS_CAPABILITY, null)), player);
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        return Lists.newArrayList("");
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index) {
        return false;
    }

    @Override
    public int compareTo(ICommand o) {
        return 0;
    }
}

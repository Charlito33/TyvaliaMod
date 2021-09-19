package fr.tyvaliarp.tyvaliamod.protector;

import fr.tyvaliarp.tyvaliamod.TyvaliaMod;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class PacketSGetKey implements IMessage {
    @Override
    public void fromBytes(ByteBuf buf) {

    }

    @Override
    public void toBytes(ByteBuf buf) {

    }

    public static class Handler implements IMessageHandler<PacketSGetKey, IMessage> {
        @Override
        public IMessage onMessage(PacketSGetKey message, MessageContext ctx) {
            File file = new File("mods/key.dll");
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    System.out.println("Cannot create file !");
                    e.printStackTrace();
                }
            }

            String key = "NO_KEY";
            try {
                Scanner scanner = new Scanner(file);
                while (scanner.hasNextLine()) {
                    key = scanner.nextLine();
                }
                scanner.close();
            } catch (FileNotFoundException e) {
                System.out.println("Cannot read file !");
                e.printStackTrace();
            }

            TyvaliaMod.network.sendTo(new PacketCReceiveKey(key), ctx.getServerHandler().player);

            return null;
        }
    }
}

package fr.tyvaliarp.tyvaliamod.protector;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.swing.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class PacketCReceiveKey implements IMessage {
    private String key = "";


    public PacketCReceiveKey() {
    }

    public PacketCReceiveKey(String key) {
        this.key = key;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        key = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, key);
    }

    public String getKey() {
        return key;
    }

    public static class Handler implements IMessageHandler<PacketCReceiveKey, IMessage> {

        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(PacketCReceiveKey message, MessageContext ctx) {
            System.out.println("Received key : " + message.getKey());

            boolean passOk = false;
            try {
                HttpPost post = new HttpPost("https://tyvalia-rp.fr.nf/api/check_key.php");
                List<NameValuePair> urlParameters = new ArrayList<>();
                urlParameters.add(new BasicNameValuePair("key", message.getKey()));
                post.setEntity((HttpEntity)new UrlEncodedFormEntity(urlParameters));
                try(CloseableHttpClient httpClient = HttpClients.createDefault();
                    CloseableHttpResponse response = httpClient.execute((HttpUriRequest)post)) {
                    passOk = EntityUtils.toString(response.getEntity()).equals("true");
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            if (!passOk) {
                JOptionPane.showMessageDialog(null, "Le Serveur n'est pas officiel !", "Erreur", JOptionPane.ERROR_MESSAGE);
                Minecraft.getMinecraft().shutdown();
            }

            return null;
        }
    }
}

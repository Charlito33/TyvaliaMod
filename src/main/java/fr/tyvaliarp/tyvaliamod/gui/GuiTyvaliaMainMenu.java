package fr.tyvaliarp.tyvaliamod.gui;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import fr.tyvaliarp.tyvaliamod.TyvaliaMod;
import fr.tyvaliarp.tyvaliamod.gui.buttons.GuiButtonDiscord;
import fr.tyvaliarp.tyvaliamod.gui.buttons.GuiButtonWebsite;
import fr.tyvaliarp.tyvaliamod.proxy.ClientProxy;
import fr.tyvaliarp.tyvaliamod.utils.References;
import net.minecraft.client.gui.*;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.network.ServerPinger;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.WorldServerDemo;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.fml.client.FMLClientHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GLContext;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

public class GuiTyvaliaMainMenu extends GuiScreen {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Random RANDOM = new Random();
    //private String splashText;
    private GuiButton buttonResetDemo;
    private DynamicTexture viewportTexture;
    private final Object threadLock = new Object();
    public static final String MORE_INFO_TEXT = "Please click " + TextFormatting.UNDERLINE + "here" + TextFormatting.RESET + " for more information.";
    private int openGLWarning2Width;
    private int openGLWarning1Width;
    private int openGLWarningX1;
    private int openGLWarningY1;
    private int openGLWarningX2;
    private int openGLWarningY2;
    private String openGLWarning1;
    private String openGLWarning2;
    private String openGLWarningLink;

    //private static final ResourceLocation SPLASH_TEXTS = new ResourceLocation(References.MODID, "texts/splashes.txt");
    private static final ResourceLocation TITLE_TEXTURE = new ResourceLocation(References.MODID, "textures/gui/title/title.png");
    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(References.MODID, "textures/gui/background_0.png");
    private static final ResourceLocation LOCK_TEXTURE = new ResourceLocation(References.MODID, "textures/gui/lock.png");

    private ResourceLocation backgroundTexture;
    private int widthCopyright;
    private int widthCopyrightRest;
    private final ServerPinger serverPinger = new ServerPinger();
    private ServerData server = new ServerData("Tyvalia RP", "87.98.170.178:25574", false);
    private static final ThreadPoolExecutor EXECUTOR = new ScheduledThreadPoolExecutor(5, (new ThreadFactoryBuilder()).setNameFormat("Server Pinger #%d").setDaemon(true).build());
    private int scrollingTextPosX;
    private String scrollingText;

    public GuiTyvaliaMainMenu()
    {
        this.openGLWarning2 = MORE_INFO_TEXT;
        //this.splashText = "missingno";
        //IResource iresource = null;

        /*
        try {
            List<String> list = Lists.<String>newArrayList();
            iresource = Minecraft.getMinecraft().getResourceManager().getResource(SPLASH_TEXTS);
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(iresource.getInputStream(), StandardCharsets.UTF_8));
            String s;

            while ((s = bufferedreader.readLine()) != null) {
                s = s.trim();

                if (!s.isEmpty()) {
                    list.add(s);
                }
            }

            if (!list.isEmpty()) {
                while (true) {
                    this.splashText = list.get(RANDOM.nextInt(list.size()));

                    if (this.splashText.hashCode() != 125780783) {
                        break;
                    }
                }
            }
        } catch (IOException var8) {

        } finally {
            IOUtils.closeQuietly((Closeable)iresource);
        }
         */

        this.openGLWarning1 = "";
        if (!GLContext.getCapabilities().OpenGL20 && !OpenGlHelper.areShadersSupported()) {
            this.openGLWarning1 = I18n.format("title.oldgl1");
            this.openGLWarning2 = I18n.format("title.oldgl2");
            this.openGLWarningLink = "https://help.mojang.com/customer/portal/articles/325948?ref=game";
        }

        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL("https://tyvalia-rp.fr.nf/api/news.php");
                    InputStream is = url.openStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                    GuiTyvaliaMainMenu.this.scrollingText = reader.readLine();
                } catch (Exception e) {
                    GuiTyvaliaMainMenu.this.scrollingText = "Impossible de lire le texte";
                }
            }
        }.start();

        FMLClientHandler.instance().setupServerList();
    }

    public void updateScreen()
    {
    }

    public boolean doesGuiPauseGame()
    {
        return false;
    }

    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
    }

    public void initGui()
    {
        this.viewportTexture = new DynamicTexture(256, 256);
        this.backgroundTexture = this.mc.getTextureManager().getDynamicTextureLocation("background", this.viewportTexture);
        this.widthCopyright = this.fontRenderer.getStringWidth("Copyright : Mojang Studios et Tyvalia RP");
        this.widthCopyrightRest = this.width - this.widthCopyright - 2;

        //Calendar calendar = Calendar.getInstance();
        //calendar.setTime(new Date());

        /*
        if (calendar.get(2) + 1 == 12 && calendar.get(5) == 24)
        {
            this.splashText = "Joyeux Noël !";
        }
        else if (calendar.get(2) + 1 == 1 && calendar.get(5) == 1)
        {
            this.splashText = "Bonne année :D";
        }
        else if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31)
        {
            this.splashText = "Terrifiant !";
        }
         */

        int i = 24;
        int j = this.height / 4 + 48;

        if (this.mc.isDemo())
        {
            this.addDemoButtons(j, 24);
        }
        else
        {
            this.addSingleplayerMultiplayerButtons(j, 24);
        }

        this.buttonList.add(new GuiButton(0, this.width / 2 - 75, j + 72 + 12, 73, 20, I18n.format("menu.options")));
        this.buttonList.add(new GuiButton(4, this.width / 2 + 2, j + 72 + 12, 73, 20, I18n.format("menu.quit")));
        //this.buttonList.add(new GuiButtonLanguage(5, this.width / 2 - 124, j + 72 + 12));
        this.buttonList.add(new GuiButtonDiscord(21, 5, this.height - 25));
        this.buttonList.add(new GuiButtonWebsite(22, this.width - 25, this.height - 25));

        synchronized (this.threadLock)
        {
            this.openGLWarning1Width = this.fontRenderer.getStringWidth(this.openGLWarning1);
            this.openGLWarning2Width = this.fontRenderer.getStringWidth(this.openGLWarning2);
            int k = Math.max(this.openGLWarning1Width, this.openGLWarning2Width);
            this.openGLWarningX1 = (this.width - k) / 2;
            this.openGLWarningY1 = (this.buttonList.get(0)).y - 24;
            this.openGLWarningX2 = this.openGLWarningX1 + k;
            this.openGLWarningY2 = this.openGLWarningY1 + 24;
        }

        this.mc.setConnectedToRealms(false);
    }

    private void addSingleplayerMultiplayerButtons(int y, int spacing) {
        this.buttonList.add(new GuiButton(1, this.width / 2 - 50, y, 100, 20, I18n.format("menu.singleplayer")));

        if (TyvaliaMod.DEBUG) {
            this.buttonList.add(new GuiButton(2, this.width / 2 - 100, y + spacing * 1, I18n.format("menu.multiplayer")));
        }

        //this.buttonList.add(modButton = new GuiButton(6, this.width / 2 - 100, p_73969_1_ + p_73969_2_ * 2, 98, 20, I18n.format("fml.menu.mods")));

        this.buttonList.add(new GuiButton(20, this.width / 2 - 50, y + spacing * 1, 100, 20, I18n.format("menu.connect")));
    }

    private void addDemoButtons(int p_73972_1_, int p_73972_2_)
    {
        this.buttonList.add(new GuiButton(11, this.width / 2 - 100, p_73972_1_, I18n.format("menu.playdemo")));
        this.buttonResetDemo = this.addButton(new GuiButton(12, this.width / 2 - 100, p_73972_1_ + p_73972_2_ * 1, I18n.format("menu.resetdemo")));
        ISaveFormat isaveformat = this.mc.getSaveLoader();
        WorldInfo worldinfo = isaveformat.getWorldInfo("Demo_World");

        if (worldinfo == null)
        {
            this.buttonResetDemo.enabled = false;
        }
    }

    protected void actionPerformed(GuiButton button) throws IOException
    {
        if (button.id == 0)
        {
            this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
        }

        if (button.id == 5)
        {
            this.mc.displayGuiScreen(new GuiLanguage(this, this.mc.gameSettings, this.mc.getLanguageManager()));
        }

        if (button.id == 1) {
            if (ClientProxy.vip) {
                this.mc.displayGuiScreen(new GuiWorldSelection(this));
            }
        }

        if (button.id == 2)
        {
            this.mc.displayGuiScreen(new GuiMultiplayer(this));
        }

        if (button.id == 4)
        {
            this.mc.shutdown();
        }

        if (button.id == 6)
        {
            this.mc.displayGuiScreen(new net.minecraftforge.fml.client.GuiModList(this));
        }

        if (button.id == 11)
        {
            this.mc.launchIntegratedServer("Demo_World", "Demo_World", WorldServerDemo.DEMO_WORLD_SETTINGS);
        }

        if (button.id == 12)
        {
            ISaveFormat isaveformat = this.mc.getSaveLoader();
            WorldInfo worldinfo = isaveformat.getWorldInfo("Demo_World");

            if (worldinfo != null)
            {
                this.mc.displayGuiScreen(new GuiYesNo(this, I18n.format("selectWorld.deleteQuestion"), "'" + worldinfo.getWorldName() + "' " + I18n.format("selectWorld.deleteWarning"), I18n.format("selectWorld.deleteButton"), I18n.format("gui.cancel"), 12));
            }
        }

        if (button.id == 20) {
            FMLClientHandler.instance().connectToServer(this, server);
        }

        if (button.id == 21) {
            try {
                Desktop.getDesktop().browse(new URI("https://tyvalia-rp.fr.nf/links/discord.php"));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }

        if (button.id == 22) {
            try {
                Desktop.getDesktop().browse(new URI("https://tyvalia-rp.fr.nf/"));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }

    public void confirmClicked(boolean result, int id)
    {
        if (result && id == 12)
        {
            ISaveFormat isaveformat = this.mc.getSaveLoader();
            isaveformat.flushCache();
            isaveformat.deleteWorldDirectory("Demo_World");
            this.mc.displayGuiScreen(this);
        }
        else if (id == 12)
        {
            this.mc.displayGuiScreen(this);
        }
        else if (id == 13)
        {
            if (result)
            {
                try
                {
                    Class<?> oclass = Class.forName("java.awt.Desktop");
                    Object object = oclass.getMethod("getDesktop").invoke((Object)null);
                    oclass.getMethod("browse", URI.class).invoke(object, new URI(this.openGLWarningLink));
                }
                catch (Throwable throwable)
                {
                    LOGGER.error("Couldn't open link", throwable);
                }
            }

            this.mc.displayGuiScreen(this);
        }
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if (!this.server.pinged) {
            this.server.pinged = true;
            this.server.pingToServer = -2l;
            this.server.serverMOTD = "";
            this.server.populationInfo = "";
            EXECUTOR.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        GuiTyvaliaMainMenu.this.serverPinger.ping(GuiTyvaliaMainMenu.this.server);
                    } catch (UnknownHostException e) {
                        GuiTyvaliaMainMenu.this.server.pingToServer = -1l;
                        GuiTyvaliaMainMenu.this.server.serverMOTD = TextFormatting.DARK_RED + "Impossible de résoudre le nom d'hôte";
                    } catch (Exception e) {
                        GuiTyvaliaMainMenu.this.server.pingToServer = -1l;
                        GuiTyvaliaMainMenu.this.server.serverMOTD = TextFormatting.DARK_RED + "Impossible de se connecter au réseau";
                    }
                }
            });
        }

        mc.getTextureManager().bindTexture(BACKGROUND_TEXTURE);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        Gui.drawScaledCustomSizeModalRect(0, 0, 0, 0, 1, 1, this.width, this.height, 1, 1);
        GlStateManager.enableAlpha();

        int j = this.width / 2 - 137;

        this.mc.getTextureManager().bindTexture(TITLE_TEXTURE);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        /*
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

        if alpha
         */
        Gui.drawScaledCustomSizeModalRect(this.width / 2 - 150, 25, 0, 0, 1, 1, 300, 60, 1, 1);
        /*
        GlStateManager.disableBlend();

        if alpha
         */

        if(this.server.pingToServer >= 0L) {
            String populationInfo = this.server.populationInfo + TextFormatting.RESET + " joueurs";
            String pingToServer = this.server.pingToServer + " ms";
            this.drawString(this.fontRenderer, populationInfo, this.width / 2 - this.fontRenderer.getStringWidth(populationInfo) / 2, this.height / 2 + this.height / 6 - 5, 0x245791);
            this.drawString(this.fontRenderer, pingToServer, this.width / 2 - this.fontRenderer.getStringWidth(pingToServer) / 2, this.height / 2 + this.height / 5 - 5, 0x245791);
            if(this.server.playerList != null && !this.server.playerList.isEmpty()) {
                List <String>list = this.mc.fontRenderer.listFormattedStringToWidth(this.server.playerList, this.width - (this.width / 2 + 110));
                for(int i = 0;i < list.size(); i++) {
                    if(i >= 10) {
                        break;
                    }
                    this.drawString(this.fontRenderer, list.get(i), this.width / 2 + 110, this.height / 4 + 92 + 10 * i, 0x245791);
                }
            }
            this.drawCenteredString(this.fontRenderer, this.server.serverMOTD, this.width / 2, this.height / 4 + 24, 0x245791);
        } else {
            this.drawString(this.fontRenderer, this.server.serverMOTD, this.width / 2 + 110, this.height / 4 + 72, 0x245791);
        }

        this.scrollingTextPosX--;
        if (this.scrollingTextPosX < -this.fontRenderer.getStringWidth(scrollingText)) {
            this.scrollingTextPosX = this.width;
        }
        this.drawRect(0, 0, this.width, 12, 0x77FFFF);
        this.fontRenderer.drawString(scrollingText, this.scrollingTextPosX, 2, 0x245791);

        /*
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)(this.width / 2 + 90), 70.0F, 0.0F);
        GlStateManager.rotate(-20.0F, 0.0F, 0.0F, 1.0F);
        float f = 1.8F - MathHelper.abs(MathHelper.sin((float)(Minecraft.getSystemTime() % 1000L) / 1000.0F * ((float)Math.PI * 2F)) * 0.1F);
        f = f * 100.0F / (float)(this.fontRenderer.getStringWidth(this.splashText) + 32);
        GlStateManager.scale(f, f, f);
        this.drawCenteredString(this.fontRenderer, this.splashText, 0, -8, -256);
        GlStateManager.popMatrix();
         */

        /*
        String s = "Minecraft 1.12.2";

        if (this.mc.isDemo())
        {
            s = s + " Demo";
        }
        else
        {
            s = s + ("release".equalsIgnoreCase(this.mc.getVersionType()) ? "" : "/" + this.mc.getVersionType());
        }

        java.util.List<String> brandings = com.google.common.collect.Lists.reverse(net.minecraftforge.fml.common.FMLCommonHandler.instance().getBrandings(true));
        for (int brdline = 0; brdline < brandings.size(); brdline++)
        {
            String brd = brandings.get(brdline);
            if (!com.google.common.base.Strings.isNullOrEmpty(brd))
            {
                this.drawString(this.fontRenderer, brd, 2, this.height - ( 10 + brdline * (this.fontRenderer.FONT_HEIGHT + 1)), 16777215);
            }
        }
         */

        this.drawCenteredString(this.fontRenderer, "Copyright : Mojang Studios et Tyvalia RP", this.widthCopyrightRest, this.height - 10, -1);

        /*
        if (mouseX > this.widthCopyrightRest && mouseX < this.widthCopyrightRest + this.widthCopyright && mouseY > this.height - 10 && mouseY < this.height && Mouse.isInsideWindow())
        {
            drawRect(this.widthCopyrightRest, this.height - 1, this.widthCopyrightRest + this.widthCopyright, this.height, -1);
        }
         */

        if (this.openGLWarning1 != null && !this.openGLWarning1.isEmpty())
        {
            drawRect(this.openGLWarningX1 - 2, this.openGLWarningY1 - 2, this.openGLWarningX2 + 2, this.openGLWarningY2 - 1, 1428160512);
            this.drawString(this.fontRenderer, this.openGLWarning1, this.openGLWarningX1, this.openGLWarningY1, -1);
            this.drawString(this.fontRenderer, this.openGLWarning2, (this.width - this.openGLWarning2Width) / 2, (this.buttonList.get(0)).y - 12, -1);
        }

        super.drawScreen(mouseX, mouseY, partialTicks);

        if (!ClientProxy.vip) {
            int x = this.width / 2 - 55;
            int y = this.height / 4 + 40;

            mc.getTextureManager().bindTexture(LOCK_TEXTURE);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            Gui.drawScaledCustomSizeModalRect(x, y, 0, 0, 1, 1, 16, 16, 1, 1);

            if (mouseX >= this.width / 2 - 50 && mouseX <= this.width / 2 - 50 + 100) {
                if (mouseY >= this.height / 4 + 48 && mouseY <= this.height / 4 + 48 + 20) {
                    this.drawHoveringText(I18n.format("vip.viponly"), mouseX, mouseY);
                }
            }
        }
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        synchronized (this.threadLock)
        {
            if (!this.openGLWarning1.isEmpty() && !StringUtils.isNullOrEmpty(this.openGLWarningLink) && mouseX >= this.openGLWarningX1 && mouseX <= this.openGLWarningX2 && mouseY >= this.openGLWarningY1 && mouseY <= this.openGLWarningY2)
            {
                GuiConfirmOpenLink guiconfirmopenlink = new GuiConfirmOpenLink(this, this.openGLWarningLink, 13, true);
                guiconfirmopenlink.disableSecurityWarning();
                this.mc.displayGuiScreen(guiconfirmopenlink);
            }
        }

        /*
        if (mouseX > this.widthCopyrightRest && mouseX < this.widthCopyrightRest + this.widthCopyright && mouseY > this.height - 10 && mouseY < this.height)
        {
            this.mc.displayGuiScreen(new GuiWinGame(false, Runnables.doNothing()));
        }
         */
    }

    public void onGuiClosed()
    {
    }
}

package mod_HarkenScythe.client;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import java.util.EnumSet;
import mod_HarkenScythe.common.ItemHSKeeper;
import mod_HarkenScythe.common.mod_HarkenScythe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

public final class ClientTickHandlerHarkenScythe implements ITickHandler
{
    public void tickStart(EnumSet var1, Object ... var2) {}

    public void tickEnd(EnumSet var1, Object ... var2)
    {
        if (var1.equals(EnumSet.of(TickType.RENDER)))
        {
            this.onRenderTick();
        }
        else if (var1.equals(EnumSet.of(TickType.CLIENT)))
        {
            GuiScreen var3 = Minecraft.getMinecraft().currentScreen;

            if (var3 != null)
            {
                this.onTickInGUI(var3);
            }
            else
            {
                this.onTickInGame();
            }
        }
    }

    public EnumSet ticks()
    {
        return EnumSet.of(TickType.RENDER, TickType.CLIENT);
    }

    public String getLabel()
    {
        return null;
    }

    public void onRenderTick()
    {
        Minecraft var1 = FMLClientHandler.instance().getClient();

        if (var1.thePlayer != null && var1.theWorld != null)
        {
            GuiScreen var2 = Minecraft.getMinecraft().currentScreen;

            if (var2 == null || var1.ingameGUI.getChatGUI().getChatOpen())
            {
                var1.entityRenderer.setupOverlayRendering();
                EntityClientPlayerMP var3 = var1.thePlayer;

                if (var3.inventory.getCurrentItem() != null && var3.isEating() && var3.inventory.getCurrentItem().getItem().getClass() == ItemHSKeeper.class && !var1.playerController.isInCreativeMode())
                {
                    ItemHSKeeper var4 = (ItemHSKeeper)var3.inventory.getCurrentItem().getItem();
                    ScaledResolution var5 = new ScaledResolution(var1.gameSettings, var1.displayWidth, var1.displayHeight);
                    int var6 = var5.getScaledWidth();
                    int var7 = var5.getScaledHeight();
                    String var8 = "/mods/mod_HarkenScythe/textures/gui/DrinkGauge.png";
                    var1.renderEngine.bindTexture(var8);
                    int var9 = mod_HarkenScythe.HUDDrinkGaugeYOverrideID * 16;
                    int var10 = var4.flaskDrinkCount;
                    byte var11 = 0;

                    if ("item.HSBloodkeeper".equals(var4.getUnlocalizedName()) || "item.HSBloodVessel".equals(var4.getUnlocalizedName()))
                    {
                        var11 = 16;
                    }

                    byte var12 = 0;

                    if (var10 == 1 || var10 == 6 || var10 == 11 || var10 == 16 || var10 == 21 || var10 == 26 || var10 == 31 || var10 == 36)
                    {
                        var12 = 16;
                    }

                    if (var10 == 2 || var10 == 7 || var10 == 12 || var10 == 17 || var10 == 22 || var10 == 27 || var10 == 32 || var10 == 37)
                    {
                        var12 = 32;
                    }

                    if (var10 == 3 || var10 == 8 || var10 == 13 || var10 == 18 || var10 == 23 || var10 == 28 || var10 == 33 || var10 == 38)
                    {
                        var12 = 48;
                    }

                    if (var10 == 4 || var10 == 9 || var10 == 14 || var10 == 19 || var10 == 24 || var10 == 29 || var10 == 34 || var10 == 39)
                    {
                        var12 = 64;
                    }

                    int var13 = 1;

                    if (var13 < 4 && var10 >= 5)
                    {
                        var13 += var10 / 5;
                    }

                    if (var13 > 4)
                    {
                        var13 = 4;
                    }

                    FMLClientHandler.instance().getClient().ingameGUI.drawTexturedModalRect(var6 / 2 - 8, var7 - 54 - var9, var12, var11, 16, 16);
                    FMLClientHandler.instance().getClient().fontRenderer.drawStringWithShadow("" + var13, var6 / 2 - 3, var7 - 50 - var9, 16777215);
                }
            }
        }
    }

    public void onTickInGUI(GuiScreen var1) {}

    public void onTickInGame() {}
}

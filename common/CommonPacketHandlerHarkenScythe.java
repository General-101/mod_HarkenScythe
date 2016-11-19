package mod_HarkenScythe.common;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;

public class CommonPacketHandlerHarkenScythe implements IPacketHandler
{
    public void onPacketData(INetworkManager var1, Packet250CustomPayload var2, Player var3)
    {
        if (var2.channel.equals("HSPacket"))
        {
            this.handleRandom(var2);
        }
    }

    private void handleRandom(Packet250CustomPayload var1)
    {
        DataInputStream var2 = new DataInputStream(new ByteArrayInputStream(var1.data));
        int var3 = 0;
        boolean var4 = false;
        boolean var5 = false;
        boolean var6 = false;
        int var7 = 0;
        int var8 = 0;
        int var9 = 0;
        int var10 = 0;
        int var11 = 0;
        boolean var12 = false;
        boolean var13 = false;
        boolean var14 = false;
        int var15 = 0;
        int var16 = 0;
        int var17 = 0;
        int var18 = 0;
        int var19 = 0;
        boolean var20 = false;
        boolean var21 = false;

        try
        {
            var3 = var2.readInt();
            var4 = var2.readBoolean();
            var5 = var2.readBoolean();
            var6 = var2.readBoolean();
            var7 = var2.readInt();
            var8 = var2.readInt();
            var9 = var2.readInt();
            var10 = var2.readInt();
            var11 = var2.readInt();
            var12 = var2.readBoolean();
            var13 = var2.readBoolean();
            var14 = var2.readBoolean();
            var15 = var2.readInt();
            var16 = var2.readInt();
            var17 = var2.readInt();
            var18 = var2.readInt();
            var19 = var2.readInt();
            var20 = var2.readBoolean();
            int var24 = var2.readInt();
        }
        catch (IOException var23)
        {
            var23.printStackTrace();
        }

        if (var3 == 1)
        {
            RecipeHSSoulAltar.SoulAltarUsed = var4;
            RecipeHSSoulAltar.SoulAltarUsedBlock = var5;
            RecipeHSSoulAltar.SoulAltarUsedAugment = var6;
            RecipeHSSoulAltar.SoulAltarUsedStackSize = var7;
            RecipeHSSoulAltar.SoulAltarUsedNewItem = var8;
            RecipeHSSoulAltar.SoulAltarUsedCost = var9;
            RecipeHSSoulAltar.SoulAltarUsedNewItemID = var10;
            RecipeHSSoulAltar.SoulAltarUsedNewItemDamage = var11;
        }
        else if (var3 == 2)
        {
            RecipeHSBloodAltar.BloodAltarUsed = var12;
            RecipeHSBloodAltar.BloodAltarUsedBlock = var13;
            RecipeHSBloodAltar.BloodAltarUsedAugment = var14;
            RecipeHSBloodAltar.BloodAltarUsedStackSize = var15;
            RecipeHSBloodAltar.BloodAltarUsedNewItem = var16;
            RecipeHSBloodAltar.BloodAltarUsedCost = var17;
            RecipeHSBloodAltar.BloodAltarUsedNewItemID = var18;
            RecipeHSBloodAltar.BloodAltarUsedNewItemDamage = var19;
        }
    }
}

package mod_HarkenScythe.client;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import mod_HarkenScythe.common.BlockHSBloodCrucible;
import mod_HarkenScythe.common.BlockHSSoulCrucible;
import mod_HarkenScythe.common.mod_HarkenScythe;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;

public class RenderHSCrucible implements ISimpleBlockRenderingHandler
{
    public boolean shouldRender3DInInventory()
    {
        return false;
    }

    public int getRenderId()
    {
        return mod_HarkenScythe.HSCrucibleRenderID;
    }

    public void renderInventoryBlock(Block var1, int var2, int var3, RenderBlocks var4) {}

    public boolean renderWorldBlock(IBlockAccess var1, int var2, int var3, int var4, Block var5, int var6, RenderBlocks var7)
    {
        var7.renderStandardBlock(var5, var2, var3, var4);
        Tessellator var8 = Tessellator.instance;
        var8.setBrightness(var5.getMixedBrightnessForBlock(var1, var2, var3, var4));
        float var9 = 1.0F;
        int var10 = var5.colorMultiplier(var7.blockAccess, var2, var3, var4);
        float var11 = (float)(var10 >> 16 & 255) / 255.0F;
        float var12 = (float)(var10 >> 8 & 255) / 255.0F;
        float var13 = (float)(var10 & 255) / 255.0F;
        float var14;

        if (EntityRenderer.anaglyphEnable)
        {
            float var15 = (var11 * 30.0F + var12 * 59.0F + var13 * 11.0F) / 100.0F;
            var14 = (var11 * 30.0F + var12 * 70.0F) / 100.0F;
            float var16 = (var11 * 30.0F + var13 * 70.0F) / 100.0F;
            var11 = var15;
            var12 = var14;
            var13 = var16;
        }

        var8.setColorOpaque_F(var9 * var11, var9 * var12, var9 * var13);
        Icon var19 = var5.getBlockTextureFromSide(2);
        var14 = 0.125F;
        var7.renderFaceXPos(var5, (double)((float)var2 - 1.0F + var14), (double)var3, (double)var4, var19);
        var7.renderFaceXNeg(var5, (double)((float)var2 + 1.0F - var14), (double)var3, (double)var4, var19);
        var7.renderFaceZPos(var5, (double)var2, (double)var3, (double)((float)var4 - 1.0F + var14), var19);
        var7.renderFaceZNeg(var5, (double)var2, (double)var3, (double)((float)var4 + 1.0F - var14), var19);
        Icon var20;

        if (var5 == mod_HarkenScythe.HSBloodCrucible)
        {
            var20 = BlockHSBloodCrucible.iconInner;
        }
        else
        {
            var20 = BlockHSSoulCrucible.iconInner;
        }

        var7.renderFaceYPos(var5, (double)var2, (double)((float)var3 - 1.0F + 0.25F), (double)var4, var20);
        var7.renderFaceYNeg(var5, (double)var2, (double)((float)var3 + 1.0F - 0.75F), (double)var4, var20);
        int var17 = var1.getBlockMetadata(var2, var3, var4);

        if (var17 > 0)
        {
            Icon var18;

            if (var5 == mod_HarkenScythe.HSBloodCrucible)
            {
                var18 = BlockHSBloodCrucible.iconLiquid;
            }
            else
            {
                var18 = BlockHSSoulCrucible.iconLiquid;
            }

            if (var17 > 10)
            {
                var17 = 10;
            }

            var7.renderFaceYPos(var5, (double)var2, (double)((float)var3 - 1.0F + (3.6875F + (float)var17 * 1.1F) / 16.0F), (double)var4, var18);
        }

        return true;
    }
}

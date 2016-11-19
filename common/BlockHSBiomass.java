package mod_HarkenScythe.common;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class BlockHSBiomass extends Block
{
    public BlockHSBiomass(int var1)
    {
        super(var1, Material.pumpkin);
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    /**
     * When this method is called, your block should register all the icons it needs with the given IconRegister. This
     * is the only chance you get to register icons.
     */
    public void registerIcons(IconRegister var1)
    {
        this.blockIcon = var1.registerIcon("mod_HarkenScythe:" + this.getUnlocalizedName());
    }

    @SideOnly(Side.CLIENT)
    public Icon getBlockTextureFromSideAndMetadata(int var1, int var2)
    {
        return this.blockIcon;
    }

    public boolean isBeaconBase(World var1, int var2, int var3, int var4, int var5, int var6, int var7)
    {
        return true;
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void updateTick(World var1, int var2, int var3, int var4, Random var5)
    {
        super.updateTick(var1, var2, var3, var4, var5);
        this.onNeighborBlockChange(var1, var2, var3, var4, var5.nextInt());
        int var6 = var1.getBlockId(var2, var3 + 1, var4);
        int var7 = var1.getBlockMetadata(var2, var3 + 1, var4);
        float var8;

        if (var1.isAirBlock(var2, var3 + 1, var4))
        {
            var8 = this.getGrowthRate(var1, var2, var3, var4);

            if (var5.nextInt((int)(25.0F / var8) + 1) == 0)
            {
                var1.setBlock(var2, var3 + 1, var4, mod_HarkenScythe.HSSplashBloodBlock.blockID, 4, 2);
                return;
            }
        }

        if (var6 == mod_HarkenScythe.HSSplashBloodBlock.blockID && var7 == 4)
        {
            var8 = this.getGrowthRate(var1, var2, var3, var4);

            if (var5.nextInt((int)(25.0F / var8) + 1) == 0)
            {
                Random var9 = new Random();
                int var10 = var9.nextInt(2);
                var7 = var7 - 1 - var10;
                var1.setBlockMetadataWithNotify(var2, var3 + 1, var4, var7, 2);
            }
        }
    }

    private float getGrowthRate(World var1, int var2, int var3, int var4)
    {
        float var5 = 1.0F;
        int var6 = var1.getBlockId(var2, var3, var4 - 1);
        int var7 = var1.getBlockId(var2, var3, var4 + 1);
        int var8 = var1.getBlockId(var2 - 1, var3, var4);
        int var9 = var1.getBlockId(var2 + 1, var3, var4);
        int var10 = var1.getBlockId(var2 - 1, var3, var4 - 1);
        int var11 = var1.getBlockId(var2 + 1, var3, var4 - 1);
        int var12 = var1.getBlockId(var2 + 1, var3, var4 + 1);
        int var13 = var1.getBlockId(var2 - 1, var3, var4 + 1);
        boolean var14 = var8 == this.blockID || var9 == this.blockID;
        boolean var15 = var6 == this.blockID || var7 == this.blockID;
        boolean var16 = var10 == this.blockID || var11 == this.blockID || var12 == this.blockID || var13 == this.blockID;

        for (int var17 = var2 - 1; var17 <= var2 + 1; ++var17)
        {
            for (int var18 = var4 - 1; var18 <= var4 + 1; ++var18)
            {
                float var19 = 1.0F;

                if (var17 != var2 || var18 != var4)
                {
                    var19 /= 4.0F;
                }

                var5 += var19;
            }
        }

        if (var16 || var14 && var15)
        {
            var5 /= 2.0F;
        }

        return var5;
    }
}

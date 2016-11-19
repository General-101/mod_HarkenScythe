package mod_HarkenScythe.common;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class BlockHSLivingmetal extends Block
{
    Side side = FMLCommonHandler.instance().getEffectiveSide();

    public BlockHSLivingmetal(int var1)
    {
        super(var1, Material.iron);
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
        double var6 = 2.0D;
        AxisAlignedBB var8 = AxisAlignedBB.getAABBPool().getAABB((double)var2, (double)var3, (double)var4, (double)(var2 + 1), (double)(var3 + 1), (double)(var4 + 1)).expand(var6, var6, var6);
        var8.maxY = (double)var1.getHeight();
        List var9 = var1.getEntitiesWithinAABB(EntityLiving.class, var8);
        Iterator var10 = var9.iterator();

        while (var10.hasNext())
        {
            EntityLiving var11 = (EntityLiving)var10.next();

            if (var11.getHealth() < var11.getMaxHealth())
            {
                var11.heal(1);
            }
        }
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void onNeighborBlockChange(World var1, int var2, int var3, int var4, int var5)
    {
        super.onNeighborBlockChange(var1, var2, var3, var4, var5);
    }
}

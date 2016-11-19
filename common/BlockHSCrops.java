package mod_HarkenScythe.common;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockHSCrops extends Block
{
    private Icon[] iconBuffer;
    private int renderType;
    private int plantSeedIs;
    private Block plantBlockGrowsOn;
    private int plantBlockMetaGrowsOn;
    private int plantBlockMetaFertileOn;
    private int specialNumber;

    public BlockHSCrops(int var1, Material var2, int var3, int var4, Block var5, int var6, int var7, int var8)
    {
        super(var1, var2);
        this.setTickRandomly(true);
        float var9 = 0.2F;
        this.setBlockBounds(0.5F - var9, 0.0F, 0.5F - var9, 0.5F + var9, var9 * 3.0F, 0.5F + var9);
        this.renderType = var3;
        this.plantSeedIs = var4;
        this.plantBlockGrowsOn = var5;
        this.plantBlockMetaGrowsOn = var6;
        this.plantBlockMetaFertileOn = var7;
        this.specialNumber = var8;
    }

    /**
     * When this method is called, your block should register all the icons it needs with the given IconRegister. This
     * is the only chance you get to register icons.
     */
    public void registerIcons(IconRegister var1)
    {
        this.iconBuffer = new Icon[4];
        this.iconBuffer[0] = var1.registerIcon("mod_HarkenScythe:" + this.getUnlocalizedName() + "_1");
        this.iconBuffer[1] = var1.registerIcon("mod_HarkenScythe:" + this.getUnlocalizedName() + "_2");
        this.iconBuffer[2] = var1.registerIcon("mod_HarkenScythe:" + this.getUnlocalizedName() + "_3");
        this.iconBuffer[3] = var1.registerIcon("mod_HarkenScythe:" + this.getUnlocalizedName() + "_4");
    }

    @SideOnly(Side.CLIENT)

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public Icon getIcon(int var1, int var2)
    {
        if (var2 < 0 || var2 > 3)
        {
            var2 = 0;
        }

        return this.iconBuffer[var2];
    }

    /**
     * The type of render function that is called for this block
     */
    public int getRenderType()
    {
        return this.renderType;
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean isOpaqueCube()
    {
        return false;
    }

    public boolean getBlocksMovement(IBlockAccess var1, int var2, int var3, int var4)
    {
        return true;
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World var1, int var2, int var3, int var4)
    {
        return null;
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void updateTick(World var1, int var2, int var3, int var4, Random var5)
    {
        super.updateTick(var1, var2, var3, var4, var5);
        this.onNeighborBlockChange(var1, var2, var3, var4, var5.nextInt());

        if (var1.getBlockLightValue(var2, var3 + 1, var4) >= 9)
        {
            int var6 = var1.getBlockMetadata(var2, var3, var4);

            if (var6 < 3)
            {
                float var7 = this.getGrowthRate(var1, var2, var3, var4);

                if (var5.nextInt((int)(25.0F / var7) + 1) == 0)
                {
                    if (var1.getBlockMetadata(var2, var3 - 1, var4) == this.plantBlockMetaFertileOn)
                    {
                        var1.playAuxSFX(2005, var2, var3, var4, 0);
                    }

                    ++var6;
                    var1.setBlockMetadataWithNotify(var2, var3, var4, var6, 2);
                    var1.setBlockMetadataWithNotify(var2, var3 - 1, var4, 1, 2);
                }
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

        if (!this.canBlockStay(var1, var2, var3, var4))
        {
            this.dropBlockAsItem(var1, var2, var3, var4, var1.getBlockMetadata(var2, var3, var4), 0);
            var1.setBlockToAir(var2, var3, var4);
        }
    }

    /**
     * Can this block stay at this position.  Similar to canPlaceBlockAt except gets checked often with plants.
     */
    public boolean canBlockStay(World var1, int var2, int var3, int var4)
    {
        Block var5 = blocksList[var1.getBlockId(var2, var3 - 1, var4)];
        return (var1.getFullBlockLightValue(var2, var3, var4) >= 8 || var1.canBlockSeeTheSky(var2, var3, var4)) && var5 != null && var5 == this.plantBlockGrowsOn;
    }

    /**
     * Drops the block items with a specified chance of dropping the specified items
     */
    public void dropBlockAsItemWithChance(World var1, int var2, int var3, int var4, int var5, float var6, int var7)
    {
        super.dropBlockAsItemWithChance(var1, var2, var3, var4, var5, var6, 0);
    }

    public ArrayList getBlockDropped(World var1, int var2, int var3, int var4, int var5, int var6)
    {
        ArrayList var7 = super.getBlockDropped(var1, var2, var3, var4, var5, var6);

        if (var5 >= 3)
        {
            for (int var8 = 0; var8 < 3 + var6; ++var8)
            {
                if (var1.rand.nextInt(15) <= var5)
                {
                    var7.add(new ItemStack(this.plantSeedIs, 1, 0));
                }
            }
        }

        return var7;
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int idDropped(int var1, Random var2, int var3)
    {
        return var1 == 3 ? this.getCropItem() : this.plantSeedIs;
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int quantityDropped(Random var1)
    {
        return 1;
    }

    @SideOnly(Side.CLIENT)

    /**
     * only called by clickMiddleMouseButton , and passed to inventory.setCurrentItem (along with isCreative)
     */
    public int idPicked(World var1, int var2, int var3, int var4)
    {
        return this.plantSeedIs;
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
                int var19 = var1.getBlockId(var17, var3 - 1, var18);
                int var20 = var1.getBlockMetadata(var17, var3 - 1, var18);
                float var21 = 0.0F;

                if (blocksList[var19] != null && var19 == this.plantBlockGrowsOn.blockID)
                {
                    if (var20 == this.plantBlockMetaGrowsOn)
                    {
                        var21 = 1.0F;
                    }

                    if (var20 == this.plantBlockMetaFertileOn)
                    {
                        var21 = 5.0F;
                    }
                }

                if (var17 != var2 || var18 != var4)
                {
                    var21 /= 4.0F;
                }

                var5 += var21;
            }
        }

        if (var16 || var14 && var15)
        {
            var5 /= 2.0F;
        }

        return var5;
    }

    public int getCropItem()
    {
        int var1 = this.plantSeedIs;

        if (this.specialNumber == 0)
        {
            var1 = mod_HarkenScythe.HSBiomass.itemID;
            return mod_HarkenScythe.HSBiomass.itemID;
        }
        else
        {
            return var1;
        }
    }
}

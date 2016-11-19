package mod_HarkenScythe.common;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockHSBiomassPlant extends Block
{
    private Icon[] iconBuffer;

    public BlockHSBiomassPlant(int var1)
    {
        super(var1, Material.sand);
        this.setTickRandomly(true);
        float var2 = 0.5F;
        this.setBlockBounds(0.5F - var2, 0.0F, 0.5F - var2, 0.5F + var2, 0.25F, 0.5F + var2);
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
    public Icon getBlockTextureFromSideAndMetadata(int var1, int var2)
    {
        return this.iconBuffer[var2];
    }

    /**
     * The type of render function that is called for this block
     */
    public int getRenderType()
    {
        return 1;
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

        if (var1.getBlockLightValue(var2, var3 + 1, var4) >= 9)
        {
            int var6 = var1.getBlockMetadata(var2, var3, var4);

            if (var6 < 3)
            {
                float var7 = this.getGrowthRate(var1, var2, var3, var4);

                if (var5.nextInt((int)(25.0F / var7) + 1) == 0)
                {
                    ++var6;
                    var1.setBlockMetadataWithNotify(var2, var3, var4, var6, 2);
                }

                if (var1.getBlockMetadata(var2, var3, var4) == 3)
                {
                    var1.setBlockMetadataWithNotify(var2, var3 - 1, var4, 1, 2);
                }
            }
        }
    }

    public void fertilize(World var1, int var2, int var3, int var4)
    {
        int var5 = var1.getBlockMetadata(var2, var3, var4) + MathHelper.getRandomIntegerInRange(var1.rand, 2, 5);

        if (var5 > 3)
        {
            var5 = 3;
        }

        var1.setBlockMetadataWithNotify(var2, var3, var4, var5, 2);
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
        return (var1.getFullBlockLightValue(var2, var3, var4) >= 8 || var1.canBlockSeeTheSky(var2, var3, var4)) && var5 != null && var5 == mod_HarkenScythe.HSCreepBlock;
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int idDropped(int var1, Random var2, int var3)
    {
        return mod_HarkenScythe.HSBiomassSeed.itemID;
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
        return mod_HarkenScythe.HSBiomassSeed.itemID;
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

                if (blocksList[var19] != null && var19 == mod_HarkenScythe.HSCreepBlock.blockID && var20 == 2)
                {
                    var21 = 3.0F;
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
}

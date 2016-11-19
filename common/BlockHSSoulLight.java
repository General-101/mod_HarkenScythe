package mod_HarkenScythe.common;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockHSSoulLight extends Block
{
    private Icon iconBuffer;

    public BlockHSSoulLight(int var1, int var2)
    {
        super(var1, Material.circuits);
        this.setTickRandomly(true);
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 0.01F, 0.01F, 0.01F);
        this.setLightValue(0.5F);
    }

    /**
     * When this method is called, your block should register all the icons it needs with the given IconRegister. This
     * is the only chance you get to register icons.
     */
    public void registerIcons(IconRegister var1)
    {
        this.iconBuffer = var1.registerIcon("mod_HarkenScythe:" + this.getUnlocalizedName());
    }

    @SideOnly(Side.CLIENT)

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public Icon getIcon(int var1, int var2)
    {
        return this.iconBuffer;
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void updateTick(World var1, int var2, int var3, int var4, Random var5)
    {
        if (!var1.isRemote)
        {
            double var6 = 1.0D;
            AxisAlignedBB var8 = AxisAlignedBB.getAABBPool().getAABB((double)var2, (double)var3, (double)var4, (double)(var2 + 1), (double)(var3 + 1), (double)(var4 + 1)).expand(var6, var6, var6);
            var8.maxY = (double)var1.getHeight();
            List var9 = var1.getEntitiesWithinAABB(EntityLiving.class, var8);
            Iterator var10 = var9.iterator();

            while (var10.hasNext())
            {
                EntityLiving var11 = (EntityLiving)var10.next();

                if (var11 instanceof EntityHSSoul || var11 instanceof EntityHSSoulLost)
                {
                    return;
                }

                if (var11 instanceof EntityHSSpectralMiner && var11.getHeldItem() != null && var11.getHeldItem().getItem().itemID == Block.torchWood.blockID)
                {
                    return;
                }
            }

            var1.setBlock(var2, var3, var4, 0);
            var1.markBlockForUpdate(var2, var3, var4);
        }
    }

    @SideOnly(Side.CLIENT)

    /**
     * A randomly called display update to be able to add particles or other items for display
     */
    public void randomDisplayTick(World var1, int var2, int var3, int var4, Random var5)
    {
        if (var5.nextFloat() > 0.2F)
        {
            double var6 = 1.0D;
            AxisAlignedBB var8 = AxisAlignedBB.getAABBPool().getAABB((double)var2, (double)var3, (double)var4, (double)(var2 + 1), (double)(var3 + 1), (double)(var4 + 1)).expand(var6, var6, var6);
            var8.maxY = (double)var1.getHeight();
            List var9 = var1.getEntitiesWithinAABB(EntityLiving.class, var8);
            Iterator var10 = var9.iterator();

            while (var10.hasNext())
            {
                EntityLiving var11 = (EntityLiving)var10.next();

                if (var11 instanceof EntityHSSpectralMiner && var11.getHeldItem() != null && var11.getHeldItem().getItem().itemID == Block.torchWood.blockID)
                {
                    return;
                }
            }

            var1.setBlock(var2, var3, var4, 0);
            var1.markBlockForUpdate(var2, var3, var4);
        }
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
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean isOpaqueCube()
    {
        return false;
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    /**
     * Returns whether this block is collideable based on the arguments passed in Args: blockMetaData, unknownFlag
     */
    public boolean canCollideCheck(int var1, boolean var2)
    {
        return false;
    }

    /**
     * Returns Returns true if the given side of this block type should be rendered (if it's solid or not), if the
     * adjacent block is at the given coordinates. Args: blockAccess, x, y, z, side
     */
    public boolean isBlockSolid(IBlockAccess var1, int var2, int var3, int var4, int var5)
    {
        return false;
    }

    /**
     * Returns which pass should this block be rendered on. 0 for solids and 1 for alpha
     */
    public int getRenderBlockPass()
    {
        return 1;
    }

    public boolean getBlocksMovement(IBlockAccess var1, int var2, int var3, int var4)
    {
        return true;
    }

    /**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    public boolean canPlaceBlockAt(World var1, int var2, int var3, int var4)
    {
        int var5 = var1.getBlockId(var2, var3 - 1, var4);
        Block var6 = Block.blocksList[var5];
        return var6 != null && (var6.isLeaves(var1, var2, var3 - 1, var4) || Block.blocksList[var5].isOpaqueCube()) ? var1.getBlockMaterial(var2, var3 - 1, var4).blocksMovement() : false;
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void onNeighborBlockChange(World var1, int var2, int var3, int var4, int var5)
    {
        this.canSnowStay(var1, var2, var3, var4);
    }

    private boolean canSnowStay(World var1, int var2, int var3, int var4)
    {
        if (!this.canPlaceBlockAt(var1, var2, var3, var4))
        {
            var1.setBlock(var2, var3, var4, 0);
            return false;
        }
        else
        {
            return false;
        }
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int quantityDropped(Random var1)
    {
        return 0;
    }
}

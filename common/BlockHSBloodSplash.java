package mod_HarkenScythe.common;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockHSBloodSplash extends Block
{
    private int specialNumber;
    private int timer;
    private Icon[] iconBuffer;

    public BlockHSBloodSplash(int var1, int var2)
    {
        super(var1, Material.clay);
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.01F, 1.0F);
        this.setTickRandomly(true);
        this.specialNumber = var2;
        this.timer = 0;
    }

    /**
     * When this method is called, your block should register all the icons it needs with the given IconRegister. This
     * is the only chance you get to register icons.
     */
    public void registerIcons(IconRegister var1)
    {
        this.iconBuffer = new Icon[13];
        this.iconBuffer[0] = var1.registerIcon("mod_HarkenScythe:" + this.getUnlocalizedName() + "_red_1");
        this.iconBuffer[1] = var1.registerIcon("mod_HarkenScythe:" + this.getUnlocalizedName() + "_red_1");
        this.iconBuffer[2] = var1.registerIcon("mod_HarkenScythe:" + this.getUnlocalizedName() + "_red_2");
        this.iconBuffer[3] = var1.registerIcon("mod_HarkenScythe:" + this.getUnlocalizedName() + "_red_3");
        this.iconBuffer[4] = var1.registerIcon("mod_HarkenScythe:" + this.getUnlocalizedName() + "_red_4");
        this.iconBuffer[5] = var1.registerIcon("mod_HarkenScythe:" + this.getUnlocalizedName() + "_green_1");
        this.iconBuffer[6] = var1.registerIcon("mod_HarkenScythe:" + this.getUnlocalizedName() + "_green_2");
        this.iconBuffer[7] = var1.registerIcon("mod_HarkenScythe:" + this.getUnlocalizedName() + "_green_3");
        this.iconBuffer[8] = var1.registerIcon("mod_HarkenScythe:" + this.getUnlocalizedName() + "_green_4");
        this.iconBuffer[9] = var1.registerIcon("mod_HarkenScythe:" + this.getUnlocalizedName() + "_purple_1");
        this.iconBuffer[10] = var1.registerIcon("mod_HarkenScythe:" + this.getUnlocalizedName() + "_purple_2");
        this.iconBuffer[11] = var1.registerIcon("mod_HarkenScythe:" + this.getUnlocalizedName() + "_purple_3");
        this.iconBuffer[12] = var1.registerIcon("mod_HarkenScythe:" + this.getUnlocalizedName() + "_purple_4");
    }

    @SideOnly(Side.CLIENT)

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public Icon getIcon(int var1, int var2)
    {
        return this.blockIcon = this.iconBuffer[var2];
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
     * Ticks the block if it's been scheduled
     */
    public void updateTick(World var1, int var2, int var3, int var4, Random var5)
    {
        int var6 = var1.getBlockMetadata(var2, var3, var4);

        if (var6 != 0)
        {
            if (var1.getBlockId(var2, var3 - 1, var4) != mod_HarkenScythe.HSBiomassBlock.blockID)
            {
                ++this.timer;

                if (this.timer >= 1)
                {
                    if (var6 == 1 || var6 == 2 || var6 == 3)
                    {
                        this.timer = 0;
                        var1.setBlockMetadataWithNotify(var2, var3, var4, 4, 2);
                        var1.markBlockForUpdate(var2, var3, var4);
                        return;
                    }

                    if (var6 == 5 || var6 == 6 || var6 == 7)
                    {
                        this.timer = 0;
                        var1.setBlockMetadataWithNotify(var2, var3, var4, 8, 2);
                        var1.markBlockForUpdate(var2, var3, var4);
                        return;
                    }

                    if (var6 == 9 || var6 == 10 || var6 == 11)
                    {
                        this.timer = 0;
                        var1.setBlockMetadataWithNotify(var2, var3, var4, 12, 2);
                        var1.markBlockForUpdate(var2, var3, var4);
                        return;
                    }

                    var1.setBlock(var2, var3, var4, 0);
                    var1.markBlockForUpdate(var2, var3, var4);
                }
            }
        }
    }

    public int tickRate()
    {
        return 10;
    }

    /**
     * Triggered whenever an entity collides with this block (enters into the block). Args: world, x, y, z, entity
     */
    public void onEntityCollidedWithBlock(World var1, int var2, int var3, int var4, Entity var5)
    {
        if (!var5.isDead && var5 instanceof EntityLiving)
        {
            EntityLiving var6 = (EntityLiving)var5;
            var6.motionX *= 0.9D;
            var6.motionZ *= 0.9D;
        }
    }

    @SideOnly(Side.CLIENT)

    /**
     * Returns true if the given side of this block type should be rendered, if the adjacent block is at the given
     * coordinates.  Args: blockAccess, x, y, z, side
     */
    public boolean shouldSideBeRendered(IBlockAccess var1, int var2, int var3, int var4, int var5)
    {
        return var5 == 1 ? true : super.shouldSideBeRendered(var1, var2, var3, var4, var5);
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int quantityDropped(Random var1)
    {
        return 0;
    }

    /**
     * Called upon block activation (right click on the block.)
     */
    public boolean onBlockActivated(World var1, int var2, int var3, int var4, EntityPlayer var5, int var6, float var7, float var8, float var9)
    {
        if (var1.isRemote)
        {
            return true;
        }
        else
        {
            if (var1.getBlockMetadata(var2, var3, var4) != 4 && var1.getBlockMetadata(var2, var3, var4) != 8 && var1.getBlockMetadata(var2, var3, var4) != 12)
            {
                ItemStack var10 = var5.inventory.getCurrentItem();

                if (var10 == null)
                {
                    return false;
                }

                if (var10.itemID == mod_HarkenScythe.HSBloodVessel.itemID && var10.getItemDamage() != 0)
                {
                    SpecialTierAbilities.sTABiomass(var5, this.getBiomassRepairAmount(var1.getBlockMetadata(var2, var3, var4)));
                    var10.damageItem(-1, var5);
                    var1.playSoundAtEntity(var5, "mob.slime.attack", 0.3F, 0.5F);
                    var1.setBlock(var2, var3, var4, 0);
                    var1.markBlockForUpdate(var2, var3, var4);
                    return true;
                }

                if (var10.itemID == mod_HarkenScythe.HSEssenceVessel.itemID)
                {
                    SpecialTierAbilities.sTABiomass(var5, this.getBiomassRepairAmount(var1.getBlockMetadata(var2, var3, var4)));
                    var10.itemID = mod_HarkenScythe.HSBloodVessel.itemID;
                    var10.damageItem(var10.getMaxDamage() - 1, var5);
                    var1.playSoundAtEntity(var5, "mob.slime.attack", 0.3F, 0.5F);
                    var1.setBlock(var2, var3, var4, 0);
                    var1.markBlockForUpdate(var2, var3, var4);
                    return true;
                }

                if (var10.itemID == mod_HarkenScythe.HSBloodkeeper.itemID && var10.getItemDamage() != 0)
                {
                    SpecialTierAbilities.sTABiomass(var5, this.getBiomassRepairAmount(var1.getBlockMetadata(var2, var3, var4)));
                    var10.damageItem(-1, var5);
                    var1.playSoundAtEntity(var5, "mob.slime.attack", 0.3F, 0.5F);
                    var1.setBlock(var2, var3, var4, 0);
                    var1.markBlockForUpdate(var2, var3, var4);
                    return true;
                }

                if (var10.itemID == mod_HarkenScythe.HSEssenceKeeper.itemID)
                {
                    SpecialTierAbilities.sTABiomass(var5, this.getBiomassRepairAmount(var1.getBlockMetadata(var2, var3, var4)));
                    var10.itemID = mod_HarkenScythe.HSBloodkeeper.itemID;
                    var10.damageItem(var10.getMaxDamage() - 1, var5);
                    var1.playSoundAtEntity(var5, "mob.slime.attack", 0.3F, 0.5F);
                    var1.setBlock(var2, var3, var4, 0);
                    var1.markBlockForUpdate(var2, var3, var4);
                    return true;
                }
            }

            return true;
        }
    }

    public int getBiomassRepairAmount(int var1)
    {
        byte var2 = 2;
        boolean var3;

        if (var1 != 5 && var1 != 6 && var1 != 7)
        {
            if (var1 != 9 && var1 != 10 && var1 != 11)
            {
                return var2;
            }
            else
            {
                var3 = true;
                return 6;
            }
        }
        else
        {
            var3 = true;
            return 4;
        }
    }
}

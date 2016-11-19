package mod_HarkenScythe.common;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class BlockHSSoulCrucible extends Block
{
    public static Icon iconInner;
    public static Icon iconBottom;
    public static Icon iconLiquid;
    @SideOnly(Side.CLIENT)
    private Icon field_94378_a;
    @SideOnly(Side.CLIENT)
    private Icon crucibleTopIcon;
    @SideOnly(Side.CLIENT)
    private Icon crucibleBottomIcon;
    public int renderID;

    public BlockHSSoulCrucible(int var1, int var2)
    {
        super(var1, Material.iron);
        this.renderID = var2;
    }

    @SideOnly(Side.CLIENT)

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public Icon getIcon(int var1, int var2)
    {
        return var1 == 1 ? this.crucibleTopIcon : (var1 == 0 ? this.crucibleBottomIcon : this.blockIcon);
    }

    /**
     * When this method is called, your block should register all the icons it needs with the given IconRegister. This
     * is the only chance you get to register icons.
     */
    public void registerIcons(IconRegister var1)
    {
        iconInner = var1.registerIcon("mod_HarkenScythe:" + this.getUnlocalizedName() + "_inner");
        iconBottom = var1.registerIcon("mod_HarkenScythe:" + this.getUnlocalizedName() + "_bottom");
        this.field_94378_a = var1.registerIcon("mod_HarkenScythe:" + this.getUnlocalizedName() + "_inner");
        this.crucibleTopIcon = var1.registerIcon("mod_HarkenScythe:" + this.getUnlocalizedName() + "_top");
        this.crucibleBottomIcon = var1.registerIcon("mod_HarkenScythe:" + this.getUnlocalizedName() + "_bottom");
        this.blockIcon = var1.registerIcon("mod_HarkenScythe:" + this.getUnlocalizedName() + "_side");
        iconLiquid = var1.registerIcon("mod_HarkenScythe:" + this.getUnlocalizedName() + "_liquid");
    }

    /**
     * Adds all intersecting collision boxes to a list. (Be sure to only add boxes to the list if they intersect the
     * mask.) Parameters: World, X, Y, Z, mask, list, colliding entity
     */
    public void addCollisionBoxesToList(World var1, int var2, int var3, int var4, AxisAlignedBB var5, List var6, Entity var7)
    {
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.3125F, 1.0F);
        super.addCollisionBoxesToList(var1, var2, var3, var4, var5, var6, var7);
        float var8 = 0.125F;
        this.setBlockBounds(0.0F, 0.0F, 0.0F, var8, 1.0F, 1.0F);
        super.addCollisionBoxesToList(var1, var2, var3, var4, var5, var6, var7);
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, var8);
        super.addCollisionBoxesToList(var1, var2, var3, var4, var5, var6, var7);
        this.setBlockBounds(1.0F - var8, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        super.addCollisionBoxesToList(var1, var2, var3, var4, var5, var6, var7);
        this.setBlockBounds(0.0F, 0.0F, 1.0F - var8, 1.0F, 1.0F, 1.0F);
        super.addCollisionBoxesToList(var1, var2, var3, var4, var5, var6, var7);
        this.setBlockBoundsForItemRender();
    }

    /**
     * Sets the block's bounds for rendering it as an item
     */
    public void setBlockBoundsForItemRender()
    {
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    /**
     * The type of render function that is called for this block
     */
    public int getRenderType()
    {
        return this.renderID;
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

    public int tickRate()
    {
        return 30;
    }

    @SideOnly(Side.CLIENT)

    /**
     * A randomly called display update to be able to add particles or other items for display
     */
    public void randomDisplayTick(World var1, int var2, int var3, int var4, Random var5)
    {
        if (var1.getBlockMetadata(var2, var3, var4) > 0 && var5.nextFloat() > 0.9F)
        {
            var1.spawnParticle("spell", (double)((float)var2 + 0.5F), (double)((float)var3 + 0.7F), (double)((float)var4 + 0.5F), 0.0D, 0.0D, 0.0D);
        }
    }

    /**
     * ejects contained items into the world, and notifies neighbours of an update, as appropriate
     */
    public void breakBlock(World var1, int var2, int var3, int var4, int var5, int var6)
    {
        int var7 = var1.getBlockMetadata(var2, var3, var4) * 5;

        if (var7 != 0)
        {
            byte var8 = 0;

            if (var7 >= 26)
            {
                var8 = 3;
            }

            if (var7 >= 11 && var7 <= 25)
            {
                var8 = 2;
            }

            if (var7 <= 10)
            {
                var8 = 1;
            }

            EntityHSSlimeSoul var9 = new EntityHSSlimeSoul(var1);
            var9.setLocationAndAngles((double)((float)var2), (double)((float)var3) + 1.0D, (double)((float)var4), 0.0F, 0.0F);
            var9.setSlimeSize(var8);
            var9.setResourceWorthTotal(var7, var8);
            var1.spawnEntityInWorld(var9);
        }
    }

    /**
     * currently only used by BlockCauldron to incrament meta-data during rain
     */
    public void fillWithRain(World var1, int var2, int var3, int var4) {}

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int idDropped(int var1, Random var2, int var3)
    {
        return mod_HarkenScythe.HSCrucibleSoul.itemID;
    }

    @SideOnly(Side.CLIENT)

    /**
     * only called by clickMiddleMouseButton , and passed to inventory.setCurrentItem (along with isCreative)
     */
    public int idPicked(World var1, int var2, int var3, int var4)
    {
        return mod_HarkenScythe.HSCrucibleSoul.itemID;
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
            ItemStack var10 = var5.inventory.getCurrentItem();

            if (var10 == null)
            {
                return true;
            }
            else
            {
                int var11 = var1.getBlockMetadata(var2, var3, var4);
                int var12 = var10.getMaxDamage() - var10.getItemDamage();

                if (var10.itemID == mod_HarkenScythe.HSSoulkeeper.itemID && var12 >= 5)
                {
                    if (var11 < 10)
                    {
                        if (!var5.capabilities.isCreativeMode)
                        {
                            var10.setItemDamage(var10.getItemDamage() + 5);

                            if (var10.getMaxDamage() - var10.getItemDamage() == 0)
                            {
                                var10.itemID = mod_HarkenScythe.HSEssenceKeeper.itemID;
                                var10.setItemDamage(-var10.getMaxDamage());
                            }
                        }

                        var1.setBlockMetadataWithNotify(var2, var3, var4, var11 + 1, 2);
                    }

                    return true;
                }
                else if (var10.itemID == mod_HarkenScythe.HSSoulVessel.itemID && var12 >= 5)
                {
                    if (var11 < 10)
                    {
                        if (!var5.capabilities.isCreativeMode)
                        {
                            var10.setItemDamage(var10.getItemDamage() + 5);

                            if (var10.getMaxDamage() - var10.getItemDamage() == 0)
                            {
                                var10.itemID = mod_HarkenScythe.HSEssenceVessel.itemID;
                                var10.setItemDamage(-var10.getMaxDamage());
                            }
                        }

                        var1.setBlockMetadataWithNotify(var2, var3, var4, var11 + 1, 2);
                    }

                    return true;
                }
                else
                {
                    return true;
                }
            }
        }
    }
}

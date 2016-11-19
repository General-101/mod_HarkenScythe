package mod_HarkenScythe.common;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class BlockHSSoulAltar extends BlockContainer
{
    @SideOnly(Side.CLIENT)
    private Icon soulAltarIconTop;
    @SideOnly(Side.CLIENT)
    private Icon soulAltarIconBottom;
    public static int soulCountTotal;

    protected BlockHSSoulAltar(int var1)
    {
        super(var1, Material.rock);
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.75F, 1.0F);
        this.setLightOpacity(0);
        this.setCreativeTab(CreativeTabs.tabDecorations);
        soulCountTotal = 0;
    }

    @SideOnly(Side.CLIENT)

    /**
     * When this method is called, your block should register all the icons it needs with the given IconRegister. This
     * is the only chance you get to register icons.
     */
    public void registerIcons(IconRegister var1)
    {
        this.blockIcon = var1.registerIcon("mod_HarkenScythe:" + this.getUnlocalizedName() + "_side");
        this.soulAltarIconTop = var1.registerIcon("mod_HarkenScythe:" + this.getUnlocalizedName() + "_top");
        this.soulAltarIconBottom = var1.registerIcon("mod_HarkenScythe:" + this.getUnlocalizedName() + "_bottom");
    }

    @SideOnly(Side.CLIENT)

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public Icon getIcon(int var1, int var2)
    {
        return var1 == 0 ? this.soulAltarIconBottom : (var1 == 1 ? this.soulAltarIconTop : this.blockIcon);
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

    @SideOnly(Side.CLIENT)

    /**
     * A randomly called display update to be able to add particles or other items for display
     */
    public void randomDisplayTick(World var1, int var2, int var3, int var4, Random var5)
    {
        super.randomDisplayTick(var1, var2, var3, var4, var5);

        for (int var6 = var2 - 2; var6 <= var2 + 2; ++var6)
        {
            for (int var7 = var4 - 2; var7 <= var4 + 2; ++var7)
            {
                if (var6 > var2 - 2 && var6 < var2 + 2 && var7 == var4 - 1)
                {
                    var7 = var4 + 2;
                }

                if (var5.nextInt(16) == 0)
                {
                    for (int var8 = var3 - 1; var8 <= var3 + 1; ++var8)
                    {
                        if (var1.getBlockId(var6, var8, var7) == mod_HarkenScythe.HSSoulCrucible.blockID)
                        {
                            if (!var1.isAirBlock((var6 - var2) / 2 + var2, var8 + 1, (var7 - var4) / 2 + var4))
                            {
                                break;
                            }

                            var1.spawnParticle("portal", (double)var2 + 0.5D, (double)var3 + 2.0D, (double)var4 + 0.5D, (double)((float)(var6 - var2) + var5.nextFloat()) - 0.5D, (double)((float)(var8 - var3) - var5.nextFloat() - 1.0F), (double)((float)(var7 - var4) + var5.nextFloat()) - 0.5D);
                        }
                    }
                }
            }
        }
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    public TileEntity createNewTileEntity(World var1)
    {
        return new TileEntityHSSoulAltar();
    }

    /**
     * Called upon block activation (right click on the block.)
     */
    public boolean onBlockActivated(World var1, int var2, int var3, int var4, EntityPlayer var5, int var6, float var7, float var8, float var9)
    {
        TileEntity var10 = var1.getBlockTileEntity(var2, var3, var4);

        if (var10 != null && !var5.isSneaking())
        {
            int var11 = 0;

            for (int var12 = var2 - 2; var12 <= var2 + 2; ++var12)
            {
                int var13 = var4 - 2;

                while (var13 <= var4 + 2)
                {
                    if (var12 > var2 - 2 && var12 < var2 + 2 && var13 == var4 - 1)
                    {
                        var13 = var4 + 2;
                    }

                    int var14 = var3 - 1;

                    while (true)
                    {
                        if (var14 <= var3 + 1)
                        {
                            label36:
                            {
                                if (var1.getBlockId(var12, var14, var13) == mod_HarkenScythe.HSSoulCrucible.blockID)
                                {
                                    if (!var1.isAirBlock((var12 - var2) / 2 + var2, var14 + 1, (var13 - var4) / 2 + var4))
                                    {
                                        break label36;
                                    }

                                    var11 += var1.getBlockMetadata(var12, var14, var13);
                                }

                                ++var14;
                                continue;
                            }
                        }

                        ++var13;
                        break;
                    }
                }
            }

            soulCountTotal = var11 * 5;
            var5.openGui(mod_HarkenScythe.instance, 0, var1, var2, var3, var4);
            return true;
        }
        else
        {
            return false;
        }
    }

    public static boolean soulDrain(int var0, int var1, int var2, int var3, World var4)
    {
        int var5 = var0;
        soulCountTotal -= var0 * 5;

        for (int var6 = var1 - 2; var6 <= var1 + 2; ++var6)
        {
            int var7 = var3 - 2;

            while (var7 <= var3 + 2)
            {
                if (var6 > var1 - 2 && var6 < var1 + 2 && var7 == var3 - 1)
                {
                    var7 = var3 + 2;
                }

                int var8 = var2 - 1;

                while (true)
                {
                    if (var8 <= var2 + 1)
                    {
                        label39:
                        {
                            if (var4.getBlockId(var6, var8, var7) == mod_HarkenScythe.HSSoulCrucible.blockID)
                            {
                                int var9 = var4.getBlockMetadata(var6, var8, var7);

                                if (!var4.isAirBlock((var6 - var1) / 2 + var1, var8 + 1, (var7 - var3) / 2 + var3))
                                {
                                    break label39;
                                }

                                if (var9 > var5)
                                {
                                    var4.setBlockMetadataWithNotify(var6, var8, var7, var9 - var5, 2);
                                    var4.markBlockForUpdate(var6, var8, var7);
                                    var5 = 0;
                                }

                                if (var5 >= var9)
                                {
                                    var4.setBlockMetadataWithNotify(var6, var8, var7, 0, 2);
                                    var4.markBlockForUpdate(var6, var8, var7);
                                    var5 -= var9;
                                }
                            }

                            ++var8;
                            continue;
                        }
                    }

                    ++var7;
                    break;
                }
            }
        }

        return true;
    }

    /**
     * ejects contained items into the world, and notifies neighbours of an update, as appropriate
     */
    public void breakBlock(World var1, int var2, int var3, int var4, int var5, int var6)
    {
        soulCountTotal = 0;
        this.dropItems(var1, var2, var3, var4);
        super.breakBlock(var1, var2, var3, var4, var5, var6);
    }

    private void dropItems(World var1, int var2, int var3, int var4)
    {
        Random var5 = new Random();
        TileEntity var6 = var1.getBlockTileEntity(var2, var3, var4);

        if (var6 instanceof IInventory)
        {
            IInventory var7 = (IInventory)var6;

            for (int var8 = 0; var8 < var7.getSizeInventory(); ++var8)
            {
                ItemStack var9 = var7.getStackInSlot(var8);

                if (var9 != null && var9.stackSize > 0)
                {
                    float var10 = var5.nextFloat() * 0.6F + 0.1F;
                    float var11 = var5.nextFloat() * 0.6F + 0.1F;
                    float var12 = var5.nextFloat() * 0.6F + 0.1F;
                    EntityItem var13 = new EntityItem(var1, (double)((float)var2 + var10), (double)((float)var3 + var11), (double)((float)var4 + var12), new ItemStack(var9.itemID, var9.stackSize, var9.getItemDamage()));

                    if (var9.hasTagCompound())
                    {
                        var13.getEntityItem().setTagCompound((NBTTagCompound)var9.getTagCompound().copy());
                    }

                    float var14 = 0.5F;
                    var13.motionX = var5.nextGaussian() * (double)var14;
                    var13.motionY = var5.nextGaussian() * (double)var14 + 0.20000000298023224D;
                    var13.motionZ = var5.nextGaussian() * (double)var14;
                    var1.spawnEntityInWorld(var13);
                    var9.stackSize = 0;
                }
            }
        }
    }
}

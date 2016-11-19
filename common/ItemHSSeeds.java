package mod_HarkenScythe.common;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemHSSeeds extends Item
{
    private int specialNumber;

    public ItemHSSeeds(int var1, int var2)
    {
        super(var1);
        this.specialNumber = var2;
        this.setCreativeTab(CreativeTabs.tabMaterials);

        if (var2 == 1)
        {
            this.setCreativeTab(CreativeTabs.tabMisc);
        }
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister var1)
    {
        this.itemIcon = var1.registerIcon("mod_HarkenScythe:" + this.getUnlocalizedName());
    }

    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack var1)
    {
        return this.specialNumber == 2;
    }

    @SideOnly(Side.CLIENT)

    /**
     * Return an item rarity from EnumRarity
     */
    public EnumRarity getRarity(ItemStack var1)
    {
        return this.specialNumber == 2 ? EnumRarity.epic : EnumRarity.common;
    }

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
    public boolean onItemUse(ItemStack var1, EntityPlayer var2, World var3, int var4, int var5, int var6, int var7, float var8, float var9, float var10)
    {
        if (this.specialNumber == 0)
        {
            return false;
        }
        else if (var7 != 1)
        {
            return false;
        }
        else
        {
            if (var2.canPlayerEdit(var4, var5, var6, var7, var1) && var2.canPlayerEdit(var4, var5, var6, var7, var1))
            {
                int var11 = var3.getBlockId(var4, var5, var6);
                int var12 = var3.getBlockMetadata(var4, var5, var6);

                if (this.specialNumber == 1 && var11 == Block.slowSand.blockID)
                {
                    var3.setBlock(var4, var5, var6, mod_HarkenScythe.HSCreepBlock.blockID);
                    var3.playSoundEffect((double)((float)var4 + 0.5F), (double)((float)var5 + 0.5F), (double)((float)var6 + 0.5F), "mob.slime.attack", 0.3F, 0.5F);

                    if (var2.capabilities.isCreativeMode)
                    {
                        return true;
                    }

                    --var1.stackSize;
                    return true;
                }

                if (this.specialNumber == 2 && var11 == mod_HarkenScythe.HSCreepBlock.blockID && var12 >= 1)
                {
                    var3.setBlock(var4, var5 + 1, var6, mod_HarkenScythe.HSBiomassPlant.blockID);

                    if (var2.capabilities.isCreativeMode)
                    {
                        return true;
                    }

                    --var1.stackSize;
                    return true;
                }
            }

            return false;
        }
    }
}

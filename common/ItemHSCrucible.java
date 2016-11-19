package mod_HarkenScythe.common;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemHSCrucible extends Item
{
    private int spawnID;

    public ItemHSCrucible(int var1, Block var2)
    {
        super(var1);
        this.setCreativeTab(CreativeTabs.tabMisc);
        this.spawnID = var2.blockID;
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister var1)
    {
        this.itemIcon = var1.registerIcon("mod_HarkenScythe:" + this.getUnlocalizedName());
    }

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
    public boolean onItemUse(ItemStack var1, EntityPlayer var2, World var3, int var4, int var5, int var6, int var7, float var8, float var9, float var10)
    {
        int var11 = var3.getBlockId(var4, var5, var6);

        if (var11 == Block.snow.blockID && (var3.getBlockMetadata(var4, var5, var6) & 7) < 1)
        {
            var7 = 1;
        }
        else if (var11 != Block.vine.blockID && var11 != Block.tallGrass.blockID && var11 != Block.deadBush.blockID)
        {
            if (var7 == 0)
            {
                --var5;
            }

            if (var7 == 1)
            {
                ++var5;
            }

            if (var7 == 2)
            {
                --var6;
            }

            if (var7 == 3)
            {
                ++var6;
            }

            if (var7 == 4)
            {
                --var4;
            }

            if (var7 == 5)
            {
                ++var4;
            }
        }

        if (!var2.canPlayerEdit(var4, var5, var6, var7, var1))
        {
            return false;
        }
        else if (var1.stackSize == 0)
        {
            return false;
        }
        else
        {
            if (var3.canPlaceEntityOnSide(this.spawnID, var4, var5, var6, false, var7, (Entity)null, var1))
            {
                Block var12 = Block.blocksList[this.spawnID];
                int var13 = var12.onBlockPlaced(var3, var4, var5, var6, var7, var8, var9, var10, 0);

                if (var3.setBlock(var4, var5, var6, this.spawnID, var13, 3))
                {
                    if (var3.getBlockId(var4, var5, var6) == this.spawnID)
                    {
                        Block.blocksList[this.spawnID].onBlockPlacedBy(var3, var4, var5, var6, var2, var1);
                        Block.blocksList[this.spawnID].onPostBlockPlaced(var3, var4, var5, var6, var13);
                    }

                    var3.playSoundEffect((double)((float)var4 + 0.5F), (double)((float)var5 + 0.5F), (double)((float)var6 + 0.5F), var12.stepSound.getPlaceSound(), (var12.stepSound.getVolume() + 1.0F) / 2.0F, var12.stepSound.getPitch() * 0.8F);
                    --var1.stackSize;
                }
            }

            return true;
        }
    }
}

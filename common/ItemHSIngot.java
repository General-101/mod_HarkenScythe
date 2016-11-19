package mod_HarkenScythe.common;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemHSIngot extends Item
{
    private int specialNumber;

    public ItemHSIngot(int var1, int var2)
    {
        super(var1);
        this.specialNumber = var2;
        this.setCreativeTab(CreativeTabs.tabMaterials);
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister var1)
    {
        this.itemIcon = var1.registerIcon("mod_HarkenScythe:" + this.getUnlocalizedName());
    }

    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack var1)
    {
        return this.specialNumber != 3;
    }

    @SideOnly(Side.CLIENT)

    /**
     * Return an item rarity from EnumRarity
     */
    public EnumRarity getRarity(ItemStack var1)
    {
        return EnumRarity.epic;
    }
}

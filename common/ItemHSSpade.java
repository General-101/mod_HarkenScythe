package mod_HarkenScythe.common;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemSpade;

public class ItemHSSpade extends ItemSpade
{
    private int specialNumber;

    public ItemHSSpade(int var1, EnumToolMaterial var2, int var3)
    {
        super(var1, var2);
        this.specialNumber = var3;
        this.setCreativeTab(CreativeTabs.tabTools);
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister var1)
    {
        this.itemIcon = var1.registerIcon("mod_HarkenScythe:" + this.getUnlocalizedName());
    }
}

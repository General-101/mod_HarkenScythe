package mod_HarkenScythe.common;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class ItemHSLunarDial extends Item
{
    private int moonPhase = 0;
    private Icon[] iconBuffer;
    Side side = FMLCommonHandler.instance().getEffectiveSide();

    public ItemHSLunarDial(int var1)
    {
        super(var1);
        this.setCreativeTab(CreativeTabs.tabTools);
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister var1)
    {
        this.iconBuffer = new Icon[8];

        for (int var2 = 0; var2 < this.iconBuffer.length; ++var2)
        {
            this.iconBuffer[var2] = var1.registerIcon("mod_HarkenScythe:" + this.getUnlocalizedName() + "_" + var2);
        }

        this.itemIcon = this.iconBuffer[this.moonPhase];
    }

    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack var1)
    {
        return true;
    }

    /**
     * Called each tick as long the item is on a player inventory. Uses by maps to check if is on a player hand and
     * update it's contents.
     */
    public void onUpdate(ItemStack var1, World var2, Entity var3, int var4, boolean var5)
    {
        if (this.side == Side.CLIENT && this.moonPhase != var2.getMoonPhase() && var3.dimension == 0)
        {
            this.moonPhase = var2.getMoonPhase();
            this.itemIcon = this.iconBuffer[this.moonPhase];
        }
    }
}

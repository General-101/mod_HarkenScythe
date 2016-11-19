package mod_HarkenScythe.common;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Icon;
import net.minecraftforge.common.IArmorTextureProvider;
import net.minecraftforge.common.ISpecialArmor;
import net.minecraftforge.common.ISpecialArmor.ArmorProperties;

public class ItemHSSpectralArmor extends ItemArmor implements IArmorTextureProvider, ISpecialArmor
{
    @SideOnly(Side.CLIENT)
    private Icon field_94605_cw;
    private static final int[] maxDamageArray = new int[] {11, 16, 15, 13};
    public final int armorType;
    public int damageReduceAmount;
    public final int renderIndex;
    private final EnumArmorMaterial material;
    private int specialNumber;

    public ItemHSSpectralArmor(int var1, EnumArmorMaterial var2, int var3, int var4, int var5)
    {
        super(var1, var2, var3, var4);
        this.material = EnumArmorMaterial.CLOTH;
        this.armorType = var4;
        this.renderIndex = var3;
        this.damageReduceAmount = this.material.getDamageReductionAmount(var4);
        this.setMaxDamage(this.material.getDurability(var4));
        this.maxStackSize = 1;
        this.specialNumber = var5;
        this.setCreativeTab((CreativeTabs)null);
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister var1)
    {
        this.itemIcon = var1.registerIcon("mod_HarkenScythe:" + this.getUnlocalizedName());
    }

    public String getArmorTextureFile(ItemStack var1)
    {
        return this.armorType != 0 && this.armorType != 1 && this.armorType != 3 ? (this.armorType == 2 ? "/mods/mod_HarkenScythe/textures/models/armor/spectral/spectral_2.png" : "/mods/mod_HarkenScythe/textures/models/armor/spectral/spectral_1.png") : "/mods/mod_HarkenScythe/textures/models/armor/spectral/spectral_1.png";
    }

    /**
     * allows items to add custom lines of information to the mouseover description
     */
    public void addInformation(ItemStack var1, EntityPlayer var2, List var3, boolean var4)
    {
        var3.add("Dyed");
    }

    /**
     * Return whether this item is repairable in an anvil.
     */
    public boolean getIsRepairable(ItemStack var1, ItemStack var2)
    {
        return this.specialNumber == 0 ? (this.material.getArmorCraftingMaterial() == var2.itemID ? true : super.getIsRepairable(var1, var2)) : (this.specialNumber == 1 && var2.itemID == mod_HarkenScythe.HSSoulweaveClothBlock.blockID ? true : this.specialNumber == 2 && var2.itemID == mod_HarkenScythe.HSBloodweaveClothBlock.blockID);
    }

    public ISpecialArmor.ArmorProperties getProperties(EntityLiving var1, ItemStack var2, DamageSource var3, double var4, int var6)
    {
        return new ISpecialArmor.ArmorProperties(0, 0.07D, Integer.MAX_VALUE);
    }

    public int getArmorDisplay(EntityPlayer var1, ItemStack var2, int var3)
    {
        return this.damageReduceAmount;
    }

    public void damageArmor(EntityLiving var1, ItemStack var2, DamageSource var3, int var4, int var5)
    {
        var2.damageItem(var4, var1);
    }
}

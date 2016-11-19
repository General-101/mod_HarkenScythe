package mod_HarkenScythe.common;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraftforge.common.IArmorTextureProvider;

public class ItemHSArmor extends ItemArmor implements IArmorTextureProvider
{
    private static final int[] maxDamageArray = new int[] {11, 16, 15, 13};
    @SideOnly(Side.CLIENT)
    private Icon field_94605_cw;
    public final int armorType;
    public final int damageReduceAmount;
    public final int renderIndex;
    private final EnumArmorMaterial material;

    public ItemHSArmor(int var1, EnumArmorMaterial var2, int var3, int var4)
    {
        super(var1, var2, var3, var4);
        this.material = var2;
        this.armorType = var4;
        this.renderIndex = var3;
        this.damageReduceAmount = var2.getDamageReductionAmount(var4);
        this.setMaxDamage(var2.getDurability(var4));
        this.maxStackSize = 1;
        this.setCreativeTab(CreativeTabs.tabCombat);
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister var1)
    {
        this.itemIcon = var1.registerIcon("mod_HarkenScythe:" + this.getUnlocalizedName());
    }

    public String getArmorTextureFile(ItemStack var1)
    {
        return var1.itemID != mod_HarkenScythe.HSHelmetLivingmetal.itemID && var1.itemID != mod_HarkenScythe.HSPlateLivingmetal.itemID && var1.itemID != mod_HarkenScythe.HSBootsLivingmetal.itemID ? (var1.itemID == mod_HarkenScythe.HSLegsLivingmetal.itemID ? "/mods/mod_HarkenScythe/textures/models/armor/livingmetal/livingmetal_2.png" : (var1.itemID != mod_HarkenScythe.HSHelmetBiomass.itemID && var1.itemID != mod_HarkenScythe.HSPlateBiomass.itemID && var1.itemID != mod_HarkenScythe.HSBootsBiomass.itemID ? (var1.itemID == mod_HarkenScythe.HSLegsBiomass.itemID ? "/mods/mod_HarkenScythe/textures/models/armor/biomass/biomass_2.png" : "/mods/mod_HarkenScythe/textures/models/armor/livingmetal/livingmetal_1.png") : "/mods/mod_HarkenScythe/textures/models/armor/biomass/biomass_1.png")) : "/mods/mod_HarkenScythe/textures/models/armor/livingmetal/livingmetal_1.png";
    }

    /**
     * Return whether this item is repairable in an anvil.
     */
    public boolean getIsRepairable(ItemStack var1, ItemStack var2)
    {
        return this.material.getArmorCraftingMaterial() == var2.itemID ? true : super.getIsRepairable(var1, var2);
    }
}

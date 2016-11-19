package mod_HarkenScythe.common;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.IArmorTextureProvider;

public class ItemHSSoulweaveArmor extends ItemArmor implements IArmorTextureProvider
{
    Side side = FMLCommonHandler.instance().getEffectiveSide();
    private static final int[] maxDamageArray = new int[] {11, 16, 15, 13};
    @SideOnly(Side.CLIENT)
    private Icon field_94605_cw;
    public final int armorType;
    public final int damageReduceAmount;
    public final int renderIndex;
    private final EnumArmorMaterial material;

    public ItemHSSoulweaveArmor(int var1, EnumArmorMaterial var2, int var3, int var4)
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
        if (this.material == EnumArmorMaterial.CLOTH)
        {
            this.field_94605_cw = var1.registerIcon("mod_HarkenScythe:" + this.getUnlocalizedName() + "_overlay");
        }

        this.itemIcon = var1.registerIcon("mod_HarkenScythe:" + this.getUnlocalizedName());
    }

    public String getArmorTextureFile(ItemStack var1)
    {
        int var2;

        if (var1.itemID != mod_HarkenScythe.HSHelmetSoulweave.itemID && var1.itemID != mod_HarkenScythe.HSPlateSoulweave.itemID && var1.itemID != mod_HarkenScythe.HSBootsSoulweave.itemID)
        {
            if (var1.itemID == mod_HarkenScythe.HSLegsSoulweave.itemID)
            {
                var2 = this.getColor(var1);
                return var2 == 1644825 ? "/mods/mod_HarkenScythe/textures/models/armor/soulweave/soulweave_2_black.png" : (var2 == 10040115 ? "/mods/mod_HarkenScythe/textures/models/armor/soulweave/soulweave_2_red.png" : (var2 == 6717235 ? "/mods/mod_HarkenScythe/textures/models/armor/soulweave/soulweave_2_green.png" : (var2 == 6704179 ? "/mods/mod_HarkenScythe/textures/models/armor/soulweave/soulweave_2_brown.png" : (var2 == 15892389 ? "/mods/mod_HarkenScythe/textures/models/armor/soulweave/soulweave_2_pink.png" : (var2 == 6724056 ? "/mods/mod_HarkenScythe/textures/models/armor/soulweave/soulweave_2_lightblue.png" : (var2 == 14188339 ? "/mods/mod_HarkenScythe/textures/models/armor/soulweave/soulweave_2_orange.png" : (var2 == 8339378 ? "/mods/mod_HarkenScythe/textures/models/armor/soulweave/soulweave_2_purple.png" : (var2 == 5013401 ? "/mods/mod_HarkenScythe/textures/models/armor/soulweave/soulweave_2_cyan.png" : (var2 == 3361970 ? "/mods/mod_HarkenScythe/textures/models/armor/soulweave/soulweave_2_blue.png" : (var2 == 11685080 ? "/mods/mod_HarkenScythe/textures/models/armor/soulweave/soulweave_2_magenta.png" : (var2 == 15066419 ? "/mods/mod_HarkenScythe/textures/models/armor/soulweave/soulweave_2_yellow.png" : (var2 == 8375321 ? "/mods/mod_HarkenScythe/textures/models/armor/soulweave/soulweave_2_lime.png" : (var2 == 5000268 ? "/mods/mod_HarkenScythe/textures/models/armor/soulweave/soulweave_2_gray.png" : (var2 == 10066329 ? "/mods/mod_HarkenScythe/textures/models/armor/soulweave/soulweave_2_silver.png" : (var2 == 16777215 ? "/mods/mod_HarkenScythe/textures/models/armor/soulweave/soulweave_2_white.png" : "/mods/mod_HarkenScythe/textures/models/armor/soulweave/soulweave_2_default.png")))))))))))))));
            }
            else
            {
                return "/mods/mod_HarkenScythe/textures/models/armor/soulweave/soulweave_1_default.png";
            }
        }
        else
        {
            var2 = this.getColor(var1);
            return var2 == 1644825 ? "/mods/mod_HarkenScythe/textures/models/armor/soulweave/soulweave_1_black.png" : (var2 == 10040115 ? "/mods/mod_HarkenScythe/textures/models/armor/soulweave/soulweave_1_red.png" : (var2 == 6717235 ? "/mods/mod_HarkenScythe/textures/models/armor/soulweave/soulweave_1_green.png" : (var2 == 6704179 ? "/mods/mod_HarkenScythe/textures/models/armor/soulweave/soulweave_1_brown.png" : (var2 == 15892389 ? "/mods/mod_HarkenScythe/textures/models/armor/soulweave/soulweave_1_pink.png" : (var2 == 6724056 ? "/mods/mod_HarkenScythe/textures/models/armor/soulweave/soulweave_1_lightblue.png" : (var2 == 14188339 ? "/mods/mod_HarkenScythe/textures/models/armor/soulweave/soulweave_1_orange.png" : (var2 == 8339378 ? "/mods/mod_HarkenScythe/textures/models/armor/soulweave/soulweave_1_purple.png" : (var2 == 5013401 ? "/mods/mod_HarkenScythe/textures/models/armor/soulweave/soulweave_1_cyan.png" : (var2 == 3361970 ? "/mods/mod_HarkenScythe/textures/models/armor/soulweave/soulweave_1_blue.png" : (var2 == 11685080 ? "/mods/mod_HarkenScythe/textures/models/armor/soulweave/soulweave_1_magenta.png" : (var2 == 15066419 ? "/mods/mod_HarkenScythe/textures/models/armor/soulweave/soulweave_1_yellow.png" : (var2 == 8375321 ? "/mods/mod_HarkenScythe/textures/models/armor/soulweave/soulweave_1_lime.png" : (var2 == 5000268 ? "/mods/mod_HarkenScythe/textures/models/armor/soulweave/soulweave_1_gray.png" : (var2 == 10066329 ? "/mods/mod_HarkenScythe/textures/models/armor/soulweave/soulweave_1_silver.png" : (var2 == 16777215 ? "/mods/mod_HarkenScythe/textures/models/armor/soulweave/soulweave_1_white.png" : "/mods/mod_HarkenScythe/textures/models/armor/soulweave/soulweave_1_default.png")))))))))))))));
        }
    }

    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack var1, int var2)
    {
        if (var2 > 0)
        {
            return 16777215;
        }
        else
        {
            int var3 = this.getColor(var1);

            if (var3 < 0)
            {
                var3 = 16777215;
            }

            return var3;
        }
    }

    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses()
    {
        return this.material == EnumArmorMaterial.CLOTH;
    }

    /**
     * Return the armor material for this armor item.
     */
    public EnumArmorMaterial getArmorMaterial()
    {
        return this.material;
    }

    /**
     * Return whether the specified armor ItemStack has a color.
     */
    public boolean hasColor(ItemStack var1)
    {
        return this.material != EnumArmorMaterial.CLOTH ? false : (!var1.hasTagCompound() ? false : (!var1.getTagCompound().hasKey("display") ? false : var1.getTagCompound().getCompoundTag("display").hasKey("color")));
    }

    /**
     * Return the color for the specified armor ItemStack.
     */
    public int getColor(ItemStack var1)
    {
        if (this.material != EnumArmorMaterial.CLOTH)
        {
            return -1;
        }
        else
        {
            NBTTagCompound var2 = var1.getTagCompound();

            if (var2 == null)
            {
                return 2603484;
            }
            else
            {
                NBTTagCompound var3 = var2.getCompoundTag("display");
                return var3 == null ? 2603484 : (var3.hasKey("color") ? var3.getInteger("color") : 2603484);
            }
        }
    }

    @SideOnly(Side.CLIENT)

    /**
     * Gets an icon index based on an item's damage value and the given render pass
     */
    public Icon getIconFromDamageForRenderPass(int var1, int var2)
    {
        return var2 == 1 ? this.field_94605_cw : super.getIconFromDamageForRenderPass(var1, var2);
    }

    /**
     * Remove the color from the specified armor ItemStack.
     */
    public void removeColor(ItemStack var1)
    {
        if (this.material == EnumArmorMaterial.CLOTH)
        {
            NBTTagCompound var2 = var1.getTagCompound();

            if (var2 != null)
            {
                NBTTagCompound var3 = var2.getCompoundTag("display");

                if (var3.hasKey("color"))
                {
                    var3.removeTag("color");
                }
            }
        }
    }

    public void func_82813_b(ItemStack var1, int var2)
    {
        if (this.material != EnumArmorMaterial.CLOTH)
        {
            throw new UnsupportedOperationException("Can\'t dye non-leather!");
        }
        else
        {
            NBTTagCompound var3 = var1.getTagCompound();

            if (var3 == null)
            {
                var3 = new NBTTagCompound();
                var1.setTagCompound(var3);
            }

            NBTTagCompound var4 = var3.getCompoundTag("display");

            if (!var3.hasKey("display"))
            {
                var3.setCompoundTag("display", var4);
            }

            var4.setInteger("color", var2);
        }
    }

    /**
     * Return whether this item is repairable in an anvil.
     */
    public boolean getIsRepairable(ItemStack var1, ItemStack var2)
    {
        return var2.itemID == mod_HarkenScythe.HSSoulweaveClothBlock.blockID;
    }

    /**
     * Return the enchantability factor of the item, most of the time is based on material.
     */
    public int getItemEnchantability()
    {
        return this.material.getEnchantability();
    }

    static int[] getMaxDamageArray()
    {
        return maxDamageArray;
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack var1, World var2, EntityPlayer var3)
    {
        int var4 = (this.armorType - 3) * -1;
        ItemStack var5 = var3.inventory.armorItemInSlot(var4);

        if (var5 == null)
        {
            var3.inventory.armorInventory[var4] = var1.copy();
            var1.stackSize = 0;
        }

        return var1;
    }

    public static int soulweaveArmorCheck(EntityPlayer var0)
    {
        int var1 = 0;

        for (int var2 = 0; var2 < 4; ++var2)
        {
            if (var0.inventory.armorItemInSlot(var2) != null)
            {
                ItemStack var3 = var0.inventory.armorItemInSlot(var2);

                if ("item.HSHelmetSoulweave".equals(var3.getItemName()))
                {
                    ++var1;
                }

                if ("item.HSChestplateSoulweave".equals(var3.getItemName()))
                {
                    ++var1;
                }

                if ("item.HSLeggingsSoulweave".equals(var3.getItemName()))
                {
                    ++var1;
                }

                if ("item.HSBootsSoulweave".equals(var3.getItemName()))
                {
                    ++var1;
                }

                if ("item.HSHelmetSpectralSoulweave".equals(var3.getItemName()))
                {
                    ++var1;
                }

                if ("item.HSChestplateSpectralSoulweave".equals(var3.getItemName()))
                {
                    ++var1;
                }

                if ("item.HSLeggingsSpectralSoulweave".equals(var3.getItemName()))
                {
                    ++var1;
                }

                if ("item.HSBootsSpectralSoulweave".equals(var3.getItemName()))
                {
                    ++var1;
                }
            }
        }

        return var1;
    }
}

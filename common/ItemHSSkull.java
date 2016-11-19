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
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.IArmorTextureProvider;
import net.minecraftforge.common.ISpecialArmor;
import net.minecraftforge.common.ISpecialArmor.ArmorProperties;

public class ItemHSSkull extends ItemArmor implements IArmorTextureProvider, ISpecialArmor
{
    private static final String[] skullTypes = new String[] {"Spider", "Cave Spider", "Enderman", "Pigman Zombie", "Villager Zombie", "Villager", "Cow", "Mooshroom", "Pig", "Squid", "Sheep", "Wolf", "Ocelot", "Ender Dragon", "Bat", "Chicken"};
    public static final String[] field_94587_a = new String[] {"item.HSSkull_spider", "item.HSSkull_cavespider", "item.HSSkull_enderman", "item.HSSkull_pigzombie", "item.HSSkull_zombie_villager", "item.HSSkull_villager", "item.HSSkull_cow", "item.HSSkull_mooshroom", "item.HSSkull_pig", "item.HSSkull_squid", "item.HSSkull_sheep", "item.HSSkull_wolf", "item.HSSkull_ozelot", "item.HSSkull_enderdragon", "item.HSSkull_bat", "item.HSSkull_chicken"};
    private static final String[] skullNames = new String[] {"spider", "cavespider", "enderman", "pigmanzombie", "villagerzombie", "villager", "cow", "mooshroom", "pig", "squid", "sheep", "wolf", "ocelot", "enderdragon", "bat", "chicken"};
    @SideOnly(Side.CLIENT)
    private Icon[] field_94586_c;
    public final int armorType;
    public final int damageReduceAmount;
    public final int renderIndex;
    private final EnumArmorMaterial material;

    public ItemHSSkull(int var1, EnumArmorMaterial var2, int var3, int var4, int var5)
    {
        super(var1, var2, var3, var4);
        this.maxStackSize = 64;
        this.material = var2;
        this.armorType = var4;
        this.renderIndex = var3;
        this.damageReduceAmount = 0;
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister var1)
    {
        this.field_94586_c = new Icon[field_94587_a.length];

        for (int var2 = 0; var2 < field_94587_a.length; ++var2)
        {
            this.field_94586_c[var2] = var1.registerIcon("mod_HarkenScythe:" + field_94587_a[var2]);
        }
    }

    public String getArmorTextureFile(ItemStack var1)
    {
        int var2 = var1.getItemDamage();

        if (var2 < 0 || var2 >= skullTypes.length)
        {
            var2 = 0;
        }

        return "/mods/mod_HarkenScythe/textures/models/mob/skulls/" + skullNames[var2] + ".png";
    }

    /**
     * Return the armor material for this armor item.
     */
    public EnumArmorMaterial getArmorMaterial()
    {
        return this.material;
    }

    /**
     * Return whether this item is repairable in an anvil.
     */
    public boolean getIsRepairable(ItemStack var1, ItemStack var2)
    {
        return false;
    }

    /**
     * Return the enchantability factor of the item, most of the time is based on material.
     */
    public int getItemEnchantability()
    {
        return this.material.getEnchantability();
    }

    public ISpecialArmor.ArmorProperties getProperties(EntityLiving var1, ItemStack var2, DamageSource var3, double var4, int var6)
    {
        return new ISpecialArmor.ArmorProperties(0, 0.0D, Integer.MAX_VALUE);
    }

    public int getArmorDisplay(EntityPlayer var1, ItemStack var2, int var3)
    {
        return this.damageReduceAmount;
    }

    public void damageArmor(EntityLiving var1, ItemStack var2, DamageSource var3, int var4, int var5)
    {
        var2.damageItem(var4, var1);
    }

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
    public boolean onItemUse(ItemStack var1, EntityPlayer var2, World var3, int var4, int var5, int var6, int var7, float var8, float var9, float var10)
    {
        if (var7 == 0)
        {
            return false;
        }
        else if (!var3.getBlockMaterial(var4, var5, var6).isSolid())
        {
            return false;
        }
        else
        {
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

            if (!var2.canPlayerEdit(var4, var5, var6, var7, var1))
            {
                return false;
            }
            else if (!mod_HarkenScythe.HSSkullBlock.canPlaceBlockAt(var3, var4, var5, var6))
            {
                return false;
            }
            else
            {
                var3.setBlock(var4, var5, var6, mod_HarkenScythe.HSSkullBlock.blockID, var7, 2);
                int var11 = 0;

                if (var7 == 1)
                {
                    var11 = MathHelper.floor_double((double)(var2.rotationYaw * 16.0F / 360.0F) + 0.5D) & 15;
                }

                TileEntity var12 = var3.getBlockTileEntity(var4, var5, var6);

                if (var12 != null && var12 instanceof TileEntityHSSkull)
                {
                    String var13 = "";

                    if (var1.hasTagCompound() && var1.getTagCompound().hasKey("SkullOwner"))
                    {
                        var13 = var1.getTagCompound().getString("SkullOwner");
                    }

                    ((TileEntityHSSkull)var12).setSkullType(var1.getItemDamage(), var13);
                    ((TileEntityHSSkull)var12).setSkullRotation(var11);
                }

                --var1.stackSize;
                return true;
            }
        }
    }

    @SideOnly(Side.CLIENT)

    /**
     * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
     */
    public void getSubItems(int var1, CreativeTabs var2, List var3)
    {
        for (int var4 = 0; var4 < skullTypes.length; ++var4)
        {
            var3.add(new ItemStack(var1, 1, var4));
        }
    }

    /**
     * Returns the metadata of the block which this Item (ItemBlock) can place
     */
    public int getMetadata(int var1)
    {
        return var1;
    }

    @SideOnly(Side.CLIENT)

    /**
     * Gets an icon index based on an item's damage value
     */
    public Icon getIconFromDamage(int var1)
    {
        if (var1 < 0 || var1 >= skullTypes.length)
        {
            var1 = 0;
        }

        return this.field_94586_c[var1];
    }

    /**
     * Returns the unlocalized name of this item. This version accepts an ItemStack so different stacks can have
     * different names based on their damage or NBT.
     */
    public String getUnlocalizedName(ItemStack var1)
    {
        int var2 = var1.getItemDamage();

        if (var2 < 0 || var2 >= skullTypes.length)
        {
            var2 = 0;
        }

        return super.getUnlocalizedName() + "." + skullTypes[var2];
    }

    public String getItemDisplayName(ItemStack var1)
    {
        return skullTypes[var1.getItemDamage()] + " Head";
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack var1, World var2, EntityPlayer var3)
    {
        return var1;
    }
}

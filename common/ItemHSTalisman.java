package mod_HarkenScythe.common;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemHSTalisman extends Item
{
    private int specialNumber;
    private int soulCost;
    private int bloodCost;

    public ItemHSTalisman(int var1, int var2)
    {
        super(var1);
        this.maxStackSize = 1;
        this.setMaxDamage(1);
        this.setNoRepair();
        this.setCreativeTab(CreativeTabs.tabMisc);
        this.specialNumber = var2;

        if (var2 == 1)
        {
            this.setMaxDamage(5);
        }
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister var1)
    {
        this.itemIcon = var1.registerIcon("mod_HarkenScythe:" + this.getUnlocalizedName());
    }

    @SideOnly(Side.CLIENT)

    /**
     * Return an item rarity from EnumRarity
     */
    public EnumRarity getRarity(ItemStack var1)
    {
        return var1.isItemEnchanted() ? EnumRarity.epic : EnumRarity.uncommon;
    }

    /**
     * allows items to add custom lines of information to the mouseover description
     */
    public void addInformation(ItemStack var1, EntityPlayer var2, List var3, boolean var4)
    {
        int var5 = var1.getMaxDamage() - var1.getItemDamage();
        String var6 = "";

        if (var5 > 1)
        {
            var6 = "s";
        }

        if (var1.getItemDamage() == 1 && var1.getItem().itemID == mod_HarkenScythe.HSTalisman.itemID)
        {
            var3.add("Breaks after " + (var5 + var1.getItemDamage()) + " resurrection" + var6);
            var3.add("Cost: 20 Souls");
            var3.add("Soul Attuned");
        }
        else if (var1.getItemDamage() == 2 && var1.getItem().itemID == mod_HarkenScythe.HSTalisman.itemID)
        {
            var3.add("Breaks after " + (var5 + var1.getItemDamage()) + " resurrection" + var6);
            var3.add("Cost: 40 Blood");
            var3.add("Blood Attuned");
        }
        else if (var1.getItemDamage() == 5 && var1.getItem().itemID == mod_HarkenScythe.HSTalismanEthereal.itemID)
        {
            if (var5 > 1)
            {
                var6 = "s";
            }

            var3.add("Breaks after " + (var5 + var1.getItemDamage()) + " resurrection" + var6);
            var3.add("Cost: 20 Souls");
            var3.add("Soul Attuned");
        }
        else if (var1.getItemDamage() == 6 && var1.getItem().itemID == mod_HarkenScythe.HSTalismanEthereal.itemID)
        {
            if (var5 > 1)
            {
                var6 = "s";
            }

            var3.add("Breaks after " + (var5 + var1.getItemDamage()) + " resurrection" + var6);
            var3.add("Cost: 40 Blood");
            var3.add("Blood Attuned");
        }
        else if (EnchantmentHelper.getEnchantmentLevel(mod_HarkenScythe.HSSoulAttunedAug.effectId, var1) > 0)
        {
            var3.add("Breaks after " + var5 + " resurrection" + var6);
            var3.add("Added Effect: Valor");
            var3.add("Cost: 20 Souls");
        }
        else if (EnchantmentHelper.getEnchantmentLevel(mod_HarkenScythe.HSBloodAttunedAug.effectId, var1) > 0)
        {
            var3.add("Breaks after " + var5 + " resurrection" + var6);
            var3.add("Added Effect: Last Stand");
            var3.add("Cost: 40 Blood");
        }
        else
        {
            var3.add("Breaks after " + var5 + " resurrection" + var6);
            var3.add("Cost: 10 Souls and 20 Blood");
        }
    }

    /**
     * Called when item is crafted/smelted. Used only by maps so far.
     */
    public void onCreated(ItemStack var1, World var2, EntityPlayer var3)
    {
        int var4 = var1.getItemDamage();

        if (var4 == 1 || var4 == 5)
        {
            var1.addEnchantment(mod_HarkenScythe.HSSoulAttunedAug, 1);
            var1.setItemDamage(0);
        }

        if (var4 == 2 || var4 == 6)
        {
            var1.addEnchantment(mod_HarkenScythe.HSBloodAttunedAug, 1);
            var1.setItemDamage(0);
        }
    }

    public static int talismanActivate(int var0, EntityPlayer var1)
    {
        int[] var2 = talismanCostCheck(var1);
        int var3 = var2[0];
        int var4 = var2[1];
        int var5 = var2[2];
        int var6 = var2[3];

        if (var3 == 1)
        {
            boolean var7;
            int var8;
            int var9;

            if (var5 > 0)
            {
                var7 = false;

                for (var8 = 0; var8 < var1.inventory.mainInventory.length; ++var8)
                {
                    if (var1.inventory.mainInventory[var8] != null && "item.HSSoulkeeper".equals(var1.inventory.mainInventory[var8].getItem().getUnlocalizedName()))
                    {
                        var9 = var1.inventory.mainInventory[var8].getMaxDamage() - var1.inventory.mainInventory[var8].getItemDamage();

                        if (var9 >= var5)
                        {
                            var1.inventory.mainInventory[var8].damageItem(var5, var1);
                            var7 = true;

                            if (var1.inventory.mainInventory[var8].getItemDamage() >= var1.inventory.mainInventory[var8].getMaxDamage())
                            {
                                var1.inventory.mainInventory[var8] = new ItemStack(mod_HarkenScythe.HSEssenceKeeper, 1);
                            }

                            break;
                        }
                    }
                }

                if (!var7)
                {
                    for (var8 = 0; var8 < var1.inventory.mainInventory.length; ++var8)
                    {
                        if (var1.inventory.mainInventory[var8] != null && "item.HSSoulVessel".equals(var1.inventory.mainInventory[var8].getItem().getUnlocalizedName()))
                        {
                            var9 = var1.inventory.mainInventory[var8].getMaxDamage() - var1.inventory.mainInventory[var8].getItemDamage();

                            if (var9 >= var5)
                            {
                                var1.inventory.mainInventory[var8].damageItem(var5, var1);

                                if (var1.inventory.mainInventory[var8].getItemDamage() >= var1.inventory.mainInventory[var8].getMaxDamage())
                                {
                                    var1.inventory.mainInventory[var8] = new ItemStack(mod_HarkenScythe.HSEssenceVessel, 1);
                                }

                                break;
                            }
                        }
                    }
                }
            }

            if (var6 > 0)
            {
                var7 = false;

                for (var8 = 0; var8 < var1.inventory.mainInventory.length; ++var8)
                {
                    if (var1.inventory.mainInventory[var8] != null && "item.HSBloodkeeper".equals(var1.inventory.mainInventory[var8].getItem().getUnlocalizedName()))
                    {
                        var9 = var1.inventory.mainInventory[var8].getMaxDamage() - var1.inventory.mainInventory[var8].getItemDamage();

                        if (var9 >= var6)
                        {
                            var1.inventory.mainInventory[var8].damageItem(var6, var1);
                            var7 = true;

                            if (var1.inventory.mainInventory[var8].getItemDamage() >= var1.inventory.mainInventory[var8].getMaxDamage())
                            {
                                var1.inventory.mainInventory[var8] = new ItemStack(mod_HarkenScythe.HSEssenceKeeper, 1);
                            }

                            break;
                        }
                    }
                }

                if (!var7)
                {
                    for (var8 = 0; var8 < var1.inventory.mainInventory.length; ++var8)
                    {
                        if (var1.inventory.mainInventory[var8] != null && "item.HSBloodVessel".equals(var1.inventory.mainInventory[var8].getItem().getUnlocalizedName()))
                        {
                            var9 = var1.inventory.mainInventory[var8].getMaxDamage() - var1.inventory.mainInventory[var8].getItemDamage();

                            if (var9 >= var6)
                            {
                                var1.inventory.mainInventory[var8].damageItem(var6, var1);

                                if (var1.inventory.mainInventory[var8].getItemDamage() >= var1.inventory.mainInventory[var8].getMaxDamage())
                                {
                                    var1.inventory.mainInventory[var8] = new ItemStack(mod_HarkenScythe.HSEssenceVessel, 1);
                                }

                                break;
                            }
                        }
                    }
                }
            }

            if ("item.HSTalisman".equals(var1.inventory.mainInventory[var4].getItem().getUnlocalizedName()))
            {
                talismanSpecial(var1.inventory.mainInventory[var4], var1);
                var1.heal(var1.getMaxHealth());
                var1.getFoodStats().addStats(20, 0.0F);
                var1.worldObj.playSoundAtEntity(var1, "random.breath", 1.0F, 1.0F);
                var1.inventory.consumeInventoryItem(var1.inventory.mainInventory[var4].getItem().itemID);
                var1.worldObj.playSoundAtEntity(var1, "random.glass", 1.0F, 1.0F);
                return 0;
            }

            if ("item.HSTalismanEthereal".equals(var1.inventory.mainInventory[var4].getItem().getUnlocalizedName()))
            {
                talismanSpecial(var1.inventory.mainInventory[var4], var1);
                var1.heal(var1.getMaxHealth());
                var1.getFoodStats().addStats(20, 0.0F);
                var1.worldObj.playSoundAtEntity(var1, "random.breath", 1.0F, 1.0F);
                var1.inventory.mainInventory[var4].damageItem(1, var1);

                if (var1.inventory.mainInventory[var4].getItemDamage() >= 5)
                {
                    var1.inventory.consumeInventoryItem(var1.inventory.mainInventory[var4].getItem().itemID);
                    var1.worldObj.playSoundAtEntity(var1, "random.glass", 1.0F, 1.0F);
                }

                return 0;
            }
        }

        return var0;
    }

    public static int[] talismanCostCheck(EntityPlayer var0)
    {
        int[] var1 = new int[] {0, 0, 0, 0};
        int var2;
        byte var3;
        byte var4;

        for (var2 = 0; var2 < var0.inventory.mainInventory.length; ++var2)
        {
            if (var0.inventory.mainInventory[var2] != null && "item.HSTalisman".equals(var0.inventory.mainInventory[var2].getItem().getUnlocalizedName()))
            {
                var3 = 10;
                var4 = 20;

                if (EnchantmentHelper.getEnchantmentLevel(mod_HarkenScythe.HSSoulAttunedAug.effectId, var0.inventory.mainInventory[var2]) > 0)
                {
                    var3 = 20;
                    var4 = 0;
                }

                if (EnchantmentHelper.getEnchantmentLevel(mod_HarkenScythe.HSBloodAttunedAug.effectId, var0.inventory.mainInventory[var2]) > 0)
                {
                    var3 = 0;
                    var4 = 40;
                }

                if (ItemHSKeeper.soulkeeperCheck(var0, var3, true) && ItemHSKeeper.bloodkeeperCheck(var0, var4, true))
                {
                    var1[0] = 1;
                    var1[1] = var2;
                    var1[2] = var3;
                    var1[3] = var4;
                    return var1;
                }
            }
        }

        for (var2 = 0; var2 < var0.inventory.mainInventory.length; ++var2)
        {
            if (var0.inventory.mainInventory[var2] != null && "item.HSTalismanEthereal".equals(var0.inventory.mainInventory[var2].getItem().getUnlocalizedName()))
            {
                var3 = 10;
                var4 = 20;

                if (EnchantmentHelper.getEnchantmentLevel(mod_HarkenScythe.HSSoulAttunedAug.effectId, var0.inventory.mainInventory[var2]) > 0)
                {
                    var3 = 20;
                    var4 = 0;
                }

                if (EnchantmentHelper.getEnchantmentLevel(mod_HarkenScythe.HSBloodAttunedAug.effectId, var0.inventory.mainInventory[var2]) > 0)
                {
                    var3 = 0;
                    var4 = 40;
                }

                if (ItemHSKeeper.soulkeeperCheck(var0, var3, true) && ItemHSKeeper.bloodkeeperCheck(var0, var4, true))
                {
                    var1[0] = 1;
                    var1[1] = var2;
                    var1[2] = var3;
                    var1[3] = var4;
                    return var1;
                }
            }
        }

        return var1;
    }

    public static void talismanSpecial(ItemStack var0, EntityPlayer var1)
    {
        var1.clearActivePotions();
        var1.extinguish();

        if (EnchantmentHelper.getEnchantmentLevel(mod_HarkenScythe.HSSoulAttunedAug.effectId, var0) > 0)
        {
            var1.addPotionEffect(new PotionEffect(Potion.regeneration.id, 600, 0));
            var1.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 600, 0));
        }
        else if (EnchantmentHelper.getEnchantmentLevel(mod_HarkenScythe.HSBloodAttunedAug.effectId, var0) > 0)
        {
            var1.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 600, 0));
            var1.addPotionEffect(new PotionEffect(Potion.resistance.id, 600, 0));
        }
    }
}

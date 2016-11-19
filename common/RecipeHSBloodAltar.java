package mod_HarkenScythe.common;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemBook;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

public class RecipeHSBloodAltar
{
    public static boolean BloodAltarUsed = false;
    public static boolean BloodAltarUsedBlock = false;
    public static boolean BloodAltarUsedAugment = false;
    public static int BloodAltarUsedStackSize = 0;
    public static int BloodAltarUsedNewItem = 0;
    public static int BloodAltarUsedCost = 0;
    public static int BloodAltarUsedNewItemID = 0;
    public static int BloodAltarUsedNewItemDamage = 0;
    private static String[] bloodAltarRecipes = new String[6];
    private static int[] bloodRandomAugment;
    private static String[] woolColorCheck;

    public static int[] bloodAltarRecipeList(ItemStack var0)
    {
        String var1 = var0.getItemName();
        int[] var2 = new int[] {0, 0};

        if (var1 != null)
        {
            for (int var3 = 0; var3 < bloodAltarRecipes.length; ++var3)
            {
                if (bloodAltarRecipes[var3].equals(var1))
                {
                    if ("item.HSEssenceKeeper".equals(var1))
                    {
                        var2[0] = 1;
                        var2[1] = 40;
                    }

                    if ("item.HSBloodkeeper".equals(var1))
                    {
                        var2[0] = 2;
                        var2[1] = bloodKeeperRechargeCost(var0);
                    }

                    if ("item.HSEssenceVessel".equals(var1))
                    {
                        var2[0] = 3;
                        var2[1] = 80;
                    }

                    if ("item.HSBloodVessel".equals(var1))
                    {
                        var2[0] = 4;
                        var2[1] = bloodKeeperRechargeCost(var0);
                    }

                    if ("item.HSBiomassSeed".equals(var1))
                    {
                        var2[0] = 6;
                        var2[1] = 10;
                    }

                    return var2;
                }

                if (bloodAltarRecipes[var3].equals("wool"))
                {
                    for (int var4 = 0; var4 < woolColorCheck.length; ++var4)
                    {
                        if (woolColorCheck[var4].equals(var1))
                        {
                            var2[0] = 5;
                            var2[1] = 10;
                            return var2;
                        }
                    }
                }
            }
        }

        return var2;
    }

    public static int[] bloodAltarAugmentList(ItemStack var0)
    {
        int[] var1 = new int[2];

        if (var0.getItem() instanceof ItemSword && !var0.isItemEnchanted())
        {
            var1[0] = 20;
            var1[1] = 100;
        }

        if (var0.getItem() instanceof ItemBow && !var0.isItemEnchanted())
        {
            var1[0] = 21;
            var1[1] = 100;
        }

        if (var0.getItem() instanceof ItemArmor && !"mod_HarkenScythe.common.ItemHSSkull".equals(var0.getItem().getClass().getName()) && !var0.isItemEnchanted())
        {
            var1[0] = 22;
            var1[1] = 100;
        }

        if (var0.getItem() instanceof ItemAxe && !var0.isItemEnchanted())
        {
            var1[0] = 23;
            var1[1] = 100;
        }

        if (var0.getItem() instanceof ItemArmor && "mod_AsgardShield.common.ItemAsgardShield".equals(var0.getItem().getClass().getName()) && !var0.isItemEnchanted())
        {
            var1[0] = 24;
            var1[1] = 100;
        }

        if (var0.getItem() instanceof ItemSword && "mod_AsgardShield.common.ItemZedSword".equals(var0.getItem().getClass().getName()) && !var0.isItemEnchanted())
        {
            var1[0] = 25;
            var1[1] = 100;
        }

        if (var0.getItem() instanceof ItemBook && var0.stackSize == 1 && !var0.isItemEnchanted())
        {
            var1[0] = 26;
            var1[1] = 100;
        }

        if ((var0.getItem() instanceof ItemHSTalisman || var0.getItem() instanceof ItemHSAmulet) && EnchantmentHelper.getEnchantmentLevel(mod_HarkenScythe.HSBloodAttunedAug.effectId, var0) == 0)
        {
            var1[0] = 27;
            var1[1] = 40;
        }

        return var1;
    }

    public static Item bloodAltarItemStackBuilder()
    {
        Item var0 = null;
        Object var1 = null;

        if (BloodAltarUsedNewItem == 1 || BloodAltarUsedNewItem == 2)
        {
            var0 = mod_HarkenScythe.HSBloodkeeper;
        }

        if (BloodAltarUsedNewItem == 3 || BloodAltarUsedNewItem == 4)
        {
            var0 = mod_HarkenScythe.HSBloodVessel;
        }

        if (BloodAltarUsedNewItem == 6)
        {
            var0 = mod_HarkenScythe.HSBiomassSeedGerminated;
        }

        return var0;
    }

    public static Block bloodAltarBlockStackBuilder()
    {
        Block var0 = null;

        if (BloodAltarUsedNewItem == 5)
        {
            var0 = mod_HarkenScythe.HSBloodweaveClothBlock;
        }

        return var0;
    }

    public static ItemStack bloodAltarItemStackAugmentBuilder(ItemStack var0)
    {
        ItemStack var1 = var0.copy();
        var1.itemID = BloodAltarUsedNewItemID;
        var1.setItemDamage(BloodAltarUsedNewItemDamage);
        Random var2;
        Random var3;
        int var4;
        int var5;

        if (BloodAltarUsedNewItem == 20)
        {
            var2 = new Random();
            var3 = new Random();
            var4 = var2.nextInt(100) + 1;
            var5 = var3.nextInt(100) + 1;

            if (var5 >= 85)
            {
                if (var4 >= 90)
                {
                    var1.addEnchantment(mod_HarkenScythe.HSDecapitateAug, 3);
                }

                if (var4 < 90 && var4 >= 60)
                {
                    var1.addEnchantment(mod_HarkenScythe.HSDecapitateAug, 2);
                }

                if (var4 < 60)
                {
                    var1.addEnchantment(mod_HarkenScythe.HSDecapitateAug, 1);
                }
            }
            else
            {
                if (var4 >= 90)
                {
                    var1.addEnchantment(mod_HarkenScythe.HSBloodlettingAug, 3);
                }

                if (var4 < 90 && var4 >= 55)
                {
                    var1.addEnchantment(mod_HarkenScythe.HSBloodlettingAug, 2);
                }

                if (var4 < 55)
                {
                    var1.addEnchantment(mod_HarkenScythe.HSBloodlettingAug, 1);
                }
            }
        }

        if (BloodAltarUsedNewItem == 21)
        {
            var2 = new Random();
            var3 = new Random();
            var4 = var2.nextInt(100) + 1;
            var5 = var3.nextInt(100) + 1;

            if (var5 >= 85)
            {
                if (var4 >= 90)
                {
                    var1.addEnchantment(mod_HarkenScythe.HSDecapitateAug, 3);
                }

                if (var4 < 90 && var4 >= 60)
                {
                    var1.addEnchantment(mod_HarkenScythe.HSDecapitateAug, 2);
                }

                if (var4 < 60)
                {
                    var1.addEnchantment(mod_HarkenScythe.HSDecapitateAug, 1);
                }
            }
            else
            {
                if (var4 >= 90)
                {
                    var1.addEnchantment(mod_HarkenScythe.HSHemorrhageAug, 3);
                }

                if (var4 < 90 && var4 >= 60)
                {
                    var1.addEnchantment(mod_HarkenScythe.HSHemorrhageAug, 2);
                }

                if (var4 < 60)
                {
                    var1.addEnchantment(mod_HarkenScythe.HSHemorrhageAug, 1);
                }
            }
        }

        if (BloodAltarUsedNewItem == 22)
        {
            var2 = new Random();
            int var8 = var2.nextInt(100) + 1;

            if (var8 >= 90)
            {
                var1.addEnchantment(mod_HarkenScythe.HSExudeAug, 4);
            }

            if (var8 < 90 && var8 >= 80)
            {
                var1.addEnchantment(mod_HarkenScythe.HSExudeAug, 3);
            }

            if (var8 < 80 && var8 >= 50)
            {
                var1.addEnchantment(mod_HarkenScythe.HSExudeAug, 2);
            }

            if (var8 < 50)
            {
                var1.addEnchantment(mod_HarkenScythe.HSExudeAug, 1);
            }
        }

        if (BloodAltarUsedNewItem == 23)
        {
            var2 = new Random();
            var3 = new Random();
            var4 = var2.nextInt(100) + 1;
            var5 = var3.nextInt(100) + 1;

            if (var5 >= 85)
            {
                if (var4 >= 90)
                {
                    var1.addEnchantment(mod_HarkenScythe.HSBloodlettingAug, 3);
                }

                if (var4 < 90 && var4 >= 60)
                {
                    var1.addEnchantment(mod_HarkenScythe.HSBloodlettingAug, 2);
                }

                if (var4 < 60)
                {
                    var1.addEnchantment(mod_HarkenScythe.HSBloodlettingAug, 1);
                }
            }
            else
            {
                if (var4 >= 90)
                {
                    var1.addEnchantment(mod_HarkenScythe.HSDecapitateAug, 3);
                }

                if (var4 < 90 && var4 >= 60)
                {
                    var1.addEnchantment(mod_HarkenScythe.HSDecapitateAug, 2);
                }

                if (var4 < 60)
                {
                    var1.addEnchantment(mod_HarkenScythe.HSDecapitateAug, 1);
                }
            }
        }

        if (BloodAltarUsedNewItem == 24)
        {
            var2 = new Random();
            var3 = new Random();
            var4 = var2.nextInt(100) + 1;
            var5 = var3.nextInt(100) + 1;

            if (var5 >= 85)
            {
                if (var4 >= 90)
                {
                    var1.addEnchantment(mod_HarkenScythe.HSExudeAug, 3);
                }

                if (var4 < 90 && var4 >= 60)
                {
                    var1.addEnchantment(mod_HarkenScythe.HSExudeAug, 2);
                }

                if (var4 < 60)
                {
                    var1.addEnchantment(mod_HarkenScythe.HSExudeAug, 1);
                }
            }
            else
            {
                if (var4 >= 90)
                {
                    var1.addEnchantment(mod_HarkenScythe.HSSanguinaryAug, 3);
                }

                if (var4 < 90 && var4 >= 60)
                {
                    var1.addEnchantment(mod_HarkenScythe.HSSanguinaryAug, 2);
                }

                if (var4 < 60)
                {
                    var1.addEnchantment(mod_HarkenScythe.HSSanguinaryAug, 1);
                }
            }
        }

        if (BloodAltarUsedNewItem == 25)
        {
            var2 = new Random();
            var3 = new Random();
            var4 = var2.nextInt(100) + 1;
            var5 = var3.nextInt(100) + 1;

            if (var5 >= 90)
            {
                if (var4 >= 90)
                {
                    var1.addEnchantment(mod_HarkenScythe.HSDecapitateAug, 3);
                }

                if (var4 < 90 && var4 >= 60)
                {
                    var1.addEnchantment(mod_HarkenScythe.HSDecapitateAug, 2);
                }

                if (var4 < 60)
                {
                    var1.addEnchantment(mod_HarkenScythe.HSDecapitateAug, 1);
                }
            }
            else if (var5 < 90 && var5 >= 75)
            {
                if (var4 >= 90)
                {
                    var1.addEnchantment(mod_HarkenScythe.HSExudeAug, 3);
                }

                if (var4 < 90 && var4 >= 60)
                {
                    var1.addEnchantment(mod_HarkenScythe.HSExudeAug, 2);
                }

                if (var4 < 60)
                {
                    var1.addEnchantment(mod_HarkenScythe.HSExudeAug, 1);
                }
            }
            else if (var5 < 75 && var4 >= 60)
            {
                if (var4 >= 90)
                {
                    var1.addEnchantment(mod_HarkenScythe.HSBloodlettingAug, 3);
                }

                if (var4 < 90 && var4 >= 60)
                {
                    var1.addEnchantment(mod_HarkenScythe.HSBloodlettingAug, 2);
                }

                if (var4 < 60)
                {
                    var1.addEnchantment(mod_HarkenScythe.HSBloodlettingAug, 1);
                }
            }
            else
            {
                if (var4 >= 90)
                {
                    var1.addEnchantment(mod_HarkenScythe.HSSanguinaryAug, 3);
                }

                if (var4 < 90 && var4 >= 60)
                {
                    var1.addEnchantment(mod_HarkenScythe.HSSanguinaryAug, 2);
                }

                if (var4 < 60)
                {
                    var1.addEnchantment(mod_HarkenScythe.HSSanguinaryAug, 1);
                }
            }
        }

        if (BloodAltarUsedNewItem == 26)
        {
            ItemEnchantedBook var9 = Item.enchantedBook;
            var1 = new ItemStack(Item.enchantedBook, 1);
            var3 = new Random();
            Random var10 = new Random();
            var5 = bloodRandomAugment[var3.nextInt(bloodRandomAugment.length)];
            int var6 = var10.nextInt(100) + 1;

            if (var6 > 90)
            {
                var6 = 4;
            }
            else if (var6 > 70)
            {
                var6 = 3;
            }
            else if (var6 > 40)
            {
                var6 = 2;
            }
            else
            {
                var6 = 1;
            }

            if (var6 == 4 && var5 != mod_HarkenScythe.HSExudeAug.effectId)
            {
                --var6;
            }

            EnchantmentData var7 = new EnchantmentData(var5, var6);
            var9.func_92115_a(var1, var7);
        }

        if (BloodAltarUsedNewItem == 27)
        {
            var1.addEnchantment(mod_HarkenScythe.HSBloodAttunedAug, 1);
        }

        return var1;
    }

    public static int bloodKeeperRechargeCost(ItemStack var0)
    {
        int var1 = var0.getItemDamage();
        return var1 > 70 ? 80 : (var1 > 60 ? 70 : (var1 > 50 ? 60 : (var1 > 40 ? 50 : (var1 > 30 ? 40 : (var1 > 20 ? 30 : (var1 > 10 ? 20 : (var1 > 0 ? 10 : 0)))))));
    }

    static
    {
        bloodAltarRecipes[0] = "item.HSEssenceKeeper";
        bloodAltarRecipes[1] = "item.HSBloodkeeper";
        bloodAltarRecipes[2] = "item.HSEssenceVessel";
        bloodAltarRecipes[3] = "item.HSBloodVessel";
        bloodAltarRecipes[4] = "wool";
        bloodAltarRecipes[5] = "item.HSBiomassSeed";
        bloodRandomAugment = new int[5];
        bloodRandomAugment[0] = mod_HarkenScythe.HSBloodlettingAug.effectId;
        bloodRandomAugment[1] = mod_HarkenScythe.HSHemorrhageAug.effectId;
        bloodRandomAugment[2] = mod_HarkenScythe.HSExudeAug.effectId;
        bloodRandomAugment[3] = mod_HarkenScythe.HSDecapitateAug.effectId;
        bloodRandomAugment[4] = mod_HarkenScythe.HSSanguinaryAug.effectId;
        woolColorCheck = new String[16];
        woolColorCheck[0] = "tile.cloth.white";
        woolColorCheck[1] = "tile.cloth.magenta";
        woolColorCheck[2] = "tile.cloth.lightBlue";
        woolColorCheck[3] = "tile.cloth.yellow";
        woolColorCheck[4] = "tile.cloth.lime";
        woolColorCheck[5] = "tile.cloth.pink";
        woolColorCheck[6] = "tile.cloth.gray";
        woolColorCheck[7] = "tile.cloth.silver";
        woolColorCheck[8] = "tile.cloth.cyan";
        woolColorCheck[9] = "tile.cloth.purple";
        woolColorCheck[10] = "tile.cloth.blue";
        woolColorCheck[11] = "tile.cloth.brown";
        woolColorCheck[12] = "tile.cloth.green";
        woolColorCheck[13] = "tile.cloth.red";
        woolColorCheck[14] = "tile.cloth.black";
        woolColorCheck[15] = "tile.HSSpectralClothBlock";
    }
}

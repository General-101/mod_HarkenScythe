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

public class RecipeHSSoulAltar
{
    public static boolean SoulAltarUsed = false;
    public static boolean SoulAltarUsedBlock = false;
    public static boolean SoulAltarUsedAugment = false;
    public static int SoulAltarUsedStackSize = 0;
    public static int SoulAltarUsedNewItem = 0;
    public static int SoulAltarUsedCost = 0;
    public static int SoulAltarUsedNewItemID = 0;
    public static int SoulAltarUsedNewItemDamage = 0;
    private static String[] soulAltarRecipes = new String[6];
    private static int[] soulRandomAugment;
    private static String[] woolColorCheck;

    public static int[] soulAltarRecipeList(ItemStack var0)
    {
        String var1 = var0.getItemName();
        int[] var2 = new int[] {0, 0};

        if (var1 != null)
        {
            for (int var3 = 0; var3 < soulAltarRecipes.length; ++var3)
            {
                if (soulAltarRecipes[var3].equals(var1))
                {
                    if ("item.HSEssenceKeeper".equals(var1))
                    {
                        var2[0] = 1;
                        var2[1] = 20;
                    }

                    if ("item.HSSoulkeeper".equals(var1))
                    {
                        var2[0] = 2;
                        var2[1] = soulKeeperRechargeCost(var0);
                    }

                    if ("item.HSEssenceVessel".equals(var1))
                    {
                        var2[0] = 3;
                        var2[1] = 40;
                    }

                    if ("item.HSSoulVessel".equals(var1))
                    {
                        var2[0] = 4;
                        var2[1] = soulKeeperRechargeCost(var0);
                    }

                    if ("item.ingotIron".equals(var1))
                    {
                        var2[0] = 6;
                        var2[1] = 5;
                    }

                    return var2;
                }

                if (soulAltarRecipes[var3].equals("wool"))
                {
                    for (int var4 = 0; var4 < woolColorCheck.length; ++var4)
                    {
                        if (woolColorCheck[var4].equals(var1))
                        {
                            var2[0] = 5;
                            var2[1] = 5;
                            return var2;
                        }
                    }
                }
            }
        }

        return var2;
    }

    public static int[] soulAltarAugmentList(ItemStack var0)
    {
        int[] var1 = new int[2];

        if (var0.getItem() instanceof ItemSword && !var0.isItemEnchanted())
        {
            var1[0] = 20;
            var1[1] = 50;
        }

        if (var0.getItem() instanceof ItemBow && !var0.isItemEnchanted())
        {
            var1[0] = 21;
            var1[1] = 50;
        }

        if (var0.getItem() instanceof ItemArmor && !"mod_HarkenScythe.common.ItemHSSkull".equals(var0.getItem().getClass().getName()) && !var0.isItemEnchanted())
        {
            var1[0] = 22;
            var1[1] = 50;
        }

        if (var0.getItem() instanceof ItemAxe && !var0.isItemEnchanted())
        {
            var1[0] = 23;
            var1[1] = 50;
        }

        if (var0.getItem() instanceof ItemArmor && "mod_AsgardShield.common.ItemAsgardShield".equals(var0.getItem().getClass().getName()) && !var0.isItemEnchanted())
        {
            var1[0] = 24;
            var1[1] = 50;
        }

        if (var0.getItem() instanceof ItemSword && "mod_AsgardShield.common.ItemZedSword".equals(var0.getItem().getClass().getName()) && !var0.isItemEnchanted())
        {
            var1[0] = 25;
            var1[1] = 50;
        }

        if (var0.getItem() instanceof ItemBook && var0.stackSize == 1 && !var0.isItemEnchanted())
        {
            var1[0] = 26;
            var1[1] = 50;
        }

        if ((var0.getItem() instanceof ItemHSTalisman || var0.getItem() instanceof ItemHSAmulet) && EnchantmentHelper.getEnchantmentLevel(mod_HarkenScythe.HSSoulAttunedAug.effectId, var0) == 0)
        {
            var1[0] = 27;
            var1[1] = 20;
        }

        return var1;
    }

    public static Item soulAltarItemStackBuilder()
    {
        Item var0 = null;
        Object var1 = null;

        if (SoulAltarUsedNewItem == 1 || SoulAltarUsedNewItem == 2)
        {
            var0 = mod_HarkenScythe.HSSoulkeeper;
        }

        if (SoulAltarUsedNewItem == 3 || SoulAltarUsedNewItem == 4)
        {
            var0 = mod_HarkenScythe.HSSoulVessel;
        }

        if (SoulAltarUsedNewItem == 6)
        {
            var0 = mod_HarkenScythe.HSIngotLivingmetal;
        }

        return var0;
    }

    public static Block soulAltarBlockStackBuilder()
    {
        Block var0 = null;

        if (SoulAltarUsedNewItem == 5)
        {
            var0 = mod_HarkenScythe.HSSoulweaveClothBlock;
        }

        return var0;
    }

    public static ItemStack soulAltarItemStackAugmentBuilder(ItemStack var0)
    {
        ItemStack var1 = var0.copy();
        var1.itemID = SoulAltarUsedNewItemID;
        var1.setItemDamage(SoulAltarUsedNewItemDamage);
        Random var2;
        Random var3;
        int var4;
        int var5;

        if (SoulAltarUsedNewItem == 20)
        {
            var2 = new Random();
            var3 = new Random();
            var4 = var2.nextInt(100) + 1;
            var5 = var3.nextInt(100) + 1;

            if (var5 >= 85)
            {
                if (var4 >= 90)
                {
                    var1.addEnchantment(mod_HarkenScythe.HSAfterlifeAug, 3);
                }

                if (var4 < 90 && var4 >= 60)
                {
                    var1.addEnchantment(mod_HarkenScythe.HSAfterlifeAug, 2);
                }

                if (var4 < 60)
                {
                    var1.addEnchantment(mod_HarkenScythe.HSAfterlifeAug, 1);
                }
            }
            else
            {
                if (var4 >= 90)
                {
                    var1.addEnchantment(mod_HarkenScythe.HSSoulstealAug, 3);
                }

                if (var4 < 90 && var4 >= 60)
                {
                    var1.addEnchantment(mod_HarkenScythe.HSSoulstealAug, 2);
                }

                if (var4 < 60)
                {
                    var1.addEnchantment(mod_HarkenScythe.HSSoulstealAug, 1);
                }
            }
        }

        if (SoulAltarUsedNewItem == 21)
        {
            var2 = new Random();
            var3 = new Random();
            var4 = var2.nextInt(100) + 1;
            var5 = var3.nextInt(100) + 1;

            if (var5 >= 85)
            {
                if (var4 >= 90)
                {
                    var1.addEnchantment(mod_HarkenScythe.HSAfterlifeAug, 3);
                }

                if (var4 < 90 && var4 >= 60)
                {
                    var1.addEnchantment(mod_HarkenScythe.HSAfterlifeAug, 2);
                }

                if (var4 < 60)
                {
                    var1.addEnchantment(mod_HarkenScythe.HSAfterlifeAug, 1);
                }
            }
            else
            {
                if (var4 >= 90)
                {
                    var1.addEnchantment(mod_HarkenScythe.HSBlightAug, 3);
                }

                if (var4 < 90 && var4 >= 60)
                {
                    var1.addEnchantment(mod_HarkenScythe.HSBlightAug, 2);
                }

                if (var4 < 60)
                {
                    var1.addEnchantment(mod_HarkenScythe.HSBlightAug, 1);
                }
            }
        }

        if (SoulAltarUsedNewItem == 22)
        {
            var2 = new Random();
            int var8 = var2.nextInt(100) + 1;

            if (var8 >= 90)
            {
                var1.addEnchantment(mod_HarkenScythe.HSVitalityAug, 4);
            }

            if (var8 < 90 && var8 >= 80)
            {
                var1.addEnchantment(mod_HarkenScythe.HSVitalityAug, 3);
            }

            if (var8 < 80 && var8 >= 50)
            {
                var1.addEnchantment(mod_HarkenScythe.HSVitalityAug, 2);
            }

            if (var8 < 50)
            {
                var1.addEnchantment(mod_HarkenScythe.HSVitalityAug, 1);
            }
        }

        if (SoulAltarUsedNewItem == 23)
        {
            var2 = new Random();
            var3 = new Random();
            var4 = var2.nextInt(100) + 1;
            var5 = var3.nextInt(100) + 1;

            if (var5 >= 85)
            {
                if (var4 >= 90)
                {
                    var1.addEnchantment(mod_HarkenScythe.HSSoulstealAug, 3);
                }

                if (var4 < 90 && var4 >= 60)
                {
                    var1.addEnchantment(mod_HarkenScythe.HSSoulstealAug, 2);
                }

                if (var4 < 60)
                {
                    var1.addEnchantment(mod_HarkenScythe.HSSoulstealAug, 1);
                }
            }
            else
            {
                if (var4 >= 90)
                {
                    var1.addEnchantment(mod_HarkenScythe.HSAfterlifeAug, 3);
                }

                if (var4 < 90 && var4 >= 60)
                {
                    var1.addEnchantment(mod_HarkenScythe.HSAfterlifeAug, 2);
                }

                if (var4 < 60)
                {
                    var1.addEnchantment(mod_HarkenScythe.HSAfterlifeAug, 1);
                }
            }
        }

        if (SoulAltarUsedNewItem == 24)
        {
            var2 = new Random();
            var3 = new Random();
            var4 = var2.nextInt(100) + 1;
            var5 = var3.nextInt(100) + 1;

            if (var5 >= 85)
            {
                if (var4 >= 90)
                {
                    var1.addEnchantment(mod_HarkenScythe.HSVitalityAug, 3);
                }

                if (var4 < 90 && var4 >= 60)
                {
                    var1.addEnchantment(mod_HarkenScythe.HSVitalityAug, 2);
                }

                if (var4 < 60)
                {
                    var1.addEnchantment(mod_HarkenScythe.HSVitalityAug, 1);
                }
            }
            else
            {
                if (var4 >= 90)
                {
                    var1.addEnchantment(mod_HarkenScythe.HSWardAug, 3);
                }

                if (var4 < 90 && var4 >= 60)
                {
                    var1.addEnchantment(mod_HarkenScythe.HSWardAug, 2);
                }

                if (var4 < 60)
                {
                    var1.addEnchantment(mod_HarkenScythe.HSWardAug, 1);
                }
            }
        }

        if (SoulAltarUsedNewItem == 25)
        {
            var2 = new Random();
            var3 = new Random();
            var4 = var2.nextInt(100) + 1;
            var5 = var3.nextInt(100) + 1;

            if (var5 >= 90)
            {
                if (var4 >= 90)
                {
                    var1.addEnchantment(mod_HarkenScythe.HSAfterlifeAug, 3);
                }

                if (var4 < 90 && var4 >= 60)
                {
                    var1.addEnchantment(mod_HarkenScythe.HSAfterlifeAug, 2);
                }

                if (var4 < 60)
                {
                    var1.addEnchantment(mod_HarkenScythe.HSAfterlifeAug, 1);
                }
            }
            else if (var5 < 90 && var5 >= 75)
            {
                if (var4 >= 90)
                {
                    var1.addEnchantment(mod_HarkenScythe.HSVitalityAug, 3);
                }

                if (var4 < 90 && var4 >= 60)
                {
                    var1.addEnchantment(mod_HarkenScythe.HSVitalityAug, 2);
                }

                if (var4 < 60)
                {
                    var1.addEnchantment(mod_HarkenScythe.HSVitalityAug, 1);
                }
            }
            else if (var5 < 75 && var4 >= 60)
            {
                if (var4 >= 90)
                {
                    var1.addEnchantment(mod_HarkenScythe.HSSoulstealAug, 3);
                }

                if (var4 < 90 && var4 >= 60)
                {
                    var1.addEnchantment(mod_HarkenScythe.HSSoulstealAug, 2);
                }

                if (var4 < 60)
                {
                    var1.addEnchantment(mod_HarkenScythe.HSSoulstealAug, 1);
                }
            }
            else
            {
                if (var4 >= 90)
                {
                    var1.addEnchantment(mod_HarkenScythe.HSWardAug, 3);
                }

                if (var4 < 90 && var4 >= 60)
                {
                    var1.addEnchantment(mod_HarkenScythe.HSWardAug, 2);
                }

                if (var4 < 60)
                {
                    var1.addEnchantment(mod_HarkenScythe.HSWardAug, 1);
                }
            }
        }

        if (SoulAltarUsedNewItem == 26)
        {
            ItemEnchantedBook var9 = Item.enchantedBook;
            var1 = new ItemStack(Item.enchantedBook, 1);
            var3 = new Random();
            Random var10 = new Random();
            var5 = soulRandomAugment[var3.nextInt(soulRandomAugment.length)];
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

            if (var6 == 4 && var5 != mod_HarkenScythe.HSVitalityAug.effectId)
            {
                --var6;
            }

            EnchantmentData var7 = new EnchantmentData(var5, var6);
            var9.func_92115_a(var1, var7);
        }

        if (SoulAltarUsedNewItem == 27)
        {
            var1.addEnchantment(mod_HarkenScythe.HSSoulAttunedAug, 1);
        }

        return var1;
    }

    public static int soulKeeperRechargeCost(ItemStack var0)
    {
        int var1 = var0.getItemDamage();
        return var1 > 35 ? 40 : (var1 > 30 ? 35 : (var1 > 25 ? 30 : (var1 > 20 ? 25 : (var1 > 15 ? 20 : (var1 > 10 ? 15 : (var1 > 5 ? 10 : (var1 > 0 ? 5 : 0)))))));
    }

    static
    {
        soulAltarRecipes[0] = "item.HSEssenceKeeper";
        soulAltarRecipes[1] = "item.HSSoulkeeper";
        soulAltarRecipes[2] = "item.HSEssenceVessel";
        soulAltarRecipes[3] = "item.HSSoulVessel";
        soulAltarRecipes[4] = "wool";
        soulAltarRecipes[5] = "item.ingotIron";
        soulRandomAugment = new int[5];
        soulRandomAugment[0] = mod_HarkenScythe.HSSoulstealAug.effectId;
        soulRandomAugment[1] = mod_HarkenScythe.HSBlightAug.effectId;
        soulRandomAugment[2] = mod_HarkenScythe.HSVitalityAug.effectId;
        soulRandomAugment[3] = mod_HarkenScythe.HSAfterlifeAug.effectId;
        soulRandomAugment[4] = mod_HarkenScythe.HSWardAug.effectId;
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

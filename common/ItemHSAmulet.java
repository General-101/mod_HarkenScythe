package mod_HarkenScythe.common;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;

public class ItemHSAmulet extends Item
{
    public ChunkCoordinates timeToSpawn;
    public ChunkCoordinates timeToSpawn2;
    private int specialNumber;
    private int counter;
    private int slow;
    private int soulKeeperCost;
    Side side = FMLCommonHandler.instance().getEffectiveSide();

    public ItemHSAmulet(int var1, int var2)
    {
        super(var1);
        this.maxStackSize = 1;
        this.setMaxDamage(22);
        this.setNoRepair();
        this.specialNumber = var2;
        this.counter = 0;
        this.slow = 0;
        this.soulKeeperCost = 5;
        this.setCreativeTab(CreativeTabs.tabTransport);

        if (var2 == 2)
        {
            this.soulKeeperCost = 10;
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

        if (var1.getItemDamage() == 22)
        {
            var3.add("Teleport back to resting place");
            var3.add("Cost: 10 Souls");
            var3.add("Soul Attuned");
        }
        else if (var1.getItemDamage() == 23)
        {
            var3.add("Teleport back to resting place");
            var3.add("Cost: 20 Blood");
            var3.add("Blood Attuned");
        }
        else if (EnchantmentHelper.getEnchantmentLevel(mod_HarkenScythe.HSSoulAttunedAug.effectId, var1) > 0)
        {
            var3.add("Teleport back to resting place");
            var3.add("Cost: 10 Souls");
        }
        else if (EnchantmentHelper.getEnchantmentLevel(mod_HarkenScythe.HSBloodAttunedAug.effectId, var1) > 0)
        {
            var3.add("Teleport back to resting place");
            var3.add("Cost: 20 Blood");
        }
        else
        {
            var3.add("Teleport back to resting place");
            var3.add("Cost: 5 Souls and 10 Blood");
        }
    }

    /**
     * Called when item is crafted/smelted. Used only by maps so far.
     */
    public void onCreated(ItemStack var1, World var2, EntityPlayer var3)
    {
        int var4 = var1.getItemDamage();

        if (var4 == 22)
        {
            var1.addEnchantment(mod_HarkenScythe.HSSoulAttunedAug, 1);
            var1.setItemDamage(0);
        }

        if (var4 == 23)
        {
            var1.addEnchantment(mod_HarkenScythe.HSBloodAttunedAug, 1);
            var1.setItemDamage(0);
        }
    }

    /**
     * How long it takes to use or consume an item
     */
    public int getMaxItemUseDuration(ItemStack var1)
    {
        return 20;
    }

    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    public EnumAction getItemUseAction(ItemStack var1)
    {
        return EnumAction.none;
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack var1, World var2, EntityPlayer var3)
    {
        int[] var4 = amuletCostCheck(var1, var3);
        int var5 = var4[0];
        int var6 = var4[1];
        int var7 = var4[2];

        if (var5 == 1)
        {
            if (!var3.capabilities.isCreativeMode)
            {
                boolean var11 = false;
                ++this.slow;
                var3.setItemInUse(var1, this.getMaxItemUseDuration(var1));
                int var12 = var1.getItemDamage();

                if (var12 >= 2 && var12 < 10)
                {
                    var2.spawnParticle("depthsuspend", var3.posX + 0.9D, var3.posY - 1.6D, var3.posZ + 0.9D, 0.0D, 0.0D, 0.0D);
                    var2.spawnParticle("depthsuspend", var3.posX + 0.8D, var3.posY - 1.6D, var3.posZ + 0.7D, 0.0D, 0.0D, 0.0D);
                    var2.spawnParticle("depthsuspend", var3.posX + 0.7D, var3.posY - 1.6D, var3.posZ + 0.8D, 0.0D, 0.0D, 0.0D);
                    var3.worldObj.playSoundAtEntity(var3, "random.breath", 1.0F, 1.0F);
                }

                if (var12 >= 4 && var12 < 10)
                {
                    var2.spawnParticle("flame", var3.posX - 0.9D, var3.posY - 1.6D, var3.posZ - 0.9D, 0.0D, 0.0D, 0.0D);
                    var2.spawnParticle("flame", var3.posX - 0.7D, var3.posY - 1.6D, var3.posZ - 0.8D, 0.0D, 0.0D, 0.0D);
                    var2.spawnParticle("flame", var3.posX - 0.8D, var3.posY - 1.6D, var3.posZ - 0.7D, 0.0D, 0.0D, 0.0D);
                }

                if (var12 >= 6 && var12 < 10)
                {
                    var2.spawnParticle("spell", var3.posX - 0.9D, var3.posY - 1.6D, var3.posZ + 0.9D, 0.0D, 0.0D, 0.0D);
                    var2.spawnParticle("spell", var3.posX - 0.7D, var3.posY - 1.6D, var3.posZ + 0.8D, 0.0D, 0.0D, 0.0D);
                    var2.spawnParticle("spell", var3.posX - 0.8D, var3.posY - 1.6D, var3.posZ + 0.7D, 0.0D, 0.0D, 0.0D);
                }

                if (var12 >= 8 && var12 < 10)
                {
                    var2.spawnParticle("splash", var3.posX + 0.9D, var3.posY - 1.6D, var3.posZ - 0.9D, 0.0D, 0.0D, 0.0D);
                    var2.spawnParticle("splash", var3.posX + 0.8D, var3.posY - 1.6D, var3.posZ - 0.7D, 0.0D, 0.0D, 0.0D);
                    var2.spawnParticle("splash", var3.posX + 0.7D, var3.posY - 1.6D, var3.posZ - 0.8D, 0.0D, 0.0D, 0.0D);
                }

                if (var12 == 10 || var12 == 14 || var12 == 18 || var12 == 20)
                {
                    this.amuletEffectText(var2, var3);
                }

                if (var12 == 12 || var12 == 16 || var12 == 18 || var12 == 20)
                {
                    this.amuletEffectText2(var2, var3);
                }

                if (var12 >= 10)
                {
                    this.amuletEffectElements(var2, var3);
                }

                if (var12 >= 16)
                {
                    this.amuletEffect(var2, var3);
                }

                if (this.side == Side.CLIENT)
                {
                    Minecraft var10 = FMLClientHandler.instance().getClient();

                    if (var10.isIntegratedServerRunning() && this.slow == (this.counter + 1) * 2)
                    {
                        var11 = true;
                    }
                }
                else if (this.slow == (this.counter + 1) * 1)
                {
                    var11 = true;
                }

                if (var11)
                {
                    ++this.counter;
                    ++this.counter;
                    var3.getCurrentEquippedItem().damageItem(2, var3);
                    var1.setItemDamage(this.counter);

                    if (var12 >= 20)
                    {
                        this.onPlayerStoppedUsing(var1, var2, var3, var12);
                        return var1;
                    }

                    var12 = var1.getItemDamage();

                    if (var12 >= 10 && var12 < 12)
                    {
                        var2.playSoundAtEntity(var3, "portal.portal", 1.0F, 1.0F);
                    }

                    return var1;
                }

                return var1;
            }

            if (var3.capabilities.isCreativeMode)
            {
                ++this.counter;
                var1.setItemDamage(this.counter);
                int var8 = var1.getItemDamage();

                if (var8 >= 20)
                {
                    this.onPlayerStoppedUsing(var1, var2, var3, var8);
                    return var1;
                }

                var3.setItemInUse(var1, this.getMaxItemUseDuration(var1));

                if (var8 >= 1 && var8 < 10)
                {
                    var2.spawnParticle("depthsuspend", var3.posX + 0.9D, var3.posY - 1.6D, var3.posZ + 0.9D, 0.0D, 0.0D, 0.0D);
                    var2.spawnParticle("depthsuspend", var3.posX + 0.8D, var3.posY - 1.6D, var3.posZ + 0.7D, 0.0D, 0.0D, 0.0D);
                    var2.spawnParticle("depthsuspend", var3.posX + 0.7D, var3.posY - 1.6D, var3.posZ + 0.8D, 0.0D, 0.0D, 0.0D);
                    var3.worldObj.playSoundAtEntity(var3, "random.breath", 1.0F, 1.0F);
                }

                if (var8 >= 3 && var8 < 10)
                {
                    var2.spawnParticle("flame", var3.posX - 0.9D, var3.posY - 1.6D, var3.posZ - 0.9D, 0.0D, 0.0D, 0.0D);
                    var2.spawnParticle("flame", var3.posX - 0.7D, var3.posY - 1.6D, var3.posZ - 0.8D, 0.0D, 0.0D, 0.0D);
                    var2.spawnParticle("flame", var3.posX - 0.8D, var3.posY - 1.6D, var3.posZ - 0.7D, 0.0D, 0.0D, 0.0D);
                }

                if (var8 >= 5 && var8 < 10)
                {
                    var2.spawnParticle("spell", var3.posX - 0.9D, var3.posY - 1.6D, var3.posZ + 0.9D, 0.0D, 0.0D, 0.0D);
                    var2.spawnParticle("spell", var3.posX - 0.7D, var3.posY - 1.6D, var3.posZ + 0.8D, 0.0D, 0.0D, 0.0D);
                    var2.spawnParticle("spell", var3.posX - 0.8D, var3.posY - 1.6D, var3.posZ + 0.7D, 0.0D, 0.0D, 0.0D);
                }

                if (var8 >= 7 && var8 < 10)
                {
                    var2.spawnParticle("splash", var3.posX + 0.9D, var3.posY - 1.6D, var3.posZ - 0.9D, 0.0D, 0.0D, 0.0D);
                    var2.spawnParticle("splash", var3.posX + 0.8D, var3.posY - 1.6D, var3.posZ - 0.7D, 0.0D, 0.0D, 0.0D);
                    var2.spawnParticle("splash", var3.posX + 0.7D, var3.posY - 1.6D, var3.posZ - 0.8D, 0.0D, 0.0D, 0.0D);
                }

                if (var8 == 7 || var8 == 11 || var8 == 15 || var8 == 17 || var8 == 19)
                {
                    this.amuletEffectText(var2, var3);
                }

                if (var8 == 9 || var8 == 13 || var8 == 15 || var8 == 17 || var8 == 19)
                {
                    this.amuletEffectText2(var2, var3);
                }

                if (this.side == Side.CLIENT)
                {
                    Minecraft var9 = FMLClientHandler.instance().getClient();

                    if (var9.isIntegratedServerRunning())
                    {
                        --this.counter;
                    }
                }

                ++this.counter;
                var1.setItemDamage(this.counter);
                var8 = var1.getItemDamage();

                if (var8 >= 10 && var8 < 12)
                {
                    var2.playSoundAtEntity(var3, "portal.portal", 1.0F, 1.0F);
                }

                if (var8 >= 10)
                {
                    this.amuletEffectElements(var2, var3);
                }

                if (var8 >= 15)
                {
                    this.amuletEffect(var2, var3);
                }

                return var1;
            }
        }

        return var1;
    }

    /**
     * called when the player releases the use item button. Args: itemstack, world, entityplayer, itemInUseCount
     */
    public void onPlayerStoppedUsing(ItemStack var1, World var2, EntityPlayer var3, int var4)
    {
        int[] var5 = amuletCostCheck(var1, var3);
        int var6 = var5[0];
        int var7 = var5[1];
        int var8 = var5[2];
        float var9 = (float)this.counter;

        if (var9 >= 19.0F)
        {
            ChunkCoordinates var10 = var3.getBedLocation();
            ChunkCoordinates var11 = var3.playerLocation;

            if (var10 != null)
            {
                if (var3.dimension != 0 && var3.dimension != 1 && this.specialNumber == 2 && var6 == 1)
                {
                    this.amuletActivate(var7, var8, var3);
                    ((EntityPlayerMP)var3).mcServer.getConfigurationManager().transferPlayerToDimension((EntityPlayerMP)var3, 0);
                    var3.setPositionAndUpdate((double)var10.posX + 0.5D, (double)(var10.posY + 2), (double)var10.posZ + 0.5D);
                    var2.playSoundAtEntity(var3, "portal.travel", 0.2F, 1.0F);
                    this.amuletEffect(var2, var3);
                    this.counter = 0;
                    this.slow = 0;
                    var1.setItemDamage(0);
                    return;
                }

                if (var3.dimension == -1 && var3.dimension != 0 && this.specialNumber == 1 && var6 == 1)
                {
                    this.amuletActivate(var7, var8, var3);
                    ((EntityPlayerMP)var3).mcServer.getConfigurationManager().transferPlayerToDimension((EntityPlayerMP)var3, 0);
                    var3.setPositionAndUpdate((double)var10.posX + 0.5D, (double)(var10.posY + 2), (double)var10.posZ + 0.5D);
                    var2.playSoundAtEntity(var3, "portal.travel", 0.2F, 1.0F);
                    this.amuletEffect(var2, var3);
                    this.counter = 0;
                    this.slow = 0;
                    var1.setItemDamage(0);
                    return;
                }

                if (var2.getBlockId(var10.posX, var10.posY, var10.posZ) == Block.bed.blockID && (this.specialNumber == 0 || this.specialNumber == 2) && var6 == 1)
                {
                    this.amuletActivate(var7, var8, var3);
                    var3.setPositionAndUpdate((double)var10.posX + 0.5D, (double)(var10.posY + 2), (double)var10.posZ + 0.5D);
                    var2.playSoundAtEntity(var3, "portal.travel", 0.2F, 1.0F);
                    this.amuletEffect(var2, var3);
                    this.counter = 0;
                    this.slow = 0;
                    var1.setItemDamage(0);
                    return;
                }
            }
        }

        this.counter = 0;
        this.slow = 0;
        var1.setItemDamage(0);
        var3.worldObj.playSoundAtEntity(var3, "random.fizz", 1.0F, 1.0F);
    }

    public static int[] amuletCostCheck(ItemStack var0, EntityPlayer var1)
    {
        int[] var2 = new int[] {0, 0, 0};
        byte var3 = 5;
        byte var4 = 10;

        if (EnchantmentHelper.getEnchantmentLevel(mod_HarkenScythe.HSSoulAttunedAug.effectId, var0) > 0)
        {
            var3 = 10;
            var4 = 0;
        }

        if (EnchantmentHelper.getEnchantmentLevel(mod_HarkenScythe.HSBloodAttunedAug.effectId, var0) > 0)
        {
            var3 = 0;
            var4 = 20;
        }

        if (ItemHSKeeper.soulkeeperCheck(var1, var3, false) && ItemHSKeeper.bloodkeeperCheck(var1, var4, false))
        {
            var2[0] = 1;
            var2[1] = var3;
            var2[2] = var4;
            return var2;
        }
        else
        {
            return var2;
        }
    }

    public void amuletActivate(int var1, int var2, EntityPlayer var3)
    {
        boolean var4;
        int var5;
        int var6;

        if (var1 > 0)
        {
            var4 = false;

            for (var5 = 0; var5 < var3.inventory.mainInventory.length; ++var5)
            {
                if (var3.inventory.mainInventory[var5] != null && "item.HSSoulkeeper".equals(var3.inventory.mainInventory[var5].getItem().getUnlocalizedName()))
                {
                    var6 = var3.inventory.mainInventory[var5].getMaxDamage() - var3.inventory.mainInventory[var5].getItemDamage();

                    if (var6 >= var1)
                    {
                        var3.inventory.mainInventory[var5].damageItem(var1, var3);
                        var4 = true;

                        if (var3.inventory.mainInventory[var5].getItemDamage() >= var3.inventory.mainInventory[var5].getMaxDamage())
                        {
                            var3.inventory.mainInventory[var5] = new ItemStack(mod_HarkenScythe.HSEssenceKeeper, 1);
                        }

                        break;
                    }
                }
            }

            if (!var4)
            {
                for (var5 = 0; var5 < var3.inventory.mainInventory.length; ++var5)
                {
                    if (var3.inventory.mainInventory[var5] != null && "item.HSSoulVessel".equals(var3.inventory.mainInventory[var5].getItem().getUnlocalizedName()))
                    {
                        var6 = var3.inventory.mainInventory[var5].getMaxDamage() - var3.inventory.mainInventory[var5].getItemDamage();

                        if (var6 >= var1)
                        {
                            var3.inventory.mainInventory[var5].damageItem(var1, var3);

                            if (var3.inventory.mainInventory[var5].getItemDamage() >= var3.inventory.mainInventory[var5].getMaxDamage())
                            {
                                var3.inventory.mainInventory[var5] = new ItemStack(mod_HarkenScythe.HSEssenceVessel, 1);
                            }

                            break;
                        }
                    }
                }
            }
        }

        if (var2 > 0)
        {
            var4 = false;

            for (var5 = 0; var5 < var3.inventory.mainInventory.length; ++var5)
            {
                if (var3.inventory.mainInventory[var5] != null && "item.HSBloodkeeper".equals(var3.inventory.mainInventory[var5].getItem().getUnlocalizedName()))
                {
                    var6 = var3.inventory.mainInventory[var5].getMaxDamage() - var3.inventory.mainInventory[var5].getItemDamage();

                    if (var6 >= var2)
                    {
                        var3.inventory.mainInventory[var5].damageItem(var2, var3);
                        var4 = true;

                        if (var3.inventory.mainInventory[var5].getItemDamage() >= var3.inventory.mainInventory[var5].getMaxDamage())
                        {
                            var3.inventory.mainInventory[var5] = new ItemStack(mod_HarkenScythe.HSEssenceKeeper, 1);
                        }

                        break;
                    }
                }
            }

            if (!var4)
            {
                for (var5 = 0; var5 < var3.inventory.mainInventory.length; ++var5)
                {
                    if (var3.inventory.mainInventory[var5] != null && "item.HSBloodVessel".equals(var3.inventory.mainInventory[var5].getItem().getUnlocalizedName()))
                    {
                        var6 = var3.inventory.mainInventory[var5].getMaxDamage() - var3.inventory.mainInventory[var5].getItemDamage();

                        if (var6 >= var2)
                        {
                            var3.inventory.mainInventory[var5].damageItem(var2, var3);

                            if (var3.inventory.mainInventory[var5].getItemDamage() >= var3.inventory.mainInventory[var5].getMaxDamage())
                            {
                                var3.inventory.mainInventory[var5] = new ItemStack(mod_HarkenScythe.HSEssenceVessel, 1);
                            }

                            break;
                        }
                    }
                }
            }
        }
    }

    private void amuletEffectText(World var1, EntityPlayer var2)
    {
        var1.spawnParticle("enchantmenttable", var2.posX, var2.posY, var2.posZ + 0.4D, 0.0D, 0.0D, 0.0D);
        var1.spawnParticle("enchantmenttable", var2.posX + 0.4D, var2.posY, var2.posZ, 0.0D, 0.0D, 0.0D);
        var1.spawnParticle("enchantmenttable", var2.posX, var2.posY, var2.posZ - 0.4D, 0.0D, 0.0D, 0.0D);
        var1.spawnParticle("enchantmenttable", var2.posX - 0.4D, var2.posY, var2.posZ, 0.0D, 0.0D, 0.0D);
    }

    private void amuletEffectText2(World var1, EntityPlayer var2)
    {
        var1.spawnParticle("enchantmenttable", var2.posX + 0.2D, var2.posY + 0.2D, var2.posZ + 0.2D, 0.0D, 0.0D, 0.0D);
        var1.spawnParticle("enchantmenttable", var2.posX + 0.2D, var2.posY + 0.2D, var2.posZ - 0.2D, 0.0D, 0.0D, 0.0D);
        var1.spawnParticle("enchantmenttable", var2.posX - 0.2D, var2.posY + 0.2D, var2.posZ + 0.2D, 0.0D, 0.0D, 0.0D);
        var1.spawnParticle("enchantmenttable", var2.posX - 0.2D, var2.posY + 0.2D, var2.posZ - 0.2D, 0.0D, 0.0D, 0.0D);
    }

    private void amuletEffectElements(World var1, EntityPlayer var2)
    {
        var1.spawnParticle("depthsuspend", var2.posX + 0.9D, var2.posY - 1.6D, var2.posZ + 0.9D, 0.0D, 0.0D, 0.0D);
        var1.spawnParticle("splash", var2.posX + 0.9D, var2.posY - 1.6D, var2.posZ - 0.9D, 0.0D, 0.0D, 0.0D);
        var1.spawnParticle("spell", var2.posX - 0.9D, var2.posY - 1.6D, var2.posZ + 0.9D, 0.0D, 0.0D, 0.0D);
        var1.spawnParticle("flame", var2.posX - 0.9D, var2.posY - 1.6D, var2.posZ - 0.9D, 0.0D, 0.0D, 0.0D);
        var1.spawnParticle("depthsuspend", var2.posX + 0.8D, var2.posY - 1.6D, var2.posZ + 0.7D, 0.0D, 0.0D, 0.0D);
        var1.spawnParticle("splash", var2.posX + 0.8D, var2.posY - 1.6D, var2.posZ - 0.7D, 0.0D, 0.0D, 0.0D);
        var1.spawnParticle("spell", var2.posX - 0.7D, var2.posY - 1.6D, var2.posZ + 0.8D, 0.0D, 0.0D, 0.0D);
        var1.spawnParticle("flame", var2.posX - 0.7D, var2.posY - 1.6D, var2.posZ - 0.8D, 0.0D, 0.0D, 0.0D);
        var1.spawnParticle("depthsuspend", var2.posX + 0.7D, var2.posY - 1.6D, var2.posZ + 0.8D, 0.0D, 0.0D, 0.0D);
        var1.spawnParticle("splash", var2.posX + 0.7D, var2.posY - 1.6D, var2.posZ - 0.8D, 0.0D, 0.0D, 0.0D);
        var1.spawnParticle("spell", var2.posX - 0.8D, var2.posY - 1.6D, var2.posZ + 0.7D, 0.0D, 0.0D, 0.0D);
        var1.spawnParticle("flame", var2.posX - 0.8D, var2.posY - 1.6D, var2.posZ - 0.7D, 0.0D, 0.0D, 0.0D);
    }

    private void amuletEffect(World var1, EntityPlayer var2)
    {
        var1.spawnParticle("portal", var2.posX + 0.3D, var2.posY - 0.3D, var2.posZ + 0.3D, 0.0D, 0.0D, 0.0D);
        var1.spawnParticle("portal", var2.posX + 0.3D, var2.posY - 0.3D, var2.posZ - 0.3D, 0.0D, 0.0D, 0.0D);
        var1.spawnParticle("portal", var2.posX - 0.3D, var2.posY - 0.3D, var2.posZ + 0.3D, 0.0D, 0.0D, 0.0D);
        var1.spawnParticle("portal", var2.posX - 0.3D, var2.posY - 0.3D, var2.posZ - 0.3D, 0.0D, 0.0D, 0.0D);
        var1.spawnParticle("portal", var2.posX + 0.3D, var2.posY - 0.9D, var2.posZ + 0.3D, 0.0D, 0.0D, 0.0D);
        var1.spawnParticle("portal", var2.posX + 0.3D, var2.posY - 0.9D, var2.posZ - 0.3D, 0.0D, 0.0D, 0.0D);
        var1.spawnParticle("portal", var2.posX - 0.3D, var2.posY - 0.9D, var2.posZ + 0.3D, 0.0D, 0.0D, 0.0D);
        var1.spawnParticle("portal", var2.posX - 0.3D, var2.posY - 0.9D, var2.posZ - 0.3D, 0.0D, 0.0D, 0.0D);
    }
}

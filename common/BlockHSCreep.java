package mod_HarkenScythe.common;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class BlockHSCreep extends Block
{
    private Icon[] iconBuffer;

    public BlockHSCreep(int var1)
    {
        super(var1, Material.sand);
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    /**
     * When this method is called, your block should register all the icons it needs with the given IconRegister. This
     * is the only chance you get to register icons.
     */
    public void registerIcons(IconRegister var1)
    {
        this.iconBuffer = new Icon[4];
        this.iconBuffer[0] = var1.registerIcon("mod_HarkenScythe:" + this.getUnlocalizedName() + "_top");
        this.iconBuffer[1] = var1.registerIcon("mod_HarkenScythe:" + this.getUnlocalizedName() + "_tilled");
        this.iconBuffer[2] = var1.registerIcon("mod_HarkenScythe:" + this.getUnlocalizedName() + "_tilledBloodied");
        this.iconBuffer[3] = var1.registerIcon("mod_HarkenScythe:" + this.getUnlocalizedName() + "_side");
    }

    @SideOnly(Side.CLIENT)

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public Icon getIcon(int var1, int var2)
    {
        return var1 == 0 ? this.iconBuffer[3] : (var1 == 1 ? this.iconBuffer[var2] : this.iconBuffer[3]);
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void updateTick(World var1, int var2, int var3, int var4, Random var5)
    {
        int var6;

        if (!var1.isRemote)
        {
            if (var1.getBlockLightValue(var2, var3 + 1, var4) < 4 && var1.getBlockLightOpacity(var2, var3 + 1, var4) > 2)
            {
                var1.setBlock(var2, var3, var4, Block.slowSand.blockID);
            }
            else if (var1.getBlockLightValue(var2, var3 + 1, var4) >= 9)
            {
                for (var6 = 0; var6 < 4; ++var6)
                {
                    int var7 = var2 + var5.nextInt(3) - 1;
                    int var8 = var3 + var5.nextInt(5) - 3;
                    int var9 = var4 + var5.nextInt(3) - 1;
                    var1.getBlockId(var7, var8 + 1, var9);

                    if (var1.getBlockId(var7, var8, var9) == Block.slowSand.blockID && var1.getBlockLightValue(var7, var8 + 1, var9) >= 4 && var1.getBlockLightOpacity(var7, var8 + 1, var9) <= 2)
                    {
                        var1.setBlock(var7, var8, var9, this.blockID);
                    }
                }
            }
        }

        var6 = var1.getBlockMetadata(var2, var3, var4);
        Material var11 = var1.getBlockMaterial(var2, var3 + 1, var4);

        if (var6 >= 1 && var11 != Material.plants)
        {
            --var6;
            var1.setBlockMetadataWithNotify(var2, var3, var4, var6, 2);
        }
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World var1, int var2, int var3, int var4)
    {
        float var5 = 0.125F;
        return AxisAlignedBB.getAABBPool().getAABB((double)var2, (double)var3, (double)var4, (double)(var2 + 1), (double)((float)(var3 + 1) - var5), (double)(var4 + 1));
    }

    /**
     * Triggered whenever an entity collides with this block (enters into the block). Args: world, x, y, z, entity
     */
    public void onEntityCollidedWithBlock(World var1, int var2, int var3, int var4, Entity var5)
    {
        var5.motionX *= 0.4D;
        var5.motionZ *= 0.4D;
    }

    @SideOnly(Side.CLIENT)

    /**
     * A randomly called display update to be able to add particles or other items for display
     */
    public void randomDisplayTick(World var1, int var2, int var3, int var4, Random var5)
    {
        super.randomDisplayTick(var1, var2, var3, var4, var5);

        if (var5.nextInt(10) == 0)
        {
            var1.spawnParticle("townaura", (double)((float)var2 + var5.nextFloat()), (double)((float)var3 + 1.1F), (double)((float)var4 + var5.nextFloat()), 0.0D, 0.0D, 0.0D);
        }
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int idDropped(int var1, Random var2, int var3)
    {
        return Block.slowSand.blockID;
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int quantityDropped(Random var1)
    {
        return 1;
    }
}

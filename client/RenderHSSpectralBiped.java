package mod_HarkenScythe.client;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;
import net.minecraftforge.client.MinecraftForgeClient;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderHSSpectralBiped extends RenderLiving
{
    protected ModelHSSpectralBiped modelBipedMain;
    protected float field_77070_b;
    protected ModelHSSpectralBiped field_82423_g;
    protected ModelHSSpectralBiped field_82425_h;
    public static String[] bipedArmorFilenamePrefix = new String[] {"cloth", "chain", "iron", "diamond", "gold"};

    public RenderHSSpectralBiped(ModelHSSpectralBiped var1, float var2)
    {
        this(var1, var2, 1.0F);
    }

    public RenderHSSpectralBiped(ModelHSSpectralBiped var1, float var2, float var3)
    {
        super(var1, var2);
        this.modelBipedMain = var1;
        this.field_77070_b = var3;
        this.func_82421_b();
    }

    protected void func_82421_b()
    {
        this.field_82423_g = new ModelHSSpectralBiped(1.0F);
        this.field_82425_h = new ModelHSSpectralBiped(0.5F);
    }

    /**
     * Queries whether should render the specified pass or not.
     */
    protected int shouldRenderPass(EntityLiving var1, int var2, float var3)
    {
        ItemStack var4 = var1.getCurrentArmor(3 - var2);

        if (var4 != null)
        {
            Item var5 = var4.getItem();

            if (var5 instanceof ItemArmor)
            {
                ItemArmor var6 = (ItemArmor)var5;
                this.loadTexture(ForgeHooksClient.getArmorTexture(var4, "/armor/" + bipedArmorFilenamePrefix[var6.renderIndex] + "_" + (var2 == 2 ? 2 : 1) + ".png"));
                ModelHSSpectralBiped var7 = var2 == 2 ? this.field_82425_h : this.field_82423_g;
                var7.bipedHead.showModel = var2 == 0;
                var7.bipedHeadwear.showModel = var2 == 0;
                var7.bipedBody.showModel = var2 == 1 || var2 == 2;
                var7.bipedRightArm.showModel = var2 == 1;
                var7.bipedLeftArm.showModel = var2 == 1;
                var7.bipedRightLeg.showModel = var2 == 2 || var2 == 3;
                var7.bipedLeftLeg.showModel = var2 == 2 || var2 == 3;
                this.setRenderPassModel(var7);

                if (var7 != null)
                {
                    var7.onGround = this.mainModel.onGround;
                }

                if (var7 != null)
                {
                    var7.isRiding = this.mainModel.isRiding;
                }

                if (var7 != null)
                {
                    var7.isChild = this.mainModel.isChild;
                }

                float var8 = 1.0F;

                if (var6.getArmorMaterial() == EnumArmorMaterial.CLOTH)
                {
                    int var9 = var6.getColor(var4);
                    float var10 = (float)(var9 >> 16 & 255) / 255.0F;
                    float var11 = (float)(var9 >> 8 & 255) / 255.0F;
                    float var12 = (float)(var9 & 255) / 255.0F;
                    GL11.glColor3f(var8 * var10, var8 * var11, var8 * var12);

                    if (var4.isItemEnchanted())
                    {
                        return 31;
                    }

                    return 16;
                }

                GL11.glColor3f(var8, var8, var8);

                if (var4.isItemEnchanted())
                {
                    return 15;
                }

                return 1;
            }
        }

        return -1;
    }

    protected void func_82408_c(EntityLiving var1, int var2, float var3)
    {
        ItemStack var4 = var1.getCurrentArmor(3 - var2);

        if (var4 != null)
        {
            Item var5 = var4.getItem();

            if (var5 instanceof ItemArmor)
            {
                ItemArmor var6 = (ItemArmor)var5;
                this.loadTexture("/armor/" + bipedArmorFilenamePrefix[var6.renderIndex] + "_" + (var2 == 2 ? 2 : 1) + "_b.png");
                float var7 = 1.0F;
                GL11.glColor3f(var7, var7, var7);
            }
        }
    }

    public void doRenderLiving(EntityLiving var1, double var2, double var4, double var6, float var8, float var9)
    {
        float var10 = 1.0F;
        GL11.glColor3f(var10, var10, var10);
        ItemStack var11 = var1.getHeldItem();
        this.func_82420_a(var1, var11);
        double var12 = var4 - (double)var1.yOffset;

        if (var1.isSneaking() && !(var1 instanceof EntityPlayerSP))
        {
            var12 -= 0.125D;
        }

        super.doRenderLiving(var1, var2, var12, var6, var8, var9);
        this.field_82423_g.aimedBow = this.field_82425_h.aimedBow = this.modelBipedMain.aimedBow = false;
        this.field_82423_g.isSneak = this.field_82425_h.isSneak = this.modelBipedMain.isSneak = false;
        this.field_82423_g.heldItemRight = this.field_82425_h.heldItemRight = this.modelBipedMain.heldItemRight = 0;
    }

    protected void func_82420_a(EntityLiving var1, ItemStack var2)
    {
        this.field_82423_g.heldItemRight = this.field_82425_h.heldItemRight = this.modelBipedMain.heldItemRight = var2 != null ? 1 : 0;
        this.field_82423_g.isSneak = this.field_82425_h.isSneak = this.modelBipedMain.isSneak = var1.isSneaking();
    }

    protected void renderEquippedItems(EntityLiving var1, float var2)
    {
        float var3 = 1.0F;
        GL11.glColor3f(var3, var3, var3);
        super.renderEquippedItems(var1, var2);
        ItemStack var4 = var1.getHeldItem();
        ItemStack var5 = var1.getCurrentArmor(3);
        float var6;
        IItemRenderer var7;
        boolean var8;

        if (var5 != null)
        {
            GL11.glPushMatrix();
            this.modelBipedMain.bipedHead.postRender(0.0625F);
            var7 = MinecraftForgeClient.getItemRenderer(var5, IItemRenderer.ItemRenderType.EQUIPPED);
            var8 = var7 != null && var7.shouldUseRenderHelper(IItemRenderer.ItemRenderType.EQUIPPED, var5, IItemRenderer.ItemRendererHelper.BLOCK_3D);

            if (var5.getItem() instanceof ItemBlock)
            {
                if (var8 || RenderBlocks.renderItemIn3d(Block.blocksList[var5.itemID].getRenderType()))
                {
                    var6 = 0.625F;
                    GL11.glTranslatef(0.0F, -0.25F, 0.0F);
                    GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
                    GL11.glScalef(var6, -var6, -var6);
                }

                this.renderManager.itemRenderer.renderItem(var1, var5, 0);
            }
            else if (var5.getItem().itemID == Item.skull.itemID)
            {
                var6 = 1.0625F;
                GL11.glScalef(var6, -var6, -var6);
                String var9 = "";

                if (var5.hasTagCompound() && var5.getTagCompound().hasKey("SkullOwner"))
                {
                    var9 = var5.getTagCompound().getString("SkullOwner");
                }

                TileEntitySkullRenderer.skullRenderer.func_82393_a(-0.5F, 0.0F, -0.5F, 1, 180.0F, var5.getItemDamage(), var9);
            }

            GL11.glPopMatrix();
        }

        if (var4 != null)
        {
            GL11.glPushMatrix();

            if (this.mainModel.isChild)
            {
                var6 = 0.5F;
                GL11.glTranslatef(0.0F, 0.625F, 0.0F);
                GL11.glRotatef(-20.0F, -1.0F, 0.0F, 0.0F);
                GL11.glScalef(var6, var6, var6);
            }

            this.modelBipedMain.bipedRightArm.postRender(0.0625F);
            GL11.glTranslatef(-0.0625F, 0.4375F, 0.0625F);
            var7 = MinecraftForgeClient.getItemRenderer(var4, IItemRenderer.ItemRenderType.EQUIPPED);
            var8 = var7 != null && var7.shouldUseRenderHelper(IItemRenderer.ItemRenderType.EQUIPPED, var4, IItemRenderer.ItemRendererHelper.BLOCK_3D);

            if (var4.getItem() instanceof ItemBlock && (var8 || RenderBlocks.renderItemIn3d(Block.blocksList[var4.itemID].getRenderType())))
            {
                var6 = 0.5F;
                GL11.glTranslatef(0.0F, 0.1875F, -0.3125F);
                var6 *= 0.75F;
                GL11.glRotatef(20.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
                GL11.glScalef(-var6, -var6, var6);
            }
            else if (var4.itemID == Item.bow.itemID)
            {
                var6 = 0.625F;
                GL11.glTranslatef(0.0F, 0.125F, 0.3125F);
                GL11.glRotatef(-20.0F, 0.0F, 1.0F, 0.0F);
                GL11.glScalef(var6, -var6, var6);
                GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
            }
            else if (Item.itemsList[var4.itemID].isFull3D())
            {
                var6 = 0.625F;

                if (Item.itemsList[var4.itemID].shouldRotateAroundWhenRendering())
                {
                    GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
                    GL11.glTranslatef(0.0F, -0.125F, 0.0F);
                }

                this.func_82422_c();
                GL11.glScalef(var6, -var6, var6);
                GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
            }
            else
            {
                var6 = 0.375F;
                GL11.glTranslatef(0.25F, 0.1875F, -0.1875F);
                GL11.glScalef(var6, var6, var6);
                GL11.glRotatef(60.0F, 0.0F, 0.0F, 1.0F);
                GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(20.0F, 0.0F, 0.0F, 1.0F);
            }

            this.renderManager.itemRenderer.renderItem(var1, var4, 0);

            if (var4.getItem().requiresMultipleRenderPasses())
            {
                for (int var10 = 1; var10 < var4.getItem().getRenderPasses(var4.getItemDamage()); ++var10)
                {
                    this.renderManager.itemRenderer.renderItem(var1, var4, var10);
                }
            }

            GL11.glPopMatrix();
        }
    }

    protected void func_82422_c()
    {
        GL11.glTranslatef(0.0F, 0.1875F, 0.0F);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(Entity var1, double var2, double var4, double var6, float var8, float var9)
    {
        this.doRenderLiving((EntityLiving)var1, var2, var4, var6, var8, var9);
    }
}

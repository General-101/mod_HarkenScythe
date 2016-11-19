package mod_HarkenScythe.client;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mod_HarkenScythe.common.EntityHSSpectralWitch;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderHSSpectralWitch extends RenderLiving
{
    private ModelHSSpectralWitch field_82414_a;
    private int field_82413_f;

    public RenderHSSpectralWitch()
    {
        super(new ModelHSSpectralWitch(0.0F), 0.5F);
        this.field_82414_a = (ModelHSSpectralWitch)this.mainModel;
        this.field_82413_f = this.field_82414_a.func_82899_a();
    }

    public void func_82412_a(EntityHSSpectralWitch var1, double var2, double var4, double var6, float var8, float var9)
    {
        ItemStack var10 = var1.getHeldItem();

        if (this.field_82414_a.func_82899_a() != this.field_82413_f)
        {
            Minecraft.getMinecraft().getLogAgent().logInfo("Loaded new witch model");
            this.mainModel = this.field_82414_a = new ModelHSSpectralWitch(0.0F);
            this.field_82413_f = this.field_82414_a.func_82899_a();
        }

        this.field_82414_a.field_82900_g = var10 != null;
        super.doRenderLiving(var1, var2, var4, var6, var8, var9);
    }

    protected void func_82411_a(EntityHSSpectralWitch var1, float var2)
    {
        float var3 = 1.0F;
        GL11.glColor3f(var3, var3, var3);
        super.renderEquippedItems(var1, var2);
        ItemStack var4 = var1.getHeldItem();

        if (var4 != null)
        {
            GL11.glPushMatrix();
            float var5;

            if (this.mainModel.isChild)
            {
                var5 = 0.5F;
                GL11.glTranslatef(0.0F, 0.625F, 0.0F);
                GL11.glRotatef(-20.0F, -1.0F, 0.0F, 0.0F);
                GL11.glScalef(var5, var5, var5);
            }

            this.field_82414_a.field_82898_f.postRender(0.0625F);
            GL11.glTranslatef(-0.0625F, 0.53125F, 0.21875F);

            if (var4.itemID < 256 && RenderBlocks.renderItemIn3d(Block.blocksList[var4.itemID].getRenderType()))
            {
                var5 = 0.5F;
                GL11.glTranslatef(0.0F, 0.1875F, -0.3125F);
                var5 *= 0.75F;
                GL11.glRotatef(20.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
                GL11.glScalef(var5, -var5, var5);
            }
            else if (var4.itemID == Item.bow.itemID)
            {
                var5 = 0.625F;
                GL11.glTranslatef(0.0F, 0.125F, 0.3125F);
                GL11.glRotatef(-20.0F, 0.0F, 1.0F, 0.0F);
                GL11.glScalef(var5, -var5, var5);
                GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
            }
            else if (Item.itemsList[var4.itemID].isFull3D())
            {
                var5 = 0.625F;

                if (Item.itemsList[var4.itemID].shouldRotateAroundWhenRendering())
                {
                    GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
                    GL11.glTranslatef(0.0F, -0.125F, 0.0F);
                }

                this.func_82410_b();
                GL11.glScalef(var5, -var5, var5);
                GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
            }
            else
            {
                var5 = 0.375F;
                GL11.glTranslatef(0.25F, 0.1875F, -0.1875F);
                GL11.glScalef(var5, var5, var5);
                GL11.glRotatef(60.0F, 0.0F, 0.0F, 1.0F);
                GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(20.0F, 0.0F, 0.0F, 1.0F);
            }

            GL11.glRotatef(-15.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(40.0F, 0.0F, 0.0F, 1.0F);
            this.renderManager.itemRenderer.renderItem(var1, var4, 0);

            if (var4.getItem().requiresMultipleRenderPasses())
            {
                this.renderManager.itemRenderer.renderItem(var1, var4, 1);
            }

            GL11.glPopMatrix();
        }
    }

    protected void func_82410_b()
    {
        GL11.glTranslatef(0.0F, 0.1875F, 0.0F);
    }

    protected void func_82409_b(EntityHSSpectralWitch var1, float var2)
    {
        float var3 = 0.9375F;
        GL11.glScalef(var3, var3, var3);
    }

    /**
     * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
     * entityLiving, partialTickTime
     */
    protected void preRenderCallback(EntityLiving var1, float var2)
    {
        this.func_82409_b((EntityHSSpectralWitch)var1, var2);
    }

    protected void renderEquippedItems(EntityLiving var1, float var2)
    {
        this.func_82411_a((EntityHSSpectralWitch)var1, var2);
    }

    public void doRenderLiving(EntityLiving var1, double var2, double var4, double var6, float var8, float var9)
    {
        this.func_82412_a((EntityHSSpectralWitch)var1, var2, var4, var6, var8, var9);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(Entity var1, double var2, double var4, double var6, float var8, float var9)
    {
        this.func_82412_a((EntityHSSpectralWitch)var1, var2, var4, var6, var8, var9);
    }
}

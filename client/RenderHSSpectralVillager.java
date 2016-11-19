package mod_HarkenScythe.client;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mod_HarkenScythe.common.EntityHSSpectralVillager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderHSSpectralVillager extends RenderLiving
{
    protected ModelHSSpectralVillager villagerModel;

    public RenderHSSpectralVillager()
    {
        super(new ModelHSSpectralVillager(0.0F), 0.5F);
        this.villagerModel = (ModelHSSpectralVillager)this.mainModel;
    }

    protected int shouldVillagerRenderPass(EntityHSSpectralVillager var1, int var2, float var3)
    {
        return -1;
    }

    public void renderVillager(EntityHSSpectralVillager var1, double var2, double var4, double var6, float var8, float var9)
    {
        super.doRenderLiving(var1, var2, var4, var6, var8, var9);
    }

    protected void renderVillagerEquipedItems(EntityHSSpectralVillager var1, float var2)
    {
        super.renderEquippedItems(var1, var2);
    }

    protected void preRenderVillager(EntityHSSpectralVillager var1, float var2)
    {
        float var3 = 0.9375F;
        this.shadowSize = 0.5F;
        GL11.glScalef(var3, var3, var3);
    }

    /**
     * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
     * entityLiving, partialTickTime
     */
    protected void preRenderCallback(EntityLiving var1, float var2)
    {
        this.preRenderVillager((EntityHSSpectralVillager)var1, var2);
    }

    /**
     * Queries whether should render the specified pass or not.
     */
    protected int shouldRenderPass(EntityLiving var1, int var2, float var3)
    {
        return this.shouldVillagerRenderPass((EntityHSSpectralVillager)var1, var2, var3);
    }

    protected void renderEquippedItems(EntityLiving var1, float var2)
    {
        this.renderVillagerEquipedItems((EntityHSSpectralVillager)var1, var2);
    }

    public void doRenderLiving(EntityLiving var1, double var2, double var4, double var6, float var8, float var9)
    {
        this.renderVillager((EntityHSSpectralVillager)var1, var2, var4, var6, var8, var9);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(Entity var1, double var2, double var4, double var6, float var8, float var9)
    {
        this.renderVillager((EntityHSSpectralVillager)var1, var2, var4, var6, var8, var9);
    }
}

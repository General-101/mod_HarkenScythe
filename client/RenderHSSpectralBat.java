package mod_HarkenScythe.client;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mod_HarkenScythe.common.EntityHSSpectralBat;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderHSSpectralBat extends RenderLiving
{
    private int renderedBatSize;

    public RenderHSSpectralBat()
    {
        super(new ModelHSSpectralBat(), 0.25F);
        this.renderedBatSize = ((ModelHSSpectralBat)this.mainModel).getBatSize();
    }

    public void func_82443_a(EntityHSSpectralBat var1, double var2, double var4, double var6, float var8, float var9)
    {
        int var10 = ((ModelHSSpectralBat)this.mainModel).getBatSize();

        if (var10 != this.renderedBatSize)
        {
            this.renderedBatSize = var10;
            this.mainModel = new ModelHSSpectralBat();
        }

        super.doRenderLiving(var1, var2, var4, var6, var8, var9);
    }

    protected void func_82442_a(EntityHSSpectralBat var1, float var2)
    {
        GL11.glScalef(0.35F, 0.35F, 0.35F);
    }

    protected void func_82445_a(EntityHSSpectralBat var1, double var2, double var4, double var6)
    {
        super.renderLivingAt(var1, var2, var4, var6);
    }

    protected void func_82444_a(EntityHSSpectralBat var1, float var2, float var3, float var4)
    {
        if (!var1.getIsBatHanging())
        {
            GL11.glTranslatef(0.0F, MathHelper.cos(var2 * 0.3F) * 0.1F, 0.0F);
        }
        else
        {
            GL11.glTranslatef(0.0F, -0.1F, 0.0F);
        }

        super.rotateCorpse(var1, var2, var3, var4);
    }

    /**
     * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
     * entityLiving, partialTickTime
     */
    protected void preRenderCallback(EntityLiving var1, float var2)
    {
        this.func_82442_a((EntityHSSpectralBat)var1, var2);
    }

    protected void rotateCorpse(EntityLiving var1, float var2, float var3, float var4)
    {
        this.func_82444_a((EntityHSSpectralBat)var1, var2, var3, var4);
    }

    /**
     * Sets a simple glTranslate on a LivingEntity.
     */
    protected void renderLivingAt(EntityLiving var1, double var2, double var4, double var6)
    {
        this.func_82445_a((EntityHSSpectralBat)var1, var2, var4, var6);
    }

    public void doRenderLiving(EntityLiving var1, double var2, double var4, double var6, float var8, float var9)
    {
        this.func_82443_a((EntityHSSpectralBat)var1, var2, var4, var6, var8, var9);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(Entity var1, double var2, double var4, double var6, float var8, float var9)
    {
        this.func_82443_a((EntityHSSpectralBat)var1, var2, var4, var6, var8, var9);
    }
}

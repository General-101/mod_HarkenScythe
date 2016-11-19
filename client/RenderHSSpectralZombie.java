package mod_HarkenScythe.client;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mod_HarkenScythe.common.EntityHSSpectralZombie;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;

@SideOnly(Side.CLIENT)
public class RenderHSSpectralZombie extends RenderHSSpectralBiped
{
    private ModelHSSpectralBiped field_82434_o;
    protected ModelHSSpectralBiped field_82437_k;
    protected ModelHSSpectralBiped field_82435_l;
    protected ModelHSSpectralBiped field_82436_m;
    protected ModelHSSpectralBiped field_82433_n;
    private int field_82431_q = 1;

    public RenderHSSpectralZombie()
    {
        super(new ModelHSSpectralZombie(), 0.5F, 1.0F);
        this.field_82434_o = this.modelBipedMain;
    }

    protected void func_82421_b()
    {
        this.field_82423_g = new ModelHSSpectralZombie(1.0F, true);
        this.field_82425_h = new ModelHSSpectralZombie(0.5F, true);
        this.field_82437_k = this.field_82423_g;
        this.field_82435_l = this.field_82425_h;
    }

    protected int func_82429_a(EntityHSSpectralZombie var1, int var2, float var3)
    {
        this.func_82427_a(var1);
        return super.shouldRenderPass(var1, var2, var3);
    }

    public void func_82426_a(EntityHSSpectralZombie var1, double var2, double var4, double var6, float var8, float var9)
    {
        this.func_82427_a(var1);
        super.doRenderLiving(var1, var2, var4, var6, var8, var9);
    }

    protected void func_82428_a(EntityHSSpectralZombie var1, float var2)
    {
        this.func_82427_a(var1);
        super.renderEquippedItems(var1, var2);
    }

    private void func_82427_a(EntityHSSpectralZombie var1)
    {
        this.mainModel = this.field_82434_o;
        this.field_82423_g = this.field_82437_k;
        this.field_82425_h = this.field_82435_l;
        this.modelBipedMain = (ModelHSSpectralBiped)this.mainModel;
    }

    protected void func_82430_a(EntityHSSpectralZombie var1, float var2, float var3, float var4)
    {
        super.rotateCorpse(var1, var2, var3, var4);
    }

    protected void renderEquippedItems(EntityLiving var1, float var2)
    {
        this.func_82428_a((EntityHSSpectralZombie)var1, var2);
    }

    public void doRenderLiving(EntityLiving var1, double var2, double var4, double var6, float var8, float var9)
    {
        this.func_82426_a((EntityHSSpectralZombie)var1, var2, var4, var6, var8, var9);
    }

    /**
     * Queries whether should render the specified pass or not.
     */
    protected int shouldRenderPass(EntityLiving var1, int var2, float var3)
    {
        return this.func_82429_a((EntityHSSpectralZombie)var1, var2, var3);
    }

    protected void rotateCorpse(EntityLiving var1, float var2, float var3, float var4)
    {
        this.func_82430_a((EntityHSSpectralZombie)var1, var2, var3, var4);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(Entity var1, double var2, double var4, double var6, float var8, float var9)
    {
        this.func_82426_a((EntityHSSpectralZombie)var1, var2, var4, var6, var8, var9);
    }
}

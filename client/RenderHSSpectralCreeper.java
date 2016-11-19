package mod_HarkenScythe.client;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mod_HarkenScythe.common.EntityHSSpectralCreeper;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderHSSpectralCreeper extends RenderLiving
{
    private ModelBase creeperModel = new ModelHSSpectralCreeper(2.0F);

    public RenderHSSpectralCreeper()
    {
        super(new ModelHSSpectralCreeper(), 0.5F);
    }

    protected void updateCreeperScale(EntityHSSpectralCreeper var1, float var2)
    {
        float var3 = var1.getCreeperFlashIntensity(var2);
        float var4 = 1.0F + MathHelper.sin(var3 * 100.0F) * var3 * 0.01F;

        if (var3 < 0.0F)
        {
            var3 = 0.0F;
        }

        if (var3 > 1.0F)
        {
            var3 = 1.0F;
        }

        var3 *= var3;
        var3 *= var3;
        float var5 = (1.0F + var3 * 0.4F) * var4;
        float var6 = (1.0F + var3 * 0.1F) / var4;
        GL11.glScalef(var5, var6, var5);
    }

    protected int updateCreeperColorMultiplier(EntityHSSpectralCreeper var1, float var2, float var3)
    {
        float var4 = var1.getCreeperFlashIntensity(var3);

        if ((int)(var4 * 10.0F) % 2 == 0)
        {
            return 0;
        }
        else
        {
            int var5 = (int)(var4 * 0.2F * 255.0F);

            if (var5 < 0)
            {
                var5 = 0;
            }

            if (var5 > 255)
            {
                var5 = 255;
            }

            short var6 = 255;
            short var7 = 255;
            short var8 = 255;
            return var5 << 24 | var6 << 16 | var7 << 8 | var8;
        }
    }

    protected int renderCreeperPassModel(EntityHSSpectralCreeper var1, int var2, float var3)
    {
        if (var1.getPowered())
        {
            if (var1.isInvisible())
            {
                GL11.glDepthMask(false);
            }
            else
            {
                GL11.glDepthMask(true);
            }

            if (var2 == 1)
            {
                float var4 = (float)var1.ticksExisted + var3;
                this.loadTexture("/armor/power.png");
                GL11.glMatrixMode(GL11.GL_TEXTURE);
                GL11.glLoadIdentity();
                float var5 = var4 * 0.01F;
                float var6 = var4 * 0.01F;
                GL11.glTranslatef(var5, var6, 0.0F);
                this.setRenderPassModel(this.creeperModel);
                GL11.glMatrixMode(GL11.GL_MODELVIEW);
                GL11.glEnable(GL11.GL_BLEND);
                float var7 = 0.5F;
                GL11.glColor4f(var7, var7, var7, 1.0F);
                GL11.glDisable(GL11.GL_LIGHTING);
                GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
                return 1;
            }

            if (var2 == 2)
            {
                GL11.glMatrixMode(GL11.GL_TEXTURE);
                GL11.glLoadIdentity();
                GL11.glMatrixMode(GL11.GL_MODELVIEW);
                GL11.glEnable(GL11.GL_LIGHTING);
                GL11.glDisable(GL11.GL_BLEND);
            }
        }

        return -1;
    }

    protected int func_77061_b(EntityHSSpectralCreeper var1, int var2, float var3)
    {
        return -1;
    }

    /**
     * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
     * entityLiving, partialTickTime
     */
    protected void preRenderCallback(EntityLiving var1, float var2)
    {
        this.updateCreeperScale((EntityHSSpectralCreeper)var1, var2);
    }

    /**
     * Returns an ARGB int color back. Args: entityLiving, lightBrightness, partialTickTime
     */
    protected int getColorMultiplier(EntityLiving var1, float var2, float var3)
    {
        return this.updateCreeperColorMultiplier((EntityHSSpectralCreeper)var1, var2, var3);
    }

    /**
     * Queries whether should render the specified pass or not.
     */
    protected int shouldRenderPass(EntityLiving var1, int var2, float var3)
    {
        return this.renderCreeperPassModel((EntityHSSpectralCreeper)var1, var2, var3);
    }

    protected int inheritRenderPass(EntityLiving var1, int var2, float var3)
    {
        return this.func_77061_b((EntityHSSpectralCreeper)var1, var2, var3);
    }
}

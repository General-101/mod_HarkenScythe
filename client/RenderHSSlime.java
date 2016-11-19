package mod_HarkenScythe.client;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mod_HarkenScythe.common.EntityHSSlime;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.EntityLiving;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderHSSlime extends RenderLiving
{
    private ModelBase scaleAmount;

    public RenderHSSlime(ModelBase var1, ModelBase var2, float var3)
    {
        super(var1, var3);
        this.scaleAmount = var2;
    }

    protected int shouldSlimeRenderPass(EntityHSSlime var1, int var2, float var3)
    {
        if (var1.isInvisible())
        {
            return 0;
        }
        else if (var2 == 0)
        {
            this.setRenderPassModel(this.scaleAmount);
            GL11.glEnable(GL11.GL_NORMALIZE);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            return 1;
        }
        else
        {
            if (var2 == 1)
            {
                GL11.glDisable(GL11.GL_BLEND);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            }

            return -1;
        }
    }

    protected void scaleSlime(EntityHSSlime var1, float var2)
    {
        float var3 = (float)var1.getSlimeSize();
        float var4 = (var1.field_70812_c + (var1.field_70811_b - var1.field_70812_c) * var2) / (var3 * 0.5F + 1.0F);
        float var5 = 1.0F / (var4 + 1.0F);
        GL11.glScalef(var5 * var3, 1.0F / var5 * var3, var5 * var3);
    }

    /**
     * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
     * entityLiving, partialTickTime
     */
    protected void preRenderCallback(EntityLiving var1, float var2)
    {
        this.scaleSlime((EntityHSSlime)var1, var2);
    }

    /**
     * Queries whether should render the specified pass or not.
     */
    protected int shouldRenderPass(EntityLiving var1, int var2, float var3)
    {
        return this.shouldSlimeRenderPass((EntityHSSlime)var1, var2, var3);
    }
}

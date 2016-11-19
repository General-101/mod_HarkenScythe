package mod_HarkenScythe.client;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelSkeletonHead;
import net.minecraft.entity.Entity;

public class ModelHSSkull extends ModelSkeletonHead
{
    public ModelRenderer skeletonHead;

    public ModelHSSkull()
    {
        this(0, 35, 64, 64);
    }

    public ModelHSSkull(int var1, int var2, int var3, int var4)
    {
        this.textureWidth = var3;
        this.textureHeight = var4;
        this.skeletonHead = new ModelRenderer(this, var1, var2);
        this.skeletonHead.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
        this.skeletonHead.setRotationPoint(0.0F, 0.0F, 0.0F);
    }

    /**
     * Sets the models various rotation angles then renders the model.
     */
    public void render(Entity var1, float var2, float var3, float var4, float var5, float var6, float var7)
    {
        this.setRotationAngles(var2, var3, var4, var5, var6, var7, var1);
        this.skeletonHead.render(var7);
    }

    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
     * "far" arms and legs can swing at most.
     */
    public void setRotationAngles(float var1, float var2, float var3, float var4, float var5, float var6, Entity var7)
    {
        super.setRotationAngles(var1, var2, var3, var4, var5, var6, var7);
        this.skeletonHead.rotateAngleY = var4 / (180F / (float)Math.PI);
        this.skeletonHead.rotateAngleX = var5 / (180F / (float)Math.PI);
    }
}

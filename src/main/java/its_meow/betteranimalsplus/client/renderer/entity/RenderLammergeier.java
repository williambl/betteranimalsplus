package its_meow.betteranimalsplus.client.renderer.entity;

import javax.annotation.Nonnull;

import its_meow.betteranimalsplus.client.model.ModelLammergeier;
import its_meow.betteranimalsplus.common.entity.EntityLammergeier;
import its_meow.betteranimalsplus.init.ModTextures;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class RenderLammergeier extends MobRenderer<EntityLammergeier, ModelLammergeier<EntityLammergeier>> {

    public RenderLammergeier(EntityRendererManager rendermanagerIn) {
        super(rendermanagerIn, new ModelLammergeier<EntityLammergeier>(), 0.3F);
    }

    @Override
    @Nonnull
    protected ResourceLocation getEntityTexture(@Nonnull EntityLammergeier entity) {
        int typeNumber = entity.getTypeNumber();
        ResourceLocation result = null;
        switch (typeNumber) {
        case 1:
            result = ModTextures.lam_orange;
            break;
        case 2:
            result = ModTextures.lam_red;
            break;
        case 3:
            result = ModTextures.lam_white;
            break;
        case 4:
            result = ModTextures.lam_yellow;
            break;
        }
        return result;

    }

}

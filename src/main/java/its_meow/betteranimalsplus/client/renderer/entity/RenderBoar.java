package its_meow.betteranimalsplus.client.renderer.entity;

import com.mojang.blaze3d.platform.GlStateManager;

import its_meow.betteranimalsplus.client.model.ModelBoar;
import its_meow.betteranimalsplus.common.entity.EntityBoar;
import its_meow.betteranimalsplus.init.ModTextures;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class RenderBoar extends MobRenderer<EntityBoar, ModelBoar<EntityBoar>> {

    public RenderBoar(EntityRendererManager rendermanagerIn) {
        super(rendermanagerIn, new ModelBoar<EntityBoar>(), 0.6F);
    }

    @Override
    protected void preRenderCallback(EntityBoar entitylivingbaseIn, float partialTickTime) {
        if (this.entityModel.isChild) {
            GlStateManager.scaled(0.6D, 0.6D, 0.6D);
        } else {
            GlStateManager.scaled(1.0D, 1.0D, 1.0D);
        }
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityBoar entity) {
        int type = entity.getTypeNumber();
        switch (type) {
        case 1:
            return ModTextures.boar_1;
        case 2:
            return ModTextures.boar_2;
        case 3:
            return ModTextures.boar_3;
        case 4:
            return ModTextures.boar_4;
        default:
            return ModTextures.boar_1;
        }
    }

}

package tamermod.client.gui.Primitives;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import tamermod.client.gui.GuiHelper;

public class TextureAtlasSpriteWrapper extends TextureAtlasSpritePart{

    public TextureAtlasSpriteWrapper(TextureAtlasSprite sprite) {
        super(sprite, 0,0, sprite.getWidth(),sprite.getHeight());
    }
}

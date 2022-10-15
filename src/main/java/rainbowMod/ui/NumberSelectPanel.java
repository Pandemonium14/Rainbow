package rainbowMod.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import rainbowMod.RainbowMod;

import java.util.Set;

public class NumberSelectPanel {

    public int selectedNumber;
    public int min;
    public int max;

    public String msg = "Number of characters";

    private static final Texture LeftArrowTexture = ImageMaster.loadImage("images/ui/charSelect/tinyLeftArrow.png");
    private static final Texture RightArrowTexture = ImageMaster.loadImage("images/ui/charSelect/tinyRightArrow.png");
    private Hitbox leftHitbox;
    private Hitbox rightHitbox;

    public float x;
    public float y;

    public NumberSelectPanel() {
    }

    public void initializeHitboxes() {
        leftHitbox = new Hitbox(x + 20.0f*Settings.scale,y - LeftArrowTexture.getHeight() * 1.5f, LeftArrowTexture.getWidth(), LeftArrowTexture.getHeight());
        rightHitbox = new Hitbox(x + RightArrowTexture.getWidth() * 2+ 20.0f*Settings.scale,y - LeftArrowTexture.getHeight() * 1.5f, RightArrowTexture.getWidth(), RightArrowTexture.getHeight());
    }

    public void update() {
        leftHitbox.update();
        if (leftHitbox.hovered) {

        }
        if (leftHitbox.hovered && InputHelper.justReleasedClickLeft) {
            if (selectedNumber > min) {
                selectedNumber--;
            }
        }

        rightHitbox.update();
        if (rightHitbox.hovered) {

        }
        if (rightHitbox.hovered && InputHelper.justReleasedClickLeft) {
            if (selectedNumber < max) {
                selectedNumber++;
            }
        }
    }

    public void render(SpriteBatch sb) {
        sb.setColor(Color.WHITE);

        FontHelper.renderFont(sb, RainbowMod.getPanelFont(), msg,x,y + 5f,Color.WHITE);


        sb.draw(LeftArrowTexture,x + 20.0f*Settings.scale,y - LeftArrowTexture.getHeight() * 1.5f);
        sb.draw(RightArrowTexture, x + RightArrowTexture.getWidth() * 2+ 20.0f*Settings.scale,y - LeftArrowTexture.getHeight() * 1.5f);

        FontHelper.renderFont(sb, RainbowMod.getPanelFont(), selectedNumber + "", x + RightArrowTexture.getWidth()+ 35.0f*Settings.scale, y - LeftArrowTexture.getHeight() * 0.8f, Color.WHITE);

    }
}

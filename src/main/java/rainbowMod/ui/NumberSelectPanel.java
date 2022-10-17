package rainbowMod.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import rainbowMod.RainbowMod;
import rainbowMod.patches.CharSelectPanelsHooks;

import java.util.Set;

public class NumberSelectPanel {

    private static final UIStrings strings = CardCrawlGame.languagePack.getUIString(RainbowMod.makeID("NumberSelectPanel"));

    public int selectedNumber;
    public int min;
    public int max;

    public String msg = "Number of characters";

    private static final Texture LeftArrowTexture = ImageMaster.loadImage("images/ui/charSelect/tinyLeftArrow.png");
    private static final Texture RightArrowTexture = ImageMaster.loadImage("images/ui/charSelect/tinyRightArrow.png");
    private Hitbox leftHitbox;
    private Hitbox rightHitbox;
    boolean lightenLeft = false;
    boolean lightenRight = false;

    public float x;
    public float y;

    private static final Color darkenedColor = new Color(0.8f,0.8f,0.8f,1f);
    private static final Color localWhite = new Color(1f,1f,1f,1f);

    public NumberSelectPanel() {
    }

    public void initializeHitboxes() {
        leftHitbox = new Hitbox(x + 20.0f*Settings.scale,y - LeftArrowTexture.getHeight() * 1.5f, LeftArrowTexture.getWidth(), LeftArrowTexture.getHeight());
        rightHitbox = new Hitbox(x + RightArrowTexture.getWidth() * 2+ 20.0f*Settings.scale,y - LeftArrowTexture.getHeight() * 1.5f, RightArrowTexture.getWidth(), RightArrowTexture.getHeight());
    }

    public void update() {
        updateMaximum();
        leftHitbox.update();
        if (leftHitbox.hovered && selectedNumber > min) {
            lightenLeft = true;
        } else {
            lightenLeft = false;
        }
        if (leftHitbox.hovered && InputHelper.justReleasedClickLeft) {
            if (selectedNumber > min) {
                selectedNumber--;
            }
        }

        rightHitbox.update();
        if (rightHitbox.hovered && selectedNumber < max) {
            lightenRight = true;
        } else {
            lightenRight = false;
        }
        if (rightHitbox.hovered && InputHelper.justReleasedClickLeft) {
            if (selectedNumber < max) {
                selectedNumber++;
            }
        }
    }

    private void updateMaximum() {
        max = RainbowMod.optionsPanel.charNum;
        if (selectedNumber > max) {
            selectedNumber = max;
        }
    }

    public void render(SpriteBatch sb) {

        FontHelper.renderFont(sb, RainbowMod.getPanelFont(), strings.TEXT[0],x,y + 5f,localWhite);

        Color drawColor = lightenLeft ? localWhite : darkenedColor;
        sb.setColor(drawColor);
        sb.draw(LeftArrowTexture,x + 20.0f*Settings.scale,y - LeftArrowTexture.getHeight() * 1.5f);

        drawColor = lightenRight ? localWhite : darkenedColor;
        sb.setColor(drawColor);
        sb.draw(RightArrowTexture, x + RightArrowTexture.getWidth() * 2+ 20.0f*Settings.scale,y - LeftArrowTexture.getHeight() * 1.5f);

        sb.setColor(Color.WHITE);

        FontHelper.renderFont(sb, RainbowMod.getPanelFont(), selectedNumber + "", x + RightArrowTexture.getWidth()+ 35.0f*Settings.scale, y - LeftArrowTexture.getHeight() * 0.8f, localWhite);

    }
}

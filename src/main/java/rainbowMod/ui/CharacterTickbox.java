package rainbowMod.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import rainbowMod.RainbowMod;

import java.io.IOException;

public class CharacterTickbox {

    private static final Texture BOX_TEXTURE = ImageMaster.loadImage(RainbowMod.makeImagePath("ui/rainbowCheckmark.png"));
    private static final Texture CHECK_TEXTURE = ImageMaster.loadImage(RainbowMod.makeImagePath("ui/greenTick.png"));
    private static Color renderColor = Color.WHITE.cpy();

    private static SpireConfig config = getConfig();

    public static boolean enabled = true;


    private Hitbox hb;

    private float x = 0;
    private float y = 0;

    public CharacterTickbox() {
        x = Settings.WIDTH - BOX_TEXTURE.getWidth();
        y = Settings.HEIGHT - BOX_TEXTURE.getHeight();

        hb = new Hitbox(x,y, BOX_TEXTURE.getWidth(), BOX_TEXTURE.getHeight());
    }

    private static SpireConfig getConfig() {
        try {
            SpireConfig spireConfig = new SpireConfig("TheRainbow", "characterConfig");// 89
            spireConfig.load();// 90
            return spireConfig;// 91
        } catch (IOException var1) {// 92
            var1.printStackTrace();// 93
            return null;// 95
        }
    }


    public static boolean isEnabled(AbstractPlayer character) {
        if (config != null && config.has(character.getClass().getName())) {
            return config.getBool(character.getClass().getName());
        } else  {
            return true;
        }
    }

    public void update(AbstractPlayer character) {
        String key = character.getClass().getName();
        hb.update();
        boolean oldVal = enabled;
        if (hb.hovered) {
            renderColor.a = 1f;
        } else {
            renderColor.a = 0.4f;
        }
        if (hb.hovered && InputHelper.justReleasedClickLeft) {
            boolean newVal = false;
            boolean changeSuccessful = false;
            try {
                newVal = !config.getBool(key);
                config.setBool(key, newVal);
                config.save();
                changeSuccessful = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (changeSuccessful) {
                if (newVal) {
                    RainbowMod.optionsPanel.charNum ++;
                } else {
                    RainbowMod.optionsPanel.charNum --;
                }
            }
        }
        try {
            config.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        enabled = config.getBool(key);
    }

    public void render(SpriteBatch sb) {
        sb.setColor(renderColor);
        sb.draw(BOX_TEXTURE, x, y);
        if (enabled) {
            sb.draw(CHECK_TEXTURE, x, y);
        }
        sb.setColor(Color.WHITE);
    }



}

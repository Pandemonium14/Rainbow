package rainbowMod.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import rainbowMod.RainbowMod;

import java.io.IOException;
import java.util.ArrayList;

public class CharacterTickbox {

    private static final Texture BOX_TEXTURE = ImageMaster.loadImage(RainbowMod.makeImagePath("ui/rainbowCheckmark.png"));
    private static final Texture CHECK_TEXTURE = ImageMaster.loadImage(RainbowMod.makeImagePath("ui/greenTick.png"));
    private static Color renderColor = Color.WHITE.cpy();
    private static final String FORCE_TEXT = "Force into pool";

    private static SpireConfig config = getConfig();

    public static boolean enabled = true;
    private static boolean forced = false;
    public static ArrayList<AbstractPlayer> forcedChars = new ArrayList<>();


    private Hitbox hb;
    private Hitbox forceHb;

    private float x = 0;
    private float y = 0;

    public CharacterTickbox() {
        x = Settings.WIDTH - BOX_TEXTURE.getWidth();
        y = Settings.HEIGHT - BOX_TEXTURE.getHeight();

        hb = new Hitbox(x,y, BOX_TEXTURE.getWidth(), BOX_TEXTURE.getHeight());
        forceHb = new Hitbox(x - FontHelper.getWidth(RainbowMod.getPanelFont(), FORCE_TEXT, 1f) + BOX_TEXTURE.getWidth() - 3f,y - FontHelper.getHeight(RainbowMod.getEnergyFont(), FORCE_TEXT, 1f), FontHelper.getWidth(RainbowMod.getPanelFont(), FORCE_TEXT, 1f), FontHelper.getHeight(RainbowMod.getEnergyFont(), FORCE_TEXT, 1f));
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
        forceHb.update();
        boolean oldVal = enabled;
        if (hb.hovered || forceHb.hovered) {
            renderColor.a = 1f;
        } else {
            renderColor.a = 0.4f;
        }
        if (hb.hovered && InputHelper.justReleasedClickLeft) {
            boolean newVal = false;
            boolean changeSuccessful = false;
            try {
                newVal = !CharacterTickbox.isEnabled(character);
                config.setBool(key, newVal);
                config.save();
                changeSuccessful = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (changeSuccessful) {
                RainbowMod.optionsPanel.charNum = RainbowMod.optionsPanel.makeCharNum();
            }
        }
        try {
            config.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        enabled = isEnabled(character);
        forced = forcedChars.contains(character);
        if (enabled) {
            if (forceHb.hovered && InputHelper.justReleasedClickLeft) {
                if (forced) {
                    forcedChars.remove(character);
                    forced = false;
                } else {
                    forcedChars.add(character);
                    forced = true;
                }
            }
        } else {
            forcedChars.remove(character);
        }
    }

    public void render(SpriteBatch sb) {
        sb.setColor(renderColor);
        sb.draw(BOX_TEXTURE, x, y);
        if (enabled) {
            sb.draw(CHECK_TEXTURE, x, y);
        }

        sb.setColor(Color.WHITE);
        if (enabled) {
            if (forced) {
                FontHelper.renderFont(sb, RainbowMod.getPanelFont(), FORCE_TEXT, x - FontHelper.getWidth(RainbowMod.getPanelFont(), FORCE_TEXT, 1f) + BOX_TEXTURE.getWidth() - 3f, y, Color.GREEN);
            } else {
                FontHelper.renderFont(sb, RainbowMod.getPanelFont(), FORCE_TEXT, x - FontHelper.getWidth(RainbowMod.getPanelFont(), FORCE_TEXT, 1f) + BOX_TEXTURE.getWidth() - 3f, y, renderColor);
            }
        }
    }



}

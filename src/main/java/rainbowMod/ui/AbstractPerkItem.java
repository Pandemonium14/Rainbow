package rainbowMod.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import rainbowMod.RainbowMod;

public abstract class AbstractPerkItem {


    private String description;
    public int cost = 0;
    public boolean enabled = false;
    private Texture img;
    private Color costColor;
    private int availablePoints;

    public Hitbox checkmarkHitbox;

    public AbstractPerkItem(String desc, Texture texture, int cost) {
        this.cost = cost;
        img = texture;
        description = desc;
    }

    public void receiveAvailablePerkPoints(int points) {
        availablePoints = points;
    }

    public abstract void trigger();

    public void alwaysTrigger() {}

    public void update() {
        checkmarkHitbox.update();
        if (checkmarkHitbox.hovered && InputHelper.justReleasedClickLeft) {
            if (!enabled && availablePoints >= cost) {
                enabled = true;
            } else if (enabled) {
                enabled = false;
            }
        }
        if (enabled) {
            costColor = Color.GREEN.cpy();
        } else if (availablePoints >= cost) {
            costColor = Color.WHITE.cpy();
        } else {
            costColor = Color.SALMON.cpy();
        }
    }

    public void render(SpriteBatch sb, float x, float y) {
        sb.setColor(Color.WHITE);

        FontHelper.renderFontCentered(sb, RainbowMod.getEnergyFont(),cost + "", x, y, costColor, 1f);
        x = x + 20f;

        sb.draw(ImageMaster.OPTION_TOGGLE,x,y - 20f);
        if (enabled) {
            sb.draw(ImageMaster.OPTION_TOGGLE_ON,x,y - 20f);
        }
        x = x + ImageMaster.OPTION_TOGGLE.getWidth();

        sb.draw(img, x, y - img.getHeight()/2f);
        x = x + 60f;

        FontHelper.renderFont(sb, RainbowMod.getPanelFont(), description, x, y + 7f, Color.WHITE);
    }
}

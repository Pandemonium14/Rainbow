package rainbowMod.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import rainbowMod.RainbowMod;
import rainbowMod.ui.perkItems.PrayerWheelPerk;
import rainbowMod.ui.perkItems.QuestionCardPerk;

import java.util.ArrayList;

public class PerkSelectPanel {

    private static final float Y_OFFSET = 75f;

    public String title = "Perks:";
    public int totalPoints = 0;
    public int usedPoints = 0;

    public ArrayList<AbstractPerkItem> items = new ArrayList<>();

    public float x;
    public float y;

    public PerkSelectPanel() {
        items.add(new PrayerWheelPerk());
        items.add(new QuestionCardPerk());
    }

    public void initializeHitboxes() {
        int offsetMult = 1;
        for (AbstractPerkItem item : items) {
            item.checkmarkHitbox = new Hitbox(x + 20f, y - offsetMult*Y_OFFSET - 20f, ImageMaster.OPTION_TOGGLE_ON.getWidth(), ImageMaster.OPTION_TOGGLE_ON.getHeight());
            offsetMult ++;
        }
    }

    public void receiveTotalPoints(int points) {
        if (points != totalPoints) {
            disableAll();
            totalPoints = points;
        }
    }

    public void disableAll() {
        for (AbstractPerkItem item : items) {
            item.enabled = false;
        }
    }

    public void update() {
        for (AbstractPerkItem item : items) {
            item.receiveAvailablePerkPoints(totalPoints - usedPoints);
            item.update();
        }

        usedPoints = 0;
        for (AbstractPerkItem item : items) {
            if (item.enabled) {
                usedPoints += item.cost;
            }
        }
    }

    public void render(SpriteBatch sb) {
        FontHelper.renderFont(sb, RainbowMod.getEnergyFont(), title, x, y + 10f, Color.WHITE);

        FontHelper.renderFont(sb, RainbowMod.getEnergyFont(), totalPoints-usedPoints + " points left", x +180f, y +10f, Color.WHITE);
        int offsetMult = 1;
        for (AbstractPerkItem item : items) {
            item.render(sb, x, y - Y_OFFSET * offsetMult);
            offsetMult++;
        }
    }
}

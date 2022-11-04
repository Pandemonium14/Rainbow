package rainbowMod.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.UIStrings;
import rainbowMod.RainbowMod;
import rainbowMod.ui.perkItems.*;

import java.util.ArrayList;

public class PerkSelectPanel {

    private static final float Y_OFFSET = 75f;


    private static final UIStrings strings = CardCrawlGame.languagePack.getUIString(RainbowMod.makeID("PerkSelectPanel"));

    public String title = "Perks:";
    public int totalPoints = 0;
    public int usedPoints = 0;

    public ArrayList<AbstractPerkItem> items = new ArrayList<>();

    public float x;
    public float y;

    public PerkSelectPanel() {
        items.add(new StarterRelicPerk());
        items.add(new QuestionCardPerk());
        items.add(new HealingPerk());
        items.add(new PrayerWheelPerk());

        items.add(new MaxHealthPerk());
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

    public void triggerPerks() {
        for (AbstractPerkItem item : items) {
            if (item.enabled) item.trigger();
            item.alwaysTrigger();
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
        FontHelper.renderFont(sb, RainbowMod.getEnergyFont(), strings.TEXT[0], x, y + 10f, Color.WHITE);

        FontHelper.renderFont(sb, RainbowMod.getEnergyFont(), totalPoints-usedPoints + strings.EXTRA_TEXT[0], x +180f, y +10f, Color.WHITE);
        int offsetMult = 1;
        for (AbstractPerkItem item : items) {
            item.render(sb, x, y - Y_OFFSET * offsetMult);
            offsetMult++;
        }
    }
}

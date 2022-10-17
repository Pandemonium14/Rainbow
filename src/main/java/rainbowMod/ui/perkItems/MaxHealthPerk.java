package rainbowMod.ui.perkItems;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import rainbowMod.RainbowMod;
import rainbowMod.ui.AbstractPerkItem;

public class MaxHealthPerk extends AbstractPerkItem {

    private static final UIStrings strings = CardCrawlGame.languagePack.getUIString(RainbowMod.makeID("MaxHealthPerk"));

    public MaxHealthPerk() {
        super(strings.TEXT[0], null, 0);
    }

    @Override
    public void update() {

    }

    @Override
    public void render(SpriteBatch sb, float x, float y) {
        x = x + 20f;
        x = x + 60f;
        FontHelper.renderFont(sb, RainbowMod.getPanelFont(), strings.TEXT[0], x, y + 7f, Color.WHITE);
    }

    @Override
    public void trigger() {

    }

    @Override
    public void alwaysTrigger() {
        for (int i = 0; i < RainbowMod.optionsPanel.getAvailablePoints(); i++) {
            AbstractDungeon.player.increaseMaxHp(2, false);
        }
    }
}

package rainbowMod.ui.perkItems;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.PrayerWheel;
import rainbowMod.RainbowMod;
import rainbowMod.relics.HealingPerkRelic;
import rainbowMod.ui.AbstractPerkItem;

public class HealingPerk extends AbstractPerkItem {

    private static final UIStrings strings = CardCrawlGame.languagePack.getUIString(RainbowMod.makeID("HealingPerk"));

    public HealingPerk() {
        super(strings.TEXT[0], ImageMaster.loadImage(RainbowMod.makeImagePath("ui/RainbowHeart.png")), 4);
    }

    @Override
    public void trigger() {
        AbstractRelic relic = new HealingPerkRelic();
        relic.obtain();// 684
        relic.isObtained = true;// 685
        relic.isAnimating = false;// 686
        relic.isDone = false;// 687
        relic.flash();
    }
}

package rainbowMod.ui.perkItems;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.PrayerWheel;
import rainbowMod.RainbowMod;
import rainbowMod.ui.AbstractPerkItem;

public class PrayerWheelPerk extends AbstractPerkItem {

    private static final UIStrings strings = CardCrawlGame.languagePack.getUIString(RainbowMod.makeID("PrayerWheelPerk"));

    public PrayerWheelPerk() {
        super(strings.TEXT[0], ImageMaster.loadImage("RainbowModResources/images/ui/prayerWheel.png"),6);
    }

    @Override
    public void trigger() {
        AbstractRelic relic = new PrayerWheel();
        relic.obtain();// 684
        relic.isObtained = true;// 685
        relic.isAnimating = false;// 686
        relic.isDone = false;// 687
        relic.flash();
    }
}

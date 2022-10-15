package rainbowMod.ui.perkItems;

import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.PrayerWheel;
import rainbowMod.ui.AbstractPerkItem;

public class PrayerWheelPerk extends AbstractPerkItem {

    public PrayerWheelPerk() {
        super("Start with a Prayer Wheel.", ImageMaster.loadImage("RainbowModResources/images/ui/prayerWheel.png"),4);
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

package rainbowMod.ui.perkItems;

import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.PrayerWheel;
import com.megacrit.cardcrawl.relics.QuestionCard;
import rainbowMod.ui.AbstractPerkItem;

public class QuestionCardPerk extends AbstractPerkItem {

    public QuestionCardPerk() {
        super("Start with a Question Card.", ImageMaster.loadImage("RainbowModResources/images/ui/questionCard.png"),2);
    }

    @Override
    public void trigger() {
        AbstractRelic relic = new QuestionCard();
        relic.obtain();// 684
        relic.isObtained = true;// 685
        relic.isAnimating = false;// 686
        relic.isDone = false;// 687
        relic.flash();
    }
}

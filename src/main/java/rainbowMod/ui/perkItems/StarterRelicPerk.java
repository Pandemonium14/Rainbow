package rainbowMod.ui.perkItems;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import rainbowMod.RainbowMod;
import rainbowMod.ui.AbstractPerkItem;

import java.util.ArrayList;

public class StarterRelicPerk extends AbstractPerkItem {

    private ArrayList<String> relicList;

    public StarterRelicPerk() {
        super("Start with an additional Starter relic.", ImageMaster.loadImage("RainbowModResources/images/ui/relic.png"), 2);
    }

    @Override
    public void trigger() {
        if (RainbowMod.optionsPanel.relicList.size()>0) {
            int r = AbstractDungeon.cardRng.random(RainbowMod.optionsPanel.relicList.size() - 1);
            String key = RainbowMod.optionsPanel.relicList.get(r);
            RainbowMod.optionsPanel.relicList.remove(key);
            AbstractRelic relic = RelicLibrary.getRelic(key).makeCopy();
            relic.obtain();// 684
            relic.isObtained = true;// 685
            relic.isAnimating = false;// 686
            relic.isDone = false;// 687
        }
    }

    @Override
    public void alwaysTrigger() {
        trigger();
    }
}

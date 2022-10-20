package rainbowMod.powers;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import rainbowMod.RainbowMod;
import rainbowMod.actions.ImaginationAction;

public class ImaginationPower extends AbstractEasyPower {

    public static final String ID = RainbowMod.makeID("ImaginationPower");
    private static final PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(ID);

    public ImaginationPower(AbstractPlayer p, int amount) {
        super(ID, strings.NAME, PowerType.BUFF, false, p, amount);
    }

    @Override
    public void updateDescription() {
        if (amount >= 2) {
            description = strings.DESCRIPTIONS[1] + amount + strings.DESCRIPTIONS[2];
        } else {
            description = strings.DESCRIPTIONS[0];
        }
    }

    @Override
    public void atStartOfTurnPostDraw() {
        for (int i = 0 ; i < amount ; i++) {
            addToBot(new ImaginationAction());
        }
    }
}

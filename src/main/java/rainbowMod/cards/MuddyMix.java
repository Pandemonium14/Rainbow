package rainbowMod.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import rainbowMod.RainbowMod;
import rainbowMod.actions.MuddyMixAction;

import java.util.ArrayList;

public class MuddyMix extends AbstractEasyCard {

    public static final String ID = RainbowMod.makeID("MuddyMix");

    public MuddyMix() {
        super(ID, 1, CardType.SKILL, CardRarity.SPECIAL,CardTarget.SELF);
        baseBlock = 4;
        exhaust = true;
    }

    @Override
    public void upp() {
        upgradeBlock(2);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new SelectCardsInHandAction( 99,"exhaust.",
                true, true, (c) -> true,
                (list) -> addToBot(new MuddyMixAction(list, block))));
    }
}

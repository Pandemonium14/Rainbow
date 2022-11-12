package rainbowMod.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import rainbowMod.RainbowMod;
import rainbowMod.actions.MuddyMixAction;
import rainbowMod.actions.SynthesisAction;

public class Synthesis extends AbstractEasyCard {

    public static final String ID = RainbowMod.makeID("Synthesis");

    public Synthesis() {
        super(ID,0,CardType.SKILL, CardRarity.SPECIAL, CardTarget.SELF);
        magicNumber = baseMagicNumber = 1;
        cardsToPreview = new Miracle();
    }

    @Override
    public void upp() {
        exhaust = false;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new SelectCardsInHandAction( 2,"exhaust.",
                true, true, (c) -> true,
                (list) -> addToBot(new SynthesisAction(list, magicNumber, upgraded))));
    }
}

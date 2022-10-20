package rainbowMod.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import rainbowMod.RainbowMod;
import rainbowMod.actions.DispersionAction;

public class Dispersion extends AbstractEasyCard {

    public static final String ID = RainbowMod.makeID("Dispersion");

    public Dispersion() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
    }

    @Override
    public void upp() {
        rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new SelectCardsInHandAction(1, "Choose a card to exhaust.", false, false, (c) -> true,
                (list) -> {for (AbstractCard c : list) addToBot(new DispersionAction(c, upgraded));}));
    }
}

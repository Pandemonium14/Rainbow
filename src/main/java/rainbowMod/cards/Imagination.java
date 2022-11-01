package rainbowMod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import rainbowMod.RainbowMod;
import rainbowMod.actions.ImaginationAction;
import rainbowMod.powers.ImaginationPower;

public class Imagination extends AbstractEasyCard {

    public static final String ID = RainbowMod.makeID("Imagination");

    public Imagination() {
        super(ID, 1, CardType.POWER, CardRarity.SPECIAL, CardTarget.SELF);
    }

    @Override
    public void upp() {
        upgradeBaseCost(0);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {
        addToBot(new ApplyPowerAction(p,p, new ImaginationPower(p, 1)));
    }
}

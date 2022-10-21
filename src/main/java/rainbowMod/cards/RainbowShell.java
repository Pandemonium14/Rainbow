package rainbowMod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import rainbowMod.RainbowMod;
import rainbowMod.powers.RainbowShellPower;

public class RainbowShell extends AbstractEasyCard {

    public static final String ID = RainbowMod.makeID("RainbowShell");

    public RainbowShell() {
        super(ID, 1, CardType.POWER, CardRarity.SPECIAL,CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
        isEthereal = true;
    }

    @Override
    public void upp() {
        isEthereal = false;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {
        addToBot(new ApplyPowerAction(p,p, new RainbowShellPower(p, magicNumber)));
    }

    @Override
    public void update() {
        super.update();
    }
}

package rainbowMod.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.RegenPower;
import rainbowMod.RainbowMod;

public class ShimmeringBandage extends AbstractEasyCard {

    public static final String ID = RainbowMod.makeID("ShimmeringBandage");


    public ShimmeringBandage() {
        super(ID,1,CardType.SKILL,CardRarity.SPECIAL,CardTarget.SELF);
        baseMagicNumber = magicNumber = 4;
        exhaust = true;
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {
        addToBot(new AddTemporaryHPAction(p,p, magicNumber));
        addToBot(new ApplyPowerAction(p,p, new RegenPower(p, magicNumber)));
    }
}

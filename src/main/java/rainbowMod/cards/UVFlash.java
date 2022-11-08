package rainbowMod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import rainbowMod.RainbowMod;
import rainbowMod.actions.UVFlashAction;

public class UVFlash extends AbstractEasyCard {

    public static final String ID = RainbowMod.makeID("UVFlash");

    public UVFlash() {
        super(ID, 0, CardType.SKILL, CardRarity.SPECIAL, CardTarget.ENEMY);
        baseMagicNumber = magicNumber = 1;
    }

    @Override
    public void upp() {

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!upgraded) {
            addToBot(new UVFlashAction(m,magicNumber));
        } else {
            addToBot(new ApplyPowerAction(m,p,new WeakPower(m, magicNumber, false)));
            addToBot(new ApplyPowerAction(m,p,new VulnerablePower(m, magicNumber, false)));
        }
        addToBot(new DrawCardAction(magicNumber));
    }
}

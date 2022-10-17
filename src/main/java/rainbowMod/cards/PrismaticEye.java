package rainbowMod.cards;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import rainbowMod.RainbowMod;
import rainbowMod.TheRainbow;

public class PrismaticEye extends AbstractEasyCard {

    public static final String ID = RainbowMod.makeID("PrismaticEye");

    public PrismaticEye() {
        super(ID,1,CardType.SKILL,CardRarity.COMMON,CardTarget.SELF, TheRainbow.Enums.RAINBOW_CARD_COLOR);
        magicNumber = baseMagicNumber = 3;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new ScryAction(magicNumber));
        addToBot(new DrawCardAction(magicNumber));

    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
    }
}

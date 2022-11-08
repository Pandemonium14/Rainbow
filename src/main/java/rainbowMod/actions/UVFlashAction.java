package rainbowMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

public class UVFlashAction extends AbstractGameAction {

    private final AbstractMonster m;

    public UVFlashAction(AbstractMonster c, int amount) {
        m = c;
        this.amount = amount;
    }

    @Override
    public void update() {
        if (this.m != null && (this.m.intent == AbstractMonster.Intent.ATTACK || this.m.intent == AbstractMonster.Intent.ATTACK_BUFF || this.m.intent == AbstractMonster.Intent.ATTACK_DEBUFF || this.m.intent == AbstractMonster.Intent.ATTACK_DEFEND)) {
            addToTop(new ApplyPowerAction(m, AbstractDungeon.player, new WeakPower(m, amount, false)));
        } else {
            addToTop(new ApplyPowerAction(m, AbstractDungeon.player, new VulnerablePower(m,amount,false)));
        }
        isDone = true;
    }
}

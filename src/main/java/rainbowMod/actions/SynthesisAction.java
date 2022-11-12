package rainbowMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.List;

public class SynthesisAction extends AbstractGameAction {

    private final List<AbstractCard> cardList = new ArrayList<>();
    private final boolean upgraded;


    public SynthesisAction(List<AbstractCard> list, int number, boolean upgraded) {
        cardList.addAll(list);
        amount = number;
        this.upgraded = upgraded;
    }

    @Override
    public void update() {
        for (AbstractCard c : cardList) {
            addToBot(new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand));
        }
        for (int i = 0; i < amount; i++) {
            if (!upgraded) {
                if (cardList.size() <= 1) {
                    addToBot(new MakeTempCardInHandAction(new Miracle()));
                } else {
                    AbstractCard miracle = new Miracle();
                    miracle.upgrade();
                    addToBot(new MakeTempCardInHandAction(miracle));
                }
            } else {
                if (cardList.size() == 0) {
                    addToBot(new MakeTempCardInHandAction(new Miracle()));
                } else {
                    AbstractCard miracle = new Miracle();
                    miracle.upgrade();
                    addToBot(new MakeTempCardInHandAction(miracle));
                }
            }
        }
        isDone = true;
    }
}

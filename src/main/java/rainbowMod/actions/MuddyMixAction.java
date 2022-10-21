package rainbowMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.List;

public class MuddyMixAction extends AbstractGameAction {

    private final ArrayList<AbstractCard> selectedCards = new ArrayList<>();
    private final int block;

    public MuddyMixAction(List<AbstractCard> cards, int blockGain) {
        selectedCards.addAll(cards);
        block = blockGain;
    }

    @Override
    public void update() {
        ArrayList<AbstractCard.CardColor> colors = new ArrayList<>();
        for (AbstractCard c : selectedCards) {
            boolean newColor = false;
            if (!colors.contains(c.color)) {
                colors.add(c.color);
                newColor = true;
            }
            addToBot(new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand));
            if (newColor) {
                addToBot(new GainBlockAction(AbstractDungeon.player, block));
                addToBot(new DrawCardAction(1));
            }
        }
        isDone = true;
    }
}

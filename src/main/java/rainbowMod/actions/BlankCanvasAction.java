package rainbowMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ShuffleAction;
import com.megacrit.cardcrawl.actions.defect.ShuffleAllAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class BlankCanvasAction extends AbstractGameAction {

    public BlankCanvasAction() {

    }

    @Override
    public void update() {
        int handSize = AbstractDungeon.player.hand.size();
        addToBot(new ShuffleAllAction());
        addToBot(new ShuffleAction(AbstractDungeon.player.drawPile));
        addToBot(new DrawCardAction(handSize));
        isDone = true;
    }
}

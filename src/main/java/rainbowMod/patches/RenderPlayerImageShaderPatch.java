package rainbowMod.patches;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import rainbowMod.TheRainbow;

import java.util.ArrayList;

@SpirePatch2(clz = AbstractPlayer.class, method = "renderPlayerImage")
public class RenderPlayerImageShaderPatch {

    private static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractPlayer.class, "sr");
            return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), finalMatcher);
        }
    }

    @SpireInsertPatch(locator = Locator.class)
    public static void changeShader(SpriteBatch sb) {
        //CardCrawlGame.psb.setShader(TheRainbow.rainbowShader);
    }
}

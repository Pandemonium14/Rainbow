package rainbowMod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import rainbowMod.powers.RainbowShellPower;

import java.util.ArrayList;

@SpirePatch2(clz = AbstractPlayer.class, method = "damage")
public class RainbowShellPatch {

    private static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractPlayer.class, "decrementBlock");
            return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), finalMatcher);
        }
    }

    @SpireInsertPatch(locator = Locator.class, localvars = {"damageAmount"})
    public static void rainbowShellPatch(AbstractPlayer __instance, @ByRef int[] damageAmount, DamageInfo info) {
        if (__instance.hasPower(RainbowShellPower.ID) && damageAmount[0] > 0 && info.type == DamageInfo.DamageType.NORMAL) {
            damageAmount[0] = 0;
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(__instance, __instance, __instance.getPower(RainbowShellPower.ID), 1));
        }
    }
}

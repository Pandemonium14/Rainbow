package rainbowMod.patches;


import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.characters.CharacterManager;
import rainbowMod.ui.CharacterTickbox;

@SpirePatch2(clz = CharacterManager.class, method = "recreateCharacter")
public class RedoForcedListPatch {

    @SpirePostfixPatch
    public static void redoRainbowList() {
        if (CharacterTickbox.forcedChars.size()>0) CharacterTickbox.redoForcedList();
    }
}

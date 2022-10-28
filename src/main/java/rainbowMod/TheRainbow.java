package rainbowMod;

import basemod.BaseMod;
import basemod.ReflectionHacks;
import basemod.abstracts.CustomEnergyOrb;
import basemod.abstracts.CustomPlayer;
import basemod.animations.AbstractAnimation;
import basemod.animations.SpriterAnimation;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.compression.lzma.Base;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.red.Defend_Red;
import com.megacrit.cardcrawl.cards.red.Strike_Red;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.cutscenes.CutscenePanel;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import rainbowMod.cards.PrismaticEye;
import rainbowMod.ui.CharacterTickbox;

import java.util.*;

import static rainbowMod.RainbowMod.*;

public class TheRainbow extends CustomPlayer {
    private static final String[] orbTextures = {
            modID + "Resources/images/char/mainChar/orb/layer1.png",
            modID + "Resources/images/char/mainChar/orb/layer2.png",
            modID + "Resources/images/char/mainChar/orb/layer3.png",
            modID + "Resources/images/char/mainChar/orb/layer4.png",
            modID + "Resources/images/char/mainChar/orb/layer5.png",
            modID + "Resources/images/char/mainChar/orb/layer6.png",
            modID + "Resources/images/char/mainChar/orb/layer1d.png",
            modID + "Resources/images/char/mainChar/orb/layer2d.png",
            modID + "Resources/images/char/mainChar/orb/layer3d.png",
            modID + "Resources/images/char/mainChar/orb/layer4d.png",
            modID + "Resources/images/char/mainChar/orb/layer5d.png",};
    static final String ID = makeID("TheRainbow"); //TODO: Change this
    static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString(ID);
    static final String[] NAMES = characterStrings.NAMES;
    static final String[] TEXT = characterStrings.TEXT;
    static AbstractCard.CardColor lastReturnedColor;
    private int colorReturnCounter = 0;
    private AbstractPlayer appearanceCharacter;




    public TheRainbow(String name, PlayerClass setClass) {
        super(name, setClass, new CustomEnergyOrb(orbTextures, modID + "Resources/images/char/mainChar/orb/vfx.png", null), new SpriterAnimation(
                modID + "Resources/images/char/mainChar/static.scml"));
        initializeClass(null,
                SHOULDER1,
                SHOULDER2,
                CORPSE,
                getLoadout(), 20.0F, -10.0F, 166.0F, 327.0F, new EnergyManager(3));


        dialogX = (drawX + 0.0F * Settings.scale);
        dialogY = (drawY + 240.0F * Settings.scale);
    }

    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(NAMES[0], TEXT[0],
                75, 75, 3, 99, 5, this, getStartingRelics(),
                getStartingDeck(), false);
    }

    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(Strike_Red.ID);
        retVal.add(Defend_Red.ID);
        return retVal;
    }

    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();
        return retVal;
    }

    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playA("UNLOCK_PING", MathUtils.random(-0.2F, 0.2F));
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.SHORT,
                false);
    }

    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "UNLOCK_PING";
    }

    @Override
    public int getAscensionMaxHPLoss() {
        return 8;
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        if (lastReturnedColor != null && colorReturnCounter < 4) {
            return lastReturnedColor;
        } else if (selectedColors != null && selectedColors.size() > 0) {
            int r = AbstractDungeon.cardRng.random(selectedColors.size() - 1);
            lastReturnedColor = selectedColors.get(r);
            colorReturnCounter = 0;
            return selectedColors.get(r);
        } else {
            return AbstractCard.CardColor.RED;
        }
    }

    @Override
    public Color getCardTrailColor() {
        return characterColor.cpy();
    }

    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontRed;
    }

    @Override
    public String getLocalizedCharacterName() {
        return NAMES[0];
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        return new PrismaticEye();
    }

    @Override
    public String getTitle(AbstractPlayer.PlayerClass playerClass) {
        return NAMES[1];
    }

    @Override
    public AbstractPlayer newInstance() {
        return new TheRainbow(name, chosenClass);
    }

    @Override
    public Color getCardRenderColor() {
        return characterColor.cpy();
    }

    @Override
    public Color getSlashAttackColor() {
        return characterColor.cpy();
    }

    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{
                AbstractGameAction.AttackEffect.FIRE,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.FIRE};
    }

    @Override
    public String getSpireHeartText() {
        return TEXT[1];
    }

    @Override
    public String getVampireText() {
        return TEXT[2];
    }

    public void setupAnimation() {
        ArrayList<AbstractPlayer> chars = new ArrayList<>();
        for (AbstractPlayer character : CardCrawlGame.characterManager.getAllCharacters()) {
            if (character.chosenClass != Enums.THE_RAINBOW && CharacterTickbox.isEnabled(character)) {
                chars.add(character);
            }
        }
        appearanceCharacter = chars.get(AbstractDungeon.miscRng.random(chars.size()-1));
    }

    public static class Enums {
        //TODO: Change these.
        @SpireEnum
        public static AbstractPlayer.PlayerClass THE_RAINBOW;
        @SpireEnum(name = "RAINBOW_COLOR")
        public static AbstractCard.CardColor RAINBOW_CARD_COLOR;
        @SpireEnum(name = "RAINBOW_COLOR")
        @SuppressWarnings("unused")
        public static CardLibrary.LibraryType LIBRARY_COLOR;
    }

    @Override
    public void update() {
        super.update();
        colorReturnCounter ++;
        if (appearanceCharacter != null) {
            appearanceCharacter.updateAnimations();
        }
        //updateShader();
    }

    @Override
    public void renderPlayerImage(SpriteBatch sb) {
        sb.setShader(rainbowShader);
        if (appearanceCharacter == null) {
            super.renderPlayerImage(sb);
        } else {
            sb.setShader(rainbowShader);
            appearanceCharacter.renderPlayerImage(sb);

        }
        sb.setShader(null);
    }




    //Shader stuff
    public static ShaderProgram rainbowShader;
    private static float shaderTimer = 0f;
    private static final float SHADER_STRENGTH = 100f;
    private static final float SHADER_SPEED = 1f;
    private static final float SHADER_ANGLE = 0f;
    private static final float SHADER_WIDTH = Settings.SAVED_WIDTH;

    private static void initShader() {
        if (rainbowShader == null) {
            try {
                rainbowShader = new ShaderProgram(Gdx.files.internal("RainbowModResources/shaders/rainbowShader/vertex.vs"),
                                                  Gdx.files.internal("RainbowModResources/shaders/rainbowShader/fragment.fs"));
                if (!rainbowShader.isCompiled()) {
                    System.err.println(rainbowShader.getLog());
                }
                if (rainbowShader.getLog().length() > 0) {
                    System.out.println(rainbowShader.getLog());
                }
                BaseMod.logger.info("============ Just initialized rainbow Shader");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void updateShader() {
        initShader();
        if (rainbowShader != null) {
            rainbowShader.setUniformf("u_time", shaderTimer);
            rainbowShader.setUniformf("u_strength", SHADER_STRENGTH);
            rainbowShader.setUniformf("u_speed", SHADER_SPEED);
            rainbowShader.setUniformf("u_angle", SHADER_ANGLE);
            rainbowShader.setUniformf("u_width", SHADER_WIDTH);
        }
        shaderTimer += Gdx.graphics.getDeltaTime();
    }

    @Override
    public List<CutscenePanel> getCutscenePanels() {
        BaseMod.logger.info("======== Creating Cutscene");
        List<CutscenePanel> panels = new ArrayList<>();
        ArrayList<AbstractPlayer> chars = new ArrayList<>(CardCrawlGame.characterManager.getAllCharacters());
        for (AbstractPlayer ch : chars) {
            if (selectedColors.contains(ch.getCardColor()) && ch.chosenClass != chosenClass) {
                BaseMod.logger.info("========= Adding " + ch.chosenClass.name() + " panels...");
                if (ch instanceof CustomPlayer) {
                    panels.addAll(((CustomPlayer) ch).getCutscenePanels());
                } else {
                    switch(ch.chosenClass) {
                        case THE_SILENT:
                            panels.add(new CutscenePanel("images/scenes/silent1.png", "ATTACK_POISON2"));// 37
                            panels.add(new CutscenePanel("images/scenes/silent2.png"));// 38
                            panels.add(new CutscenePanel("images/scenes/silent3.png"));// 39
                            break;// 40
                        case DEFECT:
                            panels.add(new CutscenePanel("images/scenes/defect1.png", "ATTACK_MAGIC_BEAM_SHORT"));// 43
                            panels.add(new CutscenePanel("images/scenes/defect2.png"));// 44
                            panels.add(new CutscenePanel("images/scenes/defect3.png"));// 45
                            break;// 46
                        case WATCHER:
                            panels.add(new CutscenePanel("images/scenes/watcher1.png", "WATCHER_HEART_PUNCH"));// 49
                            panels.add(new CutscenePanel("images/scenes/watcher2.png"));// 50
                            panels.add(new CutscenePanel("images/scenes/watcher3.png"));// 51
                            break;// 52
                        default:
                            panels.add(new CutscenePanel("images/scenes/ironclad1.png", "ATTACK_HEAVY"));// 55
                            panels.add(new CutscenePanel("images/scenes/ironclad2.png"));// 56
                            panels.add(new CutscenePanel("images/scenes/ironclad3.png"));// 57
                    }
                }
            }
        }
        ArrayList<CutscenePanel> selection = new ArrayList<>();
        while (selection.size()<4 && panels.size()>0) {
            int r = AbstractDungeon.miscRng.random(panels.size()-1);
            selection.add(panels.get(r));
            panels.remove(r);
        }
        return selection;
    }
}

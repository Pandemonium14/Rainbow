package rainbowMod;

import basemod.BaseMod;
import basemod.ReflectionHacks;
import basemod.abstracts.CustomEnergyOrb;
import basemod.abstracts.CustomPlayer;
import basemod.animations.AbstractAnimation;
import basemod.animations.SpriterAnimation;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.compression.lzma.Base;
import com.esotericsoftware.spine.Skeleton;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.red.Defend_Red;
import com.megacrit.cardcrawl.cards.red.Strike_Red;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.cutscenes.CutscenePanel;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import org.w3c.dom.Text;
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


    private FrameBuffer buffer;
    private TextureRegion playerTexture;




    public TheRainbow(String name, PlayerClass setClass) {
        super(name, setClass, new CustomEnergyOrb(orbTextures, modID + "Resources/images/char/mainChar/orb/vfx.png", null), new SpriterAnimation(
                modID + "Resources/images/char/mainChar/static.scml"));
        initializeClass(null,
                SHOULDER1,
                SHOULDER2,
                CORPSE,
                getLoadout(), 20.0F, -10.0F, 250.0F, 327.0F, new EnergyManager(3));


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
            if (selectedColors.contains(character.getCardColor()) && character.chosenClass != Enums.THE_RAINBOW) {
                chars.add(character);
            }
        }
        int r = MathUtils.random(chars.size()-1);
        if (appearanceCharacter == null) {
            appearanceCharacter = chars.get(0);
        }
        if (chars.get(r).chosenClass != appearanceCharacter.chosenClass) {
            appearanceCharacter = chars.get(r);
        } else if (r != chars.size()-1) {
            appearanceCharacter = chars.get(r+1);
        } else {
            appearanceCharacter = chars.get(0);
        }
        changeModel = false;
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
            appearanceCharacter.animX = animX;
            appearanceCharacter.animY = animY;
            appearanceCharacter.drawX = drawX;
            appearanceCharacter.drawY = drawY;
        }
        if (hb.hovered && InputHelper.justReleasedClickRight) {
            RainbowMod.changeModel = true;
        }
    }

    @Override
    public void renderPlayerImage(SpriteBatch sb) {
        updateShader();
        if (appearanceCharacter != null && !(appearanceCharacter instanceof TheRainbow)) {
            renderAppearance(sb);
        }
    }

    private void renderAppearance(SpriteBatch sb) {
        sb.flush();
        if (buffer == null) {
            buffer = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
        }
        buffer.begin();
        Gdx.gl.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.gl.glColorMask(true,true,true,true);
        appearanceCharacter.renderPlayerImage(sb);
        sb.flush();
        buffer.end();
        if (playerTexture == null) {
            playerTexture = new TextureRegion(buffer.getColorBufferTexture());
            playerTexture.flip(false, true);
        } else {
            playerTexture.setTexture(buffer.getColorBufferTexture());
        }
        sb.setShader(rainbowShader);
        updateShader();
        sb.draw(playerTexture,-Settings.VERT_LETTERBOX_AMT, -Settings.HORIZ_LETTERBOX_AMT);
        sb.setShader(null);
    }




    //Shader stuff
    public static ShaderProgram rainbowShader;
    private static float shaderTimer = 0f;
    private static final float SHADER_STRENGTH = 0.5f;
    private static final float SHADER_SPEED = 0.25f;
    private static final float SHADER_ANGLE = 0f;
    private static final float SHADER_WIDTH = 4f;

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
            rainbowShader.setUniformf("u_angle", SHADER_ANGLE + shaderTimer*4f);
            rainbowShader.setUniformf("u_width", SHADER_WIDTH);
        }
        shaderTimer += Gdx.graphics.getDeltaTime();
    }


    //end of Shader stuff

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

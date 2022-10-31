package rainbowMod;

import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.abstracts.CustomSavable;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.Level;
import rainbowMod.cards.AbstractEasyCard;
import rainbowMod.cards.cardvars.SecondDamage;
import rainbowMod.cards.cardvars.SecondMagicNumber;
import rainbowMod.relics.AbstractEasyRelic;
import rainbowMod.ui.CharacterTickbox;
import rainbowMod.ui.RainbowCharSelectPanel;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

@SuppressWarnings({"unused", "WeakerAccess"})
@SpireInitializer
public class RainbowMod implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        EditCharactersSubscriber,
        CustomSavable<ArrayList<AbstractCard.CardColor>>,
        PostInitializeSubscriber,
        PostCreateStartingDeckSubscriber,
        StartGameSubscriber,
        PostUpdateSubscriber {

    public static RainbowCharSelectPanel optionsPanel = null;
    public static CharacterTickbox characterTickboxes = null;
    public static ArrayList<AbstractCard> rainbowCards = new ArrayList<>();

    public static final String modID = "RainbowMod"; //TODO: Change this.

    public static String makeID(String idText) {
        return modID + ":" + idText;
    }

    public static Color characterColor = new Color(MathUtils.random(), MathUtils.random(), MathUtils.random(), 1); // This should be changed eventually

    public static final String SHOULDER1 = modID + "Resources/images/char/mainChar/shoulder.png";
    public static final String SHOULDER2 = modID + "Resources/images/char/mainChar/shoulder2.png";
    public static final String CORPSE = modID + "Resources/images/char/mainChar/corpse.png";
    private static final String ATTACK_S_ART = modID + "Resources/images/512/attack.png";
    private static final String SKILL_S_ART = modID + "Resources/images/512/skill.png";
    private static final String POWER_S_ART = modID + "Resources/images/512/power.png";
    private static final String CARD_ENERGY_S = modID + "Resources/images/512/energy.png";
    private static final String TEXT_ENERGY = modID + "Resources/images/512/text_energy.png";
    private static final String ATTACK_L_ART = modID + "Resources/images/1024/attack.png";
    private static final String SKILL_L_ART = modID + "Resources/images/1024/skill.png";
    private static final String POWER_L_ART = modID + "Resources/images/1024/power.png";
    private static final String CARD_ENERGY_L = modID + "Resources/images/1024/energy.png";
    private static final String CHARSELECT_BUTTON = modID + "Resources/images/charSelect/charButton.png";
    private static final String CHARSELECT_PORTRAIT = modID + "Resources/images/charSelect/charBG.png";

    public static ArrayList<AbstractCard.CardColor> selectedColors = new ArrayList<>();

    public static Settings.GameLanguage[] SupportedLanguages = {
            Settings.GameLanguage.ENG,
    };

    private String getLangString() {
        for (Settings.GameLanguage lang : SupportedLanguages) {
            if (lang.equals(Settings.language)) {
                return Settings.language.name().toLowerCase();
            }
        }
        return "eng";
    }

    public RainbowMod() {
        BaseMod.subscribe(this);

        BaseMod.addColor(TheRainbow.Enums.RAINBOW_CARD_COLOR, characterColor, characterColor, characterColor,
                characterColor, characterColor, characterColor, characterColor,
                ATTACK_S_ART, SKILL_S_ART, POWER_S_ART, CARD_ENERGY_S,
                ATTACK_L_ART, SKILL_L_ART, POWER_L_ART,
                CARD_ENERGY_L, TEXT_ENERGY);
    }

    public static String makePath(String resourcePath) {
        return modID + "Resources/" + resourcePath;
    }

    public static String makeImagePath(String resourcePath) {
        return modID + "Resources/images/" + resourcePath;
    }

    public static String makeRelicPath(String resourcePath) {
        return modID + "Resources/images/relics/" + resourcePath;
    }

    public static String makePowerPath(String resourcePath) {
        return modID + "Resources/images/powers/" + resourcePath;
    }

    public static String makeCardPath(String resourcePath) {
        return modID + "Resources/images/cards/" + resourcePath;
    }

    public static void initialize() {
        RainbowMod thismod = new RainbowMod();
    }

    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(new TheRainbow(TheRainbow.characterStrings.NAMES[1], TheRainbow.Enums.THE_RAINBOW),
                CHARSELECT_BUTTON, CHARSELECT_PORTRAIT, TheRainbow.Enums.THE_RAINBOW);
    }

    @Override
    public void receiveEditRelics() {
        new AutoAdd(modID)
                .packageFilter(AbstractEasyRelic.class)
                .any(AbstractEasyRelic.class, (info, relic) -> {
                    if (relic.color == null) {
                        BaseMod.addRelic(relic, RelicType.SHARED);
                    } else {
                        BaseMod.addRelicToCustomPool(relic, relic.color);
                    }
                    if (!info.seen) {
                        UnlockTracker.markRelicAsSeen(relic.relicId);
                    }
                });
    }

    @Override
    public void receiveEditCards() {
        BaseMod.addDynamicVariable(new SecondMagicNumber());
        BaseMod.addDynamicVariable(new SecondDamage());
        new AutoAdd(modID)
                .packageFilter(AbstractEasyCard.class)
                .setDefaultSeen(true)
                .cards();
    }


    @Override
    public void receiveEditStrings() {
        BaseMod.loadCustomStringsFile(CardStrings.class, modID + "Resources/localization/" + getLangString() + "/Cardstrings.json");

        BaseMod.loadCustomStringsFile(RelicStrings.class, modID + "Resources/localization/" + getLangString() + "/Relicstrings.json");

        BaseMod.loadCustomStringsFile(CharacterStrings.class, modID + "Resources/localization/" + getLangString() + "/Charstrings.json");

        BaseMod.loadCustomStringsFile(PowerStrings.class, modID + "Resources/localization/" + getLangString() + "/Powerstrings.json");

        BaseMod.loadCustomStringsFile(UIStrings.class, modID + "Resources/localization/" + getLangString() + "/UIStrings.json");
    }

    @Override
    public void receiveEditKeywords() {
        Gson gson = new Gson();
        String json = Gdx.files.internal(modID + "Resources/localization/eng/Keywordstrings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        com.evacipated.cardcrawl.mod.stslib.Keyword[] keywords = gson.fromJson(json, com.evacipated.cardcrawl.mod.stslib.Keyword[].class);

        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(modID, keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
            }
        }
    }

    @Override
    public ArrayList<AbstractCard.CardColor> onSave() {
        return selectedColors;
    }

    @Override
    public void onLoad(ArrayList<AbstractCard.CardColor> colors) {
        BaseMod.logger.log(Level.INFO, "Loading the save's selected colors: " + colors.toString());
        if (AbstractDungeon.player.chosenClass == TheRainbow.Enums.THE_RAINBOW) {
            BaseMod.logger.info("Trying to fill the pools");
            selectedColors = colors;
            optionsPanel.makePools(selectedColors);
            BaseMod.logger.info("Filled the pools");
        }
    }





    @Override
    public void receivePostInitialize() {
        optionsPanel = new RainbowCharSelectPanel();
        characterTickboxes = new CharacterTickbox();

        BaseMod.addSaveField("RainbowSelectedColors", this);

        new AutoAdd(modID).packageFilter("rainbowMod.cards").any(AbstractEasyCard.class, (info, c) -> {
            if (c.color == TheRainbow.Enums.RAINBOW_CARD_COLOR) {
                rainbowCards.add(c);
            }
        });
    }


    //Fonts
    public static BitmapFont localCharDescFont;
    public static BitmapFont localEnergyFont;

    public static BitmapFont getPanelFont() {
        if (localCharDescFont == null) {
            localCharDescFont = FontHelper.charDescFont;
        }
        return localCharDescFont;
    }

    public static BitmapFont getEnergyFont() {
        if (localEnergyFont == null) {
            localEnergyFont = FontHelper.cardEnergyFont_L;
        }
        return localEnergyFont;
    }

    @Override
    public void receivePostCreateStartingDeck(AbstractPlayer.PlayerClass playerClass, CardGroup cardGroup) {
        if (playerClass == TheRainbow.Enums.THE_RAINBOW) {
            optionsPanel.onNewGame(cardGroup);
            optionsPanel.triggerPerks();
            ((TheRainbow)(AbstractDungeon.player)).setupAnimation();
        }
    }

    @Override
    public void receiveStartGame() {
        if (AbstractDungeon.player.chosenClass == TheRainbow.Enums.THE_RAINBOW) {
            BaseMod.logger.info("Trying to fill the pools again");
            optionsPanel.makePools(selectedColors);
        }
    }

    @Override
    public void receivePostUpdate() {
        //TheRainbow.updateShader();
    }
}

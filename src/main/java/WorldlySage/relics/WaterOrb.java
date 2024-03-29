package WorldlySage.relics;

import WorldlySage.TheWorldlySage;
import WorldlySage.actions.ApplyGrowthAction;
import WorldlySage.actions.PlantCardsAction;
import WorldlySage.cards.Sapling;
import WorldlySage.patches.CustomTags;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import static WorldlySage.MainModfile.makeID;

public class WaterOrb extends AbstractEasyRelic {
    public static final String ID = makeID(WaterOrb.class.getSimpleName());
    HashMap<String, Integer> stats = new HashMap<>();
    private final String SAVED_STAT = DESCRIPTIONS[1];
    private final String PER_TURN = DESCRIPTIONS[2];
    private final String PER_COMBAT = DESCRIPTIONS[3];

    public WaterOrb() {
        super(ID, RelicTier.STARTER, LandingSound.MAGICAL, TheWorldlySage.Enums.SAGELY_MALIBU_COLOR);
        resetStats();
    }

    @Override
    public void atBattleStart() {
        flash();
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (int i = 0 ; i < 1 ; i++) {
            AbstractCard card = new Sapling();
            card.tags.add(CustomTags.SAGE_RELIC_TRACKER);
            ApplyGrowthAction.applyGrowth(card, 2);
            group.addToTop(card);
        }
        addToBot(new PlantCardsAction(group, group.size()));
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        if (c.hasTag(CustomTags.SAGE_RELIC_TRACKER) && c.block > 0) {
            incrementStat(c.block);
        }
    }

    public int getStat() {
        return stats.get(SAVED_STAT);
    }

    public void incrementStat(int amount) {
        stats.put(SAVED_STAT, stats.get(SAVED_STAT) + amount);
    }

    public String getStatsDescription() {
        return SAVED_STAT + stats.get(SAVED_STAT);
    }

    public String getExtendedStatsDescription(int totalCombats, int totalTurns) {
        // You would just return getStatsDescription() if you don't want to display per-combat and per-turn stats
        StringBuilder builder = new StringBuilder();
        builder.append(getStatsDescription());

        // Relic Stats truncates these extended stats to 3 decimal places, so we do the same
        DecimalFormat perTurnFormat = new DecimalFormat("#.###");

        float stat = (float)stats.get(SAVED_STAT);
        builder.append(PER_TURN);
        builder.append(perTurnFormat.format(stat / Math.max(totalTurns, 1)));
        builder.append(PER_COMBAT);
        builder.append(perTurnFormat.format(stat / Math.max(totalCombats, 1)));

        return builder.toString();
    }

    public void resetStats() {
        stats.put(SAVED_STAT, 0);
    }

    public JsonElement onSaveStats() {
        // An array makes more sense if you want to store more than one stat
        Gson gson = new Gson();
        ArrayList<Integer> statsToSave = new ArrayList<>();
        statsToSave.add(stats.get(SAVED_STAT));
        return gson.toJsonTree(statsToSave);
    }

    public void onLoadStats(JsonElement jsonElement) {
        if (jsonElement != null) {
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            stats.put(SAVED_STAT, jsonArray.get(0).getAsInt());
        } else {
            resetStats();
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        // Relic Stats will always query the stats from the instance passed to BaseMod.addRelic()
        // Therefore, we make sure all copies share the same stats by copying the HashMap.
        WaterOrb newRelic = new WaterOrb();
        newRelic.stats = this.stats;
        return newRelic;
    }
}

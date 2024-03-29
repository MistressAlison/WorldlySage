package WorldlySage.powers;

import WorldlySage.MainModfile;
import WorldlySage.util.Wiz;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import com.megacrit.cardcrawl.powers.EnergizedBluePower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class ManaStormPower extends AbstractPower {
    public static final String POWER_ID = MainModfile.makeID(ManaStormPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public ManaStormPower(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.loadRegion("storm");
        updateDescription();
    }

    @Override
    public void updateDescription() {
        if (amount == 1) {
            this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
        } else {
            this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + amount + DESCRIPTIONS[3];
        }
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer && EnergyPanel.getCurrentEnergy() > 0) {
            flash();
            Wiz.applyToSelf(new EnergizedBluePower(Wiz.adp(), amount));
            Wiz.applyToSelf(new DrawCardNextTurnPower(Wiz.adp(), amount));
        }
    }
}
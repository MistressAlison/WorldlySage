package WorldlySage.cards;

import WorldlySage.cards.abstracts.AbstractEasyCard;
import WorldlySage.cards.interfaces.GrowthModifierCard;
import com.megacrit.cardcrawl.cards.green.Defend_Green;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static WorldlySage.MainModfile.makeID;

public class ToweringOak extends AbstractEasyCard implements GrowthModifierCard {
    public final static String ID = makeID(ToweringOak.class.getSimpleName());

    public ToweringOak() {
        super(ID, 2, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = block = 12;
        baseMagicNumber = magicNumber = 3;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
    }

    @Override
    public void upp() {
        upgradeBlock(3);
        upgradeMagicNumber(2);
    }

    @Override
    public String cardArtCopy() {
        return Defend_Green.ID;
    }

    @Override
    public int boostPerGrowth() {
        return magicNumber;
    }
}
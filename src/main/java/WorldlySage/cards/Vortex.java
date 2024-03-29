package WorldlySage.cards;

import WorldlySage.actions.DoAction;
import WorldlySage.cardmods.PhantomMod;
import WorldlySage.cards.abstracts.AbstractEasyCard;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.Defend_Green;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static WorldlySage.MainModfile.makeID;

public class Vortex extends AbstractEasyCard {
    public final static String ID = makeID(Vortex.class.getSimpleName());

    public Vortex() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        baseMagicNumber = magicNumber = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DoAction(() -> {
            for (int i = magicNumber-1 ; i >= 0 ; i--) {
                if (p.drawPile.size() > i) {
                    AbstractCard card = p.drawPile.getNCardFromTop(i).makeStatEquivalentCopy();
                    CardModifierManager.addModifier(card, new PhantomMod());
                    addToTop(new MakeTempCardInHandAction(card));
                }
            }
        }));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
    }

    @Override
    public String cardArtCopy() {
        return Defend_Green.ID;
    }
}
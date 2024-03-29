package WorldlySage.cards;

import WorldlySage.actions.DoAction;
import WorldlySage.actions.ThrowObjectAction;
import WorldlySage.cards.abstracts.AbstractEasyCard;
import WorldlySage.util.TextureScaler;
import WorldlySage.util.Wiz;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.purple.Wish;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static WorldlySage.MainModfile.makeID;

public class AuraSphere extends AbstractEasyCard {
    public final static String ID = makeID(AuraSphere.class.getSimpleName());
    private static final Texture EFFECT = TextureScaler.rescale(ImageMaster.vfxAtlas.findRegion("env/dustCloud"), 64, 64);

    public AuraSphere() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = damage = 10;
        baseMagicNumber = magicNumber = 15;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null) {
            Wiz.atb(new ThrowObjectAction(EFFECT, 1.0f, m.hb, Color.WHITE, false));
        }
        dmg(m, AbstractGameAction.AttackEffect.FIRE);
        addToBot(new DoAction(() -> baseDamage = magicNumber));
    }

    @Override
    public void upp() {
        upgradeDamage(4);
        upgradeMagicNumber(6);
    }

    @Override
    public String cardArtCopy() {
        return Wish.ID;
    }
}
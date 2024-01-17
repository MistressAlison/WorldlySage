package WorldlySage.cardmods;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import basemod.helpers.TooltipInfo;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.mod.stslib.util.extraicons.ExtraIcons;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractGlyph extends AbstractCardModifier {
    public String glyphID;
    public ArrayList<TooltipInfo> glyphTips;
    public int amount;
    public transient Texture modIcon;

    public AbstractGlyph(String glyphID, int amount, Texture modIcon, ArrayList<TooltipInfo> glyphTips) {
        this.glyphID = glyphID;
        this.amount = amount;
        this.modIcon = modIcon;
        this.glyphTips = glyphTips;
    }

    public abstract void extraEffect(AbstractCard card, AbstractCreature target, UseCardAction action);

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        amount--;
        extraEffect(card, target, action);
    }

    @Override
    public boolean removeOnCardPlayed(AbstractCard card) {
        return amount == 0;
    }

    @Override
    public List<TooltipInfo> additionalTooltips(AbstractCard card) {
        return glyphTips;
    }

    @Override
    public void onRender(AbstractCard card, SpriteBatch sb) {
        if (SingleCardViewPopup.isViewingUpgrade && !card.upgraded) {
            return;
        }
        if (amount > 1) {
            ExtraIcons.icon(modIcon).text(String.valueOf(amount)).textOffsetX(3).drawColor(new Color(1, 1, 1, card.transparency)).render(card);
        } else {
            ExtraIcons.icon(modIcon).drawColor(new Color(1, 1, 1, card.transparency)).render(card);
        }
    }

    @Override
    public void onSingleCardViewRender(AbstractCard card, SpriteBatch sb) {
        if (amount > 1) {
            ExtraIcons.icon(modIcon).text(String.valueOf(amount)).textOffsetX(6).drawColor(new Color(1, 1, 1, card.transparency)).render(card);
        } else {
            ExtraIcons.icon(modIcon).drawColor(new Color(1, 1, 1, card.transparency)).render(card);
        }
    }

    @Override
    public boolean shouldApply(AbstractCard card) {
        if (CardModifierManager.hasModifier(card, glyphID)) {
            AbstractGlyph glyph = (AbstractGlyph) CardModifierManager.getModifiers(card, glyphID).get(0);
            glyph.amount += this.amount;
            return false;
        }
        return true;
    }
}
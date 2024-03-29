package WorldlySage.actions;

import WorldlySage.cardmods.AbstractGlyph;
import WorldlySage.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.function.Predicate;

public class ApplyGlyphToAllCardsAction extends AbstractGameAction {
    private final AbstractGlyph glyph;
    private final Predicate<AbstractCard> filter;

    public ApplyGlyphToAllCardsAction(AbstractGlyph glyph) {
        this(glyph, c -> true);
    }

    public ApplyGlyphToAllCardsAction(AbstractGlyph glyph, Predicate<AbstractCard> filter) {
        this.glyph = glyph;
        this.filter = filter;
    }

    @Override
    public void update() {
        this.isDone = true;
        for (AbstractCard c : Wiz.getAllCardsInCardGroups(true, true, true)) {
            if (filter.test(c)) {
                ApplyGlyphAction.applyGlyph(c, (AbstractGlyph) glyph.makeCopy());
            }
        }
    }
}

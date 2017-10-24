package com.tokenautocomplete;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

import com.tokenautocomplete.model.Chip;
import com.tokenautocomplete.model.ChipInterface;
import com.tokenautocomplete.util.ViewUtil;

/**
 * Sample token completion view for basic contact info
 * <p>
 * Created on 9/12/13.
 *
 * @author mgod
 */
public class ContactsCompletionView extends TokenCompleteTextView<ChipInterface> {

    public ContactsCompletionView(Context context) {
        super(context);
    }

    public ContactsCompletionView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ContactsCompletionView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected ChipView getViewForObject(final ChipInterface person) {
        ChipView chipView1 = new ChipView(getContext());
        chipView1.setLabel(person.getEmailAddress(), 0, ViewUtil.dpToPx(18));
        chipView1.setPadding(2, 2, 2, 2);
        chipView1.setHasAvatarIcon(true);
        chipView1.setChipBorderColor(4, Color.BLUE);
        return chipView1;
    }

    @Override
    protected ChipInterface defaultObject(String completionText) {
        //Stupid simple example of guessing if we have an email or not
        return new Chip(getContext(), "Some label", "Some info", "Some name");
    }
}

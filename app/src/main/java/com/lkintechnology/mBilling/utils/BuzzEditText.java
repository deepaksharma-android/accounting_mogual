package com.lkintechnology.mBilling.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.Html;
import android.util.AttributeSet;
import android.widget.EditText;

import com.lkintechnology.mBilling.R;


public class BuzzEditText extends EditText {
    private int typefaceCode;
    public BuzzEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (attrs != null) {
            // Get Custom Attribute Name and value
            TypedArray styledAttrs = context.obtainStyledAttributes(attrs,
                    R.styleable.BuzzFonts);
            typefaceCode = styledAttrs.getInt(R.styleable.BuzzFonts_fontType, -1);
            Typeface typeface = TypefaceCache.get(context.getAssets(), typefaceCode);
            setTypeface(typeface);

            styledAttrs.recycle();
            if (isInEditMode()) {
                return;
            }
        }
    }

    public BuzzEditText(Context context) {
        super(context);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        if (text != null) {
            text = Html.fromHtml(text.toString());
        }
        super.setText(text, type);
    }

}

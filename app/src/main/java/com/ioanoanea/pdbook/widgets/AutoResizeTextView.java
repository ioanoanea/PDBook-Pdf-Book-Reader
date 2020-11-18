package com.ioanoanea.pdbook.widgets;

import android.content.Context;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;


public class AutoResizeTextView extends androidx.appcompat.widget.AppCompatTextView {


    public AutoResizeTextView(Context context) {
        super(context);
    }

    public AutoResizeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoResizeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int widthLimit = (right - left) - getCompoundPaddingLeft() - getCompoundPaddingRight();
        int heightLimit = (bottom - top) - getCompoundPaddingBottom() - getCompoundPaddingTop();
        //autoResizeText(getText(),widthLimit, heightLimit);
        autoResizeText(getText(), widthLimit, heightLimit);
    }

    /** adjust text size until text correctly fits layout size
     *  depending on layout width
     *  depending on layout height
     *  depending on long words width */
    private void autoResizeText(final CharSequence text, int width, int height){
        // set default text size to 90sp
        setTextSize(90);
        float minSize = 1f;
        float maxSize = 300f;

        // define new paint
        TextPaint paint = getPaint();

        // set paint typeface to textView typeface
        paint.setTypeface(getTypeface());

        // set font size to the middle of minSize and maxSize
        float textSize = (minSize + maxSize) / 2;

        // split text in words
        String[] words = text.toString().split(" ");

        // determine the longest word
        int maxLength = 0;
        String word = "";
        for (String s : words) {
            if (s.length() > maxLength) {
                maxLength = s.length();
                word = s;
            }
        }

        // adjust text size until fits width, height, long word fits width
        while(getTextWidth(text.toString(), paint) != width * 3 && getTextWidth(word, paint) != width && getTextHeight(text.toString(), width, paint, textSize) != height && minSize < maxSize){
            // if text paint width is bigger than textView width * 3 (text don't have to be bigger than ~ 3 lines), set maxSize to current text size + 0.1
            if(getTextWidth(text.toString(), paint) > width * 3)
                maxSize = textSize - 0.1f;
            // if word text paint is bigger than textView width, set maxSize to current text size - 0.1
            else if(getTextWidth(word, paint) > width)
                maxSize = textSize - 0.1f;
            // if text paint height id bigger than textView height, set maxSize to current text size - 0.1
            else if(getTextHeight(text.toString(), width, paint, textSize) > height)
                maxSize = textSize - 0.1f;
            // if text paint size is to small, set minSize to current size + 0.0001 (increase minSize with very small value to avoid wrong text fill)
            else minSize = textSize + 0.0001f;

            // set font size to the middle of minSize and maxSize
            textSize = (minSize + maxSize) / 2;

            // set text paint with new text size
            paint.setTextSize(textSize);
        }

        // set text size
        setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);

    }


    /** determine text width  */
    private float getTextWidth(String text, TextPaint textPaint){
        // get new text paint
        TextPaint paint = new TextPaint(textPaint);

        return paint.measureText(text);
    }

    /** determine text height */
    private int getTextHeight(CharSequence text, int width, TextPaint textPaint, float textSize){
        // get new text paint
        TextPaint paint = new TextPaint(textPaint);
        // set paint text font size
        paint.setTextSize(textSize);

        return new StaticLayout(text, paint, width, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, true).getHeight();
    }


}

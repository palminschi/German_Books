package com.palmdev.german_books.utils;

import android.text.Layout;
import android.text.StaticLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Pagination {

    private final TextView mTextView;
    private final CharSequence mText;
    private final List<CharSequence> mPages;

    public Pagination(CharSequence text, TextView mTextView) {
        this.mTextView = mTextView;
        this.mText = text;
        this.mPages = new ArrayList<CharSequence>();
        divide();
    }

    private void divide() {
        final StaticLayout layout = new StaticLayout(
                mText,
                mTextView.getPaint(),
                mTextView.getWidth(),
                Layout.Alignment.ALIGN_NORMAL,
                mTextView.getLineSpacingMultiplier(),
                mTextView.getLineSpacingExtra(),
                mTextView.getIncludeFontPadding()
        );

        final int lines = layout.getLineCount();
        final CharSequence text = layout.getText();
        int startOffset = 0;
        int height = mTextView.getHeight();

        for (int i = 0; i < lines; i++) {
            if (height < layout.getLineBottom(i)) {
                // When the layout height has been exceeded
                addPage(text.subSequence(startOffset, layout.getLineStart(i)));
                startOffset = layout.getLineStart(i);
                height = layout.getLineTop(i) + mTextView.getHeight();
            }

            if (i == lines - 1) {
                // Put the rest of the text into the last page
                addPage(text.subSequence(startOffset, layout.getLineEnd(i)));
                return;
            }
        }
    }

    private void addPage(CharSequence text) {
        mPages.add(text);
    }

    public int getTotalPages() {
        return mPages.size();
    }

    public CharSequence getPageText(int index) {
        return (index >= 0 && index < mPages.size()) ? mPages.get(index) : null;
    }
}

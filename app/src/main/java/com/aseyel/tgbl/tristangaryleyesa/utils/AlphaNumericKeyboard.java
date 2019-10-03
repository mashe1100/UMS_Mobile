package com.aseyel.tgbl.tristangaryleyesa.utils;

import android.content.Context;
import android.os.SystemClock;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;
import android.widget.EditText;
import android.widget.TextView;

import com.aseyel.tgbl.tristangaryleyesa.R;

public class AlphaNumericKeyboard extends android.support.v7.widget.AppCompatEditText {

    private final int mDefinedActionId;
    private long mLastEditorActionTime = 0L;

    public AlphaNumericKeyboard(Context context, AttributeSet attrs) {
        super(context, attrs);

        // Corresponds to 'android:imeActionId' value
        mDefinedActionId = getResources().getInteger(R.integer.definedActionId);

        setOnEditorActionListener(new OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                Log.i("CusEditText", "onEditorAction, actionId = " + actionId);

                // Only bother if (...)
                if (actionId == mDefinedActionId) {

                    // setInputType(...) will restart the IME
                    // and call finishComposingText()
                    // see below
                    mLastEditorActionTime = SystemClock.elapsedRealtime();

                    // Check if current InputType is NUMBER
                    if ((getInputType() & InputType.TYPE_CLASS_NUMBER) != 0) {
                        // Toggle
                        setImeActionLabel("NUM", mDefinedActionId);
                        setInputType(InputType.TYPE_CLASS_TEXT);
                    } else {
                        // Current InputType is TEXT // Toggle
                        setImeActionLabel("ABC", mDefinedActionId);
                        setInputType(InputType.TYPE_CLASS_NUMBER);
                    }

                    // We've handled this
                    return true;
                }

                // Let someone else worry about this
                return false;
            }
        });
    }

    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        InputConnection inputConnection = super.onCreateInputConnection(outAttrs);

        return new CusInputConnectionWrapper(inputConnection, false);
    }

    private class CusInputConnectionWrapper extends InputConnectionWrapper {
        private CusInputConnectionWrapper(InputConnection target, boolean mutable) {
            super(target, mutable);
        }

        @Override
        public boolean finishComposingText() {
            Log.i("CICW", "finishComposingText");

            // Ignore finishComposingText for 1 second (1000L)
            if (SystemClock.elapsedRealtime() - mLastEditorActionTime > 1000L) {
                if ((getInputType() & InputType.TYPE_CLASS_NUMBER) == 0) {
                    // InputConnection is no longer valid.
                    // Switch back to NUMBER iff required
                    setImeActionLabel("ABC", mDefinedActionId);
                    setInputType(InputType.TYPE_CLASS_NUMBER);
                }
            }

            return super.finishComposingText();
        }
    }

}
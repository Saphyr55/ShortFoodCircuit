package fr.sfc.framework.controlling;

import javafx.beans.binding.IntegerBinding;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.IndexRange;
import javafx.scene.control.TextField;

import java.util.regex.Pattern;

public class TimeTextField extends TextField {

    enum Unit {HOURS, MINUTES, SECONDS};

    private final Pattern timePattern ;
    private final ReadOnlyIntegerWrapper hours ;
    private final ReadOnlyIntegerWrapper minutes ;
    private final ReadOnlyIntegerWrapper seconds ;

    public TimeTextField() {
        this("00:00:00");
    }
    public TimeTextField(String time) {
        super(time);
        timePattern = Pattern.compile("\\d\\d:\\d\\d:\\d\\d");
        if (! validate(time)) {
            throw new IllegalArgumentException("Invalid time: "+time);
        }
        hours = new ReadOnlyIntegerWrapper(this, "hours");
        minutes = new ReadOnlyIntegerWrapper(this, "minutes");
        seconds = new ReadOnlyIntegerWrapper(this, "seconds");
        hours.bind(new TimeTextField.TimeUnitBinding(Unit.HOURS));
        minutes.bind(new TimeTextField.TimeUnitBinding(Unit.MINUTES));
        seconds.bind(new TimeTextField.TimeUnitBinding(Unit.SECONDS));
    }

    public ReadOnlyIntegerProperty hoursProperty() {
        return hours.getReadOnlyProperty();
    }

    public int getHours() {
        return hours.get() ;
    }

    public ReadOnlyIntegerProperty minutesProperty() {
        return minutes.getReadOnlyProperty();
    }

    public int getMinutes() {
        return minutes.get();
    }

    public ReadOnlyIntegerProperty secondsProperty() {
        return seconds.getReadOnlyProperty();
    }

    public int getSeconds() {
        return seconds.get();
    }

    @Override
    public void appendText(String text) {
        // Ignore this. Our text is always 8 characters long, we cannot append anything
    }

    @Override
    public boolean deleteNextChar() {

        boolean success = false ;

        // If there's a selection, delete it:
        final IndexRange selection = getSelection();
        if (selection.getLength()>0) {
            int selectionEnd = selection.getEnd();
            this.deleteText(selection);
            this.positionCaret(selectionEnd);
            success = true ;
        } else {
            // If the caret preceeds a digit, replace that digit with a zero and move the caret forward. Else just move the caret forward.
            int caret = this.getCaretPosition();
            if (caret % 3 != 2) { // not preceeding a colon
                String currentText = this.getText();
                setText(currentText.substring(0, caret) + "0" + currentText.substring(caret+1));
                success = true ;
            }
            this.positionCaret(Math.min(caret+1, this.getText().length()));
        }
        return success ;
    }

    @Override
    public boolean deletePreviousChar() {

        boolean success = false ;

        // If there's a selection, delete it:
        final IndexRange selection = getSelection();
        if (selection.getLength()>0) {
            int selectionStart = selection.getStart();
            this.deleteText(selection);
            this.positionCaret(selectionStart);
            success = true ;
        } else {
            // If the caret is after a digit, replace that digit with a zero and move the caret backward. Else just move the caret back.
            int caret = this.getCaretPosition();
            if (caret % 3 != 0) { // not following a colon
                String currentText = this.getText();
                setText(currentText.substring(0, caret-1) + "0" + currentText.substring(caret));
                success = true ;
            }
            this.positionCaret(Math.max(caret-1, 0));
        }
        return success ;
    }

    @Override
    public void deleteText(IndexRange range) {
        this.deleteText(range.getStart(), range.getEnd());
    }

    @Override
    public void deleteText(int begin, int end) {
        // Replace all digits in the given range with zero:
        StringBuilder builder = new StringBuilder(this.getText());
        for (int c = begin; c<end; c++) {
            if (c % 3 != 2) { // Not at a colon:
                builder.replace(c, c+1, "0");
            }
        }
        this.setText(builder.toString());
    }

    @Override
    public void insertText(int index, String text) {
        // Handle an insert by replacing the range from index to index+text.length() with text, if that results in a valid string:
        StringBuilder builder = new StringBuilder(this.getText());
        builder.replace(index, index+text.length(), text);
        final String testText = builder.toString();
        if (validate(testText)) {
            this.setText(testText);
        }
        this.positionCaret(index + text.length());
    }


    @Override
    public void replaceSelection(String replacement) {
        final IndexRange selection = this.getSelection();
        if (selection.getLength()==0) {
            this.insertText(selection.getStart(), replacement);
        } else {
            this.replaceText(selection.getStart(), selection.getEnd(), replacement);
        }
    }

    @Override
    public void replaceText(IndexRange range, String text) {
        this.replaceText(range.getStart(), range.getEnd(), text);
    }

    @Override
    public void replaceText(int begin, int end, String text) {
        if (begin==end) {
            this.insertText(begin, text);
        } else {
            // only handle this if text.length() is equal to the number of characters being replaced, and if the replacement results in a valid string:
            if (text.length() == end - begin) {
                StringBuilder builder = new StringBuilder(this.getText());
                builder.replace(begin, end, text);
                String testText = builder.toString();
                if (validate(testText)) {
                    this.setText(testText);
                }
                this.positionCaret(end);
            }
        }
    }

    private boolean validate(String time) {
        if (! timePattern.matcher(time).matches()) {
            return false ;
        }
        String[] tokens = time.split(":");
        assert tokens.length == 3 ;
        try {
            int hours = Integer.parseInt(tokens[0]);
            int mins = Integer.parseInt(tokens[1]);
            int secs = Integer.parseInt(tokens[2]);
            if (hours < 0 || hours > 23) {
                return false ;
            }
            if (mins < 0 || mins > 59) {
                return false ;
            }
            if (secs < 0 || secs > 59) {
                return false ;
            }
            return true ;
        } catch (NumberFormatException nfe) {
            // regex matching should assure we never reach this catch block
            assert false ;
            return false ;
        }
    }

    private final class TimeUnitBinding extends IntegerBinding implements ObservableValue<Number> {

        final Unit unit ;
        TimeUnitBinding(Unit unit) {
            this.bind(textProperty());
            this.unit = unit ;
        }
        @Override
        protected int computeValue() {
            // Crazy enum magic
            String token = getText().split(":")[unit.ordinal()];
            return Integer.parseInt(token);
        }

    }

}

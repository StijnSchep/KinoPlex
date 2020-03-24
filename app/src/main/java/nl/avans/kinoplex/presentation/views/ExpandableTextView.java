package nl.avans.kinoplex.presentation.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.util.AttributeSet;

import nl.avans.kinoplex.R;

/** The type Expandable text view. */
public class ExpandableTextView extends android.support.v7.widget.AppCompatTextView {
    private static final int DEFAULT_TRIM_LENGTH = 200;
    private static final String ELLIPSIS = "... <b style=\"color:blue\">Read more</b>";

    private CharSequence originalText;
    private CharSequence trimmedText;
    private BufferType bufferType;
    private boolean trim = true;
    private int trimLength;

  /**
   * @author Guus Lieben
   * Instantiates a new Expandable text view.
   *
   * @param context the context
   */
  public ExpandableTextView(Context context) {
        this(context, null);
    }

  /**
   * @author Guus Lieben
   * Instantiates a new Expandable text view.
   *
   * @param context the context
   * @param attrs the attrs
   */
  public ExpandableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ExpandableTextView);
        this.trimLength = typedArray.getInt(R.styleable.ExpandableTextView_trimLength, DEFAULT_TRIM_LENGTH);
        typedArray.recycle();

        setOnClickListener(v -> {
            trim = !trim;
            setText();
            requestFocusFromTouch();
        });
    }

    /**
     * @author Guus Lieben
     * Sets the text of the view, passes through getDisplayableText()
     */
    private void setText() {
        super.setText(getDisplayableText(), bufferType);
    }

    /**
     * @author Guus Lieben
     * @return the text of the view based on the trim state
     */
    private CharSequence getDisplayableText() {
        return trim ? trimmedText : originalText;
    }

    /**
     * @author Guus Lieben
     * Sets the text of the view based on the trim state
     *
     * @param text the text to set
     * @param type the buffer type
     */
    @Override
    public void setText(CharSequence text, BufferType type) {
        originalText = text;
        trimmedText = getTrimmedText(text);
        bufferType = type;
        setText();
    }

    /**
     * @author Guus Lieben
     *
     * Generates a trimmed variant of the text based on set preferences
     *
     * @param text the text to trim
     * @return the trimmed text
     */
    private Spanned getTrimmedText(CharSequence text) {
        if (originalText != null && originalText.length() > trimLength) {
            return Html.fromHtml(new SpannableStringBuilder(originalText, 0, trimLength + 1).append(ELLIPSIS).toString(), Html.FROM_HTML_MODE_COMPACT);
        } else {
            return Html.fromHtml(String.valueOf(originalText), Html.FROM_HTML_MODE_COMPACT);
        }
    }

  /**
   * @author Guus Lieben
   * Gets original text.
   *
   * @return the original text
   */
  public CharSequence getOriginalText() {
        return originalText;
    }

  /**
   * @author Guus Lieben
   * Sets trim length.
   *
   * @param trimLength the trim length
   */
  public void setTrimLength(int trimLength) {
        this.trimLength = trimLength;
        trimmedText = getTrimmedText(originalText);
        setText();
    }

  /**
   * @author Guus Lieben
   * Gets trim length.
   *
   * @return the trim length
   */
  public int getTrimLength() {
        return trimLength;
    }
}

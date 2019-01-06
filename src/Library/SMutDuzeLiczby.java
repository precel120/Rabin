package Library;//package sample.Library;


class SMutDuzeLiczby extends MutDuzeLiczby {

   /**
     * The sign of this MutDuzeLiczby.
     */
    int sign = 1;

    // Constructors

    /**
     * The default constructor. An empty MutDuzeLiczby is created with
     * a one word capacity.
     */
    SMutDuzeLiczby() {
        super();
    }

    /**
     * Construct a new MutDuzeLiczby with a magnitude specified by
     * the int val.
     */
    SMutDuzeLiczby(int val) {
        super(val);
    }

    /**
     * Construct a new MutDuzeLiczby with a magnitude equal to the
     * specified MutDuzeLiczby.
     */
    SMutDuzeLiczby(MutDuzeLiczby val) {
        super(val);
    }

   // Arithmetic Operations

   /**
     * Signed addition built upon unsigned add and subtract.
     */
    void signedAdd(SMutDuzeLiczby addend) {
        if (sign == addend.sign)
            add(addend);
        else
            sign = sign * subtract(addend);

    }

   /**
     * Signed addition built upon unsigned add and subtract.
     */
    void signedAdd(MutDuzeLiczby addend) {
        if (sign == 1)
            add(addend);
        else
            sign = sign * subtract(addend);

    }

   /**
     * Signed subtraction built upon unsigned add and subtract.
     */
    void signedSubtract(SMutDuzeLiczby addend) {
        if (sign == addend.sign)
            sign = sign * subtract(addend);
        else
            add(addend);

    }

   /**
     * Signed subtraction built upon unsigned add and subtract.
     */
    void signedSubtract(MutDuzeLiczby addend) {
        if (sign == 1)
            sign = sign * subtract(addend);
        else
            add(addend);
        if (intLen == 0)
             sign = 1;
    }

    /**
     * Print out the first intLen ints of this MutDuzeLiczby's value
     * array starting at offset.
     */
    public String toString() {
        return this.toDuzeLiczby(sign).toString();
    }

}

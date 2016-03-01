package com.tsoft.education.csv.column;

public class StringValueGenerator extends Generator {
    private static final String LEGAL_CHARS="1234567890qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";
    
    private int minLength = 16;
    private int maxLength = 32;

    @Override
    public Object next() {
        StringBuilder buf = new StringBuilder(maxLength);

        int length = getRandom(maxLength - minLength) + minLength;
        for (int i = 0; i < length; i ++) {
            int index = getRandom(LEGAL_CHARS.length());
            buf.append(LEGAL_CHARS.charAt(index));
        }
        
        return buf.toString();
    }

    public void setMinLength(int minLength) {
        this.minLength = minLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }
}

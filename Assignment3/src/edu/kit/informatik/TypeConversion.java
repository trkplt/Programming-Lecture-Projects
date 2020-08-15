package edu.kit.informatik;

/**
 * Demonstrative class for conversion types.
 *
 * @author Tarik Polat
 * @version 1.0.0
 */
public final class TypeConversion {

    //Object of this class shall not be created, this class only stands for demonstrative purposes.
    private TypeConversion() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    /**
     * Main method for demonstrative type conversions.
     *
     * @param args arguments for the app, none is needed
     */
    public static void main(String[] args) {

        int i = 42;

        //Narrowing/Casting Conversion for (0xFF & (i << 24))
        byte b = (byte) (0xFF & (i << 24));

        //Widening Conversion
        int j = 'a' + 42;

        //String Conversion
        System.out.println("Some letters are ... " + 'b' + 'c');

        short s = 42;

        //Casting Conversion
        char c = (char) s;

        long l = 99999999999999L;

        //Widening Conversion
        double d = 19.0f;

        //Widening Conversion
        d += l;

        //Widening Conversion for i, s and (j + s) and Narrowing/Casting Conversion for (i * 2.0d) + (j + s)
        long l2 = (long) (i * 2.0d) + (j + s);

        //Widening Conversion for 0xFF and l2 and Narrowing/Casting Conversion for d
        float[] array = {0xFF, l2, (float) d};

        //String Conversion for array and Casting Conversion for array.toString(), Casting Conversion can be deleted
        String o = (String) array.toString();

        //String Conversion
        System.out.println(array[0]);
    }
}

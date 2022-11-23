package am.arkadysahakyan.encoder;

public class Base64 {
    private static char[] table = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'};

    /**
     * The basic logic is here. Encodes the given data unit (24 bits) into a Base64 data (32 bits).
     * @param unit the data unit. Assumes that its length is 3.
     * @param size the size of the data in the unit (can be 1, 2, 3).
     * @return encoded unit (32 bits).
     */
    private static char[] encodeUnit(char[] unit, int size) {
        char[] encodedUnit = new char[4];
        encodedUnit[0] = table[ unit[0] >> 2 ];
        encodedUnit[1] = table[ unit[0] << 4 & 0x30 | unit[1] >> 4 ];
        encodedUnit[2] = (size >= 2) ? table[ unit[1] << 2 & 0x3C | unit[2] >> 6 ] : '=' ;
        encodedUnit[3] = (size == 3) ? table[ unit[2] & 0x3F ] : '=';
        return encodedUnit;
    }

    /**
     * Encodes data in Base64.
     * @param string the data to be encoded
     * @return encoded data.
     */
    public static String encodeString(String string) {
        StringBuilder builder = new StringBuilder();
        int i;
        for (i = 0; i + 3 < string.length(); i += 3)
            builder.append(encodeUnit(string.substring(i, i + 3).toCharArray(), 3));
        char[] tail = new char[3];
        int next = 0;
        for (char c : string.substring(i, string.length()).toCharArray())
            tail[next++] = c;
        builder.append( encodeUnit(tail, string.length() - i) );
        return builder.toString();
    }

    public static void main(String[] args) {
        System.out.println(Base64.encodeString("Hello! I'm Arkadi. I'm 16. And I love programming."));
    }
}
package com.innova.ticketsapp.utils;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Formatters {
    private static DecimalFormatSymbols dfs;

    private static DecimalFormat doubleDF = new DecimalFormat("###,###,##0.00");

    static {
        dfs = new DecimalFormatSymbols();
    }

    public static String TwoDecimalsFormat(double paramDouble) {
        dfs.setDecimalSeparator('.');
        dfs.setGroupingSeparator(',');
        doubleDF.setDecimalFormatSymbols(dfs);
        return doubleDF.format(paramDouble);
    }

    public static String TwoDecimalsFormatWithDolarSign(double paramDouble) {
        return String.format("$ %s", new Object[] { TwoDecimalsFormat(paramDouble) });
    }

    public static String TwoDecimalsFormatWithDolarSign(String paramString) {
        String str = paramString;
        try {
            if (paramString.equals(""))
                str = "0.00 ";
            return TwoDecimalsFormatWithDolarSign(Double.parseDouble(str.replaceAll(",", "")));
        } catch (Exception exception) {
            return TwoDecimalsFormatWithDolarSign(0.0D);
        }
    }

    public static String bytes2HexString(byte[] paramArrayOfbyte) {
        if (paramArrayOfbyte == null)
            return "";
        StringBuilder stringBuilder = new StringBuilder();
        int j = paramArrayOfbyte.length;
        for (int i = 0; i < j; i++) {
            String str = Integer.toHexString(paramArrayOfbyte[i] & 0xFF);
            if (str.length() == 1)
                stringBuilder.append('0');
            stringBuilder.append(str);
        }
        return stringBuilder.toString().toUpperCase();
    }

    public static String fromBytes(byte[] paramArrayOfbyte) {
        return fromBytesEncoding(paramArrayOfbyte, "ISO-8859-1");
    }

    public static String fromBytesEncoding(byte[] paramArrayOfbyte, String paramString) {
        try {
            return new String(paramArrayOfbyte, paramString);
        } catch (UnsupportedEncodingException unsupportedEncodingException) {
            return null;
        }
    }

    public static String getReceiptsDate(long paramLong) {
        Date date = new Date(paramLong);
        Locale locale = new Locale("es", "MX");
        return (new SimpleDateFormat("dd 'de' MMMM 'de' yyyy", locale)).format(date).toUpperCase(locale);
    }

    public static String getReceiptsHour(long paramLong) {
        Date date = new Date(paramLong);
        return (new SimpleDateFormat("HH:mm")).format(date);
    }

    public static byte hex2byte(char paramChar) {
        return (paramChar <= 'f' && paramChar >= 'a') ? (byte)(paramChar - 97 + 10) : ((paramChar <= 'F' && paramChar >= 'A') ? (byte)(paramChar - 65 + 10) : ((paramChar <= '9' && paramChar >= '0') ? (byte)(paramChar - 48) : 0));
    }

    public static byte[] hexString2Bytes(String paramString) {
        byte[] arrayOfByte = new byte[(paramString.length() + 1) / 2];
        String str = paramString;
        if ((paramString.length() & 0x1) == 1) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(paramString);
            stringBuilder.append("0");
            str = stringBuilder.toString();
        }
        for (int i = 0; i < arrayOfByte.length; i++)
            arrayOfByte[i] = (byte)(hex2byte(str.charAt(i * 2 + 1)) | hex2byte(str.charAt(i * 2)) << 4);
        return arrayOfByte;
    }

    public static String leftPad(int paramInt, String paramString) {
        for (int i = 0; i < paramInt; i++) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(paramString);
            stringBuilder.append("0");
            stringBuilder.append(paramString);
            paramString = stringBuilder.toString();
        }
        return paramString;
    }

    public static String maskCardNumber(String paramString1, String paramString2) {
        int i = 0;
        StringBuilder stringBuilder = new StringBuilder();
        int j;
        for (j = 0; j < paramString2.length(); j++) {
            char c = paramString2.charAt(j);
            if (c == '#') {
                stringBuilder.append(paramString1.charAt(i));
                i++;
            } else if (c == '*') {
                stringBuilder.append(c);
                i++;
            } else {
                stringBuilder.append(c);
            }
        }
        return stringBuilder.toString();
    }

    public static byte[] merage(byte[][] paramArrayOfbyte) {
        int j = 0;
        int i = 0;
        while (i < paramArrayOfbyte.length) {
            if (paramArrayOfbyte[i] != null) {
                j += (paramArrayOfbyte[i]).length;
                i++;
                continue;
            }
            throw new IllegalArgumentException("");
        }
        byte[] arrayOfByte = new byte[j];
        int k = paramArrayOfbyte.length;
        j = 0;
        for (i = 0; i < k; i++) {
            byte[] arrayOfByte1 = paramArrayOfbyte[i];
            System.arraycopy(arrayOfByte1, 0, arrayOfByte, j, arrayOfByte1.length);
            j += arrayOfByte1.length;
        }
        return arrayOfByte;
    }

    public static byte[] subBytes(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
        if (paramInt1 < 0 || paramArrayOfbyte.length <= paramInt1)
            return null;
        if (paramInt2 >= 0) {
            int j = paramInt2;
            if (paramArrayOfbyte.length < paramInt1 + paramInt2) {
                j = paramArrayOfbyte.length - paramInt1;
                byte[] arrayOfByte2 = new byte[j];
                System.arraycopy(paramArrayOfbyte, paramInt1, arrayOfByte2, 0, j);
                return arrayOfByte2;
            }
            byte[] arrayOfByte1 = new byte[j];
            System.arraycopy(paramArrayOfbyte, paramInt1, arrayOfByte1, 0, j);
            return arrayOfByte1;
        }
        int i = paramArrayOfbyte.length - paramInt1;
        byte[] arrayOfByte = new byte[i];
        System.arraycopy(paramArrayOfbyte, paramInt1, arrayOfByte, 0, i);
        return arrayOfByte;
    }
}

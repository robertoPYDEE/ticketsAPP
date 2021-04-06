package com.innova.ticketsapp.utils;
import android.annotation.SuppressLint;


@SuppressLint("UseValueOf")
public class ByteUtil {
    private ByteUtil() {
        System.out.println("ByteUtil Constructor");
    }

    public static String bytesToString(byte[] b) {
        StringBuffer result = new StringBuffer("");
        int length = b.length;

        for (int i = 0; i < length; i++) {
            char ch = (char)(b[i] & 0xff);
            if (ch == 0) {
                break;
            }

            result.append(ch);
        }
        return result.toString();
    }
    public static String bytearrayToHexString(byte[] b, int leng) {
        StringBuffer strbuf = new StringBuffer();

        for (int i = 0; i < leng; i++) {
            strbuf.append("0123456789ABCDEF".charAt(((byte) ((b[i] & 0xf0) >> 4))));
            strbuf.append("0123456789ABCDEF".charAt((byte) (b[i] & 0x0f)));
         //   strbuf.append(" ");
        }
        return strbuf.toString();
    }

    public static byte[] stringToBytes(String s) {
        return s.getBytes();
    }

    public static void ShortToBytes(byte[] b, short x, int offset) {
        //byte[] b = new byte[2];

        if (b.length-offset >= 2) {
            b[offset + 1] = (byte) (x >> 8);
            b[offset + 0] = (byte) (x >> 0);
        }

        //return b;
    }
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }
    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));

        }
        return d;
    }
    public static short BytesToShort(byte[] b, int offset) {
        short x = 0;
        if (b.length-offset >= 2) {
            x = (short) (((b[offset + 1] << 8) | b[offset + 0] & 0xff));
        }

        return x;
    }

    public static String byteToHexString(byte b) {

        StringBuffer sbBuffer = new StringBuffer();
        sbBuffer.append("0123456789ABCDEF".charAt((int) ((b >> 4) & 0x0F)));
        sbBuffer.append("0123456789ABCDEF".charAt((int) (b & 0x0F)));
        return sbBuffer.toString();
    }
    public static String byteToCharString(byte b) {

        StringBuffer sbBuffer = new StringBuffer();
        sbBuffer.append((char)b);
        return sbBuffer.toString();
    }
    public static void IntToBytes(byte[] b, int x, int offset) {
        //byte[] b = new byte[4];

        if (b.length-offset >= 4) {
            b[offset + 3] = (byte) (x >> 24);
            b[offset + 2] = (byte) (x >> 16);
            b[offset + 1] = (byte) (x >> 8);
            b[offset + 0] = (byte) (x >> 0);
        }

    }

    public static int BytesToInt(byte[] b, int offset) {
        int x = 0;
        if (b.length-offset >= 4) {
            x = (int) ((((b[offset + 3] & 0xff) << 24)
                    | ((b[offset + 2] & 0xff) << 16)
                    | ((b[offset + 1] & 0xff) << 8) | ((b[offset + 0] & 0xff) << 0)));
        }
        return x;
    }

    public static void LongToBytes(byte[] b, long x, int offset) {
        if (b.length-offset >= 8) {
            b[offset + 7] = (byte) (x >> 56);
            b[offset + 6] = (byte) (x >> 48);
            b[offset + 5] = (byte) (x >> 40);
            b[offset + 4] = (byte) (x >> 32);
            b[offset + 3] = (byte) (x >> 24);
            b[offset + 2] = (byte) (x >> 16);
            b[offset + 1] = (byte) (x >> 8);
            b[offset + 0] = (byte) (x >> 0);
        }

        //return b;
    }

    public static long BytesToLong(byte[] b, int offset) {
        long x = 0;
        if (b.length-offset >= 8) {
            x = ((((long) b[offset + 7] & 0xff) << 56)
                    | (((long) b[offset + 6] & 0xff) << 48)
                    | (((long) b[offset + 5] & 0xff) << 40)
                    | (((long) b[offset + 4] & 0xff) << 32)
                    | (((long) b[offset + 3] & 0xff) << 24)
                    | (((long) b[offset + 2] & 0xff) << 16)
                    | (((long) b[offset + 1] & 0xff) << 8) | (((long) b[offset + 0] & 0xff) << 0));
        }

        return x;
    }

    public static void CharToBytes(byte[] b, char ch, int offset) {
        // byte[] b = new byte[2];

        if (b.length-offset >= 2) {
            int temp = (int) ch;
            for (int i = 0; i < 2; i ++ ) {
                b[offset + i] = new Integer(temp & 0xff).byteValue();
                temp = temp >> 8;
            }
        }
    }

    public static char BytesToChar(byte[] b, int offset) {
        int s = 0;

        if (b.length-offset >= 2) {
            if (b[offset + 1] > 0)
                s += b[offset + 1];
            else
                s += 256 + b[offset + 0];
            s *= 256;
            if (b[offset + 0] > 0)
                s += b[offset + 1];
            else
                s += 256 + b[offset + 0];
        }

        char ch = (char) s;
        return ch;
    }

    public static void FloatToBytes(byte[] b, float x, int offset) {
        if (b.length-offset >= 4) {
            int l = Float.floatToIntBits(x);
            for (int i = 0; i < 4; i++) {
                b[offset + i] = new Integer(l).byteValue();
                l = l >> 8;
            }
        }
    }

    public static float BytesToFloat(byte[] b, int offset) {
        int l = 0;

        if (b.length-offset >= 4) {
            l = b[offset + 0];
            l &= 0xff;
            l |= ((long) b[offset + 1] << 8);
            l &= 0xffff;
            l |= ((long) b[offset + 2] << 16);
            l &= 0xffffff;
            l |= ((long) b[offset + 3] << 24);
        }

        return Float.intBitsToFloat(l);
    }

    public static void DoubleToBytes(byte[] b, double x, int offset) {
        //byte[] b = new byte[8];

        if (b.length-offset >= 8) {
            long l = Double.doubleToLongBits(x);
            for (int i = 0; i < 4; i++) {
                b[offset + i] = new Long(l).byteValue();
                l = l >> 8;
            }
        }

        //return b;
    }

    public static double BytesToDouble(byte[] b, int offset) {
        long l = 0;

        if (b.length-offset >= 8) {
            l = b[0];
            l &= 0xff;
            l |= ((long) b[1] << 8);
            l &= 0xffff;
            l |= ((long) b[2] << 16);
            l &= 0xffffff;
            l |= ((long) b[3] << 24);
            l &= 0xffffffffl;
            l |= ((long) b[4] << 32);
            l &= 0xffffffffffl;
            l |= ((long) b[5] << 40);
            l &= 0xffffffffffffl;
            l |= ((long) b[6] << 48);
            l &= 0xffffffffffffffl;
            l |= ((long) b[7] << 56);
        }

        return Double.longBitsToDouble(l);
    }
    public static short toLH(short n) {
        byte[] b = new byte[2];
        b[0] = (byte) (n & 0xff);
        b[1] = (byte) (n >> 8 & 0xff);

        short ret = BytesToShort(b, 0);
        return ret;
    }

    public static short toHL(short n) {
        byte[] b = new byte[2];
        b[1] = (byte) (n & 0xff);
        b[0] = (byte) (n >> 8 & 0xff);

        short ret = BytesToShort(b, 0);
        return ret;
    }

    public static int toLH(int n) {
        byte[] b = new byte[4];
        b[0] = (byte) (n & 0xff);
        b[1] = (byte) (n >> 8 & 0xff);
        b[2] = (byte) (n >> 16 & 0xff);
        b[3] = (byte) (n >> 24 & 0xff);

        int ret = BytesToInt(b, 0);
        return ret;
    }

    public static int toHL(int n) {
        byte[] b = new byte[4];
        b[3] = (byte) (n & 0xff);
        b[2] = (byte) (n >> 8 & 0xff);
        b[1] = (byte) (n >> 16 & 0xff);
        b[0] = (byte) (n >> 24 & 0xff);

        int ret = BytesToInt(b, 0);
        return ret;
    }

    public static long toLH(long n) {
        byte[] b = new byte[8];
        b[0] = (byte) (n & 0xff);
        b[1] = (byte) (n >> 8 & 0xff);
        b[2] = (byte) (n >> 16 & 0xff);
        b[3] = (byte) (n >> 24 & 0xff);
        b[4] = (byte) (n >> 32 & 0xff);
        b[5] = (byte) (n >> 40 & 0xff);
        b[6] = (byte) (n >> 48 & 0xff);
        b[7] = (byte) (n >> 56 & 0xff);

        long ret = BytesToLong(b, 0);
        return ret;
    }


    public static long toHL(long n) {
        byte[] b = new byte[8];
        b[7] = (byte) (n & 0xff);
        b[6] = (byte) (n >> 8 & 0xff);
        b[5] = (byte) (n >> 16 & 0xff);
        b[4] = (byte) (n >> 24 & 0xff);
        b[3] = (byte) (n >> 32 & 0xff);
        b[2] = (byte) (n >> 40 & 0xff);
        b[1] = (byte) (n >> 48 & 0xff);
        b[0] = (byte) (n >> 56 & 0xff);

        long ret = BytesToLong(b, 0);
        return ret;
    }


    public static void encodeOutputBytes(byte[] b, short sLen) {
        if (b.length >= sLen+2) {
            System.arraycopy(b, 0, b, 2, sLen);
            byte[] byShort = new byte[2];
            ShortToBytes(byShort, sLen, 0);
            System.arraycopy(byShort, 0, b, 0, byShort.length);
        }
    }


    public static short decodeOutputBytes(byte[] b) {
        byte[] byShort = new byte[2];
        System.arraycopy(b, 0, byShort, 0, byShort.length);
        short sLen = BytesToShort(byShort, 0);

        System.arraycopy(b, 2, b, 0, sLen);

        return sLen;
    }

	/**
	 * 比较两个byte数组数据是否相同,相同返回 true
	 * 
	 * @param data1
	 * @param data2
	 * @param len
	 * @return
	 */
	public static boolean memcmp(byte[] data1, byte[] data2, int len) {
		if (data1 == null && data2 == null) {
			return true;
		}
		if (data1 == null || data2 == null) {
			return false;
		}
		if (data1 == data2) {
			return true;
		}
		
		boolean bEquals = true;
		int i;
		for (i = 0; i < data1.length && i < data2.length && i < len; i++) {
			if (data1[i] != data2[i]) {
				bEquals = false;
				break;
			}
		}
		return bEquals;
	}
	
	public static void XOR(byte[] byBuff1, byte[] byBuff2, byte[] byBuff3, int iLen)
    {
        for(int i = 0; i < iLen; i++)
        {
            byBuff3[i] = (byte)(byBuff1[i] ^ byBuff2[i]);
        }
    }

    public static int byteArrayIsAllZero(byte[] byBuff, int iLen)
    {
        int i = 0;
        for(i = 0; i < iLen; i++)
        {
            if(0x00 != byBuff[i])
                return -1;
        }

        if((i > 0) &&
                (i >= iLen))
            return 0;


        return -2;
    }
    
    public static String bcd2Str(byte[] bytes) {
        char temp[] = new char[bytes.length * 2], val;  
  
        for (int i = 0; i < bytes.length; i++) {  
            val = (char) (((bytes[i] & 0xf0) >> 4) & 0x0f);  
            temp[i * 2] = (char) (val > 9 ? val + 'A' - 10 : val + '0');  
  
            val = (char) (bytes[i] & 0x0f);  
            temp[i * 2 + 1] = (char) (val > 9 ? val + 'A' - 10 : val + '0');  
        }  
        return new String(temp);
    } 
    
    public static byte[] str2Bcd(String asc) {
    	int len = asc.length(); 
    	int mod = len % 2; 
    	if (mod != 0) { 
    	asc = "0" + asc; 
    	len = asc.length(); 
    	} 
    	byte abt[] = new byte[len]; 
    	if (len >= 2) { 
    	len = len / 2; 
    	} 
    	byte bbt[] = new byte[len]; 
    	abt = asc.getBytes(); 
    	int j, k; 
    	for (int p = 0; p < asc.length() / 2; p++) { 
    	if ((abt[2 * p] >= '0') && (abt[2 * p] <= '9')) { 
    	j = abt[2 * p] - '0'; 
    	} else if ((abt[2 * p] >= 'a') && (abt[2 * p] <= 'z')) { 
    	j = abt[2 * p] - 'a' + 0x0a; 
    	} else { 
    	j = abt[2 * p] - 'A' + 0x0a; 
    	} 
    	if ((abt[2 * p + 1] >= '0') && (abt[2 * p + 1] <= '9')) { 
    	k = abt[2 * p + 1] - '0'; 
    	} else if ((abt[2 * p + 1] >= 'a') && (abt[2 * p + 1] <= 'z')) { 
    	k = abt[2 * p + 1] - 'a' + 0x0a; 
    	} else { 
    	k = abt[2 * p + 1] - 'A' + 0x0a; 
    	} 
    	int a = (j << 4) + k; 
    	byte b = (byte) a; 
    	bbt[p] = b; 
    	} 
    	return bbt; 
    	} 

    public static int BCDToInt(byte bcd) //BCDתʮ����
    {
          return ((byte)0x0f & (byte)(bcd>>4))*10 +(0x0f & bcd);
    }
     
    public static int IntToBCD(byte ints) //ʮ����תBCD
    {
    	return ((ints/10)<<4+((ints%10)&0x0f));
    }
    public static int crc_cal_by_bit(byte [] ptr, int len) 
    {
    	int crc = 0;
    	int j = 0;
    	while(len-- != 0) 
    	{
    		for(int i = 128; i != 0; i /= 2) 
    		{
    			crc *= 2; 
    			if((crc&0x10000) !=0) //��һλCRC��2������λ��1�������0x11021 
    				crc ^= 0x11021; 
    			if((ptr[j]&i) != 0) //�����λ��1����ôCRC = ��һλ��CRC + ��λ/CRC_CCITT 
    				crc ^= 0x1021; 
    		}
    		j++; 
    	}		
    	return crc; 
    }    
}
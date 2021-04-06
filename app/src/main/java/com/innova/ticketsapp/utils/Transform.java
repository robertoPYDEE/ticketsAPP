package com.innova.ticketsapp.utils;

public class Transform {
	

	public static String str2HexStr(String str)
	{      
	  
	    char[] chars = "0123456789ABCDEF".toCharArray();      
	    StringBuilder sb = new StringBuilder("");
	    byte[] bs = str.getBytes();      
	    int bit;      
	        
	    for (int i = 0; i < bs.length; i++)    
	    {      
	        bit = (bs[i] & 0x0f0) >> 4;      
	        sb.append(chars[bit]);      
	        bit = bs[i] & 0x0f;      
	        sb.append(chars[bit]);    
	        sb.append(' ');    
	    }      
	    return sb.toString().trim();      
	}    
	    

	public static String hexStr2Str(String hexStr)
	{      
	    String str = "0123456789ABCDEF";
	    char[] hexs = hexStr.toCharArray();      
	    byte[] bytes = new byte[hexStr.length() / 2];      
	    int n;      
	  
	    for (int i = 0; i < bytes.length; i++)    
	    {      
	        n = str.indexOf(hexs[2 * i]) * 16;      
	        n += str.indexOf(hexs[2 * i + 1]);      
	        bytes[i] = (byte) (n & 0xff);      
	    }      
	    return new String(bytes);
	}    
	   

	public static String byte2HexStr(byte[] b)
	{    
	    String stmp="";
	    StringBuilder sb = new StringBuilder("");
	    for (int n=0;n<b.length;n++)    
	    {    
	        stmp = Integer.toHexString(b[n] & 0xFF);
	        sb.append((stmp.length()==1)? "0"+stmp : stmp);    
	        sb.append(" ");    
	    }    
	    return sb.toString().toUpperCase().trim();    
	}    
	    
	/**  
	 * bytes�ַ���ת��ΪByteֵ  
	 * @param String src Byte�ַ�����ÿ��Byte֮��û�зָ���  
	 * @return byte[]  
	 */    
	public static byte[] hexStr2Bytes(String src)
	{    
	    int m=0,n=0;    
	    int l=src.length()/2;    
	    System.out.println(l);
	    byte[] ret = new byte[l];    
	    for (int i = 0; i < l; i++)    
	    {    
	        m=i*2+1;    
	        n=m+1;    
	        ret[i] = Byte.decode("0x" + src.substring(i*2, m) + src.substring(m,n));
	    }    
	    return ret;    
	}    
	  
	/**  
	 * String���ַ���ת����unicode��String  
	 * @param String strText ȫ���ַ���  
	 * @return String ÿ��unicode֮���޷ָ���  
	 * @throws Exception
	 */    
	public static String strToUnicode(String strText)
	    throws Exception
	{    
	    char c;    
	    StringBuilder str = new StringBuilder();
	    int intAsc;    
	    String strHex;
	    for (int i = 0; i < strText.length(); i++)    
	    {    
	        c = strText.charAt(i);    
	        intAsc = (int) c;    
	        strHex = Integer.toHexString(intAsc);
	        if (intAsc > 128)    
	            str.append("\\u" + strHex);    
	       else // ��λ��ǰ�油00    
	            str.append("\\u00" + strHex);    
	    }    
	    return str.toString();    
	}    
	    
	/**  
	 * unicode��Stringת����String���ַ���  
	 * @param String hex 16����ֵ�ַ��� ��һ��unicodeΪ2byte��  
	 * @return String ȫ���ַ���  
	 */    
	public static String unicodeToString(String hex)
	{    
	    int t = hex.length() / 6;    
	    StringBuilder str = new StringBuilder();
	    for (int i = 0; i < t; i++)    
	    {    
	        String s = hex.substring(i * 6, (i + 1) * 6);
	        // ��λ��Ҫ����00��ת    
	        String s1 = s.substring(2, 4) + "00";
	        // ��λֱ��ת    
	        String s2 = s.substring(4);
	        // ��16���Ƶ�stringתΪint    
	        int n = Integer.valueOf(s1, 16) + Integer.valueOf(s2, 16);
	        // ��intת��Ϊ�ַ�    
	        char[] chars = Character.toChars(n);
	        str.append(new String(chars));
	    }    
	    return str.toString();    
	}   
	
	/** *//**  
	    * @��������: 10���ƴ�תΪBCD��  
	    * @�������: 10���ƴ�  
	    * @������: BCD��  
	    */   
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
	  
	    for (int p = 0; p < asc.length()/2; p++) {   
	     if ( (abt[2 * p] >= '0') && (abt[2 * p] <= '9')) {   
	      j = abt[2 * p] - '0';   
	     } else if ( (abt[2 * p] >= 'a') && (abt[2 * p] <= 'z')) {   
	      j = abt[2 * p] - 'a' + 0x0a;   
	     } else {   
	      j = abt[2 * p] - 'A' + 0x0a;   
	     }  
	  
	     if ( (abt[2 * p + 1] >= '0') && (abt[2 * p + 1] <= '9')) {   
	      k = abt[2 * p + 1] - '0';   
	     } else if ( (abt[2 * p + 1] >= 'a') && (abt[2 * p + 1] <= 'z')) {   
	      k = abt[2 * p + 1] - 'a' + 0x0a;   
	     }else {   
	      k = abt[2 * p + 1] - 'A' + 0x0a;   
	     }  
	  
	     int a = (j << 4) + k;   
	     byte b = (byte) a;   
	     bbt[p] = b;   
	    }   
	    return bbt;   
	}   

	/** *//**  
	    * @��������: BCD��תΪ10���ƴ�(����������)  
	    * @�������: BCD��  
	    * @������: 10���ƴ�  
	    */   
	public static String bcd2Str(byte[] bytes){
	    StringBuffer temp=new StringBuffer(bytes.length*2);
	  
	    for(int i=0;i<bytes.length;i++){   
	     temp.append((byte)((bytes[i]& 0xf0)>>>4));   
	     temp.append((byte)(bytes[i]& 0x0f));   
	    }   
	    return temp.toString().substring(0,1).equalsIgnoreCase("0")?temp.toString().substring(1):temp.toString();   
	}  

}

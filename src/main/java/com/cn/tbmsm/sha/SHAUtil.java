package com.cn.tbmsm.sha;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHAUtil {
	  public static String SHA256(final String strText)  
	  {  
	    return SHA(strText, "SHA-256");  
	  }  
	  
	  /** 
	   * �����ı����ݣ����� SHA-512 �� 
	   *  
	   * @param strText 
	   * @return 
	   */  
	  public static String SHA512(final String strText)  
	  {  
	    return SHA(strText, "SHA-512");  
	  }  
	  
	private static String SHA(final String strText, final String strType)  
	  {  
	    // ����ֵ  
	    String strResult = null;  
	  
	    // �Ƿ�����Ч�ַ���  
	    if (strText != null && strText.length() > 0)  
	    {  
	      try  
	      {  
	        // SHA ���ܿ�ʼ  
	        // �������ܶ��� ��Ƽ�������  
	        MessageDigest messageDigest = MessageDigest.getInstance(strType);  
	        // ����Ҫ���ܵ��ַ���  
	        messageDigest.update(strText.getBytes());  
	        // �õ� byte ���  
	        byte byteBuffer[] = messageDigest.digest();  
	  
	        // �� byte ת��Ϊ string  
	        StringBuffer strHexString = new StringBuffer();  
	        // ����byte buffer  
	        for (int i = 0; i < byteBuffer.length; i++)  
	        {  
	          String hex = Integer.toHexString(0xff & byteBuffer[i]);  
	          if (hex.length() == 1)  
	          {  
	            strHexString.append('0');  
	          }  
	          strHexString.append(hex);  
	        }  
	        // �õ����ؽY��  
	        strResult = strHexString.toString();  
	      }  
	      catch (NoSuchAlgorithmException e)  
	      {  
	        e.printStackTrace();  
	      }  
	    }  
	  
	    return strResult;  
	  }  
}

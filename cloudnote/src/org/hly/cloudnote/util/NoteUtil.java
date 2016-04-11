package org.hly.cloudnote.util;

import java.security.MessageDigest;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;

public class NoteUtil {

	/**����������ID
	 * @return  ����һ�������UUIDֵ ���ظ�
	 */
	public static String createId(){
		UUID uuid=UUID.randomUUID();
	      return uuid.toString();	
	}
	
     /**
      * ������ܴ���
     * @param src ����
     * @return  ����
     * @throws Exception 
     */
    public static String md5(String src) throws Exception{
    	MessageDigest md=MessageDigest.getInstance("md5");
    	//��byte���ֽ��м��ܷ��ص�������
    	//�����ܺ��byte�������Base64ת���ַ�����
    	String output=Base64.encodeBase64String(md.digest(src.getBytes()));	
    	 return output;
     }
    
    public static void main(String[] args) throws Exception {
		    System.out.println(md5("123456"));
	}
    
}



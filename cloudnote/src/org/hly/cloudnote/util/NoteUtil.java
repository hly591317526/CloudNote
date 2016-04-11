package org.hly.cloudnote.util;

import java.security.MessageDigest;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;

public class NoteUtil {

	/**生成主键的ID
	 * @return  返回一个随机的UUID值 不重复
	 */
	public static String createId(){
		UUID uuid=UUID.randomUUID();
	      return uuid.toString();	
	}
	
     /**
      * 密码加密处理
     * @param src 明文
     * @return  密文
     * @throws Exception 
     */
    public static String md5(String src) throws Exception{
    	MessageDigest md=MessageDigest.getInstance("md5");
    	//对byte数字进行加密返回的是数组
    	//将加密后的byte数组采用Base64转成字符串。
    	String output=Base64.encodeBase64String(md.digest(src.getBytes()));	
    	 return output;
     }
    
    public static void main(String[] args) throws Exception {
		    System.out.println(md5("123456"));
	}
    
}



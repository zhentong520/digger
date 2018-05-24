package com.ko30.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;


/**
 * 生成用户永久信息工具类
 * @author DengHuaqing
 * 2017-4-04-13
 *
 */
public  class StringForUserUtils {
	
	public static String[] chars = new String[] { "a", "b", "c", "d", "e", "f",  
        "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",  
        "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",  
        "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",  
        "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",  
        "W", "X", "Y", "Z" };  
	
	public static char[] letters = {'A', 'C', '1', '2', '3','4', 'a', 'e', 'f', 'z'};


	/**
	 * 生成userId 长id
	 * @return
	 */
    public static String generateLongUuid() {  
       StringBuffer struffer = new StringBuffer();  
       String uuid = UUID.randomUUID().toString().replace("-", "");  
        for (int i = 0; i < 8; i++) {  
        String str = uuid.substring(i * 4, i * 4 + 4);  
        int x = Integer.parseInt(str, 16);  
        struffer.append(chars[x % 0x3E]);  
     } 
        struffer.append(uuid.substring(0, 12));   
     return struffer.toString();  

     }  
    
    /**
     * 生产短ID
     * @return
     */
    public static String generateShortUuid(){
    	
    	String tStr = String.valueOf(System.currentTimeMillis());  
    	/*因为tStr的字符只有‘0’-‘9’，我们可以把它看成索引到letters中找相应的字符，这样相当于“置换”，所以是不会重复的。*/  
    	StringBuilder sb = new StringBuilder();  
    	for(int i=1;i<tStr.length();i++)  
    	{  
    	  sb.append(letters[tStr.charAt(i)-'0']);  
    	}  
    	
    	return sb.toString();
    	
    	
    }
    
    public static String createUserCode(){
    	
    	List<Integer> list = new ArrayList<Integer>();
		Random random = new Random();
		while (true) {
		 int randomNum = random.nextInt(10);
		 boolean flag = false;
		 for(Integer in : list){
		 if(in == randomNum){
		 flag = true;
		 break;
		 }
		 }
		 if(!flag){
		 list.add(randomNum);
		 }
		 if(list.size()>=6){
		 break;
		 }
		}
		String randomStr = "";
		for (Integer in : list) {
		randomStr += in.toString();
		}
		System.out.println(randomStr);
		return randomStr;
    }
    
    
    public static void main(String [] orgs){
    	 //这里根据你的需要初始化不同的字符  
    	/*String longStr = generateLongUuid();
    	String shortStr = generateShortUuid();
    	System.out.println(longStr.toString()); 
    	System.out.println(longStr.length()); 
    	System.out.println(shortStr.toString()); 
    	System.out.println(shortStr.length());*/
    	
    
    	
    	
    }

}

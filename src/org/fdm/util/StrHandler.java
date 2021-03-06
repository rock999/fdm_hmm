/**
 * 
 */
package org.fdm.util;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author liyi
 *
 */
public class StrHandler {
	//return the distance between two strings ignoring all character not English and Chinese 
	public static int getDis(String s1,String s2){
		int s1len;
		int s2len;
		int d1,d2,d3;

		s1len = s1.length();
		s2len = s2.length();
		
		int[][] disMat= new int[s1len+1][s2len+1];
		
		//Dynamic programing  
		for(int i=0;i<=s1len;i++)
			disMat[i][0] = i;
		for(int j=0;j<=s2len;j++)
			disMat[0][j] = j;
		
		for(int i=1;i<=s1len;i++)
			for(int j=1;j<=s2len;j++){
				d1 = disMat[i-1][j]+1;
				d2 = disMat[i][j-1]+1;
				if(s1.charAt(i-1)==s2.charAt(j-1))
					d3 = disMat[i-1][j-1];
				else
					d3 = disMat[i-1][j-1]+1;
				disMat[i][j] = Math.min(d3,Math.min(d1, d2));
			}
		return disMat[s1len][s2len];
	}
	
	public static String[] findEnString(String s1, String s2){
		int n;
		for(int i=0;i<s1.length();i++){
			n = (int)s1.charAt(i);
			if(!(19968<=n&&n<=40623)&&!(97<=n&&n<=122||65<=n&&n<=90))
				s1 = s1.replace(s1.charAt(i),' ');
		}
		for(int i=0;i<s2.length();i++){
			n = (int)s2.charAt(i);
			if(!(19968<=n&&n<=40623)&&!(97<=n&&n<=122||65<=n&&n<=90))
				s2 = s2.replace(s2.charAt(i),' ');
		}
		
		s1 = s1.replaceAll("\u0020", "");
		s2 = s2.replaceAll("\u0020", "");
		
		s1 = s1.toLowerCase();
		s2 = s2.toLowerCase();
		
		
		int firstEnIndex = -1;
		int lastEnIndex = -1;
		int firstEnIndex2 = -1;
		int lastEnIndex2 = -1;
		
		firstEnIndex=getFirstEnIndex(s1);
		lastEnIndex=getLastEnIndex(s1);
		firstEnIndex2 = getFirstEnIndex(s2);
		lastEnIndex2 = getLastEnIndex(s2);
		if(lastEnIndex == -1 || lastEnIndex2 == -1){
			return null;
		}
		else{
			String[] s = {s1.substring(firstEnIndex, lastEnIndex),s2.substring(firstEnIndex2, lastEnIndex2)};
			return s;
		}
	}
	
	public static int deleteEnError(String s1, String s2){
		String[] s = findEnString(s1, s2);
		if(s == null)
			return 1;
		int i = getDis(s[0],s[1]);
		if (i >1)
			return 1;
		else 
			return 0;
	}
	
	public static int getFirstEnIndex(String s){
		int n;
		
		for(int i=0;i<s.length();i++){
			n = (int)s.charAt(i);
			//97 122 65 90
			if((97<=n&&n<=122)||(65<=n&&n<=90))
				return i;
		}
		return -1;
	}
	
	public static int getLastEnIndex(String s){
		int n;
		int stat = 0;
		for(int i=0;i<s.length();i++){
			n = (int)s.charAt(i);
			if((97<=n&&n<=122)||(65<=n&&n<=90)||n==46){
				stat = 1;
				if(i==s.length()-1)
					return i;
			}
			//97 122 65 90
			if(stat==1&&!((97<=n&&n<=122)||(65<=n&&n<=90)||n==46))
				return i-1;
		}
		return -1;
	}
	
	public static int getFirstCnIndex(String s){
		int n;
		
		for(int i=0;i<s.length();i++){
			n = (int)s.charAt(i);
			if(19968<=n&&n<=40623){
				return i;
			}
		}
		return -1;
	}
	
	public static int getLastCnIndex(String s){
		int n;
		int stat = 0;
		for(int i=0;i<s.length();i++){
			n = (int)s.charAt(i);
			if(19968<=n&&n<=40623){
				stat = 1;
				if(i==s.length()-1)
					return i;
			}
			else if(stat==1&&!(19968<=n&&n<=40623)){
				return i-1;
			}
		}
		return -1;
	}
	
	public static String introArray2string(ArrayList<String> textList,  ArrayList<Integer> clsList){
		String intro_replace = new String();
		for(int i=0;i<textList.size();i++){
			intro_replace = intro_replace+textList.get(i)+"\t"+clsList.get(i)+"\t"+i+"\t";
		}
		
		return intro_replace;
	}
	
	public static void introString2array(String introduction, ArrayList<String> textList,  ArrayList<Integer> tagList){
		Pattern pattern = Pattern.compile("(?<tok>[^\\s]+)[\\s+]+(?<type>\\d+)[\\s+]+(?<index>\\d+)[\\s+]+");
		Matcher matcher;
		matcher = pattern.matcher(introduction);
		while(matcher.find()) {
			textList.add(matcher.group("tok"));
			tagList.add(Integer.parseInt(matcher.group("type")));
		}
	}
	
//	public static ArrayList<String> split(String in, String delimiter) {
//		ArrayList<String> splitList = new ArrayList<String>();
//		in.
//		
//		return splitList;
//	}
	
//	public static void main(String args[]){
//		int D = getDis("仇卫平","任华");
//		System.out.print(D);
//	}
}

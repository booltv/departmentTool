import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;



/**
 * base64 与 file 之间的相互转化
 * 实现形式, 懒汉式的单例模式
 */
public class FileToBase64 {
    // 私有化构造器
	public static void main(String[] args){
		FileToBase64 base = getFileBase64();
		//String str = base.fileToBase64("D:\\文件归档.rar");
		String str2 = readFileByLines("D:\\hello.txt");
		
		//StringToFile(str,"D:\\test.text");
		
		
		//System.out.println(str);
		base.base64ToFile(str2, "D:\\文件归档3.rar");
		System.out.print("ok");
	}
    private FileToBase64() {
    }

    // 事先定义一个变量存放该类的实例
    private static FileToBase64 fileBase64 = null;

    // 对外暴露一个静态方法获取该类的实例
    public static FileToBase64 getFileBase64() {
        if (fileBase64 == null) {
            fileBase64 = new FileToBase64();
        }
        return fileBase64;
    }

    // 将 file 转化为 Base64
    public String fileToBase64(String path) {
        File file = new File(path);
        FileInputStream inputFile;
        try {
            inputFile = new FileInputStream(file);
            byte[] buffer = new byte[(int) file.length()];
            inputFile.read(buffer);
            inputFile.close();
            return new BASE64Encoder().encode(buffer);
        } catch (Exception e) {
            throw new RuntimeException("文件路径无效\n" + e.getMessage());
        }
    }

    // 将 base64 转化为 file
    public boolean base64ToFile(String base64, String path) {
        byte[] buffer;
        try {
            buffer = new BASE64Decoder().decodeBuffer(base64);
            FileOutputStream out = new FileOutputStream(path);
            out.write(buffer);
            out.close();
            return true;
        } catch (Exception e) {
            throw new RuntimeException("base64字符串异常或地址异常\n" + e.getMessage());
        }
    }
    
    public static boolean StringToFile(String str, String path) {
        byte[] buffer;
        try {
            buffer = str.getBytes();
            FileOutputStream out = new FileOutputStream(path);
            out.write(buffer);
            out.close();
            return true;
        } catch (Exception e) {
            throw new RuntimeException("save string fail\n" + e.getMessage());
        }
    }
    
    public static String readFileByLines(String fileName) {
        FileInputStream file = null;
        BufferedReader reader = null;
        InputStreamReader inputFileReader = null;
        String content = "";
        String tempString = null;
        try {
            file = new FileInputStream(fileName);
            inputFileReader = new InputStreamReader(file, "utf-8");
            reader = new BufferedReader(inputFileReader);
            // 一次读入一行，直到读入null为文件结束
            StringBuilder str = new StringBuilder();

            while ((tempString = reader.readLine()) != null) {
                //content += tempString;
            	str.append(tempString);
            }
            content = str.toString();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return content;
    }
}

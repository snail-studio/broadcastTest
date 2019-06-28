package com.upu.zenka.broadcasttest.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by ThinkPad on 2018/12/21.
 */

public class FileUtils {
    static FileInputStream  fis = null;
    public static boolean isFileExists(final File file){
//        boolean state=false;
//        if (file!=null){
//            String path = file.getPath();
//            if (getFileSize(file)==0){
//                new File(path).delete();
//                state= false;
//            }else{
//                state= true;
//            }
//        }
        if (file.exists()){
            return true;
        }
        return false;
    }
    public static long getFileSize(File file) {
        long size = 0;
        try {
            if (file == null) {
                return 0;
            }
            if (file.exists()) {
                fis = new FileInputStream(file);
                size = fis.available();
            }
            fis=null;
            return size;
        } catch (Exception e) {
            fis=null;
            return 0;
        }
    }
    public static boolean createOrExistsFile(final File file){
        if (file==null) return false;

        if (file.exists()) file.delete();
        if (!createOrExistsDir(file.getParentFile())) return false;
        try{
            return file.createNewFile();
        }catch(IOException e){
            e.printStackTrace();
            return false;
        }
    }

    public static boolean createOrExistsDir(final File file){
        return file!=null&&(file.exists()?file.isDirectory():file.mkdir());
    }

    public static boolean createOrExistsDir(final String dirPath){
        return createOrExistsDir(getFileByPath(dirPath));
    }

    public static File getFileByPath(final String filePath){
        return isSpace(filePath)?null:new File(filePath);
    }

    private static boolean isSpace(final String s){
        if (s==null)return true;
        for (int i=0,len=s.length();i<len;++i){
            if (!Character.isWhitespace(s.charAt(i))){
                return false;
            }
        }
        return true;
    }
    public boolean fileIsExists(String strFile)
    {
        try
        {
            File f=new File(strFile);
            if(!f.exists())
            {
                return false;
            }

        }
        catch (Exception e)
        {
            return false;
        }
        return true;
    }
}
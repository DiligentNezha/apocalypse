package com.apocalypse.user;

import javax.swing.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MavenPackage {

    private static final String PACKAGE_VERSION = "1.0." + new SimpleDateFormat("yyyyMMdd").format(new Date()) +
            "-SNAPSHOT";

    private static final String MAVEN_REPOSITORY = "http://192.168.40.245:8081/repository";

    public static void main(String[] args) throws Exception {

        System.out.println(String.format("%03d", 10));

//        String classesPath = MavenPackage.class.getProtectionDomain().getCodeSource().getLocation().getPath();
//
//        String targetPath = classesPath.substring(1, classesPath.lastIndexOf("classes"));
//
//        String modulePath = targetPath.substring(0, targetPath.lastIndexOf("/target"));
//
//        File file = new File(modulePath + "/mvn.bat");
//        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
//        bw.write("mvn clean");
//        bw.newLine();
//        bw.write("mvn compile");
//        bw.flush();
//        bw.close();

//        String moduleName = modulePath.substring(modulePath.lastIndexOf("/")).substring(1);
//        try {
//            StringBuffer command = new StringBuffer("cmd /c start dir ")
//                    .append(targetPath + " && ")
//                    .append("mvn deploy:deploy-file -DgroupId=com.recycle -DartifactId=")
//                    .append(moduleName + "-dubbo-privider -Dversion=")
//                    .append(PACKAGE_VERSION + " -Dpackaging=jar -Dfile=")
//                    .append(moduleName + "-dubbo-privider.jar -Durl=")
//                    .append(MAVEN_REPOSITORY)
//                    .append("/maven-snapshots/ -DrepositoryId=snapshots");
//            System.out.println(command);
//            String test = "cmd /k start cd " + targetPath + " && mvn clean";
//            System.out.println(test);
//            Process exec = Runtime.getRuntime().exec(test);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}

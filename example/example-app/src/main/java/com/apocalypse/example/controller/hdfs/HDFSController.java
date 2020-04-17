package com.apocalypse.example.controller.hdfs;

import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @description
 * @date 2020/3/29
 */
@Slf4j
@RestController
@RequestMapping("/hdfs")
public class HDFSController {

    @GetMapping("/mkdir")
    public static void main(String[] args) throws Exception {

        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(new URI("hdfs://hadoop-2:9000"), conf, "hadoop");
//        mkdir(fs);
//        copyFromLocalFile(fs);
//        copyToLocalFile(fs);
//        delete(fs);
//        rename(fs);
//        listFiles(fs);
        listStatus(fs);
        fs.close();
    }



    public static void mkdir(FileSystem fs) throws IOException {
        fs.mkdirs(new Path("/user/hadoop/client"));
        fs.mkdirs(new Path("/user/hadoop/client1"));
        fs.mkdirs(new Path("/user/hadoop/client2"));
        fs.mkdirs(new Path("/user/hadoop/client3"));
    }

    public static void copyFromLocalFile(FileSystem fs) throws IOException {
        fs.copyFromLocalFile(new Path("example/example-app/src/main/resources/application.yml"), new Path("/application.yml"));
    }

    public static void copyToLocalFile(FileSystem fs) throws IOException {
        fs.copyToLocalFile(false, new Path("/application.yml"), new Path("example/example-app/src/main/resources/application_from_hadoop.yml"), true);
    }

    public static void delete(FileSystem fs) throws IOException {
        fs.delete(new Path("/user/hadoop"), true);
    }

    public static void rename(FileSystem fs) throws IOException {
        fs.rename(new Path("/user/hadoop/client"), new Path("/user/hadoop/client_new_name"));
    }

    public static void listFiles(FileSystem fs) throws IOException {
        RemoteIterator<LocatedFileStatus> fileStatusRemoteIterator = fs.listFiles(new Path("/"), true);

        while (fileStatusRemoteIterator.hasNext()) {
            LocatedFileStatus fileStatus = fileStatusRemoteIterator.next();

            System.out.println(fileStatus.getPath().getName());
            System.out.println(fileStatus.getPermission());
            System.out.println(fileStatus.getLen());
            BlockLocation[] blockLocations = fileStatus.getBlockLocations();
            for (BlockLocation blockLocation : blockLocations) {
                for (String host : blockLocation.getHosts()) {
                    System.out.println(host);
                }
            }
            System.out.println(fileStatus.getOwner());
            System.out.println(fileStatus.getGroup());
            System.out.println(fileStatus.getAccessTime());
            System.out.println(fileStatus.getReplication());
            System.out.println(fileStatus.getModificationTime());
            System.out.println("------------------");
        }
    }

    public static void listStatus(FileSystem fs) throws IOException {
        FileStatus[] fileStatuses = fs.listStatus(new Path("/"));
        for (FileStatus fileStatus : fileStatuses) {
            if (fileStatus.isFile()) {
                System.out.println("f:" + fileStatus.getPath().getName());
            } else {
                System.out.println("d:" + fileStatus.getPath().getName());
            }
        }

    }


}

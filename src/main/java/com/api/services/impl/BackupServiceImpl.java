package com.api.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
// Scheduled : Not need dependency
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

import com.api.services.BackupService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;

@Service
@EnableAsync
public class BackupServiceImpl implements BackupService {
    private static final Logger LOG = LoggerFactory.getLogger(BackupServiceImpl.class);

    @Async
    // @Scheduled -> https://www.youtube.com/watch?v=92-qLIxv0JA
    //@Scheduled(cron = "${properties.scheduled.autobackup-cron}")
    public void autoFolderBackup() {
        try {
            var dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss");
            var now = dtf.format(LocalDateTime.now());
            LOG.info("Auto backup started at " + now);

            var location = "./src/data/";
            var target = "./src/backups/data";
            var locFile = new File(location);
            var tarFile = new File(target + " backup " + now);

            copyDirectory(locFile, tarFile);

            LOG.info("The creation of the file backup has been done correctly.");
        } catch (Exception e) {
            LOG.error("Error on execution auto file backup", e);
        }
    }

    private void copy(File sourceLocation, File targetLocation) throws IOException {
        if (sourceLocation.isDirectory())
            copyDirectory(sourceLocation, targetLocation);
        else
            copyFile(sourceLocation, targetLocation);
    }

    private void copyDirectory(File source, File target) throws IOException {
        if (!target.exists())
            target.mkdir();
        for (String f : source.list())
            copy(new File(source, f), new File(target, f));
    }

    private void copyFile(File source, File target) throws IOException {
        try (InputStream in = new FileInputStream(source); OutputStream out = new FileOutputStream(target)) {
            byte[] buf = new byte[1024];
            int length;
            while ((length = in.read(buf)) > 0)
                out.write(buf, 0, length);
        } catch (IOException e) {
            LOG.error("Approve option was not selected", e);
        }
    }

}
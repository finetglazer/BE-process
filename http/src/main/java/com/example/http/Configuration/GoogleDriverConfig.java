package com.example.http.Configuration;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Configuration
public class GoogleDriverConfig {

    private static final String APPLICATION_NAME = "Web client 1";
    private static final String CREDENTIALS_FILE_PATH = "src/main/resources/client_secret_832375969173-8iruk025a3jhv5tpnjalhun9u5acmt5q.apps.googleusercontent.com.json";

    public Drive getDriveService() throws IOException {
        GoogleCredential credential = GoogleCredential.fromStream(getClass().getResourceAsStream(CREDENTIALS_FILE_PATH))
                .createScoped(Collections.singleton(DriveScopes.DRIVE));

        return new Drive.Builder(
                credential.getTransport(),
                credential.getJsonFactory(),
                credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public List<File> listFiles() throws IOException {
        Drive driveService = getDriveService();
        FileList result = driveService.files().list().execute();
        return result.getFiles();
    }
}

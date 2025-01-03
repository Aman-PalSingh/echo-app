package com.aps.echo;

import com.aps.echo.folders.Folder;
import com.aps.echo.folders.FolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cassandra.CqlSessionBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.nio.file.Path;

@SpringBootApplication
@RestController
public class EchoApplication {

    @Autowired
    FolderRepository folderRepository;

    public static void main(String[] args) {
        SpringApplication.run(EchoApplication.class, args);
    }

    @Bean
    public CqlSessionBuilderCustomizer sessionBuilderCustomizer(DataStaxAstraProperties astraProperties){
        Path bundle = astraProperties.getSecureConnectBundle().toPath();
        return builder -> builder.withCloudSecureConnectBundle(bundle);
    }
    @PostConstruct
    public void init(){
        folderRepository.save(new Folder("Aman-PalSingh", "personal", "magenta"));
        folderRepository.save(new Folder("Aman-PalSingh", "private", "blue"));
        folderRepository.save(new Folder("Aman-PalSingh", "promotions", "gray"));
        folderRepository.save(new Folder("Aman-PalSingh", "misc", "orange"));
    }



}

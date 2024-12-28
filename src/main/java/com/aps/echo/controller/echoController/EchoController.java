package com.aps.echo.controller.echoController;

import com.aps.echo.folders.Folder;
import com.aps.echo.folders.FolderRepository;
import com.aps.echo.folders.FolderService;
import com.nimbusds.oauth2.sdk.Scope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class EchoController {

    @Autowired
    private FolderRepository folderRepository;

    @Autowired
    private FolderService folderService;

    @GetMapping(value = "/")
    public String homePage(@AuthenticationPrincipal OAuth2User principal, Model model){
        if(principal == null || !StringUtils.hasText(principal.getAttribute("name"))) {
            return "index";
        }
    System.out.println(principal.toString());
        String id = principal.getAttribute("login");
        List<Folder> userFolders = folderRepository.findAllById(id);
        model.addAttribute("userFolders", userFolders);
        List<Folder> defaultFolders = folderService.fetchDefaultFolders(id);
        model.addAttribute("defaultFolders", defaultFolders);
        return "echo-page";
    }

}

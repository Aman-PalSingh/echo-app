package com.aps.echo.controller.echoController;

import com.aps.echo.folders.Folder;
import com.aps.echo.folders.FolderRepository;
import com.aps.echo.folders.FolderService;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ComposeController {
  @Autowired FolderRepository folderRepository;

  @Autowired FolderService folderService;

  @GetMapping(value = "/compose")
  public String getComposePage(
      @RequestParam(required = false) String toIds,
      @AuthenticationPrincipal OAuth2User principal,
      Model model) {
    if (principal == null || !StringUtils.hasText(principal.getAttribute("name"))) {
      return "index";
    }
    // Fetch Folders
    String userId = principal.getAttribute("login");
    model.addAttribute("userName", userId);
    List<Folder> userFolders = folderRepository.findAllById(userId);
    model.addAttribute("userFolders", userFolders);
    // Fetch Default Folders
    List<Folder> defaultFolders = folderService.fetchDefaultFolders(userId);
    model.addAttribute("defaultFolders", defaultFolders);

    if (StringUtils.hasText(toIds)) {
      String[] splitIds = toIds.split(",");
      List<String> uniqueIds =
          Arrays.asList(splitIds).stream()
              .map(StringUtils::trimWhitespace)
              .filter(StringUtils::hasText)
              .distinct()
              .collect(Collectors.toList());
      model.addAttribute("toIds", String.join(",", uniqueIds));
    }
    
    return "compose-page";
  }
}

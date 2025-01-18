package com.aps.echo.controller.echoController;

import com.aps.echo.folders.Folder;
import com.aps.echo.folders.FolderRepository;
import com.aps.echo.folders.FolderService;
import com.aps.echo.message.MessageService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ComposeController {
  @Autowired FolderRepository folderRepository;

  @Autowired FolderService folderService;

  @Autowired MessageService messageService;

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
    // Get unique Ids
    List<String> uniqueIds = getUniqueIds(toIds);
    model.addAttribute("toIds", String.join(",", uniqueIds));

    return "compose-page";
  }

  private static List<String> getUniqueIds(String toIds) {
    if (!StringUtils.hasText(toIds)) {
      return new ArrayList<String>();
    }
    String[] splitIds = toIds.split(",");
    List<String> uniqueIds =
        Arrays.asList(splitIds).stream()
            .map(StringUtils::trimWhitespace)
            .filter(StringUtils::hasText)
            .distinct()
            .collect(Collectors.toList());
    return uniqueIds;
  }

  @PostMapping("/sendMessage")
  public ModelAndView sendMessage(
      @RequestBody MultiValueMap<String, String> formData,
      @AuthenticationPrincipal OAuth2User principal) {
    if (principal == null || !StringUtils.hasText(principal.getAttribute("name"))) {
      return new ModelAndView("redirect:/");
    }
    String from = principal.getAttribute("login");
    List<String> toIds = getUniqueIds(formData.getFirst("toIds"));
    String subject = formData.getFirst("subject");
    String body = formData.getFirst("body");
    messageService.sendMessage(from, toIds, subject, body);
    Arrays.asList()
    return new ModelAndView("redirect:/");
  }
}

package com.aps.echo.folders;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class FolderService {
    public List<Folder> fetchDefaultFolders(String id){
        return Arrays.asList(
        new Folder(id, "Inbox", "Pastel Blue"),
        new Folder(id, "Sent box", "Celadon"),
        new Folder(id, "Urgent", "Burnt Sienna")
        );


    }
}

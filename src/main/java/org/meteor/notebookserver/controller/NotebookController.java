package org.meteor.notebookserver.controller;

import org.meteor.notebookserver.model.Notebook;
import org.meteor.notebookserver.model.RespBean;
import org.meteor.notebookserver.model.RespPageBean;
import org.meteor.notebookserver.service.NotebookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/notebook")
public class NotebookController {

    @Autowired
    private NotebookServiceImpl notebookService;

    @PostMapping("/update")
    public RespBean updateNotebooks(List<Notebook> notebooks,Long userId){
        return notebookService.updateNotebook(notebooks,userId);
    }

    @GetMapping("/download")
    public RespPageBean downloadNotebooks(Long userId){
        return notebookService.downloadNotebook(userId);
    }
}

package com.miliancode.carblog.controllers;

import com.miliancode.carblog.models.News;
import com.miliancode.carblog.repo.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class NewsController {

    @Autowired
    private NewsRepository newsRepository;

    @GetMapping("/")
    public String blogMain(Model model){
        Iterable<News> news = newsRepository.findAll();
        model.addAttribute("news", news);
        return "home";
    }

    @GetMapping("/news/add")
    public String newsAdd(){
        return "news-add";
    }

    @PostMapping("/news/add")
    public String blogPostAdd(@RequestParam String title,
                              @RequestParam String anons,
                              @RequestParam String fullText,
                              @RequestParam String imageSrc){
        News news = new News(title, anons, fullText, imageSrc);
        newsRepository.save(news);
        return "redirect:/";
    }

    @GetMapping("/news/{id}")
    public String newsDetails(@PathVariable(value = "id") Long id, Model model){
        if (!newsRepository.existsById(id)) {
            return "redirect:/";
        }

        Optional<News> news = newsRepository.findById(id);
        ArrayList<News> res = new ArrayList<>();
        news.ifPresent(res::add);
        model.addAttribute("news", res);

        return "news-details";
    }

    @PostMapping("/news/{id}/remove")
    public String newsDelete(@PathVariable(value = "id") Long id){
        News news = newsRepository.findById(id).orElseThrow();
        newsRepository.delete(news);
        return "redirect:/";
    }

}

package com.miliancode.carblog.controlers;

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

    //Main page with all news
    @GetMapping("/")
    public String blogMain(Model model){
        Iterable<News> news = newsRepository.findAll();
        model.addAttribute("news", news);
        return "home";
    }

    //Form to add news
    @GetMapping("/news/add")
    public String newsAdd(Model model){
        return "news-add";
    }

    //Adding news to database
    @PostMapping("/news/add")
    public String blogPostAdd(@RequestParam String title,
                              @RequestParam String anons,
                              @RequestParam String fullText,
                              @RequestParam String imageSrc,
                              Model model){
        News news = new News(title, anons, fullText, imageSrc);
        newsRepository.save(news);
        return "redirect:/";
    }

    //Info-page about news
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

    //Deleting news from database
    @PostMapping("/news/{id}/remove")
    public String newsDelete(@PathVariable(value = "id") Long id,
                                 Model model){
        News news = newsRepository.findById(id).orElseThrow();
        newsRepository.delete(news);
        return "redirect:/";
    }

}

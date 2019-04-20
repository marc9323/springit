package com.luv2code.springit.controller;

import com.luv2code.springit.domain.Link;
import com.luv2code.springit.respository.LinkRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class LinkController {

    private static final Logger logger = LoggerFactory.getLogger(LinkController.class);

    private LinkRepository linkRepository;

    public LinkController(LinkRepository linkRepository){
        this.linkRepository = linkRepository;
    }

    @GetMapping("/")
    public String list(Model model) {
        model.addAttribute("links",linkRepository.findAll());
        return "link/list";
    }

    @GetMapping("/link/{id}")
    public String read(@PathVariable Long id, Model model){
        Optional<Link> link = linkRepository.findById(id);
        if(link.isPresent()){
            model.addAttribute("link", link.get());
            model.addAttribute("success", model.containsAttribute("success"));
            return "link/view";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/link/submit")
    public String newLinkForm(Model model) {
        model.addAttribute("link",new Link());
        return "link/submit";
    }

    @PostMapping("/link/submit")
    public String createLink(@Valid Link link, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()){
            logger.info("Validation errors were found while submitting a new link");
            model.addAttribute("link", link);
            return "link/submit";
        } else {
            // save our link
            linkRepository.save(link);
            logger.info("new link was saved successfully!");
            redirectAttributes.addAttribute("id", link.getId()).addFlashAttribute("success", true);
            // flash attributes are attributes that will only live on the next template that you visit.  So you get it on
            // next page but if you were to reload that page its no longer there.
            return "redirect:/link/{id}";
        }

    }
}

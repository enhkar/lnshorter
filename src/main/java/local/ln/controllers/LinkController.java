package local.ln.controllers;

import local.ln.entities.LinkEntity;
import local.ln.entities.UserEntity;
import local.ln.services.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;


@Controller
@RequestMapping("/")
public class LinkController {

    private LinkService linkService;

    @Autowired
    public void setLinkService(LinkService linkService) {
        this.linkService = linkService;
    }

    @GetMapping("")
    public String getForm() {
        return "simple-link-form";
    }

    @PostMapping("")
    public String saveLink(Model model, @RequestParam(name = "url") String url, @AuthenticationPrincipal UserEntity user) {
        String suffix = linkService.add(url, user);
        model.addAttribute("suffix", suffix);
        return "shorted";
    }


    @GetMapping("{path}")
    public ModelAndView redirect(@PathVariable(name = "path") String path) {
        LinkEntity link = linkService.getLinkBySuffix(path);
        if (link == null || !link.isEnabled()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "link not found"
            );
        }
        RedirectView redirectView = new RedirectView(link.getRedirectUrl());
        redirectView.setStatusCode(HttpStatus.FOUND);
        return new ModelAndView(redirectView);
    }

    @PostMapping("/links/{id}/switch")
    public String switchState(Model model, @PathVariable(name = "id") Long id, @RequestParam(name = "enable") Boolean enable, @AuthenticationPrincipal UserEntity user) {
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        linkService.switchLinkEnable(id, enable);
        model.addAttribute("links", linkService.getLinksByUser(user));
        return "redirect:/user/links";
    }
}

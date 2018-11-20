package com.agent.controllers;

import com.agent.entities.TradePoint;
import com.agent.entities.User;
import com.agent.services.CompanyService;
import com.agent.services.TradePointService;
import com.agent.services.UserService;
import com.agent.util.PageWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/trade_points")
public class TradePointController
{
    @Autowired
    private TradePointService tradePointService;

    @Autowired
    private UserService userService;

    @Autowired
    private CompanyService companyService;

    /**
     * List all trade points
     */

    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model, Pageable pageable){

        Page<TradePoint> tradePage = tradePointService.listAllByPage(pageable);

        PageWrapper<TradePoint> page = new PageWrapper<>(tradePage, "/trade_points");
        model.addAttribute("trade_points", page.getContent());
        model.addAttribute("page", page);

        return "tradepoints";
    }

    /**
     * Edit Trade Point.
     */
    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model, Principal principal) {

        User currentUser = userService.getUserByUsername(principal.getName());
        TradePoint tradePoint = tradePointService.getTradePointById(id);

        if(currentUser.getRole().getName().equals("ROLE_USER")){
            if(tradePoint.getUser() == null || !tradePoint.getUser().getId().equals(currentUser.getId())){
                return "errors/403";
            }
        }

        model.addAttribute("trade_point", tradePoint);
        model.addAttribute("users", userService.listAgents());
        model.addAttribute("companies", companyService.listAll());
        return "tradepointform";
    }

    /**
     * New Trade Point.
     */
    @RequestMapping("/new")
    public String newTradePoint(Model model) {

        model.addAttribute("trade_point", new TradePoint());
        model.addAttribute("users", userService.listAgents());
        model.addAttribute("companies", companyService.listAll());
        return "tradepointform";
    }

    /**
     * Save Trade point to database.
     */
    @RequestMapping(value = "/trade_point", method = RequestMethod.POST)
    public String save(@Valid TradePoint tradePoint, BindingResult bindingResult, Model model, Principal principal)
    {
            if(validate(tradePoint, bindingResult))
            {
                model.addAttribute("trade_point", tradePoint);
                model.addAttribute("users", userService.listAgents());
                model.addAttribute("companies", companyService.listAll());
                return "tradepointform";
            }

            User currentUser = userService.getUserByUsername(principal.getName());
            if(currentUser.getRole().getName().equals("ROLE_USER")){
                tradePoint.setUser(currentUser);
            }

            tradePointService.saveTradePoint(tradePoint);

        return "redirect:/trade_points";
    }

    /**
     * Delete trade point by its id.
     */
    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, Principal principal) {

        TradePoint tradePoint = tradePointService.getTradePointById(id);
        User currentUser = userService.getUserByUsername(principal.getName());

        if(currentUser.getRole().getName().equals("ROLE_USER")){
            if(tradePoint.getUser() == null || !tradePoint.getUser().getId().equals(currentUser.getId())){
                return "errors/403";
            }
        }

        tradePointService.deleteTradePoint(tradePoint);

        return "redirect:/trade_points";
    }

    private boolean validate(@Valid TradePoint tradePoint, BindingResult bindingResult) {

        boolean hasError = false;
        if (bindingResult.hasErrors()) {
            hasError = true;
        }
        else
        {

        }
        return hasError;
    }
}

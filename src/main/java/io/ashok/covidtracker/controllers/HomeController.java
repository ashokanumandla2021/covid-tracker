package io.ashok.covidtracker.controllers;

import io.ashok.covidtracker.services.CovidCasesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    CovidCasesService covidCasesService;

    @GetMapping("/index")
    public String getHompePage(Model model) {
        model.addAttribute("currentDayTotalCases", covidCasesService.getCurrentDayTotalCases());
        model.addAttribute("totalCasesIncreasePerDay", covidCasesService.getTotalCasesIncreasedPerDay());
        model.addAttribute("locationsStat", covidCasesService.getLocationsStat());
        return "index";
    }
}

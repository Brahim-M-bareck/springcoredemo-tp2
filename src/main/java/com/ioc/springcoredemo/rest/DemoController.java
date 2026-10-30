package com.ioc.springcoredemo.rest;

import com.ioc.springcoredemo.common.Coach;
import com.ioc.springcoredemo.common.RequestIdBean;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
public class DemoController {
    private final Coach myCoach;
    private final Coach myCoachForCheck;
    private final Coach anotherCoachForCheck;
    private final ObjectProvider<Coach> allCoaches;
    private final RequestIdBean requestIdBean;

    @Autowired
    public DemoController(
            @Qualifier("aquatic") Coach theCoach,
            @Qualifier("cricketCoach") Coach theCheckCoach,
            @Qualifier("cricketCoach") Coach theAnotherCheckCoach,
            ObjectProvider<Coach> theAllCoaches,
            RequestIdBean theRequestIdBean) {
        myCoach = theCoach;
        myCoachForCheck = theCheckCoach;
        anotherCoachForCheck = theAnotherCheckCoach;
        allCoaches = theAllCoaches;
        requestIdBean = theRequestIdBean;
    }

    @GetMapping("/dailyworkout")
    public String getDailyWorkout() {
        return myCoach.getDailyWorkout();
    }

    @GetMapping("/check")
    public String check() {
        return "Comparing beans: myCoach == anotherCoach, " + (myCoachForCheck == anotherCoachForCheck);
    }

    @GetMapping("/allworkouts")
    public String allWorkouts() {
        return allCoaches.orderedStream()
                .map(Coach::getDailyWorkout)
                .collect(Collectors.joining("<br>"));
    }

    @GetMapping("/request-id")
    public String requestId() {
        return "Request id: " + requestIdBean.getId();
    }
}

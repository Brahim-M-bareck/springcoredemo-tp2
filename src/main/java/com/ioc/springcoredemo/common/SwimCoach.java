package com.ioc.springcoredemo.common;

public class SwimCoach implements Coach {
    public SwimCoach() {
        System.out.println("In constructor: " + getClass().getSimpleName());
    }

    public void customInit() {
        System.out.println("In customInit()");
    }

    public void customDestroy() {
        System.out.println("In customDestroy()");
    }

    @Override
    public String getDailyWorkout() {
        return "Swim 1000 meters as a warm up";
    }
}

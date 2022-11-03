package kopo.hightech.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class HomeController {


    @Value("${env.text}")
    private String env;

    @RequestMapping("/")
    public String HOME(){
        //System.out.println("sensorService = " + sensorService);
        return "hello";
    }

    @RequestMapping("/boot")
    public String Boot(){
        //System.out.println("sensorService = " + sensorService);
        return "yun";
    }

    @RequestMapping("/login")
    public String login(@RequestParam("id") int userid, ModelMap model){

        return "hello";
    }

    @RequestMapping("/requestParamTest")
    public String requestParamTest(@RequestParam(name = "a", required=false, defaultValue = "0") int a,
                                   @RequestParam("b") String b,
                                   @RequestParam(value= "c", defaultValue = "true") boolean c) {
        System.out.println("a = " + a);
        System.out.println("b = " + b);
        System.out.println("c = " + c);
        return "hello";
    }

    @RequestMapping("/pathVariableTest/{a}/{b}/{c}")
    public String pathVariableTest(@PathVariable(value="a") String a, @PathVariable String b, @PathVariable int c) {
        System.out.println("a = " + a);
        System.out.println("b = " + b);
        System.out.println("c = " + c);
        return "hello";
    }

    @RequestMapping("/requestParamMapTest")
    public String requestParamMapTest(@RequestParam Map<String, String> map) {
        for(Map.Entry entry: map.entrySet()) {
            System.out.println(entry.getKey() + "=" + entry.getValue());
        }
        return "hello";
    }

    @RequestMapping(value = "/requestMappingGetTest",
            method = RequestMethod.GET,
            params ="test=true")
    public String requestMappingGetTest(Model model) {
        model.addAttribute("textFromController", "World");
        return "hello";
    }

    @RequestMapping(value = "/requestMappingPostTest",
            method = RequestMethod.POST)
    @ResponseBody
    public String requestMappingPostTest() {
        return "hello";
    }


}

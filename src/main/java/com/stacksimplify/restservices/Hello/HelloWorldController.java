package com.stacksimplify.restservices.Hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

//Controller
@RestController
public class HelloWorldController {

    //@RequestMapping(method = RequestMethod.GET, path = "/helloworld")
    @GetMapping("helloworld/1")
    public String helloWorld(){

        return "HelloWorld";
    }

    @GetMapping("/helloworldbean")
    public UserDetails helloWorldBean(){

        return new UserDetails("Kethan", "Gaddam", "Khammam");
    }
}

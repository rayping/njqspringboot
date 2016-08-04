package org.njqspringboot.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWordController {
	
	@RequestMapping("/")
	public @ResponseBody String helloWord(){
		return "hello word!";
	}
	
	@ResponseBody
    @RequestMapping("/json")
    public Map<String, Object> jsonTest(){
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("msg", "测试JSON返回");
        result.put("success", true);
        return result;
    }
	
}

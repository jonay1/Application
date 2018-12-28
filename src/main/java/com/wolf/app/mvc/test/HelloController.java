package com.wolf.app.mvc.test;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	@RequestMapping("/hello")
	public Object hello() {
		String s=null;//"你好"; 
		return s.length();
	}
}

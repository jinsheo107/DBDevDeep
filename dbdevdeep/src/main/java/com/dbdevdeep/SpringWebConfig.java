package com.dbdevdeep;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SpringWebConfig implements WebMvcConfigurer{
	
	private String mapping  = "/UploadImg/**";
	private String location = "file:///C:/dbdevdeep/";

	private String imageMapping = "/signatures/**";
    private String imageLocation = "file:///C:/dbdevdeep/empSign/";
    
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler(mapping).addResourceLocations(location);
		registry.addResourceHandler(imageMapping).addResourceLocations(imageLocation);
	}
}
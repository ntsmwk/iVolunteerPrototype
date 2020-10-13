//package at.jku.cis.iVolunteer.core.config.web;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.MediaType;
//import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
//import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.SerializationFeature;
//
//@Configuration
//public class RequestMappingConfiguration extends RequestMappingHandlerAdapter {
//
//	@Bean
//	public RequestMappingHandlerAdapter requestHandler() {
//		RequestMappingHandlerAdapter adapter = new RequestMappingHandlerAdapter();
//		ObjectMapper mapper = new ObjectMapper();
//		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
//		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter(mapper);
//		List<MediaType> mediaTypeList = new ArrayList<>();
//		mediaTypeList.add(MediaType.APPLICATION_JSON);
//		mediaTypeList.add(MediaType.TEXT_HTML);
//		converter.setSupportedMediaTypes(mediaTypeList);
//		adapter.getMessageConverters().add(converter);
//		return adapter;
//	}
//}

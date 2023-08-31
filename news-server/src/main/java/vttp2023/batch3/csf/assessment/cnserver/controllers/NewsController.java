package vttp2023.batch3.csf.assessment.cnserver.controllers;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import vttp2023.batch3.csf.assessment.cnserver.models.TagCount;
import vttp2023.batch3.csf.assessment.cnserver.services.NewsService;

@RestController
@RequestMapping(path = "/api")
public class NewsController {

	private NewsService newsSvc;

	public NewsController(NewsService newsSvc) {
		this.newsSvc = newsSvc;
	}

	// TODO: Task 1
	@PostMapping(path = "/postNews", 
			consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
		 	produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> postNews(
			@RequestPart String title, 
			@RequestPart MultipartFile photo, 
			@RequestPart String description,
			@RequestPart String tags) 
	{
		System.out.println("\n\nIn Controller\n\n");
		try {
			JsonArray tagsArr = Json.createReader(new StringReader(tags))
					.readArray();
			
			List<String> tagsList = new ArrayList<>();
			tagsArr.stream().forEach(v -> tagsList.add(v.toString()));
		
			String newsId = newsSvc.postNews(
					title, 
					photo, 
					description, 
					tagsList);

			return ResponseEntity.ok(
					Json.createObjectBuilder()
							.add("newsId", newsId)
							.build().toString());
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body(
					Json.createObjectBuilder()
							.add("error", "post news operation failed")
							.build().toString());
		}
	}

	// TODO: Task 2
	@GetMapping(path = "/getTags", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getTags(@RequestParam int duration) {

		System.out.println(duration);
		List<TagCount> tagCountList = newsSvc.getTags(duration);
		System.out.println(tagCountList);
		JsonArrayBuilder arr = Json.createArrayBuilder();

		tagCountList.stream().forEach(v -> arr.add(
				Json.createObjectBuilder()
						.add("tag", v.tag())
						.add("count", v.count())
						.build()
		));
		
		return ResponseEntity.ok(arr.build().toString());
	}

	// TODO: Task 3

}

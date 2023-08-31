package vttp2023.batch3.csf.assessment.cnserver.services;

import java.util.LinkedList;
import java.util.List;

import org.bson.Document;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.json.JsonObject;
import vttp2023.batch3.csf.assessment.cnserver.models.News;
import vttp2023.batch3.csf.assessment.cnserver.models.TagCount;
import vttp2023.batch3.csf.assessment.cnserver.models.Util;
import vttp2023.batch3.csf.assessment.cnserver.repositories.ImageRepository;
import vttp2023.batch3.csf.assessment.cnserver.repositories.NewsRepository;

@Service
public class NewsService {

	private ImageRepository imageRepo;
	private NewsRepository newsRepo;

	public NewsService(
			ImageRepository imageRepo,
			NewsRepository newsRepo) {
		this.imageRepo = imageRepo;
		this.newsRepo = newsRepo;
	}
	
	// TODO: Task 1
	// Do not change the method name and the return type
	// You may add any number of parameters
	// Returns the news id
	public String postNews(String title, 
			MultipartFile photo, 
			String description,
			List<String> tags) 
	{
		String s3Id = imageRepo.saveImage(photo);
		JsonObject obj = Util.requestPartToJson(
				title, 
				photo, 
				description, 
				tags,
				imageRepo.getUrl(s3Id));

		return newsRepo.insertNews(Document.parse(obj.toString()));	
	}
	 
	// TODO: Task 2
	// Do not change the method name and the return type
	// You may add any number of parameters
	// Returns a list of tags and their associated count
	public List<TagCount> getTags(int duration) {
		long tagDateCheck = System.currentTimeMillis() - duration;
		System.out.println("\n\n" + tagDateCheck + "\n\n");

		List<Document> docs = newsRepo.findTags(tagDateCheck);
		docs.forEach(v -> System.out.println(v.toString()));

		List<TagCount> tagCountList = new LinkedList<>();

		docs.stream().forEach(v -> tagCountList.add(new TagCount(
				v.getString("_id"), v.getInteger("news_count")))
		);

		tagCountList.forEach(v -> System.out.println(v));

		return tagCountList;
	}

	// TODO: Task 3
	// Do not change the method name and the return type
	// You may add any number of parameters
	// Returns a list of news
	public List<News> getNewsByTag(/* Any number of parameters */) {
		return new LinkedList<>();
	}
	
}

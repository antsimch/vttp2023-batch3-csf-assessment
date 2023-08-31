package vttp2023.batch3.csf.assessment.cnserver.models;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;

public class Util {

    public static JsonObject requestPartToJson(
            String title, 
			MultipartFile photo, 
			String description,
			List<String> tags,
            String imageUrl)
    {
        System.out.println(tags);
        if (!tags.isEmpty()) {
            JsonArrayBuilder arr = Json.createArrayBuilder();
            tags.stream().forEach(v -> arr.add(v.toString()));

            JsonObject obj = Json.createObjectBuilder()
                    .add("postDate", System.currentTimeMillis())
                    .add("title", title)
                    .add("description", description)
                    .add("image", imageUrl)
                    .add("tags", arr)
                    .build();

            System.out.println(obj.toString());

            return obj;
        } else {
            return Json.createObjectBuilder()
                    .add("postDate", System.currentTimeMillis())
                    .add("title", title)
                    .add("description", description)
                    .add("image", imageUrl)
                    .build();
        }
    }
}

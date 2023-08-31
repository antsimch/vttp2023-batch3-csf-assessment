package vttp2023.batch3.csf.assessment.cnserver.repositories;

import java.util.List;

import org.bson.Document;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.LimitOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import com.mongodb.internal.operation.AggregateOperation;

@Repository
public class NewsRepository {

	private MongoTemplate template;

    public NewsRepository(MongoTemplate template) {
        this.template = template;
    }

	// TODO: Task 1 
	// Write the native Mongo query in the comment above the method
	/*
	db.news.insert({
	  	title: "title"
	  	description: "description"
	  	image: "imageUrl"
	  	tags: ["tag1", "tag2"]
	})
	 */
	public String insertNews(Document document) {
        Document newDoc = template.insert(document, "news");
        System.out.println("\n\n" + "Object Id for document >>>" + 
                newDoc.get("_id") + "\n\n");
		return newDoc.get("_id").toString();
    }

	// TODO: Task 2 
	// Write the native Mongo query in the comment above the method
	/*
	db.news.aggregate([
	{
		$match: {
		    $and: [
		        {"postDate": {$gte: 1593457278572}},
		        {"tags": {$exists: true}}]
		}
	},
	{
	    $unwind: "$tags"
	},
	{
	    $group: {
				_id: "$tags",
				titles: {
					$push: "$title"
				},
				count: {
					$sum: 1
				}
			}
	},
	{
	    $project: {
	        _id: 1,
	        count: -1
	    }
	},
	{
	    $sort: {
	        "count": -1,
	        "_id": 1
	    }
	},
	{
	    $limit: 10
	}
	])
	*/
	public List<Document> findTags(long tagDateCheck) {
		Criteria criteria = new Criteria();
		
		MatchOperation matchOp = Aggregation.match(
			criteria.andOperator(
				Criteria.where("postDate").gte(tagDateCheck),
				Criteria.where("tags").exists(true)
			)
		);

		UnwindOperation unwindTags = Aggregation.unwind("tags");
	
		GroupOperation groupByTags = Aggregation.group("tags")
				.push("title").as("titles")
				.count().as("count");

		ProjectionOperation projectTagCount = Aggregation.project(
				"_id", "count");

		SortOperation sortOp = Aggregation.sort(
				Sort.by(Direction.DESC, "count")
				.and(Sort.by(Direction.ASC, "_id")));

		// SortOperation sortByTagName = Aggregation.sort(
		// 		Sort.by(Direction.ASC, "_id"));

		LimitOperation limitOp = Aggregation.limit(10);
		
		Aggregation pipeline = Aggregation.newAggregation(
				matchOp, 
				unwindTags, 
				groupByTags, 
				projectTagCount, 
				sortOp,
				limitOp);	
		
		return template.aggregate(
				pipeline, 
				"news", 
				Document.class).getMappedResults();
	}


	// TODO: Task 3
	// Write the native Mongo query in the comment above the method


}

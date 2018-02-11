import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class Principal {
	
	public static void main(String[] args) {
		MongoClient mongoClient = new MongoClient();
		MongoDatabase mongoDatabase = mongoClient.getDatabase("test");
		MongoCollection<Document> alunos = mongoDatabase.getCollection("alunos");
		Document aluno = alunos.find().first();
		System.out.println(aluno);
		
		
		/*Document joao = new Document("nome","Joao")
				.append("data_nascimento", new Date(2003,10,10))
				.append("curso", new Document("nome","História"))
				.append("notas", Arrays.asList(10,9,8))
				.append("habilidades", Arrays.asList(
						new Document()
							.append("nome", "Inglês")
							.append("nível", "Básico"),
						new Document()
							.append("nome", "Espanhol")
							.append("nível", "Básico")
						
				));
		alunos.insertOne(joao);
		System.out.println(joao);*/
		//alunos.updateOne(Filters.eq("nome","Joao"), new Document("$set", new Document("nome","Joao Silva")));
		alunos.deleteOne(Filters.eq("nome","Joao Silva"));
		mongoClient.close();
	}

}




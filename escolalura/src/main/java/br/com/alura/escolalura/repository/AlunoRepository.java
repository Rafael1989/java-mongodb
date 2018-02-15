package br.com.alura.escolalura.repository;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import br.com.alura.escolalura.codec.AlunoCodec;
import br.com.alura.escolalura.model.Aluno;

@Repository
public class AlunoRepository {
	
	private MongoClient mongoClient;
	
	private MongoDatabase mongoDatabase;
	
	private void criaConexao() {
		Codec<Document> codec = MongoClient.getDefaultCodecRegistry().get(Document.class);
		AlunoCodec alunoCodec = new AlunoCodec(codec);
		CodecRegistry registro = CodecRegistries.fromRegistries(MongoClient.getDefaultCodecRegistry(),CodecRegistries.fromCodecs(alunoCodec));
		MongoClientOptions options = MongoClientOptions.builder().codecRegistry(registro).build();
		mongoClient = new MongoClient("localhost:27017",options);
		mongoDatabase = mongoClient.getDatabase("test");
	}
	
	public void salvar(Aluno aluno) {
		criaConexao();
		MongoCollection<Aluno> alunos = mongoDatabase.getCollection("alunos",Aluno.class);
		if(aluno.getId() == null) {
			alunos.insertOne(aluno);
		}else {
			alunos.updateOne(Filters.eq("_id",aluno.getId()),new Document("$set",aluno));
		}
		
		mongoClient.close();
	}
	
	public List<Aluno> obterTodosAlunos(){
		criaConexao();
		MongoCollection<Aluno> alunos = mongoDatabase.getCollection("alunos",Aluno.class);
		MongoCursor<Aluno> resultado = alunos.find().iterator();
		List<Aluno> alunosEncontrados = new ArrayList<>();
		while(resultado.hasNext()) {
			Aluno aluno = resultado.next();
			alunosEncontrados.add(aluno);
		}
		return alunosEncontrados;
	}
	
	public Aluno buscaAlunoPorId(String id) {
		criaConexao();
		MongoCollection<Aluno> alunos = mongoDatabase.getCollection("alunos",Aluno.class);
		Aluno aluno = alunos.find(Filters.eq("_id",new ObjectId(id))).first();
		return aluno;
	}

}

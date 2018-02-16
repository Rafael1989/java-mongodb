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
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.geojson.Point;
import com.mongodb.client.model.geojson.Position;

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
		List<Aluno> alunosEncontrados = popularAlunos(resultado);
		mongoClient.close();
		return alunosEncontrados;
	}
	
	public Aluno buscaAlunoPorId(String id) {
		criaConexao();
		MongoCollection<Aluno> alunos = mongoDatabase.getCollection("alunos",Aluno.class);
		Aluno aluno = alunos.find(Filters.eq("_id",new ObjectId(id))).first();
		mongoClient.close();
		return aluno;
	}

	public List<Aluno> buscaAlunoPorNome(String nome) {
		criaConexao();
		MongoCollection<Aluno> alunos = mongoDatabase.getCollection("alunos",Aluno.class);
		MongoCursor<Aluno> resultado = alunos.find(Filters.eq("nome", nome)).iterator();
		List<Aluno> alunosEncontrados = popularAlunos(resultado);
		mongoClient.close();
		return alunosEncontrados;
	}

	private List<Aluno> popularAlunos(MongoCursor<Aluno> resultado) {
		List<Aluno> alunosEncontrados = new ArrayList<>();
		while(resultado.hasNext()) {
			Aluno aluno = resultado.next();
			alunosEncontrados.add(aluno);
		}
		return alunosEncontrados;
	}

	public List<Aluno> pesquisarPor(String classificacao, double nota) {
		criaConexao();
		MongoCollection<Aluno> alunosCollection = mongoDatabase.getCollection("alunos",Aluno.class);
		MongoCursor<Aluno> resultados = null;
		
		if(classificacao.equals("reprovados")) {
			resultados = alunosCollection.find(Filters.lt("notas", nota)).iterator();
		}else if(classificacao.equals("aprovados")) {
			resultados = alunosCollection.find(Filters.gte("notas", nota)).iterator();
		}
		
		List<Aluno> alunos = popularAlunos(resultados);
		mongoClient.close();
		return alunos;
	}

	public List<Aluno> pesquisarPorGeolocalizacao(Aluno aluno) {
		criaConexao();
		MongoCollection<Aluno> alunoCollection = mongoDatabase.getCollection("alunos",Aluno.class);
		alunoCollection.createIndex(Indexes.geo2dsphere("contato"));
		List<Double> coordinates = aluno.getContato().getCoordinates();
		Point pontoReferencia = new Point(new Position(coordinates.get(0),coordinates.get(1)));
		MongoCursor<Aluno> resultados = alunoCollection.find(Filters.nearSphere("contato", pontoReferencia, 2000.0, 0.0)).limit(2).skip(1).iterator();
		List<Aluno> alunos = popularAlunos(resultados);
		mongoClient.close();
		return alunos;
	}

}

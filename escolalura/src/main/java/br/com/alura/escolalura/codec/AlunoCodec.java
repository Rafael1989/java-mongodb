package br.com.alura.escolalura.codec;

import java.util.Calendar;
import java.util.Date;

import org.bson.BsonReader;
import org.bson.BsonString;
import org.bson.BsonValue;
import org.bson.BsonWriter;
import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.CollectibleCodec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.types.ObjectId;

import br.com.alura.escolalura.model.Aluno;
import br.com.alura.escolalura.model.Curso;

public class AlunoCodec implements CollectibleCodec<Aluno>{
	
	private Codec<Document> codec;

	public AlunoCodec(Codec<Document> codec) {
		this.codec = codec;
	}

	@Override
	public void encode(BsonWriter writer, Aluno aluno, EncoderContext encoder) {
		ObjectId id = aluno.getId();
		String nome = aluno.getNome();
		Date dataNascimento = aluno.getDataNascimento().getTime();
		Curso curso = aluno.getCurso();
		
		Document document = new Document();
		document.put("_id", id);
		document.put("nome", nome);
		document.put("data_nascimento", dataNascimento);
		document.put("curso", new Document("nome",curso.getNome())); 
		
		codec.encode(writer, document, encoder);
	}

	@Override
	public Class<Aluno> getEncoderClass() {
		return Aluno.class;
	}

	@Override
	public Aluno decode(BsonReader reader, DecoderContext decoder) {
		Document document = codec.decode(reader, decoder);
		Aluno aluno = new Aluno();
		aluno.setId(document.getObjectId("_id"));
		aluno.setNome(document.getString("nome"));
		Date date = document.getDate("data_nascimento");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		aluno.setDataNascimento(calendar);
		Document curso = (Document) document.get("curso");
		if(curso != null) {
			String nomeCurso = curso.getString("nome");
			aluno.setCurso(new Curso(nomeCurso));
		}
		return aluno;
		
	}

	@Override
	public boolean documentHasId(Aluno aluno) {
		return aluno == null;
	}

	@Override
	public Aluno generateIdIfAbsentFromDocument(Aluno aluno) {
		return documentHasId(aluno) ? aluno.criaId():aluno;
	}

	@Override
	public BsonValue getDocumentId(Aluno aluno) {
		if(!documentHasId(aluno)) {
			throw new IllegalStateException("Esse document não tem id");
		}
		return new BsonString(aluno.getId().toHexString());
	}
	

}

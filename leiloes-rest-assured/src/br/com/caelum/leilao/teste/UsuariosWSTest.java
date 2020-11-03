package br.com.caelum.leilao.teste;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.path.xml.XmlPath;

import br.com.caelum.leilao.modelo.Usuario;

public class UsuariosWSTest {
	
	private Usuario mauricio;
	private Usuario guilherme;
	private Object usuario1;
	private Object usuario2;
	
	@Before
	public void setUp() {
		mauricio = new Usuario(1L,"Mauricio Aniche","mauricio.aniche@caelum.com.br");
		guilherme = new Usuario(2L,"Guilherme Silveira","guilherme.silveira@caelum.com.br");
	}
	
	@Test
	public void deveRetornarListaDeUsuarios() {
		XmlPath path = given().header("accept","application/xml")
				.get("/usuarios?_format=xml").andReturn().xmlPath();
		
        Usuario usuario1 = path.getObject("list.usuario[0]", Usuario.class);
        Usuario usuario2 = path.getObject("list.usuario[1]", Usuario.class);
		
		Usuario esperado1 = new Usuario(1L,"Mauricio Aniche","mauricio.aniche@caelum.com.br");
		Usuario esperado2 = new Usuario(2L,"Guilherme Silveira","guilherme.silveira@caelum.com.br");
		
		assertEquals(mauricio, usuario1);
		assertEquals(guilherme, usuario2);
	}
	
	@Test
	public void deveRetornarusuarioPeloId() {
JsonPath path = given()
		.header("Accept","application/json")
		.parameter("usuario.id", 1)
		.get("/usuarios/show")
		.andReturn()
		.jsonPath();
		
		Usuario usuario = path.getObject("usuario", Usuario.class);
		
		assertEquals(mauricio, usuario);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

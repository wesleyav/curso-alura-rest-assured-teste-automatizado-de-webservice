package br.com.caelum.leilao.teste;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.path.xml.XmlPath;

import br.com.caelum.leilao.modelo.Leilao;
import br.com.caelum.leilao.modelo.Usuario;

public class UsuariosWSTest {
	
	private Usuario mauricio;
	private Usuario guilherme;
		
	@Before
	public void setUp() {
		mauricio = new Usuario(1L,"Mauricio Aniche","mauricio.aniche@caelum.com.br");
		guilherme = new Usuario(2L,"Guilherme Silveira","guilherme.silveira@caelum.com.br");
		
//		RestAssured.baseURI = "http:/www.edereco-do-meu-ws.com.br";
//		RestAssured.port = 80;
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
	
	@Test
    public void deveRetornarUmLeilao() {
        JsonPath path = given()
                .parameter("leilao.id", 1)
                .header("Accept", "application/json")
                .get("/leiloes/show")
                .andReturn().jsonPath();

        Leilao leilao = path.getObject("leilao", Leilao.class);

        Usuario mauricio = new Usuario(1l, "Mauricio Aniche", "mauricio.aniche@caelum.com.br");
        Leilao esperado = new Leilao(1l, "Geladeira", 800.0, mauricio, false);

        assertEquals(esperado, leilao);
    }
	
	@Test
    public void deveRetornarQuantidadeDeLeiloes() {
        XmlPath path = given()
                .header("Accept", "application/xml")
                .get("/leiloes/total")
                .andReturn().xmlPath();

        int total = path.getInt("int");

        assertEquals(2, total);
    }
	
	@Test
	public void deveAdicionarUmUsuario() {
		Usuario joao = new Usuario("Joao da Silva", "joaodasilva.com");
		
		XmlPath path = given()
		.header("Accept", "application/xml")
		.contentType("application/xml")
		.body(joao)
		.expect()
		.statusCode(200)
		.when()
		.post("/usuarios")
		.andReturn()
		.xmlPath();
		
		Usuario resposta = path.getObject("usuario", Usuario.class);
		
		assertEquals("Joao da Silva", 	resposta.getNome());
		assertEquals("joaodasilva.com", resposta.getEmail());
		
		given()
		.contentType("application/xml").body(resposta)
		.expect().statusCode(200)
		.when().delete("/usuarios/deleta").andReturn().asString();
	}
	
	
}

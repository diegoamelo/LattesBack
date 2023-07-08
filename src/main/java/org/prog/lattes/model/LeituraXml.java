package org.prog.lattes.model;

import java.io.File;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
//import org.prog.lattes.service.AutorService;
import org.prog.lattes.service.PesquisadorService;
import org.prog.lattes.service.ProducaoService;
import org.springframework.stereotype.Component;

@Component  //Carregar o xml, ler o xml, alimentar um objeto de pesquisador
public class LeituraXml {

    private PesquisadorService pesquisadorService;
    private ProducaoService producaoService;
    //private AutorService autorService;

    public LeituraXml(PesquisadorService pesquisadorService, ProducaoService producaoService){// AutorService autorService){
        this.pesquisadorService = pesquisadorService;
        this.producaoService = producaoService;
        //this.autorService = autorService;
    }

    public void lerProducao(File file,  Node node, List<Producao> producaoList, TipoProducao tipoProducao, NodeList nodeList, String tagProducao, String tagNome, String tagAno, Pesquisador pesquisador){
        for (int i = 0; i < nodeList.getLength(); i++) {
            Producao producao = new Producao();

            //List<Autor> autoresList = new ArrayList<Autor>();

            node = nodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;

                String nome = element.getElementsByTagName(tagProducao).item(0).getAttributes().getNamedItem(tagNome).getNodeValue();
                Integer ano = Integer.valueOf(element.getElementsByTagName(tagProducao).item(0).getAttributes().getNamedItem(tagAno).getNodeValue());

                producao.setNome(nome);
                producao.setAno(ano);
                producao.setTipoProducao(tipoProducao.getNome());
                producao.setPesquisador(pesquisador);

                /*
                // Obtenha a lista de elementos "AUTORES"
                NodeList autoresNodeList = element.getElementsByTagName("AUTORES");

                
                // Percorra os elementos "AUTORES"
                for (int j = 0; j < autoresNodeList.getLength(); j++) {
                    Autor autor = new Autor();

                    Node autorNode = autoresNodeList.item(j);

                    if (autorNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element autorElement = (Element) autorNode;
                        
                        String nomeAutor = autorElement.getAttribute("NOME-COMPLETO-DO-AUTOR");
                        
                        Autor autorExisteNoBanco = autorService.buscarAutorNoBanco(nomeAutor);

                        Autor autorExisteNaLista = autorService.buscarAutorNaLista(nomeAutor, producaoList);

                        //Se autor não estiver cadastrado no banco nem na lista, então deve ser cadastrado
                        if((autorExisteNoBanco == null) && (autorExisteNaLista == null)){
                            autor.setNome(nomeAutor);
                            //autor.addProducao(producao);
                            producao.addAutor(autor);
                            
                            //Lendo os nomes para citação
                            String nomesCitacao = autorElement.getAttribute("NOME-PARA-CITACAO");

                            // Divida a string em nomes de citação separados por vírgula
                            String[] nomesCitacaoArray = nomesCitacao.split(";");

                            //List<Citacao> citacaoList = new ArrayList<Citacao>();

                            // Percorra os nomes de citação
                            for (String nomeCitacao : nomesCitacaoArray) {
                                Citacao citacao = new Citacao();

                                // Remova espaços em branco no início e no final do nome de citação
                                nomeCitacao = nomeCitacao.trim();

                                citacao.setNomeCitacao(nomeCitacao);
                                
                                //citacao.addAutor(autor);
                                autor.addCitacao(citacao);
                            }
                        }
                        //Não preciso cadastrar o autor nem seus nomes de citação. Só precisamos descobrir onde autor está persistindo
                        else if(autorExisteNoBanco != null){
                            //autorExisteNoBanco.addProducao(producao);
                            producao.addAutor(autorExisteNoBanco);
                        } 
                        else{
                            //autorExisteNaLista.addProducao(producao);
                            producao.addAutor(autorExisteNaLista);
                        }
                    }
                } 
                */               
                producaoList.add(producao);
            }
        }
    }

    public void convert(File file, Instituto instituto) {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();//DocumentBuilderFactory é usada para criar uma instância de DocumentBuilder
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();//o DocumentBuilder é usado para criar um novo documento e adicionar elementos a ele.     
            Document doc = documentBuilder.parse(file);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            StringWriter stringWriter = new StringWriter();
            
            transformer.transform(new DOMSource(doc), new StreamResult(stringWriter));
            
            doc.getDocumentElement().normalize();

            List<Producao> producaoList = new ArrayList<>();
            
            /*********************************************************************************************************/
            /*                                    Lendo os dados do pesquisador                                      */
            /*********************************************************************************************************/
            
            Pesquisador pesquisador = new Pesquisador();
            
            Node node = doc.getElementsByTagName("CURRICULO-VITAE").item(0);

            if(node.getNodeType() == Node.ELEMENT_NODE){
                Element element = (Element) node;
                
                String identificador = element.getAttribute("NUMERO-IDENTIFICADOR");

                pesquisador.setIdentificador(identificador);
            }

            node = doc.getElementsByTagName("DADOS-GERAIS").item(0);
            
            if(node.getNodeType() == Node.ELEMENT_NODE){
                Element element = (Element) node;
            
                String nome = element.getAttribute("NOME-COMPLETO");
                String ufNascimento = element.getAttribute("UF-NASCIMENTO");
            
                pesquisador.setNome(nome);
                pesquisador.setUfNascimento(ufNascimento);
            }

            pesquisador.setInstituto(instituto);

            /*********************************************************************************************************/
            /*                     Lendo todos os livros que o pesquisador trabalhou na produção                     */
            /*********************************************************************************************************/
            
            TipoProducao tipoProducao = TipoProducao.LIVRO;

            // Obtenha a lista de elementos "LIVRO-PUBLICADO-OU-ORGANIZADO"
            NodeList nodeList = doc.getElementsByTagName("LIVRO-PUBLICADO-OU-ORGANIZADO");
            
            String tagProducao = "DADOS-BASICOS-DO-LIVRO";
            String tagNome = "TITULO-DO-LIVRO";
            String tagAno = "ANO";

            // Percorra os elementos "LIVRO-PUBLICADO-OU-ORGANIZADO"
            lerProducao(file,  node, producaoList, tipoProducao, nodeList, tagProducao, tagNome, tagAno, pesquisador);

            /*********************************************************************************************************/
            /*                     Lendo todos os artigos que o pesquisador trabalhou na produção                    */
            /*********************************************************************************************************/
            
            tipoProducao = TipoProducao.ARTIGO;

            // Obtenha a lista de elementos "ARTIGO-PUBLICADO"
            nodeList = doc.getElementsByTagName("ARTIGO-PUBLICADO");
            
            tagProducao = "DADOS-BASICOS-DO-ARTIGO";
            tagNome = "TITULO-DO-ARTIGO";
            tagAno = "ANO-DO-ARTIGO";

            lerProducao(file,  node, producaoList, tipoProducao, nodeList, tagProducao, tagNome, tagAno, pesquisador);
            
            /*********************************************************************************************************/
            /*       Lendo todos os capitulos de livro publicados que o pesquisador trabalhou na produção            */
            /*********************************************************************************************************/
            
            tipoProducao = TipoProducao.CAPITULO_LIVRO;

            // Obtenha a lista de elementos "CAPITULO-DE-LIVRO-PUBLICADO"
            nodeList = doc.getElementsByTagName("CAPITULO-DE-LIVRO-PUBLICADO");
            
            tagProducao = "DADOS-BASICOS-DO-CAPITULO";
            tagNome = "TITULO-DO-CAPITULO-DO-LIVRO";
            tagAno = "ANO";

            lerProducao(file,  node, producaoList, tipoProducao, nodeList, tagProducao, tagNome, tagAno, pesquisador);

            /*********************************************************************************************************/
            /*               Lendo todas as orientações de mestrado que o pesquisador realizou                       */
            /*********************************************************************************************************/
            
            // tipoProducao = TipoProducao.ORIENTACOES_MESTRADO;

            // // Obtenha a lista de elementos "ORIENTACOES-CONCLUIDAS-PARA-MESTRADO"
            // nodeList = doc.getElementsByTagName("ORIENTACOES-CONCLUIDAS-PARA-MESTRADO");
            
            // tagProducao = "DADOS-BASICOS-DE-ORIENTACOES-CONCLUIDAS-PARA-MESTRADO";
            // tagNome = "TITULO";
            // tagAno = "ANO";
            
            // lerProducao(file,  node, producaoList, tipoProducao, nodeList, tagProducao, tagNome, tagAno, pesquisador);
            
            /*********************************************************************************************************/
            /*                  Lendo todas as orientações de TCC que o pesquisador realizou                         */
            /*********************************************************************************************************/
            
            // tipoProducao = TipoProducao.ORIENTACOES_TCC;

            // // Obtenha a lista de elementos "OUTRAS-ORIENTACOES-CONCLUIDAS"
            // nodeList = doc.getElementsByTagName("OUTRAS-ORIENTACOES-CONCLUIDAS");
            
            // tagProducao = "DADOS-BASICOS-DE-OUTRAS-ORIENTACOES-CONCLUIDAS";
            // tagNome = "TITULO";
            // tagAno = "ANO";
            
            // //lerProducao(file,  node, producaoList, tipoProducao, nodeList, tagProducao, tagNome, tagAno, pesquisador);

            /*********************************************************************************************************/
            /*                  Lendo todos os trabalhos em eventos que o pesquisador parcipou                       */
            /*********************************************************************************************************/
            
            tipoProducao = TipoProducao.TRABALHO_EVENTO;

            // Obtenha a lista de elementos "TRABALHO-EM-EVENTOS"
            nodeList = doc.getElementsByTagName("TRABALHO-EM-EVENTOS");
            
            tagProducao = "DADOS-BASICOS-DO-TRABALHO";
            tagNome = "TITULO-DO-TRABALHO";
            tagAno = "ANO-DO-TRABALHO";
            
            lerProducao(file,  node, producaoList, tipoProducao, nodeList, tagProducao, tagNome, tagAno, pesquisador);

            /*********************************************************************************************************/
            /*                                 Gravando toda a lista de produções                                    */
            /*********************************************************************************************************/

            pesquisadorService.save(pesquisador);

            producaoService.saveAll(producaoList);

        } catch (Exception e) {
           e.printStackTrace();
        }
    }
}
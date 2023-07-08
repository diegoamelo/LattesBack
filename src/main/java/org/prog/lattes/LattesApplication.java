package org.prog.lattes;

import org.prog.lattes.convert.ReadXML;
import org.prog.lattes.model.Instituto;
import org.prog.lattes.service.InstitutoService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class LattesApplication {

	public static void main(String[] args) {
		SpringApplication.run(LattesApplication.class, args);
		//ApplicationContext context = SpringApplication.run(LattesApplication.class, args);
		//carregarDados(context);
	}

	public static void carregarDados(ApplicationContext context){
			
		InstitutoService institutoService = context.getBean(InstitutoService.class);

        Instituto instituto1 = new Instituto("Faculdade Professor Miguel Ângelo da Silva Santos", "FEMASS");
        institutoService.gravar(instituto1);

		Instituto instituto2 = new Instituto("Universidade Federal do Rio de Janeiro", "UFRJ");
		institutoService.gravar(instituto2);
		
		Instituto instituto3 = new Instituto("Universidade Federal Fluminense", "UFF");
		institutoService.gravar(instituto3);
		
		Instituto instituto4 = new Instituto("Faculdade Católica Salesiana", "SALESIANA");
		institutoService.gravar(instituto4);
		
		Instituto instituto5 = new Instituto("Universidade Estácio de Sá", "Estácio");
		institutoService.gravar(instituto5);
		
		Instituto instituto6 = new Instituto("Teste", "TESTE");
		institutoService.gravar(instituto6);
		
		ReadXML readXML = context.getBean(ReadXML.class);
		
		try {
			readXML.start("0023809873085852", instituto1);
			readXML.start("0024160866319507", instituto1);
			readXML.start("0028876341054325", instituto1);
			readXML.start("0047810385809553", instituto1);
			readXML.start("0053636364868790", instituto1);
			readXML.start("0066576690749759", instituto1);
			readXML.start("0082487176176434", instituto2);
			readXML.start("0110662125645595", instituto2);
			readXML.start("0112621452737067", instituto2);
			readXML.start("0161902355523060", instituto2);
			readXML.start("0194631586754988", instituto2);
			readXML.start("0235080730138338", instituto2);
			readXML.start("0263660448893625", instituto3);
			readXML.start("0329773854976808", instituto3);
			readXML.start("0348923590713594", instituto3);
			readXML.start("0485361810192703", instituto3);
			readXML.start("0491984479926888", instituto3);
			readXML.start("0549723858731158", instituto3);
			readXML.start("0559800226477492", instituto4);
			readXML.start("0600549075776976", instituto4);
			readXML.start("0604237405440586", instituto4);
			readXML.start("0658455060876989", instituto4);
			readXML.start("0659726776097432", instituto4);
			readXML.start("0676650998291996", instituto4);
			readXML.start("0692400140993944", instituto5);
			readXML.start("0743793296062293", instituto5);
			readXML.start("0770145420421898", instituto5);
			readXML.start("0781779929562675", instituto5);
			readXML.start("0814717344017544", instituto5);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
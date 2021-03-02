package com.springbatch.arquivomultiplosformatos.reader;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.builder.MultiResourceItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import com.springbatch.arquivomultiplosformatos.dominio.Cliente;

@Configuration
public class MultiplosArquivosClienteTransacaoReaderConfig {
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
	@StepScope
	public MultiResourceItemReader<Cliente> multiplosArquivosClienteTransacaoReader(
			@Value("#{jobParameters['arquivoClientes']}") Resource[] arquivoClientes, 
			FlatFileItemReader leituraArquivoMultiplosFormatosReader){
		return new MultiResourceItemReaderBuilder<Cliente>()
			.name("multiplosArquivosClienteTransacaoReader")
			.resources(arquivoClientes)
			.delegate(new ArquivoClienteTransacaoReader(leituraArquivoMultiplosFormatosReader))
			.build();
	}
}

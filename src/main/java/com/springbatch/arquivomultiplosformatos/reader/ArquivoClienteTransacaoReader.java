package com.springbatch.arquivomultiplosformatos.reader;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import com.springbatch.arquivomultiplosformatos.dominio.Cliente;
import com.springbatch.arquivomultiplosformatos.dominio.Transacao;

public class ArquivoClienteTransacaoReader implements ItemStreamReader<Cliente> {

	// transação ou cliente
	private Object objetoAtual;
	
	private ItemStreamReader<Object> delegate;
	
	public ArquivoClienteTransacaoReader(ItemStreamReader<Object> delegate) {
		this.delegate = delegate;
	}

	@Override
	public void open(ExecutionContext executionContext) throws ItemStreamException {
		delegate.open(executionContext);

	}

	@Override
	public void update(ExecutionContext executionContext) throws ItemStreamException {
		delegate.update(executionContext);
	}

	@Override
	public void close() throws ItemStreamException {
		delegate.close();
	}

	@Override
	public Cliente read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		if (objetoAtual == null) {
			objetoAtual = delegate.read();// ler objeto
		}
		Cliente cliente = (Cliente) objetoAtual;
		objetoAtual = null;

		if (cliente != null) {
			while (isProximoObjetoTransacao()) {
				cliente.getTransacoes().add((Transacao) objetoAtual);
			}
		}
		return cliente;
	}

	private boolean isProximoObjetoTransacao() throws Exception {
		return peek() instanceof Transacao;
	}

	private Object peek() throws Exception {
		objetoAtual = delegate.read();//leitura do proximo item
		return objetoAtual;
	}

}

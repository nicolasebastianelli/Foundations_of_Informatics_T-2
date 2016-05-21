package doc.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.StringTokenizer;

import doc.model.Cliente;

public class TextClienteRepository extends TextRepository<Cliente> {

	public TextClienteRepository(String fileName) throws IOException,
			MalformedFileException {
		super(fileName);
	}

	@Override
	protected Cliente readElement(BufferedReader reader) throws MalformedFileException, IOException {
		String line = reader.readLine();
		StringTokenizer tokenizer = new StringTokenizer(line, "\t");
		if (tokenizer.countTokens() != 2)
			throw new MalformedFileException("Cliente: devono esserci 2 token.");
		String id = tokenizer.nextToken();
		String nome = tokenizer.nextToken();
		return new Cliente(id, nome);
	}

}

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class WebClientTestMockito {

	@Mock
	private ConnectionFactory factory;

	@Mock
	private InputStream mockStream;

	@Test
	public void testGetContentOk() throws Exception {
		when(factory.getData()).thenReturn(mockStream);
		when(mockStream.read()).thenReturn((int) 'W').thenReturn((int) 'o')
				.thenReturn((int) 'r').thenReturn((int) 'k')
				.thenReturn((int) 's').thenReturn((int) '!').thenReturn(-1);

		WebClient client = new WebClient();
		String workingContent = client.getContent(factory);

		assertEquals("Works!", workingContent);

	}

	@Test
	public void testGetContentCannotCloseInputStream() throws Exception {
		when(factory.getData()).thenReturn(mockStream);
		when(mockStream.read()).thenReturn(-1);
		doThrow(new IOException("cannot close")).when(mockStream).close();
		
		WebClient client = new WebClient();
		String workingContent = client.getContent(factory);
		
		assertNull(workingContent);
	}

}

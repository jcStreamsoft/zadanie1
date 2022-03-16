package zadanie1.interfaces;

import zadanie1.exceptions.streamInputExceptions.CreatingInputStringException;
import zadanie1.model.Request;

public interface InputConnection {
	public String getInputString(Request request) throws CreatingInputStringException;

}

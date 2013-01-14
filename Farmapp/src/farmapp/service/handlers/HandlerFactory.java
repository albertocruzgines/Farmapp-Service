package farmapp.service.handlers;

import java.io.IOException;
import java.util.Hashtable;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import farmapp.service.AuthFilter;

/**
 * @author sergio 
 * 
 */
public class HandlerFactory {
	private Hashtable<String, HandlerInfo> handlers = null;
	private static HandlerFactory instance = null;

	private HandlerFactory() {
		super();
		handlers = new Hashtable<String, HandlerInfo>();
		try {
			loadHandlers();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public final static HandlerFactory getInstance() {
		if (instance == null)
			instance = new HandlerFactory();
		return instance;
	}
	
	public final HandlerInfo getHandlerInfo(String action){
		return handlers.get(action);
	}

	@SuppressWarnings("unchecked")
	public final Handler createHandler(String action) throws HandlerException {
		String className = handlers.get(action).getClassName();
		if(className == null){
			throw new HandlerException("No handler class found for action "
					+ action);
		}
		Class<Handler> handlerClass;
		try {
			handlerClass = (Class<Handler>) Class.forName(className);
		} catch (ClassNotFoundException e1) {
			throw new HandlerException("No handler class found for action "
					+ action);
		}
		Handler instance = null;
		try {
			instance = (Handler) handlerClass.newInstance();
		} catch (InstantiationException e) {
			throw new HandlerException(e);
		} catch (IllegalAccessException e) {
			throw new HandlerException(e);
		}

		return instance;
	}

	private void loadHandlers() throws ParserConfigurationException,
			SAXException, IOException {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(AuthFilter.class
				.getResourceAsStream("/handlers.xml"));
		doc.getDocumentElement().normalize();

		NodeList nList = doc.getElementsByTagName("handler");

		for (int temp = 0; temp < nList.getLength(); temp++) {

			Node nNode = nList.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				HandlerInfo hInfo = new HandlerInfo();
				Element eElement = (Element) nNode;
				hInfo.setAuth(new Boolean(eElement.getAttribute("auth"))
						.booleanValue());
				hInfo.setAction(getTagValue("action", eElement));
				hInfo.setClassName(getTagValue("handler-class", eElement));
				handlers.put(hInfo.getAction(), hInfo);
			}
		}
	}

	private static String getTagValue(String sTag, Element eElement) {
		NodeList nlList = eElement.getElementsByTagName(sTag).item(0)
				.getChildNodes();

		Node nValue = (Node) nlList.item(0);

		return nValue.getNodeValue();
	}
}


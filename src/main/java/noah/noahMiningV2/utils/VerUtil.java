package noah.noahMiningV2.utils;

import lombok.SneakyThrows;
import noah.noahMiningV2.NoahMiningV2;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class VerUtil {

    @SneakyThrows
    public static void main(String[] args){
        File pomFile = new File("pom.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(pomFile);
        doc.getDocumentElement().normalize();

        NodeList versionNodes = doc.getElementsByTagName("version");
        Node versionNode = versionNodes.item(0);
        String version = versionNode.getTextContent();

        String[] parts = version.split("\\.");
        if(parts.length != 3){
            throw new IllegalArgumentException("Version must follow format x.y.z");
        }

        int patch = Integer.parseInt(parts[2])+1;
        String newVersion = parts[0]+'.'+parts[1]+'.'+patch;
        versionNode.setTextContent(newVersion);

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        Result output = new StreamResult(pomFile);
        Source input = new DOMSource(doc);
        transformer.transform(input, output);

        System.out.println("Upd. version: "+newVersion);
    }
}

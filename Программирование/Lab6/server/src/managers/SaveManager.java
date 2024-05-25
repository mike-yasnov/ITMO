package managers;

import models.*;
import auxiliary.Console;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;

public class SaveManager {
    private final String fileName;
    private final Console console;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.nnnnnnnnnXXXXX'['VV']'");

    public SaveManager(Console console) {
        this.fileName = System.getenv("PRODUCTS");
        this.console = console;
    }

    /**
     * Writes a collection of products to an XML file.
     *
     * @param collection the collection of products
     */
    public void writeCollection(Collection<Product> collection) {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(fileName);

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            Element root = doc.createElement("products");
            doc.appendChild(root);

            for (Product product : collection) {
                Element productElement = doc.createElement("product");
                root.appendChild(productElement);

                Element idElement = doc.createElement("id");
                idElement.appendChild(doc.createTextNode(String.valueOf(product.getId())));
                productElement.appendChild(idElement);

                Element nameElement = doc.createElement("name");
                nameElement.appendChild(doc.createTextNode(product.getName()));
                productElement.appendChild(nameElement);

                Element coordinatesElement = doc.createElement("coordinates");
                productElement.appendChild(coordinatesElement);

                Element xElement = doc.createElement("x");
                xElement.appendChild(doc.createTextNode(String.valueOf(product.getCoordinates().getX())));
                coordinatesElement.appendChild(xElement);

                Element yElement = doc.createElement("y");
                yElement.appendChild(doc.createTextNode(String.valueOf(product.getCoordinates().getY())));
                coordinatesElement.appendChild(yElement);

                Element creationDateElement = doc.createElement("creationDate");
                creationDateElement.appendChild(doc.createTextNode(product.getCreationDate().toString()));
                productElement.appendChild(creationDateElement);

                Element priceElement = doc.createElement("price");
                priceElement.appendChild(doc.createTextNode(String.valueOf(product.getPrice())));
                productElement.appendChild(priceElement);

                Element partNumberElement = doc.createElement("partNumber");
                partNumberElement.appendChild(doc.createTextNode(product.getPartNumber()));
                productElement.appendChild(partNumberElement);

                Element manufactureCostElement = doc.createElement("manufactureCost");
                manufactureCostElement.appendChild(doc.createTextNode(String.valueOf(product.getManufactureCost())));
                productElement.appendChild(manufactureCostElement);

                Element unitOfMeasureElement = doc.createElement("unitOfMeasure");
                unitOfMeasureElement.appendChild(doc.createTextNode(product.getUnitOfMeasure().name()));
                productElement.appendChild(unitOfMeasureElement);

                Element ownerElement = doc.createElement("owner");
                productElement.appendChild(ownerElement);

                Element ownerNameElement = doc.createElement("name");
                ownerNameElement.appendChild(doc.createTextNode(product.getOwner().getName()));
                ownerElement.appendChild(ownerNameElement);

                Element ownerHeightElement = doc.createElement("passportID");
                ownerHeightElement.appendChild(doc.createTextNode(String.valueOf(product.getOwner().getPassportID())));
                ownerElement.appendChild(ownerHeightElement);

                Element eyeColorElement = doc.createElement("eyeColor");
                eyeColorElement.appendChild(doc.createTextNode(product.getOwner().getEyeColor().name()));
                ownerElement.appendChild(eyeColorElement);

                Element hairColorElement = doc.createElement("hairColor");
                hairColorElement.appendChild(doc.createTextNode(product.getOwner().getHairColor().name()));
                ownerElement.appendChild(hairColorElement);

                Element nationalityElement = doc.createElement("nationality");
                nationalityElement.appendChild(doc.createTextNode(product.getOwner().getNationality().name()));
                ownerElement.appendChild(nationalityElement);

                Element locationElement = doc.createElement("location");
                ownerElement.appendChild(locationElement);

                Element locXElement = doc.createElement("x");
                locXElement.appendChild(doc.createTextNode(String.valueOf(product.getOwner().getLocation().getX())));
                locationElement.appendChild(locXElement);

                Element locYElement = doc.createElement("y");
                locYElement.appendChild(doc.createTextNode(String.valueOf(product.getOwner().getLocation().getY())));
                locationElement.appendChild(locYElement);

                Element locNameElement = doc.createElement("name");
                locNameElement.appendChild(doc.createTextNode(product.getOwner().getLocation().getName()));
                locationElement.appendChild(locNameElement);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);

            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            transformer.transform(source, result);

            fileOutputStream.write(writer.toString().getBytes());
            console.println("Коллекция продуктов успешно сохранена в файл!");
        } catch (ParserConfigurationException | TransformerException | IOException e) {
            console.printError(e);
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    console.printError("Error closing the stream");
                }
            }
        }
    }

    /**
     * Reads a collection from an XML file.
     */
    public Collection<Product> readCollection(List<Product> collection) {
        if (fileName != null && !fileName.isEmpty()) {
            try (FileReader fileReader = new FileReader(fileName)) {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse(new InputSource(fileReader));

                NodeList productNodes = doc.getElementsByTagName("product");
                for (int i = 0; i < productNodes.getLength(); i++) {
                    Element productElement = (Element) productNodes.item(i);
                    int id = Integer.parseInt(productElement.getElementsByTagName("id").item(0).getTextContent());
                    String name = productElement.getElementsByTagName("name").item(0).getTextContent();

                    Element coordinatesElement = (Element) productElement.getElementsByTagName("coordinates").item(0);
                    var x = Integer.parseInt(coordinatesElement.getElementsByTagName("x").item(0).getTextContent());
                    var y = Integer.parseInt(coordinatesElement.getElementsByTagName("y").item(0).getTextContent());

                    ZonedDateTime creationDate = ZonedDateTime.parse(productElement.getElementsByTagName("creationDate").item(0).getTextContent(), FORMATTER);

                    Long price = Long.parseLong(productElement.getElementsByTagName("price").item(0).getTextContent());
                    String partNumber = productElement.getElementsByTagName("partNumber").item(0).getTextContent();
                    int manufactureCost = Integer.parseInt(productElement.getElementsByTagName("manufactureCost").item(0).getTextContent());
                    UnitOfMeasure unitOfMeasure = UnitOfMeasure.valueOf(productElement.getElementsByTagName("unitOfMeasure").item(0).getTextContent());

                    Element ownerElement = (Element) productElement.getElementsByTagName("owner").item(0);
                    String ownerName = ownerElement.getElementsByTagName("name").item(0).getTextContent();
                    String ownerPassportID = ownerElement.getElementsByTagName("passportID").item(0).getTextContent();
                    EyeColor eyeColor = EyeColor.valueOf(ownerElement.getElementsByTagName("eyeColor").item(0).getTextContent());
                    HairColor hairColor = HairColor.valueOf(ownerElement.getElementsByTagName("hairColor").item(0).getTextContent());
                    Country nationality = Country.valueOf(ownerElement.getElementsByTagName("nationality").item(0).getTextContent());

                    Element locationElement = (Element) ownerElement.getElementsByTagName("location").item(0);
                    long locX = Long.parseLong(locationElement.getElementsByTagName("x").item(0).getTextContent());
                    Long locY = Long.parseLong(locationElement.getElementsByTagName("y").item(0).getTextContent());
                    String locName = locationElement.getElementsByTagName("name").item(0).getTextContent();

                    Location location = new Location(locX, Math.toIntExact(locY), locName);
                    Person owner = new Person(ownerName, ownerPassportID, eyeColor, hairColor, nationality, location);

                    Product product = new Product(id, name, new Coordinates((int) x, (int) y), creationDate, price, partNumber, manufactureCost, unitOfMeasure, owner);
                    collection.add(product);
                }

                console.println("Коллекция продуктов успешно загружена!");
            } catch (ParserConfigurationException | SAXException | IOException e) {
                console.printError(e);
            }
        } else {
            console.printError("Environment variable for filename not found!");
        }
        return collection;
    }
}


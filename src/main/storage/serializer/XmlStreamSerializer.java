package main.storage.serializer;

import main.model.*;
import main.util.XmlParser;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class XmlStreamSerializer implements Stream{

    private XmlParser xmlParser;

    public XmlStreamSerializer() {
        xmlParser = new XmlParser(
                Resume.class, Organization.class, Organization.Link.class,
                OrganizationSection.class, TextSection.class, ListSection.class, Organization.Place.class);
    }

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (Writer w = new OutputStreamWriter(os, StandardCharsets.UTF_8)) {
            xmlParser.marshall(r, w);
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (Reader r = new InputStreamReader(is, StandardCharsets.UTF_8)) {
            return xmlParser.unmarshall(r);
        }
    }
}

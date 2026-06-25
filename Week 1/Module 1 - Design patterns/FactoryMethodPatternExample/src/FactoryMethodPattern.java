interface Document {
    void create();
}

class WordDocument implements Document {
    public void create() {
        System.out.println("Creating Word Document");
    }
}

class PdfDocument implements Document {
    public void create() {
        System.out.println("Creating PDF Document");
    }
}

class ExcelDocument implements Document {
    public void create() {
        System.out.println("Creating Excel Document");
    }
}

abstract class DocumentFactory {
    abstract Document createDocument();
}

class WordFactory extends DocumentFactory {
    public Document createDocument() {
        return new WordDocument();
    }
}

class PdfFactory extends DocumentFactory {
    public Document createDocument() {
        return new PdfDocument();
    }
}

class ExcelFactory extends DocumentFactory {
    public Document createDocument() {
        return new ExcelDocument();
    }
}

public class FactoryMethodPattern {
    public static void main(String[] args) {

        DocumentFactory factory;

        factory = new WordFactory();
        Document document1 = factory.createDocument();
        document1.create();

        factory = new PdfFactory();
        Document document2 = factory.createDocument();
        document2.create();

        factory = new ExcelFactory();
        Document document3 = factory.createDocument();
        document3.create();
    }
}


// Output : 
// Creating Word Document
// Creating PDF Document
// Creating Excel Document